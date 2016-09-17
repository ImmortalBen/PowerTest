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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
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
import org.swtchart.IAxis;
import org.swtchart.IBarSeries;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.Range;
import org.swtchart.ext.InteractiveChart;

import outputResult.HistoryPie;
import outputResult.piechart;
import outputProcessing.Calculator;

public class ChartWindow extends ApplicationWindow {
	public String filePath = CalculateWindow_Manual.outputPath;
	//public String filePath = "C:\\Users\\benyin\\Documents\\Graduation_Paper\\Data\\My12306-05-17";
	private static Chart[] charts;
//	private static long lastTime = System.currentTimeMillis();
//	private static int dataLength;
//	private static double[] test;
	private ArrayList hotSpot = new ArrayList();
	private static int recordTime = 60;
	private double maxY = 0;
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
		
		Button btnToChart = new Button(container, SWT.CENTER);
		btnToChart.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnToChart.setText("与历史纪录进行对比");
		btnToChart.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {

				System.out.println("pieSize"+String.valueOf(Calculator.MethodList.size()));
				System.out.println("pieSize"+String.valueOf(Calculator.HistoryMethod.size()));
				if (Calculator.PowerListNum.size() != 0){
					piechart piechart = new piechart();
				}
				if (Calculator.HistoryPowerNum.size() != 0){
					HistoryPie historyPiechart = new HistoryPie();
				}	
				
			}
		});
		//未能做到动态刷新，总提示 widget is disposed

		

		
		
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
		/*未能做到动态刷新，总提示 widget is disposed
				int index = 1;		
				System.out.println("NI LAO MU:"+index);
				while(true){
					System.out.println("NI LAO MU:"+index);
					long curTime = System.currentTimeMillis();
					dataLength = test.length;
					if(curTime - lastTime > 1000){
						lastTime = curTime;
						
						if (index >= dataLength){
							break;
						}
						charts[1] = updateComponentChart(charts[1], "CPU", test, index);
						charts[1].getAxisSet().adjustRange();
						index++;
					}
				}
		*/
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
		String componentName = new String();
		try {
			fis = new FileInputStream(filePath);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			System.out.println("read from: "+filePath);
			String line = new String();
			Double lastData= (double) -1;
			int index = 0;
			while ((line = br.readLine()) != null) {
				int begin=line.indexOf("] ");
				if (begin != -1){
					String oneSecData = line.substring(begin+2,line.length());
					Double curData= Double.parseDouble(oneSecData);
				    Data.add(curData);
				    if (lastData == -1){
				    	lastData = curData;
				    }
				    if (curData / lastData >=3 || curData > 200){
				    	hotSpot.add(index);
				    }
				    index++;
				    lastData = curData;
				}
				else if (componentName != null){
					componentName = line;
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
		double[] chartData = new double[recordTime];
		for(int i=0;i<recordTime;i++){
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
			double maxComData = 0;
			for (int j=0; j<componentData.length; j++){
				maxY = Math.max(maxY, componentData[j]);
				maxComData = Math.max(maxComData, componentData[j]);
			}
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
		        IAxis Y = charts[0].getAxisSet().getYAxis(0);
		        Y.setRange(new Range(0, maxY+0.2*maxY));

			}
			// adjust the axis range
			chart.getAxisSet().adjustRange();
			IAxis Y = chart.getAxisSet().getYAxis(0);
	        Y.setRange(new Range(0, maxComData+0.2*maxComData));
			charts[i] = chart;
		}
		return charts;
	}
	
}
