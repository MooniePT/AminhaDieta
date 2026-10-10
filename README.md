# A Minha Dieta – Diet & Fitness Tracker (JavaFX)

A desktop application for tracking diet, hydration, exercise and weight. It calculates personalised calorie and macronutrient goals and shows progress through charts and PDF reports.

> **Academic group project** – Object-Oriented Programming course, University of Beira Interior (UBI).
> Team: Carlos Farinha, João Rodrigues, Henrique Marques, André Schroder.

## Screenshots

<!-- Add screenshots to docs/screenshots/ and uncomment:
![Dashboard](docs/screenshots/dashboard.png)
![Meals](docs/screenshots/meals.png)
![History and PDF export](docs/screenshots/history.png)
-->

## Features

- **Multiple user profiles** with automatic BMR (Mifflin-St Jeor), daily calorie target and BMI classification
- **Food diary** with a customisable food database (kcal, protein, carbs, fat per 100 g) and quick-add shortcuts
- **Hydration tracking** with a daily goal based on body weight (35 ml/kg)
- **Exercise log** with predefined and custom activities, and a 7-day calories-burned chart
- **Dashboard** with progress bars, a macronutrient pie chart and a weight-evolution line chart
- **History** with search, date filters and **PDF export**
- **Customisable UI**: animated or static background colours and selectable fonts
- **Local persistence** using Java serialization (no external database needed)

## Tech Stack

- Java 21
- JavaFX 21 (FXML + CSS)
- Maven (javafx-maven-plugin, maven-shade-plugin)
- OpenPDF (PDF reports)
- Inno Setup (Windows installer)

## Download

A Windows installer is available on the [Releases page](https://github.com/MooniePT/AminhaDieta/releases/latest).

## Getting Started

**Requirements:** JDK 21+ and Maven 3.6+

```bash
git clone https://github.com/MooniePT/AminhaDieta.git
cd AminhaDieta/AminhaDieta
mvn clean javafx:run
```

To build a runnable fat JAR:

```bash
mvn clean package
java -jar target/AMinhaDieta-1.0.0.jar
```

No Maven installed? On Windows, `build_with_portable_maven.ps1` downloads a temporary Maven and runs the build.

## Project Structure

```
AminhaDieta/
├── AminhaDieta/                 # Maven project
│   ├── pom.xml
│   ├── setup.iss                # Inno Setup installer script
│   └── src/main/
│       ├── java/app/
│       │   ├── Main.java / Launcher.java   # Entry points
│       │   ├── model/           # Domain classes (UserProfile, MealEntry, Food, ...)
│       │   ├── persistence/     # DataStore – saves/loads app state
│       │   └── ui/              # SceneManager + one controller per screen
│       └── resources/
│           ├── fxml/            # Screen layouts
│           ├── css/             # Styles
│           └── images/          # Icons
└── docs/
    ├── GIT_MANUAL.md            # Team Git workflow (Portuguese)
    └── study-guide-pt/          # Study notes on the code (Portuguese)
```

The application follows an **MVC** structure: models in `app.model`, views in FXML/CSS, and controllers in `app.ui.controller`.

## What I Learned

- Designing an object-oriented domain model and applying MVC in a real desktop app
- Building UIs with JavaFX, FXML and CSS, including charts and scene navigation
- Managing builds and dependencies with Maven and packaging a Windows installer
- Working in a 4-person team with Git branches and pull requests
