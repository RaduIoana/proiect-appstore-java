package user;

import metode_de_plata.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Portofel {
    private double soldPortofel;
    private ArrayList<MetodaGenerica> metode;

    public Portofel()
    {
        this.soldPortofel = 0;
        this.metode = new ArrayList<>();
    }

    public MetodaGenerica getMet(int i)
    {
        return metode.get(i);
    }

    public void adaugaMetoda(MetodaGenerica met)
    {
        this.metode.add(met);
    }

    public void arataInfMet()
    {
        for (MetodaGenerica met : metode)
            System.out.println(metode.indexOf(met) + ": " + met.toString());
    }

    public void adaugareMetoda()
    {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Ce metoda de plata doriti sa adaugati? (card/voucher)");
        String alegere = keyboard.nextLine().toLowerCase();
        if(alegere.equals("card"))
        {
            System.out.println("Introduceti soldul cardului: ");
            int sold = keyboard.nextInt();
            keyboard.nextLine();

            System.out.println("Introduceti nr card: ");
            String nr = keyboard.nextLine();

            System.out.println("Introduceti data exp: ");
            System.out.println("An: ");
            int year = keyboard.nextInt();
            System.out.println("Luna: ");
            int month = keyboard.nextInt();
            keyboard.nextLine();
            LocalDate data_exp = LocalDate.of(year,month,1);

            boolean yesPass = false;
            System.out.println("Doriti sa setati o parola pentru card? (Y/N)");
            String c = keyboard.nextLine();
            String pass = "";
            if(c.charAt(0) == 'Y')
            {
                yesPass = true;
                System.out.println("Introduceti o parola pentru card: ");
                pass = keyboard.nextLine();
            }

            MetodaGenerica card1 = new Card(sold, data_exp, nr, pass, yesPass);
            card1.setId(metode.size()+1);
            metode.add(card1);
        }
        else if (alegere.equals("voucher")) {
            System.out.println("Introduceti soldul voucherului: ");
            int sold = keyboard.nextInt();
            keyboard.nextLine();

            System.out.println("Introduceti nume voucher: ");
            String nume = keyboard.nextLine();

            System.out.println("Introduceti data exp: ");
            System.out.println("An: ");
            int year = keyboard.nextInt();
            System.out.println("Luna: ");
            int month = keyboard.nextInt();
            keyboard.nextLine();
            LocalDate data_exp = LocalDate.of(year,month,1);

            MetodaGenerica voucher1 = new Voucher(sold, nume, data_exp);
            voucher1.setId(metode.size()+1);
            metode.add(voucher1);
        }
        else
        {
            System.out.println("Alegere invalida!");
        }
    }

    /*public void arata_sold_metoda()
    {
        arata_inf_met();
        System.out.println("Selectati metoda a carui sold doriti sa-l verificati: ");
        Scanner keyboard = new Scanner(System.in);
        int x = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println("Soldul tau este: " + metode.get(x).getSold());
    }*/

    public double getSoldPortofel() {
        return soldPortofel;
    }

    public void setSoldPortofel(double soldPortofel) {
        this.soldPortofel = soldPortofel;
    }
}
