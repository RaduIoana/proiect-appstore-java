package aplicatii;

import java.time.LocalDate;

public class AplicatiePlatita extends Aplicatie {
    protected double pret;
    protected double discount;

    public AplicatiePlatita()
    {
        super();
        this.pret = 0;
        this.discount = 0;
    }

    public AplicatiePlatita(String nume, String versiune, String categorie, LocalDate data_lansare,
                            boolean free, double pret, double discount)
    {
        super(nume, versiune, categorie, data_lansare, free);
        this.pret = pret;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                " Aplicatie: " + nume +
                ", versiune: " + versiune +
                ", categorie: " + categorie +
                ", data lansare: " + data_lansare +
                ", pret: " + pret +
                ", discount: " + discount;
    }

    public double getPret()
    {
        return pret-(pret*discount/100);
    }
    public double getPretNodisc()
    {
        return pret;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

}
