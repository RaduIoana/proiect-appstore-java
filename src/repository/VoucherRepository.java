package repository;

import com.sun.jdi.VMOutOfMemoryException;
import jdbc.DBConfig;
import metode_de_plata.Card;
import metode_de_plata.MetodaGenerica;
import metode_de_plata.Voucher;
import services.AuditService;

import java.sql.*;
import java.time.ZoneId;
import java.util.Objects;

import static services.AuditService.getAuditService;

public class VoucherRepository {
    private static VoucherRepository voucherRepository;

    public static VoucherRepository getVoucherRepository(){
        if(voucherRepository == null)
            voucherRepository = new VoucherRepository();
        return voucherRepository;
    }
    public void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS voucher" +
                "(id int, nume VARCHAR(50), data_exp DATE,"+
                " PRIMARY KEY (id), FOREIGN KEY (id) REFERENCES metoda_generica(id) ON DELETE CASCADE)";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Created voucher table");
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayVoucher() {
        String query = "SELECT * FROM voucher";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Show all vouchers");
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString(1));
                System.out.println("Nume: "+resultSet.getString(2));
                System.out.println("Data expirarii: "+resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertVoucher(int id, String nume, Date dataExp){
        String insertAppSql = "INSERT INTO voucher(id, nume, data_exp)" +
                "VALUES(?, ?, ?)";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Insert a voucher");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertAppSql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nume);
            preparedStatement.setDate(3, dataExp);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Voucher getVoucherById(int id) {
        String selectSql = "SELECT * FROM voucher WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Obtain a voucher");
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

    public void updateVoucher(Voucher v, int id) {
        String updateq = "UPDATE voucher SET nume=?, data_exp=? WHERE id=?";

        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Update a voucher");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateq);
            preparedStatement.setString(1, v.getNume());
            java.sql.Date data = new java.sql.Date(v.getData_exp().getYear(),
                    v.getData_exp().getMonthValue(), v.getData_exp().getDayOfMonth());
            preparedStatement.setDate(2, data);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVoucher(int id){
        String query = "DELETE FROM voucher where id=?";
        Connection connection = DBConfig.getDatabaseConnection();
        getAuditService().log("Delete a voucher");
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected Voucher mapToApp(ResultSet resultSet) throws SQLException{
        if (resultSet.next()) {
            return new Voucher(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3).toLocalDate());
            }
        return null;
    }
}

