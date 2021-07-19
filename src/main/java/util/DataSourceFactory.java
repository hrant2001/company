package util;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceFactory.class);

    private static PoolingDataSource dataSource = null;
    private static final PropertyManager PROPERTY_MANAGER = PropertyManager.getInstance();

    private final static String CONNECTION_URI;
    private final static String USERNAME;
    private final static String PASSWORD;

    static {
        USERNAME = PROPERTY_MANAGER.getProperty("username");
        PASSWORD = PROPERTY_MANAGER.getProperty("password");
        CONNECTION_URI = PROPERTY_MANAGER.getProperty("connection-uri");
    }

    public static synchronized PoolingDataSource getInstance() {
        if (dataSource == null) {
            try {
                Class.forName(PROPERTY_MANAGER.getProperty("db-driver")).newInstance();
                dataSource = setupDataSource(CONNECTION_URI, USERNAME, PASSWORD);
            } catch (Exception e) {
                LOGGER.error("DB datasource creation failed: " + e);
                System.exit(1);
            }
        }
        return dataSource;
    }

    private static PoolingDataSource setupDataSource(String connectURI, String user, String password) {
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        config.maxActive = 150;
        config.maxIdle = 100;
        config.minIdle = 30;
        config.maxWait = 1000;

        ObjectPool connectionPool = new GenericObjectPool(null, config);

        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory(connectURI, user, password);

        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(
                        connectionFactory, connectionPool, null, null, false, true);

        PoolingDataSource poolingDataSource = new PoolingDataSource(connectionPool);

        return poolingDataSource;
    }
}