package services;

import java.util.List;
import entities.Module;
import repository.ModuleRepository;

public class ModuleService {
    private ModuleRepository moduleRepository = new ModuleRepository();

    public Module ajouterModule(Module module) {
        return moduleRepository.insert(module);
    }

    public List<Module> listerModules() {
        return moduleRepository.selectAll();
    }

    public boolean moduleExists(int moduleId) {
        return moduleRepository.existsById(moduleId);
    }
    
    public Module trouverModuleParId(int id) {
        return moduleRepository.findById(id);
    }
}
