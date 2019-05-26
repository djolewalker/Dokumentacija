/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.eKatalogPodataka;
import Model.ePlasman;
import Model.eSistemNagrada;
import baza.DB;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Dimitrije
 */
public class cPartialNagrade extends cPrikazDokumentacija {

    public static eKatalogPodataka podaci;
    public static ObservableList<eSistemNagrada> sistemi;
    public static ObservableList<ePlasman> plasmani;
    public static eSistemNagrada odabraniSistem;

    @FXML
    private TableView<eSistemNagrada> twSistemi;
    @FXML
    private TableColumn<eSistemNagrada, String> tcNazivSistema;
    @FXML
    private Button btObrisiSistem;
    @FXML
    private Button btIzmeniPlasman;
    @FXML
    private Label lbNazivSistema;
    @FXML
    private TableView<ePlasman> twKonkSistem;
    @FXML
    private TableColumn<ePlasman, String> tcPlasman;
    @FXML
    private TableColumn<ePlasman, String> tcBodovi;
    @FXML
    private Button btKreirajPlasman;
    @FXML
    private Button btKreirajSistem2;
    @FXML
    private Label lbGreskaKonkSistem;
    @FXML
    private Button btSacuvajSIS;
    @FXML
    private TextField tfNazivSIS;
    @FXML
    private Label lbGreskaFBS;
    @FXML
    private TextField tfIznosPlasmana;
    @FXML
    private Button btPotvrdiUnosPlasmana;
    @FXML
    private TextField tfPlasmanPlasmana;
    @FXML
    private Label lbGreskaPlasmanDodaj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(url);
        if (url.toString().endsWith("bPrikazSistemaNagrada.fxml")) {
            podaci = new eKatalogPodataka("cpartialnagrade");
            sistemi = FXCollections.observableArrayList(podaci.sistemiNagrada);
            tcNazivSistema.setCellValueFactory(new PropertyValueFactory("naziv"));
            twSistemi.setItems(sistemi);
        } else if (url.toString().endsWith("view/bFormaDetaljiSistema.fxml")) {
        }
    }

    public void pokreniFormuDetaljiSistemaTabele(eSistemNagrada sistem) {
        lbNazivSistema.setText(sistem.getNaziv());
        odabraniSistem = sistem;
        plasmani = FXCollections.observableArrayList(sistem.plasmani);
        twKonkSistem.setItems(plasmani);
        tcPlasman.setCellValueFactory(new PropertyValueFactory("id"));
        tcBodovi.setCellValueFactory(new PropertyValueFactory("iznos"));
    }

    @FXML
    private void pokreniFormuKreirajSistem(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/nagrade/bFormaUnosSistema.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void sacuvajNoviSIS(ActionEvent event) {
        if (tfNazivSIS.getText().equalsIgnoreCase("")) {
            lbGreskaFBS.setText("Unesite naziv");
        } else {
            try {
                eSistemNagrada noviSistem = new eSistemNagrada(tfNazivSIS.getText(), 0);
                DB db = DB.konektujSe();
                PreparedStatement prst = db.getPRPSTM("insert into sistem_nagrada (naziv_nag_sis) values (?)");
                prst.setNString(1, noviSistem.getNaziv());
                int broj = prst.executeUpdate();
                if (broj != 1) {
                    throw new SQLException("Nije uspeo upis");
                }
                ResultSet rs = prst.getGeneratedKeys();
                rs.next();
                noviSistem.setId(rs.getInt(1));
                podaci.sistemiNagrada.add(noviSistem);
                sistemi.add(noviSistem);
                ((Stage) (tfNazivSIS.getScene().getWindow())).close();

            } catch (SQLException sQLException) {
                System.out.println(sQLException);
            }
        }
    }

    @FXML
    private void pokreniFormuDeatljiSistema(ActionEvent event) throws IOException {
        if (twSistemi.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/nagrade/bFormaDetaljiSistema.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            cPartialNagrade c = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
            c.pokreniFormuDetaljiSistemaTabele(twSistemi.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void obrisisSistem(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setHeaderText("Potvrdite da zelite da izbrisete sistem " + odabraniSistem.getNaziv());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DB db = DB.konektujSe();
            db.dmlNaredba("delete from plasmani_nagrada where id_nag_sis = " + odabraniSistem.getId());
            db.dmlNaredba("delete from sistem_nagrada where id_nag_sis = " + odabraniSistem.getId());
            sistemi.remove(odabraniSistem);
            podaci.sistemiNagrada.remove(odabraniSistem);
            ((Stage) (btObrisiSistem.getScene().getWindow())).close();
        }
    }

    @FXML
    private void pokreniFormuKreirajPlasman(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/nagrade/bDodajPlasman.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void unesiPlasman(ActionEvent event) {
        boolean uslov = true;
        if (tfPlasmanPlasmana.getText().equalsIgnoreCase("") || notNubmer(tfPlasmanPlasmana.getText())) {
            lbGreskaPlasmanDodaj.setText("Pogresan unos");
        } else if (tfIznosPlasmana.getText().equalsIgnoreCase("") || notNubmer(tfIznosPlasmana.getText())) {
            lbGreskaPlasmanDodaj.setText("Pogresan unos");
        } else {
            for (ePlasman plasman : odabraniSistem.plasmani) {
                if (plasman.getId() == Integer.parseInt(tfPlasmanPlasmana.getText())) {
                    plasman.setIznos(Integer.parseInt(tfIznosPlasmana.getText()));
                    DB db = DB.konektujSe();
                    db.dmlNaredba("update plasmani_nagrada set iznos = " + plasman.getIznos() + " where id_plasmana= " + plasman.getId() + " and id_nag_sis= " + odabraniSistem.getId());
                    uslov = false;
                    plasmani.remove(plasman);
                    plasmani.add(plasman);
                }
            }
            if (uslov) {
                DB db = DB.konektujSe();
                ePlasman plasman = new ePlasman( 0, Integer.parseInt(tfIznosPlasmana.getText()), Integer.parseInt(tfPlasmanPlasmana.getText()));
                db.dmlNaredba("insert into plasmani_nagrada values (" + odabraniSistem.getId() + "," + plasman.getId() + "," + plasman.getIznos() + ")");
                odabraniSistem.plasmani.add(plasman);
                plasmani.add(plasman);
            }
            ((Stage) (tfIznosPlasmana.getScene().getWindow())).close();
        }

    }

    @FXML
    private void obrisiPlasman(ActionEvent event) {
        ePlasman plasman = twKonkSistem.getSelectionModel().getSelectedItem();
        DB db = DB.konektujSe();
        db.dmlNaredba("delete from plasmani_nagrada where id_nag_sis= " + odabraniSistem.getId() + " and id_plasmana= " + plasman.getId());
        odabraniSistem.plasmani.remove(plasman);
        plasmani.remove(plasman);
    }

    private boolean notNubmer(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException nfe) {
            return true;
        }
        return false;
    }

}
