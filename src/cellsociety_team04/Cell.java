package cellsociety_team04;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Cell {
	protected Pane rootElement;
	protected Rectangle block;
	public static final int cellSize = 25;

	public Cell(int sizeOfCell,Pane rootElement,int xCoord,int yCoord){
		this.rootElement = rootElement;
		this.block = new Rectangle(xCoord,yCoord,sizeOfCell,sizeOfCell);
	}
	
	public abstract void fillCellWithColors();
	/*
	 * @Override
		public void fillCellWithColors() {
			block.setFill(Color.WHITE); //Main Color
			block.setStroke(Color.BLUE); // Border Color
			block.setStrokeWidth(2);
		}
	 */
	
	public void addToScene(){
		rootElement.getChildren().add(block);
	}

}
