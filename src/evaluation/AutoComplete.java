package evaluation;

import database.ConnectionDB;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoComplete extends ListView<TextFlow> {
    private HashMap<ColumnTablePair, TextField> registry = new HashMap<>();
    private TextField target;
    private Color color = Color.AQUA;
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
                double x = entry.localToScene(0.0,0.0).getX();
                double y = entry.localToScene(0.0,0.0).getY() + entry.getHeight();
                if(nv.length() > ov.length()){
                    activateAutoComplete(key,x,y);
                }else if(nv.length()==0){
                    activateAutoComplete(key,x,y);
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
                                text_.setFill(color);
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

    public void setColor(Color color) {
        this.color = color;
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
