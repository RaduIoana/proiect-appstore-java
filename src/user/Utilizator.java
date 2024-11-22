package user;

import aplicatii.Aplicatie;
import aplicatii.AplicatieAbon;
import aplicatii.AplicatiePlatita;
import metode_de_plata.Card;
import metode_de_plata.Voucher;

import java.util.Scanner;
import java.util.ArrayList;

public class Utilizator implements Comparable<Utilizator> {
    private String numeUtil;
    private String parola;
    private ArrayList<Aplicatie> aplicatiiInstalate;
    private Portofel wallet = new Portofel();
    private boolean abonament = false;
    private int zileRamaseAbon = 0;

    public Utilizator() {}

    public Utilizator(String numeUtil, String parola) {
        this.numeUtil = numeUtil;
        this.parola = parola;
        this.aplicatiiInstalate = new ArrayList<>();
        this.abonament = false;
        this.zileRamaseAbon = 0;
    }

    @Override
    public int compareTo(Utilizator o) {
        return numeUtil.compareTo(o.numeUtil);
    }

    public void arataAplInst()
    {
        for(Aplicatie apl : aplicatiiInstalate)
            System.out.println(apl.toString());
    }

    public ArrayList<Aplicatie> getAplicatiiInstalate() {
        return aplicatiiInstalate;
    }

    public Portofel getWallet() {
        return wallet;
    }

    public String getParola() {
        return parola;
    }

    public String getNumeUtil() {
        return numeUtil;
    }

    public boolean isAbonament() {
        return abonament;
    }

    public int getZileRamaseAbon() {
        return zileRamaseAbon;
    }

    public void verificareAbonament()
    {
        if(abonament)
            System.out.println("Aveti " + this.zileRamaseAbon +
                    " zile ramase pana la expirarea abonamentului. Doriti sa il reinnoiti? (Y/N)");
        else
            System.out.println("Nu aveti abonament. Doriti sa cumparati un abonament? (Y/N)");
        Scanner keyboard = new Scanner(System.in);
        StringBuilder c = new StringBuilder();
        c.append(keyboard.nextLine());
        if(c.charAt(0) == 'Y')
        {
            System.out.println("Selectati nr de luni de adaugat. 1 luna = 25 lei\nLa fiecare luna adaugata aveti un discount de 5% din suma finala, pana la 50%.");
            double nr_luni = keyboard.nextDouble();
            keyboard.nextLine();
            double suma_finala;
            if(nr_luni == 1.0) suma_finala = 25;
            else
            {
                suma_finala = nr_luni * 25;
                suma_finala = suma_finala - Math.min(suma_finala / 2, nr_luni * 5 / 100);
            }
            System.out.println("Suma finala este: " + suma_finala + "\nDoriti sa faceti plata? (Y/N)");
            c.insert(0,keyboard.nextLine());
            if(c.charAt(0) == 'Y')
            {
                try
                {
                    if (suma_finala > wallet.getSoldPortofel()) throw new ArithmeticException();
                    wallet.setSoldPortofel(wallet.getSoldPortofel() - suma_finala);
                    this.abonament = true;
                    this.zileRamaseAbon += (int) (30 * nr_luni);
                    System.out.println("Soldul ramas in cont este: " + wallet.getSoldPortofel()
                            + "\nAcum aveti " + this.zileRamaseAbon + " zile ramase din abonament.");
                }
                catch (ArithmeticException e)
                {
                    System.out.println("Suma finala depaseste soldul contului. Tranzactie nereusita.");
                }
            }
        }
    }

    public void adaugaBani()
    {
        StringBuilder c1 = new StringBuilder();
        c1.append("Y");
        System.out.println("Introduceti parola: ");
        Scanner keyboard = new Scanner(System.in);
        StringBuilder inputpass = new StringBuilder();
        inputpass.append(keyboard.nextLine());

        while (c1.charAt(0) == 'Y')
        {
            // aici, as putea sa fac o metoda withdrawCardMoney
            // in loc sa pun aceleasi 30 linii de cod de 3 ori :))
            if (inputpass.substring(0).equals(parola))
            {
                System.out.println("Din ce metoda doriti sa adaugati bani?");
                wallet.arataInfMet();
                int alegere = keyboard.nextInt();
                keyboard.nextLine();

                if(wallet.getMet(alegere) instanceof Card card_ales)
                {
                    StringBuilder cardpass = new StringBuilder();
                    if(card_ales.getYesPass())
                    {
                        System.out.println("Introduceti parola cardului: ");
                        cardpass.append(keyboard.nextLine());
                    }

                    if(cardpass.substring(0).equals(card_ales.getParola())
                            && card_ales.getYesPass())
                    {
                        String result = retragereBaniCard(card_ales,alegere);
                        c1.insert(0,result);
                    }
                    else if(!card_ales.getYesPass())
                    {
                        String result = retragereBaniCard(card_ales,alegere);
                        c1.insert(0,result);
                    }
                    else
                        System.out.println("Parola incorecta.");

                }
                else if(wallet.getMet(alegere) instanceof Voucher voucher_ales)
                {
                    System.out.println("Pe voucher sunt " + voucher_ales.getSold()
                            + " lei. Doriti sa ii adaugati in cont?(Y/N)");
                    c1.insert(0,keyboard.nextLine());

                    if(c1.charAt(0) == 'Y') {
                        wallet.setSoldPortofel(wallet.getSoldPortofel() + voucher_ales.getSold());
                        wallet.getMet(alegere).setSold(0);
                        System.out.println("Doriti sa mai adaugati bani in portofel?(Y/N)");
                        c1.insert(0, keyboard.nextLine());
                    }
                }
            }
            else
            {
                System.out.println("Parola incorecta. Incercati din nou?(Y/N)");
                c1.insert(0,keyboard.nextLine());
                if (c1.charAt(0) == 'Y')
                    inputpass.append(keyboard.nextLine());
            }
        }
    }

