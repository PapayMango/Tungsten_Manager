package evaluation;

import com.sun.javafx.event.EventDispatchChainImpl;
import com.sun.javafx.event.EventHandlerManager;
import com.sun.management.GarbageCollectionNotificationInfo;
import database.*;
import home.SceneTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
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
import javafx.stage.Stage;
import javafx.stage.Window;

import java.lang.management.GarbageCollectorMXBean;
import java.net.URL;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.jar.Attributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TextField binder;

    @FXML
    private TextField fromPh;

    @FXML
    private TextField toPh;

    @FXML
    private ListView<TextFlow> autoComplete = new ListView<>();

    private TextField autoComplete_target;

    private HashMap<String,Label> binders = new HashMap<>();

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

        String[] qualitiy_str = {"-","A","B","C","D"};

        qualities.add(deodorize);
        qualities.add(methylene);
        qualities.add(cockroach);

        for (ComboBox<String> comboBox:qualities){
            comboBox.getItems().addAll(qualitiy_str);
            comboBox.getSelectionModel().selectFirst();
        }
        autoComplete_target = additive;

        autoComplete.setVisible(false);
        autoComplete.setMaxHeight(144);
        autoComplete.setFocusTraversable(false);
//        autoComplete.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            if (e.getClickCount() >= 2){
//                StringBuilder stringBuilder = new StringBuilder();
//                for (Node node:autoComplete.getSelectionModel().getSelectedItem().getChildren()){
//                    if (node instanceof Text){
//                        stringBuilder.append(((Text)node).getText());
//                    }
//                }
//                additive.setText(stringBuilder.toString());
//                additive.positionCaret(additive.getText().length());
//                autoComplete.setVisible(false);
//            }
//        });
        autoComplete.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            if (e.getClickCount() >= 2){
                StringBuilder stringBuilder = new StringBuilder();
                for (Node node:autoComplete.getSelectionModel().getSelectedItem().getChildren()){
                    if (node instanceof Text){
                        stringBuilder.append(((Text)node).getText());
                    }
                }
                autoComplete_target.setText(stringBuilder.toString());
                autoComplete_target.positionCaret(autoComplete_target.getText().length());
                autoComplete.setVisible(false);
            }
        });

        autoComplete.addEventFilter(KeyEvent.KEY_RELEASED,(e)->{
            if (e.getCode() == KeyCode.UP & autoComplete.getSelectionModel().getSelectedIndex() == 0){
                autoComplete.getSelectionModel().selectLast();
                autoComplete.scrollTo(autoComplete.getItems().size()-1);
                System.out.println(autoComplete.getSelectionModel().getSelectedItem());
            }else if(e.getCode() == KeyCode.DOWN & autoComplete.getSelectionModel().getSelectedIndex() == autoComplete.getItems().size()-1){
                autoComplete.getSelectionModel().selectFirst();
                autoComplete.scrollTo(0);
                System.out.println(autoComplete.getSelectionModel().getSelectedItem());
            }
        });

        additive.textProperty().addListener((ob,ov,nv)-> {
            System.out.println("new : " + nv);
            System.out.println("old : " + ov);
            System.out.println(additive.getHeight());
            System.out.println(additive.getWidth());
            System.out.println(additive.getLayoutX());
            System.out.println(additive.getLayoutY());
            System.out.println(additive.getLayoutBounds());
            System.out.println(additive.getBoundsInLocal());
            System.out.println(additive.getBoundsInParent());
            System.out.println(filter.getBoundsInParent());
            System.out.println(additive.getInsets());
            System.out.println(additive.getBoundsInParent().getMinX()+filter.getBoundsInParent().getMinX());
            System.out.println(additive.getBoundsInParent().getMinY()+filter.getBoundsInParent().getMinY() + additive.getParent().getBoundsInParent().getMinY());
            autoComplete_target = additive;
            if(nv.length() > ov.length()){
                activateAutoComplete(additive,377.0,172.0);
            }else if(nv.length()==0){
                activateAutoComplete(additive,377.0,172.0);
            }else if(nv.length() <= ov.length()){
                validation(additive);
            }
        });

        additive.addEventFilter(KeyEvent.KEY_PRESSED,(keyEvent)->{
            if(keyEvent.getCode() == KeyCode.TAB){
                autoComplete.setVisible(false);
                return;
            }
            if(keyEvent.getCode() == KeyCode.UP|keyEvent.getCode() == KeyCode.DOWN){
                if(autoComplete.getSelectionModel().getSelectedIndex() == 0 & keyEvent.getCode() == KeyCode.UP | autoComplete.getSelectionModel().getSelectedIndex() == autoComplete.getItems().size()-1 & keyEvent.getCode() == KeyCode.DOWN){
                    Event.fireEvent(autoComplete,new KeyEvent(KeyEvent.KEY_RELEASED,"","",keyEvent.getCode(),false,false,false,false));
                }else {
                    Event.fireEvent(autoComplete,new KeyEvent(KeyEvent.KEY_PRESSED,"","",keyEvent.getCode(),false,false,false,false)); 
                }
            }
        });

        additive.addEventFilter(KeyEvent.KEY_RELEASED,(keyEvent -> {
            HBox hBox = (HBox)additive.getParent();
            System.out.println(keyEvent.getCode());
            if(keyEvent.getCode().isWhitespaceKey() & autoComplete.isVisible()){
                StringBuilder stringBuilder = new StringBuilder();
                for (Node node:autoComplete.getSelectionModel().getSelectedItem().getChildren()){
                    if (node instanceof Text){
                        stringBuilder.append(((Text)node).getText());
                    }
                }
                additive.setText(stringBuilder.toString());
                additive.positionCaret(additive.getText().length());
                autoComplete.setVisible(false);
            }else if(keyEvent.getCode() == KeyCode.ENTER & !autoComplete.isVisible()){
                if(additive.getText() != ""){
                    if(!additives.containsKey(additive.getText())){
                        Label label = new Label(additive.getText());
                        label.getStyleClass().add("additive");
                        label.setOnMouseClicked((e)->{
                            if(e.getButton() == MouseButton.PRIMARY){
                                if(label.getStyleClass().contains("selected")){
                                    label.getStyleClass().remove("selected");
                                }else {
                                    label.getStyleClass().add("selected");
                                }
                            }else if(e.getButton() == MouseButton.SECONDARY){
                                if(label.getStyleClass().contains("selected")){
                                    additives.remove(label.getText());
                                    hBox.getChildren().remove(label);
                                    changeAdditive_();
                                }
                            }
                        });
                        additives.put(additive.getText(),label);
                        ((HBox)((TextField)keyEvent.getSource()).getParent()).getChildren().add(label);
                    }
                    additive.setText("");
                }
            }
        }));

        binder.textProperty().addListener((ob,ov,nv)-> {
            System.out.println("new : " + nv);
            System.out.println("old : " + ov);
            System.out.println(binder.getBoundsInParent());
            System.out.println(binder.getBoundsInLocal());
            System.out.println(binder.getLayoutBounds());
            System.out.println(binder.getInsets());
            System.out.println(binder.getLayoutX());
            System.out.println(binder.getLayoutY());
            System.out.println(binder.getParent().getBoundsInParent());
            autoComplete_target = binder;
            if(nv.length() > ov.length()){
                activateAutoComplete(binder,377.0,208.0);
            }else if(nv.length()==0){
                activateAutoComplete(binder,377.0,208.0);
            }else if(nv.length() <= ov.length()){
                validation(binder);
            }
        });
        binder.addEventFilter(KeyEvent.KEY_PRESSED,(keyEvent)->{
            if(keyEvent.getCode() == KeyCode.TAB){
                autoComplete.setVisible(false);
                return;
            }
            if(keyEvent.getCode() == KeyCode.UP|keyEvent.getCode() == KeyCode.DOWN){
                if(autoComplete.getSelectionModel().getSelectedIndex() == 0 & keyEvent.getCode() == KeyCode.UP | autoComplete.getSelectionModel().getSelectedIndex() == autoComplete.getItems().size()-1 & keyEvent.getCode() == KeyCode.DOWN){
                    Event.fireEvent(autoComplete,new KeyEvent(KeyEvent.KEY_RELEASED,"","",keyEvent.getCode(),false,false,false,false));
                }else {
                    Event.fireEvent(autoComplete,new KeyEvent(KeyEvent.KEY_PRESSED,"","",keyEvent.getCode(),false,false,false,false));
                }
            }
        });

        binder.addEventFilter(KeyEvent.KEY_RELEASED,(keyEvent -> {
            HBox hBox = (HBox)binder.getParent();
            System.out.println(keyEvent.getCode());
            if(keyEvent.getCode().isWhitespaceKey() & autoComplete.isVisible()){
                StringBuilder stringBuilder = new StringBuilder();
                for (Node node:autoComplete.getSelectionModel().getSelectedItem().getChildren()){
                    if (node instanceof Text){
                        stringBuilder.append(((Text)node).getText());
                    }
                }
                binder.setText(stringBuilder.toString());
                binder.positionCaret(binder.getText().length());
                autoComplete.setVisible(false);
            }else if(keyEvent.getCode() == KeyCode.ENTER & !autoComplete.isVisible()){
                if(binder.getText() != ""){
                    if(!binders.containsKey(binder.getText())){
                        Label label = new Label(binder.getText());
                        label.getStyleClass().add("binder");
                        label.setOnMouseClicked((e)->{
                            if(e.getButton() == MouseButton.PRIMARY){
                                if(label.getStyleClass().contains("selected")){
                                    label.getStyleClass().remove("selected");
                                }else {
                                    label.getStyleClass().add("selected");
                                }
                            }else if(e.getButton() == MouseButton.SECONDARY){
                                if(label.getStyleClass().contains("selected")){
                                    binders.remove(label.getText());
                                    hBox.getChildren().remove(label);
                                    changeBinder_();
                                }
                            }
                        });
                        binders.put(binder.getText(),label);
                        ((HBox)((TextField)keyEvent.getSource()).getParent()).getChildren().add(label);
                    }
                    binder.setText("");
                }
            }
        }));

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
//        System.out.println("hash map : " + constraintsMap);
//        System.out.println(constraintsMap.entrySet());
//        System.out.println("method : " + constraintsMap.get("method"));
        ArrayList<? extends DataObject>arrayList = new ArrayList<>();
        ArrayList<String> constraintList = new ArrayList<>(constraintsMap.values());
        constraintList.add("lot = '" + tungsten.getLot() + "'");
        String[] args = constraintList.toArray(constraintList.toArray(new String[arrayList.size()]));

