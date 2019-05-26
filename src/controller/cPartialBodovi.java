/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import Model.eKatalogPodataka;
import Model.ePlasman;
import Model.eSistemBodovanja;
import baza.DB;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Dimitrije
 */
public class cPartialBodovi extends cPrikazDokumentacija {

    public static eKatalogPodataka podaci;
    public static ObservableList<eSistemBodovanja> sistemi;
    public static ObservableList<ePlasman> plasmani;
    public static eSistemBodovanja odabraniSistem;

    @FXML
    private TableView<eSistemBodovanja> twBodovniSIstemi;
    @FXML
    private TableColumn<eSistemBodovanja, String> tcNazivSistema;
    @FXML
    private TextField tfNazivFBS;
    @FXML
    private Label lbGreskaFBS;
    @FXML
    private Button btDetalji;
    @FXML
    private TableView<ePlasman> twKonkSistem;
    @FXML
    private TableColumn<ePlasman, String> tcPlasman;
    @FXML
    private TableColumn<ePlasman, String> tcBodovi;
    @FXML
    private Button btObrisiSistem;
    @FXML
    private Button btPotvrdiUnosPlasmana;
    @FXML
    private Button btIzmeniPlasman;
    @FXML
    private Label lbNazivSistema;
    @FXML
    private Button btKreirajPlasman;
    @FXML
    private Button btKreirajSistem2;
    @FXML
    private Label lbGreskaKonkSistem;
    @FXML
    private TextField tfPlasmanPlasmana;
    @FXML
    private TextField tfBodoviPlasmana;
    @FXML
    private Label lbGreskaPlasmanDodaj;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(url);
        if (url.toString().endsWith("bPrikazSistemaBodova.fxml")) {
            podaci = new eKatalogPodataka("cPartialBodovi");
            System.out.println(podaci.sistemiBodovanja.get(0).naziv);
            sistemi = FXCollections.observableArrayList(podaci.sistemiBodovanja);
            twBodovniSIstemi.setItems(sistemi);
            tcNazivSistema.setCellValueFactory(new PropertyValueFactory("naziv"));
        }
    }

    public void pokreniFormuDetaljiSistemaTabele(eSistemBodovanja sistem) {
        lbNazivSistema.setText(sistem.getNaziv());
        odabraniSistem = sistem;
        plasmani = FXCollections.observableArrayList(sistem.plasmani);
        twKonkSistem.setItems(plasmani);
        tcPlasman.setCellValueFactory(new PropertyValueFactory("id"));
        tcBodovi.setCellValueFactory(new PropertyValueFactory("brojBodova"));
        System.out.println("OK");
    }

    @FXML
    private void pokreniFormuKreirajSistem(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/bFormaUnosSistema.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void sacuvajNoviBS(ActionEvent event) {
        if (tfNazivFBS.getText().equalsIgnoreCase("")) {
            lbGreskaFBS.setText("Unesite naziv");
        } else {
            try {
                eSistemBodovanja noviSistem = new eSistemBodovanja(tfNazivFBS.getText(), 0);
                DB db = DB.konektujSe();
                PreparedStatement prst = db.getPRPSTM("insert into sistem_bodova (naziv_bod_sis) values (?)");
                prst.setNString(1, noviSistem.getNaziv());
                int broj = prst.executeUpdate();
                if (broj != 1) {
                    throw new SQLException("Nije uspeo upis");
                }
                ResultSet rs = prst.getGeneratedKeys();
                rs.next();
                noviSistem.setId(rs.getInt(1));
                podaci.sistemiBodovanja.add(noviSistem);
                sistemi.add(noviSistem);
                ((Stage) (tfNazivFBS.getScene().getWindow())).close();

            } catch (SQLException sQLException) {
                System.out.println(sQLException);
            }
        }
    }

    @FXML
    private void pokreniFormuDeatljiSistema(ActionEvent event) throws IOException {
        if (twBodovniSIstemi.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/bFormaDetaljiSistema.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            cPartialBodovi c = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
            c.pokreniFormuDetaljiSistemaTabele(twBodovniSIstemi.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void obrisisSistem(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setHeaderText("Potvrdite da zelite da izbrisete sistem");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DB db = DB.konektujSe();
            db.dmlNaredba("delete from plasmani_bodova where id_bod_sis = " + odabraniSistem.getId());
            db.dmlNaredba("delete from sistem_bodova where id_bod_sis = " + odabraniSistem.getId());
            sistemi.remove(odabraniSistem);
            podaci.sistemiBodovanja.remove(odabraniSistem);
            ((Stage) (btObrisiSistem.getScene().getWindow())).close();
        }
    }

    @FXML
    private void pokreniFormuKreirajPlasman(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/bDodajPlasman.fxml"));
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
        } else if (tfBodoviPlasmana.getText().equalsIgnoreCase("") || notNubmer(tfBodoviPlasmana.getText())) {
            lbGreskaPlasmanDodaj.setText("Pogresan unos");
        } else {
            for (ePlasman plasman : odabraniSistem.plasmani) {
                if (plasman.getId() == Integer.parseInt(tfPlasmanPlasmana.getText())) {
                    plasman.setBrojBodova(Integer.parseInt(tfBodoviPlasmana.getText()));
                    DB db = DB.konektujSe();
                    db.dmlNaredba("update plasmani_bodova set broj_bod = " + plasman.getBrojBodova() + " where id_plasmana= " + plasman.getId() + " and id_bod_sis= " + odabraniSistem.getId());
                    uslov = false;
                    plasmani.remove(plasman);
                    plasmani.add(plasman);
                }
            }
            if (uslov) {
                DB db = DB.konektujSe();
                ePlasman plasman = new ePlasman(Integer.parseInt(tfBodoviPlasmana.getText()), 0, Integer.parseInt(tfPlasmanPlasmana.getText()));
                db.dmlNaredba("insert into plasmani_bodova values (" + plasman.getId() + "," + odabraniSistem.getId() + "," + plasman.getBrojBodova() + ")");
                odabraniSistem.plasmani.add(plasman);
                plasmani.add(plasman);
            }
            ((Stage) (tfBodoviPlasmana.getScene().getWindow())).close();
        }

    }

    @FXML
    private void obrisiPlasman(ActionEvent event) {
        ePlasman plasman = twKonkSistem.getSelectionModel().getSelectedItem();
        DB db = DB.konektujSe();
        db.dmlNaredba("delete from plasmani_bodova where id_bod_sis= " + odabraniSistem.getId() + " and id_plasmana= " + plasman.getId());
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
