package hellofx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
//import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameFormController {
	private final BooleanProperty rotateFinished = new SimpleBooleanProperty(true);
	private String currentSideRotating = "";
	private static int dimention = 10;
	private static double multiple = setDimention(dimention);
	private static double DOUBLE_1 = -1.1 * multiple;

	private static double DOUBLE_jump = +1.1;

	private static final int FACE = 2;// face
	private static final int RIGHT = 0;// right
	private static final int UP = 3;// up
	private static final int BACK = 1;// back
	private static final int LEFT = 4;// left
	private static final int DOWN = 5;// down
	private static final int GRAY = 6;// blank

	private static final float X_Face = 0.5f / 7f;
	private static final float X_Right = 1.5f / 7f;
	private static final float X_Up = 2.5f / 7f;
	private static final float X_Back = 3.5f / 7f;
	private static final float X_Left = 4.5f / 7f;
	private static final float X_Down = 5.5f / 7f;
	private static final float X_Blank = 6.5f / 7f;
	private final Color[] COLORS = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.WHITE,
			Color.GRAY };
	private ChangeListener<? super Number> rotMap;
	private Group sceneRootGroup = new Group();

	private int rotateDegree;

	private Rotate rotateXMeshGroup;

	private Rotate rotateYMeshGroup;

	private Rotate rotateZMeshGroup;

	SubScene subScene = new SubScene(sceneRootGroup, 600, 600, true, javafx.scene.SceneAntialiasing.BALANCED);
	private final PerspectiveCamera camera = new PerspectiveCamera(true);
	private final AmbientLight ambientLight = new AmbientLight(Color.WHITE);
//	private final PointLight light1 = new PointLight(Color.WHITE);
	private static Group meshGroup = new Group();

	private HashMap<String, Integer> sidesRotate = new HashMap<String, Integer>();// side, number for transform
	private Point3D axis;
	private HashMap<String, ArrayList<LittleCube>> sidesMap = new HashMap<String, ArrayList<LittleCube>>();// Side-layer-name
	private ArrayList<LittleCube> arrayListCurrnetLayer;
	private ArrayList<LittleCube> cubes = new ArrayList<LittleCube>();
	private String[] rotateNames = { "F", "R", "U", "B", "L", "D", "X", "Y", "Z", "M", "S", "E" };
	private ArrayList<Button> arrayListButton = new ArrayList<>();

	private double mouseOldX;

	private double mouseOldY;
	private double mousePosX;
	private double mousePosY;
	private double mouseReleasedY;
	private double mouseReleasedX;
	private double mousePresseddY;
	private double mousePresseddX;

	private EventHandler<ActionEvent> eventHandlerButton = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {

			EventTarget target = event.getTarget();
			if (target instanceof Button) {

				Button b = (Button) target;
				validateButton(b);

			}
		}

	};
	private Button buttonScramble;

	@FXML
	private MenuItem scShotItem;

	@FXML
	void screenShotAction(ActionEvent event) {

//		WritableImage snapshot = centerPane.getScene().snapshot(null);
		WritableImage snapshot = centerPane.snapshot(null, null);

//		WritableImage asdf = new Pane(sceneRootGroup).snapshot(null, null);

		saveImageFile(snapshot, null);
//		subScene = new SubScene(sceneRootGroup, 600, 600, true, javafx.scene.SceneAntialiasing.BALANCED);
	}

	private void saveImageFile(WritableImage writableImage, Stage stage) {

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("image files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
//            /**/*from ww w . j av a  2s.c  o  m*/
//             * ?.
//             */
			String fileName = file.getName();

			if (!fileName.toUpperCase().endsWith(".PNG")) {
				file = new File(file.getAbsolutePath() + ".png");
			}

			// PixelReader pixelReader = image.getPixelReader();
			// int width = (int) image.getWidth();
			// int height = (int) image.getHeight();
			// WritableImage writableImage = new WritableImage(pixelReader, width, height);

			try {
				ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@FXML
	private MenuItem mentuItemNew;
	private SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(false);

	@FXML
	SimpleBooleanProperty mentuItemNewOnAction(ActionEvent event) {
		if (event != null) {

			simpleBooleanProperty.set(true);
			
			ObservableList<Node> children = meshGroup.getChildren();
			for (int i = 0; i < children.size(); i++) {
				Node node = children.get(i);
				boolean remove = children.remove(node);
				if (remove) {
					i++;
				}
				
				
			}
			
		}
		return simpleBooleanProperty;
	}

	@FXML
	private Pane centerPane;

	@FXML
	private TextField commandField;

	@FXML
	private Button runButton;

	@FXML
	void run(ActionEvent event) {
		//System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		String result = commandField.getText();
		String response = "";
		if (result.length() > 0 && result != null) {

			response = result;
			ArrayList<String> arrayList1 = new ArrayList<>();
			String[] split = response.split(" ");

			for (int i = 0; i < split.length; i++) {
				String string = split[i];

				String rotateName = findRotateName(string);
				int before = getNumberBefor(string, rotateName);
				int after = getNumberafter(string, rotateName);

				if (after > 1) {

					for (int j = 1; j <= after; j++) {
						arrayList1.add(rotateName + (before > 1 ? "-" + before : ""));
					}
				} else {
					arrayList1.add(rotateName + (before > 1 ? "-" + before : ""));
				}
			}

			///////////////

//		ArrayList<Button> arrayList = new ArrayList<>();

			SimpleIntegerProperty intProperty = new SimpleIntegerProperty();
			intProperty.set(-1);

			ChangeListener<Boolean> v = new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				////System.out.println("222222222222");
					if (!oldValue) {
						if (newValue) {
							intProperty.set(intProperty.get() + 1);
							if (intProperty.get() < arrayList1.size()) {
								find(arrayList1.get(intProperty.get()));
							}
						}
					}
				}

			};

			SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty();
			booleanProperty.set(false);

			ChangeListener<Number> listenerInt = new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				////System.out.println("111111111");
					if (newValue.intValue() == arrayList1.size()) {
						rotateFinished.removeListener(v);
						booleanProperty.set(true);
					}
				}
			};
			booleanProperty.addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						intProperty.removeListener(listenerInt);
					}

				}
			});
			intProperty.addListener(listenerInt);
			rotateFinished.addListener(v);
			intProperty.set(intProperty.get() + 1);
			find(arrayList1.get(intProperty.get()));

		}
	}

	private void find(String string) {
		if (string.contains("-")) {
			String[] split = string.split("-");

			int layer = Integer.parseInt(split[1]);
			findSideLayer_Rotate(split[0], layer);

		} else {
			findSideLayer_Rotate(string, 0);
		}

	}
	private void findSideLayer_Rotate(String side, int layer) {
		int degree = 90;

		if (side.contains("X")) {
			degree *= -1;
		}

		if (side.contains("i")) {
			side = side.replace("i", "");
			degree *= -1;
		}
		if (side.contains("M") || side.contains("S") || side.contains("E")) {
//			////System.out.println(side + " 111> " + layer);
			if (side.contains("E")) {
				degree *= -1;
			}

			side = side.replace("M", "L");
			side = side.replace("S", "F");
			side = side.replace("E", "U");

			layer = dimention / 2;
//			////System.out.println(side + " 222> " + layer + " dim " + dimention + " >> " + (dimention / 2));

		}
		if (side.contains("B") || side.contains("D") || side.contains("R")) {
//			////System.out.println(side + " 1> " + layer);
			side = side.replace("B", "F");
			side = side.replace("D", "U");
			side = side.replace("R", "L");

			side = side.replace("M", "L");
			side = side.replace("S", "F");
			side = side.replace("E", "U");

			layer = dimention - 1 - layer;
//			////System.out.println(side + " 2> " + layer);
			degree *= -1;
		}
//		if (side.contains("D")) {
//			side = side.replace("D", "U");
//			layer = dimention - 1 - layer;
//			degree *= -1;
//		}
//		if (side.contains("R")) {
//			side = side.replace("R", "L");
//			layer = dimention - 1 - layer;
//			degree *= -1;
//		}
		String sideLayer = side.toLowerCase();
		if (layer >= 0) {
			sideLayer += layer;
		} else {
			sideLayer += "0";
		}

		boolean canRotate = canRotate(sideLayer);
		if (canRotate) {
			currentSideRotating = sideLayer;
			rotateDegree += degree;
//			////System.out.println(sideLayer + " 2222222 aaaa");

			realRotate(sideLayer, degree);
//				

		}
	}
	private int getNumberafter(String string, String s) {
		String a = string.substring(string.indexOf(s) + s.length());
		try {
			return Integer.parseInt(a);
		} catch (Exception e) {
		}

		return 1;

	}

