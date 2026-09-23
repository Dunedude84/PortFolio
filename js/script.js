// ===========================
// Internationalisation (FR / EN)
// ===========================
const translations = {
    fr: {
        'nav.home': 'Accueil',
        'nav.about': 'À propos',
        'nav.skills': 'Compétences',
        'nav.projects': 'Projets',
        'nav.contact': 'Contact',
        'hero.greeting': 'Bonjour, je suis',
        'hero.typing': 'Programmeur-Analyste Junior',
        'hero.description': "Programmeur-analyste junior passionné par le développement full-stack, à la recherche d'une opportunité",
        'hero.btnProjects': 'Voir mes projets',
        'hero.btnContact': 'Me contacter',
        'about.title': 'À propos de moi',
        'about.p1': "Passionné d'informatique depuis mon plus jeune âge, j'ai commencé par explorer la programmation et le matériel sur les premiers ordinateurs familiaux. La vie m'a ensuite rapidement orienté vers le marché du travail, où j'ai passé plus de dix ans comme monteur/câbleur en électricité industrielle.",
        'about.p2': "Aujourd'hui, je reviens à ma passion première. Ma formation en programmation maintenant complétée, je possède des compétences solides en développement front-end, back-end et en bases de données, et je cherche un emploi comme programmeur-analyste junior pour mettre en pratique mon savoir-faire technique et ma persévérance.",
        'about.h1.title': 'Formation complétée',
        'about.h1.text': 'Programmeur-Analyste (LEA.9C) Collège CDI',
        'about.h2.title': 'Spécialisation',
        'about.h2.text': 'Développement Web front-end, back-end et conception de bases de données',
        'about.h3.title': 'Passion',
        'about.h3.text': "Innovation, design d'interfaces intuitives et résolution pratique de problèmes techniques",
        'skills.title': 'Compétences acquises',
        'skills.cat1': 'Front-End & Back-End',
        'skills.cat2': 'Outils & Méthodologies',
        'skills.cat3': 'Compétences Transversales',
        'skills.soft.1': 'Résolution de problèmes',
        'skills.soft.2': "Travail d'équipe",
        'skills.soft.3': 'Communication',
        'skills.soft.4': 'Autonomie',
        'skills.soft.5': 'Analytique',
        'skills.soft.6': 'Persévérant',
        'skills.soft.7': 'Autodidacte',
        'skills.soft.8': 'Créatif',
        'skills.soft.9': 'Apprentissage continu',
        'skills.soft.10': 'Souci du détail',
        'projects.title': 'Mes projets personnels',
        'projects.viewDemo': 'Voir la démo',
        'projects.viewRepo': 'Voir le dépôt GitHub',
        'projects.viewProject': 'Voir le projet',
        'p1.alt': 'Projet Horaires Pharmacie',
        'p1.desc': "Application web complète de gestion des horaires pour pharmacie, avec authentification admin/employé, génération d'horaires, gestion des disponibilités et résumé des heures travaillées. Un mode invité en lecture seule est disponible pour la démonstration.<br>Voir le site en ligne : <a href=\"https://hey-hi.ca\" target=\"_blank\">hey-hi.ca</a><br><strong>Identifiants invité :</strong> Nom d'utilisateur: <strong><code>guest038</code></strong> · Mot de passe: <strong><code>1357</code></strong><br><em>Au moment de la connexion, le site propose <strong>deux onglets</strong> : un onglet <strong>Employé</strong> et un onglet <strong>Administrateur</strong>. Les identifiants invités ci-dessus fonctionnent sur les deux onglets, en mode lecture seule (sans droits de modification).</em>",
        'p1.f1': "Génération automatique de l'horaire",
        'p1.f2': 'Gestion des employés et disponibilités',
        'p1.f3': 'Résumé des heures par employé',
        'p1.f4': 'Mode invité en lecture seule',
        'p2.alt': 'Projet API Discogs',
        'p2.title': 'API Discogs (Application Android)',
        'p2.desc': "Application Android native qui interroge l'API Discogs pour afficher la collection de vinyles d'un utilisateur. Navigation par collection utilisateur, détails des albums (pistes, artistes, crédits), vue en grille avec recherche alphabétique et sélecteur d'utilisateur intégré.",
        'p2.f1': 'UI moderne avec Material 3 et Jetpack Compose',
        'p2.f2': 'Navigation entre écrans (collection / détail)',
        'p2.f3': 'API REST Discogs avec Retrofit + OkHttp',
        'p2.f4': 'Architecture MVVM avec ViewModel',
        'p3.alt': 'Projet Solar System Java',
        'p3.desc': "Simulation 3D du système solaire développée en Java avec LWJGL/OpenGL, traduite d'une version C++ originale. Rendu réaliste des planètes texturées, mécanique orbitale, anneaux de Saturne, caméra libre et interface interactive ImGui.",
        'p3.f1': 'Rendu 3D avec textures réalistes',
        'p3.f2': 'Anneaux de Saturne et orbite lunaire',
        'p3.f3': 'Caméra libre et contrôle du temps',
        'p3.f4': 'Interface ImGui avec fiches planétaires',
        'p4.alt': 'Projet Cosmos Explorer',
        'p4.desc': "Site web éducatif et interactif dédié à l'exploration du système solaire. Présentation détaillée des planètes, astéroïdes, comètes et services d'observation astronomique.",
        'p4.f1': 'Interface immersive',
        'p4.f2': 'Compteur de temps dynamique',
        'p4.f3': 'Navigation intuitive',
        'p4.f4': 'Contenu éducatif riche',
        'p5.alt': 'Projet Dominion Sign Group',
        'p5.desc': "Site web corporatif célébrant les 75 ans d'expertise de Dominion Sign Group. Présentation des services, réalisations et histoire de l'entreprise avec un design moderne et professionnel.",
        'p5.f1': 'Design moderne et élégant',
        'p5.f2': 'Compteur anniversaire dynamique',
        'p5.f3': 'Galerie de réalisations',
        'p5.f4': 'Navigation responsive',
        'contact.title': 'Contactez-moi!',
        'contact.intro': "Programmeur-Analyste junior, je suis activement à la recherche d'opportunités d'emploi en développement pour lancer ma carrière. N'hésitez pas à me contacter pour discuter de vos projets ou d'opportunités de collaboration.",
        'footer.rights': 'Tous droits réservés.'
    },
    en: {
        'nav.home': 'Home',
        'nav.about': 'About',
        'nav.skills': 'Skills',
        'nav.projects': 'Projects',
        'nav.contact': 'Contact',
        'hero.greeting': 'Hello, I am',
        'hero.typing': 'Junior Programmer-Analyst',
        'hero.description': 'Junior programmer-analyst passionate about full-stack development, looking for an opportunity',
        'hero.btnProjects': 'View my projects',
        'hero.btnContact': 'Contact me',
        'about.title': 'About Me',
        'about.p1': "Passionate about computers from a very young age, I started out by exploring programming and hardware on the first family computers. Life then quickly steered me toward the workforce, where I spent more than ten years as an assembler/wireman in industrial electricity.",
        'about.p2': "Today, I am returning to my first passion. With my programming training now completed, I have strong skills in front-end, back-end and database development, and I am looking for a job as a junior programmer-analyst where I can put my technical know-how and perseverance to work.",
        'about.h1.title': 'Completed Education',
        'about.h1.text': 'Programmer-Analyst (LEA.9C) — CDI College',
        'about.h2.title': 'Specialization',
        'about.h2.text': 'Front-end and back-end web development, and database design',
        'about.h3.title': 'Passion',
        'about.h3.text': 'Innovation, intuitive interface design and practical problem solving',
        'skills.title': 'Acquired Skills',
        'skills.cat1': 'Front-End & Back-End',
        'skills.cat2': 'Tools & Methodologies',
        'skills.cat3': 'Soft Skills',
        'skills.soft.1': 'Problem Solving',
        'skills.soft.2': 'Teamwork',
        'skills.soft.3': 'Communication',
        'skills.soft.4': 'Autonomy',
        'skills.soft.5': 'Analytical',
        'skills.soft.6': 'Perseverant',
        'skills.soft.7': 'Self-Taught',
        'skills.soft.8': 'Creative',
        'skills.soft.9': 'Continuous Learning',
        'skills.soft.10': 'Attention to Detail',
        'projects.title': 'My Personal Projects',
        'projects.viewDemo': 'View live demo',
        'projects.viewRepo': 'View GitHub repository',
        'projects.viewProject': 'View project',
        'p1.alt': 'Pharmacy Schedule project',
        'p1.desc': "Complete web application for pharmacy schedule management, with admin/employee authentication, schedule generation, availability management and a summary of hours worked. A read-only guest mode is available for demonstration.<br>Visit the live site: <a href=\"https://hey-hi.ca\" target=\"_blank\">hey-hi.ca</a><br><strong>Guest credentials:</strong> Username: <strong><code>guest038</code></strong> · Password: <strong><code>1357</code></strong><br><em>At login, the site offers <strong>two tabs</strong>: an <strong>Employee</strong> tab and an <strong>Administrator</strong> tab. The guest credentials above work on both tabs, in read-only mode (no editing rights).</em>",
        'p1.f1': 'Automatic schedule generation',
        'p1.f2': 'Employee and availability management',
        'p1.f3': 'Hours summary per employee',
        'p1.f4': 'Read-only guest mode',
        'p2.alt': 'Discogs API project',
        'p2.title': 'Discogs API (Android Application)',
        'p2.desc': "Native Android application that queries the Discogs API to display a user's vinyl collection. Browse by user collection, album details (tracks, artists, credits), grid view with alphabetical search and built-in user selector.",
        'p2.f1': 'Modern UI with Material 3 and Jetpack Compose',
        'p2.f2': 'Screen navigation (collection / detail)',
        'p2.f3': 'Discogs REST API with Retrofit + OkHttp',
        'p2.f4': 'MVVM architecture with ViewModel',
        'p3.alt': 'Java Solar System project',
        'p3.desc': "3D solar system simulation developed in Java with LWJGL/OpenGL, ported from an original C++ version. Realistic rendering of textured planets, orbital mechanics, Saturn's rings, free camera and interactive ImGui interface.",
        'p3.f1': '3D rendering with realistic textures',
        'p3.f2': "Saturn's rings and lunar orbit",
        'p3.f3': 'Free camera and time control',
        'p3.f4': 'ImGui interface with planetary fact sheets',
        'p4.alt': 'Cosmos Explorer project',
        'p4.desc': 'Educational and interactive website dedicated to exploring the solar system. Detailed presentations of planets, asteroids, comets and astronomical observation services.',
        'p4.f1': 'Immersive interface',
        'p4.f2': 'Dynamic time counter',
        'p4.f3': 'Intuitive navigation',
        'p4.f4': 'Rich educational content',
        'p5.alt': 'Dominion Sign Group project',
        'p5.desc': "Corporate website celebrating Dominion Sign Group's 75 years of expertise. Presentation of services, achievements and company history with a modern and professional design.",
        'p5.f1': 'Modern and elegant design',
        'p5.f2': 'Dynamic anniversary counter',
        'p5.f3': 'Achievements gallery',
        'p5.f4': 'Responsive navigation',
        'contact.title': 'Contact Me!',
        'contact.intro': "As a junior Programmer-Analyst, I am actively looking for job opportunities in development to launch my career. Feel free to contact me to discuss your projects or collaboration opportunities.",
        'footer.rights': 'All rights reserved.'
    }
};

