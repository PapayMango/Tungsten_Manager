package home;

import javafx.fxml.FXMLLoader;
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
}
