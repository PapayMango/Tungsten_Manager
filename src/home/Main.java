package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Main extends Application {

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
        launch(args);
    }
}
