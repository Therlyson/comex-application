package org.example.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String url = "jdbc:mysql://localhost:3306/comex";
    private String user = System.getenv("MYSQL_USER");
    private String password = System.getenv("MYSQL_PASSWORD");

    public Connection criaConexao(){
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
