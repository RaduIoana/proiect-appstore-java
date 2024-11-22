package aplicatii;

import java.sql.Date;
import java.time.LocalDate;

public class Aplicatie {
    protected String nume;
    protected String versiune;
    protected String categorie;
    protected LocalDate data_lansare;
    protected int id;
    protected static int idCounter = 0;
    protected boolean free;

    public Aplicatie() {
        System.out.println("S-a creat o aplicatie vida.");
    }
    public Aplicatie(String nume, String versiune, String categorie, LocalDate data_lansare,
                     boolean free)
    {
        this.nume = nume;
        this.categorie = categorie;
        this. versiune = versiune;
        this.data_lansare = data_lansare;
        this.id = idCounter++;
        this.free = free;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " Aplicatie: " + nume +
                ", versiune: " + versiune +
                ", categorie: " + categorie +
                ", data lansare: " + data_lansare;
    }

    public int getId() {
        return id;
    }

    public boolean isFree() {
        return free;
    }
    public double getPret(){
        return  0;
    }
    public String getNume() {
        return nume;
    }

    public String getVersiune() {
        return versiune;
    }

    public LocalDate getData_lansare() {
        return data_lansare;
    }

    public String getCategorie() {
        return categorie;
    }
}
