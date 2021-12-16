package evaluation;

import database.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController implements Initializable {
    @FXML
    public TextField fromConcentration;
    @FXML
    public TextField toConcentration;
    @FXML
    public TextField fromPh;
    @FXML
    public TextField toPh;
    @FXML
    public ComboBox deodorize;
    @FXML
    public ComboBox methylene;
    @FXML
    public ComboBox cockroach;
    @FXML
    public TextField additive;
    @FXML
    public TextField binder;
    @FXML
    public AnchorPane filter;
    @FXML
    public AnchorPane main;
    private ObservableList<Evaluation> observableList;
    @FXML
    public DatePicker fromDate;
    @FXML
    public DatePicker toDate;
    @FXML
    public TableColumn column_lot;
    @FXML
    public TableColumn column_concentration;
    @FXML
    public TableColumn column_ph;
    @FXML
    public TableColumn column_additive;
    @FXML
    public TableColumn column_binder;
    @FXML
    public TableColumn column_deodorize;
    @FXML
    public TableColumn column_methylene;
    @FXML
    public TableColumn column_cockroach;
    @FXML
    public TableColumn column_date;
    @FXML
    public TableView result_tb;
    @FXML
    private AutoComplete autoComplete;

    private HashMap<String,String> constraints = new HashMap<>();

    private ArrayList<ComboBox<String>> qualities = new ArrayList<>();

    public boolean refreshTable(HashMap<String,String> constraintsMap){
        System.out.println("refreshTable");
        System.out.println("hash map : " + constraintsMap);
        System.out.println(constraintsMap.entrySet());
        System.out.println("method : " + constraintsMap.get("method"));
        ArrayList<? extends DataObject> arrayList = new ArrayList<>();
        ArrayList<String> constraintList = new ArrayList<>(constraintsMap.values());
        String[] args = constraintList.toArray(constraintList.toArray(new String[arrayList.size()]));

        System.out.println(args.length);
        System.out.println(args);
        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL(args));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);

        result_tb.getColumns().clear();
        result_tb.getItems().clear();
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_lot,column_concentration,column_ph,column_additive,column_binder,column_deodorize,column_methylene,column_cockroach,column_date);

        return true;
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
    private void changeConcentration(KeyEvent keyEvent){
        String constraint = "concentration ";

        if (fromConcentration.getText() != ""){
            if(toConcentration.getText() != ""){
                constraint += " between " + fromConcentration.getText() + " and " + toConcentration.getText();
            }else {
                constraint += " >= " + fromConcentration.getText();
            }
        }else if(toConcentration.getText() != ""){
            constraint += " <= " + toConcentration.getText();
        }else {
            constraints.remove("concentration");
            refreshTable(constraints);
            return;
        }
        if(constraints.containsKey("concentration")){
            constraints.replace("concentration",constraint);
        }else {
            constraints.put("concentration",constraint);
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList filter_observableList = ((VBox)filter.getChildren().get(0)).getChildren();
        for (int i = 0; i < filter_observableList.size() ; i++) {
            ((HBox)filter_observableList.get(i)).getStyleClass().add("filter");
        }
        fromConcentration.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.matches("[\\d.]*")){
                fromConcentration.setText(t1.replaceAll("[^\\d.]",""));
            }
            if(t1.matches("[\\d.]*[.][\\d.]*[.]")){
                fromConcentration.setText(t1.substring(0,t1.length()-1));
            }
        });

        toConcentration.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.matches("[\\d.]*")){
                toConcentration.setText(t1.replaceAll("[^\\d.]",""));
            }
            if(t1.matches("[\\d.]*[.][\\d.]*[.]")){
                toConcentration.setText(t1.substring(0,t1.length()-1));
            }
        });

        fromPh.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.matches("[\\d.]*")){
                fromPh.setText(t1.replaceAll("[^\\d.]",""));
            }
            if(t1.matches("[\\d.]*[.][\\d.]*[.]")){
                fromPh.setText(t1.substring(0,t1.length()-1));
            }
        });

        toPh.textProperty().addListener((observableValue, s, t1) -> {
            if(!t1.matches("[\\d.]*")){
                toPh.setText(t1.replaceAll("[^\\d.]",""));
            }
            if(t1.matches("[\\d.]*[.][\\d.]*[.]")){
                toPh.setText(t1.substring(0,t1.length()-1));
            }
        });

        autoComplete.register(new AutoComplete.ColumnTablePair("name","material","additive"),additive);
        autoComplete.register(new AutoComplete.ColumnTablePair("name","binder","binder"),binder);
        autoComplete.init();


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
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);

        column_lot.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("lot"));
        column_concentration.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("concentration"));
        column_ph.setCellValueFactory(new PropertyValueFactory<Evaluation,Integer>("ph"));
        column_additive.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("additive"));
        column_binder.setCellValueFactory(new PropertyValueFactory<Evaluation,Float>("binder"));
        column_deodorize.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("deodorize"));
        column_methylene.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("methylene"));
        column_cockroach.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("cockroach"));
        column_date.setCellValueFactory(new PropertyValueFactory<Evaluation, Date>("update_date"));
        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);
        result_tb.getColumns().clear(); 
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_lot,column_concentration,column_ph,column_additive,column_binder,column_deodorize,column_methylene,column_cockroach,column_date);
    }
    @FXML
    private void changeAdditive(KeyEvent keyEvent){
        String constraint = "a.name ";
        String text = additive.getText();
        if (keyEvent.getCode() != KeyCode.ENTER & keyEvent.getCode() != KeyCode.TAB)
            return;
        if(text != ""){
            constraint += " = '" + text + "'";
            if(constraints.containsKey("additive")){
                constraints.replace("additive",constraint);
            }else {
                constraints.put("additive",constraint);
            }
        }else {
            if(constraints.containsKey("additive"))
                constraints.remove("additive");
        }
        refreshTable(constraints);
    }
    @FXML
    private void changeBinder(KeyEvent keyEvent){
        String constraint = "b.name ";
        String text = binder.getText();
        if (keyEvent.getCode() != KeyCode.ENTER & keyEvent.getCode() != KeyCode.TAB)
            return;
        if(text != ""){
            constraint += " = '" + text + "'";
            if(constraints.containsKey("binder")){
                constraints.replace("binder",constraint);
            }else {
                constraints.put("binder",constraint);
            }
        }else {
            if(constraints.containsKey("binder"))
                constraints.remove("binder");
        }
        refreshTable(constraints);
    }
}
