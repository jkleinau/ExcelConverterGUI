import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class Controller {
    @FXML private Label label;
     public void onClickImport(ActionEvent event) {
        System.out.println("Click");
         final FileChooser fileChooser = new FileChooser();
         configuringFileChooser(fileChooser);
         Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
         File selectedFile = fileChooser.showOpenDialog(stage);

     }



    private void configuringFileChooser(FileChooser fileChooser) {
        // Set title for FileChooser
        fileChooser.setTitle("Select Import CSV");

        // Set Initial Directory
        //fileChooser.setInitialDirectory(new File("C:/Users/tran/Pictures"));

        // Add Extension Filters
        fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("All Files", "*.*"), //
                new FileChooser.ExtensionFilter("CSV", "*.csv")); //

    }

    public void onClickExport(ActionEvent event) {
    }
}