    private String retragereBaniCard(Card card_ales, int alegere){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Sold curent: "+ card_ales.getSold() +
                " Introduceti suma de adaugat: ");
        double suma = keyboard.nextDouble();
        keyboard.nextLine();

        double sold = card_ales.getSold();
        try
        {
            if(suma > sold) throw new ArithmeticException();
            wallet.setSoldPortofel(wallet.getSoldPortofel() + suma);
            wallet.getMet(alegere).setSold(sold - suma);

            System.out.println("Au fost adaugati " + suma
                    + " lei in portofel, iar soldul total este acum:" + wallet.getSoldPortofel());
        }
        catch (Exception e)
        {
            System.out.println("Suma de adaugat depaseste suma de bani de pe card. Tranzactie nereusita.");
            System.out.println("Incercati din nou? (Y/N)");
            return keyboard.nextLine();
        }
        System.out.println("Doriti sa mai adaugati bani in portofel?(Y/N)");
        return keyboard.nextLine();
    }

    public void instalareAplicatie(ArrayList<Aplicatie> ls_apl, int id_apl)
    {
        Aplicatie app = ls_apl.stream().filter(obj -> obj.getId() == id_apl)
                .findFirst().orElse(null);
        if(app == null)
        {
            System.out.println("Aplicatia ceruta nu exista.");
            return;
        }
        System.out.println(app.toString());
        System.out.println("Doriti sa instalati?(Y/N)");
        StringBuilder c = new StringBuilder();
        Scanner keyboard = new Scanner(System.in);
        c.append(keyboard.nextLine());

        if(c.charAt(0) == 'Y')
        {
            if(app.isFree())
            {
                System.out.println("Aplicatia s-a instalat cu succes.");
                aplicatiiInstalate.add(app);
                return;
            }
            if(abonament)
            {
                if(app instanceof AplicatiePlatita apl)
                    System.out.println("Datorita abonamentului, aveti o reducere de " + apl.getDiscount() + "%");
                if(app instanceof AplicatieAbon apl)
                    System.out.println("Datorita abonamentului, aveti o reducere de " + apl.getDiscAbon()  + "%" +
                            " la abonarea pentru versiunea premium a aplicatiei");
            }

            double pret_final = 0;
            if(app instanceof AplicatieAbon apl)
            {
                System.out.println("Introduceti nr de luni de adaugat: ");
                double nr_luni = keyboard.nextDouble();
                keyboard.nextLine();

                if(abonament)
                    pret_final = apl.getPret() + apl.getPreta(nr_luni);
                else
                    pret_final = apl.getPretNodisc() + apl.getPretaNodisc(nr_luni);
                System.out.println("Pret final: " + pret_final);
            }
            if (app instanceof AplicatiePlatita apl)
            {
                if(abonament)
                    pret_final = apl.getPret();
                else
                    pret_final = apl.getPretNodisc();
                System.out.println("Pret final: " + pret_final);
            }

            System.out.println("Doriti sa faceti plata?");
            c.insert(0,keyboard.nextLine());

            if(c.charAt(0) == 'Y')
            {
                if(pret_final <= getWallet().getSoldPortofel())
                {
                    getWallet().setSoldPortofel(getWallet().getSoldPortofel() - pret_final);
                    aplicatiiInstalate.add(app);
                    System.out.println("Aplicatia s-a instalat cu succes.");
                }
                else
                {
                    System.out.println("Suma finala depaseste soldul contului. Tranzactie nereusita.");
                }
            }
        }
    }

    public void dezinstalareAplicatie()
    {
        if(aplicatiiInstalate.isEmpty())
        {
            System.out.println("Nu aveti instalate aplicatii.");
            return;
        }
        System.out.println("Ce aplicatie doriti sa dezinstalati?");

        Scanner keyboard = new Scanner(System.in);
        int alegere = keyboard.nextInt();

        aplicatiiInstalate.removeIf(obj -> obj.getId() == alegere);
        System.out.println("Aplicatia a fost dezinstalata cu succes.");
    }
}
