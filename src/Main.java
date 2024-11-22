import aplicatii.*;
import metode_de_plata.*;
import java.time.LocalDate;
import jdbc.DBConfig;
import repository.*;
import services.AppstoreService;
import user.Utilizator;

public class Main {

    public static void main(String[] args) {
        //dbconfig: copy paste
        //repository, unul pt fiecare clasa
        //service cu logica
        //serviciu audit
        DBConfig.getDatabaseConnection();
        AplicatieRepository appRepo = AplicatieRepository.getAplicatieRepository();
        appRepo.createTable();

        MetGenericaRepository metRepo = MetGenericaRepository.getMetGenericaRepository();
        metRepo.createTable();

        CardRepository cardRepo = CardRepository.getCardRepository();
        cardRepo.createTable();

        VoucherRepository vouchRepo = VoucherRepository.getVoucherRepository();
        vouchRepo.createTable();

        UtilizatorRepository utilRepo = UtilizatorRepository.getUtilizatorRepository();
        utilRepo.createTable();

        MetodaGenerica v = new Voucher(100,"voucher1",LocalDate.now());
        MetodaGenerica m = new Card(300, LocalDate.now(),"123 456 789 0","cardpass",true);
        //metRepo.insertMetoda(v);
        //metRepo.insertMetoda(m);

        metRepo.displayMetoda();
        metRepo.updateMetoda(new Voucher(50,"vo", LocalDate.now()),13);
        //System.out.println(metRepo.getMetodaById(13,"VOUCHER").toString());
        //System.out.println(metRepo.getMetodaById(15,"CARD").toString());
        //metRepo.deleteMetoda(14);

        //declarari date
        LocalDate data = LocalDate.of(2020,10,18);
        LocalDate data2 = LocalDate.of(2018,3,31);
        Aplicatie app = new AplicatiePlatita("TextEditor","6.14","office",data,false,25,15);
        Aplicatie app2 = new Aplicatie("Microsoft Word","2.00","office",data2,true);
        Aplicatie app3 = new AplicatieAbon("Zuma premium","1.0","jocuri",data,false,14.99,0,9.99,20);
        Aplicatie app4 = new Aplicatie("Candy crush","3.3","jocuri",data2,true);
        Utilizator u = new Utilizator("Nume","parola");
        Utilizator u2 = new Utilizator("TheBestUser","appstorepa55");

        //appRepo.insertAplicatie(app2);
        appRepo.displayAplicatie();
        appRepo.getAplicatieById(2);
        appRepo.updateAplicatie(app4,2);
        System.out.println(appRepo.getAplicatieById(2).toString());
        //appRepo.deleteAplicatie(1);

        //utilRepo.insertUtilizator(u);
        //utilRepo.insertUtilizator(u2);
        utilRepo.displayUtilizator();
        utilRepo.getUtilizatorById(1);
        //utilRepo.updateUtilizator(new Utilizator("TheBestUser", "badpa55"), 2);
        //utilRepo.getUtilizatorById(2);
        //utilRepo.deleteUtilizator(2);

        AppstoreService.lista_util.add(u);
        AppstoreService.lista_util.add(u2);
        AppstoreService.adaugare_apl(app);
        AppstoreService.adaugare_apl(app2);
        AppstoreService.adaugare_apl(app3);
        AppstoreService.adaugare_apl(app4);

        for(Aplicatie appl : AppstoreService.aplicatii_magazin)
            System.out.println(appl.toString());

        AppstoreService.creareCont();
        Utilizator u3 = AppstoreService.lista_util.getLast(); //ultimul user creat

        //System.out.println(apl.nume);

        Utilizator w = AppstoreService.logare();
        w.getWallet().adaugaMetoda(v);
        w.getWallet().adaugaMetoda(m);

        AppstoreService.adauga_met_incont(w);
        AppstoreService.vizualizare_metode(w);

        AppstoreService.adaugare_bani_cont(w);
        AppstoreService.verifica_abonament(w);
        AppstoreService.filtrare_aplicatii();
        AppstoreService.instaleaza_aplicatie(w);
        AppstoreService.instaleaza_aplicatie(w);
        AppstoreService.dezinstaleaza_aplicatie(w);
        AppstoreService.apl_inst_user(w);
    }
}