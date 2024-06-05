import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import entities.Module;
import entities.Professeur;
import entities.Cours;
import services.ProfesseurService;
import services.ModuleService;
import services.CoursService;

public class View {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ProfesseurService professeurService = new ProfesseurService();
        ModuleService moduleService = new ModuleService();
        CoursService coursService = new CoursService();

        int choix;
        do {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("|                 Menu Gestion Scolaire               |");
            System.out.println("+-----------------------------------------------------+");
            System.out.println("| 1. Créer des modules                                |");
            System.out.println("| 2. Lister des modules                               |");
            System.out.println("| 3. Ajouter des professeurs                          |");
            System.out.println("| 4. Créer des cours                                  |");
            System.out.println("| 5. Lister les cours                                 |");
            System.out.println("| 6. Lister les cours d'un module et d'un professeur  |");
            System.out.println("| 7. Quitter                                          |");
            System.out.println("+-----------------------------------------------------+");
            System.out.print("Entrez votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: // Créer des modules
                    clearScreen();
                    System.out.println("=== Créer un nouveau module ===");
                    System.out.print("Entrer le nom du module : ");
                    String nom = scanner.nextLine();
                    if (nom.isEmpty()) {
                        System.out.println("Le nom du module ne peut pas être vide.");
                        break;
                    }
                    Module module = new Module();
                    module.setNom(nom);
                    moduleService.ajouterModule(module);
                    System.out.println("Module ajouté avec succès.");
                    break;

                case 2: // Lister des modules
                    clearScreen();
                    System.out.println("---------------------------------------------");
                    System.out.println("|               LISTE DES MODULES           |");
                    System.out.println("---------------------------------------------");
                    List<Module> modules = moduleService.listerModules();
                    if (modules.isEmpty()) {
                        System.out.println("|               Aucun module disponible.               |");
                        System.out.println("------------------------------------------------");
                    } else {
                        System.out.println("+--------+----------------------------------+");
                        System.out.printf("| %-6s | %-32s |\n", "ID", "Nom");
                        System.out.println("+--------+----------------------------------+");
                        for (Module mod : modules) {
                            System.out.printf("| %-6d | %-32s |\n", mod.getId(), mod.getNom());
                            System.out.println("+--------+----------------------------------+");
                        }
                    }
                    break;

                case 3: // Ajouter des professeurs
                    clearScreen();
                    System.out.println("=== Ajouter un Professeur ===");
                    Professeur professeur = new Professeur();
                    System.out.print("Nom: ");
                    String nomProf = scanner.nextLine();
                    if (nomProf.isEmpty()) {
                        System.out.println("Le nom ne peut pas être vide.");
                        break;
                    }
                    professeur.setNom(nomProf);

                    System.out.print("Prénom: ");
                    String prenomProf = scanner.nextLine();
                    if (prenomProf.isEmpty()) {
                        System.out.println("Le prénom ne peut pas être vide.");
                        break;
                    }
                    professeur.setPrenom(prenomProf);

                    System.out.print("Téléphone: ");
                    String telProf = scanner.nextLine();
                    if (telProf.isEmpty()) {
                        System.out.println("Le téléphone ne peut pas être vide.");
                        break;
                    }
                    professeur.setTel(telProf);

                    professeurService.ajouterProfesseur(professeur);
                    System.out.println("Professeur ajouté avec succès.");
                    break;

                case 4: // Créer des cours
                    clearScreen();
                    System.out.println("=== Créer un nouveau cours ===");
                    Cours cours = new Cours();

                    System.out.print("Date (YYYY-MM-DD): ");
                    String dateStr = scanner.nextLine();
                    try {
                        cours.setDate(java.time.LocalDate.parse(dateStr));
                    } catch (Exception e) {
                        System.out.println("Format de date invalide.");
                        break;
                    }

                    System.out.print("Heure de début (HH:MM): ");
                    String heureDbStr = scanner.nextLine();
                    try {
                        cours.setHeureDb(java.time.LocalTime.parse(heureDbStr));
                    } catch (Exception e) {
                        System.out.println("Format d'heure de début invalide.");
                        break;
                    }

                    System.out.print("Heure de fin (HH:MM): ");
                    String heureFinStr = scanner.nextLine();
                    try {
                        cours.setHeureFin(java.time.LocalTime.parse(heureFinStr));
                    } catch (Exception e) {
                        System.out.println("Format d'heure de fin invalide.");
                        break;
                    }

                    System.out.print("ID du professeur: ");
                    int profId = scanner.nextInt();
                    scanner.nextLine();
                    Professeur prof = professeurService.trouverProfesseurParId(profId);
                    if (prof == null) {
                        System.out.println("Professeur non trouvé.");
                        break;
                    }
                    cours.setProfesseur(prof);

                    System.out.print("ID du module: ");
                    int modId = scanner.nextInt();
                    scanner.nextLine();
                    Module mod = moduleService.trouverModuleParId(modId);
                    if (mod == null) {
                        System.out.println("Module non trouvé.");
                        break;
                    }
                    cours.setModule(mod);

                    coursService.creerCours(cours);
                    System.out.println("Cours créé avec succès.");
                    break;

                case 5: // Lister les cours
                    clearScreen();
                    System.out.println("---------------------------------------------");
                    System.out.println("|               LISTE DES COURS             |");
                    System.out.println("---------------------------------------------");
                    List<Cours> coursList = coursService.listerTousLesCours();
                    if (coursList.isEmpty()) {
                        System.out.println("|               Aucun cours disponible.               |");
                        System.out.println("------------------------------------------------");
                    } else {
                        System.out.println("+--------+------------+------------+------------+------------------+------------------+");
                        System.out.printf("| %-6s | %-10s | %-10s | %-10s | %-16s | %-16s |\n", "ID", "Date", "Heure Db", "Heure Fin", "Professeur", "Module");
                        System.out.println("+--------+------------+------------+------------+------------------+------------------+");
                        for (Cours cr : coursList) {
                            System.out.printf("| %-6d | %-10s | %-10s | %-10s | %-16s | %-16s |\n", cr.getId(), cr.getDate(), cr.getHeureDb(), cr.getHeureFin(), cr.getProfesseur().getNom(), cr.getModule().getNom());
                            System.out.println("+--------+------------+------------+------------+------------------+------------------+");
                        }
                    }
                    break;

                    case 6: // Lister les cours d'un module et d'un professeur
                    clearScreen();
                    System.out.println("=== Lister les cours d'un module et d'un professeur ===");
                    System.out.print("ID du module: ");
                    int moduleId = scanner.nextInt();
                    if (!moduleService.moduleExists(moduleId)) {
                        System.out.println("Module non trouvé.");
                        break;
                    }

                    System.out.print("ID du professeur: ");
                    int professeurId = scanner.nextInt();
                    if (!professeurService.professeurExists(professeurId)) {
                        System.out.println("Professeur non trouvé.");
                        break;
                    }
                    scanner.nextLine();

                    List<Cours> coursListByModuleAndProf = coursService.listerCoursParModuleEtProfesseur(moduleId, professeurId);
                    if (coursListByModuleAndProf.isEmpty()) {
                        System.out.println("Aucun cours trouvé pour ce module et ce professeur.");
                    } else {
                        System.out.println("+--------+------------+------------+------------+");
                        System.out.printf("| %-6s | %-10s | %-10s | %-10s |\n", "ID", "Date", "Heure Db", "Heure Fin");
                        System.out.println("+--------+------------+------------+------------+");
                        for (Cours cr : coursListByModuleAndProf) {
                            System.out.printf("| %-6d | %-10s | %-10s | %-10s |\n", cr.getId(), cr.getDate(), cr.getHeureDb(), cr.getHeureFin());
                            System.out.println("+--------+------------+------------+------------+");
                        }
                    }
                    break;

                case 7:
                    System.out.println("Fermeture du programme.");
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }

        } while (choix != 7);

        scanner.close();
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Erreur lors du nettoyage de l'écran: " + ex.getMessage());
        }
    }
}
