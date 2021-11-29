package home;

import database.*;
import evaluation.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SceneTransition {

    public static SceneTransition sceneTransition = new SceneTransition();

    private SceneTransition(){
        super();
    }

    public Stage transition(String fxmlName,String title){
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            Scene scene = new Scene(root,1000,800);

            stage.getIcons().add(new Image("file:./src/home/images/logo2.png"));
            stage.setScene(scene);
            stage.setTitle(title);

        }catch (Exception e){
            e.printStackTrace();
//          create process to show error messages on stage
        }
        return stage;
    }

    public Stage transition(String fxmlName, String title,DataObject dataObject){
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            Object controller = loader.getController();
            if(controller instanceof hasDataObject){
                ((hasDataObject)controller).setDataObject(dataObject);
            }
            Scene scene = new Scene(root,1000,800);

            stage.getIcons().add(new Image("file:./src/home/images/logo2.png"));
            stage.setScene(scene);
            stage.setTitle(title);

        }catch (Exception e){
            e.printStackTrace();
//          create process to show error messages on stage
        }
        return stage;
    }

    public Scene changeScene(String fxmlName, Scene scene, DataObject dataObject){
        System.out.println("changeScene");
        System.out.println((Tungsten) dataObject);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            System.out.println(loader);
            loader.setController(new Controller());
            Controller controller = loader.getController();
            System.out.println(controller);
            if(controller instanceof Controller){
                controller.setDataObject(dataObject);
                System.out.println("b");
            }

            Parent root = loader.load();
            System.out.println(controller);
            System.out.println("a");

            scene.setRoot(root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return scene;
    }

     public Scene changeScene(String fxmlName, Scene scene){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            scene.setRoot(root);
        }catch (Exception e){
            e.printStackTrace();
        }
        return scene;
    }

}
