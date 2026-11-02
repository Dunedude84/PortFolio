# Portfolio - Vincent Lortie

[English](README.md) | **Français**

Portfolio professionnel développé pour la recherche d'emploi en tant que Programmeur-Analyste junior.

## 🎯 Objectif

Ce portfolio a été créé pour présenter mes compétences, mes projets et mon parcours professionnel dans le domaine du développement.

## 🌐 Bilingue FR / EN

Le site est entièrement bilingue (français / anglais) :

- Sélecteur de langue (boutons FR / EN) dans la barre de navigation
- Tout le contenu traduit via des attributs `data-i18n` et un dictionnaire de traductions dans `js/script.js`
- Détection de la langue du navigateur à la première visite, préférence mémorisée dans `localStorage`
- CV téléchargeable dans les deux langues (`vincent_lortie_CV_fr.pdf` / `vincent_lortie_CV_en.pdf`)

## 🚀 Technologies Utilisées

### Front-End
- **HTML5** - Structure sémantique et moderne
- **CSS3** - Styles avancés avec animations et transitions
- **JavaScript (Vanilla)** - Interactivité, animations et internationalisation
- **Font Awesome** - Icônes vectorielles (hébergées localement, sans CDN)

### Back-End & Desktop
- **Java** - Programmation orientée objet
- **JavaFX / LWJGL / OpenGL** - Interfaces graphiques et rendu 3D pour applications desktop
- **FXML** - Définition d'interfaces utilisateur pour JavaFX
- **Node.js / Express** - API back-end pour applications web
- **MongoDB** - Base de données NoSQL

### Cloud, Conteneurs & DevOps
- **Microsoft Azure (AKS & ACR)** - Cluster Kubernetes managé et registre privé de conteneurs
- **Terraform (IaC)** - Provisionnement Infrastructure as Code (VNet, Subnet, NSG, ACR, AKS)
- **Docker & Kubernetes** - Conteneurisation multi-stage, Deployments, Services, Secrets et RBAC
- **Cloudflare Zero-Trust Tunnel** - Exposition sécurisée via tunnel QUIC sortant sans port public entrant
- **GitHub Actions (CI/CD)** - Tests unitaires automatisés, validation Terraform et scan de sécurité Trivy
- **PowerShell & Bash** - Scripts CLI d'opérations (déploiement, rollback idempotent, métriques en direct)

## 📋 Sections

