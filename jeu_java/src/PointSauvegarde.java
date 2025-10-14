import java.io.Serializable;

// Classe représentant un point de sauvegarde du jeu
// Implémente Serializable pour permettre l'enregistrement dans un fichier

public class PointSauvegarde implements Serializable {

    private String nom;
    private int pointsVieJoueur;
    private int pointsVieAdversaire;
    private int niveau;

    public PointSauvegarde(String nom, int pointsVieJoueur, int pointsVieAdversaire, int niveau) {
        this.nom = nom;
        this.pointsVieJoueur = pointsVieJoueur;
        this.pointsVieAdversaire = pointsVieAdversaire;
        this.niveau = niveau;
    }

    // Getters
    
    public String getNom() {
        return nom;
    }

    public int getPointsVieJoueur() {
        return pointsVieJoueur;
    }

    public int getPointsVieAdversaire() {
        return pointsVieAdversaire;
    }

    public int getNiveau() {
        return niveau;
    }

    // Setters
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPointsVieJoueur(int pointsVieJoueur) {
        this.pointsVieJoueur = pointsVieJoueur;
    }

    public void setPointsVieAdversaire(int pointsVieAdversaire) {
        this.pointsVieAdversaire = pointsVieAdversaire;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return nom;
    }
}