function detectLanguage() {
    const saved = localStorage.getItem('portfolio-lang');
    if (saved === 'fr' || saved === 'en') return saved;
    return (navigator.language || '').toLowerCase().startsWith('en') ? 'en' : 'fr';
}

let currentLang = detectLanguage();
const langButtons = document.querySelectorAll('.lang-btn');

function applyLanguage(lang) {
    currentLang = lang;
    document.documentElement.lang = lang;
    const strings = translations[lang];

    document.querySelectorAll('[data-i18n]').forEach(el => {
        const value = strings[el.getAttribute('data-i18n')];
        if (value !== undefined) el.textContent = value;
    });
    document.querySelectorAll('[data-i18n-html]').forEach(el => {
        const value = strings[el.getAttribute('data-i18n-html')];
        if (value !== undefined) el.innerHTML = value;
    });
    document.querySelectorAll('[data-i18n-alt]').forEach(el => {
        const value = strings[el.getAttribute('data-i18n-alt')];
        if (value !== undefined) el.setAttribute('alt', value);
    });
    document.querySelectorAll('[data-i18n-title]').forEach(el => {
        const value = strings[el.getAttribute('data-i18n-title')];
        if (value !== undefined) el.setAttribute('title', value);
    });

    langButtons.forEach(btn => {
        btn.classList.toggle('active', btn.getAttribute('data-lang') === lang);
    });
}

