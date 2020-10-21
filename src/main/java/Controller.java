import javafx.event.ActionEvent;
import javafx.scene.control.Control;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    //public TextField importTextField;
     public void onClickImport(ActionEvent event) {
        //System.out.println("Click");
         final FileChooser fileChooser = new FileChooser();
         configuringFileChooser(fileChooser);
         Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
         File selectedFile = fileChooser.showOpenDialog(stage);

         //System.out.println(selectedFile.getAbsolutePath());
         Main.fileImportPath = selectedFile.getAbsolutePath();
         //importTextField.setText(Main.fileImportPath);
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
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);
        Stage stage = Stage.class.cast(Control.class.cast(event.getSource()).getScene().getWindow());
        File selectedFile = directoryChooser.showDialog(stage);
        Main.fileExportPath = selectedFile.getAbsolutePath();
    }
    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select a Export Directory");

        // Set Initial Directory
        //directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public void onClickConvert(ActionEvent event) {
        assert !Main.fileExportPath.isEmpty() || !Main.fileImportPath.isEmpty();
        Main.convert();
    }
}
