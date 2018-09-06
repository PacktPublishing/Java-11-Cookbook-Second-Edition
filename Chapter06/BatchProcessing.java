package com.packt.cookbook.ch06_db;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BatchProcessing {
    private static int n = 100_000;
    public static void main(String... args) {
        //executeStatement("truncate table person");

        Instant start = Instant.now();
        //insertUsingStatements();                           //29802 ms
        //insertUsingPreparedStatements();                   //26178 ms
        //insertUsingOneStatement();                         // 1082 ms
        //insertUsingMaxPreparedStatement();                 // 1175 ms
        //insertUsingStatementBatch();                       // 5342 ms
        //insertUsingPreparedStatementBatch();               // 2299 ms
        //insertUsingPreparedStatementBatchWithReWrite();    //  750 ms
        //insertUsingPreparedStatementBatchesWithReWrite();  //  715 ms
        //System.out.println(Statement.SUCCESS_NO_INFO);
        //updateUsingPreparedStatementBatchWithReWrite();    // 2954 ms

        //readUsingStatements();                  //2338
        //readUsingPreparedStatements();          //2300
        //readUsingOneStatement();                //2338
        readUsingOnePreparedStatement();        //2209

        int c = countRecords();
        System.out.println(c + " records in " + Duration.between(start, Instant.now()).toMillis() + " ms");
    }

    private static void readUsingOnePreparedStatement() {
        int minAge = 0, maxAge = 0, minCount = n, maxCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("select count(*) from person where age = ").append(i).append(";");
        }
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sb.toString())) {
            boolean hasResult = pst.execute();
            int i = 0;
            while (hasResult){
                try (ResultSet rs = pst.getResultSet()) {
                    rs.next();
                    int c = rs.getInt(1);
                    if(c < minCount) {
                        minAge = i;
                        minCount = c;
                    }
                    if(c > maxCount) {
                        maxAge = i;
                        maxCount = c;
                    }
                    i++;
                    hasResult = pst.getMoreResults();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("least popular age=" + minAge + "(" + minCount + "), most popular age=" + maxAge + "(" + maxCount + ")");
    }

    private static void readUsingOneStatement() {
        int minAge = 0, maxAge = 0, minCount = n, maxCount = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("select count(*) from person where age = ").append(i).append(";");
        }
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()){
            boolean hasResult = st.execute(sb.toString());
            int i = 0;
            while (hasResult){
                try (ResultSet rs = st.getResultSet()) {
                    rs.next();
                    int c = rs.getInt(1);
                    if(c < minCount) {
                        minAge = i;
                        minCount = c;
                    }
                    if(c > maxCount) {
                        maxAge = i;
                        maxCount = c;
                    }
                    i++;
                    hasResult = st.getMoreResults();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("least popular age=" + minAge + "(" + minCount + "), most popular age=" + maxAge + "(" + maxCount + ")");
    }

    private static void readUsingPreparedStatements() {
        int minAge = 0, maxAge = 0, minCount = n, maxCount = 0;
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement("select count(*) from person where age = ?")) {
            for (int i = 0; i < 100; i++) {
                pst.setInt(1, i);
                try(ResultSet rs = pst.executeQuery()){
                    rs.next();
                    int c = rs.getInt(1);
                    if(c < minCount) {
                        minAge = i;
                        minCount = c;
                    }
                    if(c > maxCount) {
                        maxAge = i;
                        maxCount = c;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("least popular age=" + minAge + "(" + minCount + "), most popular age=" + maxAge + "(" + maxCount + ")");
        //least popular age=20(925), most popular age=1(1072)
    }

    private static void readUsingStatements() {
        int minAge = 0, maxAge = 0, minCount = n, maxCount = 0;
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()){
            for (int i = 0; i < 100; i++) {
                try(ResultSet rs = st.executeQuery("select count(*) from person where age = " + i)){
                    rs.next();
                    int c = rs.getInt(1);
                    if(c < minCount) {
                        minAge = i;
                        minCount = c;
                    }
                    if(c > maxCount) {
                        maxAge = i;
                        maxCount = c;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("least popular age=" + minAge + "(" + minCount + "), most popular age=" + maxAge + "(" + maxCount + ")");
    }

    private static void updateUsingPreparedStatementBatchWithReWrite() {
        try (Connection conn = createDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement("update person set name = 'Updated' where age = ?")) {
            for (int i = 0; i < 100; i++) {
                pst.setInt(1, i);
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertUsingPreparedStatementBatchesWithReWrite() {
        int batchSize = 30000;
        boolean execute = false;
        try (Connection conn = createDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement("insert into person (name, age) values (?, ?)")) {
            for (int i = 0; i < n; i++) {
                pst.setString(1, "Name" + String.valueOf(i));
                pst.setInt(2, (int)(Math.random() * 100));
                pst.addBatch();
                if((i > 0 && i % batchSize == 0) || (i == n - 1 && execute)) {
                    pst.executeBatch();
                    System.out.println(i);
                    if(n - 1 - i < batchSize && !execute){
                        execute = true;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertUsingPreparedStatementBatchWithReWrite() {
        try (Connection conn = createDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement("insert into person (name, age) values (?, ?)")) {
            for (int i = 0; i < n; i++) {
                pst.setString(1, "Name" + String.valueOf(i));
                pst.setInt(2, (int)(Math.random() * 100));
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertUsingPreparedStatementBatch() {
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement("insert into person (name, age) values (?, ?)")) {
            for (int i = 0; i < n; i++) {
                pst.setString(1, "Name" + String.valueOf(i));
                pst.setInt(2, (int)(Math.random() * 100));
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertUsingStatementBatch() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()){
            for (int i = 0; i < n; i++) {
                st.addBatch("insert into person (name, age) values ('Name" + String.valueOf(i) + "', " +
                        (String.valueOf((int)(Math.random() * 100))) + ")");
            }
            st.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertUsingMaxPreparedStatement() {
        int limit = 32766, l = 0;
        List<String> queries = new ArrayList<>();
        List<Integer> bindVariablesCount = new ArrayList<>();
        String insert = "insert into person (name, age) values ";
        StringBuilder sb = new StringBuilder(insert);
        for(int i = 0; i < n; i++){
            sb.append("(?, ?)");
            l = l + 2;
            if(i == n - 1) {
                queries.add(sb.toString());
                bindVariablesCount.add(l % limit);
            }
            if(l % limit == 0) {
                queries.add(sb.toString());
                bindVariablesCount.add(limit);
                sb = new StringBuilder(insert);
            } else {
                sb.append(",");
            }
        }
        try(Connection conn = getConnection()) {
            int i = 0, q = 0;
            for(String query: queries){
                try(PreparedStatement pst = conn.prepareStatement(query)) {
                    int j = 0;
                    while (j < bindVariablesCount.get(q)) {
                        pst.setString(++j, "Name" + String.valueOf(i++));
                        pst.setInt(++j, (int)(Math.random() * 100));
                    }
                    pst.executeUpdate();
                    q++;
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private static void insertUsingOneStatement() {
        StringBuilder sb = new StringBuilder("insert into person (name, age) values ");
        for(int i = 0; i < n; i++){
            sb.append("(").append("'Name").append(String.valueOf(i)).append("',")
                    .append(String.valueOf((int)(Math.random() * 100))).append(")");
            if(i < n - 1) {
                sb.append(",");
            }
        }
        try(Connection conn = getConnection();
            Statement st = conn.createStatement()){
            st.execute(sb.toString());
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private static void insertUsingPreparedStatements() {
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement("insert into person (name, age) values (?, ?)")) {
            for (int i = 0; i < n; i++) {
                pst.setString(1, "Name" + String.valueOf(i));
                pst.setInt(2, (int)(Math.random() * 100));
                pst.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void insertUsingStatements() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()){
            for (int i = 0; i < n; i++) {
                st.execute("insert into person (name, age) values ('Name" + String.valueOf(i) + "', " +
                        (String.valueOf((int)(Math.random() * 100))) + ")");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static int countRecords() {
        int count = -1;
        try(Connection conn = getConnection();
            Statement st = conn.createStatement()){
            ResultSet rs = st.executeQuery("select count(*) from person");
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return count;
    }

    private static void executeStatement(String sql) {
        try(Connection conn = getConnection();
            Statement st = conn.createStatement()){
            st.execute(sql);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private static Connection getConnection(){
        String url = "jdbc:postgresql://localhost/cookbook?rewriteBatchedStatements=true";
        try {
            return DriverManager.getConnection(url);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create DB connection using url=" + url);
        }
    }

    private static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setPoolName("cookpool");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl("jdbc:postgresql://localhost/cookbook");
        ds.setUsername( "cook");
        //ds.setPassword("123Secret");
        ds.setMaximumPoolSize(2);
        ds.setMinimumIdle(2);
        ds.addDataSourceProperty("reWriteBatchedInserts", Boolean.TRUE);
        return ds;
    }


}
