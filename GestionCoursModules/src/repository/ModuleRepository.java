package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import core.Database;
import entities.Module;

public class ModuleRepository extends Database {
    private final String SQL_SELECT_ALL = "SELECT * FROM module";
    private final String SQL_INSERT = "INSERT INTO module (nom) VALUES (?)";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM module WHERE id = ?";
    private final String SQL_SELECT_BY_NOM = "SELECT * FROM module WHERE nom = ?";
    private final String SQL_EXISTS_BY_ID = "SELECT COUNT(*) FROM module WHERE id = ?";

    public Module insert(Module module) {
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, module.getNom());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                module.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion d'un module: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return module;
    }

    public Module findById(int id) {
        Module module = null;
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                module = new Module();
                module.setId(rs.getInt("id"));
                module.setNom(rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du module: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return module;
    }

    public Module findByNom(String nom) {
        Module module = null;
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_BY_NOM);
            statement.setString(1, nom);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                module = new Module();
                module.setId(rs.getInt("id"));
                module.setNom(rs.getString("nom"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du module par nom: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return module;
    }

    public List<Module> selectAll() {
        List<Module> modules = new ArrayList<>();
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Module module = new Module();
                module.setId(rs.getInt("id"));
                module.setNom(rs.getString("nom"));
                modules.add(module);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des modules: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return modules;
    }

    public boolean existsById(int moduleId) {
        boolean exists = false;
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_EXISTS_BY_ID);
            statement.setInt(1, moduleId);
            ResultSet rs = statement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du module: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return exists;
    }
}
