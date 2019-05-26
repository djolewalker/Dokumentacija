package Model;

import java.util.ArrayList;

/**
 * @author Dimitrije
 * @version 1.0
 * @created 24-May-2019 8:45:27 PM
 */
public abstract class absSistemClanova implements ISistem {

    public String naziv;
    public int id;
    public ArrayList<eClan> clanovi;


    public absSistemClanova(String naziv, int id) {
        this.naziv = naziv;
        this.id = id;
        this.clanovi = new ArrayList();
    }
    

    @Override
    public void dodajElement(absElement element) {
        this.clanovi.add((eClan)element);
    }

    @Override
    public void obrisiElement(absElement element) {
        this.clanovi.remove((eClan)element);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