function setLanguage(lang) {
    localStorage.setItem('portfolio-lang', lang);
    applyLanguage(lang);
    startTyping();
    logWelcomeMessage();
}

function logWelcomeMessage() {
    if (currentLang === 'en') {
        console.log('%c👋 Hello! Thanks for visiting my portfolio!', 'color: #6366f1; font-size: 20px; font-weight: bold;');
        console.log('%cIf you are interested in my profile, feel free to contact me!', 'color: #ec4899; font-size: 14px;');
        console.log('%c🚀 Developed with passion by Vincent Lortie', 'color: #14b8a6; font-size: 12px;');
    } else {
        console.log('%c👋 Bonjour! Merci de visiter mon portfolio!', 'color: #6366f1; font-size: 20px; font-weight: bold;');
        console.log('%cSi vous êtes intéressé par mon profil, n\'hésitez pas à me contacter!', 'color: #ec4899; font-size: 14px;');
        console.log('%c🚀 Développé avec passion par Vincent Lortie', 'color: #14b8a6; font-size: 12px;');
    }
}

langButtons.forEach(btn => {
    btn.addEventListener('click', () => {
        const lang = btn.getAttribute('data-lang');
        if (lang !== currentLang) setLanguage(lang);
    });
});

applyLanguage(currentLang);

