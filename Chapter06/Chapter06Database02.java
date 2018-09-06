package com.packt.cookbook.ch06_db;

import org.postgresql.ds.PGPoolingDataSource;
import org.postgresql.ds.PGSimpleDataSource;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Chapter06Database02 {
    public static void main(String... args) {
/*
        demo12_blob_bytea();
        demo13_blob_bytea_stream();
        demo13_blob_bytea_bytes();
        demo14_blob_oid();
        demo15_clob_oid();
        demo15_clob_text();
        demo16_stored();
*/
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

    private static void demo12_blob_bytea(){
        execute("drop table if exists images"); //to make the demo re-runnable
        execute("create table images (id integer, image bytea)");

        System.out.println();
        traverseRS("select * from images");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            String sql = "insert into images (id, image) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("src/main/java/com/packt/cookbook/ch06_db/image1.png");
                FileInputStream fis = new FileInputStream(file);
                Blob blob = conn.createBlob();   //Not implemented
                OutputStream out = blob.setBinaryStream(1);
                int i = -1;
                while ((i = fis.read()) != -1) {
                    out.write(i);
                }
                st.setBlob(2, blob);

                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
                conn.commit();
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from images");
    }

    private static void demo13_blob_bytea_stream(){
        execute("drop table if exists images"); //to make the demo re-runnable
        execute("create table images (id integer, image bytea)");

        System.out.println();
        traverseRS("select * from images");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            String sql = "insert into images (id, image) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("src/main/java/com/packt/cookbook/ch06_db/image1.png");
                FileInputStream fis = new FileInputStream(file);
                st.setBinaryStream(2, fis);
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select image from images where id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        try(InputStream is = rs.getBinaryStream(1)) {
                            int i;
                            System.out.print("ints = ");
                            while ((i = is.read()) != -1) {
                                System.out.print(i);
                            }
                        }
                    }
                }
            }
            System.out.println();
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()){
                        //Blob blob = rs.getBlob(1); //Does not work
                        byte[] bytes = rs.getBytes(1);
                        System.out.println("bytes = " + bytes);
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from images");
    }

    private static void demo13_blob_bytea_bytes(){
        execute("drop table if exists images"); //to make the demo re-runnable
        execute("create table images (id integer, image bytea)");

        System.out.println();
        traverseRS("select * from images");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            String sql = "insert into images (id, image) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("src/main/java/com/packt/cookbook/ch06_db/image1.png");
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = fis.readAllBytes();
                System.out.println("bytes = " + bytes);
                st.setBytes(2, bytes);
                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select image from images where id = ?";
            System.out.println();
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()){
                        byte[] bytes = rs.getBytes(1);
                        System.out.println("bytes = " + bytes);
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from images");
    }

    private static void demo14_blob_oid(){
        execute("create table lobs (id integer, lob oid)");

        System.out.println();
        traverseRS("select * from lobs");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobm = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            long lob = lobm.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
            LargeObject obj = lobm.open(lob, LargeObjectManager.WRITE);
            File file = new File("src/main/java/com/packt/cookbook/ch06_db/image1.png");
            try (FileInputStream fis = new FileInputStream(file)){
                int size = 2048;
                byte[] bytes = new byte[size];
                int len = 0;
                while ((len = fis.read(bytes, 0, size)) > 0) {
                    obj.write(bytes, 0, len);
                }
                obj.close();

                String sql = "insert into lobs (id, lob) values(?, ?)";
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setInt(1, 100);
                    st.setLong(2, lob);
                    st.executeUpdate();
                }
            }
            conn.commit();

            String sql = "select lob from lobs where id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()){

                        Blob blob = rs.getBlob(1);
                        byte[] bytes = blob.getBytes(1, (int)blob.length());
//                        System.out.println("bytes = " + bytes);
/*
                        lob = rs.getLong(1);
                        obj = lobm.open(lob, LargeObjectManager.READ);
                        byte[] bytes = new byte[obj.size()];
                        obj.read(bytes, 0, obj.size());
                        System.out.println("bytes = " + bytes);
                        obj.close();
*/
                    }
                }
            }

            conn.commit();
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from lobs");
        System.out.println();
        execute("select lo_unlink((select lob from lobs))");
        execute("drop table if exists lobs"); //to make the demo re-runnable
    }

    private static void demo15_clob_oid(){
        execute("create table lobs (id integer, lob oid)");

        System.out.println();
        traverseRS("select * from lobs");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobm = conn.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
            long lob = lobm.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
            LargeObject obj = lobm.open(lob, LargeObjectManager.WRITE);
            File file = new File("src/main/java/com/packt/cookbook/ch06_db/Chapter06Database02.java");
            try (FileInputStream fis = new FileInputStream(file)){
                int size = 2048;
                byte[] bytes = new byte[size];
                int len = 0;
                while ((len = fis.read(bytes, 0, size)) > 0) {
                    obj.write(bytes, 0, len);
                }
                obj.close();

                String sql = "insert into lobs (id, lob) values(?, ?)";
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setInt(1, 100);
                    st.setLong(2, lob);
                    st.executeUpdate();
                }
            }
            conn.commit();

            String sql = "select lob from lobs where id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()){
                        Blob blob = rs.getBlob(1);
                        byte[] bytes = blob.getBytes(1, (int)blob.length());
                        String str = new String(bytes, Charset.forName("UTF-8"));
                        System.out.println("bytes = " + str);
