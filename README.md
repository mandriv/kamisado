# Kamisado

Kamisado is an abstract strategy board game for two players that's played on an 8x8 multicoloured board. Each player controls a set of eight octagonal dragon tower pieces. Each player’s set of dragon towers contains a tower to match each of the colours that appear on the squares of the board (i.e., a brown tower, a green tower, etc.). One player’s towers have gold dragons mounted on the top, while the other player’s towers are topped with black dragons.

For server implementation [https://github.com/mandriv/kamisado-server](click here) 

## Project features

  - Play against a friend on the same machine
  - Play against three different difficulty AI player
  - Watch two AI player compete against each other
  - Game statistics
  - In-game atmospheric theme music!
  - Online matchmaking (to be completed)

You can also:
  - Save your game and continue it later
  - Undo / redo your moves
  - Expierience best graphics provided by my mad Gimp skills and good ol' Swing library (pun intended)

## Tech

The core of the project relies on MVC desigin pattern represented primarly by three classes: Board.java (Model), BoardGUI.java (View), GameController.java (Controller). Computers moves are generated using AI.java class. Depending on difficulty, AI checks all the possible moves for 1-6 next consecutive turns and picks the best one based on the evaluation function. This function returns the evaluation score based on a couple of factors:

  - Number of one move instant wins possibilites
  - Number of possible moves
  - Type of piece used for final move (e.g. it is better to finish round with double sumo than with a tower)
 
Each game can be saved and loaded. Game data is stored as JSON objects and can we loaded with provided constructor. Each human vs computer game is saved for stats purposes.

Online part is not yet fully developed. It relies on Node.js server hosted currently on heroku, which serves mainly as RESTful API for users and games management and Socket.io connections. The idea is to have the model and controller part served online, so the user has only a view of the logic behind the game. Each action request is to be served by WebSockets (Socket.io) using JSON objects. Server has basic security precautions like authorization, authentication (implemented using JSON Web Tokens).
