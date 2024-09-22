# Angry Birds by Codesmashers

## **Actors:**
1. **Player**: The primary user who interacts with the game, controlling the gameplay, birds, and other elements.
2. **Game System**: The internal system that manages game physics, bird trajectories, structure impacts, and game logic.

---

## **Use Cases:**

### 1. **Start Game**
   - **Description**: The player starts a new game session.
   - **Actor**: Player
   - **Precondition**: The game must be installed and running.
   - **Postcondition**: The game shows the starting screen with available levels or the main menu.

### 2. **Select Level**
   - **Description**: The player selects a specific level to play.
   - **Actor**: Player
   - **Precondition**: The game must be in the main menu or level selection mode.
   - **Postcondition**: The chosen level loads, showing the birds, pigs, and structures.

### 3. **Create Custom Level**
   - **Description**: The player creates a custom level with their choice of birds, pigs, and structures.
   - **Actor**: Player
   - **Precondition**: The custom level creation feature is unlocked or available.
   - **Postcondition**: The game saves and loads the custom level for the player to play or edit.

### 4. **Aim and Shoot**
   - **Description**: The player uses the catapult to aim and launch a bird towards the structure.
   - **Actor**: Player
   - **Precondition**: The player has control of the catapult and birds.
   - **Postcondition**: A bird is launched, and the game calculates its trajectory using the game physics engine.

### 5. **Use Special Ability**
   - **Description**: The player triggers a special ability for a specific bird type during flight.
   - **Actor**: Player
   - **Precondition**: The player must have launched a bird with a special ability.
   - **Postcondition**: The special ability is activated, causing additional damage or unique effects.

### 6. **View Score**
   - **Description**: The player views their score at the end of the level based on how many pigs were destroyed and how many birds were used.
   - **Actor**: Player
   - **Precondition**: The level must be completed.
   - **Postcondition**: The score is displayed, and the player can either replay the level or proceed to the next one.

### 7. **End Game**
   - **Description**: The player can choose to end the game or the game automatically ends after all levels are completed.
   - **Actor**: Player
   - **Precondition**: The player completes the final level or manually exits.
   - **Postcondition**: The game ends, showing the final score or game completion screen.

---

## **Relationships:**

- **Player → Start Game**: A player can start the game.
- **Player → Select Level**: A player can choose a level to play.
- **Player → Create Custom Level**: A player can create a custom level (optional).
- **Player → Aim and Shoot**: A player can aim and launch a bird using the catapult.
- **Player → Use Special Ability**: A player can trigger a bird's special ability after launching.
- **Player → View Score**: A player can view the score at the end of each level.
- **Player → End Game**: A player can end the game either manually or by completing all levels.
- **Game System → Use Special Ability**: The system manages the mechanics of special abilities for each bird.
- **Game System → Calculate Physics**: The game system calculates bird trajectory and impact using physics.

---

## **Diagram Multiplicities**:
- **1 Player → 1 Game System**
- **1 Player → 1 Start Game**
- **1 Player → 1 Select Level**
- **1 Player → 1..* Aim and Shoot**
- **1 Player → 0..1 Create Custom Level**
- **1 Game System → 1..* Use Special Ability**
- **1 Game System → 1..* Calculate Physics**

