package repository;

import jdbc.DBConfig;
import metode_de_plata.Card;
import metode_de_plata.MetodaGenerica;
import metode_de_plata.Voucher;
import services.AuditService;

import java.sql.*;
import java.util.Objects;

import static services.AuditService.getAuditService;

public class MetGenericaRepository {
    private static MetGenericaRepository metGenericaRepository;

    public static MetGenericaRepository getMetGenericaRepository(){
        if(metGenericaRepository == null)
            metGenericaRepository = new MetGenericaRepository();
        return metGenericaRepository;
    }
    public void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS metoda_generica" +
                "(id int PRIMARY KEY AUTO_INCREMENT, sold double)";
        getAuditService().log("Created method table");

        Connection connection = DBConfig.getDatabaseConnection();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMetoda() {
        String query = "SELECT * FROM metoda_generica";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Show all methods");


        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("Sold: "+resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMetoda(MetodaGenerica m){
        String insertMetodaSql = "INSERT INTO metoda_generica(sold) VALUES(?)";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Insert a method");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertMetodaSql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, m.getSold());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                if (m instanceof Voucher)
                    new VoucherRepository().insertVoucher(generatedKeys.getInt(1), ((Voucher) m).getNume(),
                            Date.valueOf(((Voucher) m).getData_exp()));
                if (m instanceof Card)
                    new CardRepository().insertCard(generatedKeys.getInt(1), ((Card) m).getNumar_card(),
                            Date.valueOf(((Card) m).getData_exp()), ((Card) m).getParola(),
                            ((Card) m).getYesPass());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MetodaGenerica getMetodaById(int id, String tip) {
        String selectSql = "SELECT * FROM metoda_generica WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Obtain a method");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToApp(resultSet, tip, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateMetoda(MetodaGenerica met, int id) {
        String updateq = "UPDATE metoda_generica SET sold=? WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Update a method");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateq);
            preparedStatement.setDouble(1, met.getSold());
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            if(met instanceof Voucher v)
                new VoucherRepository().updateVoucher(v, id);

            if(met instanceof Card c)
                new CardRepository().updateCard(c, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMetoda(int id){
        String query = "DELETE FROM metoda_generica where id=?";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Delete a method");

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private MetodaGenerica mapToApp(ResultSet resultSet, String tip, int id) throws SQLException{
        if (resultSet.next()) {
            if(Objects.equals(tip, "VOUCHER"))
                return new VoucherRepository().getVoucherById(id);
            else if (Objects.equals(tip, "CARD")) {
                return new CardRepository().getCardById(id);
            }
        }
        return null;
    }
}
