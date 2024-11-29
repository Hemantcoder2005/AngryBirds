# Angry Birds 

## Contributors
- **Hemant Narula**  
- **Girik Aggarwal**

---

## Introduction  
This project is a clone of the popular game Angry Birds, featuring unique gameplay where players launch birds to defeat structures and enemies. The game is designed to be fun and challenging, offering multiple levels and features for an immersive experience. Players can even create their own custom levels!

---

## Features  

### 1. **Game Levels**  
- **6 Levels**: Pre-built levels that gradually increase in difficulty.  
- **Custom Level Creator**: Allows players to design and play their own levels.  

### 2. **GamePlay**
- Slingshot birds to destroy pigs and structures.
- Various types of birds and pigs used for different levels

### 3. **Save Game Feature**  
- The game saves the player's progress and state.  
- Players can resume the game from where they left off.  

### 4. **Stars Feature**  
- Players are awarded stars based on the number of birds used to clear a level.  
- Encourages strategic gameplay.  

### 5. **Damage System**  
- Structures and pigs take damage when hit by birds.  
- Includes realistic interactions where objects can break or topple when hit.  

### 6. **Screens**  
The game transitions through several screens for a seamless user experience:  
- **Intro Screen**: A short story introduces the game while assets load in the background.  
- **Main Menu**: Options for starting the game, settings, and credits.  
- **Level Selector**: Visually choose levels to play.  
- **Game Screen**: The main gameplay screen.  
- **Win Screen**: Displays after completing a level successfully.  
- **Lose Screen**: Displays when the player fails a level.  

### 7. **Pause Menu**  
Available during gameplay with the following options:  
- Resume Game  
- Restart Level  
- Quit to Main Menu  
### 8. **Technical Features**:
- Developed using *libGDX* for game rendering.
- *Box2D* for realistic physics.

### 9. **UI/UX**:
- Static GUI with screens for:
        - Loading Screen, Main Menu, Level Selection, Game Screen.
        - Save, Load, Pause, Win, and Lose Windows.
- Interactive buttons and visual feedback for user actions.
- Basic sound controls.
- SFX for clicks and gameplay.

### 10. **Keyboard Commands**  
- **Enter**: Transition from the intro screen to the splash screen after assets are loaded.  
- **M**: Mute/unmute game audio.  
- **Q**: Quit the game.  


## Project Structure
1. Intro Screen: Prepares the player for the game while assets load.  
2. Main Menu: Serves as the central hub for navigation.  
3. Level Selector: Lets players choose levels or access the custom level creator.  
4. Game Screen: Where gameplay occurs.  
5. Win/Lose Screens: Provide feedback on the outcome of each level.  


## Resources
Images and assets used in this project are based on resources from the Angry Birds Wiki:
https://angrybirds.fandom.com/wiki/Angry_Birds_Wiki

---
## Demo Video Link
https://drive.google.com/file/d/1r9K2uhwPad_tmGyO_PaNckeegQKWmjoW/view?usp=sharing

## To Run  
Make sure you have the necessary dependencies installed. Then, use the following command:  

1. *Clone the Repository*:
   ```bash
   git clone "https://github.com/Hemantcoder2005/AngryBirds.git"


2. *Import the Project*:
    - Use IntelliJ IDEA with Gradle support.
3. *Command to run the game*
```bash
./gradlew run
