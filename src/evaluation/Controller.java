package evaluation;

import com.sun.management.GarbageCollectionNotificationInfo;
import database.*;
import home.SceneTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.lang.management.GarbageCollectorMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements hasDataObject,Initializable{

    private ObservableList<Evaluation> observableList;

    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane details;

    @FXML
    private AnchorPane filter;

    @FXML
    private Label lot;

    @FXML
    private Label date;

    @FXML
    private Label product;

    @FXML
    private Label ph;

    @FXML
    private Label concentration;

    @FXML
    private Label shipment;

    @FXML
    private Label location_;

    @FXML
    private ComboBox<String> deodorize;

    @FXML
    private ComboBox<String> methylene;

    @FXML
    private ComboBox<String> cockroach;

    @FXML
    private TableColumn column_concentration;

    @FXML
    private TableColumn column_ph;

    @FXML
    private TableColumn column_additive;

    @FXML
    private TableColumn column_binder;

    @FXML
    private TableColumn column_deodorize;

    @FXML
    private TableColumn column_methylene;

    @FXML
    private TableColumn column_cockroach;

    @FXML
    private TableColumn column_shipment;

    @FXML
    private TableColumn column_date;

    @FXML
    private TableView result_tb;

    @FXML
    private TextField toConcentration;

    @FXML
    private TextField fromConcentration;

    @FXML
    private TextField additive;

    @FXML
    private TextField fromPh;

    @FXML
    private TextField toPh;

    @FXML
    private ListView<String> autoComplete = new ListView<>();

    private HashMap<String,Label> additives = new HashMap<>();

    private ArrayList<ComboBox<String>> qualities = new ArrayList<>();

    private Tungsten tungsten;

    private HashMap<String,String> constraints = new HashMap<>();

    private Stage currentWindow;

    @FXML
    private void changePage(){
        System.out.println(location_.getScene().getWindow());
        location_.getScene().getWindow().hide();
        SceneTransition.sceneTransition.getPrevious().show();
    }

    @Override
    public boolean setDataObject(DataObject dataObject) {
        if(dataObject  instanceof Tungsten){
            this.tungsten = (Tungsten) dataObject;
            System.out.println("set data : " + tungsten);
            setParameters(tungsten);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public DataObject getDataObject() {
        return tungsten;
    }

    private void setParameters(Tungsten tungsten){
        System.out.println(tungsten);
        System.out.println(tungsten.getLot());
        System.out.println(lot);
        lot.setText(tungsten.getLot());
        date.setText(tungsten.getProduct_date().toString());
        product.setText(tungsten.getQuantity() + "kg");
        ph.setText("" + tungsten.getPh());
        shipment.setText(tungsten.getShipment() + "kg");
        concentration.setText(tungsten.getConcentration() + " wt%");
        location_.setText(tungsten.getLocation());
        ArrayList<? extends DataObject>arrayList = new ArrayList<>();

        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL("lot = '" + tungsten.getLot() + "'"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);
        result_tb.getColumns().clear();
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_concentration,column_ph,column_additive,column_binder,column_deodorize,column_methylene,column_cockroach,column_shipment,column_date);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList details_observableList = ((VBox)details.getChildren().get(0)).getChildren();
        for (int i = 0; i < details_observableList.size() ; i++) {
            ((HBox)details_observableList.get(i)).getStyleClass().add("details");
        }
        ObservableList filter_observableList = ((VBox)filter.getChildren().get(0)).getChildren();
        for (int i = 0; i < filter_observableList.size() ; i++) {
            ((HBox)filter_observableList.get(i)).getStyleClass().add("filter");
        }

        String[] qualitiy_str = {"-","A","B","C","D"};

        qualities.add(deodorize);
        qualities.add(methylene);
        qualities.add(cockroach);

        for (ComboBox<String> comboBox:qualities){
            comboBox.getItems().addAll(qualitiy_str);
            comboBox.getSelectionModel().selectFirst();
        }

        autoComplete.setVisible(false);
        autoComplete.setMaxHeight(132);
//        autoComplete.toFront();
//        ArrayList<? extends DataObject>arrayList = new ArrayList<>();
//
//        try {
//            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(arrayList.size());
//
//        observableList = FXCollections.observableList((ArrayList<Evaluation>)arrayList);

        column_concentration.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("concentration"));
        column_ph.setCellValueFactory(new PropertyValueFactory<Evaluation,Integer>("ph"));
        column_additive.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("additive"));
        column_binder.setCellValueFactory(new PropertyValueFactory<Evaluation,Float>("binder"));
        column_deodorize.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("deodorize"));
        column_methylene.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("methylene"));
        column_cockroach.setCellValueFactory(new PropertyValueFactory<Evaluation,String>("cockroach"));
        column_shipment.setCellValueFactory(new PropertyValueFactory<Evaluation,Integer>("shipment"));
        column_date.setCellValueFactory(new PropertyValueFactory<Evaluation, Date>("update_date"));

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
        constraintList.add("lot = '" + tungsten.getLot() + "'");
        String[] args = constraintList.toArray(constraintList.toArray(new String[arrayList.size()]));
