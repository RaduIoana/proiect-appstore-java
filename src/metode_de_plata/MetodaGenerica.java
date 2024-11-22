package metode_de_plata;

public abstract class MetodaGenerica {
    protected double sold;
    protected static int idCounter = 1;
    protected int id;

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
