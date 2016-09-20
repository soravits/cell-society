package cellsociety_team04;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GameOfLifeCell extends Cell{
	public enum States{Dead,Alive};
	
	private States states;
	public GameOfLifeCell(int sizeOfCell, Pane rootElement, int xCoord, int yCoord) {
		super(sizeOfCell, rootElement, xCoord, yCoord);
		this.states= States.Dead;
	}
	
	public void killCell(){
		this.states = States.Dead;
		updateCellColorDependingOnState();
	}

	public void reviveCell(){
		this.states = States.Alive;
		updateCellColorDependingOnState();
	}
	
	public int getStatusCode(){
		if(this.states == States.Alive){
			return 1;
		}
		return 0;
	}
	
	private void updateCellColorDependingOnState() {
		if(this.states == States.Dead){
			block.setFill(Color.BLACK);
			block.setStroke(Color.WHITE);
			block.setStrokeWidth(2);
		}
		else{
			block.setFill(Color.WHITE);
			block.setStroke(Color.BLACK);
			block.setStrokeWidth(2);
		}		
	}
	
	public boolean checkCurrentCellState(int aliveSurroundingCells) {
		boolean isAlive = true;
		if(this.states == States.Alive){
			if(true){
//(aliveSurroundingCells >= 3) || (aliveSurroundingCells < 2)
				isAlive = false;
			}
		}
		else{
			if(true){
				//(aliveSurroundingCells == 3)
				isAlive = true;
			}
		}
		return isAlive;
	}
	
	@Override
	public void fillCellWithColors() {
		block.setFill(Color.WHITE);
		block.setStroke(Color.BLUE);
		block.setStrokeWidth(2);
	}

}
