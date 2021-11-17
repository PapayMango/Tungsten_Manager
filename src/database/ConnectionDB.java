package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class ConnectionDB {

    private static String uri = "jdbc:mysql://192.168.1.3:3306/tungsten_db";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    private boolean isConnected = false;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Connection connection;

    public static ConnectionDB connectionDB = new ConnectionDB();

    private ConnectionDB(){}

    public ConnectionDB connectDB(){
        if(!isConnected){
            try{
                Class.forName(driver);
                connection = DriverManager.getConnection(uri,"user001","rmml0321");
//            preparedStatement = connection.prepareStatement("select * from tungsten");
//            resultSet = preparedStatement.executeQuery();
                setConnected(true);
                return this;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else {
            return this;
        }
    }

    public boolean insertTungsten(String table,Tungsten tungsten){
        if (isConnected){
            String sql = "insert into tungsten(lot,update_date,product_date,quantity,quality,method) values('" + tungsten.getLot() +"'," + tungsten.getUpdate_date() + "," + tungsten.getProduct_date() + "," + tungsten.getQuantity() + ",'" + tungsten.getQuality() + "','" + tungsten.getMethod() + "'"+")";
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

    public ArrayList<Tungsten> selectTungsten(){
        return new ArrayList<Tungsten>();
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

}
