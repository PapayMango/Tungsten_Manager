package home;

import database.DataObject;
import database.Tungsten;
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

    public Scene changeScene(String fxmlName, Scene scene, DataObject dataObject){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            Controller controller = loader.getController();
            if(controller instanceof Controller)
                controller.setTungsten((Tungsten) dataObject);
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
