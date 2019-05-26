package Model;

import java.util.ArrayList;
import baza.DB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dimitrije
 * @version 1.0
 * @created 24-May-2019 8:45:18 PM
 */
public class eKatalogPodataka {

    public ArrayList<eSistemBodovanja> sistemiBodovanja;
    public ArrayList<eSistemKazni> sistemiKazni;
    public ArrayList<eSistemNagrada> sistemiNagrada;
    public ArrayList<eDokument> dokumentacija;

    public eKatalogPodataka(String objekatPosaljioc) {
        if (objekatPosaljioc.equalsIgnoreCase("cpartialbodovi")) {
            sistemiBodovanja = new ArrayList();
            this.povuciIzBazeBodove();
        }else if(objekatPosaljioc.equalsIgnoreCase("cpartialnagrade")){
            sistemiNagrada = new ArrayList();
            this.povuciIzBazeNagrade();
        }else if(objekatPosaljioc.equalsIgnoreCase("cpartialkazne")){
            sistemiKazni = new ArrayList();
            this.povuciIzBazeKazne();
        }else if(objekatPosaljioc.equalsIgnoreCase("zpartialdokumenta")){
            dokumentacija = new ArrayList();
            this.povuciIzBazeDokumenta();
        }
    }

    private void povuciIzBazeBodove() {
        try {
            DB db = DB.konektujSe();
            ResultSet rs = db.selectUpit("select * from sistem_bodova");
            while (rs.next()) {
                sistemiBodovanja.add(new eSistemBodovanja(rs.getString("naziv_bod_sis"), rs.getInt("id_bod_sis")));
            }
        } catch (SQLException sQLException) {
            System.out.println(sQLException);
        }

        try {
            DB db = DB.konektujSe();
            for (eSistemBodovanja sistem : sistemiBodovanja) {
                ResultSet rs = db.selectUpit("select * from plasmani_bodova where id_bod_sis = " + sistem.getId());
                while (rs.next()) {
                    sistem.dodajElement(new ePlasman(rs.getInt("broj_bod"), 0, rs.getInt("id_plasmana")));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void povuciIzBazeNagrade() {
        try {
            DB db = DB.konektujSe();
            ResultSet rs = db.selectUpit("select * from sistem_nagrada");
            while (rs.next()) {
                sistemiNagrada.add(new eSistemNagrada(rs.getString("naziv_nag_sis"), rs.getInt("id_nag_sis")));
            }
        } catch (SQLException sQLException) {
            System.out.println(sQLException);
        }

        try {
            DB db = DB.konektujSe();
            for (eSistemNagrada sistem : sistemiNagrada) {
                ResultSet rs = db.selectUpit("select * from plasmani_nagrada where id_nag_sis = " + sistem.getId());
                while (rs.next()) {
                    sistem.dodajElement(new ePlasman(0, rs.getInt("iznos"), rs.getInt("id_plasmana")));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void povuciIzBazeKazne() {
        try {
            DB db = DB.konektujSe();
            ResultSet rs = db.selectUpit("select * from sistem_kazni");
            while (rs.next()) {
                sistemiKazni.add(new eSistemKazni(rs.getString("naz_sis_kaz"), rs.getInt("id_sis_kaz")));
            }
        } catch (SQLException sQLException) {
            System.out.println(sQLException);
        }

        try {
            DB db = DB.konektujSe();
            for (eSistemKazni sistem : sistemiKazni) {
                ResultSet rs = db.selectUpit("select * from kazne_dokumenta where id_sis_kaz = " + sistem.getId());
                while (rs.next()) {
                    eClan clan = new eClan(rs.getInt("id_clana"));
                    sistem.dodajElement(clan);
                    ResultSet rez = db.selectUpit("select * from clan where id_clana= "+clan.getId());
                    rez.next();
                    clan.setNaziv(rez.getString("naz_clana"));
                    clan.setUslov(rez.getString("uslov"));
                    clan.setGornjaGranica(rez.getFloat("gor_gran"));
                    clan.setDonjaGranica(rez.getFloat("donja_gran"));
                    clan.setPocetakVazenja(rez.getDate("pocetak_vaz"));
                    clan.setDatumIsteka(rez.getDate("kraj_vaz"));
                    clan.setOpis(rez.getString("opis"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void povuciIzBazeDokumenta() {
        try {
            DB db = DB.konektujSe();
            ResultSet rs = db.selectUpit("select * from dokument");
            while (rs.next()) {
                dokumentacija.add(new eDokument(rs.getNString("naz_dok"), rs.getInt("id_dok")));
            }
        } catch (SQLException sQLException) {
            System.out.println(sQLException);
        }

        try {
            DB db = DB.konektujSe();
            for (eDokument sistem : dokumentacija) {
                ResultSet rs = db.selectUpit("select * from clanovi_dokumenta where id_dok = " + sistem.getId());
                while (rs.next()) {
                    eClan clan = new eClan(rs.getInt("id_clana"));
                    sistem.dodajElement(clan);
                    ResultSet rez = db.selectUpit("select * from clan where id_clana= "+clan.getId());
                    rez.next();
                    clan.setNaziv(rez.getNString("naz_clana"));
                    clan.setUslov(rez.getNString("uslov"));
                    clan.setGornjaGranica(rez.getFloat("gor_gran"));
                    clan.setDonjaGranica(rez.getFloat("donja_gran"));
                    clan.setPocetakVazenja(rez.getDate("pocetak_vaz"));
                    clan.setDatumIsteka(rez.getDate("kraj_vaz"));
                    clan.setOpis(rez.getNString("opis"));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
