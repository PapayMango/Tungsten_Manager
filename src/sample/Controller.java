package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public AnchorPane root;

    @FXML
    public AnchorPane container;

    @FXML
    public AnchorPane content;

    @FXML
    public AnchorPane wrapper;

    @FXML
    public Label label_;

    @FXML
    private Button nextButton;
    
    @FXML
    private Button button_;

    @FXML
    private Label select;

    @FXML
    private ListView<TextFlow> listView;

    @FXML
    private TextField textField;

    private ObservableList observableList;

    private Text text_;

    @FXML
    private TextFlow textFlow;



    @FXML
    void OnclickedNextButton(ActionEvent event){
//        nextButton.getScene().getWindow().hide();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transition.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root,1000,800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("transition");
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random random = new Random();
        String[] strings = {"item01","item02","item03","item04","item05"};
        TextFlow[] textFlows = new TextFlow[5];
        for (int i = 0; i<5;i++){
            textFlows[i] = new TextFlow();
            for (int j = 0;j<strings[i].length();j++){
                Text text = new Text();
                text.setText(strings[i].substring(j,j+1));
                text.setFill(new Color(random.nextDouble(),random.nextDouble(),random.nextDouble(),1.0));
                textFlows[i].getChildren().add(text);
            }
        }
        observableList = FXCollections.observableArrayList(textFlows);
        listView.getItems().addAll(observableList);
        listView.getSelectionModel().selectFirst();
        listView.setFocusTraversable(false);
//        Text text = new Text("text_Label");
//        text.getSelectionEnd();
        Text text01 = new Text("text_01");
        text01.setFill(new Color(0.1,0.3,0.1,1));
        Text text02 = new Text("text_02");
        text02.setFill(new Color(0.2,0.2,0.1,1));
        Text text03 = new Text("text_03");
        text03.setFill(new Color(0.3,0.1,0.1,1));
        textFlow.getChildren().addAll(text01,text02,text03);
        textFlow.setPadding(new Insets(10));

//        root.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(root.getId() + " : event filter   Mouse event : " + e );
//        });
//        container.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(container.getId() + " : event filter   Mouse event : " + e );
//        });
//        content.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(content.getId() + " : event filter   Mouse event : " + e );
//        });
//        wrapper.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(wrapper.getId() + " : event filter   Mouse event : " + e );
//        });
//        root.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(root.getId() + " : event handler   Mouse event : " + e );
//        });
//        container.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(container.getId() + " : event handler   Mouse event : " + e );
//        });
//        content.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(content.getId() + " : event handler   Mouse event : " + e );
//        });
//        wrapper.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(wrapper.getId() + " : event handler   Mouse event : " + e );
//        });
//        select.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(select.getId() + " : event handler   Mouse event : " + e );
//        });
//        select.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(select.getId() + " : event filter   Mouse event : " + e );
//            Event.fireEvent(button_,new ActionEvent());
//            Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_PRESSED,"","", KeyCode.DOWN,false,false,false,false));
//        });
//        button_.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(button_.getId() + " : event handler   Mouse event : " + e );
//        });
//        button_.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(button_.getId() + " : event filter   Mouse event : " + e );
//        });
//        textField.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(textField.getId() + " : event handler   Mouse event : " + e );
//        });
//        textField.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(textField.getId() + " : event filter   Mouse event : " + e );
//        });
//        listView.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(listView.getId() + " : event handler   Mouse event : " + e );
//            System.out.println(listView.getSelectionModel().getSelectedIndex());
//            System.out.println(listView.getItems().size());
////            listView.getSelectionModel().select(4);
//        });
//        listView.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println(listView.getId() + " : event filter   Mouse event : " + e );
//        });
//
//
//        root.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(root.getId() + " : event filter   Key event : " + e );
//        });
//        container.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(container.getId() + " : event filter   Key event : " + e );
//        });
//        content.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(content.getId() + " : event filter   Key event : " + e );
//        });
//        wrapper.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(wrapper.getId() + " : event filter   Key event : " + e );
//        });
//        root.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(root.getId() + " : event handler   Key event : " + e );
//        });
//        container.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(container.getId() + " : event handler   Key event : " + e );
//        });
//        content.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(content.getId() + " : event handler   Key event : " + e );
//        });
//        wrapper.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(wrapper.getId() + " : event handler   Key event : " + e );
//        });
//        select.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(select.getId() + " : event handler   Key event : " + e );
//        });
//        select.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(select.getId() + " : event filter   Key event : " + e );
//        });
//        button_.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(button_.getId() + " : event handler   Key event : " + e );
//        });
//        button_.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(button_.getId() + " : event filter   Key event : " + e );
//        });
        textField.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(textField.getId() + " : event handler   Key event : " + e );
            if(e.getCode().isArrowKey())
            {
//                listView.requestFocus();
                select.setText(String.valueOf(textField.getCaretPosition()));
                if(listView.getSelectionModel().getSelectedIndex() == 0 & e.getCode() == KeyCode.UP | listView.getSelectionModel().getSelectedIndex() == listView.getItems().size()-1 & e.getCode() == KeyCode.DOWN){
                    Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_RELEASED,"","",e.getCode(),false,false,false,false));
                }else {
                    Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_PRESSED,"","",e.getCode(),false,false,false,false));
                }
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(textField.getId() + " : event filter   Key event : " + e );

            if (e.getCode().isWhitespaceKey()){
//                Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_PRESSED,"","",e.getCode(),false,false,false,false));
                select.setText(textField.getText(0,textField.getCaretPosition()));
            }
        });
