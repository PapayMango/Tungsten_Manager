package stock;

import com.sun.javafx.scene.control.LabeledText;
import database.ConnectionDB;
import database.DataObject;
import database.DataType;
import database.Tungsten;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private ObservableList<Tungsten> observableList;

    @FXML
    private TableView result_tb;

    @FXML
    private TableColumn<Tungsten,String> column_lot;

    @FXML
    private TableColumn<Tungsten, Integer> column_stock;

    @FXML
    private TableColumn<Tungsten,Integer> column_shipment;

    @FXML
    private TableColumn<Tungsten, Date> column_date;

    @FXML
    private TableColumn<Tungsten,String> column_quality;

    @FXML
    private TableColumn<Tungsten,String> column_method;

    @FXML
    private TableColumn<Tungsten,String> column_deodorize;

    @FXML
    private TableColumn<Tungsten,String> column_methylene;

    @FXML
    private TableColumn<Tungsten,String> column_cockroach;

    @FXML
    private TableColumn<Tungsten,Float> column_ph;

    private ArrayList<Label> selectedMethods = new ArrayList<Label>();

    private HashMap<String,String> constraints = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");

        ArrayList<? extends DataObject>arrayList = new ArrayList<>();

        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataType.Tungsten, Tungsten.createSelectSQL());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Tungsten>)arrayList);

        column_lot.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("lot"));
        column_stock.setCellValueFactory(new PropertyValueFactory<Tungsten,Integer>("quantity"));
        column_shipment.setCellValueFactory(new PropertyValueFactory<Tungsten,Integer>("shipment"));
        column_date.setCellValueFactory(new PropertyValueFactory<Tungsten,Date>("update_date"));
        column_deodorize.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("deodorize"));
        column_methylene.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("methylene"));
        column_cockroach.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("cockroach"));
        column_method.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("method"));
        column_ph.setCellValueFactory(new PropertyValueFactory<Tungsten,Float>("ph"));

        result_tb.getColumns().clear();
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_lot,column_stock,column_shipment,column_date,column_ph,column_quality,column_method);
    }

    public boolean refreshTable(HashMap<String,String> constraintsMap){
        System.out.println("hash map : " + constraintsMap);
        System.out.println(constraintsMap.entrySet());
        System.out.println("method : " + constraintsMap.get("method"));
        ArrayList<? extends DataObject>arrayList = new ArrayList<>();
        ArrayList<String> constraintList = new ArrayList<>(constraintsMap.values());
//        System.out.println((String[]) constraintList.toArray(constraintList.toArray(new String[arrayList.size()])));
//        System.out.println(constraintsMap.values());
//        System.out.println(constraintsMap.values().toArray()[0].getClass());
//        System.out.println(constraintsMap.values().toArray().getClass());
//        System.out.println((String[])constraintsMap.values().toArray());
        String[] args = constraintList.toArray(constraintList.toArray(new String[arrayList.size()]));
//        String[] args = (String[])constraintsMap.values().toArray();

        System.out.println(args.length);
        System.out.println(args);
        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataType.Tungsten, Tungsten.createSelectSQL(args));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Tungsten>)arrayList);

        result_tb.getColumns().clear();
        result_tb.getItems().clear();
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_lot,column_stock,column_shipment,column_date,column_ph,column_quality,column_method);

        return true;
    }

    @FXML
    private void changeMethod(MouseEvent mouseEvent){
        Label label = (Label)mouseEvent.getSource();
        String constraint = "method = '";

        if(label.getStyleClass().size()==1){
            label.getStyleClass().add("isSelected");
            selectedMethods.add(label);
        }else {
            label.getStyleClass().remove(1);
            selectedMethods.remove(label);
        }
        if(selectedMethods.size()==0){
            if(constraints.containsKey("method"))
                constraints.remove("method");
            refreshTable(constraints);
        }else {
            for(int i = 0; i < selectedMethods.size(); i++) {
                if (i == 0) {
                    constraint += selectedMethods.get(i).getText() + "'";
                    continue;
                }
                constraint += " or method = '" + selectedMethods.get(i).getText() + "'";
            }
            if(constraints.containsKey("method")){
                constraints.replace("method",constraint);
            }else {
                constraints.put("method",constraint);
            }
            refreshTable(constraints);
        }
    }

    @FXML
    private void changeLot(KeyEvent keyEvent){
        TextField textField = (TextField)keyEvent.getSource();
        String constraint = "lot like '%" + textField.getText() + "%'";

        if(constraints.containsKey("lot")){
            constraints.replace("lot",constraint);
        }else {
            constraints.put("lot",constraint);
        }
        refreshTable(constraints);
    }
}
