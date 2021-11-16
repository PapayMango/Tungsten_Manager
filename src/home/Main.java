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
//            Class.forName(driver);
//            Connection connection = DriverManager.getConnection(uri,"user001","rmml0321");
////            Connection connection = DriverManager.getConnection(uri);
//            PreparedStatement preparedStatement = connection.prepareStatement("select * from tungsten");
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()){
//                System.out.println(resultSet);
//            }
//            for (int i = 1001; i <= 2000;i++){
//                String sql = "insert into tungsten(lot,update_date,product_date,quantity,quality,method) values('" + lot("MWx",i) +"',now(),now()," + quantity() + ",'" + quality() + "','" + method_str() + "'"+")";
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