//F L F Ui R U F2 L2 Ui Li B Di Bi L2 U
	private int getNumberBefor(String string, String s) {
		String a = string.substring(0, string.indexOf(s));
		try {
			return Integer.parseInt(a);
		} catch (Exception e) {
		}

		return 0;
	}

	private String findRotateName(String string) {
		String res = "";
		for (int i = 0; i < rotateNames.length; i++) {
			String s = rotateNames[i];
			if (string.toLowerCase().contains(s.toLowerCase())) {
				if (string.toLowerCase().contains("i")) {
					res = s + "i";
				} else {

					res = s;
				}
			}

		}
		return res;
	}

	@FXML
	private ToolBar tbLeft;

	@FXML
	private ToolBar tbRight;

	public GameFormController(int dimention, Color face, Color right, Color up, Color back, Color left, Color down) {
		multiple = setDimention(dimention);
		// //System.out.println("asdfas dfasd asd asd asdf asd fasf");

		DOUBLE_1 = -1.1 * multiple;
		DOUBLE_jump = +1.1;

		COLORS[0] = right;
		COLORS[1] = back;
		COLORS[2] = face;
		COLORS[3] = up;
		COLORS[4] = left;
		COLORS[5] = down;
	}

	@FXML
	public void initialize() {
		centerPane.getChildren().add(subScene);
		subScene.widthProperty().bind(centerPane.widthProperty());
		subScene.heightProperty().bind(centerPane.heightProperty());

		validateScene();

		setter();

		rotMap = initRotMap();

	}

	private void validateScene() {

//		gCount("private void validateScene() {");
		subScene.setFill(Color.web("#98caa4"));
//		subScene.widthProperty().bind(((AnchorPane)meshGroup.getParent()).widthProperty());
//		subScene.heightProperty().bind(((AnchorPane)meshGroup.getParent()).heightProperty());
		subScene.setPickOnBounds(true);

		setCamera();
		addLights();

		addAxis();

		createLittleCubes();

		Button bReset = new Button("Restart");
		bReset.setDisable(true);

		buttonScramble = new Button("Scramble");

//		Button bReplay = new Button("Replay");
//		bReplay.setDisable(true);
//
//		buttonSequence = new Button("Sequence");
//
//		Label lSolved = new Label("Solved");
//		lSolved.setVisible(false);
////
//		tbTop.getItems().addAll(new Separator(), bReset, bSc, bReplay, bSeq, new Separator(), lSolved, new Separator(),
//				BScreenShot);
//		pane.setTop(tbTop);
//		tbRight = new ToolBar(new Button("R"), new Button("Ri"), new Separator(), new Button("X"), new Button("Xi"));
		tbRight.getItems().addAll(buttonScramble);

//		pane.setRight(tbRight);

//		tbRight.setOrientation(Orientation.VERTICAL);
//		tbLeft = new ToolBar();

//		tbLeft.setOrientation(Orientation.VERTICAL);
//		tbLeft.setMinWidth(300);

//		tbTop.getItems().addAll();

		addButton1(tbLeft, rotateNames[8], rotateNames[6], rotateNames[7]);
		addButton1(tbLeft, rotateNames[8] + "i", rotateNames[6] + "i", rotateNames[7] + "i");
		addButton(rotateNames[0], tbLeft);
		addButton(rotateNames[0] + "i", tbLeft);
		addButton(rotateNames[1], tbLeft);
		addButton(rotateNames[1] + "i", tbLeft);
		addButton(rotateNames[2], tbLeft);
		addButton(rotateNames[2] + "i", tbLeft);

		if (dimention % 2 != 0) {
			addButton1(tbLeft, rotateNames[10], rotateNames[9], rotateNames[11]);
			addButton1(tbLeft, rotateNames[10] + "i", rotateNames[9] + "i", rotateNames[11] + "i");
		}

		addButton(rotateNames[3], tbLeft);
		addButton(rotateNames[3] + "i", tbLeft);
		addButton(rotateNames[4], tbLeft);
		addButton(rotateNames[4] + "i", tbLeft);
		addButton(rotateNames[5], tbLeft);
		addButton(rotateNames[5] + "i", tbLeft);

//		////System.out.println(arrayListButton.size()+" s  ss ");
		tbLeft.getItems().add(new Separator());
//		pane.setLeft(tbLeft);

//		pane.setCenter(subScene);
		WritableImage image = new WritableImage(COLORS.length, 1);// create image looks like palette.png
		PhongMaterial mat = new PhongMaterial();

		PixelWriter writer = image.getPixelWriter();
		for (int ii = 0; ii < COLORS.length; ii++) {
			writer.setColor(ii, 0, COLORS[ii]);
		}
		mat.setDiffuseMap(image);
		mat.setSpecularPower(1);
		setRotateToLittleCubes(mat);

		rotateXMeshGroup = new Rotate(30, 0, 0, 0, Rotate.X_AXIS);
		rotateYMeshGroup = new Rotate(30, 0, 0, 0, Rotate.Y_AXIS);
		rotateZMeshGroup = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);

		meshGroup.getTransforms().addAll(rotateXMeshGroup, rotateYMeshGroup, rotateZMeshGroup);

		sceneRootGroup.getChildren().add(meshGroup);

	}

	private void setCamera() {
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		camera.setTranslateZ(-15 - 7 * multiple);
		subScene.setCamera(camera);
	}

	private void addLights() {
		sceneRootGroup.getChildren().add(ambientLight);
//		sceneRootGroup.getChildren().add(light1);
//		double dimModel = 100 * 2;
//		light1.setTranslateX(camera.getTranslateX());
//		light1.setTranslateY(camera.getTranslateY());
//		light1.setTranslateZ(camera.getTranslateZ());

	}

	private void addAxis() {

		int AxisLength = dimention * 2;
		Box xAxis = new Box(AxisLength, 0.1, 0.1);
		Box yAxis = new Box(0.1, AxisLength, 0.1);
		Box zAxis = new Box(0.1, 0.1, AxisLength);

		xAxis.setTranslateX(xAxis.getWidth() / 2);

		yAxis.setTranslateY(-yAxis.getHeight() / 2);
		zAxis.setTranslateZ(-zAxis.getDepth() / 2);

		xAxis.setMaterial(new PhongMaterial(Color.RED));
		yAxis.setMaterial(new PhongMaterial(Color.YELLOW));
		zAxis.setMaterial(new PhongMaterial(Color.BLUE));

		Group axisGroup = new Group();
		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		meshGroup.getChildren().add(axisGroup);

	}

	private void setRotateToLittleCubes(PhongMaterial mat) {
//		gCount("private void setRotateToLittleCubes() {");

		for (int i = 0; i < cubes.size(); i++) {
			LittleCube littleCube = cubes.get(i);
			MeshView meshView = littleCube.getMeshview();
			ObservableList<Transform> transforms = meshView.getTransforms();

			Affine affineIni = new Affine();
			affineIni.prepend(new Rotate(0, Rotate.X_AXIS));
			affineIni.prepend(new Rotate(0, Rotate.Z_AXIS));
			transforms.add(affineIni);

			TriangleMesh mesh = createCube(littleCube.getIntArray());

			meshView.setMesh(mesh);
			meshView.setMaterial(mat);
//			meshView.setCursor(Cursor.W_RESIZE);

//			mesh.setCullFace(CullFace.FRONT);

			Point3D point3d = littleCube.getPoint3D();
			transforms.addAll(new Translate(point3d.getX(), point3d.getY(), point3d.getZ()));
			meshGroup.getChildren().add(meshView);

		}
	}

	private void createLittleCubes() {
		int id = 1;
		for (int f = 0; f < dimention; f++) {// ==z
			for (int u = 0; u < dimention; u++) {// ==y
				for (int l = 0; l < dimention; l++) {// ==x

					if (isInside(l)) {
						if (isInside(u)) {
							if (isInside(f)) {
								continue;
							}
						}
					}

					LittleCube littleCube = new LittleCube(l, u, f);
					littleCube.setID(id++);

					littleCube.setFaceColor(GRAY);
					littleCube.setBackColor(GRAY);
					littleCube.setRightColor(GRAY);
					littleCube.setLeftColor(GRAY);
					littleCube.setUpColor(GRAY);
					littleCube.setDownColor(GRAY);
					littleCube.paint();

					littleCube.setMeshView(new MeshView());

					littleCube.setPoint3D(new Point3D(DOUBLE_1 + l * DOUBLE_jump, DOUBLE_1 + u * DOUBLE_jump,
							DOUBLE_1 + f * DOUBLE_jump));

					initMaps("l", l, "u", u, "f", f, littleCube);

					cubes.add(littleCube);

				}
			}
		}

	}

	private boolean isInside(int l) {
//		gCount("private boolean isInside(int l) {");
		boolean res = false;
		if (l > 0 && l < dimention - 1) {
			res = true;
		}

		return res;
	}

	private void initMaps(String sideL, int layerL, String sideU, int layerU, String sideF, int layerF,
			LittleCube littleCube) {
//		gCount("private void initMaps(String sideL, int layerL, String sideU, int layerU, String sideF, int layerF, LittleCube littleCube) {");

		sideL += layerL;
		sideU += layerU;
		sideF += layerF;

		sidesRotate.put(sideL, 0);
		if (layerL == 0) {
			littleCube.setLeftColor(LEFT);
		} else if (layerL == dimention - 1) {
			littleCube.setRightColor(RIGHT);
		}

		sidesRotate.put(sideU, 1);
		if (layerU == 0) {
			littleCube.setUpColor(UP);
		} else if (layerU == dimention - 1) {
			littleCube.setDownColor(DOWN);
		}

		sidesRotate.put(sideF, 2);
		if (layerF == 0) {
			littleCube.setFaceColor(FACE);
		} else if (layerF == dimention - 1) {
			littleCube.setBackColor(BACK);
		}

		littleCube.paint();

		fillSidesMap(sideL, littleCube);

		fillSidesMap(sideU, littleCube);

		fillSidesMap(sideF, littleCube);

		fillSidesMap("x0", littleCube);
		sidesRotate.put("x0", 0);

		fillSidesMap("y0", littleCube);
		sidesRotate.put("y0", 1);

		fillSidesMap("z0", littleCube);
		sidesRotate.put("z0", 2);

	}

	private TriangleMesh createCube(int[] face) {
//		gCount("private TriangleMesh createCube(int[] face) {");

		TriangleMesh m = new TriangleMesh();

		// POINTS
		m.getPoints().addAll(0.5f/* x */, 0.5f/* y */, 0.5f/* z */ // p0
				, 0.5f, -0.5f, 0.5f, // p1
				0.5f, 0.5f, -0.5f, // p2
				0.5f, -0.5f, -0.5f, // p3
				-0.5f, 0.5f, 0.5f, // p4
				-0.5f, -0.5f, 0.5f, // p5
				-0.5f, 0.5f, -0.5f, // p6
				-0.5f, -0.5f, -0.5f // p7};
		);

		// TEXTURES
		m.getTexCoords().addAll(X_Face, 0.5f, X_Right, 0.5f, X_Up, 0.5f, X_Back, 0.5f, X_Left, 0.5f, X_Down, 0.5f,
				X_Blank, 0.5f);

		// FACES
		m.getFaces().addAll(2, face[0], 3, face[0], 6, face[0], // F
				3, face[0], 7, face[0], 6, face[0],

				0, face[1], 1, face[1], 2, face[1], // R
				2, face[1], 1, face[1], 3, face[1],

				1, face[2], 5, face[2], 3, face[2], // U
				5, face[2], 7, face[2], 3, face[2],

				0, face[3], 4, face[3], 1, face[3], // B
				4, face[3], 5, face[3], 1, face[3],

				4, face[4], 6, face[4], 5, face[4], // L
				6, face[4], 7, face[4], 5, face[4],

				0, face[5], 2, face[5], 4, face[5], // D
				2, face[5], 6, face[5], 4, face[5]);
		return m;
	}

	private void setter() {
//		gCount("private void setter(Scene scene) {");
		buttonScramble.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Random random = new Random();
				ArrayList<Button> arrayList = new ArrayList<>();

				SimpleIntegerProperty intProperty = new SimpleIntegerProperty();
				intProperty.set(0);

				ChangeListener<Boolean> v = new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (!oldValue) {
							if (newValue) {
								validateButton(arrayList.get(intProperty.get()));
								intProperty.set(intProperty.get() + 1);
							}
						}
					}

				};

				intProperty.addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {

						if (newValue.intValue() == arrayList.size()) {
							rotateFinished.removeListener(v);
						}
					}
				});

				rotateFinished.addListener(v);
				for (int i = 0; i < 20; i++) {
					int nextInt = random.nextInt(arrayListButton.size());
					Button button = arrayListButton.get(nextInt);
					arrayList.add(button);
				}
				validateButton(arrayList.get(intProperty.get()));
			}
		});
