package database;

import java.sql.*;
import java.util.ArrayList;

public class ConnectionDB {

    private static String uri = "jdbc:mysql://192.168.1.3:3306/tungsten_db";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    private boolean isConnected = false;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Connection connection;

    public static ConnectionDB connectionDB = new ConnectionDB();

    private ConnectionDB(){}

    public ConnectionDB connectDB() throws Exception{
        if(!isConnected){
            try{
                Class.forName(driver);
                connection = DriverManager.getConnection(uri,"user001","rmml0321");
/*
            preparedStatement = connection.prepareStatement("select * from tungsten");
            resultSet = preparedStatement.executeQuery();
*/
                setConnected(true);
                return this;
            }catch (Exception e){
                e.printStackTrace();
                throw new Exception();
            }
        }else {
            return this;
        }
    }

    public boolean insert(DataObjectType dataObjectType, DataObject dataObject){
        if (isConnected){
            String sql = "";
            switch (dataObjectType){
                case Tungsten:
                     sql = Tungsten.createInsertSQL((Tungsten) dataObject);
                    break;
                case Company:
                    break;
                case Shipment:
                    break;
                case Evaluation:
                    break;
                default:
                    break;
            }
            System.out.println(sql);
            try {
                preparedStatement = connection.prepareStatement(sql);
                System.out.println(preparedStatement.executeUpdate());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    public ArrayList<? extends DataObject> select(DataObjectType dataObjectType, String sql){
//        System.out.println("select");
        if (isConnected){
            try {
                resultSet = connection.prepareStatement(sql).executeQuery();
                switch (dataObjectType){
                    case Tungsten:
                        return Tungsten.createData(resultSet);
                    case Company:
                        break;
                    case Shipment:
                        return Shipment.createData(resultSet);
                    case Evaluation:
                        return Evaluation.createData(resultSet);
                    default:
                        break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {

        }
        return new ArrayList<DataObject>();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

}
