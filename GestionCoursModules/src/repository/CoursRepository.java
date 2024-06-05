package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import core.Database;
import entities.Cours;
import entities.Module;
import entities.Professeur;

public class CoursRepository extends Database {
    private final String SQL_SELECT_ALL = "SELECT * FROM cours";
    private final String SQL_INSERT = "INSERT INTO cours (date, heureDb, heureFin, id_professeur, id_module) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_SELECT_BY_MODULE_AND_PROFESSEUR = "SELECT * FROM cours WHERE id_module = ? AND id_professeur = ?";

    public Cours insert(Cours cours) {
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, java.sql.Date.valueOf(cours.getDate()));
            statement.setTime(2, java.sql.Time.valueOf(cours.getHeureDb()));
            statement.setTime(3, java.sql.Time.valueOf(cours.getHeureFin()));
            statement.setInt(4, cours.getProfesseur().getId());
            statement.setInt(5, cours.getModule().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                cours.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création d'un cours: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return cours;
    }

    public List<Cours> selectAll() {
        List<Cours> coursList = new ArrayList<>();
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setDate(rs.getDate("date").toLocalDate());
                cours.setHeureDb(rs.getTime("heureDb").toLocalTime());
                cours.setHeureFin(rs.getTime("heureFin").toLocalTime());

                Professeur prof = new Professeur();
                prof.setId(rs.getInt("id_professeur"));
                cours.setProfesseur(prof);

                Module mod = new Module();
                mod.setId(rs.getInt("id_module"));
                cours.setModule(mod);

                coursList.add(cours);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des cours: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return coursList;
    }

    public List<Cours> selectByModuleAndProfesseur(int idModule, int idProfesseur) {
        List<Cours> coursList = new ArrayList<>();
        try {
            ouvrirConnexion();
            PreparedStatement statement = conn.prepareStatement(SQL_SELECT_BY_MODULE_AND_PROFESSEUR);
            statement.setInt(1, idModule);
            statement.setInt(2, idProfesseur);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cours cours = new Cours();
                cours.setId(rs.getInt("id"));
                cours.setDate(rs.getDate("date").toLocalDate());
                cours.setHeureDb(rs.getTime("heureDb").toLocalTime());
                cours.setHeureFin(rs.getTime("heureFin").toLocalTime());

                Professeur prof = new Professeur();
                prof.setId(rs.getInt("id_professeur"));
                cours.setProfesseur(prof);

                Module mod = new Module();
                mod.setId(rs.getInt("id_module"));
                cours.setModule(mod);

                coursList.add(cours);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sélection des cours par module et professeur: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
        return coursList;
    }
}
