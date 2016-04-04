package powertesting;

import java.io.InputStream;
import java.util.Properties;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
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
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindow extends ApplicationWindow {
	public static String powerTutorPath;
	
	static {
		InputStream in = ConfigWindow_Manual.class.getClassLoader().getResourceAsStream("path.cfg.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
			String path = System.getProperty("user.dir");
			powerTutorPath = path + properties.getProperty("powerTutor");
			System.out.println("powertutor_path: " + powerTutorPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("�������Բ��");

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
		GridLayout gridlayout = new GridLayout(2, false);
		gridlayout.marginHeight = 0;
		gridlayout.marginWidth = 0;
		container.setLayout(gridlayout);

		Label image = new Label(container, SWT.CENTER);
		image.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		Image imagefile = new Image(null, "icon\\jnu.jpg");
		image.setImage(imagefile);

		// �ָ���
		Label label_1 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_2 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// ��avd manager��ģ��
		Label lbl1 = new Label(container, SWT.CENTER);
		lbl1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lbl1.setText("ʹ��ǰ��򿪰�׿ģ������������\n�����ť��AVD������");
		lbl1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));

		Button btn1 = new Button(container, SWT.CENTER);
		btn1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
		btn1.setText("Open AVD Manager");
		btn1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));

		btn1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String s = "cmd /c android avd";
				try {
					Runtime.getRuntime().exec(s);
					System.out.println("Open AVD Manager Successfully ");
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		// �ָ���
		Label label_3 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_4 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// �ֶ�����ģ��
		Label lbl2 = new Label(container, SWT.NONE | SWT.CENTER);
		lbl2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lbl2.setText("�ֶ�����(�ֶ�����app���в���)");
		lbl2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));

		Button btn2 = new Button(container, SWT.CENTER);
		btn2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		btn2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					new CalculateWindow_Manual();
					getShell().close();
					CalculateWindow_Manual.main(null);

				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("Error");
				}

			}
		});
		btn2.setText("   Start   ");

		// �ָ���
		Label label_5 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_6 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		// �Զ�����ģ��
		Label lbl3 = new Label(container, SWT.NONE);
		lbl3.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		lbl3.setText("�Զ�����(ʹ�ò����������̽��в���)");
		lbl3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));

		Button btn3 = new Button(container, SWT.NONE);
		btn3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		btn3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btn3.setText("   Start   ");

		// �ָ���
		Label label_7 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Label label_8 = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label tips = new Label(container, SWT.CENTER);
		tips.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		tips.setText("(����\"\\bin\\path.cfg.properties\"�����ô�����Ŀ���Ͳ��Խ�����·��)\n(ʹ�ò��Թ����Զ����������ò��Թ���·��)");

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
			MainWindow window = new MainWindow();
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

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(480, 350);
	}
}
