package services;

import java.util.List;
import entities.Cours;
import repository.CoursRepository;

public class CoursService {
    private CoursRepository coursRepository = new CoursRepository();

    public Cours creerCours(Cours cours) {
        return coursRepository.insert(cours);
    }

    public List<Cours> listerTousLesCours() {
        return coursRepository.selectAll();
    }

    public List<Cours> listerCoursParModuleEtProfesseur(int idModule, int idProfesseur) {
        return coursRepository.selectByModuleAndProfesseur(idModule, idProfesseur);
    }
}
