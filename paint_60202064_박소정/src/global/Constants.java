package global;

import shapes.TArc;
import shapes.TFreeLine;
//import shapes.TArc;
import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;
import shapes.TTriangle;

public class Constants {
	
	public enum ETransformationStyle {
		e2PTransformation,
		eNPTransformation
	}

	public enum ETools {
		//variable 이름임
		eSelection("선택", new TSelection(), "images/line.png", ETransformationStyle.e2PTransformation, "도형을 선택합니다."),
		eRectangle("네모", new TRectangle(), "images/rectangle.png", ETransformationStyle.e2PTransformation, "네모를 그립니다."),
		eOval("동그라미", new TOval(), "images/oval.png", ETransformationStyle.e2PTransformation, "동그라미를 그립니다."),
		eLine("라인", new TLine(), "images/line.png", ETransformationStyle.e2PTransformation, "라인을 그립니다."),
		ePolygon("폴리곤", new TPolygon(), "images/polygon.png", ETransformationStyle.eNPTransformation, "다각형을 그립니다."),
		eTriangle("삼각형", new TTriangle(), "images/triangle.png", ETransformationStyle.e2PTransformation, "세모를 그립니다."),
		eArc("반원", new TArc(), "images/arc.png", ETransformationStyle.e2PTransformation, "반원을 그립니다."),
		eText("글씨", new TFreeLine(), "images/line.png", ETransformationStyle.e2PTransformation, "반원을 그립니다.");
		//
		private String label;
		private TShape tool;
		private String image;
		private ETransformationStyle eTransformationStyle;
		private String toolTip;
		
		private ETools(String label, TShape tool, String image, ETransformationStyle eTransformationStyle, String toolTip) {
			this.tool = tool;
			this.label = label;
			this.image = image;
			this.eTransformationStyle = eTransformationStyle;
			this.toolTip = toolTip;
		}
		public String getLabel() {
			return this.label;
		}
		public String getImage() {
			return this.image;
		}
		public String getToolTip() {
			return this.toolTip;
		}
		public TShape newShape() {
			return this.tool.clone();
		}
		Object getTransformationStyle() {
			return eTransformationStyle;
		}
		public ETransformationStyle geteTransformationStyle() {
			return eTransformationStyle;
		}
		public void seteTransformationStyle(ETransformationStyle eTransformationStyle) {
			this.eTransformationStyle = eTransformationStyle;
		}
	}
	
	public enum EFileMenu {
		eNew("새로만들기", 'N'),
		eOpen("열기", 'O'),
		eSave("저장하기", 'S'),
		eSaveAs("다른이름으로저장", 'A'),
		ePrint("프린트", 'P'),
		eQuit("종료", 'Q');
		
		private String lable;
		private char keyvalue;
		private EFileMenu(String label, char keyvalue) {
			this.lable = label;
			this.keyvalue = keyvalue;
		}
		public String getLabel() {
			return this.lable;
		}
		public char getKeyvalue() {
			return this.keyvalue;
		}
	}
	
	public enum EEditMenu {
		eUndo("뒤로가기", 'Z'),
		eRedo("되돌리기", 'Y'),
		eCut("자르기", 'C'),
		eCopy("복사하기", 'U'),
		ePaste("붙여넣기", 'T'),
		eGroup("그룹화", 'G'),
		eUnGroup("비그룹화", 'B');
		
		private String lable;
		private char keyvalue;
		private EEditMenu(String label, char keyvalue) {
			this.lable = label;
			this.keyvalue = keyvalue;
		}
		public String getLabel() {
			return this.lable;
		}
		public char getKeyvalue() {
			return this.keyvalue;
		}
	}
	
	public enum EColorMenu {
		eLineColor("선색깔", 'L'),
		eFillColor("채우기색깔", 'F');
		
		private String lable;
		private char keyvalue;
		private EColorMenu(String label, char keyvalue) {
			this.lable = label;
			this.keyvalue = keyvalue;
		}
		public String getLabel() {
			return this.lable;
		}
		public char getKeyvalue() {
			return this.keyvalue;
		}
	}

}
