package home;

import com.sun.management.GarbageCollectionNotificationInfo;
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

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

//    static
//    {
//        // notification listener. is notified whenever a gc finishes.
//        NotificationListener notificationListener = new NotificationListener()
//        {
//            @Override
//            public void handleNotification(Notification notification, Object handback)
//            {
//                System.out.println("notification");
//                System.out.println(notification);
//                System.out.println(notification.getUserData());
//                System.out.println(notification.getType());
//                System.out.println(notification.getMessage());
//            }
//        };
//
//        // register our listener with all gc beans
//        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans())
//        {
//            System.out.println("register : " + gcBean);
//            NotificationEmitter emitter = (NotificationEmitter) gcBean;
//            emitter.addNotificationListener(notificationListener,null,null);
//        }
//    }

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
            Random random = new Random();
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
//            for (int i = 1; i <= 50;i++){
//                String sql = "insert into binder(name,update_date) values('" + lot("Binder", i) +"',now())";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            for (int i = 1; i <= 1000;i++){
//                String sql = "insert into additive(material_id,update_date,evaluation_id) values(" + (random.nextInt(18)+1) +",now()," + (random.nextInt(5000)+1) + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            ArrayList<Integer> list = new ArrayList<Integer>();
//            for (int i=1; i<4061; i++) {
//                list.add(i);
//            }
//            Collections.shuffle(list);
//
//            String[] locations = {"1F","2F","3F"};
//            for (int i = 1; i <= 2000;i++){
//                String sql = "update tungsten set initial_concentration = " + 30*random.nextFloat() + ", location = '" + locations[random.nextInt(3)] + "' where id = " + i;
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            for (int i = 0; i < 1000;i++){
//                String sql = "insert into binder_mixing(evaluation_id,binder_id) values(" + list.get(i) + "," + (random.nextInt(50)+1) + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            String[] materials = {"Pd","DPG","Mo","Fe2O3","FeO","Nb","C","Al2O3","Ag","Mn","Co","Ni","Fe","Al","SiO2","K","Na","NaCl"};
//
//
//            for (int i = 0; i < materials.length;i++){
//
//                String sql = "insert into material(name) values('" + materials[i] +"')";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            for (int i = 1; i <= 4500;i++){
//                String sql = "insert into evaluation(lot,deodorize,methylene,cockroach,update_date,concentration) values('" + lot("MWx", random.nextInt(2000)+1) +"','" + quality() +"','" + quality() +  "','" +  quality() + "',now()," + 30*random.nextFloat()  +")";
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
