package hellofx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class FirstFormController {

	@FXML
	private Line line;

	@FXML
	private ImageView newImage;

	@FXML
	private Label saveDate;

	@FXML
	private ImageView saveImge;

	SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(false);
	
	
	@FXML
	public
	SimpleBooleanProperty onMouseClicked(MouseEvent event) {
		if (event!=null) {
		////System.out.println("void onMouseClicked(MouseEvent event) {");
		booleanProperty.set(true);
		}
		return booleanProperty;
	}

	@FXML
	void onMouseEntered(MouseEvent event) {
		////System.out.println("void onMouseEntered(MouseEvent event) {");
	}

	@FXML
	void onMouseExited(MouseEvent event) {
		////System.out.println("void onMouseExited(MouseEvent event) {");
	}

}
