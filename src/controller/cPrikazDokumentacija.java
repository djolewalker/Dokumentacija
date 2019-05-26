package controller;

import Model.eKatalogPodataka;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class cPrikazDokumentacija implements Initializable {
    
    public eKatalogPodataka katalogPodataka;
    
    private Label label;
    @FXML
    private VBox vbBodSis;
    @FXML
    private VBox vbSisNag;
    @FXML
    private VBox vbKazSis;
    @FXML
    private VBox vbDokumentacija;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void prikaziBodSis(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/bodovi/bPrikazSistemaBodova.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void prikaziSisNag(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/nagrade/bPrikazSistemaNagrada.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void prikaziSisKaz(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/kazne/bPrikazSistemaKazni.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void prikaziDokumentaciju(MouseEvent event) {
    }
    
}