//        System.out.println(args.length);
//        System.out.println(args);
        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Evaluation, Evaluation.createSelectSQL(args));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(arrayList.size());

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
        changeAdditive_();
    }

    private void changeAdditive_(){
        String constraint = "a.name ";
        int i = 0;
        if(additives.size() > 0){
            for (String key:additives.keySet()) {
                if (i == 0){
                    constraint += " = '" + key + "'";
                    i++;
                    continue;
                }
                constraint += " and a.name = '" + key + "'";
            }
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
        changeBinder_();
    }

    private void changeBinder_(){
        String constraint = "b.name ";
        int i = 0;
        if(binders.size() > 0){
            for (String key:binders.keySet()) {
                if (i == 0){
                    constraint += " = '" + key + "'";
                    i++;
                    continue;
                }
                constraint += " or b.name = '" + key + "'";
            }
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



    private void activateAutoComplete(TextInputControl target,double x,double y){

        TextField textField = (TextField) target;

        String text = target.getText();
        ArrayList<String> arrayList;
        ArrayList<TextFlow>textFlowArrayList = new ArrayList<>();
        if(text != ""){
            try {
                 if(autoComplete_target == additive){
                     arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from material where name like '%" + text + "%'");
                 }else {
                     arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from binder where name like '%" + text + "%'");
                 }
                for (String str:arrayList) {
                    TextFlow textFlow = new TextFlow();
                    Pattern pattern = Pattern.compile(text,Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()){
                        int index = 0;
                        int[] index_ = {matcher.start(),matcher.end(),str.length()};
                        for(int i = 0; i < 3;i++){
                            Text text_ = new Text();
                            text_.setText(str.substring(index,index_[i]));
                            if(i == 1)
                                text_.setFill(Color.AQUA);
                            textFlow.getChildren().add(text_);
                            index = index_[i];
                        }
                        textFlowArrayList.add(textFlow);
                    }
                }
                ObservableList observableList = FXCollections.observableList(textFlowArrayList);
                autoComplete.getItems().clear();
                autoComplete.getItems().addAll(observableList);
                autoComplete.prefHeightProperty().bind(Bindings.size(observableList).multiply(24));
                autoComplete.getSelectionModel().selectFirst();
                if(observableList.size() == 0){
                    if(!textField.getStyleClass().contains("validation_error")) {
                        textField.getStyleClass().add("validation_error");
                    }
                    autoComplete.setVisible(false);
                }else {
                    if(!autoComplete.isVisible()){
                        textField.getStyleClass().remove("validation_error");
                        AnchorPane.setLeftAnchor(autoComplete,x);
                        AnchorPane.setTopAnchor(autoComplete,y);
                        autoComplete.prefHeightProperty().bind(Bindings.size(observableList).multiply(24));
                        autoComplete.getSelectionModel().selectFirst();
                        autoComplete.setVisible(true);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(text == ""){
            autoComplete.setVisible(false);
            if(textField.getStyleClass().contains("validation_error")){
                textField.getStyleClass().remove("validation_error");
            }
        }
    }

    private boolean validation(TextInputControl target){
        TextField textField = (TextField) target;

        String text = target.getText();
        ArrayList<String> arrayList = new ArrayList<>();

        if(text != ""){
            try {
                if(autoComplete_target == additive){
                    arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from material where name = '" + text + "'");
                }else {
                    arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from binder where name = '" + text + "'");
                }
                if(arrayList.size() == 0){
                    if(!textField.getStyleClass().contains("validation_error")) {
                        textField.getStyleClass().add("validation_error");
                    }
                }else {
                    if(textField.getStyleClass().contains("validation_error")){
                        textField.getStyleClass().remove("validation_error");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(text == ""){
            if(textField.getStyleClass().contains("validation_error")){
                textField.getStyleClass().remove("validation_error");
            }
        }
        return arrayList.size() == 1;
    }

    private void changeTarget(TextField target){
        autoComplete_target = target;
//        autoComplete.removeEventFilter();
        autoComplete.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            if (e.getClickCount() >= 2){
                StringBuilder stringBuilder = new StringBuilder();
                for (Node node:autoComplete.getSelectionModel().getSelectedItem().getChildren()){
                    if (node instanceof Text){
                        stringBuilder.append(((Text)node).getText());
                    }
                }
                autoComplete_target.setText(stringBuilder.toString());
                autoComplete_target.positionCaret(autoComplete_target.getText().length());
                autoComplete.setVisible(false);
            }
        });
    }

    public class AutoComplete extends ListView<TextFlow>{

        private TextField target;
        private ArrayList<TextField> registry = new ArrayList<>();

        public void init(ArrayList<TextField> arrayList,AutoComplete autoComplete){
            registry = arrayList;
            for (TextField entry:registry){
                entry.textProperty().addListener((ob,ov,nv)-> {
                    if(nv.length() > ov.length()){
                        activateAutoComplete(entry,391.0,177.0);
                    }else if(nv.length()==0){
                        activateAutoComplete(entry,391.0,177.0);
                    }else if(nv.length() <= ov.length()){
                        validation_(entry,"sql");
                    }
                });
                entry.addEventFilter(KeyEvent.KEY_PRESSED,(keyEvent)->{
                    if(keyEvent.getCode() == KeyCode.TAB){
                        System.out.println(this);
                        autoComplete.setVisible(false);
                        return;
                    }
                    if(keyEvent.getCode() == KeyCode.UP|keyEvent.getCode() == KeyCode.DOWN){
                        if(autoComplete.getSelectionModel().getSelectedIndex() == 0 & keyEvent.getCode() == KeyCode.UP | autoComplete.getSelectionModel().getSelectedIndex() == autoComplete.getItems().size()-1 & keyEvent.getCode() == KeyCode.DOWN){
                            Event.fireEvent(autoComplete,new KeyEvent(KeyEvent.KEY_RELEASED,"","",keyEvent.getCode(),false,false,false,false));
                        }else {
                            Event.fireEvent(autoComplete,new KeyEvent(KeyEvent.KEY_PRESSED,"","",keyEvent.getCode(),false,false,false,false));
                        }
                    }
                });
                entry.addEventFilter(KeyEvent.KEY_RELEASED,(keyEvent -> {
                    if(keyEvent.getCode().isWhitespaceKey() & autoComplete.isVisible()){
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Node node:autoComplete.getSelectionModel().getSelectedItem().getChildren()){
                            if (node instanceof Text){
                                stringBuilder.append(((Text)node).getText());
                            }
                        }
                        entry.setText(stringBuilder.toString());
                        entry.positionCaret(entry.getText().length());
                        autoComplete.setVisible(false);
                    }
                }));
            }
        }
        private boolean validation_(TextInputControl target,String sql){
            TextField textField = (TextField) target;

            String text = target.getText();
            ArrayList<String> arrayList = new ArrayList<>();

            if(text != ""){
                try {
//                arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from material where name = '" + text + "'");
                    arrayList = ConnectionDB.connectionDB.connectDB().selectRaw(sql);
                    if(arrayList.size() == 0){
                        if(!textField.getStyleClass().contains("validation_error")) {
                            textField.getStyleClass().add("validation_error");
                        }
                    }else {
                        if(textField.getStyleClass().contains("validation_error")){
                            textField.getStyleClass().remove("validation_error");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(text == ""){
                if(textField.getStyleClass().contains("validation_error")){
                    textField.getStyleClass().remove("validation_error");
                }
            }
            return arrayList.size() == 1;
        }
    }
}
