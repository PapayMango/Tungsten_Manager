package shipment;

import database.*;
import home.SceneTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable, hasDataObject {

    @FXML
    public AnchorPane main;
    @FXML
    public Label lot;
    @FXML
    public Label date;
    @FXML
    public Label concentration;
    @FXML
    public Label shipment;
    @FXML
    public Label ph;
    @FXML
    public Label location_;
    @FXML
    public Label product;
    @FXML
    public Label company;
    @FXML
    public Label num_employee;
    @FXML
    public Label capital;
    @FXML
    public Label company_location;
    @FXML
    public Label shipment_;
    @FXML
    public Label shipping_date;
    @FXML
    public Label sales;
    @FXML
    public Label costs;
    @FXML
    public Label concentration_;
    @FXML
    public Label ph_;
    @FXML
    public Label binder;
    @FXML
    public Label additive_;
    @FXML
    public Label deodorize;
    @FXML
    public Label methylene;
    @FXML
    public Label cockroach;
    @FXML
    public Label toStock;
    @FXML
    public Label amount_product;
    @FXML
    public Label update_date;
    @FXML
    public Label toEvaluation;
    @FXML
    public TableView result_tb;
    @FXML
    public TableColumn column_date;
    @FXML
    public TableColumn column_shipment;
    @FXML
    public TableColumn column_sales;
    @FXML
    public TableColumn column_costs;
    @FXML
    public TableColumn column_company;
    @FXML
    public VBox product_box;
    @FXML
    public VBox evaluation_box;
    @FXML
    public VBox shipment_box;
    @FXML
    public Label company_name;
    @FXML
    public Label amount_shipment;

    private Tungsten tungsten;

    private Evaluation evaluation;

    private DateTimeFormatter dateTimeFormatter;

    private ObservableList<Shipment> observableList;

    private Shipment selected;

    @FXML
    public void changePage(MouseEvent mouseEvent) {
        Label label = (Label)mouseEvent.getSource();
        System.out.println(location_.getScene().getWindow());
        System.out.println(label.getId());
        System.out.println(label.getId().matches("toStock"));
        location_.getScene().getWindow().hide();
        if(label.getId().matches("toStock")){
            System.out.println("a");
            Stage stage = SceneTransition.sceneTransition.transition("../stock/stock.fxml","在庫管理",(Stage)result_tb.getScene().getWindow());
            System.out.println(stage);
            stage.show();
        }else if(label.getId().matches("toEvaluation")){
            System.out.println("b");
            SceneTransition.sceneTransition.getPrevious().show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Node node:product_box.getChildren()){
                node.getStyleClass().add("hbox_margin");
        }
        for (Node node:evaluation_box.getChildren()){
                node.getStyleClass().add("hbox_margin");
        }
        for (Node node:shipment_box.getChildren()){
                node.getStyleClass().add("hbox_margin");
        }
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        column_company.setCellValueFactory(new PropertyValueFactory<Shipment,Company>("Company"));
        column_date.setCellValueFactory(new PropertyValueFactory<Shipment, Timestamp>("shipping_date"));
        column_shipment.setCellValueFactory(new PropertyValueFactory<Shipment,Integer>("amount"));
        column_costs.setCellValueFactory(new PropertyValueFactory<Shipment,String>("costs"));
        column_sales.setCellValueFactory(new PropertyValueFactory<Shipment,String>("sales"));
        result_tb.setRowFactory((t) ->{
            TableRow tableRow = new TableRow();
            tableRow.addEventFilter(MouseEvent.MOUSE_CLICKED,(event)->{
                setShipmentLabel();
            });
            return tableRow;
        });
    }

    @Override
    public boolean setDataObject(DataObject dataObject){
        if(dataObject  instanceof Evaluation){
            this.evaluation = (Evaluation) dataObject;
            System.out.println("set data : " + evaluation);
            setParameters(evaluation);
            return true;
        }else {
            return false;
        }
    }

    private void setParameters(Evaluation evaluation){
        try {
            ArrayList<? extends DataObject> arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Tungsten,"select * from tungsten where lot = '" + evaluation.getLot() + "'");
            if (arrayList.get(0) instanceof Tungsten){
                tungsten = (Tungsten) arrayList.get(0);
            }
            date.setText(tungsten.getProduct_date().toLocalDateTime().format(dateTimeFormatter));
            shipment.setText(tungsten.getShipment() + "kg");
            concentration.setText(tungsten.getConcentration() + "wt%");
            ph.setText(tungsten.getPh() + "");
            amount_product.setText(tungsten.getQuantity() + "kg");
            location_.setText(tungsten.getLocation());
        }catch (Exception e){
            e.printStackTrace();
        }
        concentration_.setText(evaluation.getConcentration() + "wt%");
        ph_.setText(evaluation.getPh() + "");
        additive_.setText(evaluation.getAdditive());
        binder.setText(evaluation.getBinder());
        deodorize.setText(evaluation.getDeodorize());
        methylene.setText(evaluation.getMethylene());
        cockroach.setText(evaluation.getCockroach());
        update_date.setText(evaluation.getUpdate_date().toLocalDateTime().format(dateTimeFormatter));
        lot.setText(evaluation.getLot());

        ArrayList<? extends DataObject>arrayList = new ArrayList<>();

        try {
            arrayList = ConnectionDB.connectionDB.connectDB().select(DataObjectType.Shipment, Shipment.createSelectSQL("s.lot = '" + tungsten.getLot() + "' and se.evaluation_id = " + evaluation.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        observableList = FXCollections.observableList((ArrayList<Shipment>)arrayList);
        result_tb.getColumns().clear();
        result_tb.setItems(observableList);
        result_tb.getColumns().addAll(column_date,column_shipment,column_sales,column_costs,column_company);

        result_tb.getSelectionModel().selectFirst();
        if(arrayList.size()>0)
            setShipmentLabel();
    }

    @Override
    public DataObject getDataObject() {
        return evaluation;
    }

    private void setShipmentLabel(){
        Shipment shipment = (Shipment) result_tb.getSelectionModel().getSelectedItem();
        if(selected == shipment)
            return;
        selected = shipment;
        company_name.setText(shipment.getCompany().getName());
        company_location.setText(shipment.getCompany().getLocation());
        num_employee.setText(shipment.getCompany().getEmployees() + "");
        costs.setText(shipment.getCosts() + "円");
        sales.setText(shipment.getSales() + "円");
        capital.setText(shipment.getCompany().getCapital() +"千円");
        amount_shipment.setText(shipment.getAmount() + "kg");
        shipping_date.setText(shipment.getShipping_date().toLocalDateTime().format(dateTimeFormatter));
    }
}
