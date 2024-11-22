package repository;

import jdbc.DBConfig;
import services.AuditService;
import user.Utilizator;

import java.sql.*;

import static services.AuditService.getAuditService;

public class UtilizatorRepository {
    private static UtilizatorRepository utilizatorRepository;

    public static UtilizatorRepository getUtilizatorRepository(){
        if(utilizatorRepository == null)
            utilizatorRepository = new UtilizatorRepository();
        return utilizatorRepository;
    }
    public void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS utilizator" +
                "(id int PRIMARY KEY AUTO_INCREMENT, nume_utilizator VARCHAR(100), parola VARCHAR(100)," +
                "abonament BOOL, zile_ramase_abon int)";
        Connection connection = DBConfig.getDatabaseConnection();
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            getAuditService().log("Created Utilizator table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayUtilizator() {
        String query = "SELECT * FROM utilizator";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Show all users");
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("Nume utilizator: "+resultSet.getString(2));
                System.out.println("Parola: "+resultSet.getString(3));
                System.out.println("Abonament: "+resultSet.getString(4));
                System.out.println("Zile ramase abonament: "+resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUtilizator(Utilizator util){
        String insertAppSql = "INSERT INTO utilizator(nume_utilizator, parola, abonament, zile_ramase_abon)" +
                "VALUES(?, ?, ?, ?)";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Insert a user");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAppSql);
            preparedStatement.setString(1, util.getNumeUtil());
            preparedStatement.setString(2, util.getParola());
            preparedStatement.setBoolean(3, util.isAbonament());
            preparedStatement.setInt(4, util.getZileRamaseAbon());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Utilizator getUtilizatorById(int id) {
        String selectSql = "SELECT * FROM utilizator WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Obtain one user");
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

    public void updateUtilizator(Utilizator util, int id) {
        String updateq = "UPDATE utilizator SET nume_utilizator=?, parola=?" +
                ", abonament=?, zile_ramase_abon=? WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Update a user");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateq);
            preparedStatement.setString(1, util.getNumeUtil());
            preparedStatement.setString(2, util.getParola());
            preparedStatement.setBoolean(3,util.isAbonament());
            preparedStatement.setInt(4, util.getZileRamaseAbon());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUtilizator(int id){
        String query = "DELETE FROM utilizator where id=?";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Delete a user");
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Utilizator mapToApp(ResultSet resultSet) throws SQLException{
        if (resultSet.next()) {
            return new Utilizator(resultSet.getString(1),
                    resultSet.getString(2));
        }
        return null;
    }
}
