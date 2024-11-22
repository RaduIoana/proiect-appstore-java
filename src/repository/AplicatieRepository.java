package repository;

import aplicatii.Aplicatie;
import jdbc.DBConfig;
import services.AuditService;

import java.sql.*;
import static services.AuditService.getAuditService;

public class AplicatieRepository {
    private static AplicatieRepository aplicatieRepository;

    public static AplicatieRepository getAplicatieRepository(){
        if(aplicatieRepository == null)
            aplicatieRepository = new AplicatieRepository();
        return aplicatieRepository;
    }

    public void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS aplicatie" +
                "(id int PRIMARY KEY AUTO_INCREMENT, nume VARCHAR(100), versiune VARCHAR(20)," +
                "categorie VARCHAR(50), data_lansare DATE, free BOOL)";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Created Aplicatie table");
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConfig.closeDatabaseConnection();
    }

    public void displayAplicatie() {
        String query = "SELECT * FROM aplicatie";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Show all apps");
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("Nume: "+resultSet.getString(2));
                System.out.println("Versiune: "+resultSet.getString(3));
                System.out.println("Categorie: "+resultSet.getString(4));
                System.out.println("Data lansare: "+resultSet.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBConfig.closeDatabaseConnection();
    }

    public void insertAplicatie(Aplicatie app){
        String insertAppSql = "INSERT INTO aplicatie(nume, versiune, categorie, data_lansare, free)" +
                "VALUES(?, ?, ?, ?, ?)";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Insert an app");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAppSql);
            preparedStatement.setString(1, app.getNume());
            preparedStatement.setString(2, app.getVersiune());
            preparedStatement.setString(3, app.getCategorie());
            java.sql.Date data = new java.sql.Date(app.getData_lansare().getYear(),
                    app.getData_lansare().getMonthValue(), app.getData_lansare().getDayOfMonth());
            preparedStatement.setDate(4, data);
            preparedStatement.setBoolean(5, app.isFree());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Aplicatie getAplicatieById(int id) {
        String selectSql = "SELECT * FROM aplicatie WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Obtain an app");
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

    public void updateAplicatie(Aplicatie app, int id) {
        String updateq = "UPDATE aplicatie SET nume=?, versiune=?" +
        ", categorie=?, data_lansare=?, free=? WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Update an app");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateq);
            preparedStatement.setString(1, app.getNume());
            preparedStatement.setString(2, app.getVersiune());
            preparedStatement.setString(3,app.getCategorie());
            java.sql.Date data = new java.sql.Date(app.getData_lansare().getYear(),
                    app.getData_lansare().getMonthValue(), app.getData_lansare().getDayOfMonth());
            preparedStatement.setDate(4, data);
            preparedStatement.setBoolean(5, app.isFree());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAplicatie(int id){
        String query = "DELETE FROM aplicatie where id=?";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Delete an app");
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Aplicatie mapToApp(ResultSet resultSet) throws SQLException{
        if (resultSet.next()) {
            return new Aplicatie(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getBoolean(6));
        }
        return null;
    }
}
