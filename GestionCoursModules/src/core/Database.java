package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    protected Connection conn = null;
    protected PreparedStatement statement = null;

    public void ouvrirConnexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/GestionCoursModules", "root", "");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la BD: " + e.getMessage());
        }
    }

    public void initPrepareStatement(String sql) {
        try {
            if (conn != null) {
                statement = conn.prepareStatement(sql);
            }
        } catch (SQLException e) {
            System.out.println("Erreur Initialisation du PrepareStatement: " + e.getMessage());
        }
    }

    public int executeUpdate() {
        int nbreLigne = 0;
        try {
            if (statement != null) {
                nbreLigne = statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Erreur Execution de requete: " + e.getMessage());
        }
        return nbreLigne;
    }

    public ResultSet executeSelect() {
        ResultSet rs = null;
        try {
            if (statement != null) {
                rs = statement.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println("Erreur Execution de requete: " + e.getMessage());
        }
        return rs;
    }

    public void fermerConnexion() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }
}
