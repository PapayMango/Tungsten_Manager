package shipment;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    public void changePage(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
