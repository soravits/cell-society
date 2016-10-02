REFACTORING_DL202.md
 - what was the design issue(s) in the code you chose to refactor
 One of the biggest issues was redundancy across different classes. This is somewhat more than 
 simply pulling out duplicated code into a new method, because this type of refactoring involves 
 relocating code to a different class. 
 
 - why you think the new version of the code is better
 The new version of the code is better because the original contained the same methods in four 
 separate classes, so changing the functionality of when such method would entail changing each of 
 its duplicates across the 4 classes (if we want consistency). Thus, the refactoring process added 
 better organization and flexibility to our program by guaranteeing that changing any of these 
 shared methods would only entail a single modification to the superclass and not to each of the 
 subclasses which used to share this code.
 
 - a link to one or more commits you made that refactored the problem code
 
 https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team04/commit/f4e4cbe3b940fb08b01dbff9034f81db2170c194