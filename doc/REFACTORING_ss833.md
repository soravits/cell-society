**Design Refactoring Report**

**Problem**
The design issue I chose to refactor was the ease of access that every class, especially the Simulation classes, had to the 2D array in the Grid class. Previously, we had allowed our subclasses access to the grid in the superclass through the use of getters and setters. However, I realized that the simulation classes and Grid subclasses do not need to know that the Grid class implements the grid as a 2D array. In fact, the previous implementation restricted the ability to change the data structure with which the grid was implemented without messing up the program. Additionally, classes in general should not have access to the the grid's implementation, only an encapsulated abstract Grid object.

**Solution**
By removing the getters and setters to the 2D array and adding getters and setters that would allow for access to specific cells (parameterized by rows and columns) and the number of rows and columns instead, I was able to better encapsulate my data and limit access to the way my grid was implemented without affecting the functionality of the simulations. Now, rather than the Simulation and Grid subclasses iterate through every index in the array directly, they base their access to specific cells by the row and column of the cell. The initial implementation gave us more control over our manipulation of the 2D array, but it also made our subclass code reliant on the fact that our grid is implemented with this data structure.

This is the link to the commit that includes the changes: 
https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team04/commit/6652db31f56fd096fb534bd78509df8a24c82f36