package baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DB {

    private static Connection konekcija;
    private static Statement naredba;
    private static DB db = new DB();

    private DB() {

    }

    public static DB konektujSe() {

        if (konekcija == null) {
            try {
                konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/asocijacija_sudija","dimitrije","1234");
                naredba = konekcija.createStatement();
            } catch (Exception e) {
                System.out.println("Konekcija nije uspela: " + e);
                return null;
            }
        }
        return db;
    }

    public ResultSet selectUpit(String sqlIzraz) {
        try {
            return naredba.executeQuery(sqlIzraz);
        } catch (Exception e) {
            System.out.println("Select nije uspeo: " + e);
            return null;
        }

    }

    public boolean dmlNaredba(String sqlIzraz) {
        try {
            naredba.executeUpdate(sqlIzraz);
            return true;
        } catch (Exception e) {
            System.out.println("Naredba nije uspela: " + e);
            return false;
        }
    }
    
    public PreparedStatement getPRPSTM(String str){
        try {
            return konekcija.prepareStatement(str, Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }


}