//        String[] args = (String[])constraintsMap.values().toArray();

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
        result_tb.getColumns().addAll(column_concentration,column_ph,column_additive,column_binder,column_deodorize,column_methylene,column_cockroach,column_shipment,column_date);

        return true;
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

    @FXML
    private void changeAdditive(KeyEvent keyEvent){
        TextField textField = (TextField) keyEvent.getSource();
        HBox hBox = (HBox)((TextField)keyEvent.getSource()).getParent();
        String text = textField.getText();
        ArrayList<String> arrayList;
        System.out.println("text : " + keyEvent.getText());
        System.out.println("code : " + keyEvent.getCode());
        System.out.println("char : " + keyEvent.getCharacter());
        System.out.println("key event : " + keyEvent);
        System.out.println("text : " + text);
        System.out.println(keyEvent.getCode().isArrowKey());
        if(text != "" & !keyEvent.getCode().isArrowKey()){
            try {
                arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from material where name like '%" + text + "%'");
                ObservableList observableList = FXCollections.observableList(arrayList);
                autoComplete.getItems().clear();
                autoComplete.getItems().addAll(observableList);
                autoComplete.prefHeightProperty().bind(Bindings.size(observableList).multiply(24));

                if(observableList.size() == 0){
                    autoComplete.setVisible(false);
                }else {
                    if(!autoComplete.isVisible()){
                        AnchorPane.setTopAnchor(autoComplete,177.0);
                        AnchorPane.setLeftAnchor(autoComplete,391.0);
                        System.out.println("binding size : " + Bindings.size(observableList).get());
                        System.out.println(Bindings.size(observableList).multiply(10).get());
                        autoComplete.prefHeightProperty().bind(Bindings.size(observableList).multiply(24));
                        autoComplete.setVisible(true);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(keyEvent.getCode().isArrowKey()){
            KeyEvent event =new KeyEvent(autoComplete,autoComplete,keyEvent.getEventType(),keyEvent.getText(),keyEvent.getCharacter(),keyEvent.getCode(), keyEvent.isAltDown(),keyEvent.isControlDown(),keyEvent.isControlDown(), keyEvent.isShortcutDown());
            Event.fireEvent(autoComplete,event);
            System.out.println(event);
//            Event.fireEvent(autoComplete,keyEvent.copyFor(autoComplete,autoComplete));]
//            System.out.println(keyEvent.copyFor(autoComplete,autoComplete));
        }else if(text == ""){
            autoComplete.setVisible(false);
        }
//        System.out.println(keyEvent.getText());
//        System.out.println(((TextField)keyEvent.getSource()).getText());
//        System.out.println(keyEvent.getCode());
        if(keyEvent.getCode() == KeyCode.ENTER){
            System.out.println("Enter");
            System.out.println(keyEvent.getSource());
            System.out.println(((TextField)keyEvent.getSource()).getParent());
            if(textField.getText() != ""){
                if(!additives.containsKey(text)){
                    Label label = new Label(textField.getText());
                    label.getStyleClass().add("additive");
                    label.setOnMouseClicked((e)->{
                        System.out.println(e);
                        System.out.println(label);
                        System.out.println("a");
                        if(e.getButton() == MouseButton.PRIMARY){
                            System.out.println("click");
                            if(label.getStyleClass().contains("selected")){
                                label.getStyleClass().remove("selected");
                            }else {
                                label.getStyleClass().add("selected");
                            }
                        }else if(e.getButton() == MouseButton.SECONDARY){
                            if(label.getStyleClass().contains("selected")){
                                additives.remove(label.getText());
                                hBox.getChildren().remove(label);
                            }
                            System.out.println(label);
                        }
                    });
                    additives.put(text,label);
                    System.out.println(((HBox)((TextField)keyEvent.getSource()).getParent()).getChildren().add(label));
                }
            }
            textField.setText("");
        }
    }
}
