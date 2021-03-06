/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.persistence.jdbc.db;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.knowm.yank.Yank;
import org.openhab.core.items.GroupItem;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.ColorItem;
import org.openhab.core.library.items.ContactItem;
import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.NumberItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.StringItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.HSBType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.OpenClosedType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.persistence.FilterCriteria;
import org.openhab.core.persistence.FilterCriteria.Ordering;
import org.openhab.core.persistence.HistoricItem;
import org.openhab.core.types.State;
import org.openhab.persistence.jdbc.model.ItemVO;
import org.openhab.persistence.jdbc.model.ItemsVO;
import org.openhab.persistence.jdbc.model.JdbcItem;
import org.openhab.persistence.jdbc.utils.DbMetaData;
import org.openhab.persistence.jdbc.utils.StringUtilsExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Database Configuration class.
 *
 * @author Helmut Lehmeyer - Initial contribution
 */
public class JdbcBaseDAO {
    private final Logger logger = LoggerFactory.getLogger(JdbcBaseDAO.class);

    public Properties databaseProps = new Properties();
    protected String urlSuffix = "";
    public Map<String, String> sqlTypes = new HashMap<>();

    // Get Database Meta data
    protected DbMetaData dbMeta;

    protected String sqlPingDB;
    protected String sqlGetDB;
    protected String sqlIfTableExists;
    protected String sqlCreateNewEntryInItemsTable;
    protected String sqlCreateItemsTableIfNot;
    protected String sqlDeleteItemsEntry;
    protected String sqlGetItemIDTableNames;
    protected String sqlGetItemTables;
    protected String sqlCreateItemTable;
    protected String sqlInsertItemValue;

    /********
     * INIT *
     ********/
    public JdbcBaseDAO() {
        initSqlTypes();
        initDbProps();
        initSqlQueries();
    }

    /**
     * ## Get high precision by fractal seconds, examples ##
     *
     * mysql > 5.5 + mariadb > 5.2:
     * DROP TABLE FractionalSeconds;
     * CREATE TABLE FractionalSeconds (time TIMESTAMP(3), value TIMESTAMP(3));
     * INSERT INTO FractionalSeconds (time, value) VALUES( NOW(3), '1999-01-09 20:11:11.126' );
     * SELECT time FROM FractionalSeconds ORDER BY time DESC LIMIT 1;
     *
     * mysql <= 5.5 + mariadb <= 5.2: !!! NO high precision and fractal seconds !!!
     * DROP TABLE FractionalSeconds;
     * CREATE TABLE FractionalSeconds (time TIMESTAMP, value TIMESTAMP);
     * INSERT INTO FractionalSeconds (time, value) VALUES( NOW(), '1999-01-09 20:11:11.126' );
     * SELECT time FROM FractionalSeconds ORDER BY time DESC LIMIT 1;
     *
     * derby:
     * DROP TABLE FractionalSeconds;
     * CREATE TABLE FractionalSeconds (time TIMESTAMP, value TIMESTAMP);
     * INSERT INTO FractionalSeconds (time, value) VALUES( CURRENT_TIMESTAMP, '1999-01-09 20:11:11.126' );
     * SELECT time, value FROM FractionalSeconds;
     *
     * H2 + postgreSQL + hsqldb:
     * DROP TABLE FractionalSeconds;
     * CREATE TABLE FractionalSeconds (time TIMESTAMP, value TIMESTAMP);
     * INSERT INTO FractionalSeconds (time, value) VALUES( NOW(), '1999-01-09 20:11:11.126' );
     * SELECT time, value FROM FractionalSeconds;
     *
     * Sqlite:
     * DROP TABLE FractionalSeconds;
     * CREATE TABLE FractionalSeconds (time TIMESTAMP, value TIMESTAMP);
     * INSERT INTO FractionalSeconds (time, value) VALUES( strftime('%Y-%m-%d %H:%M:%f' , 'now' , 'localtime'),
     * '1999-01-09 20:11:11.124' );
     * SELECT time FROM FractionalSeconds ORDER BY time DESC LIMIT 1;
     *
     */

