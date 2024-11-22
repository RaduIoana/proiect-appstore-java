package repository;

import jdbc.DBConfig;
import metode_de_plata.Card;
import services.AuditService;

import java.sql.*;

import static services.AuditService.getAuditService;

public class CardRepository {
    private static CardRepository cardRepository;

    public static CardRepository getCardRepository(){
        if(cardRepository == null)
            cardRepository = new CardRepository();
        return cardRepository;
    }
    public void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS card" +
                "(id int, numar_card VARCHAR(50), data_exp DATE,"+
                " parola VARCHAR(100), yespass BOOL," +
                " PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES metoda_generica(id) ON DELETE CASCADE)";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Created card table");

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayCard() {
        String query = "SELECT * FROM card";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Show all cards");


        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("Numar card: "+resultSet.getString(2));
                System.out.println("Data expirarii: "+resultSet.getString(3));
                System.out.println("Parola: "+resultSet.getString(4));
                System.out.println("Yespass: "+resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCard(int id, String numar_card, Date dataExp, String parola, boolean yespass){
        String insertCardSql = "INSERT INTO card(id, numar_card, data_exp, parola, yespass)" +
                "VALUES(?, ?, ?, ?, ?)";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Insert a card");


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCardSql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, numar_card);
            preparedStatement.setDate(3, dataExp);
            preparedStatement.setString(4, parola);
            preparedStatement.setBoolean(5, yespass);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Card getCardById(int id) {
        String selectSql = "SELECT * FROM card WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Obtain a card");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToApp(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCard(Card c, int id) {
        String updateq = "UPDATE card SET numar_card=?, data_exp=?, parola=?, yespass=? WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Update a card");

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateq);
            preparedStatement.setString(1, c.getNumar_card());
            java.sql.Date data = new java.sql.Date(c.getData_exp().getYear(),
                    c.getData_exp().getMonthValue(), c.getData_exp().getDayOfMonth());
            preparedStatement.setDate(2, data);
            preparedStatement.setString(3, c.getParola());
            preparedStatement.setBoolean(4, c.getYesPass());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(int id){
        String query = "DELETE FROM card where id=?";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Delete a card");
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected Card mapToApp(ResultSet resultSet) throws SQLException{
        if (resultSet.next()) {
            return new Card(resultSet.getInt(1),
                    resultSet.getDate(3).toLocalDate(),
                    resultSet.getString(2),
                    resultSet.getString(4),
                    resultSet.getBoolean(5));
        }
        return null;
    }
}

