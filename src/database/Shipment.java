package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Shipment implements DataObject{

    private String lot;
    private Date shipping_date;
    private Company company;
    private int amount;
    private int price;

    public Shipment(String lot, Date shipping_date, Company company, int amount, int price) {
        this.lot = lot;
        this.shipping_date = shipping_date;
        this.company = company;
        this.amount = amount;
        this.price = price;
    }

    private Shipment() {}

    @Override
    public DataType getType() {
        return DataType.Shipment;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Date getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(Date shipping_date) {
        this.shipping_date = shipping_date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static ArrayList<? extends DataObject> createData(ResultSet resultSet){
        ArrayList<Shipment> arrayList = new ArrayList<Shipment>();
        Shipment shipment;
//        System.out.println("createdata");
        try{
            while (resultSet.next()){
                shipment  = new Shipment();
                shipment.setLot(resultSet.getString("lot"));
                shipment.setShipping_date(resultSet.getDate("shipping_date"));
//                shipment.setCompany(resultSet.getInt("quantity"));
                shipment.setCompany(new Company());
                shipment.setAmount(resultSet.getInt("amount"));
                shipment.setPrice(resultSet.getInt("price"));
                arrayList.add(shipment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
}
