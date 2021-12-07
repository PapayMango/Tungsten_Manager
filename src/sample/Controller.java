package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
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
    private Button nextButton;
    
    @FXML
    private Button button_;

    @FXML
    private Label select;

    @FXML
    private ListView listView;

    @FXML
    private TextField textField;

    private ObservableList observableList;

    private Text text_;

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

        String[] strings = {"item01","item02","item03","item04","item05"};
        observableList = FXCollections.observableArrayList(strings);
        listView.getItems().addAll(observableList);
        listView.getSelectionModel().selectFirst();

        root.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(root.getId() + " : event filter   Mouse event : " + e );
        });
        container.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(container.getId() + " : event filter   Mouse event : " + e );
        });
        content.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(content.getId() + " : event filter   Mouse event : " + e );
        });
        wrapper.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(wrapper.getId() + " : event filter   Mouse event : " + e );
        });
        root.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(root.getId() + " : event handler   Mouse event : " + e );
        });
        container.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(container.getId() + " : event handler   Mouse event : " + e );
        });
        content.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(content.getId() + " : event handler   Mouse event : " + e );
        });
        wrapper.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(wrapper.getId() + " : event handler   Mouse event : " + e );
        });
        select.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(select.getId() + " : event handler   Mouse event : " + e );
        });
        select.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(select.getId() + " : event filter   Mouse event : " + e );
//            Event.fireEvent(button_,new ActionEvent());
//            Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_PRESSED,"","", KeyCode.DOWN,false,false,false,false));
        });
        button_.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(button_.getId() + " : event handler   Mouse event : " + e );
        });
        button_.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(button_.getId() + " : event filter   Mouse event : " + e );
        });
        textField.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(textField.getId() + " : event handler   Mouse event : " + e );
        });
        textField.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(textField.getId() + " : event filter   Mouse event : " + e );
        });
        listView.addEventHandler(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(listView.getId() + " : event handler   Mouse event : " + e );
        });
        listView.addEventFilter(MouseEvent.MOUSE_CLICKED,(e)->{
            System.out.println(listView.getId() + " : event filter   Mouse event : " + e );
        });


        root.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(root.getId() + " : event filter   Key event : " + e );
        });
        container.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(container.getId() + " : event filter   Key event : " + e );
        });
        content.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(content.getId() + " : event filter   Key event : " + e );
        });
        wrapper.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(wrapper.getId() + " : event filter   Key event : " + e );
        });
        root.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(root.getId() + " : event handler   Key event : " + e );
        });
        container.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(container.getId() + " : event handler   Key event : " + e );
        });
        content.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(content.getId() + " : event handler   Key event : " + e );
        });
        wrapper.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(wrapper.getId() + " : event handler   Key event : " + e );
        });
        select.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(select.getId() + " : event handler   Key event : " + e );
        });
        select.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(select.getId() + " : event filter   Key event : " + e );
        });
        button_.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(button_.getId() + " : event handler   Key event : " + e );
        });
        button_.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(button_.getId() + " : event filter   Key event : " + e );
        });
        textField.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(textField.getId() + " : event handler   Key event : " + e );
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(textField.getId() + " : event filter   Key event : " + e );
            if(e.getCode().isArrowKey())
            {
                listView.requestFocus();
                Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_PRESSED,"","",e.getCode(),false,false,false,false));

            }
            if (e.getCode().isWhitespaceKey())
                Event.fireEvent(listView,new KeyEvent(KeyEvent.KEY_PRESSED,"","",e.getCode(),false,false,false,false));
        });
        listView.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(listView.getId() + " : event handler   Key event : " + e );
            if (e.getCode().isWhitespaceKey()){
                System.out.println(listView.getSelectionModel().getSelectedItem());
                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
            }
        });
        listView.addEventFilter(KeyEvent.KEY_PRESSED,(e)->{
            System.out.println(listView.getId() + " : event filter   Key event : " + e );
        });


        root.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(root.getId() + " : event filter   Action event : " + e );
        });
        container.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(container.getId() + " : event filter   Action event : " + e );
        });
        content.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(content.getId() + " : event filter   Action event : " + e );
        });
        wrapper.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(wrapper.getId() + " : event filter   Action event : " + e );
        });
        root.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(root.getId() + " : event handler   Action event : " + e );
        });
        container.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(container.getId() + " : event handler   Action event : " + e );
        });
        content.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(content.getId() + " : event handler   Action event : " + e );
        });
        wrapper.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(wrapper.getId() + " : event handler   Action event : " + e );
        });
        select.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(select.getId() + " : event handler   Action event : " + e );
        });
        select.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(select.getId() + " : event filter   Action event : " + e );
        });
        button_.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(button_.getId() + " : event handler   Action event : " + e );
        });
        button_.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(button_.getId() + " : event filter   Action event : " + e );
        });
        textField.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(textField.getId() + " : event handler   Action event : " + e );
        });
        textField.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(textField.getId() + " : event filter   Action event : " + e );
        });
        listView.addEventHandler(ActionEvent.ACTION,(e)->{
            System.out.println(listView.getId() + " : event handler   Action event : " + e );
        });
        listView.addEventFilter(ActionEvent.ACTION,(e)->{
            System.out.println(listView.getId() + " : event filter   Action event : " + e );
        });


    }
}
