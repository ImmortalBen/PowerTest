package powertesting;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
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
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import outputProcessing.Calculator;

import org.eclipse.swt.widgets.Text;

public class ResultWindow extends ApplicationWindow {
	private Text text;
	private int PID;
	private int[] totalUserCPU = new int[10];
	private int[] totalSystemCPU = new int[10];
	private int[] AUACPU = new int[10];
	private int[] VSS = new int[10];
	private int[] RSS = new int[10];
	private int[] GlobFreeMem = new int[10];
	private int[] GlobMapMem = new int[10];
	private int[] GlobAnonMem = new int[10];
	private int[] GlobSlabMem = new int[10];
	private int[] StorageRead = new int[10];
	private int[] StorageWrtn = new int[10];
	/**
	 * Create the application window,
	 */
	public ResultWindow() {
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
		
		CTabFolder body = new CTabFolder(parent, SWT.NONE);
		//body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		CTabItem tbPower = new CTabItem(body, SWT.NONE);
		tbPower.setText("Power Measured Data");		
		
		CTabItem tbPerformance = new CTabItem(body, SWT.NONE);
		tbPerformance.setText("Performance Measured Data");
		
		//The code below is content in 
		//Power Measured Data Tab
		
		Composite powerContainer = new Composite(body, SWT.NONE);		
		tbPower.setControl(powerContainer);
		GridLayout gridlayout = new GridLayout(1, false);
		gridlayout.marginHeight = 0;
		gridlayout.marginWidth = 0;
		powerContainer.setLayout(gridlayout);
		
		Label image = new Label(powerContainer, SWT.CENTER);
		image.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		Image imagefile = new Image(null, "icon\\jnu.jpg");
		image.setImage(imagefile);
		
		CTabFolder power = new CTabFolder(powerContainer, SWT.NONE);
		power.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
		CTabItem tbtmCpuPower = new CTabItem(power, SWT.NONE);
		tbtmCpuPower.setText("CPU Power");
		
		text = new Text(power, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL |SWT.READ_ONLY);
		tbtmCpuPower.setControl(text);
		
		for(int i=0; i<Calculator.MethodPowerData.size(); i++){
		//for(int i=0; i<10; i++){
			  	text.append(Calculator.MethodPowerData.get(i));
				//text.append(String.valueOf(i));
			  text.append("\n");
		}
		
		CTabItem tbtmLcdPower = new CTabItem(power, SWT.NONE);
		tbtmLcdPower.setText("LCD Power");
		
		CTabItem tbtmWifiPower = new CTabItem(power, SWT.NONE);
		tbtmWifiPower.setText("WIFI Power");
		
		CTabItem tbtmgPower = new CTabItem(power, SWT.NONE);
		tbtmgPower.setText("3G Power");
		
		//The code above is content in 
		//Power Measured Tab
		
		//The code below is content in 
		//Performance Measured Tab
		
		Composite perforContainer = new Composite(body, SWT.NONE);		
		tbPerformance.setControl(perforContainer);
		GridLayout perforGridlayout = new GridLayout(1, false);
		perforGridlayout.marginHeight = 0;
		perforGridlayout.marginWidth = 0;
		perforContainer.setLayout(perforGridlayout);
		
		Label image2 = new Label(perforContainer, SWT.CENTER);
		image2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		Image imagefile2 = new Image(null, "icon\\jnu.jpg");
		image2.setImage(imagefile2);
		
		CTabFolder performance = new CTabFolder(perforContainer, SWT.NONE);
		performance.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
		CTabItem tbtmCpuPerformance = new CTabItem(performance, SWT.NONE);
		tbtmCpuPerformance.setText("CPU Performance");
		
		Text CPUPerformanceText = new Text(performance, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL |SWT.READ_ONLY);
		tbtmCpuPerformance.setControl(CPUPerformanceText);
		String outputPath = new String();
		outputPath = CalculateWindow_Manual.outputPath == null 
				? "C:\\Users\\benyin\\Documents\\Graduation_Paper\\Data" : CalculateWindow_Manual.outputPath;
		try {
			
			getCPUInfo(outputPath+"\\top.txt");
			getVMStatInfor(outputPath+"\\vmstat.txt");
			getIOstatInfo(outputPath+"\\iostat.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		int AveUserCPU=0;
		int MaxUserCPU=0;
		int AveSystemCPU=0;
		int MaxSystemCPU=0;
		int AveAUACPU=0;
		int MaxAUACPU=0;
		for(int i=0; i<10; i++){
			AveUserCPU += totalUserCPU[i];
			AveSystemCPU += totalSystemCPU[i];
			AveAUACPU += AUACPU[i];
			MaxUserCPU = Math.max(totalUserCPU[i], MaxUserCPU);
			MaxSystemCPU = Math.max(totalSystemCPU[i], MaxSystemCPU);
			MaxAUACPU = Math.max(AUACPU[i], MaxAUACPU);
		}
		AveUserCPU /= 10;
		AveSystemCPU /= 10;
		AveAUACPU /= 10;
		CPUPerformanceText.append("Average User CPU:			"+String.valueOf(AveUserCPU)+"%\n");
		CPUPerformanceText.append("Average System CPU:		"+String.valueOf(AveSystemCPU)+"%\n");
		CPUPerformanceText.append("Average AUA CPU:			"+String.valueOf(AveAUACPU)+"%\n\n");
		
		CPUPerformanceText.append("Max User CPU:				"+String.valueOf(MaxUserCPU)+"%\n");
		CPUPerformanceText.append("Max System CPU:			"+String.valueOf(MaxSystemCPU)+"%\n");
		CPUPerformanceText.append("Max AUA CPU:				"+String.valueOf(MaxAUACPU)+"%\n\n");
		
		int startTime = 0;
		int endTime = 5;		
		for(int i=0; i<10; i++){
			CPUPerformanceText.append(String.valueOf(startTime)+"s--"+String.valueOf(endTime)+"s:\n");
			CPUPerformanceText.append("User CPU:					"+String.valueOf(totalUserCPU[i])+"%\n");
			CPUPerformanceText.append("System CPU:				"+String.valueOf(totalSystemCPU[i])+"%\n");
			CPUPerformanceText.append("AUA CPU:					"+String.valueOf(AUACPU[i])+"%\n\n");
			startTime += 6;
			endTime += 6;
		}
		
		CTabItem tbtmMemoryPerformance = new CTabItem(performance, SWT.NONE);
		tbtmMemoryPerformance.setText("Memory Performance");
		Text MemPerformanceText = new Text(performance, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL |SWT.READ_ONLY);
		tbtmMemoryPerformance.setControl(MemPerformanceText);
		int maxVSS = 0;
		int maxRSS = 0;
		int AveFreeMem=0;
		int AveMapMem=0;
		int AveAnonMem=0;
		int AveSlabMem=0;
		for(int i=0; i<10; i++){
			maxVSS = Math.max(maxVSS, VSS[i]);		
			maxRSS = Math.max(maxRSS, RSS[i]);	
			AveFreeMem += GlobFreeMem[i];
			AveMapMem += GlobMapMem[i];
			AveAnonMem += GlobAnonMem[i];
			AveSlabMem += GlobSlabMem[i];
		}
		maxVSS /= 1000;
		maxRSS /= 1000;
		AveFreeMem /= 10000;
		AveMapMem /= 10000;
		AveAnonMem /= 10000;
		AveSlabMem /= 10000;
		MemPerformanceText.append("Average Global Free Memory:			"+String.valueOf(AveFreeMem)+"MB\n");
		MemPerformanceText.append("Average Global Mapped Memory:		"+String.valueOf(AveMapMem)+"MB\n");
		MemPerformanceText.append("Average Global Anonymous Memory:	"+String.valueOf(AveAnonMem)+"MB\n");
		MemPerformanceText.append("Average Global Slab Memory:			"+String.valueOf(AveSlabMem)+"MB\n\n");
		
		MemPerformanceText.append("Max AUA VSS:				"+String.valueOf(maxVSS)+"MB\n");
		MemPerformanceText.append("Max AUA RSS:				"+String.valueOf(maxRSS)+"MB\n\n");
		
		startTime = 0;
		endTime = 5;		
		for(int i=0; i<10; i++){
			MemPerformanceText.append(String.valueOf(startTime)+"s--"+String.valueOf(endTime)+"s:\n");
			MemPerformanceText.append("Global Free Memory:		"+String.valueOf(GlobFreeMem[i]/1000)+"MB\n");
			MemPerformanceText.append("Global Mapped Memory:		"+String.valueOf(GlobMapMem[i]/1000)+"MB\n");
			MemPerformanceText.append("Global Anonymous Memory:	"+String.valueOf(GlobAnonMem[i]/1000)+"MB\n");
			MemPerformanceText.append("Global Slab Memory:		"+String.valueOf(GlobSlabMem[i]/1000)+"MB\n");
			MemPerformanceText.append("AUA VSS:					"+String.valueOf(VSS[i]/1000)+"MB\n");
			MemPerformanceText.append("AUA RSS:					"+String.valueOf(RSS[i]/1000)+"MB\n\n");
			
			startTime += 6;
			endTime += 6;
		}
		
		CTabItem tbtmStoragePerformance = new CTabItem(performance, SWT.NONE);
		tbtmStoragePerformance.setText("Storage Performance");
		Text StoragePerformanceText = new Text(performance, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL |SWT.READ_ONLY);
		tbtmStoragePerformance.setControl(StoragePerformanceText);
		
		startTime = 0;
		endTime = 5;		
		for(int i=0; i<10; i++){
			StoragePerformanceText.append(String.valueOf(startTime)+"s--"+String.valueOf(endTime)+"s:\n");
			StoragePerformanceText.append("Storage Read:				");
			if(StorageRead[i]>1000) {				
				StoragePerformanceText.append(String.valueOf(StorageRead[i]/1000)+"MB\n");
			}
			else{
				StoragePerformanceText.append(String.valueOf(StorageRead[i])+"KB\n");
			}
			StoragePerformanceText.append("Storage Written:			");
			if(StorageRead[i]>1000) {				
				StoragePerformanceText.append(String.valueOf(StorageWrtn[i]/1000)+"MB\n\n");
			}
			else{
				StoragePerformanceText.append(String.valueOf(StorageWrtn[i])+"KB\n\n");
			}
			
			startTime += 6;
			endTime += 6;
		}

		
		//The code above is content in 
		//Performance Measured Data
		
		Button btnToChart = new Button(powerContainer, SWT.CENTER);
		btnToChart.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnToChart.setText("查看各组件耗电量曲线图");
		btnToChart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new ChartWindow();
				getShell().close();
				ChartWindow.main(null);
			}
		});
				
