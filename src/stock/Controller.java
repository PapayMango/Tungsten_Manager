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

    @FXML
    private TableColumn<Tungsten,String> column_deodorize;

    @FXML
    private TableColumn<Tungsten,String> column_methylene;

    @FXML
    private TableColumn<Tungsten,String> column_cockroach;

    @FXML
    private TableColumn<Tungsten,Float> column_ph;



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
        System.out.println("columns_num : " + result_tb.getColumns().size());
        result_tb.setItems(observableList);
        System.out.println("columns_num : " + result_tb.getColumns().size());
        result_tb.getColumns().addAll(column_lot,column_stock,column_shipment,column_date,column_ph,column_quality,column_method);
        System.out.println("columns_num : " + result_tb.getColumns().size());
    }
}
