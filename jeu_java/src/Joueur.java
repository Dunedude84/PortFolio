import java.util.Random;

public class Joueur extends PersonnageRPG {
    private Random random;
    private int derniersPointsChance; // Pour stocker les derniers points de chance calculés

    public Joueur(String nom, int pointsVie, int pointsAttaque, int pointsDefense, String nomFichier) {
        super(nom, pointsVie, pointsAttaque, pointsDefense, nomFichier);
        this.random = new Random();
    }

    @Override
    public int attaque(PersonnageRPG cible) {
        // Calculer les points de chance
        derniersPointsChance = calculerPointsChance();

        // Calculer les dégâts avec la formule spécifique au joueur
        int degats = (2 * this.getPointsAttaque()) - cible.getPointsDefense() + derniersPointsChance;

        // Assurer que les dégâts ne sont pas négatifs
        if (degats < 0) {
            degats = 0;
        }

        // Appliquer les dégâts à la cible
        cible.recevoirDegats(degats);

        return degats;
    }

    public int getDerniersPointsChance() {
        return derniersPointsChance;
    }

    // Calcule les points de chance basés sur un lancer de dé à 6 faces

    @Override
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

    // Méthode pour utiliser un objet magique

    public int[] utiliserMagie(PersonnageRPG cible) {
        // Lancer deux dés à 6 faces
        int de1 = random.nextInt(6) + 1;
        int de2 = random.nextInt(6) + 1;
        int somme = de1 + de2;

        int typeObjet;
        int effet;

        // Déterminer l'objet magique en fonction de la somme des dés
        if (somme >= 1 && somme <= 3) {
            // Cristal Sombre: Ajoute 10 points à la vie de l'adversaire
            typeObjet = 0;
            effet = 10;
            cible.soigner(effet);
        } else if (somme >= 4 && somme <= 6) {
            // Fleur Empoisonnée: Supprime 3 points de la vie du joueur
            typeObjet = 1;
            effet = 3;
            this.recevoirDegats(effet);
        } else if (somme >= 7 && somme <= 9) {
            // Boule de Feu: Supprime 30 points de la vie de l'adversaire
            typeObjet = 2;
            effet = 30;
            cible.recevoirDegats(effet);
        } else {
            // Potion de Vie: Ajoute 40 points à la vie du joueur
            typeObjet = 3;
            effet = 40;
            this.soigner(effet);
        }

        return new int[] { typeObjet, effet };
    }

    // Obtient le nom de l'objet magique en fonction de son type

    public static String getNomObjetMagique(int typeObjet) {
        switch (typeObjet) {
            case 0:
                return "Cristal Sombre";
            case 1:
                return "Fleur Empoisonnée";
            case 2:
                return "Boule de Feu";
            case 3:
                return "Potion de Vie";
            default:
                return "Objet Inconnu";
        }
    }
}