		return parent;
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
			ResultWindow window = new ResultWindow();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("电量消耗及性能指标测试结果");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 500);
	}

		public void getCPUInfo(String path) throws IOException{
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			System.out.println("read from: "+path);
			String line = new String();
			int countAUA = 0;
			int countCPU = 0;
			while ((line = br.readLine()) != null) {
				Boolean re = line.matches("User [0-9]+%, System [0-9]+%, IOW [0-9]+%, IRQ [0-9]+%");
				if (re){
					int begin = 0;
					int end = line.indexOf("%");
					totalUserCPU[countCPU] = Integer.parseInt(line.substring(5, end));

					begin = end+10;
					end = line.indexOf("%", end+1);
					totalSystemCPU[countCPU] = Integer.parseInt(line.substring(begin, end));
					countCPU++;
				}
				String packageName = CalculateWindow_Manual.packageName != null ? CalculateWindow_Manual.packageName : "com.example.l2048";
				if (line.indexOf(packageName) != -1){
					line = line.substring(1);
					String tmpStr[] = line.split(" ");
					PID = Integer.parseInt(tmpStr[2]);
					int ss =0;
					for (int i=1; i<tmpStr.length; i++){						
						if(tmpStr[i].indexOf("%") != -1){
							String AUAStr = tmpStr[i].trim().substring(0,tmpStr[i].indexOf("%"));		
							AUACPU[countAUA]= Integer.parseInt(AUAStr);
						}
						else if(tmpStr[i].indexOf("K") != -1 && ss == 0){
							VSS[countAUA] = Integer.parseInt(tmpStr[i].substring(0,tmpStr[i].indexOf("K")-1));
							ss = 1;
						}
						else if (tmpStr[i].indexOf("K") != -1 && ss == 1){
							RSS[countAUA] = Integer.parseInt(tmpStr[i].substring(0,tmpStr[i].indexOf("K")-1));
							ss = 0;
						}						
					}					
					countAUA++;
				}
			}
				
			System.out.println("Total User CPU: "+totalUserCPU[5]);
			System.out.println("Total System CPU: "+totalSystemCPU[5]);
			System.out.println("AUACPU: "+AUACPU[5]);
			System.out.println("VSS: "+VSS[5]);
			System.out.println("RSS: "+RSS[5]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到指定文件");
			e.printStackTrace();
		}
	}
	
	public void getVMStatInfor(String path) throws IOException{
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			System.out.println("read from: "+path);
			String line = new String();
			while ((line = br.readLine()) != null) {
				Boolean re = line.matches(" r  b    free mapped   anon   slab    in   cs  flt  us ni sy id wa ir");
				if (re){
					line = br.readLine();	
					System.out.println(line);
					for (int i=0; i<10; i++){
						line = br.readLine();
						String[] memArray = line.split(" ");
						int count = 0;
						for (int j=0; j<memArray.length; j++){
							if(memArray[j].matches("[0-9]+")){
								if (count == 2){
									GlobFreeMem[i] = Integer.parseInt(memArray[j]);
								}
								else if (count == 3){
									GlobMapMem[i] = Integer.parseInt(memArray[j]);
								}
								else if (count == 4){
									GlobAnonMem[i] = Integer.parseInt(memArray[j]);
								}
								else if (count == 5){
									GlobSlabMem[i] = Integer.parseInt(memArray[j]);
								}
								count++;
							}
						}
						line = br.readLine();
					}
					System.out.println("GLOBAL FREE MEM: "+GlobFreeMem[9]);
					System.out.println("GLOBAL MAPPED MEM: "+GlobMapMem[9]);
					System.out.println("GLOBAL ANON MEM: "+GlobAnonMem[9]);
					System.out.println("GLOBAL SLAB MEM: "+GlobSlabMem[9]);
					break;
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到指定文件");
			e.printStackTrace();
		}
	}
	
	public void getIOstatInfo(String path) throws IOException{
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			System.out.println("read from: "+path);
			String line = new String();
			for (int i = 0; i<10; i++){
				while (line.indexOf("Device") == -1){
					line = br.readLine();
				}
				line = br.readLine();
				line = br.readLine();
				while (line.indexOf("Device") == -1 && line != null){						
					if (line.indexOf("Device") != -1 || line == null){
						break;
					}
					String strgRead = line.substring(49,59);
					String strgWrtn = line.substring(60,70);					
					StorageRead[i] += Integer.parseInt(strgRead.trim());
					StorageWrtn[i] += Integer.parseInt(strgWrtn.trim());
					if(line.indexOf("mmcblk") != -1){
						break;
					}
					line = br.readLine();
					line = br.readLine();
				}
				line = br.readLine();
				line = br.readLine();
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到指定文件");
			e.printStackTrace();
		}
	}
}
