package util;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static final BasicDataSource DATASOURCE = new BasicDataSource();
    private static final PropertyManager PROPERTY_MANAGER = PropertyManager.getInstance();
//    private static ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
//            PROPERTY_MANAGER.getProperty("connection-url"), PROPERTY_MANAGER.getProperty("username"), PROPERTY_MANAGER.getProperty("password"));
//    private static GenericObjectPool<Connection> connectionPool = new GenericObjectPool<Connection>(connectionFactory);
//    private static PoolingDataSource<Connection> pool = new PoolingDataSource<>(connectionPool);

    static {
        DATASOURCE.setUrl(PROPERTY_MANAGER.getProperty("connection-url"));
        DATASOURCE.setUsername(PROPERTY_MANAGER.getProperty("username"));
        DATASOURCE.setPassword(PROPERTY_MANAGER.getProperty("password"));
        DATASOURCE.setDriverClassName(PROPERTY_MANAGER.getProperty("db-driver"));
    }

    private DBCPDataSource() {
    }

    public static DataSource getDataSource() throws SQLException {
        return DATASOURCE;
    }
}
