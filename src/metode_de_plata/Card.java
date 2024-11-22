package metode_de_plata;

import java.time.LocalDate;

public class Card extends MetodaGenerica {
    private String numar_card;
    private LocalDate data_exp;
    private String parola;
    private boolean yesPass;

    public Card() {}

    public Card(double sold, LocalDate data_exp,String numar_card, String parola, boolean yp) {
        this.sold = sold;
        this.data_exp = data_exp;
        this.numar_card = numar_card;
        this.parola = parola;
        this.yesPass = yp;
        this.id = idCounter++;
    }

    @Override
    public String toString() {
        return "Numar card: " + numar_card +
                ", Data expirarii: " + data_exp +
                ", Sold: " + sold;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public boolean getYesPass() {
        return yesPass;
    }

    public void setYesPass(boolean yesPass) {
        this.yesPass = yesPass;
    }

    public String getNumar_card() {
        return numar_card;
    }

    public LocalDate getData_exp() {
        return data_exp;
    }
}
