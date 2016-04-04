package outputResult;
import java.awt.*;

import javax.swing.*;

import outputProcessing.Calculator;
import outputProcessing.ConsoleResult;

import java.util.*;
import java.awt.geom.*;
import java.util.Date;

@SuppressWarnings("serial")
public class piechart extends JFrame {
	// 初始数据
	private ArrayList<Double> data=Calculator.PowerListNum;
	//private ArrayList<String> method=Caculator.method;
	//private double data[] = { 100., 150, 113., 127., 299.234, 120., 93., 123.,
	//		127., 199., 157., 189. };
	private String percent[] = new String[Calculator.PowerListNum.size()]; // 每个数据所占百分比
	private int radian[] = new int[Calculator.PowerListNum.size()]; // 每个数据所对应的弧度数
	private double max, min;
	// 初始化每块饼的颜色
	private int dia = 240;// 设置饼图透明度
	private Color c1 = new Color(0, 255, 0, dia);
	private Color c2 = new Color(255, 255, 0, dia);
	private Color c3 = new Color(255, 0, 0, dia);
	private Color c4 = new Color(255, 128, 64, dia);
	private Color c5 = new Color(255, 128, 255, dia);
	private Color c6 = new Color(255, 0, 128, dia);
	private Color c7 = new Color(233, 124, 24, dia);
	private Color c8 = new Color(204, 119, 115, dia);
	private Color c9 = new Color(89, 159, 230, dia);
	private Color c10 = new Color(148, 140, 179, dia);
	private Color c11 = new Color(128, 0, 64, dia);
	private Color c12 = new Color(174, 197, 208, dia);
	private Color colors[] = { c1, c2, c3, c12, c11, c6, c4, c8, c9, c10, c5,
			c7 };
	// 统计图的宽度和高度
	private int width = 1000;
	private int height = 450;
	// 定义标题起始坐标变量
	private int titleStart_x;
	private int titleStart_y;
	// 定义圆心坐标
	private int oval_x = 60;
	private int oval_y = 90;
	// 定义椭圆的长轴和短轴
	private int long_axes = 280;
	private int short_axes = 200;
	// 定义图例区域起点坐标
	private int cutlineRect_x = 70;
	private int cutlineRect_y = 450;
	// 定义图倒区域矩形的宽度和高度
	private int cutlineRect_width = 100;
	private int cutlineRect_heigth = 235;
	// 定义日期变量
	private Date date = new Date();
	// 定义统计图其他显示信息的超始坐标
	private int otherInfo_x1;
	private int otherInfo_y1;
	private int otherInfo_x2;
	private int otherInfo_y2;
	private int otherInfo_x3;
	private int otherInfo_y3;
	// 定义标题\X\Y轴信息
	private String title = "各方法电量统计   (饼形统计图)   ";
	private String otherInfo1 = "比例越大好点越多";
	private String otherInfo2 = "每个方法的电量";
	private String otherInfo3 = "绘图日期：";
	// 双缓冲设置
	private Image offScreenImage = null;
	private Graphics offScreenBuffer = null;
	public piechart() {
		super("电量统计饼图");
		setSize(1000, 450);
		setLocation(100, 177);
		setResizable(true);
		setVisible(true);
		// setBackground(Color.pink);
		init();
	}
	public void init() {
		offScreenImage = this.createImage(width, height);
		offScreenBuffer = offScreenImage.getGraphics();
		NumberBudget();
		CoorBudget();
	}
	public void setData(ArrayList<Double> source){
		data = source;
	}
	public void NumberBudget() {
		// 求数据中的最大值和最小值
		max = data.get(0);
		min = data.get(0);
		for (int mm = 0; mm < data.size(); mm++) {
			if (data.get(mm) > max)
				max = data.get(mm);
			if (data.get(mm) < min)
				min = data.get(mm);
		}
		// 对数据进行求和运算
		float allData_sum = 0;
		for (int s = 0; s < data.size(); s++) {
			allData_sum += data.get(s);
		}
		// 计算每个数据占总数的百分比
		for (int p = 0; p < data.size(); p++) {
			percent[p] = String
					.valueOf(Math.round(data.get(p) / allData_sum * 100))
					+ "%";
		}
		// 计算每个数据所对应的弧度数
		for (int r = 0; r < data.size(); r++) {
			radian[r] = (int) Math.round((double) data.get(r) / allData_sum * 360);
		}
	}
	public void CoorBudget() {
		// 预算标题信息的起始坐标
		titleStart_x = 22;
		titleStart_y = (width / 2) - (title.length() * 15 / 2);
		// 初始化统计图其他显示信息的起始坐标(位置固定)
		otherInfo_x1 = 390;
		otherInfo_y1 = 450;
		otherInfo_x2 = 400;
		otherInfo_y2 = 480;
		otherInfo_x3 = 390;
		otherInfo_y3 = 5;
	}
	public void update(Graphics g) {
		paint(g);
	}
	public void paint(Graphics g) {
		PaintBackground(offScreenBuffer);
		PaintChart(offScreenBuffer);
		g.drawImage(offScreenImage, 0, 0, this);
	}
	public void PaintBackground(Graphics g) {
		// 渐变背景初始颜色
		Color BackStartColor = Color.white;
		Color BackLastColor = new Color(162, 189, 230);
		Color titleColor = Color.black;
		Color otherInfoColor = new Color(41, 78, 118);
		// 标题背景颜色
		Color titleBackColor = new Color(147, 179, 225);
		// 统计图中心区域颜色
		Color cutlineColor = new Color(0, 128, 255, 50);
		// 图例数据颜色
		Color cutDataColor = Color.red;
		Font titleFont = new Font("黑体", Font.BOLD, 18);
		Font otherFont = new Font("宋体", Font.PLAIN, 12);
		String year = "";
		String month = "";
		String day = "";
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints hints = new RenderingHints(null);
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		GradientPaint gradient = new GradientPaint(0, 0, BackStartColor, 0,
				400, BackLastColor, false);
		g2.setPaint(gradient);
		Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height);
		g2.fill(rect);
		// 绘制标题背景
		g2.setColor(titleBackColor);
		g2.fill3DRect(0, 0, width, 30, true);
		// 绘制图例区域背景
		g2.setColor(cutlineColor);
		g2.fillRect(cutlineRect_y, cutlineRect_x, cutlineRect_width,
				cutlineRect_heigth);
		g2.setColor(cutlineColor);//(Color.white);
		g2.drawRect(cutlineRect_y, cutlineRect_x, cutlineRect_width,
				cutlineRect_heigth);
		// 绘制统计图标题
		g2.setFont(titleFont);
		g2.setColor(titleColor);
		g2.drawString(title, titleStart_y, titleStart_x);
		// 显示绘制日期
		year = Integer.toString(1900 + date.getYear());
		month = Integer.toString(date.getMonth() + 1);
		day = Integer.toString(date.getDate());
		g2.drawString(year + "年" + month + "月" + day + "日", otherInfo_y3 + 60,
				otherInfo_x3);
		// 显示数据百分比
		int colorRectWH = 15;
		int space = 5; // 图例中小色块之间的间隔距离
		int addData = cutlineRect_x;
		for (int i = 0; i < data.size(); i++) {
			g2.setColor(colors[i%12]);
			g2.fill3DRect(cutlineRect_y, addData, colorRectWH, colorRectWH,
					true);
			if (data.get(i) == max ||data.get(i) == min)
				g2.setColor(Color.red);
			else
				g2.setColor(cutDataColor);
			g2.drawString(String.valueOf(data.get(i)) +String.valueOf(ConsoleResult.GetMathod(" "+Calculator.MethodList.get(i)))+ "   (" + percent[i] + ")",
					cutlineRect_y + 20, addData + colorRectWH - space);
			addData += colorRectWH + space;
		}
	}
	public void PaintChart(Graphics g) {
		int start = 0;
		int rVal = 192;
		int gVal = 192;
		int bVal = 192;
		int frameCount = 60;
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints hints = new RenderingHints(null);
		hints.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		for (int t = 20; t >= 0; t--) {
			rVal = t * 255 / frameCount;
			gVal = t * 255 / frameCount;
			bVal = t * 255 / frameCount;
			g2.setColor(new Color(rVal, gVal, bVal, 50));
			g2.drawOval(oval_x, oval_y + t, long_axes, short_axes);
		}
		for (int a = 0; a < data.size(); a++) {
			Arc2D arc = new Arc2D.Float(Arc2D.PIE);
			g2.setColor(colors[a%12]);
			arc.setFrame(oval_x, oval_y, long_axes, short_axes);
			arc.setAngleStart(start);
			arc.setAngleExtent(radian[a]);
			g2.fill(arc);
			if (data.get(a) == max || data.get(a) == min)
				g2.setColor(Color.white);
			else
				g2.setColor(new Color(223, 223, 223, 150));
			g2.draw(arc);
			start += radian[a];
		}
	}
	public static void main(String[] arsg) {
		try {
			piechart cake = new piechart();
		} catch (Exception exe) {
		}
	}
}