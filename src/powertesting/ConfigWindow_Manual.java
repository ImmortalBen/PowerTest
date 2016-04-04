package powertesting;

import java.io.InputStream;
import java.util.Properties;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class ConfigWindow_Manual extends ApplicationWindow {
	
	private String apkPath;
	private String outputPath;
	
	public void setDefaultPath() {
	    InputStream in = ConfigWindow_Manual.class.getClassLoader().getResourceAsStream("path.cfg.properties");
	    Properties properties = new Properties();
	    try {
			properties.load(in);
			apkPath = properties.getProperty("manual_apk");
			outputPath = properties.getProperty("manual_output");
			System.out.println(apkPath);
			System.out.println(outputPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the application window.
	 */
	public ConfigWindow_Manual() {
		super(null);
		createActions();
		setDefaultPath();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout gridlayout = new GridLayout(3, false);
		gridlayout.marginHeight = 0;
		container.setLayout(gridlayout);
		container.setFocus();
		
		Label image = new Label(container, SWT.CENTER);
		image.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,true,3,1));
		Image imagefile = new Image(null, "icon\\jnu.jpg");
		image.setImage(imagefile);
		
		/* 图片自动缩放
		image.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent arg0) {
				// TODO 自动生成的方法存根
				Point size = image.getSize();
				Point p = image.getLocation();
				arg0.gc.drawImage(imagefile, 0, 0, 705, 88, p.x, p.y, size.x, size.y);
			}
		});*/

		//选择apk文件路径

		Label lbl_apk = new Label(container,SWT.NONE);
		lbl_apk.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		lbl_apk.setLayoutData(new GridData(GridData.CENTER,GridData.CENTER,false,true));
		lbl_apk.setText("选择待测程序apk文件");
		Text txt_apk = new Text(container,SWT.BORDER);
		txt_apk.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,true));
		Button btn_apk = new Button(container,SWT.CENTER);
		btn_apk.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, true));
		btn_apk.setText("浏  览");
		btn_apk.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		
		
		
		//选择输出路径

		Label lbl_output = new Label(container,SWT.NONE);
		lbl_output.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		lbl_output.setLayoutData(new GridData(GridData.CENTER,GridData.CENTER,false,true));
		lbl_output.setText("选择测试结果输出路径");
		Text txt_output = new Text(container,SWT.BORDER);
		txt_output.setLayoutData(new GridData(GridData.FILL,GridData.CENTER,true,true));
		Button btn_output = new Button(container,SWT.CENTER);
		btn_output.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, true));
		btn_output.setText("浏  览");
		btn_output.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		
		
		
		
		Button start = new Button(container,SWT.CENTER);
		start.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		start.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, true, 3, 1));
		
		start.setText("Start");
		
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
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			ConfigWindow_Manual window = new ConfigWindow_Manual();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param shell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("电量测试插件(配置)");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 350);
	}
}
