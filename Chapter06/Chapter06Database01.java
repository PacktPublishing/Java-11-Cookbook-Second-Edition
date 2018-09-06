package com.packt.cookbook.ch06_db;

import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Chapter06Database01 {
    public static void main(String... args) {
/*
        demo1_connect();
        demo2_execute_select();
        demo3_execute_insert();
        demo4_traverseRS();
        demo5_select_update_delete_select();
        demo6_prepared();
        demo7_prepared_insert();
        demo8_transaction();
        demo9_transaction();
        demo10_transaction();
        demo11_transaction();
*/
    }

    private static void demo1_connect(){
        try (Connection conn = getDbConnection()) {
            //code goes here
        } catch(Exception ex) { ex.printStackTrace(); }
    }

    private static void demo2_execute_select(){
        System.out.println();
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                boolean res = st.execute("Select id, type, value from enums");
                if (res) {
                    ResultSet rs = st.getResultSet();
                    while (rs.next()) {
                        int id = rs.getInt(1); //More efficient than rs.getInt("id")
                        String type = rs.getString(2);
                        String value = rs.getString(3);
                        System.out.println("id = " + id + ", type = " + type + ", value = " + value);
                    }
                } else {
                    int count = st.getUpdateCount();
                    System.out.println("Update count = " + count);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeUpdate1(String sql){
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                int count = st.executeUpdate(sql);
                System.out.println("Update count = " + count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeUpdate(String sql){
        try (Connection conn = getDbConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void demo3_execute_insert(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                boolean res = st.execute("insert into enums (id, type, value) values(1,'vehicle','car')");
                if (res) {
                    ResultSet rs = st.getResultSet();
                    while (rs.next()) {
                        int id = rs.getInt(1); //More efficient than rs.getInt("id")
                        String type = rs.getString(2);
                        String value = rs.getString(3);
                        System.out.println("id = " + id + ", type = " + type + ", value = " + value);
                    }
                } else {
                    int count = st.getUpdateCount();
                    System.out.println("Update count = " + count);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void demo4_traverseRS(){
        System.out.println();
        traverseRS("Select * from enums");
    }

    private static void traverseRS1(String sql){
        System.out.println("traverseRS(" + sql + "):");
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                try(ResultSet rs = st.executeQuery(sql)){
                    int cCount = 0;
                    Map<Integer, String> cName = new HashMap<>();
                    while (rs.next()) {
                        if (cCount == 0) {
                            ResultSetMetaData rsmd = rs.getMetaData();
                            cCount = rsmd.getColumnCount();
                            for (int i = 1; i <= cCount; i++) {
                                cName.put(i, rsmd.getColumnLabel(i));
                            }
                        }
                        List<String> l = new ArrayList<>();
                        for (int i = 1; i <= cCount; i++) {
                            l.add(cName.get(i) + " = " + rs.getString(i));
                        }
                        System.out.println(l.stream().collect(Collectors.joining(", ")));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void traverseRS(String sql){
        System.out.println("traverseRS(" + sql + "):");
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                try(ResultSet rs = st.executeQuery(sql)){
                    traverseRS(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void traverseRS(ResultSet rs) throws Exception{
        int cCount = 0;
        Map<Integer, String> cName = new HashMap<>();
        while (rs.next()) {
            if (cCount == 0) {
                ResultSetMetaData rsmd = rs.getMetaData();
                cCount = rsmd.getColumnCount();
                for (int i = 1; i <= cCount; i++) {
                    cName.put(i, rsmd.getColumnLabel(i));
                }
            }
            List<String> l = new ArrayList<>();
            for (int i = 1; i <= cCount; i++) {
                l.add(cName.get(i) + " = " + rs.getString(i));
            }
            System.out.println(l.stream().collect(Collectors.joining(", ")));
        }
    }


    private static void demo5_select_update_delete_select(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        traverseRS("Select * from enums");
        executeUpdate("insert into enums (id, type, value) values(1,'vehicle','car')");
        traverseRS("Select * from enums");
        executeUpdate("update enums set value = 'bus' where value = 'car'");
        traverseRS("Select * from enums");
        executeUpdate("delete from enums where value = 'bus'");
        traverseRS("Select * from enums");
    }

    private static void demo6_prepared(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        try (Connection conn = getDbConnection()) {
            try (PreparedStatement st = conn.prepareStatement("Select id, type, value from enums")) {
                boolean res = st.execute();
                if (res) {
                    ResultSet rs = st.getResultSet();
                    while (rs.next()) {
                        int id = rs.getInt(1); //More efficient than rs.getInt("id")
                        String type = rs.getString(2);
                        String value = rs.getString(3);
                        System.out.println("id = " + id + ", type = " + type + ", value = " + value);
                    }
                } else {
                    int count = st.getUpdateCount();
                    System.out.println("Update count = " + count);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void demo7_prepared_insert(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        traverseRS("select * from enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            String[][] values = {{"1", "vehicle", "car"},{"2", "vehicle", "truck"}};
            try (PreparedStatement st = conn.prepareStatement("insert into enums (id, type, value) values(?, ?, ?)")) {
                for(String[] v: values){
                    st.setInt(1, Integer.parseInt(v[0]));
                    st.setString(2, v[1]);
                    st.setString(3, v[2]);
                    int count = st.executeUpdate();
                    System.out.println("Update count = " + count);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println();
        traverseRS("select * from enums");
    }

    private static void demo8_transaction(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        traverseRS("select * from enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String sql = "insert into enums (id, type, value) values(1,'vehicle','car')";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                System.out.println(sql);
                System.out.println("Update count = " + st.executeUpdate());
            }
            conn.commit();
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from enums");
    }

    private static void demo9_transaction(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        traverseRS("select * from enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String sql = "insert into enums (id, type, value) values(1,'vehicle','car')";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                System.out.println(sql);
                System.out.println("Update count = " + st.executeUpdate());
            }
            conn.commit();
            sql = "inseeeeeert into enums (id, type, value) values(2,'vehicle','truck')";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                System.out.println(sql);
                System.out.println("Update count = " + st.executeUpdate());
            }
            conn.commit();
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        System.out.println("");
        traverseRS("select * from enums");
    }

    private static void demo10_transaction(){
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        traverseRS("select * from enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String[][] values = {{"1", "vehicle", "car"},{"b", "vehicle", "truck"},{"3", "vehicle", "crewcab"}};
            String sql = "insert into enums (id, type, value) values(?, ?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (String[] v: values){
                    try{
                        System.out.print("id=" + v[0] + ": ");
                        st.setInt(1, Integer.parseInt(v[0]));
                        st.setString(2, v[1]);
                        st.setString(3, v[2]);
                        int count = st.executeUpdate();
                        conn.commit();
                        System.out.println("Update count = " + count);
                    } catch(Exception ex){
                        //conn.rollback();
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from enums");
    }

    private static void demo11_transaction(){
        executeUpdate("delete from test"); //to make the demo re-runnable
        executeUpdate("delete from enums where id < 7"); //to make the demo re-runnable

        System.out.println();
        traverseRS("select * from enums");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String[][] values = {{"1", "vehicle", "car"},{"b", "vehicle", "truck"},{"3", "vehicle", "crewcab"}};
            String sql = "insert into enums (id, type, value) values(?, ?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                for (String[] v: values){
                    try(Statement stm = conn.createStatement()) {
                        System.out.print("id=" + v[0] + ": ");
                        stm.execute("insert into test values('" + v[2] + "')");
                        st.setInt(1, Integer.parseInt(v[0]));
                        st.setString(2, v[1]);
                        st.setString(3, v[2]);
                        int count = st.executeUpdate();
                        conn.commit();
                        System.out.println("Update count = " + count);
                    } catch(Exception ex){
                        conn.rollback();
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from enums");

        System.out.println();
        traverseRS("select * from test");
    }

    private static void execute(String sql){
        try (Connection conn = getDbConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.execute();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeStatement(String sql){
        try (Connection conn = getDbConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static Connection getDbConnection(){
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("cookbook");
        source.setInitialConnections(3);
        source.setMaxConnections(10);
        source.setLoginTimeout(10);
        try {
            return source.getConnection();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Connection getDbConnection2(){
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("cookbook");
        source.setLoginTimeout(10);
        try {
            return source.getConnection();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Connection getDbConnection1(){
        String url = "jdbc:postgresql://localhost/cookbook";
        Properties prop = new Properties( );
        //expensiveInitClass.put( "user", "cook" );
        //expensiveInitClass.put( "password", "secretPass123" );

        try {
            return DriverManager.getConnection(url, prop);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
