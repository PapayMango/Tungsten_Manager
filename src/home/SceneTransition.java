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
import javafx.stage.Window;

public class SceneTransition {

    private Stage previous;

    public static SceneTransition sceneTransition = new SceneTransition();

    private SceneTransition(){
        super();
    }

    public Stage transition(String fxmlName,String title,Stage previous){
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
            Scene scene = new Scene(root,1000,800);

            setPrevious(previous);

            stage.getIcons().add(new Image("file:./src/home/images/logo2.png"));
            stage.setScene(scene);
            stage.setTitle(title);

        }catch (Exception e){
            e.printStackTrace();
//          create process to show error messages on stage
        }
        return stage;
    }

    public Stage transition(String fxmlName, String title,DataObject dataObject,Stage previous){
        Stage stage = new Stage();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));

            Parent root = loader.load();
            Object controller = loader.getController();
            if(controller instanceof hasDataObject){
                ((hasDataObject)controller).setDataObject(dataObject);
            }
            Scene scene = new Scene(root,1000,800);

            setPrevious(previous);
            stage.getIcons().add(new Image("file:./src/home/images/logo2.png"));
            stage.setScene(scene);
            stage.setTitle(title);
//          necessity of reference counting to optimization
        }catch (Exception e){
            e.printStackTrace();
//          create process to show error messages on stage
        }

//        this.previous =
        return stage;
    }

    public Stage getPrevious(){
        return previous;
    }

    public Scene changeScene(String fxmlName, Scene scene, DataObject dataObject){
        System.out.println("changeScene");
        System.out.println((Tungsten) dataObject);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
            Parent root = loader.load();
//            loader.setController(new Controller());
            Controller controller = loader.getController();
            System.out.println(loader.getLocation());
            System.out.println(loader.toString());
            System.out.println(controller.getClass());
            if(controller instanceof Controller){
                System.out.println("c");
                controller.setDataObject(dataObject);
            }
            System.out.println("b");
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

    private void setPrevious(Stage previous){
        this.previous = previous;
    }
}