/*
                        lob = rs.getLong(1);
                        obj = lobm.open(lob, LargeObjectManager.READ);
                        byte[] bytes = new byte[obj.size()];
                        obj.read(bytes, 0, obj.size());
                        String str = new String(bytes, Charset.forName("UTF-8"));
                        System.out.println("bytes = " + str);
                        obj.close();
*/
                    }
                }
            }

            conn.commit();
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        traverseRS("select * from lobs");
        System.out.println();
        execute("select lo_unlink((select lob from lobs))");
        execute("drop table if exists lobs"); //to make the demo re-runnable
    }

    private static void demo15_clob_text(){
        execute("drop table if exists texts"); //to make the demo re-runnable
        execute("create table texts (id integer, text text)");

        System.out.println();
        traverseRS("select * from texts");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            String sql = "insert into texts (id, text) values(?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                File file = new File("src/main/java/com/packt/cookbook/ch06_db/Chapter06Database02.java");


                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] bytes = fis.readAllBytes();

                    //st.setBytes(2, bytes);
                    st.setString(2, new String(bytes, Charset.forName("UTF-8")));
                }

/*
                try(Reader reader = new FileReader(file)){
                    //st.setClob(2, reader);  //Not implemented
                    //st.setNCharacterStream(2, reader); //Not implemented
                    //st.setCharacterStream(2, reader, file.length()); //Not implemented
                    st.setCharacterStream(2, reader, (int)file.length());
                }
*/

                int count = st.executeUpdate();
                System.out.println("Update count = " + count);
            }
            sql = "select text from texts where id = ?";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setInt(1, 100);
                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        String str = rs.getString(1);
                        System.out.println(str);

/*
                        try(Reader reader = rs.getCharacterStream(1)) {
                            char[] chars = new char[160];
                            reader.read(chars);
                            System.out.println(chars);
                        }
*/
                    }
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        System.out.println();
        executeUpdate("delete from texts"); //to make the demo re-runnable
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


    private static void demo16_stored(){
        execute("drop function if exists selectText(int)");
        execute("drop table if exists texts");
        System.out.println();
        try (Connection conn = getDbConnection()) {
            //replace(string text, from text, to text)
            String sql = "{ ? = call replace(?, ?, ? ) }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.registerOutParameter(1, Types.VARCHAR);
                st.setString(2, "Hello, World! Hello!");
                st.setString(3, "llo");
                st.setString(4, "y");
                st.execute();
                String res = st.getString(1);
                System.out.println(res);
            }
            System.out.println();

            execute("create or replace function createTableTexts() returns void as $$ " +
                            "drop table if exists texts; " +
                            "create table texts (id integer, text text); " +
                        "$$ language sql");
            sql = "{ call createTableTexts() }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.execute();
            }
            traverseRS("select createTableTexts()");
            traverseRS("select * from createTableTexts()");
            execute("drop function if exists createTableTexts()");
            System.out.println();

            execute("create or replace function insertText(int, varchar) returns void as $$ " +
                    "insert into texts (id, text) values($1, replace($2, 'XX', 'ext')); " +
                    "$$ language sql");
            sql = "{ call insertText(?, ?) }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.setInt(1, 1);
                st.setString(2, "TXX 1");
                st.execute();
            }
            execute("select insertText(2, 'TXX 2')");
            traverseRS("select * from texts");
            execute("drop function if exists insertText()");
            System.out.println();

            execute("insert into texts (id, text) values(3,'Text 3'),(4,'Text 4')");
            traverseRS("select * from texts");
            execute("create or replace function countTexts() returns bigint as $$ " +
                            "select count(*) from texts; " +
                        "$$ language sql");
            sql = "{ ? = call countTexts() }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.registerOutParameter(1, Types.BIGINT);
                st.execute();
                System.out.println("Result of countTexts() = " + st.getLong(1));
            }
            traverseRS("select countTexts()");
            traverseRS("select * from countTexts()");
            execute("drop function if exists countTexts()");
            System.out.println();

            execute("create or replace function selectText(int) returns setof texts as $$ " +
                    "       select * from texts where id=$1;" +
                    "     $$ language sql");
/*
            sql = "{ ? = call selectText(?) }";
            try (CallableStatement st = conn.prepareCall(sql)) {
                st.registerOutParameter(1, Types.OTHER);
                st.setInt(2, 1);
                st.execute();
                System.out.println("traverseRS(st.getGeneratedKeys()):");
                traverseRS((ResultSet)st.getObject(1));
            }
*/
            traverseRS("select selectText(1)");
            traverseRS("select * from selectText(1)");
            execute("drop function if exists selectText(int)");
            System.out.println();

            execute("create or replace function selectText(int) returns refcursor as " +
                         "$$ declare curs refcursor; " +
                            "begin " +
                               "open curs for select * from texts where id=$1; " +
                               "return curs; " +
                            "end; " +
                         "$$ language plpgsql");
            conn.setAutoCommit(false);
            sql = "{ ? = call selectText(?) }";
            try(CallableStatement st = conn.prepareCall(sql)){
                st.registerOutParameter(1, Types.OTHER);
                st.setInt(2, 2);
                st.execute();
                try(ResultSet rs = (ResultSet) st.getObject(1)){
                    System.out.println("traverseRS(refcursor()=>rs):");
                    traverseRS(rs);
                }
            }
            traverseRS("select selectText(2)");
            traverseRS("select * from selectText(2)");
            execute("drop function if exists selectText(int)");
            execute("drop table if exists texts");
        } catch (Exception ex) { ex.printStackTrace(); }
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
