import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ListView;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

// Contrôleur principal du jeu RPG
public class RPGController implements Initializable {

    // Éléments de l'interface utilisateur
    @FXML
    private ImageView imgHero;
    @FXML
    private ImageView imgEnemy;
    @FXML
    private ImageView btnCommencer;
    @FXML
    private ImageView btnCharger;
    @FXML
    private ImageView btnEnregistrer;
    @FXML
    private ImageView btnAttaquer;
    @FXML
    private ImageView btnMagie;

    // Labels pour les statistiques du joueur
    @FXML
    private Label lblHeroName;
    @FXML
    private Label lblHeroHP;
    @FXML
    private Label lblHeroAttack;
    @FXML
    private Label lblHeroDefense;
    @FXML
    private Label lblHeroStatus;

    // Labels pour les statistiques de l'adversaire
    @FXML
    private Label lblEnemyName;
    @FXML
    private Label lblEnemyHP;
    @FXML
    private Label lblEnemyAttack;
    @FXML
    private Label lblEnemyDefense;
    @FXML
    private Label lblEnemyStatus;

    // Variables du jeu
    private Joueur joueur;
    private PersonnageRPG adversaireCourant;
    private PersonnageRPG[] adversaires;
    private int niveauActuel;
    private boolean jeuEnCours;
    private List<PointSauvegarde> pointsSauvegarde;
    private final String SAVE_FILE = "sauvegardes.dat";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialiser les personnages
        initialiserPersonnages();

        // Charger les points de sauvegarde existants
        chargerPointsSauvegarde();

        // Configurer les gestionnaires d'événements pour les boutons
        configurerBoutons();

        // Désactiver les boutons d'action au démarrage
        desactiverBoutonsAction();

