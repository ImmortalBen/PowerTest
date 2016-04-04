package powertesting;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Table;

import outputProcessing.Calculator;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class ResultWindow extends ApplicationWindow {
	private Table table;
	private Text text;

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
		
		
		CTabItem tbtmCpuPower = new CTabItem(body, SWT.NONE);
		tbtmCpuPower.setText("CPU Power");
		
		text = new Text(body, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL |SWT.READ_ONLY);
		tbtmCpuPower.setControl(text);
		
		for(int i=0; i<Calculator.MethodPowerData.size(); i++){
			  text.append(Calculator.MethodPowerData.get(i));
		}
		
		CTabItem tbtmLcdPower = new CTabItem(body, SWT.NONE);
		tbtmLcdPower.setText("LCD Power");
				
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
		newShell.setText("µçÁ¿²âÊÔ²å¼þ");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 500);
	}

}