1. **Accueil** - Présentation avec effet de frappe animé
2. **À propos** - Parcours et présentation personnelle
3. **Compétences** - Compétences techniques, Cloud/DevOps et transversales (Azure AKS, Terraform, Docker, Kubernetes, GitHub Actions, PowerShell, HTML5, CSS3, JavaScript, Java, C#, SQL, etc.)
4. **Projets** - Présentation de mes réalisations dans l'ordre :
   1. Migration Cloud-Native : Azure AKS & Terraform (`hey-hi.ca` & `biblio.hey-hi.ca`)
   2. Horaires Pharmacie
   3. Guichet Automatique (Simulation ATM — WPF)
   4. API Discogs (Application Android)
   5. Solar System Java (Simulation 3D)
   6. Cosmos Explorer
   7. Dominion Sign Group
5. **Contact** - Informations de contact et liens professionnels

## ✨ Fonctionnalités

### Design & UX
- Thème spatial sombre et moderne avec animations binaires « matrix »
- Interface responsive (mobile, tablette, desktop)
- Animations fluides et professionnelles
- Palette de couleurs cohérente avec effets lumineux
- Navigation intuitive avec menu hamburger sur mobile

### Animations
- Effet de frappe pour le titre principal (redémarre dans la langue choisie)
- Animations au scroll (fade-in, slide-in)
- Carrousel rotatif de badges de compétences
- Effets de survol sur les cartes et boutons
- Parallax subtil sur la section hero
- Traînée d'étoiles animée suivant le curseur

### Interactivité
- Navigation smooth scroll
- Menu mobile responsive
- Indicateur de section active dans la navigation
- Basculement FR/EN instantané
- Téléchargement du CV en français ou en anglais

### Accessibilité
- Navigation au clavier
- Attributs ARIA appropriés
- Contraste de couleurs optimisé
- Focus visible pour la navigation au clavier
- Attribut `lang` mis à jour au changement de langue

## 📁 Structure du Projet

```
Portfolio/
│
├── index.html                      # Page principale
├── css/
│   └── style.css                   # Styles et animations
├── js/
│   └── script.js                   # Logique, interactivité et traductions FR/EN
├── vendor/
│   └── fontawesome/                # Librairie d'icônes (CSS + polices auto-hébergées)
├── images/                         # Images du portfolio
├── DOMINION/                       # Projet Dominion Sign Group
├── Siteweb_systeme_solaire/        # Projet Cosmos Explorer
├── vincent_lortie_CV_fr.pdf        # CV (français)
├── vincent_lortie_CV_en.pdf        # CV (anglais)
├── README.md                       # Documentation (English)
└── README.fr.md                    # Documentation (français)
```

> Note : les projets suivants sont développés et déployés dans des dépôts
> séparés, ils ne sont pas inclus ici. Des liens sont fournis dans la section
> Projets du portfolio :
> - **Migration Cloud-Native : Azure AKS & Terraform** ([Dunedude84/azure-aks-terraform-hey-hi](https://github.com/Dunedude84/azure-aks-terraform-hey-hi))
> - **Horaires Pharmacie** ([hey-hi.ca](https://hey-hi.ca))
> - **Guichet Automatique (WPF)** ([Dunedude84/atm-simulation](https://github.com/Dunedude84/atm-simulation))
> - **API Discogs (Android)** ([Dunedude84/discogs-api](https://github.com/Dunedude84/discogs-api))
> - **Solar System Java** ([Dunedude84/solar-system-java](https://github.com/Dunedude84/solar-system-java))

## 🎨 Palette de Couleurs

- **Primaire** : #3498db (Bleu spatial)
- **Primaire foncé** : #102e50 (Bleu profond)
- **Primaire clair** : #5dade2 (Bleu clair)
- **Texte** : #ffffff / #d1d5db
- **Arrière-plan** : #000000 / #0a0a0a

## 🌐 Projets Présentés

### 1. Migration Cloud-Native : Azure AKS & Terraform
Conteneurisation et migration de mes deux applications web en production (`hey-hi.ca` & `biblio.hey-hi.ca`) depuis un serveur local vers Microsoft Azure Kubernetes Service (AKS).
- **Technologies** : Microsoft Azure AKS, Terraform (IaC), Docker, Kubernetes, Cloudflare Zero-Trust Tunnel, GitHub Actions CI/CD, PowerShell, Bash, Python
- Infrastructure as Code avec Terraform (`azurerm` v4) : VNet, Subnet, NSG, Azure Container Registry (`ACR`) avec Managed Identity `AcrPull`, et cluster AKS (`1.35`, Azure CNI Overlay + Cilium)
- Exposition Zero-Trust via un Pod `cloudflared` déployé dans le cluster routant le trafic vers les services internes `ClusterIP` sans aucun port public entrant ouvert
- Pipeline CI/CD GitHub Actions (tests unitaires Node.js, `terraform fmt/validate`, scan de vulnérabilités Trivy)
- Outil CLI d'opérations en PowerShell (`hey-hi-ops.ps1`) pour les mises à jour sans coupure (*Rolling Update*), les rollbacks déclaratifs idempotents et les métriques CPU/RAM en direct (`kubectl top`)
- Dépôt GitHub : [Dunedude84/azure-aks-terraform-hey-hi](https://github.com/Dunedude84/azure-aks-terraform-hey-hi)
- Applications en production : [hey-hi.ca](https://hey-hi.ca) & [biblio.hey-hi.ca](https://biblio.hey-hi.ca)

### 2. Horaires Pharmacie
Application web complète de gestion des horaires pour pharmacie.
- **Technologies** : Node.js, Express, MongoDB
- Authentification admin/employé
- Génération d'horaires et gestion des disponibilités
- Résumé des heures travaillées
- Mode invité en lecture seule pour démonstration
- Au moment de la connexion, le site propose **deux onglets** : un onglet **Employé** et un onglet **Administrateur**.
  Les identifiants invités (`guest038` / `1357`) fonctionnent sur les deux onglets, en mode lecture seule (sans droits de modification).
- Démo en ligne : [hey-hi.ca](https://hey-hi.ca)

### 3. Guichet Automatique (Simulation ATM)
Simulation complète d'un guichet automatique — application de bureau WPF, architecture MVVM, EF Core + SQL Server. Projet d'intégration du programme LEA.9C.
- **Technologies** : C#, WPF, Microsoft.Toolkit.Mvvm, EF Core 3.1, SQL Server
- Authentification client par NIP et portail administrateur
- Dépôts, retraits, virements, paiements de factures, intérêts, marge de crédit, prélèvements hypothécaires
- 4 types de comptes, 9 types de transactions, migrations et données de démonstration
- Dépôt GitHub : [Dunedude84/atm-simulation](https://github.com/Dunedude84/atm-simulation)

### 4. API Discogs (Application Android)
Application Android native qui interroge l'API Discogs pour afficher une collection de vinyles.
- **Technologies** : Kotlin, Jetpack Compose, Retrofit, Coil, MVVM
- UI Material 3 et navigation entre écrans (collection / détail)
- Sélecteur d'utilisateur, grille alphabétique, détails de chaque album
- Dépôt GitHub : [Dunedude84/discogs-api](https://github.com/Dunedude84/discogs-api)

### 5. Solar System Java (Simulation 3D)
Simulation 3D du système solaire en Java avec LWJGL/OpenGL.
- **Technologies** : Java, LWJGL, OpenGL, JOML, Maven
- Rendu 3D avec textures réalistes, anneaux de Saturne, orbite lunaire
- Caméra libre, contrôle du temps, interface ImGui
- Dépôt GitHub : [Dunedude84/solar-system-java](https://github.com/Dunedude84/solar-system-java)

### 6. Cosmos Explorer
Site web éducatif sur le système solaire.
- **Technologies** : HTML5, CSS3, JavaScript, Animations
- Interface immersive
- Contenu éducatif riche
- Compteur de temps dynamique
- Navigation intuitive

### 7. Dominion Sign Group
Site web corporatif célébrant les 75 ans d'expertise de l'entreprise.
- **Technologies** : HTML5, CSS3, JavaScript, Responsive Design
- Design professionnel et élégant
- Compteur anniversaire dynamique
- Galerie de réalisations
- Navigation responsive

## 📱 Responsive Design

Le portfolio est entièrement responsive et optimisé pour :
- 📱 Mobile (< 640px)
- 📱 Tablette (640px - 968px)
- 💻 Desktop (> 968px)

## 🔧 Installation & Utilisation

1. Cloner ou télécharger le projet
2. Ouvrir `index.html` dans un navigateur moderne
3. Aucune dépendance ou installation requise

## 📝 Personnalisation

Pour personnaliser le portfolio :

1. **Contenu et traductions** : Modifier l'objet `translations` dans `js/script.js` (`fr` et `en`) et le contenu français par défaut dans `index.html`
2. **Couleurs** : Ajuster les variables CSS dans `:root` (style.css)
3. **Projets** : Ajouter/modifier les projets dans la section `#projets`
4. **Compétences** : Ajuster les technologies et icônes dans la section `#competences`

## 🎯 Optimisations

- Code optimisé pour les performances
- Throttling des événements scroll
- Animations CSS plutôt que JavaScript quand possible
- Images optimisées

## 📧 Contact

Pour toute question ou opportunité professionnelle :
- **Email** : vincelortie@gmail.com
- **LinkedIn** : [Vincent Lortie](https://www.linkedin.com/in/vincent-lortie-b55986253)
- **GitHub** : [@Dunedude84](https://github.com/Dunedude84/)

## 📄 Licence

© 2026 Vincent Lortie. Tous droits réservés.

---

**Note** : Ce portfolio est conçu pour la recherche d'emploi en développement. N'hésitez pas à me contacter pour discuter d'opportunités professionnelles!
