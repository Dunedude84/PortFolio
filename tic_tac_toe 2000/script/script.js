// declaration de la constante de temps.
const INTERVALLE_ERREUR = 1000;
// declaration des variables globales.
let partieEnCours = false;
let tourJoueur1 = true;
let coups = 0;
let Tableau = [];
function initialisation() 
    {
        document.getElementById("terminerPartie").disabled=true;
    }
    
function debuterPartie() 
    {
        // verification de la saisie des noms.
        // utilisation d'une autre fonction
        // pour effectuer la verification.
        try
        {
            if(validerNoms())
            {
                // desactivation et activation des boutons.
                document.getElementById("debuterPartie").disabled=true;
                document.getElementById("terminerPartie").disabled=false;
                // desactiver les zones de saisie avec les noms des joueurs.
                document.getElementById("txtJoueur1").disabled=true;
                document.getElementById("txtJoueur2").disabled=true;
                // effacer la grille de jeu (couleur et X/O).
                for(cnt = 1; cnt <10; cnt++)
                {
                    document.getElementById("cellule" + cnt).className="";
                    document.getElementById("cellule" + cnt).innerHTML="";
                }
                // initialisation de notre element span qui affiche le message.
                document.getElementById("resultat").innerHTML="";
                // Initialisation de nos variables de jeu.
                Tableau[1]=Tableau[2]=Tableau[3]=Tableau[4]=Tableau[5]=
                Tableau[6]=Tableau[7]=Tableau[8]=Tableau[9]= 0;
                partieEnCours=true;
                tourJoueur1=true;
                coups = 0;
                preparerTourJoueur();
            }
        }
        catch (erreur)
        {
            traiterErreur(erreur);
        }
    }
    function validerNoms() 
    {
        try 
        {
            // Récupération des noms saisis.
            let nomJoueur1 = document.getElementById("txtJoueur1").value;
            let nomJoueur2 = document.getElementById("txtJoueur2").value;
            // Création d'une variable pour afficher nos messages.
            let message;
            // On efface les messages qui ont été affichés.
            message = document.getElementById("joueur1").innerHTML = "";
            message = document.getElementById("joueur2").innerHTML = "";
            // Si le nom de joueur 1 est vide.
            if (nomJoueur1.trim().length == 0) 
            {
                message = document.getElementById("joueur1");
                message.innerHTML = "Saisir le nom du premier joueur.";
                message.style.color = "red";
                return false;
            }
            // Si le nom de joueur 2 est vide.
            else if (nomJoueur2.trim().length == 0) 
            {
                message = document.getElementById("joueur2");
                message.innerHTML = "Saisir le nom du deuxième joueur.";
                message.style.color = "red";
                return false;
            }
            // Si les noms sont identiques.
            else if (nomJoueur1.trim().toLowerCase() == nomJoueur2.trim().toLowerCase()) 
            {
                let message = document.getElementById("joueur2");
                message.innerHTML = "Les deux joueurs ne peuvent pas avoir le même nom";
                message.style.color = "red";
                return false;
            }
            // Les noms semblent valides.
            return true;
        } catch (erreur) 
        {
            traiterErreur(erreur);
        }
    }  
    function preparerTourJoueur() 
    {
        try 
        {
            // identification du joueur a qui c'est
            // le tour de jouer dans la partie.
            if(tourJoueur1)
            {
               // c'est le tour de joueur 1, mettre son nom en vert.
               document.getElementById("txtJoueur1").className = "joueurActif";
               document.getElementById("txtJoueur2").className = ""; 
            }
            else
            {
                // c'est le tour de joueur 2, mettre son nom en vert.
                document.getElementById("txtJoueur2").className = "joueurActif";
                document.getElementById("txtJoueur1").className = "";
            }
            
        } catch (erreur) 
        {
            traiterErreur(erreur);
        }
    }
    function validerClicCellule(numero) 
    {
        try 
        {
            if (partieEnCours)
            {
                // verifier si la cellule a deja ete utilisee.
                if(document.getElementById("cellule" + numero).innerHTML != "")
                {
                    // si la cellule a deja ete utilisee,
                    // afficher la cellule en rouge pour signifier
                    // a l'usager que c'est un choix non valide.
                    document.getElementById("cellule" + numero).className = "celluleinvalide";
                    // attendre une seconde et remettre
                    // la cellule dans son etat initial.
                    setTimeout(function() 
                    {
                        document.getElementById("cellule" + numero).className = "";
                    }, INTERVALLE_ERREUR)
                }
                else
                {
                    // dans la cellule, assigner le bon caractere.
                    tourJoueur1 ? 
                    document.getElementById("cellule" + numero).innerHTML = "X" : 
                    document.getElementById("cellule" + numero).innerHTML = "O";
                    // modifier la grosseur du caractere.
                    document.getElementById("cellule" + numero).style.fontSize = "50px";
                    // modification de la couleur du texte selon le joueur.
                    tourJoueur1 ? 
                    document.getElementById("cellule" + numero).style.color = "#dc2626" : 
                    document.getElementById("cellule" + numero).style.color = "#eab308";
                    // enregistrement de l'information saisie dans la cellule.
                    enregistrerInfos(numero);
                    // incrementation de notre variable coups.
                    coups++;
                    verifierGagnant();
                }
            }
            
        } 
        catch (erreur) 
        {
            traiterErreur(erreur);
        }
    }
    function enregistrerInfos(numero)
    {
        // il faut inscrire dans la bonne case tu tableau
        // la valeur X ou O selon le joueur actif.
        Tableau[numero] = tourJoueur1 ? "X" : "O";
    }
    function verifierGagnant()
    {
        // il faut verifier le contenu du tableau pour voir s'il y a un gagnant.
        // Les possibilites pour gagner sont [1,2,3], [4,5,6], [7,8,9], [1,5,9]
        // [3,5,7], [1,4,7], [2,5,8], [3,6,9].
        
            if(Tableau[1] == Tableau[2] && Tableau[1] == Tableau[3] && Tableau[1] != 0)
            {
               identifierGagnant(1, 2, 3); 
            }
            else if(Tableau[4] == Tableau[5] && Tableau[4] == Tableau[6] && Tableau[4] != 0)
            {
                identifierGagnant(4, 5, 6); 
            }
            else if(Tableau[7] == Tableau[8] && Tableau[7] == Tableau[9] && Tableau[7] != 0)
            {
                identifierGagnant(7, 8, 9); 
            }
            else if(Tableau[1] == Tableau[4] && Tableau[1] == Tableau[7] && Tableau[1] != 0)
            {
                identifierGagnant(1, 4, 7); 
            }
            else if(Tableau[2] == Tableau[5] && Tableau[2] == Tableau[8] && Tableau[2] != 0)
            {
                identifierGagnant(2, 5, 8); 
            }
            else if(Tableau[3] == Tableau[6] && Tableau[3] == Tableau[9] && Tableau[3] != 0)
            {
                identifierGagnant(3, 6, 9); 
            }
            else if(Tableau[3] == Tableau[5] && Tableau[3] == Tableau[7] && Tableau[3] != 0)
            {
                identifierGagnant(3, 5, 7); 
            }
            else if(Tableau[1] == Tableau[5] && Tableau[1] == Tableau[9] && Tableau[1] != 0)
            {
                identifierGagnant(1, 5, 9); 
            }
            else if(coups == 9)
            {
                document.getElementById("resultat").innerHTML = "Partie nulle !";
                terminerPartie(false); // Don't clear board when game ends naturally
            }
            else
            {
                tourJoueur1 ? tourJoueur1 = false : tourJoueur1 = true;
                preparerTourJoueur();
            }
            
    } 
    function identifierGagnant(cellule1, cellule2, cellule3)
    {
        let message = "";
        document.getElementById("cellule" + cellule1).className = "celluleGagnante";
        document.getElementById("cellule" + cellule2).className = "celluleGagnante";
        document.getElementById("cellule" + cellule3).className = "celluleGagnante";
        tourJoueur1 ? 
            message += document.getElementById("txtJoueur1").value :
            message += document.getElementById("txtJoueur2").value;
        message += " a gagné la partie";
        document.getElementById("resultat").innerHTML = message;
        terminerPartie(false); // Don't clear board when game ends naturally
    }
    function terminerPartie(clearBoard = true)
    {
        try
        {
            // reactiver les textbox avec les noms des joueurs.
            document.getElementById("txtJoueur1").disabled = false;
            document.getElementById("txtJoueur2").disabled = false;
            // retirer la couleur verte des zones de saisie.
            document.getElementById("txtJoueur1").className = "";
            document.getElementById("txtJoueur2").className = "";
            
            // Only clear board if explicitly requested (manual termination)
            if(clearBoard) {
                // effacer la grille de jeu (couleur et X/O).
                for(cnt = 1; cnt <10; cnt++)
                {
                    document.getElementById("cellule" + cnt).className="";
                    document.getElementById("cellule" + cnt).innerHTML="";
                    document.getElementById("cellule" + cnt).style.fontSize="";
                    document.getElementById("cellule" + cnt).style.color="";
                }
                // initialisation de notre element span qui affiche le message.
                document.getElementById("resultat").innerHTML="";
            }
            
            // reinitialisation de notre variable partieEnCours.
            partieEnCours = false;
            // Activer les boutons correctement.
            document.getElementById("debuterPartie").disabled = false;
            document.getElementById("terminerPartie").disabled = true;
        }
        catch (erreur)
        {
            traiterErreur(erreur);
        }
    }