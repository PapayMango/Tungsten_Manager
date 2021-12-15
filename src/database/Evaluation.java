package database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Evaluation implements DataObject{

    private int id;
    private String lot;
    private String methylene;
    private String deodorize;
    private String cockroach;
    private Timestamp update_date;
    private float concentration;
    private String additive;
    private String binder;
    private float ph;
    private int shipment;

    public Evaluation(int id, String lot, String methylene, String deodorize, String cockroach, Timestamp update_date, float concentration, String additive, String binder, float ph, int shipment) {
        this.id = id;
        this.lot = lot;
        this.methylene = methylene;
        this.deodorize = deodorize;
        this.cockroach = cockroach;
        this.update_date = update_date;
        this.concentration = concentration;
        this.additive = additive;
        this.binder = binder;
        this.ph = ph;
        this.shipment = shipment;
    }
    
    private Evaluation(){}

    @Override
    public DataObjectType getType() {
        return DataObjectType.Evaluation;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getMethylene() {
        return methylene;
    }

    public void setMethylene(String methylene) {
        this.methylene = methylene;
    }

    public String getDeodorize() {
        return deodorize;
    }

    public void setDeodorize(String deodorize) {
        this.deodorize = deodorize;
    }

    public String getCockroach() {
        return cockroach;
    }

    public void setCockroach(String cockroach) {
        this.cockroach = cockroach;
    }

    public Timestamp getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Timestamp update_date) {
        this.update_date = update_date;
    }

    public float getConcentration() {
        return concentration;
    }

    public void setConcentration(float concentration) {
        this.concentration = concentration;
    }

    public String getAdditive() {
        return additive;
    }

    public void setAdditive(String additive) {
        this.additive = additive;
    }

    public String getBinder() {
        return binder;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public int getShipment() {
        return shipment;
    }

    public void setShipment(int shipment) {
        this.shipment = shipment;
    }

    public static ArrayList<? extends DataObject> createData(ResultSet resultSet){
        ArrayList<Evaluation> arrayList = new ArrayList<Evaluation>();
        Evaluation evaluation;
        Evaluation previous_evaluation = new Evaluation();
        try{
            while (resultSet.next()){
//                System.out.println(previous_evaluation.getId());
//                System.out.println(resultSet.getString("Id"));
                evaluation  = new Evaluation();
                if(previous_evaluation.getId() != resultSet.getInt("id")){
                    evaluation.setId(resultSet.getInt("id"));
                    evaluation.setLot(resultSet.getString("lot"));
                    evaluation.setConcentration(resultSet.getFloat("concentration"));
                    evaluation.setPh(resultSet.getFloat("ph"));
                    evaluation.setAdditive(resultSet.getString("additive"));
                    evaluation.setBinder(resultSet.getString("binder"));
                    evaluation.setDeodorize(resultSet.getString("deodorize"));
                    evaluation.setMethylene(resultSet.getString("methylene"));
                    evaluation.setCockroach(resultSet.getString("cockroach"));
                    evaluation.setShipment(resultSet.getInt("total_shipment"));
                    evaluation.setUpdate_date(resultSet.getTimestamp("update_date"));
                    previous_evaluation = evaluation;
                    arrayList.add(evaluation);
                }else {
//                    previous_evaluation.setAdditive(previous_evaluation.getAdditive() + " " + resultSet.getString("additive"));
//                    arrayList.set(arrayList.size()-1,previous_evaluation);
                    arrayList.get(arrayList.size()-1).setAdditive(previous_evaluation.getAdditive() + " " + resultSet.getString("additive"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String createSelectSQL(String... args){
//        String sql = "select * from evaluation";
        String sql = "select e.lot,e.deodorize,e.methylene,e.cockroach,e.total_shipment,e.concentration,e.ph,e.update_date, b.name as binder ,e.lot,e.id,a.name as additive from evaluation as e left join (select binder_mixing.binder_id,binder_mixing.evaluation_id ,binder.name from binder_mixing inner join binder on binder.id\n" +
                "= binder_mixing.binder_id) as b on e.id = b.evaluation_id left join (select additive.material_id,additive.evaluation_id,material.name from additive inner join material on additive.material_id = material.id) as a on e.id = a.evaluation_id";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
