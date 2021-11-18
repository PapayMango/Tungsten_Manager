package stock;

import database.ConnectionDB;
import database.DataObject;
import database.DataType;
import database.Tungsten;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");

        ArrayList<? extends DataObject>arrayList = ConnectionDB.connectionDB.connectDB().select(DataType.Tungsten,Tungsten.createSelectSQL());
        System.out.println(arrayList.size());

        observableList = FXCollections.observableList((ArrayList<Tungsten>)arrayList);

        column_lot.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("lot"));
        column_stock.setCellValueFactory(new PropertyValueFactory<Tungsten,Integer>("quantity"));
        column_shipment.setCellValueFactory(new PropertyValueFactory<Tungsten,Integer>("shipment"));
        column_date.setCellValueFactory(new PropertyValueFactory<Tungsten,Date>("update_date"));
        column_quality.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("quality"));
        column_method.setCellValueFactory(new PropertyValueFactory<Tungsten,String>("method"));

        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_lot,column_stock,column_date,column_quality,column_method);
    }
}
