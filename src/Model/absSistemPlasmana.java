package Model;

import java.util.ArrayList;

/**
 * @author Dimitrije
 * @version 1.0
 * @created 24-May-2019 8:45:26 PM
 */
public abstract class absSistemPlasmana implements ISistem {

    public String naziv;
    public int id;
    public ArrayList<ePlasman> plasmani;

    public absSistemPlasmana(String naziv, int id) {
        this.naziv = naziv;
        this.id = id;
        this.plasmani = new ArrayList();
    }

    @Override
    public void dodajElement(absElement element) {
        plasmani.add((ePlasman)element);
    }

    @Override
    public void obrisiElement(absElement element) {
        plasmani.remove((ePlasman)element);
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
