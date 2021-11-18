package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Tungsten implements DataObject{
    
    private String lot;
    private Date update_date;
    private Date product_date;
    private int quantity;
    private String quality;
    private String method;
    private int shipment;


    private boolean isInitiated = false;

    private ArrayList<Shipment> shipments = new ArrayList<Shipment>();
    public Tungsten(String lot, Date update_date, Date product_date, int quantity, String quality, String method) {
        this.lot = lot;
        this.update_date = update_date;
        this.product_date = product_date;
        this.quantity = quantity;
        this.quality = quality;
        this.method = method;
    }

    private Tungsten() {}

    public int getShipment() {
//        setShipments();
//        System.out.println("getShipment : " + isInitiated);
//        System.out.println("lot no : " + this.getLot());
        if(!isInitiated){
            setShipments();
            System.out.println("setShipment : " + shipment);
            return shipment;
        }else {
//            System.out.println("getShipment : "+ shipment);
            return shipment;
        }
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public Date getProduct_date() {
        return product_date;
    }

    public void setProduct_date(Date product_date) {
        this.product_date = product_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public DataType getType() {
        return DataType.Tungsten;
    }

    public ArrayList<Shipment> getShipments(){

        setShipments();

        return new ArrayList<Shipment>(shipments);
    }

    public boolean setShipments(){

        try {
            String sql = "select * from shipment where lot = '" + getLot() + "'";
            shipments =(ArrayList<Shipment>) ConnectionDB.connectionDB.connectDB().select(DataType.Shipment,sql);
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
        System.out.println("createdata");
        try{
            while (resultSet.next()){
                tungsten  = new Tungsten();
                tungsten.setLot(resultSet.getString("lot"));
                tungsten.setUpdate_date(resultSet.getDate("update_date"));
                tungsten.setProduct_date(resultSet.getDate("product_date"));
                tungsten.setQuantity(resultSet.getInt("quantity"));
                tungsten.setQuality(resultSet.getString("quality"));
                tungsten.setMethod(resultSet.getString("method"));
                arrayList.add(tungsten);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String createInsertSQL(Tungsten tungsten){
        return "insert into tungsten(lot,update_date,product_date,quantity,quality,method) values('" + tungsten.getLot() +"'," + tungsten.getUpdate_date() + "," + tungsten.getProduct_date() + "," + tungsten.getQuantity() + ",'" + tungsten.getQuality() + "','" + tungsten.getMethod() + "')";
    }

    public static String createSelectSQL(String... args){
        String sql = "select * from tungsten";
        if(args.length == 0){
            return sql;
        }else{
            sql += " where ";
            for(String str:args){
                sql += str;
            }
        }
        return  sql;
    }
}
