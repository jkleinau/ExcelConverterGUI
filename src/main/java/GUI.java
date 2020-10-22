import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Aufmaß");
        primaryStage.setScene(new Scene(root, 470, 160));
        primaryStage.show();
        ObjectUtils stringUtils = new ObjectUtils();
    }


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop(){

    }
}