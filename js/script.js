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
const textToType = 'Programmeur-Analyste';
let charIndex = 0;

function typeText() {
    if (charIndex < textToType.length) {
        typingText.textContent = textToType.substring(0, charIndex + 1);
        charIndex++;
        setTimeout(typeText, 100);
    }
}

// Démarrer l'effet de frappe après un court délai
setTimeout(() => {
    typingText.textContent = '';
    typeText();
}, 1500);

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
const footerText = document.querySelector('.footer-content p');
if (footerText) {
    footerText.innerHTML = footerText.innerHTML.replace('2025', currentYear);
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
console.log('%c👋 Bonjour! Merci de visiter mon portfolio!', 'color: #6366f1; font-size: 20px; font-weight: bold;');
console.log('%cSi vous êtes intéressé par mon profil, n\'hésitez pas à me contacter!', 'color: #ec4899; font-size: 14px;');
console.log('%c🚀 Développé avec passion par Vincent Lortie', 'color: #14b8a6; font-size: 12px;');
