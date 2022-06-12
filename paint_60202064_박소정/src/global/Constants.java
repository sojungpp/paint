package global;

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
		eTriangle("삼각형", new TTriangle(), "images/triangle.png", ETransformationStyle.e2PTransformation, "세모를 그립니다.");
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
		eNew("새로만들기"),
		eOpen("열기"),
		eSave("저장하기"),
		eSaveAs("다른이름으로저장"),
		ePrint("프린트"),
		eQuit("종료");
		
		private String lable;
		private EFileMenu(String label) {
			this.lable = label;
		}
		public String getLabel() {
			return this.lable;
		}
	}
	
	public enum EEditMenu {
		eUndo("뒤로가기"),
		eRedo("되돌리기"),
		eCut("자르기"),
		eCopy("복사하기"),
		ePaste("붙여넣기"),
		eGroup("그룹화"),
		eUnGroup("비그룹화");
		
		private String lable;
		private EEditMenu(String label) {
			this.lable = label;
		}
		public String getLabel() {
			return this.lable;
		}
	}
	
	public enum EColorMenu {
		eLineColor("선색깔"),
		eFillColor("채우기색깔");
		
		private String lable;
		private EColorMenu(String label) {
			this.lable = label;
		}
		public String getLabel() {
			return this.lable;
		}
	}

}