    private void initSqlQueries() {
        logger.debug("JDBC::initSqlQueries: '{}'", this.getClass().getSimpleName());
        sqlPingDB = "SELECT 1";
        sqlGetDB = "SELECT DATABASE()";
        sqlIfTableExists = "SHOW TABLES LIKE '#searchTable#'";

        sqlCreateNewEntryInItemsTable = "INSERT INTO #itemsManageTable# (ItemName) VALUES ('#itemname#')";
        sqlCreateItemsTableIfNot = "CREATE TABLE IF NOT EXISTS #itemsManageTable# (ItemId INT NOT NULL AUTO_INCREMENT,#colname# #coltype# NOT NULL,PRIMARY KEY (ItemId))";
        sqlDeleteItemsEntry = "DELETE FROM items WHERE ItemName=#itemname#";
        sqlGetItemIDTableNames = "SELECT itemid, itemname FROM #itemsManageTable#";
        sqlGetItemTables = "SELECT table_name FROM information_schema.tables WHERE table_type='BASE TABLE' AND table_schema='#jdbcUriDatabaseName#' AND NOT table_name='#itemsManageTable#'";
        sqlCreateItemTable = "CREATE TABLE IF NOT EXISTS #tableName# (time #tablePrimaryKey# NOT NULL, value #dbType#, PRIMARY KEY(time))";
        sqlInsertItemValue = "INSERT INTO #tableName# (TIME, VALUE) VALUES( #tablePrimaryValue#, ? ) ON DUPLICATE KEY UPDATE VALUE= ?";
    }

    /**
     * INFO: http://www.java2s.com/Code/Java/Database-SQL-JDBC/StandardSQLDataTypeswithTheirJavaEquivalents.htm
     */
    private void initSqlTypes() {
        logger.debug("JDBC::initSqlTypes: Initialize the type array");
        sqlTypes.put("CALLITEM", "VARCHAR(200)");
        sqlTypes.put("COLORITEM", "VARCHAR(70)");
        sqlTypes.put("CONTACTITEM", "VARCHAR(6)");
        sqlTypes.put("DATETIMEITEM", "TIMESTAMP");
        sqlTypes.put("DIMMERITEM", "TINYINT");
        sqlTypes.put("LOCATIONITEM", "VARCHAR(30)");
        sqlTypes.put("NUMBERITEM", "DOUBLE");
        sqlTypes.put("ROLLERSHUTTERITEM", "TINYINT");
        sqlTypes.put("STRINGITEM", "VARCHAR(65500)");// jdbc max 21845
        sqlTypes.put("SWITCHITEM", "VARCHAR(6)");
        sqlTypes.put("tablePrimaryKey", "TIMESTAMP");
        sqlTypes.put("tablePrimaryValue", "NOW()");
    }

