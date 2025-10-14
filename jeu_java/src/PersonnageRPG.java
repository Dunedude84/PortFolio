import java.io.Serializable;
import java.util.Random;

// Classe de base pour tous les personnages du jeu RPG
public class PersonnageRPG implements Serializable {

    protected String nom;
    protected int pointsVie;
    protected int pointsAttaque;
    protected int pointsDefense;
    protected String nomFichier;
    protected Random random;
    protected int derniersPointsChance; // Pour stocker les derniers points de chance calculés

    public PersonnageRPG(String nom, int pointsVie, int pointsAttaque, int pointsDefense, String nomFichier) {
        this.nom = nom;
        this.pointsVie = pointsVie;
        this.pointsAttaque = pointsAttaque;
        this.pointsDefense = pointsDefense;
        this.nomFichier = nomFichier;
        this.random = new Random();
        this.derniersPointsChance = 0;
    }

    // Méthode pour attaquer un autre personnage avec chance

    public int attaque(PersonnageRPG cible) {
        // Calculer les points de chance
        derniersPointsChance = calculerPointsChance();

        // Calculer les dégâts avec la formule incluant la chance
        int degats = (2 * this.pointsAttaque) - cible.getPointsDefense() + derniersPointsChance;

        // Assurer que les dégâts ne sont pas négatifs
        if (degats < 0) {
            degats = 0;
        }

        // Appliquer les dégâts à la cible
        cible.recevoirDegats(degats);

        return degats;
    }

    // Calcule les points de chance basés sur un lancer de dé à 6 faces

    protected int calculerPointsChance() {
        int lancerDe = random.nextInt(6) + 1; // Lancer de dé (1-6)

        if (lancerDe == 1 || lancerDe == 6) {
            return -6; // Malchance
        } else if (lancerDe == 3) {
            return 6; // Chance
        } else {
            return 0; // Neutre
        }
    }

    // Retourne les derniers points de chance calculés lors de la dernière attaque

    public int getDerniersPointsChance() {
        return derniersPointsChance;
    }

    // Méthode pour recevoir des dégâts

    public void recevoirDegats(int degats) {
        this.pointsVie -= degats;
    }

    // Méthode pour soigner le personnage

    public void soigner(int montant) {
        this.pointsVie += montant;
    }

    // Vérifie si le personnage est mort (points de vie <= 0)

    public boolean estMort() {
        return this.pointsVie <= 0;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPointsVie() {
        return pointsVie;
    }

    public void setPointsVie(int pointsVie) {
        this.pointsVie = pointsVie;
    }

    public int getPointsAttaque() {
        return pointsAttaque;
    }

    public void setPointsAttaque(int pointsAttaque) {
        this.pointsAttaque = pointsAttaque;
    }

    public int getPointsDefense() {
        return pointsDefense;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
}
