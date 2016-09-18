Cell Society : Design Document
===================

Introduction
-------------------
The purpose of the program to be written is to animate specific 2D Cellular Autonoma simulations according to a set of rules specified by the user. The primary design goals of the project include optimally encapsulating data, implementing inheritance hierarchies in order to make use of polymorphism, and structuring code in such a way that simulations can easily be added with pre-defined behaviors. The implementation details of each specific simulation will be closed to the other classes, and similarly, the way cell behaviors are defined will be hidden from simulation classes. In a hierarchical style, higher-level classes will have access to and control over lower-level classes through public methods defined in the latter.

Overview
-------------------
We intend to create one large main class that instantiates an instance of a controller class. This class facilitates the different simulations according to the user's input. From the simulation classes, we initiate the grid as well as instantiate the proper kind of cells inheriting the main Cell superclass. For each of their simulations, the front-end includes a screen as well as many buttons that will toggle different options and restrictions for the simulation. From the back-end, the program will take in the user XML input as well as different numbers/parameters inputted from the front-end display and create a function that changes the cells. Finally, the UI gets updated to reflect the changes that were processed in the back end. 

**Overall:**
1. Create a main class that takes in XML data or somehow determines which simulation to start -> starts that simulation
2. Simulation begins to create a grid that front-end will display on stage -> shows the simulation on UI
3. From simulation profile, pulls the proper cell from the Cell superclass and instantiates it -> Helps back-end figure out what type of cells simulation should use
4. Front-end talks to back-end, passes in user input parameters and back-end processes data, develops data in response and responds with updating UI -> Updates front-end to reflect the simulation profile
5. Updates go through to UIDisplay class that shows the actual simulation

**General Data Structures:** MultiDimensional array, Rectangle Shape, Cell Object<br/>
**General Classes:** Main, Simulation (For each different Simulation, e.g. GameOfLife.java, ForestFire.java, MainMenu.java, Controller.java)<br/>
**General Methods:** 
1. pullParams() -> Pull parameters from XML/front-end and update values
2. startPoint() -> Setup spot on grid that recursion will begin
3. recurse() -> Iterate to all children next to it, and check whether or not they satisfy conditions that justify changing their current state e.g. Game Of Life. Big method that is at the heart of the simulation profile, keeps going until simulation ends
4. changeState() -> If certain conditions are satisfied change current state to represent conditions
5. updateUI() -> Updates UI with the changes reflected from back-end
6. display() -> Set scene on stage for the simulation profile
7. setUpInitialButtons -> Set up buttons, grid UI, etc and default conditions for the simulation

