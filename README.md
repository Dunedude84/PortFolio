# Portfolio - Vincent Lortie

**English** | [Français](README.fr.md)

Professional portfolio built for my job search as a Junior Programmer-Analyst.

## 🎯 Purpose

This portfolio was created to showcase my skills, projects and professional background in software development.

## 🌐 Bilingual FR / EN

The website is fully bilingual (French / English):

- Language switcher (FR / EN buttons) in the navigation bar
- All content translated via `data-i18n` attributes and a translations dictionary in `js/script.js`
- Browser language detection on first visit, preference saved in `localStorage`
- CV available for download in both languages (`vincent_lortie_CV_fr.pdf` / `vincent_lortie_CV_en.pdf`)

## 🚀 Technologies Used

### Front-End
- **HTML5** - Modern semantic structure
- **CSS3** - Advanced styles with animations and transitions
- **JavaScript (Vanilla)** - Interactivity, animations and internationalization
- **Font Awesome** - Vector icons

### Back-End & Desktop
- **Java** - Object-oriented programming
- **JavaFX / LWJGL / OpenGL** - Graphical interfaces and 3D rendering for desktop applications
- **FXML** - UI definitions for JavaFX
- **Node.js / Express** - Back-end APIs for web applications
- **MongoDB** - NoSQL database

## 📋 Sections

1. **Home** - Introduction with animated typing effect
2. **About** - Background and personal introduction
3. **Skills** - Technical and soft skills (HTML5, CSS3, JavaScript, Java, JavaFX, FXML, SQL, etc.)
4. **Projects** - Showcase of my work, in order:
   1. Horaires Pharmacie (Pharmacy Scheduler)
   2. ATM Simulation (WPF Desktop App)
   3. Discogs API (Android Application)
   4. Solar System Java (3D Simulation)
   5. Cosmos Explorer
   6. Dominion Sign Group
5. **Contact** - Contact information and professional links

## ✨ Features

### Design & UX
- Modern dark space theme with binary "matrix" animations
- Responsive layout (mobile, tablet, desktop)
- Smooth, professional animations
- Consistent color palette with glow effects
- Intuitive navigation with hamburger menu on mobile

### Animations
- Typing effect on the main title (restarts in the selected language)
- Scroll-triggered animations (fade-in, slide-in)
- Rotating skill badge carousel
- Hover effects on cards and buttons
- Subtle parallax on the hero section
- Animated star trail following the cursor

### Interactivity
- Smooth scroll navigation
- Responsive mobile menu
- Active section indicator in the navigation
- Instant FR/EN language switching
- CV download in French or English

### Accessibility
- Keyboard navigation
- Proper ARIA attributes
- Optimized color contrast
- Visible focus for keyboard navigation
- `lang` attribute updated on language switch

## 📁 Project Structure

```
Portfolio/
│
├── index.html                      # Main page
├── css/
│   └── style.css                   # Styles and animations
├── js/
│   └── script.js                   # Logic, interactivity and FR/EN translations
├── images/                         # Portfolio images
├── DOMINION/                       # Dominion Sign Group project
├── Siteweb_systeme_solaire/        # Cosmos Explorer project
├── vincent_lortie_CV_fr.pdf        # Resume (French)
├── vincent_lortie_CV_en.pdf        # Resume (English)
├── README.md                       # Documentation (English)
└── README.fr.md                    # Documentation (français)
```