// ===========================
// Navigation Mobile
// ===========================
const hamburger = document.getElementById('hamburger');
const navMenu = document.getElementById('nav-menu');
const navLinks = document.querySelectorAll('.nav-link');

// Toggle menu mobile
hamburger.addEventListener('click', () => {
    hamburger.classList.toggle('active');
    navMenu.classList.toggle('active');
});

// Fermer le menu quand on clique sur un lien
navLinks.forEach(link => {
    link.addEventListener('click', () => {
        hamburger.classList.remove('active');
        navMenu.classList.remove('active');
    });
});

// ===========================
// Navigation Active State
// ===========================
const sections = document.querySelectorAll('section');
const navbar = document.getElementById('navbar');

window.addEventListener('scroll', () => {
    // Effet de scroll sur la navbar
    if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }

    // Mise à jour du lien actif
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;
        if (window.scrollY >= sectionTop - 200) {
            current = section.getAttribute('id');
        }
    });

    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('href') === `#${current}`) {
            link.classList.add('active');
        }
    });
});

// ===========================
// Smooth Scroll
// ===========================
navLinks.forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        const targetId = link.getAttribute('href');
        const targetSection = document.querySelector(targetId);
        
        if (targetSection) {
            const offsetTop = targetSection.offsetTop - 70;
            window.scrollTo({
                top: offsetTop,
                behavior: 'smooth'
            });
        }
    });
});

// ===========================
// Typing Effect
// ===========================
const typingText = document.querySelector('.typing-text');
let typingToken = 0;

function startTyping() {
    const token = ++typingToken;
    const textToType = translations[currentLang]['hero.typing'];
    typingText.textContent = '';
    let charIndex = 0;

    (function typeText() {
        if (token !== typingToken) return;
        if (charIndex < textToType.length) {
            typingText.textContent = textToType.substring(0, charIndex + 1);
            charIndex++;
            setTimeout(typeText, 100);
        }
    })();
}

