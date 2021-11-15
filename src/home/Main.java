package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.sql.*;

public class Main extends Application {

    private static String uri = "jdbc:mysql://192.168.1.3:3306/tungsten_db";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    @Override
    public void start(Stage primaryStage) throws Exception{


        System.out.println(primaryStage.getIcons().add(new Image("file:./src/home/images/logo2.png")));
//        System.out.println(primaryStage.getIcons().add(new Image("file:images/logo.png")));
//        System.out.println(new Image("file:./src/home/images/logo.png"));
//        System.out.println("path = " + Paths.get("./src/home/images/logo.png").toAbsolutePath());
        System.out.println(primaryStage.getStyle());
        System.out.println(primaryStage.titleProperty());
        System.out.println(primaryStage.getOwner());
        System.out.println(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("Rmml Co.,Ltd  希少金属材料研究所");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("main");
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(uri,"user001","rmml0321");
//            Connection connection = DriverManager.getConnection(uri);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from tungsten");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(resultSet);
            }
            for (int i = 6; i <= 9;i++){
                String sql = "insert into tungsten(lot,update_date,producr_date,quantity,quality,method) values('MWx1600" + i +"',now(),now(),14,'A','2F電気分解'"+")";
//                String sql = "insert into tungsten(lot,quantity,quality,method) values ('MWx16002',14,'A','2F電気分解');";
                System.out.println(sql);
                preparedStatement = connection.prepareStatement(sql);
                System.out.println("a");
                System.out.println(preparedStatement.executeUpdate());
                while (resultSet.next()){
                    System.out.println(resultSet);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
    }
}
