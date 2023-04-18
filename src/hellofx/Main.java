package hellofx;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private FXMLLoader firstfxmlLoader = new FXMLLoader(getClass().getResource("/resource/FirstForm.fxml"));
	private Scene sceneFirt;
	
	private FXMLLoader newfxmlLoader = new FXMLLoader(getClass().getResource("/resource/NewForm.fxml"));
	private Scene sceneNew;
	private SimpleBooleanProperty propertyNew;
	
	private FXMLLoader gamefxmlLoader = new FXMLLoader(getClass().getResource("/resource/GameForm.fxml"));
	private Scene sceneGame;

	private Parent firstfxmlParent;

	private Stage stage;

	private ChangeListener<Boolean> firstListener = new ChangeListener<Boolean>() {

		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
			if (arg2) {
				try {
					newfxmlLoader = new FXMLLoader(getClass().getResource("/resource/NewForm.fxml"));
						sceneNew = new Scene(newfxmlLoader.load());
					stage.setScene(sceneNew);
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
			//System.out.println("aaaaaaaaaaaaaaaaaaaaaa "+arg2);
			if (arg2) {
				try {

					NewFormController controller = (NewFormController) newfxmlLoader.getController();

//					GameFormController gameFormController = (GameFormController) gamefxmlLoader.getController();

//				 gameFormController.put(controller.getDimention(),
//							controller.getFaceColor(), controller.getRightColor(), controller.getUpColor(),
//							controller.getBackColor(), controller.getLeftColor(), controller.getDownColor());
//					gamefxmlLoader.getn
					gamefxmlLoader = new FXMLLoader(getClass().getResource("/resource/GameForm.fxml"));
					//System.out.println("asd asd fasd f "+controller.getDimention());
					gamefxmlLoader.setController(new GameFormController(controller.getDimention(),
							controller.getFaceColor(), controller.getRightColor(), controller.getUpColor(),
							controller.getBackColor(), controller.getLeftColor(), controller.getDownColor()));
//					if (sceneGame == null) {
						Parent load = gamefxmlLoader.load();
						sceneGame = new Scene(load);

//					}
					stage.setScene(sceneGame);
					integerProperty.set(2);
					propertyNew.set(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};

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
					propertyNew = controller.generate(null);
					propertyNew.removeListener(newListener);

					propertyNew.addListener(newListener);

				} else if (arg2.intValue() == 2) {
					GameFormController controller = (GameFormController) gamefxmlLoader.getController();
					SimpleBooleanProperty property = controller.mentuItemNewOnAction(null);
					property.removeListener(firstListener);

					property.addListener(firstListener);
				}
			}
		});
		integerProperty.set(0);
		sceneFirt= new Scene((Parent) firstfxmlParent);

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

		primaryStage.setTitle("newRubik");

//		primaryStage.setResizable(false);
		primaryStage.setScene(sceneFirt);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
