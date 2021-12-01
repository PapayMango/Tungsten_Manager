package database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Tungsten implements DataObject{
    
    private String lot;
    private Timestamp update_date;
    private Timestamp product_date;
    private int quantity;
    private String deodorize;
    private String methylene;
    private String cockroach;
    private String method;
    private int shipment;
    private float ph;
    private float concentration;
    private String location;

    @Deprecated
    private boolean isInitiated = false;

    @Deprecated
    private ArrayList<Shipment> shipments = new ArrayList<Shipment>();

    public Tungsten(String lot, Timestamp update_date, Timestamp product_date, int quantity, String deodorize, String method, float ph, String methylene, String cockroach, int shipment, float concentration, String location) {
        this.lot = lot;
        this.update_date = update_date;
        this.product_date = product_date;
        this.quantity = quantity;
        this.deodorize = deodorize;
        this.method = method;
        this.ph = ph;
        this.methylene = methylene;
        this.cockroach = cockroach;
        this.shipment = shipment;
        this.concentration = concentration;
        this.location = location;
    }

    private Tungsten() {}

    public void setShipment(int shipment) {
        this.shipment = shipment;
    }

    public int getShipment() {
        return shipment;
    }

    public String getMethylene() {
        return methylene;
    }

    public void setMethylene(String methylene) {
        this.methylene = methylene;
    }

    public String getCockroach() {
        return cockroach;
    }

    public void setCockroach(String cockroach) {
        this.cockroach = cockroach;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Timestamp getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Timestamp update_date) {
        this.update_date = update_date;
    }

    public Timestamp getProduct_date() {
        return product_date;
    }

    public void setProduct_date(Timestamp product_date) {
        this.product_date = product_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDeodorize() {
        return deodorize;
    }

    public void setDeodorize(String deodorize) {
        this.deodorize = deodorize;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public float getConcentration() {
        return concentration;
    }

    public void setConcentration(float concentration) {
        this.concentration = concentration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public DataObjectType getType() {
        return DataObjectType.Tungsten;
    }

    @Deprecated
    public ArrayList<Shipment> getShipments(){

        setShipments();

        return new ArrayList<Shipment>(shipments);
    }

    @Deprecated
    public boolean setShipments(){

        try {
            String sql = "select * from shipment where lot = '" + getLot() + "'";
            shipments =(ArrayList<Shipment>) ConnectionDB.connectionDB.connectDB().select(DataObjectType.Shipment,sql);
            for(Shipment ship: shipments){
                shipment += ship.getAmount();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        isInitiated = true;
        return true;
    }

    public static ArrayList<? extends DataObject> createData(ResultSet resultSet){
        ArrayList<Tungsten> arrayList = new ArrayList<Tungsten>();
        Tungsten tungsten;
//        System.out.println("create_data");
        try{
            while (resultSet.next()){
                tungsten  = new Tungsten();
                tungsten.setLot(resultSet.getString("lot"));
                tungsten.setUpdate_date(resultSet.getTimestamp("update_date"));
                tungsten.setProduct_date(resultSet.getTimestamp("product_date"));
                tungsten.setQuantity(resultSet.getInt("quantity"));
                tungsten.setDeodorize(resultSet.getString("deodorize"));
                tungsten.setMethod(resultSet.getString("method"));
                tungsten.setPh(resultSet.getFloat("ph"));
                tungsten.setMethylene(resultSet.getString("methylene"));
                tungsten.setCockroach(resultSet.getString("cockroach"));
                tungsten.setShipment(resultSet.getInt("total_shipment"));
                tungsten.setConcentration(resultSet.getFloat("initial_concentration"));
                tungsten.setLocation(resultSet.getString("location"));
                arrayList.add(tungsten);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String createInsertSQL(Tungsten tungsten){
        return "insert into tungsten(lot,update_date,product_date,quantity,deodorize,method,ph,methylene,cockroach) values('" + tungsten.getLot() +"'," + tungsten.getUpdate_date() + "," + tungsten.getProduct_date() + "," + tungsten.getQuantity() + ",'" + tungsten.getDeodorize() + "','" + tungsten.getMethod() + "' " + tungsten.getPh() + "," + tungsten.getMethylene() +"'," + tungsten.getCockroach() + "')";
    }

    public static String createSelectSQL(String... args){
        String sql = "select * from tungsten";
        if(args.length == 0){
            return sql;
        }else {

            sql += " where ";
            for(int i = 0; i < args.length; i++){
                if(i == 0){
                    sql += "(" + args[i] + ")";
                    continue;
                }
                sql += " and (" + args[i] + ")";
            }
        }
        System.out.println("sql : " + sql);
        return  sql;
    }
}
