package Model;

import java.util.Date;

/**
 * @author Dimitrije
 * @version 1.0
 * @created 24-May-2019 8:45:31 PM
 */
public class eClan extends absElement {

    private String naziv;
    private String uslov;
    private double gornjaGranica;
    private double donjaGranica;
    private Date pocetakVazenja;
    private Date datumIsteka;
    private String opis;

    public eClan(int id, String naziv , String uslov, double gornjaGranica, double donjaGranica, Date pocetakVazenja, Date datumIsteka, String opis) {
        super(id);
        this.naziv = naziv;
        this.uslov = uslov;
        this.gornjaGranica = gornjaGranica;
        this.donjaGranica = donjaGranica;
        this.pocetakVazenja = pocetakVazenja;
        this.datumIsteka = datumIsteka;
        this.opis = opis;
    }

    public eClan(int id) {
        super(id);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }


    public String getUslov() {
        return uslov;
    }

    public void setUslov(String uslov) {
        this.uslov = uslov;
    }

    public double getGornjaGranica() {
        return gornjaGranica;
    }

    public void setGornjaGranica(double gornjaGranica) {
        this.gornjaGranica = gornjaGranica;
    }

    public double getDonjaGranica() {
        return donjaGranica;
    }

    public void setDonjaGranica(double donjaGranica) {
        this.donjaGranica = donjaGranica;
    }

    public Date getPocetakVazenja() {
        return pocetakVazenja;
    }

    public void setPocetakVazenja(Date pocetakVazenja) {
        this.pocetakVazenja = pocetakVazenja;
    }

    public Date getDatumIsteka() {
        return datumIsteka;
    }

    public void setDatumIsteka(Date datumIsteka) {
        this.datumIsteka = datumIsteka;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