        // Afficher l'état initial du jeu
        afficherEtatInitial();
    }

    // Initialise les personnages du jeu
    private void initialiserPersonnages() {
        // Créer le joueur
        joueur = new Joueur("Le Chevalier", 200, 10, 10, "knight.jpg");

        // Créer les adversaires
        adversaires = new PersonnageRPG[3];
        adversaires[0] = new PersonnageRPG("Chevalier Maléfique", 175, 13, 5, "evilknight.jpg");
        adversaires[1] = new PersonnageRPG("Monstre Maléfique", 180, 14, 6, "monster.jpg");
        adversaires[2] = new PersonnageRPG("Dragon Maléfique", 190, 15, 7, "dragon.jpg");

        // Initialiser le niveau actuel
        niveauActuel = -1;
        jeuEnCours = false;

        // Initialiser la liste des points de sauvegarde
        pointsSauvegarde = new ArrayList<>();
    }

    // Affiche l'état initial du jeu avec Le Chevalier vs Chevalier Maléfique
    private void afficherEtatInitial() {
        // Définir l'adversaire initial (Chevalier Maléfique)
        adversaireCourant = adversaires[0];

        // Mettre à jour les statistiques du joueur
        lblHeroName.setText(joueur.getNom());
        lblHeroHP.setText(String.valueOf(joueur.getPointsVie()));
        lblHeroAttack.setText(String.valueOf(joueur.getPointsAttaque()));
        lblHeroDefense.setText(String.valueOf(joueur.getPointsDefense()));

        // Mettre à jour les statistiques de l'adversaire
        lblEnemyName.setText(adversaireCourant.getNom());
        lblEnemyHP.setText(String.valueOf(adversaireCourant.getPointsVie()));
        lblEnemyAttack.setText(String.valueOf(adversaireCourant.getPointsAttaque()));
        lblEnemyDefense.setText(String.valueOf(adversaireCourant.getPointsDefense()));

        // Mettre à jour les images
        imgHero.setImage(new Image(getClass().getResourceAsStream("images/" + joueur.getNomFichier())));
        imgEnemy.setImage(new Image(getClass().getResourceAsStream("images/" + adversaireCourant.getNomFichier())));
    }

    // Configure les gestionnaires d'événements pour les boutons
    private void configurerBoutons() {
        // Bouton Commencer
        btnCommencer.setOnMouseClicked(event -> {
            commencerNouvellePartie();
        });
        btnCommencer.setPickOnBounds(true);

        // Bouton Charger
        btnCharger.setOnMouseClicked(event -> {
            afficherDialogueCharger();
        });
        btnCharger.setPickOnBounds(true);

        // Bouton Enregistrer
        btnEnregistrer.setOnMouseClicked(event -> {
            afficherDialogueSauvegarder();
        });
        btnEnregistrer.setPickOnBounds(true);

        // Bouton Attaquer
        btnAttaquer.setOnMouseClicked(event -> {
            if (jeuEnCours) {
                attaquer();
            }
        });
        btnAttaquer.setPickOnBounds(true);

        // Bouton Magie
        btnMagie.setOnMouseClicked(event -> {
            if (jeuEnCours) {
                utiliserMagie();
            }
        });
        btnMagie.setPickOnBounds(true);

    }

    // Commence une nouvelle partie
    private void commencerNouvellePartie() {
        // Réinitialiser le joueur
        joueur.setPointsVie(200);

        // Réinitialiser les adversaires
        adversaires[0].setPointsVie(175);
        adversaires[1].setPointsVie(180);
        adversaires[2].setPointsVie(190);

        // Définir le niveau actuel à 0 (premier adversaire)
        niveauActuel = 0;
        adversaireCourant = adversaires[niveauActuel];

        // Mettre à jour l'interface utilisateur
        mettreAJourInterface();

        // Activer les boutons d'action
        activerBoutonsAction();

        // Marquer le jeu comme en cours
        jeuEnCours = true;
    }

    // Attaque l'adversaire actuel
    private void attaquer() {
        // Le joueur attaque l'adversaire
        int degatsJoueur = joueur.attaque(adversaireCourant);

        // Afficher le résultat de l'attaque
        afficherDialogueAttaque(degatsJoueur);

        // Mettre à jour l'interface
        mettreAJourInterface();

        // Vérifier si l'adversaire est vaincu
        if (adversaireCourant.estMort()) {
            gererVictoireSurAdversaire();
            return;
        }

        // L'adversaire riposte
        int degatsAdversaire = adversaireCourant.attaque(joueur);

        // Afficher le résultat de la riposte
        afficherDialogueRiposte(degatsAdversaire);

        // Mettre à jour l'interface
        mettreAJourInterface();

        // Vérifier si le joueur est vaincu
        if (joueur.estMort()) {
            gererDefaiteJoueur();
        }
    }

    // Utilise un objet magique
    private void utiliserMagie() {
        // Le joueur utilise la magie
        int[] resultatMagie = joueur.utiliserMagie(adversaireCourant);
        int typeObjet = resultatMagie[0];
        int effet = resultatMagie[1];

        // Afficher le résultat de l'utilisation de la magie
        afficherDialogueMagie(typeObjet, effet);

        // Mettre à jour l'interface
        mettreAJourInterface();

        // Vérifier si l'adversaire est vaincu
        if (adversaireCourant.estMort()) {
            gererVictoireSurAdversaire();
            return;
        }

        // L'adversaire riposte
        int degatsAdversaire = adversaireCourant.attaque(joueur);

        // Afficher le résultat de la riposte
        afficherDialogueRiposte(degatsAdversaire);

        // Mettre à jour l'interface
        mettreAJourInterface();

        // Vérifier si le joueur est vaincu
        if (joueur.estMort()) {
            gererDefaiteJoueur();
        }
    }

    // Gère la victoire sur un adversaire
    private void gererVictoireSurAdversaire() {
        // Marquer l'adversaire comme mort dans l'interface
        lblEnemyStatus.setText("Mort!");
        lblEnemyStatus.setTextFill(Color.RED);

        // Vérifier si c'était le dernier adversaire
        if (niveauActuel == 2) {
            // Le joueur a gagné le jeu
            lblHeroStatus.setText("Vous Avez Conquis!");
            lblHeroStatus.setTextFill(Color.RED);

            // Désactiver les boutons d'action
            desactiverBoutonsAction();

            // Marquer le jeu comme terminé
            jeuEnCours = false;
        } else {
            // Afficher un dialogue de victoire et passer au niveau suivant
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Victoire");
            alert.setHeaderText("Vous avez vaincu votre adversaire!");
            alert.setContentText("Continuez vers le prochain défi!");

            // Configurer la taille de la fenêtre pour afficher le texte complet
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(150);
            alert.getDialogPane().setMinWidth(350);
            alert.getDialogPane().setPrefWidth(400);

            alert.showAndWait();

            // Passer au niveau suivant
            niveauActuel++;
            adversaireCourant = adversaires[niveauActuel];

            // Restaurer les statistiques du joueur à l'état initial
            joueur.setPointsVie(200);
            joueur.setPointsAttaque(10);
            joueur.setPointsDefense(10);

            // Mettre à jour l'interface
            mettreAJourInterface();
        }
    }

    // Gère la défaite du joueur
    private void gererDefaiteJoueur() {
        // Marquer le joueur comme mort dans l'interface
        lblHeroStatus.setText("Vous êtes mort!");
        lblHeroStatus.setTextFill(Color.RED);

        // Désactiver les boutons d'action
        desactiverBoutonsAction();

        // Marquer le jeu comme terminé
        jeuEnCours = false;
    }

    // Met à jour l'interface utilisateur avec les statistiques actuelles
    private void mettreAJourInterface() {
        // Mettre à jour les statistiques du joueur
        lblHeroName.setText(joueur.getNom());
        lblHeroHP.setText(String.valueOf(joueur.getPointsVie()));
        lblHeroAttack.setText(String.valueOf(joueur.getPointsAttaque()));
        lblHeroDefense.setText(String.valueOf(joueur.getPointsDefense()));

        // Mettre à jour les statistiques de l'adversaire
        lblEnemyName.setText(adversaireCourant.getNom());
        lblEnemyHP.setText(String.valueOf(adversaireCourant.getPointsVie()));
        lblEnemyAttack.setText(String.valueOf(adversaireCourant.getPointsAttaque()));
        lblEnemyDefense.setText(String.valueOf(adversaireCourant.getPointsDefense()));

        // Mettre à jour le statut de l'adversaire
        if (adversaireCourant.estMort()) {
            lblEnemyStatus.setText("Mort!");
            lblEnemyStatus.setTextFill(Color.RED);
        } else {
            lblEnemyStatus.setText(""); // Effacer le statut pour les ennemis vivants
            lblEnemyStatus.setTextFill(Color.BLACK);
        }

        // Mettre à jour le statut du joueur
        if (joueur.estMort()) {
            lblHeroStatus.setText("Vous êtes mort!");
            lblHeroStatus.setTextFill(Color.RED);
        } else {
            lblHeroStatus.setText(""); // Effacer le statut pour le joueur vivant
            lblHeroStatus.setTextFill(Color.BLACK);
        }

        // Mettre à jour les images
        imgHero.setImage(new Image(getClass().getResourceAsStream("images/" + joueur.getNomFichier())));
        imgEnemy.setImage(new Image(getClass().getResourceAsStream("images/" + adversaireCourant.getNomFichier())));
    }

    // Affiche une boîte de dialogue pour l'attaque du joueur
    private void afficherDialogueAttaque(int degats) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Vous attaquez!");
        alert.setHeaderText("Résultat");

        // Obtenir les points de chance réels de la dernière attaque
        int pointsChance = joueur.getDerniersPointsChance();
        String messageChance;

        if (pointsChance > 0) {
            messageChance = "votre chance était de +" + pointsChance + ".";
        } else if (pointsChance < 0) {
            messageChance = "votre chance était de " + pointsChance + ".";
        } else {
            messageChance = "votre chance était neutre (0).";
        }

        alert.setContentText("Vous avez infligé " + degats + " points de dégâts, " + messageChance);

        // Configurer la taille de la fenêtre pour afficher le texte complet
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(150);
        alert.getDialogPane().setMinWidth(400);
        alert.getDialogPane().setPrefWidth(450);

        alert.showAndWait();
    }

    // Affiche une boîte de dialogue pour la riposte de l'adversaire
    private void afficherDialogueRiposte(int degats) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("L'adversaire riposte!");
        alert.setHeaderText("Résultat");

        // Obtenir les points de chance réels de la dernière attaque de l'adversaire
        int pointsChance = adversaireCourant.getDerniersPointsChance();
        String messageChance;

        if (pointsChance > 0) {
            messageChance = "sa chance était de +" + pointsChance + ".";
        } else if (pointsChance < 0) {
            messageChance = "sa chance était de " + pointsChance + ".";
        } else {
            messageChance = "sa chance était neutre (0).";
        }

        alert.setContentText("L'adversaire vous a infligé " + degats + " points de dégâts, " + messageChance);

        // Configurer la taille de la fenêtre pour afficher le texte complet
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(150);
        alert.getDialogPane().setMinWidth(400);
        alert.getDialogPane().setPrefWidth(450);

        alert.showAndWait();
    }

    // Affiche une boîte de dialogue pour l'utilisation de la magie
    private void afficherDialogueMagie(int typeObjet, int effet) {
        String nomObjet = Joueur.getNomObjetMagique(typeObjet);
        String message = "";

        switch (typeObjet) {
            case 0: // Cristal Sombre
                message = "Vous avez trouvé le " + nomObjet + ", votre adversaire gagne " + effet + " points de vie!";
                break;
            case 1: // Fleur Empoisonnée
                message = "Vous avez trouvé la " + nomObjet + ", vous perdez " + effet + " points de vie!";
                break;
            case 2: // Boule de Feu
                message = "Vous avez trouvé la " + nomObjet + ", votre adversaire perd " + effet + " points de vie!";
                break;
            case 3: // Potion de Vie
                message = "Vous avez trouvé la " + nomObjet + ", vous gagnez " + effet + " points de vie!";
                break;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Vous utilisez la magie!");
        alert.setHeaderText(nomObjet);
        alert.setContentText(message);

        // Configurer la taille de la fenêtre pour afficher le texte complet
        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(200);
        alert.getDialogPane().setMinWidth(450);
        alert.getDialogPane().setPrefWidth(500);

        // Ajouter une image pour l'objet magique
        String imageFile = "";
        switch (typeObjet) {
            case 0:
                imageFile = "cristalsombre.png";
                break;
            case 1:
                imageFile = "fleurempoisonnee.png";
                break;
            case 2:
                imageFile = "bouledefeu.png";
                break;
            case 3:
                imageFile = "potiondevie.png";
                break;
        }

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("images/" + imageFile)));

        alert.setGraphic(icon);

        alert.showAndWait();
    }

    // Affiche une boîte de dialogue pour sauvegarder la partie
    private void afficherDialogueSauvegarder() {
        if (!jeuEnCours) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Impossible de sauvegarder");
            alert.setHeaderText(null);
            alert.setContentText("Aucune partie en cours à sauvegarder.");

            // Configurer la taille de la fenêtre pour afficher le texte complet
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(120);
            alert.getDialogPane().setMinWidth(350);
            alert.getDialogPane().setPrefWidth(400);

            alert.showAndWait();
            return;
        }

        // Créer une boîte de dialogue pour saisir le nom de la sauvegarde
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sauvegarder la Progression");
        dialog.setHeaderText("Nom du point de sauvegarde");
        dialog.setContentText("");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String nomSauvegarde = result.get().trim();

            // Créer un nouveau point de sauvegarde
            PointSauvegarde point = new PointSauvegarde(
                    nomSauvegarde,
                    joueur.getPointsVie(),
                    adversaireCourant.getPointsVie(),
                    niveauActuel);

            // Ajouter à la liste et sauvegarder
            pointsSauvegarde.add(point);
            sauvegarderPointsSauvegarde();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sauvegarde");
            alert.setHeaderText(null);
            alert.setContentText("Partie sauvegardée avec succès.");

            // Configurer la taille de la fenêtre pour afficher le texte complet
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(120);
            alert.getDialogPane().setMinWidth(300);
            alert.getDialogPane().setPrefWidth(350);

            alert.showAndWait();
        }
    }

    // Affiche une boîte de dialogue pour charger une partie
    private void afficherDialogueCharger() {
        if (pointsSauvegarde.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Impossible de charger");
            alert.setHeaderText(null);
            alert.setContentText("Aucune sauvegarde disponible.");

            // Configurer la taille de la fenêtre pour afficher le texte complet
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(120);
            alert.getDialogPane().setMinWidth(300);
            alert.getDialogPane().setPrefWidth(350);

            alert.showAndWait();
            return;
        }

        // Créer une boîte de dialogue avec une liste de sauvegardes
        Dialog<PointSauvegarde> dialog = new Dialog<>();
        dialog.setTitle("Charger un Point de Sauvegarde");
        dialog.setHeaderText(null);

        // Configurer les boutons
        ButtonType chargerButtonType = new ButtonType("Charger");
        ButtonType annulerButtonType = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().addAll(chargerButtonType, annulerButtonType);

        // Créer la liste des sauvegardes
        ListView<PointSauvegarde> listView = new ListView<>();
        listView.getItems().addAll(pointsSauvegarde);
        listView.setPrefHeight(200);
        listView.setPrefWidth(300);

        dialog.getDialogPane().setContent(listView);

        // Convertir le résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == chargerButtonType) {
                return listView.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        Optional<PointSauvegarde> result = dialog.showAndWait();
        if (result.isPresent()) {
            PointSauvegarde point = result.get();

            // Charger les données de la sauvegarde
            joueur.setPointsVie(point.getPointsVieJoueur());
            niveauActuel = point.getNiveau();
            adversaireCourant = adversaires[niveauActuel];
            adversaireCourant.setPointsVie(point.getPointsVieAdversaire());

            // Mettre à jour l'interface
            mettreAJourInterface();

            // Réinitialiser les statuts
            lblHeroStatus.setText("");
            lblEnemyStatus.setText("");

            // Activer les boutons d'action
            activerBoutonsAction();

            // Marquer le jeu comme en cours
            jeuEnCours = true;

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Chargement");
            alert.setHeaderText(null);
            alert.setContentText("Partie chargée avec succès.");

            // Configurer la taille de la fenêtre pour afficher le texte complet
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(120);
            alert.getDialogPane().setMinWidth(300);
            alert.getDialogPane().setPrefWidth(350);

            alert.showAndWait();
        }
    }

    // Active les boutons d'action (Attaquer, Magie)
    private void activerBoutonsAction() {
        btnAttaquer.setOpacity(1.0);
        btnMagie.setOpacity(1.0);
        btnAttaquer.setDisable(false);
        btnMagie.setDisable(false);
    }

    // Désactive les boutons d'action (Attaquer, Magie)
    private void desactiverBoutonsAction() {
        btnAttaquer.setOpacity(0.5);
        btnMagie.setOpacity(0.5);
        btnAttaquer.setDisable(true);
        btnMagie.setDisable(true);
    }

    // Charge les points de sauvegarde depuis le fichier
    @SuppressWarnings("unchecked")
    private void chargerPointsSauvegarde() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            pointsSauvegarde = (List<PointSauvegarde>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Le fichier n'existe pas encore, c'est normal
            pointsSauvegarde = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            pointsSauvegarde = new ArrayList<>();
        }
    }

    // Sauvegarde les points de sauvegarde dans le fichier
    private void sauvegarderPointsSauvegarde() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(pointsSauvegarde);
        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la sauvegarde: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
