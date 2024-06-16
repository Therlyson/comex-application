package org.example.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String url = "jdbc:mysql://localhost:3306/comex";
    private String user = "root";
    private String password = "Familia5##";

    public Connection criaConexao(){
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
