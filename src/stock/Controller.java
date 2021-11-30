package stock;

import database.ConnectionDB;
import database.DataObject;
import database.DataObjectType;
import database.Tungsten;
import home.SceneTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.*;

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

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private TextField fromStock;

    @FXML
    private TextField toStock;

    @FXML
    private TextField fromShipment;

    @FXML
    private TextField toShipment;

    @FXML
    private ComboBox<String> deodorize;

    @FXML
    private ComboBox<String> methylene;

    @FXML
    private ComboBox<String> cockroach;

    @FXML
    private TextField fromPh;

    @FXML
    private TextField toPh;

//    private Stage evaluation;


    private ArrayList<Label> selectedMethods = new ArrayList<Label>();

    private ArrayList<ComboBox<String>>qualities = new ArrayList<>();

    private HashMap<String,String> constraints = new HashMap<>();

    private Stage currentWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");


        fromStock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                System.out.println(observableValue + " : " + s + " : " + t1);
                if(!t1.matches("\\d*")){
                    fromStock.setText(t1.replaceAll("[^\\d]",""));
                }
            }
        });

        toStock.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")){
                    toStock.setText(t1.replaceAll("[^\\d]",""));
                }
            }
        });
        
        fromShipment.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                System.out.println(observableValue + " : " + s + " : " + t1);
                if(!t1.matches("\\d*")){
                    fromShipment.setText(t1.replaceAll("[^\\d]",""));
                }
            }
        });

        toShipment.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.matches("\\d*")){
                    toShipment.setText(t1.replaceAll("[^\\d]",""));
                }
            }
        });

        String[] qualitiy_str = {"-","A","B","C","D"};

        qualities.add(deodorize);
        qualities.add(methylene);
        qualities.add(cockroach);

        for (ComboBox<String> comboBox:qualities){
            comboBox.getItems().addAll(qualitiy_str);
            comboBox.getSelectionModel().selectFirst();
        }

        ArrayList<? extends DataObject>arrayList = new ArrayList<>();

        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Tungsten, Tungsten.createSelectSQL());
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
        result_tb.setRowFactory((t) ->{
            TableRow tableRow = new TableRow();
            tableRow.addEventFilter(MouseEvent.MOUSE_CLICKED,(event)->{
                if(event.getClickCount() >= 2){
                    TableRow selected = (TableRow)event.getSource();
                    if(result_tb.getSelectionModel().getSelectedIndex() == selected.getIndex()){
                        Stage current = (Stage)result_tb.getScene().getWindow();
                        if(current != currentWindow){
                            currentWindow = current;
                            System.out.println("a");
                        }
                        result_tb.getScene().getWindow().hide();
//                        SceneTransition.sceneTransition.changeScene("../evaluation/evaluation.fxml",result_tb.getScene(),(Tungsten)result_tb.getSelectionModel().getSelectedItem());

                        Stage stage = SceneTransition.sceneTransition.transition("../evaluation/evaluation.fxml","評価試験",(Tungsten)result_tb.getSelectionModel().getSelectedItem(),(Stage) result_tb.getScene().getWindow());
                        stage.show();
                    }
                    System.out.println("tablerow : " + event);
                    System.out.println("button : " + event.getButton());
                    System.out.println("count : " + event.getClickCount());
                    System.out.println("target : " + event.getTarget());
                    System.out.println("source : " + event.getSource());
                    System.out.println(((TableRow)event.getSource()).getIndex());
                    System.out.println("event : " + event.getEventType());
                }
            });
            return tableRow;
        });

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
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Tungsten, Tungsten.createSelectSQL(args));
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

    @FXML
    private void changeDate(ActionEvent actionEvent){
        String constraint = "update_date ";

        if (fromDate.getValue() != null){
            if(toDate.getValue() != null){
                constraint += " between '" + fromDate.getValue() + "' and '" + toDate.getValue() + "'";
            }else {
                constraint += " >= '" + fromDate.getValue() + "'";
            }
        }else if(toDate.getValue() != null){
            constraint += " <= '" + toDate.getValue() + "'";
        }else {
            constraints.remove("Date");
            refreshTable(constraints);
            return;
        }

        if(constraints.containsKey("Date")){
            constraints.replace("Date",constraint);
        }else {
            constraints.put("Date",constraint);
        }

        System.out.println(constraint);

        refreshTable(constraints);

    }

    @FXML
    private void changeStock(KeyEvent keyEvent){
        String constraint = "quantity ";

        if (fromStock.getText() != ""){
            if(toStock.getText() != ""){
                constraint += " between " + fromStock.getText() + " and " + toStock.getText();
            }else {
                constraint += " >= " + fromStock.getText();
            }
        }else if(toStock.getText() != ""){
            constraint += " <= " + toStock.getText();
        }else {
            constraints.remove("stock");
            refreshTable(constraints);
            return;
        }

        if(constraints.containsKey("stock")){
            constraints.replace("stock",constraint);
        }else {
            constraints.put("stock",constraint);
        }

        System.out.println(constraint);

        refreshTable(constraints);
        
    }

    @FXML
    private void changeShipment(KeyEvent keyEvent){
        String constraint = "total_shipment ";

        if (fromShipment.getText() != ""){
            if(toShipment.getText() != ""){
                constraint += " between " + fromShipment.getText() + " and " + toShipment.getText();
            }else {
                constraint += " >= " + fromShipment.getText();
            }
        }else if(toShipment.getText() != ""){
            constraint += " <= " + toShipment.getText();
        }else {
            constraints.remove("shipment");
            refreshTable(constraints);
            return;
        }

        if(constraints.containsKey("shipment")){
            constraints.replace("shipment",constraint);
        }else {
            constraints.put("shipment",constraint);
        }

        System.out.println(constraint);

        refreshTable(constraints);

    }
    
    @FXML
    private void changePh(KeyEvent keyEvent){
        String constraint = "ph ";

        if (fromPh.getText() != ""){
            if(toPh.getText() != ""){
                constraint += " between " + fromPh.getText() + " and " + toPh.getText();
            }else {
                constraint += " >= " + fromPh.getText();
            }
        }else if(toPh.getText() != ""){
            constraint += " <= " + toPh.getText();
        }else {
            constraints.remove("ph");
            refreshTable(constraints);
            return;
        }

        if(constraints.containsKey("ph")){
            constraints.replace("ph",constraint);
        }else {
            constraints.put("ph",constraint);
        }

        System.out.println(constraint);

        refreshTable(constraints);

    }

    @FXML
    private void changeQuality(ActionEvent actionEvent) {

        String constraint = "";
        String qualityType = "";
        for (ComboBox<String> comboBox : qualities) {
            qualityType = comboBox.getId();
            System.out.println(qualityType);
            if (comboBox.getValue() != "-") {
                constraint = qualityType + " = '" + comboBox.getValue() + "'";
                if (constraints.containsKey(qualityType)) {
                    constraints.replace(qualityType, constraint);
                } else {
                    constraints.put(qualityType, constraint);
                }
            } else {
                if (constraints.containsKey(qualityType))
                    constraints.remove(qualityType);
            }
        }

        refreshTable(constraints);
//        if(deodorize.getValue() != "-"){
//            constraint = "deodorize = '" + deodorize.getValue() + "'";
//            if(constraints.containsKey("deodorize")){
//                constraints.replace("deodorize",constraint);
//            }else {
//                constraints.put("deodorize",constraint);
//            }
//        }else {
//            if(constraints.containsKey("deodorize"))
//                constraints.remove("deodorize");
//        }
//        if(methylene.getValue() != "-"){
//            constraint = "methylene = '" + methylene.getValue() + "'";
//            if(constraints.containsKey("methylene")){
//                constraints.replace("methylene",constraint);
//            }else {
//                constraints.put("methylene",constraint);
//            }
//        }else {
//            if(constraints.containsKey("methylene"))
//                constraints.remove("methylene");
//        }
//        if(cockroach.getValue() != "-"){
//            constraint = "cockroach = '" + cockroach.getValue() + "'";
//            if(constraints.containsKey("cockroach")){
//                constraints.replace("cockroach",constraint);
//            }else {
//                constraints.put("cockroach",constraint);
//            }
//        }else {
//            if(constraints.containsKey("cockroach"))
//                constraints.remove("cockroach");
//        }

    }

//    @FXML
//    private void changePage(MouseEvent mouseEvent){
//        System.out.println("changePage : " + mouseEvent);
//        SceneTransition.sceneTransition.changeScene("../evaluation/evaluation.fxml",result_tb.getScene(),(Tungsten)result_tb.getSelectionModel().getSelectedItem());
//    }
}