    /**
     * INFO: https://github.com/brettwooldridge/HikariCP
     *
     * driverClassName (used with jdbcUrl):
     * Derby: org.apache.derby.jdbc.EmbeddedDriver
     * H2: org.h2.Driver
     * HSQLDB: org.hsqldb.jdbcDriver
     * Jaybird: org.firebirdsql.jdbc.FBDriver
     * MariaDB: org.mariadb.jdbc.Driver
     * MySQL: com.mysql.jdbc.Driver
     * MaxDB: com.sap.dbtech.jdbc.DriverSapDB
     * PostgreSQL: org.postgresql.Driver
     * SyBase: com.sybase.jdbc3.jdbc.SybDriver
     * SqLite: org.sqlite.JDBC
     *
     * dataSourceClassName (for alternative Configuration):
     * Derby: org.apache.derby.jdbc.ClientDataSource
     * H2: org.h2.jdbcx.JdbcDataSource
     * HSQLDB: org.hsqldb.jdbc.JDBCDataSource
     * Jaybird: org.firebirdsql.pool.FBSimpleDataSource
     * MariaDB, MySQL: org.mariadb.jdbc.MySQLDataSource
     * MaxDB: com.sap.dbtech.jdbc.DriverSapDB
     * PostgreSQL: org.postgresql.ds.PGSimpleDataSource
     * SyBase: com.sybase.jdbc4.jdbc.SybDataSource
     * SqLite: org.sqlite.SQLiteDataSource
     *
     * HikariPool - configuration Example:
     * allowPoolSuspension.............false
     * autoCommit......................true
     * catalog.........................
     * connectionInitSql...............
     * connectionTestQuery.............
     * connectionTimeout...............30000
     * dataSource......................
     * dataSourceClassName.............
     * dataSourceJNDI..................
     * dataSourceProperties............{password=<masked>}
     * driverClassName.................
     * healthCheckProperties...........{}
     * healthCheckRegistry.............
     * idleTimeout.....................600000
     * initializationFailFast..........true
     * isolateInternalQueries..........false
     * jdbc4ConnectionTest.............false
     * jdbcUrl.........................jdbc:mysql://192.168.0.1:3306/test
     * leakDetectionThreshold..........0
     * maxLifetime.....................1800000
     * maximumPoolSize.................10
     * metricRegistry..................
     * metricsTrackerFactory...........
     * minimumIdle.....................10
     * password........................<masked>
     * poolName........................HikariPool-0
     * readOnly........................false
     * registerMbeans..................false
     * scheduledExecutorService........
     * threadFactory...................
     * transactionIsolation............
     * username........................xxxx
     * validationTimeout...............5000
     */
    private void initDbProps() {
        // databaseProps.setProperty("dataSource.url", "jdbc:mysql://192.168.0.1:3306/test");
        // databaseProps.setProperty("dataSource.user", "test");
        // databaseProps.setProperty("dataSource.password", "test");

        // Most relevant Performance values
        // maximumPoolSize to 20, minimumIdle to 5, and idleTimeout to 2 minutes.
        // databaseProps.setProperty("maximumPoolSize", ""+maximumPoolSize);
        // databaseProps.setProperty("minimumIdle", ""+minimumIdle);
        // databaseProps.setProperty("idleTimeout", ""+idleTimeout);
        // databaseProps.setProperty("connectionTimeout",""+connectionTimeout);
        // databaseProps.setProperty("idleTimeout", ""+idleTimeout);
        // databaseProps.setProperty("maxLifetime", ""+maxLifetime);
        // databaseProps.setProperty("validationTimeout",""+validationTimeout);
    }

    public void initAfterFirstDbConnection() {
        logger.debug("JDBC::initAfterFirstDbConnection: Initializing step, after db is connected.");
        // Initialize sqlTypes, depending on DB version for example
        dbMeta = new DbMetaData();// get DB information
    }

    /**************
     * ITEMS DAOs *
     **************/
    public Integer doPingDB() {
        return Yank.queryScalar(sqlPingDB, Integer.class, null);
    }

    public String doGetDB() {
        return Yank.queryScalar(sqlGetDB, String.class, null);
    }

