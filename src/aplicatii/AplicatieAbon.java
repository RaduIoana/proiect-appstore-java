package aplicatii;

import java.time.LocalDate;

public class AplicatieAbon extends AplicatiePlatita {
    private double pretAbon;
    private double discAbon;

    public AplicatieAbon() {
        super();
        this.pretAbon = 0;
        this.discAbon = 0;
    }

    public AplicatieAbon(String nume, String versiune, String categorie, LocalDate data_lansare,
                         boolean free, double pret, double discount, double pretAbon, double discAbon) {
        super(nume, versiune, categorie, data_lansare, free, pret, discount);
        this.pretAbon = pretAbon;
        this.discAbon = discAbon;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " Aplicatie: " + nume +
                ", versiune: " + versiune +
                ", categorie: " + categorie +
                ", data lansare: " + data_lansare +
                ", pret: " + pret +
                ", discount: " + discount +
                ", pret abonament: " + pretAbon +
                ", discount abonament: " + discAbon;
    }

    public double getPretaNodisc(double nr_luni)
    {
        return nr_luni * pretAbon;
    }
    public double getPreta(double nr_luni)
    {
        if(nr_luni==1)return pretAbon * nr_luni;
        return pretAbon * nr_luni - (pretAbon * nr_luni * discAbon / 100);
    }
    public double getDiscAbon() {
        return discAbon;
    }

    public void setPretAbon(double pret_abon) {
        this.pretAbon = pret_abon;
    }

    public void setDiscAbon(double disc_abon) {
        this.discAbon = disc_abon;
    }
}
