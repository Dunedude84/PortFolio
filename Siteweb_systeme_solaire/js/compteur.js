// Fonction auto-exécutée pour éviter les conflits de portée
(function() {
    // Date de fondation de l'entreprise : 14 février 2003
    const dateFoundation = new Date('2003-02-14T00:00:00Z'); // Format UTC
    
    function updateCompteur() {
        try {
            // Récupérer l'élément du compteur
            const compteurElement = document.getElementById('compteur');
            
            // Vérifier si l'élément existe
            if (!compteurElement) {
                console.warn('L\'élément compteur n\'existe pas');
                return;
            }
            
            // Obtenir la date et l'heure actuelles
            const now = new Date();
            
            // Calculer la différence en millisecondes
            const difference = now.getTime() - dateFoundation.getTime();
            
            if (isNaN(difference)) {
                compteurElement.innerHTML = 'Depuis le 14 février 2003';
                return;
            }
            
            // Calcul des jours, heures, minutes et secondes
            const days = Math.floor(difference / (1000 * 60 * 60 * 24));
            const hours = Math.floor((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((difference % (1000 * 60)) / 1000);
            
            // Mise à jour du compteur
            compteurElement.innerHTML = 
                days + ' jours, ' + 
                hours + ' heures, ' + 
                minutes + ' minutes et ' + 
                seconds + ' secondes';
            
            // Rendre visible le compteur
            compteurElement.style.visibility = 'visible';
            compteurElement.style.opacity = '1';
        } catch (error) {
            console.error('Erreur dans le compteur:', error);
            const compteurElement = document.getElementById('compteur');
            if (compteurElement) {
                compteurElement.innerHTML = 'Depuis le 14 février 2003';
            }
        }
    }
    
    // Fonction pour initialiser le compteur
    function initCompteur() {
        // Mise à jour initiale
        updateCompteur();
        
        // Mise à jour toutes les secondes
        setInterval(updateCompteur, 1000);
    }
    
    // Vérifier si le DOM est déjà chargé
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initCompteur);
    } else {
        // Le DOM est déjà chargé
        initCompteur();
    }

    // Ajouter un gestionnaire d'événements pour la fenêtre chargée
    window.addEventListener('load', function() {
        // Réessayer après le chargement complet de la page
        updateCompteur();
    });
})();