> Note: the following projects are developed and deployed in separate
> repositories and are not included here. Links are provided in the
> Projects section of the portfolio:
> - **Horaires Pharmacie** (`hey-hi.ca`)
> - **Discogs API (Android)** ([Dunedude84/discogs-api](https://github.com/Dunedude84/discogs-api))
> - **Solar System Java** ([Dunedude84/solar-system-java](https://github.com/Dunedude84/solar-system-java))

## 🎨 Color Palette

- **Primary**: #3498db (Space Blue)
- **Primary Dark**: #102e50 (Deep Blue)
- **Primary Light**: #5dade2 (Light Blue)
- **Text**: #ffffff / #d1d5db
- **Background**: #000000 / #0a0a0a

## 🌐 Featured Projects

### 1. Horaires Pharmacie (Pharmacy Scheduler)
Complete web application for pharmacy schedule management.
- **Technologies**: Node.js, Express, MongoDB
- Admin/employee authentication
- Schedule generation and availability management
- Summary of hours worked
- Read-only guest mode for demonstration
- At login, the site offers **two tabs**: an **Employee** tab and an **Administrator** tab.
  The guest credentials (`guest038` / `1357`) work on both tabs, in read-only mode (no editing rights).
- Live demo: [hey-hi.ca](https://hey-hi.ca)

### 2. ATM Simulation (Guichet Automatique)
Complete ATM simulation — WPF desktop app, MVVM architecture, EF Core + SQL Server. Capstone project of the LEA.9C program.
- **Technologies**: C#, WPF, Microsoft.Toolkit.Mvvm, EF Core 3.1, SQL Server
- Client PIN authentication and admin portal
- Deposits, withdrawals, transfers, bill payments, interest, line of credit, mortgage debits
- 4 account types, 9 transaction types, migrations and seed data
- GitHub repository: [Dunedude84/atm-simulation](https://github.com/Dunedude84/atm-simulation)

### 3. Discogs API (Android Application)
Native Android application that queries the Discogs API to display a vinyl collection.
- **Technologies**: Kotlin, Jetpack Compose, Retrofit, Coil, MVVM
- Material 3 UI and screen navigation (collection / detail)
- User selector, alphabetical grid, details for each album
- GitHub repository: [Dunedude84/discogs-api](https://github.com/Dunedude84/discogs-api)

### 4. Solar System Java (3D Simulation)
3D solar system simulation in Java with LWJGL/OpenGL.
- **Technologies**: Java, LWJGL, OpenGL, JOML, Maven
- 3D rendering with realistic textures, Saturn's rings, lunar orbit
- Free camera, time control, ImGui interface
- GitHub repository: [Dunedude84/solar-system-java](https://github.com/Dunedude84/solar-system-java)

### 5. Cosmos Explorer
Educational website about the solar system.
- **Technologies**: HTML5, CSS3, JavaScript, Animations
- Immersive interface
- Rich educational content
- Dynamic time counter
- Intuitive navigation

### 6. Dominion Sign Group
Corporate website celebrating the company's 75 years of expertise.
- **Technologies**: HTML5, CSS3, JavaScript, Responsive Design
- Professional and elegant design
- Dynamic anniversary counter
- Achievements gallery
- Responsive navigation

## 📱 Responsive Design

The portfolio is fully responsive and optimized for:
- 📱 Mobile (< 640px)
- 📱 Tablet (640px - 968px)
- 💻 Desktop (> 968px)

## 🔧 Installation & Usage

1. Clone or download the project
2. Open `index.html` in a modern browser
3. No dependencies or installation required

## 📝 Customization

To customize the portfolio:

1. **Content & translations**: Edit the `translations` object in `js/script.js` (both `fr` and `en`) and the default French markup in `index.html`
2. **Colors**: Adjust the CSS variables in `:root` (style.css)
3. **Projects**: Add/edit projects in the `#projets` section
4. **Skills**: Adjust the technologies and icons in the `#competences` section

## 🎯 Optimizations

- Code optimized for performance
- Scroll event throttling
- CSS animations rather than JavaScript when possible
- Optimized images

## 📧 Contact

For any question or professional opportunity:
- **Email**: vincelortie@gmail.com
- **LinkedIn**: [Vincent Lortie](https://www.linkedin.com/in/vincent-lortie-b55986253)
- **GitHub**: [@Dunedude84](https://github.com/Dunedude84/)

## 📄 License

© 2026 Vincent Lortie. All rights reserved.

---

**Note**: This portfolio is built for a job search in software development. Feel free to contact me to discuss professional opportunities!
