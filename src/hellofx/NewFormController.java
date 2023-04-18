package hellofx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

public class NewFormController {

	@FXML
	private ColorPicker backPicker;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private ColorPicker downPicker;

	@FXML
	private ColorPicker facePicker;

	@FXML
	private ColorPicker leftPicker;

	@FXML
	private ColorPicker rightPicker;

	@FXML
	private ColorPicker upPicker;

	@FXML
	private Button generateButton;

	SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(false);

	@FXML
	public void initialize() {

		comboBox.getItems().removeAll(comboBox.getItems());
		int a = 2;
		for (int i = a; i <= 20; i++) {
			comboBox.getItems().add("" + i);
		}
		comboBox.getSelectionModel().select("" + a);

		facePicker.setValue(Color.web("0045AD"));

		rightPicker.setValue(Color.web("B90000"));

		upPicker.setValue(Color.web("FFD500"));

		backPicker.setValue(Color.web("009B48"));

		leftPicker.setValue(Color.web("FF5900"));

		downPicker.setValue(Color.web("FFFFFF"));

	}

	@FXML
	public
	SimpleBooleanProperty generate(ActionEvent event) {
		if (event != null) {
			
			booleanProperty.set(true);
		}
		return booleanProperty;
	}


	public int getDimention() {
		return Integer.parseInt(comboBox.getValue());
	}


	public Color getFaceColor() {
		return facePicker.getValue();
	}
	public Color getRightColor() {
		return rightPicker.getValue();
	}

	public Color getUpColor() {
		return upPicker.getValue();
	}

	public Color getBackColor() {
		return backPicker.getValue();
	}

	public Color getLeftColor() {
		return leftPicker.getValue();
	}

	public Color getDownColor() {
		return downPicker.getValue();
	}

}
