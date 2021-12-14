package database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Shipment implements DataObject{

    private String lot;
    private Timestamp shipping_date;
    private Company company;
    private int amount;
    private int price;
    private int sales;
    private int costs;

    public Shipment(String lot, Timestamp shipping_date, Company company, int amount, int price, int sales, int costs) {
        this.lot = lot;
        this.shipping_date = shipping_date;
        this.company = company;
        this.amount = amount;
        this.price = price;
        this.sales = sales;
        this.costs = costs;
    }

    private Shipment() {}

    public static String createSelectSQL(String s) {
        String sql = "select s.lot,c.location,se.evaluation_id,se.shipment_id,c.id,s.shipping_date,s.amount,s.sales,s.costs,s.price,c.name,c.capital,c.num_employees from shipment_evaluation as se left join shipment as s on se.shipment_id = s.id left join company as c on s.company = c.id where " + s;
        System.out.println("sql : " + sql);
        return  sql;
    }

    @Override
    public DataObjectType getType() {
        return DataObjectType.Shipment;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Timestamp getShipping_date() {
        return shipping_date;
    }

    public void setShipping_date(Timestamp shipping_date) {
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
        Company company;
//        System.out.println("createdata");
        try{
            while (resultSet.next()){
                shipment  = new Shipment();
                company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setCapital(resultSet.getInt("capital"));
                company.setEmployees(resultSet.getInt("num_employees"));
                company.setName(resultSet.getString("name"));
                company.setLocation(resultSet.getString("location"));
                shipment.setLot(resultSet.getString("lot"));
                shipment.setShipping_date(resultSet.getTimestamp("shipping_date"));
                shipment.setCompany(company);
                shipment.setAmount(resultSet.getInt("amount"));
                shipment.setPrice(resultSet.getInt("price"));
                shipment.setCosts(resultSet.getInt("costs"));
                shipment.setSales(resultSet.getInt("sales"));
                arrayList.add(shipment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }
}