    public boolean doIfTableExists(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlIfTableExists, new String[] { "#searchTable#" },
                new String[] { vo.getItemsManageTable() });
        logger.debug("JDBC::doIfTableExists sql={}", sql);
        return Yank.queryScalar(sql, String.class, null) != null;
    }

    public Long doCreateNewEntryInItemsTable(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlCreateNewEntryInItemsTable,
                new String[] { "#itemsManageTable#", "#itemname#" },
                new String[] { vo.getItemsManageTable(), vo.getItemname() });
        logger.debug("JDBC::doCreateNewEntryInItemsTable sql={}", sql);
        return Yank.insert(sql, null);
    }

    public ItemsVO doCreateItemsTableIfNot(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlCreateItemsTableIfNot,
                new String[] { "#itemsManageTable#", "#colname#", "#coltype#" },
                new String[] { vo.getItemsManageTable(), vo.getColname(), vo.getColtype() });
        logger.debug("JDBC::doCreateItemsTableIfNot sql={}", sql);
        Yank.execute(sql, null);
        return vo;
    }

    public void doDeleteItemsEntry(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlDeleteItemsEntry, new String[] { "#itemname#" },
                new String[] { vo.getItemname() });
        logger.debug("JDBC::doDeleteItemsEntry sql={}", sql);
        Yank.execute(sql, null);
    }

    public List<ItemsVO> doGetItemIDTableNames(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlGetItemIDTableNames, new String[] { "#itemsManageTable#" },
                new String[] { vo.getItemsManageTable() });
        logger.debug("JDBC::doGetItemIDTableNames sql={}", sql);
        return Yank.queryBeanList(sql, ItemsVO.class, null);
    }

    public List<ItemsVO> doGetItemTables(ItemsVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlGetItemTables,
                new String[] { "#jdbcUriDatabaseName#", "#itemsManageTable#" },
                new String[] { vo.getJdbcUriDatabaseName(), vo.getItemsManageTable() });
        logger.debug("JDBC::doGetItemTables sql={}", sql);
        return Yank.queryBeanList(sql, ItemsVO.class, null);
    }

    /*************
     * ITEM DAOs *
     *************/
    public void doUpdateItemTableNames(List<ItemVO> vol) {
        if (!vol.isEmpty()) {
            String sql = updateItemTableNamesProvider(vol);
            Yank.execute(sql, null);
        }
    }

    public void doCreateItemTable(ItemVO vo) {
        String sql = StringUtilsExt.replaceArrayMerge(sqlCreateItemTable,
                new String[] { "#tableName#", "#dbType#", "#tablePrimaryKey#" },
                new String[] { vo.getTableName(), vo.getDbType(), sqlTypes.get("tablePrimaryKey") });
        logger.debug("JDBC::doCreateItemTable sql={}", sql);
        Yank.execute(sql, null);
    }

    public void doStoreItemValue(Item item, ItemVO vo) {
        vo = storeItemValueProvider(item, vo);
        String sql = StringUtilsExt.replaceArrayMerge(sqlInsertItemValue,
                new String[] { "#tableName#", "#tablePrimaryValue#" },
                new String[] { vo.getTableName(), sqlTypes.get("tablePrimaryValue") });
        Object[] params = new Object[] { vo.getValue(), vo.getValue() };
        logger.debug("JDBC::doStoreItemValue sql={} value='{}'", sql, vo.getValue());
        Yank.execute(sql, params);
    }

    public List<HistoricItem> doGetHistItemFilterQuery(Item item, FilterCriteria filter, int numberDecimalcount,
            String table, String name) {
        String sql = histItemFilterQueryProvider(filter, numberDecimalcount, table, name);
        logger.debug("JDBC::doGetHistItemFilterQuery sql={}", sql);
        List<Object[]> m = Yank.queryObjectArrays(sql, null);

        List<HistoricItem> items = new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {
            items.add(new JdbcItem(item.getName(), getState(item, m.get(i)[1]), objectAsDate(m.get(i)[0])));
        }
        return items;
    }

    /*************
     * Providers *
     *************/
    static final DateTimeFormatter JDBC_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String histItemFilterQueryProvider(FilterCriteria filter, int numberDecimalcount, String table,
            String simpleName) {
        logger.debug(
                "JDBC::getHistItemFilterQueryProvider filter = {}, numberDecimalcount = {}, table = {}, simpleName = {}",
                filter.toString(), numberDecimalcount, table, simpleName);

        String filterString = "";
        if (filter.getBeginDate() != null) {
            filterString += filterString.isEmpty() ? " WHERE" : " AND";
            filterString += " TIME>'" + JDBC_DATE_FORMAT.format(filter.getBeginDateZoned()) + "'";
        }
        if (filter.getEndDate() != null) {
            filterString += filterString.isEmpty() ? " WHERE" : " AND";
            filterString += " TIME<'" + JDBC_DATE_FORMAT.format(filter.getEndDateZoned()) + "'";
        }
        filterString += (filter.getOrdering() == Ordering.ASCENDING) ? " ORDER BY time ASC" : " ORDER BY time DESC ";
        if (filter.getPageSize() != 0x7fffffff) {
            filterString += " LIMIT " + filter.getPageNumber() * filter.getPageSize() + "," + filter.getPageSize();
        }
        // SELECT time, ROUND(value,3) FROM number_item_0114 ORDER BY time DESC LIMIT 0,1
        // rounding HALF UP
        String queryString = "NUMBERITEM".equalsIgnoreCase(simpleName) && numberDecimalcount > -1
                ? "SELECT time, ROUND(value," + numberDecimalcount + ") FROM " + table
                : "SELECT time, value FROM " + table;
        if (!filterString.isEmpty()) {
            queryString += filterString;
        }
        logger.debug("JDBC::query queryString = {}", queryString);
        return queryString;
    }

    private String updateItemTableNamesProvider(List<ItemVO> namesList) {
        logger.debug("JDBC::updateItemTableNamesProvider namesList.size = {}", namesList.size());
        String queryString = "";
        for (int i = 0; i < namesList.size(); i++) {
            ItemVO it = namesList.get(i);
            queryString += "ALTER TABLE " + it.getTableName() + " RENAME TO " + it.getNewTableName() + ";";
        }
        logger.debug("JDBC::query queryString = {}", queryString);
        return queryString;
    }

    protected ItemVO storeItemValueProvider(Item item, ItemVO vo) {
        String itemType = getItemType(item);

        logger.debug("JDBC::storeItemValueProvider: item '{}' as Type '{}' in '{}' with state '{}'", item.getName(),
                itemType, vo.getTableName(), item.getState().toString());

        // insertItemValue
        logger.debug("JDBC::storeItemValueProvider: getState: '{}'", item.getState().toString());
        if ("COLORITEM".equals(itemType)) {
            vo.setValueTypes(getSqlTypes().get(itemType), java.lang.String.class);
            vo.setValue(item.getState().toString());
        } else if ("NUMBERITEM".equals(itemType)) {
            String it = getSqlTypes().get(itemType);
            if (it.toUpperCase().contains("DOUBLE")) {
                vo.setValueTypes(it, java.lang.Double.class);
                Number newVal = ((DecimalType) item.getState());
                logger.debug("JDBC::storeItemValueProvider: newVal.doubleValue: '{}'", newVal.doubleValue());
                vo.setValue(newVal.doubleValue());
            } else if (it.toUpperCase().contains("DECIMAL") || it.toUpperCase().contains("NUMERIC")) {
                vo.setValueTypes(it, java.math.BigDecimal.class);
                DecimalType newVal = ((DecimalType) item.getState());
                logger.debug("JDBC::storeItemValueProvider: newVal.toBigDecimal: '{}'", newVal.toBigDecimal());
                vo.setValue(newVal.toBigDecimal());
            } else if (it.toUpperCase().contains("INT")) {
                vo.setValueTypes(it, java.lang.Integer.class);
                Number newVal = ((DecimalType) item.getState());
                logger.debug("JDBC::storeItemValueProvider: newVal.intValue: '{}'", newVal.intValue());
                vo.setValue(newVal.intValue());
            } else {// fall back to String
                vo.setValueTypes(it, java.lang.String.class);
                logger.warn("JDBC::storeItemValueProvider: item.getState().toString(): '{}'",
                        item.getState().toString());
                vo.setValue(item.getState().toString());
            }
        } else if ("ROLLERSHUTTERITEM".equals(itemType) || "DIMMERITEM".equals(itemType)) {
            vo.setValueTypes(getSqlTypes().get(itemType), java.lang.Integer.class);
            Number newVal = ((DecimalType) item.getState());
            logger.debug("JDBC::storeItemValueProvider: newVal.intValue: '{}'", newVal.intValue());
            vo.setValue(newVal.intValue());
        } else if ("DATETIMEITEM".equals(itemType)) {
            vo.setValueTypes(getSqlTypes().get(itemType), java.sql.Timestamp.class);
            java.sql.Timestamp d = new java.sql.Timestamp(
                    ((DateTimeType) item.getState()).getZonedDateTime().toInstant().toEpochMilli());
            logger.debug("JDBC::storeItemValueProvider: DateTimeItem: '{}'", d);
            vo.setValue(d);
        } else {
            /*
             * !!ATTENTION!!
             *
             * 1. DimmerItem.getStateAs(PercentType.class).toString() always
             * returns 0
             * RollershutterItem.getStateAs(PercentType.class).toString() works
             * as expected
             *
             * 2. (item instanceof ColorItem) == (item instanceof DimmerItem) =
             * true Therefore for instance tests ColorItem always has to be
             * tested before DimmerItem
             *
             * !!ATTENTION!!
             */
            // All other items should return the best format by default
            vo.setValueTypes(getSqlTypes().get(itemType), java.lang.String.class);
            logger.debug("JDBC::storeItemValueProvider: other: item.getState().toString(): '{}'",
                    item.getState().toString());
            vo.setValue(item.getState().toString());
        }
        return vo;
    }

    /*****************
     * H E L P E R S *
     *****************/
    protected State getState(Item item, Object v) {
        String clazz = v.getClass().getSimpleName();
        logger.debug("JDBC::ItemResultHandler::handleResult getState value = '{}', getClass = '{}', clazz = '{}'",
                v.toString(), v.getClass(), clazz);
        if (item instanceof NumberItem) {
            String it = getSqlTypes().get("NUMBERITEM");
            if (it.toUpperCase().contains("DOUBLE")) {
                return new DecimalType(((Number) v).doubleValue());
            } else if (it.toUpperCase().contains("DECIMAL") || it.toUpperCase().contains("NUMERIC")) {
                return new DecimalType((BigDecimal) v);
            } else if (it.toUpperCase().contains("INT")) {
                return new DecimalType(((Integer) v).intValue());
            }
            return DecimalType.valueOf(((String) v).toString());
        } else if (item instanceof ColorItem) {
            return HSBType.valueOf(((String) v).toString());
        } else if (item instanceof DimmerItem) {
            return new PercentType(objectAsInteger(v));
        } else if (item instanceof SwitchItem) {
            return OnOffType.valueOf(((String) v).toString().trim());
        } else if (item instanceof ContactItem) {
            return OpenClosedType.valueOf(((String) v).toString().trim());
        } else if (item instanceof RollershutterItem) {
            return new PercentType(objectAsInteger(v));
        } else if (item instanceof DateTimeItem) {
            return new DateTimeType(
                    ZonedDateTime.ofInstant(Instant.ofEpochMilli(objectAsLong(v)), ZoneId.systemDefault()));
        } else if (item instanceof StringItem) {
            return StringType.valueOf(((String) v).toString());
        } else {// Call, Location, String
            return StringType.valueOf(((String) v).toString());
        }
    }

    protected Date objectAsDate(Object v) {
        if (v instanceof java.lang.String) {
            // toInstant is Java8 only: return Date.from(Timestamp.valueOf(v.toString()).toInstant());
            return new Date(Timestamp.valueOf(v.toString()).getTime());
        }
        // toInstant is Java8 only: return Date.from(((Timestamp) v).toInstant());
        return new Date(((Timestamp) v).getTime());
    }

    protected Long objectAsLong(Object v) {
        if (v instanceof Long) {
            return ((Number) v).longValue();
        } else if (v instanceof java.sql.Date) {
            return ((java.sql.Date) v).getTime();
        }
        return ((java.sql.Timestamp) v).getTime();
    }

    protected Integer objectAsInteger(Object v) {
        if (v instanceof Byte) {
            return ((Byte) v).intValue();
        }
        return ((Integer) v).intValue();
    }

    public String getItemType(Item i) {
        Item item = i;
        String def = "STRINGITEM";
        if (i instanceof GroupItem) {
            item = ((GroupItem) i).getBaseItem();
            if (item == null) {
                // if GroupItem:<ItemType> is not defined in
                // *.items using StringType
                // logger.debug("JDBC: BaseItem GroupItem:<ItemType> is not
                // defined in *.items searching for first Member and try to use
                // as ItemType");
                logger.debug(
                        "JDBC::getItemType: Cannot detect ItemType for {} because the GroupItems' base type isn't set in *.items File.",
                        i.getName());
                item = ((GroupItem) i).getMembers().iterator().next();
                if (item == null) {
                    logger.debug(
                            "JDBC::getItemType: No ItemType found for first Child-Member of GroupItem {}, use ItemType for STRINGITEM as Fallback",
                            i.getName());
                    return def;
                }
            }
        }
        String itemType = item.getClass().getSimpleName().toUpperCase();
        logger.debug("JDBC::getItemType: Try to use ItemType {} for Item {}", itemType, i.getName());
        if (sqlTypes.get(itemType) == null) {
            logger.warn(
                    "JDBC::getItemType: No sqlType found for ItemType {}, use ItemType for STRINGITEM as Fallback for {}",
                    itemType, i.getName());
            return def;
        }
        return itemType;
    }

    /******************************
     * public Getters and Setters *
     ******************************/
    public Map<String, String> getSqlTypes() {
        return sqlTypes;
    }

    public String getDataType(Item item) {
        return sqlTypes.get(getItemType(item));
    }
}
