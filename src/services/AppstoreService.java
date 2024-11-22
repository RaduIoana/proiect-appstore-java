package services;

import aplicatii.Aplicatie;
import user.Utilizator;

import java.util.*;

public class AppstoreService {
    public static ArrayList<Utilizator> lista_util = new ArrayList<>();
    public static ArrayList<Aplicatie> aplicatii_magazin = new ArrayList<>();
    public static Set<String> categorii_magazin = new HashSet<>();

    public static void adaugare_apl(Aplicatie app) {
        aplicatii_magazin.add(app);
        categorii_magazin.add(app.getCategorie());
    }

    public static void creareCont() {
        System.out.println("Introduceti numele de utilizator: ");
        Scanner keyboard = new Scanner(System.in);
        String nume = keyboard.nextLine();

        System.out.println("Introduceti parola: ");
        String parola = keyboard.nextLine();

        Utilizator new_util = new Utilizator(nume, parola);
        lista_util.add(new_util);
        lista_util.sort(Utilizator::compareTo);
    }

    public static Utilizator logare() {
        System.out.println("Introduceti numele de utilizator: ");
        Scanner keyboard = new Scanner(System.in);
        String nume = keyboard.nextLine();

        Utilizator dummy = new Utilizator(nume, "");
        int i = Collections.binarySearch(lista_util, dummy, Utilizator::compareTo);
        StringBuilder c = new StringBuilder();
        c.append("Y");

        if (i >= 0) {
            while (c.charAt(0) == 'Y') {
                System.out.println("Introduceti parola: ");
                String parola = keyboard.nextLine();
                if (parola.equals(lista_util.get(i).getParola())) {

                    return lista_util.get(i);
                } else {
                    System.out.println("Parola incorecta. Incercati din nou?");
                    c.insert(0, keyboard.nextLine());
                }
            }
        } else
            System.out.println("Acest cont nu exista.");
        return null;
    }

    public static void vizualizare_metode(Utilizator u) {
        u.getWallet().arataInfMet();
    }

    public static void verifica_abonament(Utilizator u) {
        u.verificareAbonament();
    }

    public static void adaugare_bani_cont(Utilizator u) {
        u.adaugaBani();
    }

    public static void adauga_met_incont(Utilizator u) {
        u.getWallet().adaugareMetoda();
    }

    public static void apl_inst_user(Utilizator u) {
        u.arataAplInst();
    }

    public static void instaleaza_aplicatie(Utilizator u) {
        AppstoreService.filtrare_aplicatii();
        System.out.println("Introduceti id-ul aplicatiei dorite: ");
        Scanner keyboard = new Scanner(System.in);
        int id = keyboard.nextInt();
        keyboard.nextLine();
        u.instalareAplicatie(aplicatii_magazin, id);
    }

    public static void dezinstaleaza_aplicatie(Utilizator u) {
        u.arataAplInst();
        u.dezinstalareAplicatie();
    }

    public static void filtrare_aplicatii() {
        System.out.println("Selectati o optiune de filtrare:\n" +
                "1: A-Z\n" + "2: Z-A\n" +
                "3: crescator dupa pret\n" + "4: descrescator dupa pret\n" +
                "5: dupa categorie");
        Scanner keyboard = new Scanner(System.in);
        int alegere = keyboard.nextInt();
        keyboard.nextLine();
        switch (alegere) {
            case 1:
                aplicatii_magazin.sort(Comparator.comparing(Aplicatie::getNume));
                for (Aplicatie app : aplicatii_magazin)
                    System.out.println(app.toString());
                break;
            case 2:
                aplicatii_magazin.sort(Comparator.comparing(Aplicatie::getNume).reversed());
                for (Aplicatie app : aplicatii_magazin)
                    System.out.println(app.toString());
                break;
            case 3:
                aplicatii_magazin.sort(Comparator.comparing(Aplicatie::getPret));
                for (Aplicatie app : aplicatii_magazin)
                    System.out.println(app.toString());
                break;
            case 4:
                aplicatii_magazin.sort(Comparator.comparing(Aplicatie::getPret).reversed());
                for (Aplicatie app : aplicatii_magazin)
                    System.out.println(app.toString());
                break;
            case 5:
                System.out.println("Alegeti o categorie:");
                for (String cat : categorii_magazin)
                    System.out.println(cat);
                String categ = keyboard.nextLine();

                if (categorii_magazin.contains(categ)) {
                    List<Aplicatie> filtrare = aplicatii_magazin.stream().filter(apl -> apl.getCategorie()
                            .equals(categ)).toList();
                    for (Aplicatie apl : filtrare)
                        System.out.println(apl.toString());
                }
                break;
        }
    }
}
