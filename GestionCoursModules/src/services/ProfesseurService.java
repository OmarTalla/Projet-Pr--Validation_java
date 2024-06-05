package services;

import java.util.List;
import entities.Professeur;
import repository.ProfesseurRepository;

public class ProfesseurService {
    private ProfesseurRepository professeurRepository = new ProfesseurRepository();

    public Professeur ajouterProfesseur(Professeur professeur) {
        return professeurRepository.insert(professeur);
    }

    public List<Professeur> listerProfesseurs() {
        return professeurRepository.selectAll();
    }

    public boolean professeurExists(int professeurId) {
        return professeurRepository.existsById(professeurId);
    }


    public Professeur trouverProfesseurParId(int id) {
        return professeurRepository.findById(id);
    }
}
