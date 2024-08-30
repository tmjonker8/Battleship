# Battleship

My Version of the board game Battleship. Player connects to another player on their network to play a game of Battleship.  Does not work with public IP's. The ships are represented by colored rectangles. 

I will admit, this is extremely messy code.  It was my first real project and I learned a lot while doing it, but it is very difficult to read.  I've actually cleaned it up quite a bit since I first made it, but it's still extremely difficult to follow.

![Battleship](https://github.com/tmjonker8/Battleship/blob/master/Images/GameBoard.PNG)
![Battleship](https://github.com/tmjonker8/Battleship/blob/master/Images/SettingUpPieces.PNG)
![Battleship](https://github.com/tmjonker8/Battleship/blob/master/Images/GAMEPLAY.PNG)

## Getting Started

1. Make sure you've installed all requirements
2. Clone this repository: git clone https://github.com/tmjonker/BattleShip
3. Change into the directory of the project
4. Change into the /src/ directory
4. Maven is used as the build tool.  POM.xml is included.
5. Use whichever IDE you prefer to compile and build the project.

## How to Play

* User must first decide whether they want to Host or be the Client by clicking on the 'Game' menu, selecting the 'New Game' menu, and clicking on one of the options.
    * If user decides that they want to be the Host, game won't start until client connects.
    * If user decides that they want to be the Client, they must enter the IP address for the Host that they want to connect to.
* Once connected, user must then set up their game board by selecting one of the ship buttons: Carrier, Battleship, Submarine, Cruiser, Carrier, and then dragging the corresponding game piece to a location on the left grid. NOTE: All ships are represented by colored rectangles!
    * Upon clicking on one of the ship buttons, user has the opportunity to rotate the game piece by clicking the 'Rotate' button.
    * If user wants to erase the last placed game piece, they can use the 'Clear Last' button and try again, or if they want to start over, they can use the 'Clear All' button to clear all of their game pieces.
* Once user has dropped all 5 of their game pieces, they must click 'Roll' to determine who has first play.
    * Both users must 'Roll' before game can start.
* Once both users have rolled, both users must click on 'Finalize' to indicate that they are ready to play.
* Game will tell you when it's your turn.
* When it's your turn, you click on any one of the grid boxes in the RIGHT grid to attack your opponents ships.
    * If you hit, the block turns Green, if you miss, the block turns Blue.
* Your opponents progress is tracked on the LEFT grid. 
    * If your opponent hits your ship, the box turns RED. 
    * If your opponent misses, the box turns BLUE.
* Game is played until one player wipes out their opponents 5 ships.
* Player can disconnect at any time by clicking on 'Game' -> 'Current Game' -> 'Disconnect'.
        
## How to Test using 1 Computer

* First, get your local ip address by typing ipconfig in a cmd.exe prompt (Windows).
    * Or use /sbin/ifconfig (Linux).
* To test using one computer, open two different GameGUI windows by executing java GameGUI twice.
* In one of the GameGUI windows, start a new game as a HOST.
* In the other GameGUI window, start a new game by using CONNECT TO HOST.
    * Type the local ip address that you found above into the dialog box that pops up.
* Both windows should now be connected and you can test the game.

## Built With

* [JavaFX](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm) - For GUI programming.
* [Java 8](http://www.oracle.com/technetwork/java/javase/documentation/index.html) - Primary programming language.


## Authors

* **Tim Jonker** - *Initial work* 


