package global;

//import shapes.TArc;
import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelection;
import shapes.TShape;

public class Constants {
	
	public enum ETransformationStyle {
		e2PTransformation,
		eNPTransformation
	}

	public enum ETools {
		//variable �̸���
		eSelection("����", new TSelection(), "images/line.png", ETransformationStyle.e2PTransformation),
		eRectangle("�׸�", new TRectangle(), "images/rectangle.png", ETransformationStyle.e2PTransformation),
		eOval("���׶��", new TOval(), "images/oval.png", ETransformationStyle.e2PTransformation),
		eLine("����", new TLine(), "images/line.png", ETransformationStyle.e2PTransformation),
		ePolygon("������", new TPolygon(), "images/polygon.png", ETransformationStyle.eNPTransformation);
		//
		private String label;
		private TShape tool;
		private String image;
		private ETransformationStyle eTransformationStyle;
		
		private ETools(String label, TShape tool, String image, ETransformationStyle eTransformationStyle) {
			this.tool = tool;
			this.label = label;
			this.image = image;
			this.eTransformationStyle = eTransformationStyle;
		}
		public String getLabel() {
			return this.label;
		}
		public String getImage() {
			return this.image;
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
		eNew("���θ����"),
		eOpen("����"),
		eSave("�����ϱ�"),
		eSaveAs("�ٸ��̸���������"),
		ePrint("����Ʈ"),
		eQuit("����");
		
		private String lable;
		private EFileMenu(String label) {
			this.lable = label;
		}
		public String getLabel() {
			return this.lable;
		}
	}
	
	public enum EEditMenu {
		eUndo("�ڷΰ���"),
		eRedo("�ǵ�����"),
		eCut("�ڸ���"),
		eCopy("�����ϱ�"),
		ePaste("�ٿ��ֱ�"),
		eGroup("�׷�ȭ"),
		eUnGroup("��׷�ȭ");
		
		private String lable;
		private EEditMenu(String label) {
			this.lable = label;
		}
		public String getLabel() {
			return this.lable;
		}
	}

}
