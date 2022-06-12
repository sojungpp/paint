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
		//variable �̸���
		eSelection("����", new TSelection(), "images/line.png", ETransformationStyle.e2PTransformation, "������ �����մϴ�."),
		eRectangle("�׸�", new TRectangle(), "images/rectangle.png", ETransformationStyle.e2PTransformation, "�׸� �׸��ϴ�."),
		eOval("���׶��", new TOval(), "images/oval.png", ETransformationStyle.e2PTransformation, "���׶�̸� �׸��ϴ�."),
		eLine("����", new TLine(), "images/line.png", ETransformationStyle.e2PTransformation, "������ �׸��ϴ�."),
		ePolygon("������", new TPolygon(), "images/polygon.png", ETransformationStyle.eNPTransformation, "�ٰ����� �׸��ϴ�."),
		eTriangle("�ﰢ��", new TTriangle(), "images/triangle.png", ETransformationStyle.e2PTransformation, "���� �׸��ϴ�."),
		eArc("�ݿ�", new TArc(), "images/arc.png", ETransformationStyle.e2PTransformation, "�ݿ��� �׸��ϴ�."),
		eText("�۾�", new TFreeLine(), "images/line.png", ETransformationStyle.e2PTransformation, "�ݿ��� �׸��ϴ�.");
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
		eNew("���θ����", 'N'),
		eOpen("����", 'O'),
		eSave("�����ϱ�", 'S'),
		eSaveAs("�ٸ��̸���������", 'A'),
		ePrint("����Ʈ", 'P'),
		eQuit("����", 'Q');
		
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
		eUndo("�ڷΰ���", 'Z'),
		eRedo("�ǵ�����", 'Y'),
		eCut("�ڸ���", 'C'),
		eCopy("�����ϱ�", 'U'),
		ePaste("�ٿ��ֱ�", 'T'),
		eGroup("�׷�ȭ", 'G'),
		eUnGroup("��׷�ȭ", 'B');
		
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
		eLineColor("������", 'L'),
		eFillColor("ä������", 'F');
		
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