//		buttonSequence.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				String response = "";
//				TextInputDialog tid = new TextInputDialog();
//				tid.setTitle("Warning Dialog");
//				tid.setHeaderText("Loading a Sequence");
//				tid.setContentText("Add a valid sequence of movements:\n(previous movements will be discarded)");
//				Optional<String> result = tid.showAndWait();
//				if (result.isPresent()) {
//					response = result.get();
//					ArrayList<String> arrayList1 = new ArrayList<>();
//					String[] split = response.split(" ");
//
//					for (int i = 0; i < split.length; i++) {
//						String string = split[i];
//
//						String rotateName = findRotateName(string);
//						int before = getNumberBefor(string, rotateName);
//						int after = getNumberafter(string, rotateName);
//
//						if (after > 1) {
//
//							for (int j = 1; j <= after; j++) {
//								arrayList1.add(rotateName + (before > 1 ? "-" + before : ""));
//							}
//						} else {
//							arrayList1.add(rotateName + (before > 1 ? "-" + before : ""));
//						}
//					}
//
//					///////////////
//
////					ArrayList<Button> arrayList = new ArrayList<>();
//
//					SimpleIntegerProperty intProperty = new SimpleIntegerProperty();
//					intProperty.set(-1);
//
//					ChangeListener<Boolean> v = new ChangeListener<Boolean>() {
//
//						@Override
//						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
//								Boolean newValue) {
////							////System.out.println("222222222222");
//							if (!oldValue) {
//								if (newValue) {
//									intProperty.set(intProperty.get() + 1);
//									if (intProperty.get() < arrayList1.size()) {
//										find(arrayList1.get(intProperty.get()));
//									}
//								}
//							}
//						}
//
//					};
//
//					SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty();
//					booleanProperty.set(false);
//
//					ChangeListener<Number> listenerInt = new ChangeListener<Number>() {
//
//						@Override
//						public void changed(ObservableValue<? extends Number> observable, Number oldValue,
//								Number newValue) {
////							////System.out.println("111111111");
//							if (newValue.intValue() == arrayList1.size()) {
//								rotateFinished.removeListener(v);
//								booleanProperty.set(true);
//							}
//						}
//					};
//					booleanProperty.addListener(new ChangeListener<Boolean>() {
//
//						@Override
//						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
//								Boolean newValue) {
//							if (newValue) {
//								intProperty.removeListener(listenerInt);
//							}
//
//						}
//					});
//					intProperty.addListener(listenerInt);
//					rotateFinished.addListener(v);
//					intProperty.set(intProperty.get() + 1);
//					find(arrayList1.get(intProperty.get()));
//
//				}
//				// F L F Ui R U F2 L2 Ui Li B Di Bi L2 U
////				 Ui Li Ui Fi R2 Bi R F U B2 U Bi L Ui F U R Fi
//			}
//
//			private void find(String string) {
//				if (string.contains("-")) {
//					String[] split = string.split("-");
//
//					int layer = Integer.parseInt(split[1]);
//					findSideLayer_Rotate(split[0], layer);
//
//				} else {
//					findSideLayer_Rotate(string, 0);
//				}
//
//			}
//
//			private int getNumberafter(String string, String s) {
//				String a = string.substring(string.indexOf(s) + s.length());
//				try {
//					return Integer.parseInt(a);
//				} catch (Exception e) {
//				}
//
//				return 1;
//
//			}
//
////F L F Ui R U F2 L2 Ui Li B Di Bi L2 U
//			private int getNumberBefor(String string, String s) {
//				String a = string.substring(0, string.indexOf(s));
//				try {
//					return Integer.parseInt(a);
//				} catch (Exception e) {
//				}
//
//				return 0;
//			}
//
//			private String findRotateName(String string) {
//				String res = "";
//				for (int i = 0; i < rotateNames.length; i++) {
//					String s = rotateNames[i];
//					if (string.toLowerCase().contains(s.toLowerCase())) {
//						if (string.toLowerCase().contains("i")) {
//							res = s + "i";
//						} else {
//
//							res = s;
//						}
//					}
//
//				}
//				return res;
//			}
//
//		});
		PhongMaterial selected = new PhongMaterial(Color.YELLOW);
		subScene.setOnMousePressed(mouseEvent -> {

			mouseOldX = mouseEvent.getSceneX();
			mouseOldY = mouseEvent.getSceneY();

			mousePresseddX = mouseEvent.getSceneX();
			mousePresseddY = mouseEvent.getSceneY();

			/*
			 * double sceneX = mouseEvent.getSceneX(); /*double screenX =
			 * mouseEvent.getScreenX();
			 */

			// //System.out.println("setOnMousePressed Screen " + sceneX + " " + screenX);

			EventTarget target = mouseEvent.getTarget();
			if (target instanceof MeshView) {
				MeshView meshView = (MeshView) target;

				for (int i = 0; i < cubes.size(); i++) {

					LittleCube littleCube = cubes.get(i);
					MeshView view = littleCube.getMeshview();
					if (view.equals(meshView)) {
//						Node intersectedNode = mouseEvent.getPickResult().getIntersectedNode();
//						////System.out.println(intersectedNode + " vvv ");// MeshView

//						////System.out.println("id " + littleCube.getID() + " luf " + littleCube.getL() + " "
//								+ littleCube.getU() + " " + littleCube.getF() + " " + view.getTransforms());

//						Point3D meshNormal = getMeshNormal(meshView);
//						////System.out.println(meshNormal);
//						////System.out.println("point3d " + littleCube.getPoint3D());
//						meshView.setCursor(Cursor.CLOSED_HAND);

						Material m = view.getMaterial();
						String key = "material";

						if (m.equals(selected)) {
							Material material = (Material) view.getProperties().get(key);
							view.setMaterial(material);

						} else {
							view.getProperties().put(key, m);
							view.setMaterial(selected);

						}

					} else {
//						meshView.setLayoutX(20);
					}

				}
			}

		});

		subScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {

				mouseReleasedX = mouseEvent.getSceneX();
				mouseReleasedY = mouseEvent.getSceneY();

				int moadelatKhat = moadelatKhat(mousePresseddX, mousePresseddY, mouseReleasedX, mouseReleasedY);

				switch (moadelatKhat) {
				case 1:// right
						// //System.out.println("right");
					break;
				case 2:// down
						// //System.out.println("down");
					break;
				case 3:// left
						// //System.out.println("left");
					break;
				case 4:// up
						// //System.out.println("up");
					break;
				case 5:// 1,2
						// //System.out.println("1,2");
					break;
				case 6:// 2,3
						// //System.out.println("2,3");
					break;
				case 7:// 3,4
						// //System.out.println("3,4");
					break;
				case 8:// 4,1
						// //System.out.println("4,1");
					break;
				}

			}
		});
		subScene.setOnMouseDragged(mouseEvent -> {
//			////System.out.println(me);

			mousePosX = mouseEvent.getSceneX();
			mousePosY = mouseEvent.getSceneY();
			double angleXGroup = rotateXMeshGroup.getAngle();
			double angleYGroup = rotateYMeshGroup.getAngle();
			double valueX = angleXGroup + (mousePosY - mouseOldY);
			double valueY = angleYGroup - (mousePosX - mouseOldX);

			EventTarget target = mouseEvent.getTarget();

			if (target instanceof MeshView) {
				// useFull for select with mouse to rotate a layer

//				MeshView meshView = (MeshView) target;
//
//				for (int i = 0; i < cubes.size(); i++) {
//
//					LittleCube littleCube = cubes.get(i);
//					MeshView view = littleCube.getMeshview();
//					if (view.equals(meshView)) {
//					} else {
//					}
//
//				}

			} else {

				boolean checkAngleX = true;
				boolean checkAngleY = true;
//				boolean checkAngleX = checkAngle(valueX);
//				boolean checkAngleY = checkAngle(valueY);

				if (checkAngleX) {
					rotateXMeshGroup.setAngle(valueX);
				}
				if (checkAngleY) {
					rotateYMeshGroup.setAngle(valueY);
				}
			}

			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
//			me = mouseEvent;

//			////System.out.println("rotateXMeshGroup " + valueX);
//			////System.out.println("rotateYMeshGroup " + valueY);
		});
		subScene.setOnScroll(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (event instanceof ScrollEvent) {
					try {
						ScrollEvent scrollEvent = (ScrollEvent) event;
						double deltaY = scrollEvent.getDeltaY();
						camera.setTranslateZ(camera.getTranslateZ() + deltaY / 20);
					} catch (Exception e) {
					}
				}

			}
		});

		subScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case X:
					rotate(0);
					break;
				case Y:
					rotate(1);
					break;
				case Z:
					rotate(2);

					break;
				case L:
					rotate(6);
					break;
				case U:
					rotate(7);
					break;
				case F:
					rotate(8);
					break;
				case ADD:

					rotate(3);
					break;
				case SUBTRACT:
					rotate(4);
					break;
				case S:
					rotate(5);
					break;
				default:
					break;
				}

			}
		});

	}

	private int moadelatKhat(double mouseX0, double mouseY0, double mouseX1, double mouseY1) {
		int res = 0;// 1 right , 2 down , 3 left , 4 up

		if (mouseX1 > mouseX0) {
			if (mouseY1 > mouseY0) {

				double Y1 = Math.tan(Math.toRadians(22.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2
				double Y2 = Math.tan(Math.toRadians(67.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2

				if (mouseY1 <= Y1) {// right , 1st area in 1st quarter
//					////System.out.println("1.1");

					res = 1;
				}
				if (mouseY1 > Y1 && mouseY1 <= Y2) {// 2nd area in 1st quarter
//					////System.out.println("2.1");
					res = 5;
				}
				if (mouseY1 > Y2) {// down , 3rd area in 1st quarter
					// //System.out.println("3.1");
					res = 2;
				}
			}
			if (mouseY1 == mouseY0) {// absolute right

			}
			if (mouseY1 < mouseY0) {

				double Y7 = Math.tan(Math.toRadians(292.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2
				double Y8 = Math.tan(Math.toRadians(337.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2

				if (mouseY1 < Y7) {// up, 3rd area in 4st quarter
					// //System.out.println("3.4");
					res = 4;
				}
				if (mouseY1 >= Y7 && mouseY1 < Y8) {// turn right , 2nd area in 4st quarter
					// //System.out.println("2.4");
					res = 8;
				}
				if (mouseY1 >= Y8) {// right , 1st area in 4st quarter
					// //System.out.println("1.4");
					res = 1;
				}
			}
		}
		if (mouseX1 == mouseX0) {
			if (mouseY1 > mouseY0) {// absolute down

			}
			if (mouseY1 == mouseY0) {// did'nt move

			}

			if (mouseY1 < mouseY0) {// absolute up

			}
		}
		if (mouseX1 < mouseX0) {
			if (mouseY1 > mouseY0) {

				double Y3 = Math.tan(Math.toRadians(112.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2
				double Y4 = Math.tan(Math.toRadians(157.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2
				if (mouseY1 > Y3) {// down , 3rd area in 2nd quarter
					// //System.out.println("3.2");
					res = 2;
				}
				if (mouseY1 <= Y3 && mouseY1 > Y4) {// 2nd area in 2nd quarter
					// //System.out.println("2.2");
					res = 6;
				}
				if (mouseY1 <= Y4) {// left , 1st area in 2nd quarter
					// //System.out.println("1.2");
					res = 3;
				}
			}
			if (mouseY1 == mouseY0) {// absolute left

			}

			if (mouseY1 < mouseY0) {

				double Y5 = Math.tan(Math.toRadians(202.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2
				double Y6 = Math.tan(Math.toRadians(247.5)) * (mouseX1 - mouseX0) + mouseY0;// compare to y2
				if (mouseY1 >= Y5) {// left , 1st area in 3rd quarter
					// //System.out.println("1.3");
					res = 3;
				}
				if (mouseY1 < Y5 && mouseY1 >= Y6) {// 2nd area in 3rd quarter
					// //System.out.println("2.3");
					res = 7;
				}
				if (mouseY1 < Y6) {// up , 3rd area in 3rd quarter
					// //System.out.println("3.3");
					res = 4;
				}
			}
		}
		return res;
	}

	private void rotate(int i) {
//		gCount("private void rotate(int i) {");
		String sideLayer = "l0";
		int degree = 10;
		boolean canRotate = false;
		switch (i) {
		case 0:
			if (rotateFinished.get()) {

//				rotateAll(i);

			}
			break;
		case 1:
			if (rotateFinished.get()) {
//				rotateAll(i);

			}
			break;
		case 2:
			if (rotateFinished.get()) {

//				rotateAll(i);
			}
			break;
		case 3:
			camera.setTranslateX(camera.getTranslateX() + 1);
			break;
		case 4:
			camera.setTranslateX(camera.getTranslateX() - 1);
			break;
		case 5:
			rotateXMeshGroup.setAngle(0);
			rotateYMeshGroup.setAngle(0);
			rotateZMeshGroup.setAngle(0);

			break;
		case 6:

			sideLayer = "l0";
			degree = 90;
			canRotate = canRotate(sideLayer);
			if (canRotate) {
				currentSideRotating = sideLayer;
				rotateDegree += degree;
				realRotate(sideLayer, degree);
			}

			break;
		case 7:
			sideLayer = "u0";
			degree = 90;
			canRotate = canRotate(sideLayer);
			if (canRotate) {
				currentSideRotating = sideLayer;
				rotateDegree += degree;
				realRotate(sideLayer, degree);
			}
			break;
		case 8:
			sideLayer = "f0";
			degree = 90;

			canRotate = canRotate(sideLayer);
			if (canRotate) {
				currentSideRotating = sideLayer;
				rotateDegree += degree;

				realRotate(sideLayer, degree);
//				

			}
			break;

		}
	}

	private void addButton(String string, ToolBar toolBar) {

		int value = 45;

		AnchorPane ancherPane = new AnchorPane();
		ToolBar toolBarbbb = new ToolBar();
		for (int i = 0; i < dimention / 2; i++) {
			String text = string;
			if (i > 0) {

				text = (i + 1) + text;
			}
			Button b = new Button(text);
			b.setPrefWidth(value);
			b.setOnAction(eventHandlerButton);
			b.getProperties().put("Side", string);
			b.getProperties().put("Layer", i);
			// Retrieving the items list of the toolbar
			toolBarbbb.getItems().addAll(b);

			arrayListButton.add(b);
		}
		ancherPane.getChildren().add(toolBarbbb);
		toolBar.getItems().add(ancherPane);
//			vboxFRU.getChildren().addAll(toolBarbbb);

	}

	private void addButton1(ToolBar tbLeft, String s1, String s2, String s3) {
		int value = 45;
		Button x = new Button(s1);
		x.setPrefWidth(value);
		x.getProperties().put("Side", s1);
		arrayListButton.add(x);

		Button y = new Button(s2);
		y.setPrefWidth(value);
		y.getProperties().put("Side", s2);
		arrayListButton.add(y);

		Button z = new Button(s3);
		z.setPrefWidth(value);
		z.getProperties().put("Side", s3);
		arrayListButton.add(z);

		x.setOnAction(eventHandlerButton);
		y.setOnAction(eventHandlerButton);
		z.setOnAction(eventHandlerButton);

		ToolBar toolBarXYZ = new ToolBar();
		// Retrieving the items list of the toolbar
		toolBarXYZ.getItems().addAll(x, y, z);

		VBox vbox = new VBox();
		vbox.setSpacing(15);
//		vbox.setPadding(new Insets(50, 50, 50, 100));
		vbox.getChildren().addAll(toolBarXYZ);
		tbLeft.getItems().add(vbox);
	}

	private void fillSidesMap(String side, LittleCube littleCube) {
		ArrayList<LittleCube> arrayList = new ArrayList<LittleCube>();
//		////System.out.println(side + " + ");
//		ArrayList<LittleCube> put = new ArrayList<>();
//		boolean add = false;

		if (sidesMap.containsKey(side)) {
			arrayList = sidesMap.get(side);
//			add =
			arrayList.add(littleCube);

		} else {
			arrayList = new ArrayList<LittleCube>();
			arrayList.add(littleCube);
			sidesMap.put(side, arrayList);
//			put =
			sidesMap.get(side);
		}

//		////System.out.println("add > "+add+ " put > "+arrayList);
	}

	private ChangeListener<Number> initRotMap() {
		return (ObservableValue<? extends Number> observable, Number angOld, Number angNew) -> {
			for (int i = 0; i < arrayListCurrnetLayer.size(); i++) {
				LittleCube littleCube = arrayListCurrnetLayer.get(i);
				MeshView meshview = littleCube.getMeshview();
				ObservableList<Transform> transforms = meshview.getTransforms();

				Affine a = new Affine(transforms.get(0));
//					////System.out.println(a + " before ");

				a.prepend(new Rotate(angNew.doubleValue() - angOld.doubleValue(), axis));
//					////System.out.println(a + " after");
//					////System.out.println(angNew.doubleValue() + " cc " + angOld.doubleValue() + " AXis " + axis);

				transforms.remove(transforms.get(0));
				transforms.add(0, a);

			}
		};
	}

	private static double setDimention(int i) {
//		gCount("private static double setDimention(int i) {");
		dimention = i;
		double res = 0.0;
		if (i > 1) {
			i--;

			res += i * 0.5;
		}
		return res;
	}



	private void validateButton(Button b) {
		String side = (String) b.getProperties().get("Side");
		Object object = b.getProperties().get("Layer");
		int layer = 0;
		if (object != null) {
			layer = (Integer) object;
		}
//		////System.out.println(side + " >>>>>>>>> " + layer);
		findSideLayer_Rotate(side, layer);
	}

	private void realRotate(String sideLayer, int degree) {
//		gCount("private void rotate(String sideLayer, int degree) {");

		arrayListCurrnetLayer = sidesMap.get(sideLayer);
//		////System.out.println(sideLayer + " >> " + arrayListCurrnetLayer);
		Timeline timeline = new Timeline();
		KeyValue keyValue = null;
		KeyFrame keyFrame = null;
		boolean doTransition = true;

		timeline = new Timeline();

		Integer indexAxis = sidesRotate.get(sideLayer);

		// //System.out.println(sidesRotate + " >>>>>>> " + sideLayer + " >>> " +
		// indexAxis);

		switch (indexAxis) {
		case 0:
			axis = Rotate.X_AXIS;
			break;
		case 1:
			axis = Rotate.Y_AXIS;
			break;
		case 2:
			axis = Rotate.Z_AXIS;
			break;

		}

		int firstValue = 0;
		int endValue = degree;

		DoubleProperty rotation = new SimpleDoubleProperty(0d);
		rotation.set(firstValue);
		rotation.addListener(rotMap);

		for (int j = 0; j < arrayListCurrnetLayer.size(); j++) {
			LittleCube littleCube = arrayListCurrnetLayer.get(j);
			changeSide(littleCube, sideLayer, (degree > 0) ? true : false);
			rotateDegree = degree;
		}
		keyValue = new KeyValue(rotation, endValue);
		EventHandler<ActionEvent> onFinished = event -> {
			rotation.removeListener(rotMap);
			rotateFinished.set(true);
		};
		keyFrame = new KeyFrame(Duration.millis(500), onFinished, keyValue);

		timeline.getKeyFrames().add(keyFrame);
		if (doTransition) {
			timeline.playFromStart();
		}
		rotateFinished.set(false);

	}

	private void changeSide(LittleCube littleCube, String rotatingSideLayer, boolean clockWise) {
		int oldL = littleCube.getL();
		int oldU = littleCube.getU();
		int oldF = littleCube.getF();

		int newL = -1;
		int newU = -1;
		int newF = -1;

//		if (rotatingSideLayer.equals("x")) {
//			clockWise=!clockWise;
//		////System.out.println("ffffffffffffffffffffffffffffff");	
//		}

		switch (rotatingSideLayer.substring(0, 1)) {

		case "s":
		case "z":
		case "f":

			if (clockWise) {
				newF = oldF;

				newU = oldL;
				newL = dimention - 1 - oldU;

			} else {
				newF = oldF;

				newU = dimention - 1 - oldL;
				newL = oldU;

			}
			break;

		case "e":
		case "y":
		case "u":
			if (clockWise) {
				newU = oldU;

				newL = oldF;
				newF = dimention - 1 - oldL;

			} else {
				newU = oldU;

				newL = dimention - 1 - oldF;
				newF = oldL;

			}
			break;

		case "x":
		case "m":
		case "l":
			if (clockWise) {

				newL = oldL;

				newF = oldU;
				newU = dimention - 1 - oldF;
			} else {
				newL = oldL;

				newF = dimention - 1 - oldU;
				newU = oldF;

			}

			break;
		}
		checkOldNewValues(littleCube, oldL, oldU, oldF, newL, newU, newF);
	}

	private void checkOldNewValues(LittleCube littleCube, int oldL, int oldU, int oldF, int newL, int newU, int newF) {
		if (newL > -1) {

			if (oldL != newL) {
				sidesMap.get("l" + oldL).remove(littleCube);
				littleCube.setL(newL);
				sidesMap.get("l" + newL).add(littleCube);
			}
		}

		if (newU > -1) {

			if (oldU != newU) {
				sidesMap.get("u" + oldU).remove(littleCube);
				littleCube.setU(newU);
				sidesMap.get("u" + newU).add(littleCube);
			}
		}

		if (newF > -1) {

			if (oldF != newF) {
				sidesMap.get("f" + oldF).remove(littleCube);
				littleCube.setF(newF);
				sidesMap.get("f" + newF).add(littleCube);
			}
		}
	}

	private boolean canRotate(String sideLayer) {
//		gCount("private boolean canRotate(String sideLayer) {");

		boolean res = false;
		if (!rotateFinished.get()) {
			return rotateFinished.get();
		}
		if (currentSideRotating.equals(sideLayer)) {
			return true;
		}
		if (currentSideRotating.equals("")) {
			currentSideRotating = sideLayer;
			return true;
		}
		if (rotateDegree % 90 == 0) {
			currentSideRotating = sideLayer;
			res = true;
		} else {
			res = false;
		}
		return res;
	}

}