![Overview Design:](https://github.com/brianlizhou/CellSoc/blob/master/DesignImage.jpg "O1.png")

User Interface
-------------------

The user interface consists of three main parts. The first is the main menu, which has buttons that allow the user
to scan the current XML file, choose the desired type of simulation, and move to the next scene. This scene displays
the parameters for the simulation as read by the XML and gives the user the ability to change parameter values before the simulation begins. Once the user presses the button to start the simulation, the scene changes to the simulation page, which contains buttons to control the simulations, such as start, stop, and reset as well as a physical representation of the grid itself, containing the cells the define the state of the simulation. These will be dependent on the current simulation being run.

![User Selection Menu:](https://lh3.googleusercontent.com/-PlrUD9xaABw/V93tQRMolSI/AAAAAAAAAMU/uYLwNz2c8lEB9Zhv2IrhlHVLitRg8A4zQCLcB/s0/UI1.png "UI1.png")

![User sets parameters for selected simulation](https://lh3.googleusercontent.com/-42KxXlJeUFM/V93y6vYRoCI/AAAAAAAAAM0/aoi4KZtSVwYAbsKP68UlzAQkStUCYGskQCLcB/s0/UI2.png "UI2.png")

![Simulation view](https://lh3.googleusercontent.com/-IcZHhRv4xeU/V93zY8eV9hI/AAAAAAAAAM8/7pgrc5UMScIbZV9yQvGOs_lHnc2bR25egCLcB/s0/UI3.jpg "UI3.jpg")

The user selection will be limited to just valid inputs (for example, you can't have 110% as a threshold, or negative values for the grid size). If the user fails to input valid parameter values through the XML, such as the grid    size, the program will use a default value and notify the user of all the fields for which a value was not selected. Rather than display an error message in response to bad input data, these default parameters will be shown and can be edited in the second part of the user interface shown above.


Design Details
-------------------
**Main Class**
- Handles Specific Features: Starts the correct simulation class corresponding with one indicated on XML
- Resources Used: Basic XML initialization file
- Collaboration: Corresponding Simulation class that is being started
- Extended Possibilities: Make it start a new simulation profile for a new extension/simulation
- Justify Creation: Need an entrypoint to the game to boot

**Simulation Class**
- Handles Specific Features: Begins simulation and is the manager for telling cells how to change, also checks for boundaries and surrounding cells for case handling
- Resources Used: 2-D grid, Rectangle Shape, input XML data from main, data from other classes (states, etc)
- Collaboration: Needs to talk the cell class in order to figure out what kind of cell it's using, and also needs to talk to main displayWindow class to reflect changes on back-end on to display
- Extended Possibilities: If there are any other features that need to be changed for a specific simulation, it's very easy to change in here because all of the changes are compartmentalized in this class for each unique simultion
- Justify Creation: Need this simulation class to process the back-end behavior for each class depending on parameters and rules
- Use Cases: Depends on the state of bordering cells, will check i+/- 1 and j+/-1 to figure out state of surrounding cells and change it's current state depending on rules, parameters and conditions

**Cell Class**
- Handles Specific Features: Handles cases and cell changes on an individual basis, superclass that changes depending on simulation profile
- Resources Used: SubCell classes
- Collaboration: Talks to the subCell simulation cell classes, thus is used throughout the entire program, collaborates with simulation class, back-end and front-end window display
- Extended Possibilities: Create a new subcell class for the simulation profile, enumerate the different states, and check if other cells are matched up
- Justify Creation: Need one super cell for common methods, that way we can quickly get the general cell class

**Simulation Cell Class**
- Handles Specific Features: This class is important in that when the simulation class tells it to change state, it will go and immediately tell the display window to change state depending on how the enumerated state is
- Resources Used: Enumerated state for all possible states, location of the cell, and connection to the display window
- Collaboration: Takes in data from simulation class, and subsequently updates itself depending on that data. Afterwards it updates the image inside the main displaywindow to show the effect/change on the simulation
- Extended Possibilities: Add another simulation cell class for the simulation and make it have similar behavior but with a different enumerated state
- Justify Creation:Need this in order to keep track of different cell states and represent that on the game window

**Controller Class**
- Handles Specific Features: Whenever there is an update within the simulation given a specific input parameter, the controller class updates all of the rectangles or tells the cells to change their state
- Resources Used: JavaFX and all of the other enumerated states for the simulation cells
- Collaboration: Talks to the SimulationProfile class (main simulation class) as well as all of the simulation cells
- Extended Possibilities: We need to update the controller whenever we change the parameter so if we do real-time or something for simulation times, this class would expand to take in that case
- Justify Creation: We need this to control and tell all of the cell classes to change their state depending on conditions on every step or cell change condition

**DisplayWindow Class**
- Handles Specific Features: Shows the display of the simulation with graphics, and will update in accordance to data flow
- Resources Used: Javafx scene, stage, and Rectangle shapes, as well as color and other JavaFX APIs
- Collaboration: Collaborates with the Simulation class cell directly, and indirectly with all of the other backend processing that allows it to change state properly
- Extended Possibilities: Since all of the UI graphic display stuff is in this class, any buttons/additions we want to add can be in this class.
- Justify Creation: We need this to display the simulation, otherwise we won't have any graphical addition to the simulation profile

**Main Menu Class**
- Handles Specific Features: Shows the initial display that allows you to choose your simulation profile
- Resources Used: JavaFX APIs
- Collaboration: Collaborates with Simulation class and is the initialization from main
- Extended Possibilities: All of the initial parameters and boot start is from this class, so if we wanted to change the initialization we would modify this class
- Justify Creation: Need an interactive main menu to let people choose a simulation

<br/>
<br/>
Overall, Main -> Starts Simulation Class -> Updates Data and tells Simulation Cells to change state -> Simulation Cells change DisplayWindow
In terms of classes, should also have DisplayWindow superclass with multiple subclasses for different simulation profiles
 
Design Considerations
-------------------
The majority of the design was agreed on by the team, but there are a couple of considerations that need to be resolved before a complete design solution is devised. Firstly, the implementation of the controller and menu classes is still unclear. Weve thought about creating a single class that sets up the menu and also controls the current simulation being run, which would allow for a single interface between the Main class and simulation class, but separating the two into Controller and Menu classes would allow for a clearer divide between a class that controls the whole program and a class that is just responsible for a Menu GUI. Our decision to have a Controller class is important in that it does not leave all of the responsibilities to the Simulation class makes it much easier to facilitate the current simulation being run.
Additionally, the higher-level nature of the Controller class lends itself to be separately implemented in relation to the Menu Class. Another concept to be addressed is the relationship between graphics and behavior. We considered leaving anything to do with JavaFX to one Graphics class, which would restrict the Simulation class to non-graphical mechanics, but we decided that allowing the Simulation classes access to JavaFX is more flexible in terms of defining the way the GUI changes with respect to the behavior of the simulation being run.

Team Responsibilities
-------------------
This section describes the program components each team member plans to take primary and secondary responsibility for and a high-level plan of how the team will complete the program.

 - Soravit: Fire, cell class, main/XML work
 - Delia: Segregation, controller class, menu class
 - Brian: Game of Life, simulation superclass
 - Shared responsibility: Predator-Prey

Each person is responsible for back-end and front-end development for the simulations theyre specializing in. Each person also specializes in one shared aspect of the project, such as superclasses, helper classes (menu), the main class, and the XML file. All three people will specialize in one easier simulation and work together on the Predator-Prey simulation. Specific simulations will be built after main class, control class and helpers, superclasses, and XML work are established. 

Use Cases
-------------------

 - Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
	 - Check state of all 8 neighboring cells
	 - Update state of this cell to dead if there are more than 3 neighboring cells that are alive.
	 - Also update state of this cell to dead if there are less than 2 neighboring cells that are alive
 - Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
	 - Our implementation of Game of life will have closed edges, meaning that cells cant be born beyond those edges and that the cells along the edge will have fewer than 8 neighbors
	 - If exactly 3 of the edge cells neighbors are populated, the edge cell gets updated to live
	 - Program checks for null neighbors (since were using a 2D array)
 - Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
	 - In the step function, we will update the state of each cell based on their neighbors and current position. 
	 - Will change the color of the cell in the grid based on a pre-set color scheme for the particular simulation
 - Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
	 - We will update an instance variable in the cell class for the cell objects in this simulation
	 - This instance variable will set the threshold for a cells state to change based on its neighbors
	 - This information will be passed into the simulation class that is responsible for specifying parameters in the fire simulation
 - Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
	 - In the GUI that the user uses to play the animations, there will be a button along the sidebar (along with the other buttons) to return to the menu page and switch to a different type of simulation.
	 - The controller class will be responsible for this switch. It will redefine the Simulation object based on the users choice and initialize the variables accordingly. 

