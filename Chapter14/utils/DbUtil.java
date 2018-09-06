package com.packt.cookbook.ch14_testing.utils;

import com.packt.cookbook.ch14_testing.api.Vehicle;
import com.packt.cookbook.ch14_testing.api.TrafficUnit;
import com.packt.cookbook.ch14_testing.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch14_testing.api.SpeedModel.TireCondition;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DbUtil {

    public static String selectResult(String process){
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement("select result from result where process = ?")) {
            st.setString(1, process);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void storeResult(String process, int trafficUnitsNumber, double timeSec, String dateLocation, double[] speedLimitByLane, String result){
        String limits = Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", "));
        String sql = "insert into result(process, traffic_units_number, time_sec, date_location, speed_limit_by_lane, result) values(?,?,?,?,?,?)";
        //System.out.println("  " + sql + ", params=" + process + ", " + trafficUnitsNumber + ", " + timeSec + ", " + dateLocation + ", " + result );
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            int i =1;
            st.setString(i++, process);
            st.setInt(i++, trafficUnitsNumber);
            st.setDouble(i++, timeSec);
            st.setString(i++, dateLocation);
            st.setString(i++, limits);
            st.setString(i++, result);
            int count = st.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void createResultTable(){
        execute("drop table if exists result");
        execute("create table result(\n" +
                "  id serial not null,\n" +
                "  process character varying not null,\n" +
                "  traffic_units_number integer not null,\n" +
                "  time_sec numeric not null,\n" +
                "  date_location character varying not null,\n" +
                "  speed_limit_by_lane character varying not null,\n" +
                "  result character varying not null\n" +
                ");\n");
    }

    public static void createDataTables(){
        execute("drop table if exists data_common");
        execute("create table data_common(\n" +
                "  traffic_units_number integer not null,\n" +
                "  time_sec numeric not null,\n" +
                "  date_location character varying not null,\n" +
                "  speed_limit_by_lane character varying not null\n" +
                ");\n");
        execute("drop table if exists data");
        execute("create table data(\n" +
                "  id serial not null,\n" +
                "  vehicle_type character varying not null,\n" +
                "  horse_power integer not null,\n" +
                "  weight_pounds integer not null,\n" +
                "  passengers_count integer not null,\n" +
                "  payload_pounds integer not null,\n" +
                "  speed_limit_mph numeric not null,\n" +
                "  temperature integer not null,\n" +
                "  road_condition character varying not null,\n" +
                "  tire_condition character varying not null,\n" +
                "  traction numeric not null,\n" +
                "  speed numeric not null\n" +
                ");\n");
    }

    public static List<TrafficUnit> selectData(int trafficUnitsNumber){
        List<TrafficUnit> result = new ArrayList<>();
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement("select vehicle_type, horse_power, weight_pounds, passengers_count, payload_pounds, speed_limit_mph, " +
                     "temperature, road_condition, tire_condition, traction, speed from data order by id limit " + trafficUnitsNumber)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                result.add(new TrafficUnitImpl(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static void recordDataCommon(int trafficUnitsNumber, double timeSec, String dateLocation, double[] speedLimitByLane){
        String limits = Arrays.stream(speedLimitByLane).mapToObj(Double::toString).collect(Collectors.joining(", "));

        String sql = "insert into data_common(traffic_units_number, time_sec, date_location, speed_limit_by_lane) values(?,?,?,?)";
        //System.out.println("  " + sql + ", params=" + process + ", " + trafficUnitsNumber + ", " + timeSec + ", " + dateLocation + ", " + result );
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            int i = 1;
            st.setInt(i++, trafficUnitsNumber);
            st.setDouble(i++, timeSec);
            st.setString(i++, dateLocation);
            st.setString(i++, limits);
            int count = st.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void recordData(Connection conn, TrafficUnit tu, double speed) {
        String sql = "insert into data(vehicle_type, horse_power, weight_pounds, passengers_count, payload_pounds, speed_limit_mph, " +
                "temperature, road_condition, tire_condition, traction, speed) values(?,?,?,?,?,?,?,?,?,?,?)";
/*
        System.out.println("  " + sql + ", params=" + tu.getVehicleType() + ", " + tu.getHorsePower() + ", " + tu.getWeightPounds()
                + ", " + tu.getPassengersCount() + ", " + tu.getPayloadPounds() + ", " + tu.getSpeedLimitMph() + ", " + tu.getTemperature()
                + ", " + tu.getRoadCondition() + ", " + tu.getTireCondition()+ ", " + tu.getTraction() + ", " + speed);
*/
        try {
            int i = 1;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(i++, tu.getVehicleType().name());
            st.setInt(i++, tu.getHorsePower());
            st.setInt(i++, tu.getWeightPounds());
            st.setInt(i++, tu.getPassengersCount());
            st.setInt(i++, tu.getPayloadPounds());
            st.setDouble(i++, tu.getSpeedLimitMph());
            st.setInt(i++, tu.getTemperature());
            st.setString(i++, tu.getRoadCondition().name());
            st.setString(i++, tu.getTireCondition().name());
            st.setDouble(i++, tu.getTraction());
            st.setDouble(i++, speed);
            int count = st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isEnoughData(int trafficUnitsNumber){
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement("select count(*) from data")) {
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return count >= trafficUnitsNumber;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static double getTimeSecFromDataCommon(){
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement("select time_sec from data_common")) {
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                return rs.getDouble(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    public static void execute(String sql){
        //System.out.println("  " + sql);
        try (Connection conn = getDbConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getDbConnection(){
        PGPoolingDataSource source = new PGPoolingDataSource();
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

    private static class TrafficUnitImpl implements TrafficUnit{
        private int horsePower, weightPounds, payloadPounds, passengersCount, temperature;
        private Vehicle.VehicleType vehicleType;
        private double speedLimitMph, traction, speed;
        private RoadCondition roadCondition;
        private TireCondition tireCondition;

        public TrafficUnitImpl(ResultSet rs) throws SQLException{
            //vehicle_type, horse_power, weight_pounds, passengers_count, payload_pounds,
            // speed_limit_mph, temperature, road_condition, tire_condition, traction
            int i = 1;
            this.vehicleType = Vehicle.VehicleType.valueOf(rs.getString(i++));
            this.horsePower = rs.getInt(i++);
            this.weightPounds = rs.getInt(i++);
            this.passengersCount = rs.getInt(i++);
            this.payloadPounds = rs.getInt(i++);
            this.speedLimitMph = rs.getDouble(i++);
            this.temperature = rs.getInt(i++);
            this.roadCondition = RoadCondition.valueOf(rs.getString(i++));
            this.tireCondition = TireCondition.valueOf(rs.getString(i++));
            this.traction = rs.getDouble(i++);
            this.speed = rs.getDouble(i++);
        }
        public int getHorsePower() {
            return horsePower;
        }
        public int getWeightPounds() {
            return weightPounds;
        }
        public int getPayloadPounds() {
            return payloadPounds;
        }
        public int getPassengersCount() {
            return passengersCount;
        }
        public int getTemperature() {
            return temperature;
        }
        public Vehicle.VehicleType getVehicleType() {
            return vehicleType;
        }
        public double getSpeedLimitMph() {
            return speedLimitMph;
        }
        public double getTraction() {
            return traction;
        }
        public RoadCondition getRoadCondition() {
            return roadCondition;
        }
        public TireCondition getTireCondition() {
            return tireCondition;
        }
        public double getSpeed() {
            return speed;
        }

    }
}
