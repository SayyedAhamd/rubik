package hellofx;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	FXMLLoader firstfxmlLoader = new FXMLLoader(getClass().getResource("/resource/FirstForm.fxml"));
	FXMLLoader newfxmlLoader = new FXMLLoader(getClass().getResource("/resource/NewForm.fxml"));

	private Parent firstfxmlParent;

	private Stage stage;

	private ChangeListener<Boolean> firstListener = new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2) {
				try {

					stage.setScene(new Scene(newfxmlLoader.load()));
					integerProperty.set(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	};
	private ChangeListener<Boolean> newListener = new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2) {
				try {

					NewFormController controller = (NewFormController) newfxmlLoader.getController();

					FXMLLoader gamefxmlLoader = new FXMLLoader(getClass().getResource("/resource/GameForm.fxml"));

//					GameFormController gameFormController = (GameFormController) gamefxmlLoader.getController();
					
//				 gameFormController.put(controller.getDimention(),
//							controller.getFaceColor(), controller.getRightColor(), controller.getUpColor(),
//							controller.getBackColor(), controller.getLeftColor(), controller.getDownColor());
//					gamefxmlLoader.getn
					gamefxmlLoader.setController(new GameFormController(controller.getDimention(),
							controller.getFaceColor(), controller.getRightColor(), controller.getUpColor(),
							controller.getBackColor(), controller.getLeftColor(), controller.getDownColor()));
					Parent load = gamefxmlLoader.load();
					stage.setScene(new Scene(load));
					integerProperty.set(2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
//	private ChangeListener<Boolean> gameListener = new ChangeListener<Boolean>() {
//
//		@Override
//		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//
//		}
//	};
	private SimpleIntegerProperty integerProperty;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;

		firstfxmlParent = firstfxmlLoader.load();
		integerProperty = new SimpleIntegerProperty(-1);
		integerProperty.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				if (arg2.intValue() == 0) {

					FirstFormController controller = (FirstFormController) firstfxmlLoader.getController();
					SimpleBooleanProperty onMouseClicked = controller.onMouseClicked(null);
					onMouseClicked.removeListener(firstListener);

					onMouseClicked.addListener(firstListener);

				} else if (arg2.intValue() == 1) {
					//System.out.println("vvvvvvvvvvvvvvvvvvvvvvvv 11111111");
					NewFormController controller = (NewFormController) newfxmlLoader.getController();
					SimpleBooleanProperty property = controller.generate(null);
					property.removeListener(newListener);

					property.addListener(newListener);

				} else if (arg2.intValue() == 2) {

				}
			}
		});
		integerProperty.set(0);
		Scene scene = new Scene((Parent) firstfxmlParent);

//		SimpleBooleanProperty generate = newcontroller.generate(null);
//		generate.addListener(new ChangeListener<Boolean>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//				if (arg2) {
//					try {
//						primaryStage.setScene(new Scene(gamefxmlLoader.load()));
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});

//		Parent newForm = fxmlLoader.load(getClass().getResource("../resource/NewForm.fxml"));
//		Parent gameForm = fxmlLoader.load(getClass().getResource("../resource/GameForm.fxml"));
//		firstForm.getco

		primaryStage.setTitle("Hello World");

//		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
