package com.packt.cookbook.ch06_db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class HikariPool {
    public static void main(String... args) {
/*
        readData(createDataSource1());
        readData(createDataSource2());
        readData(createDataSource3());
        readData(createDataSource4());
        readData(new HikariPool().createDataSource5());
*/
    }

    private static void readData(DataSource ds) {
        try(Connection conn = ds.getConnection();
            PreparedStatement pst = conn.prepareStatement("Select id, type, value from enums");
            ResultSet rs = pst.executeQuery()){
            System.out.println();
            while (rs.next()) {
                int id = rs.getInt(1);
                String type = rs.getString(2);
                String value = rs.getString(3);
                System.out.println("id = " + id + ", type = " + type + ", value = " + value);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    private static DataSource createDataSource1() {
        HikariDataSource ds = new HikariDataSource();
        ds.setPoolName("cookpool");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl("jdbc:postgresql://localhost/cookbook");
        ds.setUsername( "cook");
        //ds.setPassword("123Secret");
        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.addDataSourceProperty("cachePrepStmts", Boolean.TRUE);
        ds.addDataSourceProperty("prepStmtCacheSize", 256);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        ds.addDataSourceProperty("useServerPrepStmts", Boolean.TRUE);
        return ds;
    }

    private static DataSource createDataSource2() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("cookpool");
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost/cookbook");
        config.setUsername("cook");
        //conf.setPassword("123Secret");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 256);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);

        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    private static DataSource createDataSource3() {
        Properties props = new Properties();
        props.setProperty("poolName", "cookpool");
        props.setProperty("driverClassName", "org.postgresql.Driver");
        props.setProperty("jdbcUrl", "jdbc:postgresql://localhost/cookbook");
        props.setProperty("username", "cook");
        //props.setProperty("password", "123Secret");
        props.setProperty("maximumPoolSize", "10");
        props.setProperty("minimumIdle", "2");
        props.setProperty("dataSource.cachePrepStmts","true");
        props.setProperty("dataSource.prepStmtCacheSize", "256");
        props.setProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        props.setProperty("dataSource.useServerPrepStmts","true");

        HikariConfig config = new HikariConfig(props);
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    private static DataSource createDataSource4() {
        //Set system property -Dhikaricp.configurationFile=src/main/resources/database.properties
        HikariConfig config = new HikariConfig();
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    private DataSource createDataSource5() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("database.properties").getFile());
        HikariConfig config = new HikariConfig(file.getAbsolutePath());
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

}

