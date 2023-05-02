package com.example.pcmarket;

import java.sql.*;
import java.sql.Statement;

public class Connect {

    private String driver = "org.postgresql.Driver";
    private String host = "195.150.230.208";
    private String port = "5432";   //wymagane kiedy nie jest domyślny dla bazy
    private String dbname = "2023_polak_michal";
    private String user = "2023_polak_michal";
    private String url = "jdbc:postgresql://" + host+":"+port + "/" + dbname;
    private String pass = "35229";
    private Connection connection;

    public Connect () {
        connection = makeConnection();
    }

    public Connection getConnection(){
        return(connection);
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException sqle) {
            System.err.println("Blad przy zamykaniu polaczenia: " + sqle);
        }
    }

    public Connection makeConnection(){
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, pass);
            return(connection);
        } catch(ClassNotFoundException cnfe) {
            System.err.println("Blad ladowania sterownika: " + cnfe);
            return(null);
        } catch(SQLException sqle) {
            System.err.println("Blad przy nawiązywaniu polaczenia: " + sqle);
            return(null);
        }
    }

    public ResultSet select( String query, Connection connection){
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void insert(String query, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Poprawnie dodano dane do tabeli!");
        } catch (SQLException e) {
            System.out.println("Błąd przy dodawaniu danych: " + e);
        }
    }
}
