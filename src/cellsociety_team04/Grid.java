package balloonDodge;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid {
	public Grid(int rowLength,int sizeOfCell,Pane rootElement,int initialX,int initialY){
		Rectangle[][] grid = new Rectangle[rowLength][rowLength];
		for(int i=0; i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				Rectangle block = new Rectangle(sizeOfCell * (i) + initialX,sizeOfCell*(j) + initialY,sizeOfCell,sizeOfCell);
				block.setFill(Color.WHITE);
				block.setStroke(Color.BLUE);
				block.setStrokeWidth(2);
				
				grid[i][j] = block;
				rootElement.getChildren().add(block);
			}
		}
		
		
	}
	
	
	
}
