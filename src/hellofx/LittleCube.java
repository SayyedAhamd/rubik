package hellofx;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;

public class LittleCube {

	private int upColor;
	private int downColor;
	private int faceColor;
	private int backColor;
	private int rightColor;
	private int leftColor;
	private int l;
	private int u;
	private int f;
	private int id;
	private MeshView meshview;
	private Point3D point3D;
	// F R U B L D
	private int[] res = new int[] { 0, 0, 0, 0, 0, 0 };
//	private double rotateAngleX;
//	private double rotateAngleY;
//	private double rotateAngleZ;
//	private RotateTransition rotateShape;
	private String string = "empty";
	private int count = 0;

	void gCount(String s) {
		//System.out.println("RubicsCube4 : " + count + " -> " + s);
		count++;
	}

	public LittleCube() {
		this.settext((count++) + " public LittleCube() {");
	}

	public LittleCube(int x, int y, int z) {
		l = x;
		u = y;
		f = z;
		this.settext((count++) + " public LittleCube(int x, int y, int z) {");
	}

	private String add(String string, int i) {
		this.settext((count++) + " private String add(String string, int i) {");
		return string + "=" + i;

	}

	public String export() {
		String export = "";
		String and = ",";
		String andInArray = "-";

		export += "res=[";
		for (int i = 0; i < res.length; i++) {
			export += "" + res[i] + andInArray;
		}
		export = export.substring(0, export.lastIndexOf(andInArray));
		export += "]";
		export += and;
		export += add("l", l);
		export += and;
		export += add("u", u);
		export += and;
		export += add("f", f);
		export += and;
		export += add("faceColor", faceColor);
		export += and;
		export += add("rightColor", rightColor);
		export += and;
		export += add("upColor", upColor);
		export += and;
		export += add("backColor", backColor);
		export += and;
		export += add("leftColor", leftColor);
		export += and;
		export += add("downColor", downColor);
		export += and;
		//System.out.println(meshview);
		export += point3D.toString();
		return export;

	}

	public int getID() {
		this.settext((count++) + " public int getID() {");
		return id;
	}

	public int[] getIntArray() {
		this.settext((count++) + " public int[] getIntArray() {");

		return res;
	}

	public MeshView getMeshview() {
		this.settext((count++) + " public MeshView getMeshview() {");
		return meshview;
	}

	public Point3D getPoint3D() {
		this.settext((count++) + " public Point3D getPoint3D() {");
		return point3D;
	}

//	public double getRotateAngleX() {
//		this.settext((count++) + " public double getRotateAngleX() {");
//		return rotateAngleX;
//	}
//
//	public double getRotateAngleY() {
//		this.settext((count++) + " public double getRotateAngleY() {");
//		return rotateAngleY;
//	}
//
//	public double getRotateAngleZ() {
//		this.settext((count++) + " public double getRotateAngleZ() {");
//		return rotateAngleZ;
//	}

//	public RotateTransition getRotateShape() {
//		this.settext((count++) + " public RotateTransition getRotateShape() {");
//		return rotateShape;
//	}

	public String getText() {
		this.settext((count++) + " public String getText() {");
		String s = string;
		string = "empty";
		count = 0;
		return s;
	}

	public void setID(int id) {
		this.settext((count++) + " public void setID(int id) {");
		this.id = id;
	}

	public void setL(int l) {
		this.settext((count++) + " public void setL(int l) {");
		this.l = l;
	}

	public int getL() {
		this.settext((count++) + " public int getL() {");
		return this.l;
	}

	public void setU(int u) {
		this.settext((count++) + " public void setU(int u) {");
		this.u = u;
	}

	public int getU() {
		this.settext((count++) + " public int getU() {");
		return this.u;
	}

	public void setF(int f) {
		this.settext((count++) + " public void setF(int f) {");
		this.f = f;
	}

	public int getF() {
		this.settext((count++) + " public int getF() {");
		return this.f;
	}