// Démarrer l'effet de frappe après un court délai
setTimeout(startTyping, 1500);

// ===========================
// Skills Circle Carousel (fade-in/fade-out)
// ===========================
const skillsCircleBadges = document.querySelectorAll('#skills-circle .skill-item');

if (skillsCircleBadges.length) {
    const acquiredSkills = Array.from(document.querySelectorAll('#competences .skill-icon-item')).map(item => ({
        iconClass: item.querySelector('i').className,
        name: item.querySelector('.skill-name').textContent.trim()
    }));

    if (acquiredSkills.length) {
        const badgeCount = skillsCircleBadges.length;

        const rotateBadge = (badge, skillIndex) => {
            const icon = badge.querySelector('i');
            const span = badge.querySelector('span');
            const skill = acquiredSkills[skillIndex % acquiredSkills.length];

            icon.classList.add('skill-content-fade');
            span.classList.add('skill-content-fade');

            setTimeout(() => {
                icon.className = `${skill.iconClass} skill-content-fade`;
                span.textContent = skill.name;

                requestAnimationFrame(() => {
                    icon.classList.remove('skill-content-fade');
                    span.classList.remove('skill-content-fade');
                });
            }, 1500);
        };

        skillsCircleBadges.forEach((badge, i) => {
            let skillIndex = i;
            setInterval(() => {
                skillIndex += badgeCount;
                rotateBadge(badge, skillIndex);
            }, 3000);
        });
    }
}

// ===========================
// Skill Icons Animation
// ===========================
const skillIcons = document.querySelectorAll('.skill-icon-item');

const animateSkillIcons = () => {
    skillIcons.forEach((icon, index) => {
        const iconPosition = icon.getBoundingClientRect().top;
        const screenPosition = window.innerHeight;

        if (iconPosition < screenPosition) {
            setTimeout(() => {
                icon.style.opacity = '1';
                icon.style.transform = 'translateY(0)';
            }, index * 50);
        }
    });
};

// Initialiser l'état des icônes
skillIcons.forEach(icon => {
    icon.style.opacity = '0';
    icon.style.transform = 'translateY(20px)';
    icon.style.transition = 'all 0.5s ease';
});

// Observer pour les animations au scroll
const observerOptions = {
    threshold: 0.2,
    rootMargin: '0px 0px -100px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
        }
    });
}, observerOptions);

// Observer tous les éléments avec la classe fade-in
document.querySelectorAll('.fade-in').forEach(el => {
    observer.observe(el);
});

// Animer les icônes de compétences au scroll
window.addEventListener('scroll', animateSkillIcons);
animateSkillIcons(); // Appel initial



// ===========================
// Scroll Reveal Animations
// ===========================
const revealElements = document.querySelectorAll('.skill-category, .project-card, .about-content, .contact-content');

const revealOnScroll = () => {
    revealElements.forEach(element => {
        const elementTop = element.getBoundingClientRect().top;
        const elementBottom = element.getBoundingClientRect().bottom;
        const windowHeight = window.innerHeight;

        if (elementTop < windowHeight - 100 && elementBottom > 0) {
            element.style.opacity = '1';
            element.style.transform = 'translateY(0)';
        }
    });
};

// Initialiser l'état des éléments
revealElements.forEach(element => {
    element.style.opacity = '0';
    element.style.transform = 'translateY(30px)';
    element.style.transition = 'all 0.6s ease';
});

window.addEventListener('scroll', revealOnScroll);
revealOnScroll(); // Appel initial

// ===========================
// Parallax Effect for Hero
// ===========================
const heroContent = document.querySelector('.hero-content');

window.addEventListener('scroll', () => {
    const scrolled = window.pageYOffset;
    if (heroContent && scrolled < window.innerHeight) {
        heroContent.style.transform = `translateY(${scrolled * 0.5}px)`;
        heroContent.style.opacity = 1 - scrolled / 700;
    }
});

