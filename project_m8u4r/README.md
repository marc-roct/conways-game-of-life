# Conway's Game Of Life


Conway's Game of Life is a cellular automaton created by mathematician John Horton Conway. The game is made of an
infinite 2-dimensional plane consisting of individual squares called cells. Each cell can either be "alive"
or "dead" and these cells evolve over time in step increments. After each step the following occurs.

- Any live cell with fewer than two live neighbours dies, as if by underpopulation.
- Any live cell with two or three live neighbours lives on to the next generation.
- Any live cell with more than three live neighbours dies, as if by overpopulation.
- Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

*Neighbors* are any cells in the 8 squares surrounding the cell.

This application would be used my mathematicians or those who are simply interested in this cellular automaton.

I am interested in this project because I too am interested in this cellular automaton.

##User Stories

As a user, I want to be able to add and removes cells from the plane

As a user, I want to be able to automatically increment the steps.

As a user, I want to be able to reset the board.

As a user, I want to be able to manually increment steps to oversee the evolution of the cells

As a user, I want to be able to increments steps backward to see previous states of the cells

As a user, I want to speed the up step rate to see the end state of the cells states if there are any

As a user, I want to be able to save my board state to file

As a user, I want to be able to be able to load my board state from file 

##Phase4: Task 2

Thu Nov 25 17:01:09 PST 2021  
Added cell at position 20,9 to the board

Thu Nov 25 17:01:11 PST 2021  
Added cell at position 20,10 to the board

Thu Nov 25 17:01:11 PST 2021  
Added cell at position 20,11 to the board

Thu Nov 25 17:01:13 PST 2021  
Generated next board of cells.

Thu Nov 25 17:01:13 PST 2021  
Generated next board of cells.

Thu Nov 25 17:01:13 PST 2021  
Generated next board of cells.

##Phase 4: Task 3

My model class, which includes SetOfCell and Cell, was refactored many times throughout the project 
iteration is compact and has clear cohesion. On the other hand my BoardGUI class is a mess, with all
components of the frame being included in the single class. Originally I thought it would be unnecessary
to split the components into different class, but now I realize that if I want to change something I 
would need to alter many parts of the code. If I were to go back I would refactor BoardGUI into 3 classes,
a frame, a game panel, and a panel for buttons that way I can alter functionality in each class without
having to tinker with the rest of the code.