	public void setMeshView(MeshView meshView) {
		this.settext((count++) + " public void setMeshView(MeshView meshView) {");
		this.meshview = meshView;
		
		meshView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//System.out.println("id " + id + " " + event);
				PickResult pickResult = event.getPickResult();
				int intersectedFace = pickResult.getIntersectedFace();
//				pickResult.getd
				//System.out.println("face" + intersectedFace);
				sayColor(intersectedFace);

			}

		});
		meshView.setCullFace(CullFace.BACK);
//		meshView.setCullFace(CullFace.NONE);
		
//		rotateShape = new RotateTransition(Duration.seconds(1), meshview);
//		rotateAngleX = 0;
//		rotateAngleY = 0;
//		rotateAngleZ = 0;
	}

	private void sayColor(int face) {
		switch (face) {
		case 0:
		case 1:
			//System.out.println(faceColor);
			break;
		case 2:
		case 3:
			//System.out.println(rightColor);

			break;
		case 4:
		case 5:
			//System.out.println(upColor);

			break;
		case 6:
		case 7:
			//System.out.println(backColor);

			break;
		case 8:
		case 9:
			//System.out.println(leftColor);

			break;
		case 10:
		case 11:
			//System.out.println(downColor);

			break;

		}
	}

	public void setPoint3D(Point3D point3d) {
		this.settext((count++) + " public void setPoint3D(Point3D point3d) {");
		this.point3D = point3d;

	}

	public void setUpColor(int color) {
		this.settext((count++) + " public void setUpColor(int color) {");
		upColor = color;
	}

	public void setDownColor(int color) {
		this.settext((count++) + " public void setDownColor(int color) {");
		downColor = color;
	}

	public void setFaceColor(int color) {
		this.settext((count++) + " public void setFaceColor(int color) {");
		faceColor = color;
	}

	public void setBackColor(int color) {
		this.settext((count++) + " public void setBackColor(int color) {");
		backColor = color;
	}

	public void setRightColor(int color) {
		this.settext((count++) + " public void setRightColor(int color) {");
		rightColor = color;
	}

	public void setLeftColor(int color) {
		this.settext((count++) + " public void setLeftColor(int color) {");
		leftColor = color;
	}

	public void paint() {
		this.settext((count++) + " public void paint() {");
		res[0] = faceColor;
		res[1] = rightColor;
		res[2] = upColor;
		res[3] = backColor;
		res[4] = leftColor;
		res[5] = downColor;
	}

	public int getUpColor() {
		return upColor;
	}

	public int getDownColor() {
		return downColor;
	}

	public int getFaceColor() {
		return faceColor;
	}

	public int getBackColor() {
		return backColor;
	}

	public int getRightColor() {
		return rightColor;
	}

	public int getLeftColor() {
		return leftColor;
	}

//	public void setRotateAngleX(double rotateAngleX) {
//		this.settext((count++) + " public void setRotateAngleX(double rotateAngleX) {");
//		this.rotateAngleX = rotateAngleX;
////		this.rotateAngleX = this.rotateAngleX % 360;
//	}
//
//	public void setRotateAngleY(double rotateAngleY) {
//		this.settext((count++) + " public void setRotateAngleY(double rotateAngleY) {");
//		this.rotateAngleY = rotateAngleY;
////		this.rotateAngleY = this.rotateAngleY % 360;
//	}
//
//	public void setRotateAngleZ(double rotateAngleZ) {
//		this.settext((count++) + " public void setRotateAngleZ(double rotateAngleZ) {");
//
//		this.rotateAngleZ = rotateAngleZ;
////		this.rotateAngleZ = this.rotateAngleZ % 360;
//	}

	public void settext(String string) {
		this.string += "\n" + string;
//		this.settext((count++)+"30");

	}

	@Override
	public String toString() {
		this.settext((count++) + " public String toString() {");
		String res = "";
res+=id;
		return res;
	}

}