// ===========================
// Dynamic Year in Footer
// ===========================
const currentYear = new Date().getFullYear();
const footerYear = document.getElementById('current-year');
if (footerYear) {
    footerYear.textContent = currentYear;
}

// ===========================
// Cursor Trail Effect (Optional)
// ===========================
let mouseX = 0;
let mouseY = 0;
let cursorX = 0;
let cursorY = 0;

document.addEventListener('mousemove', (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;
});

// ===========================
// Preloader (Optional)
// ===========================
window.addEventListener('load', () => {
    document.body.classList.add('loaded');
});

// ===========================
// Performance Optimization
// ===========================
// Throttle function pour optimiser les événements scroll
function throttle(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Appliquer le throttle aux événements scroll
const throttledScroll = throttle(() => {
    animateSkillIcons();
    revealOnScroll();
}, 100);

window.addEventListener('scroll', throttledScroll);

// ===========================
// Accessibility Improvements
// ===========================
// Ajouter le support du clavier pour les liens de navigation
navLinks.forEach(link => {
    link.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            link.click();
        }
    });
});

// Focus visible pour l'accessibilité
document.addEventListener('keydown', (e) => {
    if (e.key === 'Tab') {
        document.body.classList.add('keyboard-nav');
    }
});

document.addEventListener('mousedown', () => {
    document.body.classList.remove('keyboard-nav');
});

// ===========================
// Moving Stars Effect
// ===========================
class Star {
    constructor(x, y) {
        this.x = x;
        this.y = y;
        this.size = Math.random() * 3 + 1;
        this.speedX = (Math.random() - 0.5) * 2;
        this.speedY = (Math.random() - 0.5) * 2;
        this.life = 100;
        this.opacity = 1;
    }

    update() {
        this.x += this.speedX;
        this.y += this.speedY;
        this.life -= 2;
        this.opacity = this.life / 100;
    }

    draw(ctx) {
        ctx.save();
        ctx.globalAlpha = this.opacity;
        
        // Draw star with glow effect
        const gradient = ctx.createRadialGradient(this.x, this.y, 0, this.x, this.y, this.size * 2);
        gradient.addColorStop(0, 'rgba(52, 152, 219, 1)');
        gradient.addColorStop(0.5, 'rgba(52, 152, 219, 0.5)');
        gradient.addColorStop(1, 'rgba(52, 152, 219, 0)');
        
        ctx.fillStyle = gradient;
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.size * 2, 0, Math.PI * 2);
        ctx.fill();
        
        // Draw bright center
        ctx.fillStyle = 'rgba(255, 255, 255, 0.9)';
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.size / 2, 0, Math.PI * 2);
        ctx.fill();
        
        ctx.restore();
    }
}

// Create canvas for stars
const canvas = document.createElement('canvas');
canvas.id = 'stars-canvas';
canvas.style.position = 'fixed';
canvas.style.top = '0';
canvas.style.left = '0';
canvas.style.width = '100%';
canvas.style.height = '100%';
canvas.style.pointerEvents = 'none';
canvas.style.zIndex = '9999';
document.body.appendChild(canvas);

const ctx = canvas.getContext('2d');
let stars = [];
let mouseXPos = 0;
let mouseYPos = 0;

// Resize canvas
function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
}

resizeCanvas();
window.addEventListener('resize', resizeCanvas);

// Track mouse position
document.addEventListener('mousemove', (e) => {
    mouseXPos = e.clientX;
    mouseYPos = e.clientY;
    
    // Create new stars at mouse position
    for (let i = 0; i < 3; i++) {
        stars.push(new Star(
            mouseXPos + (Math.random() - 0.5) * 10,
            mouseYPos + (Math.random() - 0.5) * 10
        ));
    }
});

// Animation loop
function animateStars() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    // Update and draw stars
    stars = stars.filter(star => star.life > 0);
    
    stars.forEach(star => {
        star.update();
        star.draw(ctx);
    });
    
    requestAnimationFrame(animateStars);
}

animateStars();

// ===========================
// Console Message
// ===========================
logWelcomeMessage();
