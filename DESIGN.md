Cell Society : Design Document
===================

Introduction
-------------------
The purpose of the program to be written is to animate specific 2D Cellular Autonoma simulations according to a set of rules specified by the user. The primary design goals of the project include optimally encapsulating data, implementing inheritance hierarchies in order to make use of polymorphism, and structuring code in such a way that simulations and other additional features can easily be added with pre-defined behaviors. The implementation details of each specific simulation will be closed to the other classes, and similarly, the way cell behaviors are defined will be hidden from simulation classes. In a hierarchical style, higher-level classes will have access to and control over lower-level classes through public methods defined in the latter.

Overview
-------------------
We created one main class whose sole purpose was to instantiate an instance of a controller class, titled MainMenu.java. This class facilitates the different simulations according to the user's input. From the simulation classes, we initiate the grid specific to the simulation but inheriting from a Grid.java superclass, as well as instantiate the proper kind of cells inheriting the main Cell.java superclass. For each simulation, the front-end before the simulation allows users to selec parameters and options/modes to run in, and during the simulation includes a screen as well as many buttons that will toggle different options and restrictions for the simulation. From the back-end, the program takes in the user XML input as well as different numbers/parameters inputted from the front-end display and create a function that changes the cells. Finally, the UI gets updated to reflect the changes that were processed in the back end. 

**Overall:**
1. Create a main class that instantiates MainMenu, which takes XML data and lets user determine which simulation to start -> starts that simulation
2. Simulations create a grid and populate it with cells that front-end displays on stage -> shows the simulation on UI
3. From simulation profile, pulls the proper cell from the Cell superclass and instantiates it as the subclass matching the simulation -> Helps back-end figure out what type of cells simulation should use
4. Front-end talks to back-end, passes in user input parameters and back-end processes data, develops data in response and responds with updating UI -> Updates front-end to reflect the simulation profile
5. Updates go through to UIDisplay class that shows the actual simulation

**General Data Structures:** MultiDimensional array, Rectangle Shape, Cell Object, Spinners, Location, CellType
**General Classes:** Main, Simulation, MainMenu.java, UserInput.java and all subclasses, Cell.java, Grid.java, XMLFactory.java, (For each different Simulation, e.g. GameOfLife.java, ForestFire.java can build subclasses for the listed)<br/>
**General Methods:** 
1. instantiateGrid() -> Creates grid in all simulation subclasses
2. step() -> Runs the animation
3. updateGraph() -> Called each time by step() to update graphs
4. getAllNeighbors() -> Returns list of every neighboring cell for a cell of interest
5. startXMLSimulation() and startManualSimulation() -> launches after user chooses which simulation
6. init() -> starts simulation by setting up all major components and calling other methods
7. setUpButtons -> Set up buttons, grid UI, etc and default conditions for the simulation

![Overview Design:](https://github.com/brianlizhou/CellSoc/blob/master/DesignImage.jpg "O1.png")

User Interface
-------------------

The user interface consists of three main parts. The first is the main menu, which has buttons that allow the user
to scan the current XML file, choose the desired type of simulation, and move to the next scene. This scene displays
the parameters for the simulation as read by the XML and gives the user the ability to change parameter values before the simulation begins. Once the user presses the button to start the simulation, the scene changes to the simulation page, which contains buttons to control the simulations, such as start, stop, and reset as well as a physical representation of the grid itself, containing the cells the define the state of the simulation. These will be dependent on the current simulation being run.

The user selection will be limited to just valid inputs (for example, you can't have 110% as a threshold, or negative values for the grid size). If the user fails to input valid parameter values through the XML, such as the grid size, the program will use a default value and notify the user of all the fields for which a value was not selected. The error checker will display an error message in response to bad data, but will allow the user to continue with a default value and still run the simulation.


Design Details
-------------------
**Main Class**
- Handles Specific Features: Launches MainMenu controller class
- Justify Creation: Need an entrypoint to the game to boot

**Simulation Class**
- Handles Specific Features: Begins simulation and is the manager for telling cells how to change, also checks for boundaries and surrounding cells for case handling
- Resources Used: input XML data from main, data from other classes (states, etc), grid and cell, or userInput data
- Collaboration: Needs to talk the cell class in order to figure out what kind of cell it's using, and also needs to talk to main displayWindow class to reflect changes on back-end on to display. Must also communicate with grid class to initialize all cells and check grid edges.
- Justify Creation: If there are any other features that need to be changed for a specific simulation, it's very easy to change in here because all of the changes are compartmentalized in this class for each unique simulation. This makes it very extendible to other simulations.

**Grid Class**
 - Resources used: 2-D grid, Rectangle Shape
 - Use Cases: Depends on the state of bordering cells, will check i+/- 1 and j+/-1 to figure out state of surrounding cells and change it's current state depending on rules, parameters and conditions
 - Justify creation: Same reason as simulation

**Cell Class**
- Handles Specific Features: Handles cases and cell changes on an individual basis, superclass that changes depending on simulation profile
- Collaboration: Talks to Grid class, thus is used throughout the entire program, collaborates with simulation class, back-end and front-end window display
- Justify Creation: Need one super cell for common methods, that way we can quickly get the general cell class. Enums help with flexibility and making this class applicable to all cell types

**UserInput Class**
- Handles Specific Features: Shows the simulation's parameters as selection spinners, and provides buttons to determine which shape of cell user wants
- Resources Used: Javafx scene, stage, Spinner, Button, and Rectangle shapes, as well as color and other JavaFX APIs
- Collaboration: Collaborates with the Simulation class cell directly, and indirectly with all of the other backend processing that allows it to change state properly
- Extended Possibilities: Since all of the UI graphic display stuff is in this class, any buttons/additions we want to add can be in this class.
- Justify Creation: We need this to provide an interactive medium for users of the simulation, otherwise we won't have any graphical addition to the simulation profile.

**Main Menu Class**
Starts the correct simulation class corresponding with one selected by user
- Resources Used: XML initialization file
- Collaboration: Corresponding Simulation class that is being started
- Handles Specific Features: Shows the initial display that allows you to choose your simulation profile
- Resources Used: JavaFX APIs
- Collaboration: Collaborates with Simulation class and is the initialization from main
- Justify Creation: Need an interactive main menu to let people choose a simulation

<br/>
<br/>

Adding New Features
-------------------
To add a new simulation, new subclasses for Simulation, Cell, Grid, UserInput, and XMLFactory will be created. After adding the unique properties of that simulation to these classes, the XML file should be updated and one more case added as a MainMenu button.
 
Design Trade-Offs
-------------------
Our code is designed this way because we realized that there were too many differences between various simulations for any of the classes - cell, grid, simulation, userinput, or xmlfactory - to be shared without simulation-specific subclasses. At first, we thought a single Cell class would do for different simulations, but we soon realized that cells were too different from each other and these differences would render a Cell class useless if it had to apply to every type of cell we used. 
The benefits of this are that these different subclasses have a lot of freedom to implement methods that are unique to their simulations, and debugging a simulation is relatively easier when it has its own classes than looking in a shared class and changing something that might affect other simulations. 
The drawback is obviously that each time we add a new simulation, we have to create at least 5 new classes and implement each of them (even if each one does not require too much typing).

Assumptions
-------------------

 Our program does not make many assumptions about the given
XML file or manual input, since it implements error detection for invalid input, defaulting to certain values when this is the case. Whether the neighbors of a cell include NE, SE, SW, and NW depends on the specific simulation and not the shape of a cell.