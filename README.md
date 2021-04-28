# Tankathon
[![firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?&styleflat&logo=firebase)](https://firebase.google.com/)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.31-red.svg)](http://kotlinlang.org/)
[![LibGDX](https://img.shields.io/badge/libgdx-1.9.14-green.svg)](https://libgdx.badlogicgames.com/)
[![LibKTX](https://img.shields.io/badge/libktx-1.9.14--b1-blue.svg)](https://libktx.github.io/)

This Project has been developed with :heart: by
```
Marius Bæver Larsen     mariubla@stud.ntnu.no   Student @ NTNU
Johan M E Johnsen       jmjohnse@stud.ntnu.no   Student @ NTNU
Kristian Flock          krisfloc@ntnu.no        Student @ NTNU
Øystein Morken Linde    oysteiml@stud.ntnu.no   Student @ NTNU
Bjørn Nygard Lerberg    btlerber@stud.ntnu.no   Student @ NTNU 
```

## Run the game :rocket:
### Prerequisites
One of
- Android phone
- Android emulator
  - For mac we suggest Bluestack

### Installation
1. Download the [latest release](https://github.com/mariusblarsen/TDT4240-tankathon/releases/latest) of the APK.
2. Install the APK on your device/emulator.
3. Enjoy :tada:


## Background :mag_right:
Tankathon is an Android game built while taking NTNU’s software architecture course, TDT4240, taught by Alf Inge Wang. Therefore the background for the  development process has been to learn about architectural design.

## The game :video_game:
The objective of the game is to have fun, and be able to compete with your friends at the highscore list.   

## The technologies :hammer_and_wrench:
The game is developed in using Kotling with the gamedevelopment framwork LibGDX and LibKTX. The LibDGX framework provides useful utilities and LibKTX provides Kotlin extension for the framework. Firebase is used as the database to keep track of highscores. 


## Structure :open_file_folder:
Main folders of the project

    .
    ├── android         # Code and assets for specific for android and firebase
    ├── core            # Main implementation
    ├── desktop         # Contains desktop launcher, only used during development
    └── README.md

### Entity Component System
    .
    ├── ...
    ├── core/src/tdt4240/tankathon/game/ecs
    │   ├── component       # All components
    │   ├── system          # All systems
    │   └── ECSengine.kt    # Custom pooled engine to handle entities
    └── ...

### Firebase
    .
    ├── ...
    ├── android/src/tdt4240/tankathon/game
    │   └── AndroidInterface.kt             # Implementation of the FirebaseInterface
    ├── core/src/tdt4240/tankathon/game
    │   └── FirebaseInterface.kt            # Interface for firebase logic
    └── ...

### Assets
    .
    ├── ...
    ├── android
    │   ├── assets
    │   │   ├── Neon_UI_Skin            # Assets used for UI
    │   │   ├── map                     # Tilemaps and responding tilesets
    │   │   └── ...                     # Rest of sprites used for the game objects
    │   └── ...
    └── ...




