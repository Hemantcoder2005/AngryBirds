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

[![](https://mermaid.ink/img/pako:eNqlV99P2zAQ_leiPJWNIrHHaJrUFg0hAUPrxDQpL9f4aAyOHdkOUDH423dO0ibOj4LWPlTO3Xfn8_nuu-QlTBTDMAoTAcaccVhryGIZ0K-UBOeQYfBSSdzvs7GgrZNOjlpSlKwn02iwj0xAJIUAi8tEaV9l4BHb-NdYtkO5xEcUVyBhjdoLifG7O05O7SYKllZzuW47RYGJLW0nXNpAuNV1ka1Qe2FppJgWhbEqq8B7gvB2bzmMAtqhpeJmrowpTaJgpZRAkC11rghu-kYLleUCLbIhozL_dYRBS56Vibnha-PltBLPuWZD8gVYyClxAypKZJHYQo_dxk7vJWMlVPJAR7rkxr7N3cObd7QzNFarDbLJ0dDhNNURar8okhRZIcbCKPfwQsjoHjUHyvhVvWopUwRh027KLTzgGWR06rJEWLk8-mDgnYB-K8W8oDr6c_e_D7C0iGIfYK5UNlfP3qHxORfUyCNJ2ibC7xqUhruW8XNBx0S59nLU3Z-KyfOUK3LElYyCG-U7oypVSbnJLTWh0u22gUIm6aSS017kAcRtjR8kjF8a7kv0ZuSYP5H1Yqu2WeaYkPvZigvyPlpKBR5i_weFUE-HRQDJwyEOfqfcHnSGc40oD3FABOSZDjfcf1XMB7p0Jvgjvteh10pnIFqBdvQzndFkYuOA72DsuNZR_rh2y7hekoBnHufVvVHew4r-2jpg9zSkZnItqjSAW_UBN-qJiNQBcrcavqyZoQl9qYB1Bqog0VIVkvlROWmuqcJ64jnV7Vo7i4vyfkZqw921t9FztzA247RTM4VHYT171nHQmF9kOST-7ilIJnChhOCGinHyY-X4JVCr-9PjoHn4MshGdSW-Y7M7Q_keFYencRhMp9_qVfuNpoJ57ziD8BbOB5ycfKIHVzPvQKg4RxG02lboPsxu-Feg5l1gKCI3yipcNdW-_p1OmzHZ0zQDsqdqRmNPVQ_FWu4apxTXI6Er3lJ9V95QeN-ipuauYke5XcWOSiuFo4RSvqOfjryhnY6ippuOtKaZvRdAq6rsK1S73QcuypVoLMPjMEOKkDP6Kii7JQ5tiqQKI1oy0A9xGMtXwkFh1XIjkzCi7fE4JAJYp2F0B8LQU5Ez1ybVJ0UFef0HGAK9Vw?type=png)](https://mermaid.live/edit#pako:eNqlV99P2zAQ_leiPJWNIrHHaJrUFg0hAUPrxDQpL9f4aAyOHdkOUDH423dO0ibOj4LWPlTO3Xfn8_nuu-QlTBTDMAoTAcaccVhryGIZ0K-UBOeQYfBSSdzvs7GgrZNOjlpSlKwn02iwj0xAJIUAi8tEaV9l4BHb-NdYtkO5xEcUVyBhjdoLifG7O05O7SYKllZzuW47RYGJLW0nXNpAuNV1ka1Qe2FppJgWhbEqq8B7gvB2bzmMAtqhpeJmrowpTaJgpZRAkC11rghu-kYLleUCLbIhozL_dYRBS56Vibnha-PltBLPuWZD8gVYyClxAypKZJHYQo_dxk7vJWMlVPJAR7rkxr7N3cObd7QzNFarDbLJ0dDhNNURar8okhRZIcbCKPfwQsjoHjUHyvhVvWopUwRh027KLTzgGWR06rJEWLk8-mDgnYB-K8W8oDr6c_e_D7C0iGIfYK5UNlfP3qHxORfUyCNJ2ibC7xqUhruW8XNBx0S59nLU3Z-KyfOUK3LElYyCG-U7oypVSbnJLTWh0u22gUIm6aSS017kAcRtjR8kjF8a7kv0ZuSYP5H1Yqu2WeaYkPvZigvyPlpKBR5i_weFUE-HRQDJwyEOfqfcHnSGc40oD3FABOSZDjfcf1XMB7p0Jvgjvteh10pnIFqBdvQzndFkYuOA72DsuNZR_rh2y7hekoBnHufVvVHew4r-2jpg9zSkZnItqjSAW_UBN-qJiNQBcrcavqyZoQl9qYB1Bqog0VIVkvlROWmuqcJ64jnV7Vo7i4vyfkZqw921t9FztzA247RTM4VHYT171nHQmF9kOST-7ilIJnChhOCGinHyY-X4JVCr-9PjoHn4MshGdSW-Y7M7Q_keFYencRhMp9_qVfuNpoJ57ziD8BbOB5ycfKIHVzPvQKg4RxG02lboPsxu-Feg5l1gKCI3yipcNdW-_p1OmzHZ0zQDsqdqRmNPVQ_FWu4apxTXI6Er3lJ9V95QeN-ipuauYke5XcWOSiuFo4RSvqOfjryhnY6ippuOtKaZvRdAq6rsK1S73QcuypVoLMPjMEOKkDP6Kii7JQ5tiqQKI1oy0A9xGMtXwkFh1XIjkzCi7fE4JAJYp2F0B8LQU5Ez1ybVJ0UFef0HGAK9Vw)