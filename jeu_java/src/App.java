import javafx.application.Application; // Classe de base pour les applications JavaFX
import javafx.application.Platform; // Pour exécuter du code sur le thread d'application JavaFX
import javafx.fxml.FXMLLoader; // Charge les fichiers FXML pour l'interface graphique
import javafx.scene.Parent; // Représentation de la racine de la scène (FXML)
import javafx.scene.Scene; // Définit une scène dans l'application JavaFX
import javafx.stage.Stage; // Fenêtre principale de l'application

import java.io.IOException; // Gestion des erreurs d'entrée/sortie

// Classe principale de l'application JavaFX.
// Hérite de la classe Application pour démarrer l'application.
public class App extends Application {

    // Méthode start() : point d'entrée principal pour l'application JavaFX.

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // Charge le fichier FXML "rpgui.fxml" en utilisant FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rpgui.fxml"));

            // Crée une instance du contrôleur associé à l'interface (RPGController)
            RPGController controller = new RPGController();

            // Associe le contrôleur au loader FXML pour lier les éléments de l'UI aux
            // méthodes/variables
            loader.setController(controller);

            // Charge la structure de l'interface depuis le fichier FXML
            Parent root = loader.load();

            // Crée une scène avec la racine chargée (l'interface) et des dimensions fixes
            Scene scene = new Scene(root, 1024, 768);

            // Définit le titre de la fenêtre principale
            primaryStage.setTitle("La Conquête du Chevalier");

            // Associe la scène à la fenêtre principale
            primaryStage.setScene(scene);

            // Empêche le redimensionnement de la fenêtre
            primaryStage.setResizable(false);

            // Exécute l'affichage de la fenêtre sur le thread d'application JavaFX (thread
            // principal)
            Platform.runLater(() -> primaryStage.show());
        } catch (Exception e) {
            // Gestion des exceptions (peut être améliorée pour afficher un message
            // utilisateur)
            e.printStackTrace();
        }
    }

    // Méthode main() : point d'entrée standard de l'application JavaFX.
    // Lance l'application en appelant la méthode start().
    public static void main(String[] args) {
        launch(args); // Démarrage de l'application JavaFX
    }
}
