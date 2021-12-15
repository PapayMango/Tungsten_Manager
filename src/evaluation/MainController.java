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
    private AutoComplete autoComplete;
    private HashMap<String,String> constraints = new HashMap<>();
    private HashMap<String,Label> binders = new HashMap<>();

    private HashMap<String,Label> additives = new HashMap<>();

    private ArrayList<ComboBox<String>> qualities = new ArrayList<>();

    public boolean refreshTable(HashMap<String,String> constraintsMap){
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
        autoComplete = new AutoComplete();
        main.getChildren().add(autoComplete);
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
    public static class AutoComplete extends ListView<TextFlow>{

        private HashMap<ColumnTablePair,TextField> registry = new HashMap<>();
        private TextField target;
//        private ArrayList<TextField> registry = new ArrayList<>();

        public void init(){

            this.setVisible(false);
            this.setMaxHeight(144);
            this.setFocusTraversable(false);

            this.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
                if (e.getClickCount() >= 2){
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Node node:this.getSelectionModel().getSelectedItem().getChildren()){
                        if (node instanceof Text){
                            stringBuilder.append(((Text)node).getText());
                        }
                    }
                    target.setText(stringBuilder.toString());
                    target.positionCaret(target.getText().length());
                    this.setVisible(false);
                }
            });
            this.addEventFilter(KeyEvent.KEY_RELEASED,(e)->{
                if (e.getCode() == KeyCode.UP & this.getSelectionModel().getSelectedIndex() == 0){
                    this.getSelectionModel().selectLast();
                    this.scrollTo(this.getItems().size()-1);
                    System.out.println(this.getSelectionModel().getSelectedItem());
                }else if(e.getCode() == KeyCode.DOWN & this.getSelectionModel().getSelectedIndex() == this.getItems().size()-1){
                    this.getSelectionModel().selectFirst();
                    this.scrollTo(0);
                    System.out.println(this.getSelectionModel().getSelectedItem());
                }
                e.consume();
            });

            for (ColumnTablePair key:registry.keySet()){
                TextField entry = registry.get(key);
                entry.textProperty().addListener((ob,ov,nv)-> {
                    target = entry;
                    if(nv.length() > ov.length()){
                        activateAutoComplete(key,391.0,177.0);
                    }else if(nv.length()==0){
                        activateAutoComplete(key,391.0,177.0);
                    }else if(nv.length() <= ov.length()){
                        validation_(entry,key);
                    }
                });
                entry.addEventFilter(KeyEvent.KEY_PRESSED,(keyEvent)->{
                    if(keyEvent.getCode() == KeyCode.TAB){
                        System.out.println(this);
                        this.setVisible(false);
                        return;
                    }
                    if(keyEvent.getCode() == KeyCode.UP|keyEvent.getCode() == KeyCode.DOWN){
                        if(this.getSelectionModel().getSelectedIndex() == 0 & keyEvent.getCode() == KeyCode.UP | this.getSelectionModel().getSelectedIndex() == this.getItems().size()-1 & keyEvent.getCode() == KeyCode.DOWN){
                            Event.fireEvent(this,new KeyEvent(KeyEvent.KEY_RELEASED,"","",keyEvent.getCode(),false,false,false,false));
                        }else {
                            Event.fireEvent(this,new KeyEvent(KeyEvent.KEY_PRESSED,"","",keyEvent.getCode(),false,false,false,false));
                        }
                        keyEvent.consume();
                    }
                });
                entry.addEventFilter(KeyEvent.KEY_RELEASED,(keyEvent -> {
                    if(keyEvent.getCode().isWhitespaceKey() & this.isVisible()){
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Node node:this.getSelectionModel().getSelectedItem().getChildren()){
                            if (node instanceof Text){
                                stringBuilder.append(((Text)node).getText());
                            }
                        }
                        entry.setText(stringBuilder.toString());
                        entry.positionCaret(entry.getText().length());
                        this.setVisible(false);
                    }
                }));
            }
        }
        public boolean register(ColumnTablePair key,TextField textField){
            if(!registry.containsKey(key)){
                registry.put(key,textField);
                return true;
            }else{
                return false;
            }
        }
        private boolean validation_(TextField target,ColumnTablePair key){

            TextField textField = target;
            String text = target.getText();
            ArrayList<String> arrayList = new ArrayList<>();

            if(text != ""){
                try {
                    arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select " + key.column + " from " + key.Table + " where " + key.column + " = '" + text + "'");
//                    arrayList = ConnectionDB.connectionDB.connectDB().selectRaw(sql);
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
        private void activateAutoComplete(ColumnTablePair key,double x,double y){

            TextField textField = registry.get(key);
            String text = target.getText();
            ArrayList<String> arrayList;
            ArrayList<TextFlow>textFlowArrayList = new ArrayList<>();

            if(text != ""){
                try {
//                    if(textField == registry.get(textField.getId())){
//                        arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from material where name like '%" + text + "%'");
//                    }else {
//                        arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select name from binder where name like '%" + text + "%'");
//                    }
                    arrayList = ConnectionDB.connectionDB.connectDB().selectRaw("select " + key.column + " from " + key.Table + " where " + key.column + " like '%" + text + "%'");
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
                    this.getItems().clear();
                    this.getItems().addAll(observableList);
                    this.prefHeightProperty().bind(Bindings.size(observableList).multiply(24));
                    this.getSelectionModel().selectFirst();
                    if(observableList.size() == 0){
                        if(!textField.getStyleClass().contains("validation_error")) {
                            textField.getStyleClass().add("validation_error");
                        }
                        this.setVisible(false);
                    }else {
                        if(!this.isVisible()){
                            textField.getStyleClass().remove("validation_error");
                            AnchorPane.setLeftAnchor(this,x);
                            AnchorPane.setTopAnchor(this,y);
                            this.prefHeightProperty().bind(Bindings.size(observableList).multiply(24));
                            this.getSelectionModel().selectFirst();
                            this.setVisible(true);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(text == ""){
                this.setVisible(false);
                if(textField.getStyleClass().contains("validation_error")){
                    textField.getStyleClass().remove("validation_error");
                }
            }
        }
        public static class ColumnTablePair{
            private final String column;
            private final String Table;
            private  final String Key;
            public ColumnTablePair(String column, String table, String key) {
                this.column = column;
                Table = table;
                Key = key;
            }

            public String getKey() {
                return Key;
            }

            public String getTable() {
                return Table;
            }

            public String getColumn() {
                return column;
            }
        }
    }
}