//        listView.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
//            System.out.println(listView.getId() + " : event handler   Key event : " + e );
//            if (e.getCode().isWhitespaceKey()){
//                System.out.println(listView.getSelectionModel().getSelectedItem());
//                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
//                textField.requestFocus();
//            }
//            if (e.getCode() == KeyCode.UP & listView.getSelectionModel().getSelectedIndex() == 0){
//                listView.getSelectionModel().select(listView.getItems().size()-1);
//            }else if(e.getCode() == KeyCode.DOWN & listView.getSelectionModel().getSelectedIndex() == listView.getItems().size()-1){
//                listView.getSelectionModel().select(0);
//            }
//        });
        listView.addEventHandler(KeyEvent.KEY_RELEASED,(e)->{
            System.out.println(listView.getId() + " : event handler   Key event : " + e );
            if (e.getCode().isWhitespaceKey()){
                System.out.println(listView.getSelectionModel().getSelectedItem());
                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
                textField.requestFocus();
            }
            if (e.getCode() == KeyCode.UP & listView.getSelectionModel().getSelectedIndex() == 0){
                listView.getSelectionModel().select(listView.getItems().size()-1);
            }else if(e.getCode() == KeyCode.DOWN & listView.getSelectionModel().getSelectedIndex() == listView.getItems().size()-1){
                listView.getSelectionModel().select(0);
            }
        });
        listView.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(listView.getId() + " : event filter   Key event : " + e );
        });

//
//        root.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(root.getId() + " : event filter   Action event : " + e );
//        });
//        container.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(container.getId() + " : event filter   Action event : " + e );
//        });
//        content.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(content.getId() + " : event filter   Action event : " + e );
//        });
//        wrapper.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(wrapper.getId() + " : event filter   Action event : " + e );
//        });
//        root.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(root.getId() + " : event handler   Action event : " + e );
//        });
//        container.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(container.getId() + " : event handler   Action event : " + e );
//        });
//        content.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(content.getId() + " : event handler   Action event : " + e );
//        });
//        wrapper.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(wrapper.getId() + " : event handler   Action event : " + e );
//        });
//        select.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(select.getId() + " : event handler   Action event : " + e );
//        });
//        select.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(select.getId() + " : event filter   Action event : " + e );
//        });
//        button_.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(button_.getId() + " : event handler   Action event : " + e );
//        });
//        button_.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(button_.getId() + " : event filter   Action event : " + e );
//        });
//        textField.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(textField.getId() + " : event handler   Action event : " + e );
//        });
//        textField.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(textField.getId() + " : event filter   Action event : " + e );
//        });
//        listView.addEventHandler(ActionEvent.ACTION,(e)->{
//            System.out.println(listView.getId() + " : event handler   Action event : " + e );
//        });
//        listView.addEventFilter(ActionEvent.ACTION,(e)->{
//            System.out.println(listView.getId() + " : event filter   Action event : " + e );
//        });
//
//        label_.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println("event filter : " + e);
//        });
//        label_.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
//            System.out.println("event handler : " + e);
//        });

    }

    public void click(MouseEvent mouseEvent) {
        System.out.println("click function : " + mouseEvent);

    }
}
