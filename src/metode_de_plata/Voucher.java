package metode_de_plata;

import java.time.LocalDate;

public class Voucher extends MetodaGenerica {
    private String nume;
    private LocalDate data_exp;

    public Voucher() {}

    public Voucher(double sold, String nume, LocalDate data_exp) {
        this.sold = sold;
        this.nume = nume;
        this.data_exp = data_exp;
        this.id = idCounter++;
    }

    public String getNume() {
        return nume;
    }

    public LocalDate getData_exp() {
        return data_exp;
    }

    @Override
    public String toString() {
        return "Nume voucher: " + nume +
                ", Data expirarii: " + data_exp +
                ", Sold: " + sold;
    }
}
