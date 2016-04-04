package powertesting;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ext.InteractiveChart;


public class ChartWindow extends ApplicationWindow {
	//public String filePath = CalculateWindow_Manual.outputPath;
	public String filePath = "C:\\Users\\benyin\\Documents\\Graduation_Paper\\Data\\3.30";
	private static Chart[] charts;
	private static long lastTime = System.currentTimeMillis();
	private static int dataLength;
	private static double[] test;
	/**
	 * Create the application window,
	 */
	public ChartWindow() {
		super(null);
		createActions();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gridlayout = new GridLayout(2, false);
		gridlayout.marginHeight = 0;
		gridlayout.marginWidth = 0;
		container.setLayout(gridlayout);
		
		Label image = new Label(container, SWT.CENTER);
		image.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		Image imagefile = new Image(null, "icon\\jnu.jpg");
		image.setImage(imagefile);
		
		CTabFolder body = new CTabFolder(container, SWT.NONE);
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		String[] componentNames = {"total", "CPU", "LCD"};
		charts = new Chart[componentNames.length];
		try {
			charts = setComponentCharts(body, componentNames);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i=0;i<componentNames.length;i++){			
				String componentName = componentNames[i];
				CTabItem tbtmComponent = new CTabItem(body, SWT.NONE);			
				tbtmComponent.setText(componentName);
				tbtmComponent.setControl(charts[i]);
				tbtmComponent.setText(componentName);
		}
		
		

		
		
		//chart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}
	
	
	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			ChartWindow window = new ChartWindow();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//未能做到动态刷新，总提示 widget disposed
//		int index = 1;		
//		while(true){
//			long curTime = System.currentTimeMillis();
//			
//			if(curTime - lastTime > 1000){
//				lastTime = curTime;
//				if (index >= dataLength){
//					break;
//				}
//				charts[1] = updateComponentChart(charts[1], "CPU", test, index);
//				charts[1].getAxisSet().adjustRange();
//				index++;
//			}
//		}
		
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("电量测试插件");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 500);
	}

	//read component data from outputPath (path/CPU.txt)
	public double[] readComponentData(String filePath) throws IOException{
		ArrayList<Double> Data = new ArrayList<Double>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			System.out.println("read from: "+filePath);
			String line = new String();
			while ((line = br.readLine()) != null) {
				int begin=line.indexOf("] ");
				if (begin != -1){
					String oneSecData = line.substring(begin+2,line.length());
				    Data.add(Double.parseDouble(oneSecData));
				}		    
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到指定文件");
			e.printStackTrace();
		}
		br.close();
		Double[] tmpData = new Double[Data.size()];
		Data.toArray(tmpData);
		double[] chartData = new double[Data.size()];
		for(int i=0;i<Data.size();i++){
			chartData[i] = tmpData[i];
		}
		return chartData;
	}
	
	//创建各个部件对应的视图
	protected Chart[] setComponentCharts(CTabFolder body, String[] componentNames) throws IOException{
		Chart[] charts = new Chart[componentNames.length];
		int[] colors = {SWT.COLOR_BLUE, SWT.COLOR_BLACK, SWT.COLOR_RED, SWT.COLOR_GREEN, SWT.COLOR_YELLOW};
		for (int i=0; i<componentNames.length; i++){
			String componentName = componentNames[i];
			String componentPath = filePath + "\\"+componentName+".txt";
			//filePath = "C:\\Users\\benyin\\Documents\\Graduation_Paper\\Data\\cpu.txt";
			double[] componentData = readComponentData(componentPath);
			// create a chart
			Chart chart = new Chart(body, SWT.NONE);

			// set titles: ComponentName
			chart.getTitle().setText(componentName+"(mW)");
			chart.getAxisSet().getXAxis(0).getTitle().setText("Time(s)");
			chart.getAxisSet().getYAxis(0).getTitle().setText("Power");
			//chart.getAxisSet().getYAxis(0).getTitle().setText("Amplitude");

			// create line series
			ILineSeries lineSeries = (ILineSeries) chart.getSeriesSet()
			    .createSeries(SeriesType.LINE, componentName);
			lineSeries.setYSeries(componentData);
			lineSeries.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
			if(i != 0){		
				// create componentName line series in TotalChart(在Total中显示所有部件耗电量)
		        ILineSeries lineSeries2 = (ILineSeries) charts[0].getSeriesSet()
		                .createSeries(SeriesType.LINE, componentNames[i]);
		        lineSeries2.setYSeries(componentData);
		        lineSeries2.setLineColor(Display.getDefault().getSystemColor(colors[i]));
		        charts[0].getAxisSet().adjustRange();

			}
			// adjust the axis range
			chart.getAxisSet().adjustRange();
			charts[i] = chart;
		}
		return charts;
	}
	
	private static Chart updateComponentChart(Chart chart, String componentName, double[] data, int index){
		double[] updateData = new double[60];
		for (int i=0; i<60; i++){
			updateData[i] = data[i+index];
		}
		Chart updateChart = chart;
		// create line series
		ILineSeries lineSeries = (ILineSeries) updateChart.getSeriesSet()
		    .getSeries(componentName);
		lineSeries.setYSeries(updateData);
		updateChart.getAxisSet().adjustRange();
		return updateChart;
	}
}
