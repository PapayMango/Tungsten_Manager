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
//            Random random = new Random();

//            for (int i = 1; i <= 5000;i++)    {
//                String sql = "update evaluation set ph = " + 14*random.nextFloat()  + " where id = " + i;
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }
//            for (int i = 51; i <= 1000;i++){
//                String sql = "insert into binder(name) values('" + lot("Binder", i) +"')";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }
//            for (int i = 1; i <= 10000;i++){
//                String sql = "insert into additive(material_id,update_date,evaluation_id) values(" + (random.nextInt(83)+1) +",now()," + (random.nextInt(20000)+1) + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }
//            ArrayList<Integer> list = new ArrayList<Integer>();
//            for (int i=1001; i<20001; i++) {
//                list.add(i);
//            }
//            Collections.shuffle(list);
//            for (int i = 0; i < 4000;i++){
//                String sql = "insert into binder_mixing(evaluation_id,binder_id) values(" + list.get(i) +"," + (random.nextInt(1000)+1) + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }
////
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
//            String sql = "select s.id,e.id,e.lot from evaluation as e inner join shipment as s on e.lot = s.lot where s.id > 4059";
//            System.out.println(sql);
//            preparedStatement = connection.prepareStatement(sql);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()){
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getInt(2));
//                System.out.println(resultSet.getString(3));
//                if(random.nextBoolean()){
//                    sql = "insert into shipment_evaluation(shipment_id,evaluation_id) values(" + resultSet.getInt(1)+ "," + resultSet.getInt(2) + ")";
//                    preparedStatement = connection.prepareStatement(sql);
//                    System.out.println(preparedStatement.executeUpdate());
//                }
//            }

//            String[] materials = {"Pd","DPG","Mo","Fe2O3","FeO","Nb","C","Al2O3","Ag","Mn","Co","Ni","Fe","Al","SiO2","K","Na","NaCl"};
//            String[] materials = {"H","He","Li","Be","B","N","O","F","Ne","Na","Mg","Sc","P","S","Cl","Ar","K","Ca","Sc","Ti","V","Cr","Mn","Fe","Cu","Zn","Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr","Tc","Ru","Rh","Cd","In","Sn","Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd","Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb","Lu","Hf","Ta"};
//
//
//            for (int i = 0; i < materials.length;i++){
//
//                String sql = "insert into material(name) values('" + materials[i] +"')";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }
//            for (int i = 1; i <= 15000;i++){
//                String sql = "insert into evaluation(lot,deodorize,methylene,cockroach,update_date,concentration,ph) values('" + lot("MWx", random.nextInt(2000)+1) +"','" + quality() +"','" + quality() +  "','" +  quality() + "',now()," + 30*random.nextFloat()  +"," + 14*random.nextFloat() + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }
//            for (int i = 50; i <= 1000;i++){
//                String sql = "insert into company(name,capital,num_employees) values('" + lot("Company", i) +"'," + price() +  "," +  quantity() + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//                while (resultSet.next()){
//                    System.out.println(resultSet);
//                }
//            }
//            for (int i = 1; i <= 5049;i++){
//                String sql = "insert into shipment(lot,shipping_date,company,amount,price) values('" + lot("MWx", random.nextInt(2000)+1) +"',now()," + (random.nextInt(1000)+1) +  "," +  amount() + "," + price() + ")";
//                System.out.println(sql);
//                preparedStatement = connection.prepareStatement(sql);
//                System.out.println("a");
//                System.out.println(preparedStatement.executeUpdate());
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
        launch(args);
    }
}
