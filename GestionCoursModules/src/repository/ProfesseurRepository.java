package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import core.Database;
import entities.Professeur;

public class ProfesseurRepository extends Database {
    private final String SQL_INSERT = "INSERT INTO professeur (nom, prenom, tel) VALUES (?, ?, ?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM professeur";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM professeur WHERE id = ?";
    private final String SQL_EXISTS_BY_ID = "SELECT COUNT(*) FROM professeur WHERE id = ?";
    public Professeur insert(Professeur professeur) {
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, professeur.getNom());
            statement.setString(2, professeur.getPrenom());
            statement.setString(3, professeur.getTel());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                professeur.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion d'un professeur: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return professeur;
    }

    public Professeur findById(int id) {
        Professeur professeur = null;
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                professeur = new Professeur();
                professeur.setId(rs.getInt("id"));
                professeur.setNom(rs.getString("nom"));
                professeur.setPrenom(rs.getString("prenom"));
                professeur.setTel(rs.getString("tel"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du professeur: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return professeur;
    }

    public List<Professeur> selectAll() {
        List<Professeur> professeurs = new ArrayList<>();
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(rs.getInt("id"));
                professeur.setNom(rs.getString("nom"));
                professeur.setPrenom(rs.getString("prenom"));
                professeur.setTel(rs.getString("tel"));
                professeurs.add(professeur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des professeurs: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return professeurs;
    }

    public boolean existsById(int professeurId) {
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_EXISTS_BY_ID);
            statement.setInt(1, professeurId);
            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du professeur: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return false;
    }

}
