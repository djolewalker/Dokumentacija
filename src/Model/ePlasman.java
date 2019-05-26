package Model;

/**
 * @author Dimitrije
 * @version 1.0
 * @created 24-May-2019 8:45:30 PM
 */
public class ePlasman extends absElement {

    private int brojBodova;
    private int iznos;

    public ePlasman(int brojBodova, int iznos, int id) {
        super(id);
        this.brojBodova = brojBodova;
        this.iznos = iznos;
    }
    
    public int getBrojBodova() {
        return brojBodova;
    }

    public void setBrojBodova(int brojBodova) {
        this.brojBodova = brojBodova;
    }

    public int getIznos() {
        return iznos;
    }

    public void setIznos(int iznos) {
        this.iznos = iznos;
    }
    
    

}
