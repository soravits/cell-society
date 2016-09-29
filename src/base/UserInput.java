package base;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

public class UserInput {
	private Alert alert;
	
	public UserInput(){
		
	}
	
	public void selectGridSize(){
		TextField numberField = new TextField();
		numberField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
	}
	
	public void errorPopup(String errorText){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Input error");

		    String s = errorText;
		    alert.setContentText(s);
		    alert.showAndWait();
//		    String msg = "Invalid text entered: ";
		

	}
}
