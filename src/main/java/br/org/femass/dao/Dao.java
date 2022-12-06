package br.org.femass.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  abstract class Dao {
    protected final String ENDERECO = "localhost";
    protected final String BANCODADOS = "bdbiblioteca";
    protected final String PORTA = "5432";
    protected final String USUARIO = "postgres";
    protected final String SENHA = "admin";


    protected Connection getConexao() throws SQLException {
        String url = "jdbc:postgresql://" + ENDERECO + ":" + PORTA + "/" + BANCODADOS;
        Connection conn = DriverManager.getConnection(url, USUARIO, SENHA);
        return conn;
    }


    

    
}
