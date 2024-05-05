![image](https://github.com/ayman-art/Fight-Enemies/assets/52029584/eb67bb7c-16e2-43a2-8b83-1c4b8749a1be)


# Jengen Game Engine
   - Jengen is a simple 2D game engine written in Java. It is used to create 2D Tile-based Desktop games.
   - It is designed to be easy to use and flexible enough to support a wide range of games. Jengen provides a simple API for rendering graphics, handling input, and managing game state. It also includes support for physics, sound, and networking. Jengen is designed to be extensible, so you can easily add new features and customize it to suit your needs.

## Features

- **[Graphics](#graphics)**: Jengen provides a simple API for rendering sprites, tiles, and animations on the screen. Besides, it supports the minimap feature.

- **[Physics](#physics)**: Jengen includes a basic physics model for handling collisions and movement of:
    - Mobs
    - projectiles
    - particles
    - Effects(Special Powers).

- **[Sound](#sound)**: Jengen supports an audio API for playing sound effects and eventsounds in the game.

- **[GUI](#gui)**: Jengen provides a simple GUI for displaying game information and controls. Besides, it supports the minimap feature. Also, it supports an easy way to transition between different States.

- **[Input Handling](#input-handling)**: Jengen supports keyboard and mouse input for controlling the game.

- **[Winning State](#winning-state)**: Jengen supports different winning states for the game.

- **[Game Context](#game-context)**: Jengen provides a game context for setting up the game environment and parameters.

- **[Pause-Resume](#pause-resume)**: Jengen supports pausing and resuming the game.

## Advanced Features

- **[Multi-Player Support](#multi-player-support)**: Jengen also supports multiplayer games with multiple players. It uses UDP for communication between players.
- **[Level Creation](#level-creation)**: Jengen provides a simple API for creating and loading levels from files.
    - It supports different types of levels like
        - SpawnLevel (from predefined images or txt files)
        - RandomLevel (fully random level) (Advanced)
- **[AI](#ai)**: Jengen includes a basic AI system for controlling the behavior of non-player characters (NPCs).
- **[Security](#security)**: Jengen provides a simple way to encrypt and decrypt the game data. This is used to prevent cheating or tampering with the game data.
- **[Recording](#recording)**: Jengen supports recording the game in an efficient way.

---
## Demos(Most of Engine Features & MultiPlayer Mode)



https://github.com/ayman-art/Fight-Enemies/assets/52029584/b5edc26c-9c30-4bbf-ac0b-da3fb323c285



https://github.com/ayman-art/Fight-Enemies/assets/52029584/ab8b46a5-1f30-43f9-996e-4317225a2363


## Design Patterns Used

1- **Builder Pattern**: Used to create Game Context objects to allow for easy and flexible configuration of the game environment.  

2- **Prototype Pattern**: Used to create copies of objects like mobs, projectiles, and effects. This helps in beginning the recording of the game.  

3- **State Pattern**: Used to manage the GUI states and transitions between different states including Main Menu, Playing Game, Settings, About.  

4- **Factory Pattern**: Mainly used in creating the level mobs and effects from the level files.  

5- **Singleton Pattern**: Used to create a single instance of the Server and Client Controllers(used in networking).  

6- **Decorator Pattern**: Used to add Special Powers to the player in a flexible way which allow for multiple combinations of powers(even the same type!). 

7- **Flyweight Pattern**: Used a lot! one of usage was to share the same sprite for multiple entities to save memory and improve performance.  

8- **Command Pattern**: Used to create commands for the server and client to communicate with each other.  

9- **Memento Pattern**: Used to save the game state and restore it when needed. It is used to begin playing record.  

10- **Producer-Consumer - like Pattern**: Used in generating the random levels. The producers thread generates the level and the consumers(actual level) consumes only the first generated level and stops the still running producers.  

11- **Strategy Pattern**: Used to determine the winning state of the game. It allows for different winning states to be implemented.  



## Features Details and How to Use Them

## Graphics
- Jengen Rendering System is based on Pixel-Map Rendering.
- Jengen supports making sprites from sprite sheets in a simple way. It supports operations on sprites like flipping, rotating, extracting.
- Jengen supports making animations from sprite sheets. This make it easy to animate the sprites with separting the animation logic from the rendering logic.
- It assumes that the color 0xff00ff00 is the transparent color of the sprite.
- Jengen supports rendering the MiniMap of the level. This allows the player to see the whole level and the position of the player and the mobs.
- Jengen supports Blending Colors. This is used in both the minimap and the Invisibility Effect.


<br>
<br>

![bbe7d7c6-5e8d-4be6-a624-53d6835b8ff3](https://github.com/ayman-art/Fight-Enemies/assets/52029584/3c303496-0f18-45b0-8801-da19cb6c22c7)



## Physics
- Jengen supports Collision Detection with a varity of properties:
  - Breakable Tiles: The player can break the tiles if it has the required power.
  - NonBreakable Tiles: The player can't break the tiles. This is used mainly in the level boundaries.
  - Solid Tiles: It causes collision with the player and the mobs. Also the projectiles can't pass through it, as well as the particles.4
  - NonSolid Tiles: The player and the mobs can pass through it. Also the projectiles and the particles can pass through it.
  - Jengen supports Particle Physics. The particles can collide with the solid tiles and the mobs. Also, they can bounce off the solid tiles making a realistic effect.

- Besides, They are special powers implemented with Encapsulation and Decorator Pattern. The player can have multiple powers at the same time. The powers are:
    - Decorated Effects:
        - Invisibility: The player can't be seen by the mobs.
        - Speed: The player can move faster.
        - TileBreaker: It makes the player break any breakable tile in front of them.
        - HelperFighter: It creates a Helper which searches for the nearest enemy and then chases and shoot it. (Advanced)
    - NonDecorated Effects:
        - Health: The player can have more health. This is used to make the game more interesting and challenging.
        - Coin: The player can collect coins. This is used to make the game more interesting and challenging.



## Sound
- Jengen supports playing sound effects and event sounds in the game. The supported sound formats are of '.wav' extension.
- The sound effects are loaded only once when the Player starts the game. This is to make the game more efficient and to prevent the lagging of the game.

## GUI
- GUI is fully implemented in Jengen. It supports different states like:
    - Main Menu
    - Playing Game
    - Settings
    - About
- Also it handles events inside the Game itself. For example, the player can pause the game, resume the game, or exit the game from the GUI.

## Input Handling
Jengen supports keyboard and mouse input for controlling the game and there are stored in the InputSnapshot object. The InputSnapshot object is used to store the input of the player in a specific frame. This is used to prevent inefficiency in taking snapshots every frame.

## Winning State
Jengen supports different winning states based on the type of the game. The winning state is determined by the Game Context object. The winning state can be:
- Collect All Coins: The player should collect all the coins in the level to win the game.
- Kill All Mobs: The player should kill all the mobs in the level to win the game.
- Reach the End: The player should reach the end of the level to win the game.


## Game Context
- The Game Context object is used to configure the game environment and parameters. It includes the following contexts:
    - GUI Context: Used to configure the GUI of the game.
    - Level Context: Used to configure the level of the game.
        - Random Level Context: Used to create a random level.
        - Spawn Level Context: Used to create a level from predefined images or txt files.
    - Player Context: Used to configure the player of the game.
    - Screen Context: Used to configure the screen of the game.
    - Projectile Context: Used to configure the projectiles of the game.
    - AI Context: Used to configure the AI of the game.
    - Winning State Context: Used to configure the winning state of the game.
## Pause-Resume
Jengen supports pausing and resuming the game. It allows the player to pause the game and resume it later.

## Advanced Features

## Multi-Player Support
- Jengen supports multiplayer games with multiple players. It uses UDP for communication between players. The server and client controllers are used to manage the communication between players.
    - Server Controller: Used to manage the server side of the game. It stores the essential information of the clients.
    - Client Controller: Used to manage the client side of the game. It sends and receives commands from the server.
    - First, the server controller is started and then the client controller is started by setting the server IP address and port number.
    - The server controller listens for incoming connections from clients and the client controller connects to the server.
    - Then clients can send Connect Command to the server to join the game. It keeps sending this command until the server accepts the connection and sends the Acknowledge Command.
    - Then when other clients join the game, the server sends their information to the existing clients and sends the information of the existing clients to the new clients.
    - Every Frame, the server receives the Update Command from the clients and sends the Update Command to all the clients.
    - When a Client Disconnects, it sends "10" packets to the server to inform it that it is disconnecting. The server then sends the Disconnect Command to all the clients.
    - The server controller is responsible for managing the game state and sending the game state to the clients and it doesn't save the game state.




## Level Creation
 - Jengen provides a simple API for creating and loading levels from files. It supports different types of levels like:
    - Spawn Level: Used to create a level from predefined images or txt files. It first reads the tiles file (txt or png) and then reads the mobs, coins, and effects files (txt) from the level folder.![19543bf1-111b-488c-a262-cabfd1ad6826](https://github.com/ayman-art/Fight-Enemies/assets/52029584/6c279220-f686-47be-89e4-b5accc2e263a)![3d98209d-bdd4-4133-964b-c68e5fa5facc](https://github.com/ayman-art/Fight-Enemies/assets/52029584/3f06f8da-bcca-48d1-bc89-f6e71225b5be)

      
    - Random Level:
        - Used to create a random level. It generates the level randomly based on the size. The Engine Provides 3 Levels Of Difficulty which determines the density of the tiles and enemies.
        - This is done by using a DSU data structure to generate the level. It prevents the generation of isolated tiles and ensures that the all the tiles are connected.
        - Two threads are used to generate the level as mentioned in the Producer-Consumer Like Pattern.
        - This allows the game to generate the level efficiently and make the game more interesting and endless!
    - The level is updated by updating the mobs, coins, and effects based on the game state.
    - The level is rendered by iterating over the tiles, mobs, coins, and effects and rendering them on the screen.![8ef259a1-e0f6-4177-8817-06091b40bb2f](https://github.com/ayman-art/Fight-Enemies/assets/52029584/47b33b1e-0e67-49ae-9808-6e8eb06dc9c2)![e88605da-645c-45a5-8eba-aa344de3c893](https://github.com/ayman-art/Fight-Enemies/assets/52029584/5344c65e-ff93-4741-aa6b-56e6e7b4f149)

    - The Mobs are divided into:
        - Player: The player of the game. Also, Decorated
        - Dummy: Simple Mob that moves and shoots randomly.
        - Helper: Mob that helps the player by chasing and shooting the nearest enemy(Dummy or Chaser). It is added to the World when the HelperFighter Effect is activated
        - Chaser: Mob that chases the player and shoots it.



## AI
- Jengen includes a basic AI system for controlling the behavior of non-player characters (NPCs). Jengen supports different types of AI:
    - A* Algorithm for Path-Finding: Used to find the shortest path between the player and the mobs. It follows a heuristic approach to find the path to the player.
    - Dijkstra Algorithm for Path-Finding: Used to find the shortest path between the player and the mobs. It follows a standard approach to find the path to the player.
    - Greedy First Search Algorithm for Path-Finding: Used to find the shortest path between the player and the mobs. It follows a greedy approach to find the path to the player.
    - Random Movement for Movement: Used to move the mobs randomly in the level. It is used to make the game more interesting and challenging.

    - The Path is only calculated when the player is in the chasing range of the mob. The chasing range is determined by the Player Context object.
    - The Path is not calculated every frame to prevent inefficiency. It is calculated only when mob lies on a tile perfectly.

# Security
- Jengen provides a simple way to encrypt and decrypt the game data. This is used to prevent cheating or tampering with the game data. The encryption and decryption are actually simple. But it is hard for non-technical users to tamper with the game data.
- The encryption is done when the Game is designed. It takes the number of level, the Tiles file or image, and the Entites File and encrypts them. The ouput is saved in a file with the extension '.enc'   .
- The decryption is done when the Game is played. It takes the current level number, the Tiles file or image, and the Entities File and encrypts them. This is compared with the saved encrypted file. If they are the same, the game is played. Otherwise, the game is not played! So, don't try to cheat!
- There is another method to decrypt the data itself but not implemented yet.
- This essentially is useful in MultiPlayer Mode.



# Recording
- Jengen supports recording the game in an efficient way. It records the game state at a specific frame and saves it. And for each frame it only saves the changes in the game state. This is done by using the Memento Pattern along with simulation.
- The recording allows the player to replay the game. It is used to make the game more interesting.
- Here is an img of how the recording is done:
---
![aaea8640-f590-4ed5-8e0c-05a38dfab240](https://github.com/ayman-art/Fight-Enemies/assets/52029584/9dc82fda-a095-4706-8b44-e4ac4f2abc58)

## Design Considerations


## Folder Structure
```
Jengen
    ├── src
    │  ├── com
    │       └── ayman
    │           └── jengen
    └── Tests         ├── audio
                      ├── entity
                      │   ├── mob
                      │   │   └── decoratedPlayer
                      │   │
                      ├── gameCreation
                      │   └── contexts
                      │       └── levelcontexts
                      ├── Graphics
                      ├── gui
                      │   └── states
                      ├── Input
                      ├── level
                      │   ├── effects
                      │   │   └── decorationEffects
                      │   ├── snapshots
                      │   └── winning
                      ├── network
                      │   ├── client
                      │   │   ├── commands
                      │   │   └── controller
                      │   └── server
                      │       ├── commands
                      │       └── controller
                      └── util

```

 ## Make your own game!
- **Step 1**: Create a Context for each module of the game.
- **Step 2**: Create a GameContext object and pass the created contexts to it.
- **Step 3**: Create a Jengen object and pass the GameContext object to it. Use this Entry point to start the game.

```
    public static void main(String[] args) {
        GameContext game = new GameContext.Builder()
                .setGUIContext(guiContext)
                .setLevelContext(levelContext)
                .setPlayerContext(playerContext)
                .setScreenContext(screenContext)
                .setProjectileContext(projectileContext)
                .setAIContext(aiContext)
                .setWinningStateContext(winningStateContext)
                .build();

        ////////////////////////////////
        Jengen jengen = new Jengen(game);
        jengen.start();
        ////////////////////////////////
    }
```
