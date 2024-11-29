# Angry Birds Clone  

## Contributors  
- **Hemant Narula**  
- **Girik Aggarwal**  

---

## Introduction  
This project is a clone of the popular game **Angry Birds**, featuring unique gameplay where players launch birds to defeat structures and enemies. The game is designed to be fun and challenging, offering multiple levels and features for an immersive experience. Players can even create their own custom levels!

---

## Features  

### 1. **Game Levels**  
- **6 Pre-built Levels**: Levels gradually increase in difficulty, keeping the game engaging.  
- **Custom Level Creator**: Players can create their own levels by dragging and dropping blocks, pigs, and other elements into place.  

### 2. **Gameplay**  
- Use a slingshot to launch birds and destroy structures and pigs.  
- Various bird types with unique abilities, making gameplay dynamic and strategic.

### 3. **Save Game Feature**  
- Automatically saves the player's progress and game state.  
- Players can resume the game from where they left off.

### 4. **Stars System**  
- Players are awarded stars based on the number of birds used to clear a level.  
- Encourages strategic gameplay and efficient resource management.

### 5. **Damage System**  
- Structures and pigs take damage when hit by birds.  
- Realistic physics interactions, where objects break, topple, or collapse upon impact.

### 6. **User Interface (UI) and Screens**  
The game features multiple screens for a seamless user experience:  
- **Intro Screen**: A short story introduces the game while assets load in the background.  
- **Main Menu**: Options for starting the game, adjusting settings, and viewing credits.  
- **Level Selector**: Visually select levels to play or create custom levels.  
- **Game Screen**: The main gameplay area.  
- **Win Screen**: Displays after successfully completing a level.  
- **Lose Screen**: Displays when the player fails to complete a level.  

### 7. **Pause Menu**  
The pause menu is accessible during gameplay and includes options to:  
- Resume Game  
- Restart Level  
- Quit to Main Menu  

### 8. **Technical Features**  
- Developed using **libGDX** for game rendering.  
- **Box2D** for realistic physics simulations.

### 9. **UI/UX**  
- Static GUI with screens for:  
  - Loading Screen, Main Menu, Level Selection, Game Screen.  
  - Save, Load, Pause, Win, and Lose Windows.  
- Interactive buttons with visual feedback for user actions.  
- Sound effects for clicks and gameplay actions.

### 10. **Keyboard Commands**  
- **Enter**: Transition from the intro screen to the main menu after assets are loaded.  
- **M**: Mute or unmute the game audio.  
- **Q**: Quit the game.

---

## Project Structure  
1. **Intro Screen**: Prepares the player for the game while assets load.  
2. **Main Menu**: Central hub for navigation.  
3. **Level Selector**: Lets players choose levels or access the custom level creator.  
4. **Game Screen**: Main gameplay area.  
5. **Win/Lose Screens**: Provide feedback on the outcome of each level.

---

## Demo Video  

<iframe width="560" height="315" src="https://drive.google.com/file/d/1r9K2uhwPad_tmGyO_PaNckeegQKWmjoW/preview" frameborder="0" allowfullscreen></iframe>

---

## To Run  
Follow these steps to run the project:

1. **Clone the Repository**:
```bash
git clone "https://github.com/Hemantcoder2005/AngryBirds.git"
```
2. **Run the Game**:
```bash
./gradlew run
```
