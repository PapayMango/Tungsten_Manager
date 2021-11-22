package home;

import database.DataObject;
import database.Evaluation;
import database.Tungsten;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.sql.*;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    private static String uri = "jdbc:mysql://192.168.1.3:3306/tungsten_db";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.getIcons().add(new Image("file:./src/home/images/logo2.png"));
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("Rmml Co.,Ltd  希少金属材料研究所");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    private static String lot(String prefix,int num){
        String num_str;
        int length;
        if(num >= 1000000 | num < 0){
            return prefix + "000000";
        }else {
            num_str = "" + num;
            length = num_str.length();
//            System.out.println(num_str.length());
            for (int i = 0;i < 6-length;i++){
                num_str = "0" + num_str;
//                System.out.println(num_str);
            }
//            System.out.println(num_str);
        }
        return prefix + num_str;
    }

    private static int quantity(){

        Random rand = new Random();

        return rand.nextInt(1000);
    }

    private static int amount(){

        Random rand = new Random();

        return rand.nextInt(100);
    }

    private static int price(){

        Random rand = new Random();

        return rand.nextInt(1000000);
    }

    private static int company(){

        Random rand = new Random();

        return rand.nextInt(49) + 1;
    }

    private static String quality(){

        String[] quality = {"A","B","C","D"};
        Random random = new Random();

        return quality[random.nextInt(4)];
    }

    private static String method_str(){

        String[] method = {"1Fポット","2F電気分解","3F花瓶"};
        Random random = new Random();

        return method[random.nextInt(3)];
    }

    public static void main(String[] args) {
        System.out.println("main");
        try{
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(uri,"user001","rmml0321");
            PreparedStatement preparedStatement = connection.prepareStatement(Tungsten.createSelectSQL());
            ResultSet resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
//            System.out.println(resultSet);

//            Tungsten tungsten = new Tungsten("a",new Date(),new Date(),1,'A',"A");
//            Evaluation evaluation = new Evaluation();
//            DataObject dataObject = (DataObject)evaluation;
//

//
//            while (resultSet.next()){
//                System.out.println(resultSet);
//                System.out.println("ID : " + resultSet.getInt("id"));
//                System.out.println(resultSet.getDate("update_date"));
//            }
//            Random random = new Random();
//
//
//            for (int i = 1; i <= 5;i++){
//                String sql = "update tungsten set update_date = now() where id = " + i;
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println(preparedStatement.executeUpdate());
//            }
//            Random random = new Random();
//
//            for (int i = 1; i <= 500;i++){
//                String sql = "insert into shipment(lot,shipping_date,company,amount,price) values('" + lot("MWx", random.nextInt(2000)+1) +"',now()," + company() +  "," +  amount() + "," + price()  +")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            for (int i = 1; i <= 49;i++){
//                String sql = "insert into company(name,capital,num_employees) values('" + lot("Company", i) +"'," + price() +  "," +  quantity() + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
    }
}
