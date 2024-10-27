# Angry Birds 

## Contributors
- **Hemant Narula**
- **Girik Aggarwal**

## Introduction
This project is a clone of the popular game Angry Birds. It features a unique gameplay experience where players can launch birds to defeat structures and enemies. The game is designed to be engaging and fun, with various levels and challenges.

## Project Structure
The game consists of several key components:

1. **Intro Screen**: 
   - Displays a short story to introduce the game.
   - Loads game assets in the background for a smooth experience.

2. **Main Menu**: 
   - Provides options for starting the game, accessing settings, and viewing credits.

3. **Level Selector**: 
   - Allows players to choose from different levels to play.
   - Each level is represented visually for easy selection.

4. **Gameplay**:
   - The core of the game where players interact and launch birds.
   - Serializes data from assets/cache/1.json (or 2.json, depending on the level being played).
   - Includes a pause screen with the following functionalities:
     - Resume Game
     - Restart Level
     - Quit to Main Menu

## Gameplay Features
- Intuitive controls for launching birds.
- Various levels with increasing difficulty.
- Engaging sound effects and background music.
- Smooth animations and graphics.

## To run
```bash
./gradlew run
```
- Run above command in terminal
- Make sure java is installed in local environment

