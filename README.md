Cell Society README
===================
#### Members Of Group
1. Delia Li
2. Soravit Sophastienphong
3. Brian Zhou

#### Date Started, Date Finished
Date Started: September 15th <br/>
Date Finished: Technically October 3rd, some additions afterwards<br/>
Total Hours Spent: ~25 hours each, so ~75 hours total

#### Roles:
Everyone was a full stack engineer, worked on all components of the projects. <br/>
In terms of big picture: <br/>
1. Delia did most of Segregation and SugarScape, as well as user most of the input materials & configuration extensions
2. Soravit did most of Predator&Prey, ForestFire, and Foraging Ants, as well grid wrap-around behavior, some input materials/configuration extensions, and initial graphs/visualization materials
3. Brian did most of Game Of Life and Slime Molds, as well as most of the visualization extensions and grid shapes(hex, triangle, and square)

Specific details located in all of our Analysis.md in respective folders.
<br/>
#### Links Used
Instructions for all of the simulations are found here at the link for basic (1) and extensions (2)
```bash
1. https://www.cs.duke.edu/courses/compsci308/fall16/assign/02_cellsociety/part2_NXZ.php
2. https://www.cs.duke.edu/courses/compsci308/fall16/assign/02_cellsociety/part3_JHV.php
```

#### Entrypoint / Files to start project
Main.java class, located within the package "main"<br/>
Test files and pre-completed XML files are located within data/xml folder in a file called rules.xml

#### Required Files:
Only required file is located within the images folder, and is the background image for the simulation main menu.<br/>
The file name is BackgroundCellSoc.jpg.

#### Instructions / Usage
Run Main.java as a java application, and manually input simulation parameters for the simulation you want to see. Alternatively, input the configuration data
within you XML file and run the simulations with XML. There are no easter eggs, but lots of possibilities of interesting simulations depending on different data/initial states you use.

#### Known Bugs:
If you start Game Of Life simulation with a gridlength of over 50, there is a graphical glitch that makes all of the cells mix. In order to fix this bug,
just adjust the window size or press step make it go one step iteration and the visual bug will fix itself.

#### Extra Features:
All of the Visualization extras, most of the Configuration extras, and some Simulation extras as specified in the URL:https://www.cs.duke.edu/courses/compsci308/fall16/assign/02_cellsociety/part3_JHV.php <br?>
Additional simulations include Foraging Ants, Slime Molds (Not completely implemented with angles), SugarScape.

#### Impressions for improvements:
1. Add resource files for styles.css and drawables
2. Improve data structure for grid 
3. Perhaps create specific cells for each different type of cell (depending on simulation)
4. Additional impressions found in our individual ANALYSIS.md located in our respective project folders
