package powertesting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import outputProcessing.Calculator;
import outputProcessing.MethodReader;

public class CalculateWindow_Manual extends ApplicationWindow {

	public static String projectName;
	public static String outputPath;
	public static String packageName;
	private String mainActivityName;
	private String workspacePath;

	public void setDefaultPath() {
		InputStream in = CalculateWindow_Manual.class.getClassLoader().getResourceAsStream("path.cfg.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			projectName = properties.getProperty("project_name");
			outputPath = properties.getProperty("manual_output");
			workspacePath = properties.getProperty("workspace_path");
			
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("MM-dd");  
		    String logDate = sDateFormat.format(new   java.util.Date());
		    outputPath += "\\" + projectName + "-" + logDate;
			System.out.println("projectName: " + projectName);
			System.out.println("outputPath: " + outputPath);
			System.out.println("workspacePath: " + workspacePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���������ڰ�װPowerTutor���򣬴������װ������Ŀ
	private void install() {

		// ��װPowerTutor
		String drive = MainWindow.powerTutorPath.substring(0, 2); // ��ȡ��װ��·���̷�
		String c = "cmd /c " + drive + " && cd " + MainWindow.powerTutorPath + " && adb install PowerTutor.apk";
		System.out.println(c);
		try {
			Process p = Runtime.getRuntime().exec(c);
			InputStream input = p.getInputStream();
			if (input.read() != -1) {
				System.out.println("install powertutor OK");
			} else
				System.out.println("install powertutor Error");
			input.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// ����build.xml�ļ�
		drive = workspacePath.substring(0, 2);
		c = "cmd /c " + drive + "&& cd " + workspacePath + "&& android update project -n " + projectName + " -p "
				+ projectName;
		// String c = "cmd /c " + drive + " && cd " + MainWindow.powerTutorPath
		// + " && adb install PowerTutor.apk";
		System.out.println(c);
		try {
			Process p = Runtime.getRuntime().exec(c);
			InputStream input = p.getInputStream();
			if (input.read() != -1) {
				System.out.println("update project OK");
			} else
				System.out.println("update project Error");
			input.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// ����project
		drive = workspacePath.substring(0, 2);
		c = "cmd /c " + drive + "&& cd " + workspacePath + "\\" + projectName + " && ant clean";
		System.out.println(c);
		try {
			Process p = Runtime.getRuntime().exec(c);
			InputStream input = p.getInputStream();
			if (input.read() != -1) {
				System.out.println("clean project OK");
			} else
				System.out.println("clean project Error");
			input.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// �������apk�ļ�
		drive = workspacePath.substring(0, 2);
		c = "cmd /c " + drive + "&& cd " + workspacePath + "\\" + projectName + " && ant debug";
		System.out.println(c);
		try {
			Process p = Runtime.getRuntime().exec(c);
			InputStream input = p.getInputStream();
			if (input.read() != -1) {
				System.out.println("create apk file OK");
			} else
				System.out.println("create apk file Error");
			input.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// ͨ�����ɵ�apk�ļ���װ������Ŀ
		drive = workspacePath.substring(0, 2);
		c = "cmd /c " + drive + "&& cd " + workspacePath + "\\" + projectName + "\\bin && adb install " + projectName
				+ "-debug.apk";
		System.out.println(c);
		try {
			Process p = Runtime.getRuntime().exec(c);
			InputStream input = p.getInputStream();
			if (input.read() != -1) {
				System.out.println("install apk OK");
			} else
				System.out.println("install apk Error");
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void config() {
		// ɾ��dmtrace.trace�ļ��Լ�PowerTutor.txt�ļ�
		String c = "cmd /c adb shell && " + "cd sdcard && " + "rm dmtrace.trace && " + "rm PowerTutor.txt";
		try {
			Process p = Runtime.getRuntime().exec(c);
			InputStream input = p.getInputStream();
			if (input.read() != -1) {
				System.out.println("remove history files OK");
			} else
				System.out.println("remove history files Error");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("remove history files Error");
		}
		// ��ȡ���ⰲװ��Ŀ�İ��������Activity��
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		String xmlFilePath = workspacePath + "\\" + projectName + "\\AndroidManifest.xml";
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(xmlFilePath));
		} catch (Exception e) {
			System.out.println("Error");
		}
		Element element = document.getDocumentElement();
		packageName = element.getAttribute("package");
		System.out.println("packageName: " + packageName);

		NodeList activities = element.getElementsByTagName("activity");
		for (int index = 0; index < activities.getLength(); index++) {
			Element activity = (Element) activities.item(index);
			NodeList nl = activity.getElementsByTagName("intent-filter");
			if (nl.getLength() != 0) {
				NodeList action = ((Element) nl.item(0)).getElementsByTagName("action");
				NodeList category = ((Element) nl.item(0)).getElementsByTagName("category");
				if ((action.getLength() != 0) && (category.getLength() != 0)) {
					String actionName = ((Element) action.item(0)).getAttribute("android:name");
					String categoryName = ((Element) category.item(0)).getAttribute("android:name");
					if (actionName.equals("android.intent.action.MAIN")
							&& categoryName.equals("android.intent.category.LAUNCHER")) {
						mainActivityName = activity.getAttribute("android:name");
						break;
					} else
						continue;
				} else
					continue;
			} else
				continue;
		}
		System.out.println("mainActivity: " + mainActivityName);
	}

	/**
	 * Create the application window.
	 */
	public CalculateWindow_Manual() {
		super(null);
		setDefaultPath();
		install();
		config();
		createActions();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setFocus();
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		container.setLayout(gridLayout);

		Label image = new Label(container, SWT.CENTER);
		image.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		Image imagefile = new Image(null, "icon\\jnu.jpg");
		image.setImage(imagefile);

		Label tips = new Label(container, SWT.CENTER);
		tips.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		tips.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tips.setText("���ڴ��������Ժ򡭡�");

		tips.dispose();

		// �ָ���
		Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_2 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// ��ʼ����(��PowerTutor�ʹ������)
		Label start_lbl = new Label(container, SWT.CENTER);
		start_lbl.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		start_lbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		start_lbl.setText("�����ť��ʼ����,\n�ȴ��������򿪺��뿪ʼ����");
		Button start_btn = new Button(container, SWT.CENTER);
		start_btn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		start_btn.setText("   start(��ûŪ��,Ī�㣡)   ");
		start_btn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				// ��PowerTutor
				try {
					String c = "cmd /c adb shell am start -n "
							+ "edu.umich.PowerTutor/edu.umich.PowerTutor.ui.UMLogger";
					Process p = Runtime.getRuntime().exec(c);
					InputStream input = p.getInputStream();
					if (input.read() != -1) {
						System.out.println("Open PowerTutor OK");
					} else
						System.out.println("Open PowerTutor Error");
				} catch (Exception e3) {
					System.out.println("Open PowerTutor Error");
				}
				// �򿪴���Android����
				try {
					String c = "cmd /c adb shell am start -n " + packageName + "/" + packageName + mainActivityName;
					Process p = Runtime.getRuntime().exec(c);
					InputStream input = p.getInputStream();
					if (input.read() != -1) {
						System.out.println("Open project OK");
					} else
						System.out.println("Open project Error");
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("Open project Error");
				}
			}
		});

		// �ָ���
		Label label_3 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_4 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// ֹͣ����
		Label stop_lbl = new Label(container, SWT.CENTER);
		stop_lbl.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		stop_lbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		stop_lbl.setText("�����ťֹͣ����");
		Button stop_btn = new Button(container, SWT.CENTER);
		stop_btn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		stop_btn.setText("   stop(��ûŪ��,Ī�㣡)   ");
		stop_btn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				// ����dmtrace.trace�ļ���ָ�����·��
				String c = "cmd.exe /c adb pull /sdcard/dmtrace.trace " + outputPath;
				try {
					Runtime.getRuntime().exec(c);
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("copy dmtrace.trace Error");
				}

				// ��dmtrace.trace�ļ�ת��Ϊhtml�ļ�
				c = "cmd /c dmtracedump -h " + outputPath + "\\dmtrace.trace > " + outputPath + "\\output.html";
				try {
					Runtime.getRuntime().exec(c);
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("transfer dmtrace.trace Error");
				}

				// ����powerTutor.txt�ļ���ָ�������·��
				c = "cmd.exe /c adb pull /sdcard/PowerTutor.txt " + outputPath;
				try {
					Runtime.getRuntime().exec(c);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					System.out.println("copy powertutor.txt Error");
				}

				// ж�ش������
				c = "cmd /c adb uninstall " + packageName;
				try {
					Process p = Runtime.getRuntime().exec(c);
					InputStream input = p.getInputStream();
					if (input.read() != -1) {
						System.out.println("uninstall project OK");
					} else
						System.out.println("uninstall project Error");
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("uninstall project Error");
				}

				// ����������

				// ͨ��output.html�ļ�����method.txt�ļ�
				MethodReader methodreader = new MethodReader();
				try {
					methodreader.getMethodTextFromHtml(outputPath + "\\output.html");
				} catch (IOException e1) {

					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Calculator cal = new Calculator();
				try {
					cal.getPowerTextFromMethodText(outputPath + "\\method.txt", outputPath + "\\PowerTutor.txt",
							outputPath + "\\powerInfo.txt");
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}

				/*
				 * for (int i = 0; i < CalculatorInc.Inclocalmethod.size(); i++)
				 * { try { new
				 * ConsoleResult(CalculatorInc.Inclocalmethod.get(i),
				 * CalculatorInc.Inclocalpower.get(i)); } catch (IOException e1)
				 * { // TODO �Զ����ɵ� catch �� e1.printStackTrace(); }
				 * 
				 * }
				 */
				System.out.println("OK");
			}
		});

		// �ָ���
		Label label_5 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_6 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// ����������
		Label analyze_lbl = new Label(container, SWT.CENTER);
		analyze_lbl.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		analyze_lbl.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		analyze_lbl.setText("�����ť����������");
		Button analyze_btn = new Button(container, SWT.CENTER);
		analyze_btn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		analyze_btn.setText("   analyze(��ûŪ��,Ī�㣡)   ");
		analyze_btn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new ChartWindow();
				getShell().close();
				ChartWindow.main(null);
			}
		});

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
			CalculateWindow_Manual window = new CalculateWindow_Manual();
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
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("�������Բ��(����)");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(480, 300);
	}

}