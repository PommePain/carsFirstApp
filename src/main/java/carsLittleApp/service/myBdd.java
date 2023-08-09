package carsLittleApp.service;

import java.sql.*;

import carsLittleApp.app.Cars;
import com.mysql.cj.jdbc.Driver;

public class myBdd {
    private final String connection = "jdbc:mysql://";
    private final String hostname = "localhost";
    private final String user = "";
    private final String password = "";
    private final int port = 3306;

    private final String dbName = "local";

    private final Connection con = createDatabaseConnection();

    // Get db infos

    public String getDbName() { return this.dbName; }

    public String getHostname() { return this.hostname; }

    // Connection Creation

    public Connection createDatabaseConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String finalUrl = connection+hostname+":"+port+"/"+dbName;
            con = DriverManager.getConnection(finalUrl, user, password);
        } catch (SQLException | ClassNotFoundException sqlException) {
            System.out.println("Error during creating connection...");
            sqlException.printStackTrace();
        }

        return con;
    }

    public ResultSet getContentFromDb(String sqlRequest) {
        ResultSet dbRes = null;

        try {
            Connection con = createDatabaseConnection();
            Statement statement = con.createStatement();
            dbRes = statement.executeQuery(sqlRequest);
        } catch (SQLException exception) {
            System.out.println("Error during getting content from db");
            exception.printStackTrace();
        }

        return dbRes;
    }

    public boolean isExist(String sqlQuery) {
        boolean check = false;
        try {
            Connection con = createDatabaseConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(sqlQuery);
            if (res.next()) check = true;
        } catch (Exception exception) {
            System.out.println("Error during checking if an item exist\n>>>>> " + sqlQuery);
            exception.printStackTrace();
        }
        return check;
    }

    public int getTotalRows (ResultSet dbContent) {
        int total = 0;
        try {
            dbContent.last();
            total = dbContent.getRow();
            dbContent.beforeFirst();
        } catch (Exception exception) {
            System.out.println("Error during getting total rows of : " + dbContent);
            exception.printStackTrace();
        }
        return total;
    }

    public void deleteRow (int id) {
        try {
            Connection con = createDatabaseConnection();
            String sqlDelete = "DELETE FROM cars WHERE id = ?";
            PreparedStatement deleteRowStatement = con.prepareStatement(sqlDelete);
            deleteRowStatement.setInt(1, id);
            deleteRowStatement.execute();
            deleteRowStatement.close();
        } catch (Exception exception) {
            System.out.println("Error during deleting row : " + id);
            exception.printStackTrace();
        }
    }

    public void insertRow (String sqlInsert) {
        try {
            con.createStatement().executeQuery(sqlInsert);
        } catch (Exception exception) {
            System.out.println("Error during inserting row");
            exception.printStackTrace();
        }
    }
}
