
package outputResult;

import javax.swing.JFrame;
import java.awt.Panel;
import javax.swing.JTextField;

import outputProcessing.Calculator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.TextArea;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.ImageIcon;

public class PowerInfoList {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	/*
	 * 
	 * /** Create the application.
	 */
	public PowerInfoList() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setMaximumSize(new Dimension(100, 100));
		frame.setBounds(100, 100, 528, 463);
		frame.getContentPane().setLayout(null);

		Panel panel = new Panel();
		panel.setBounds(10, 141, 492, 273);
		frame.getContentPane().add(panel);

		final TextArea textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 492, 273);

		for (int i = 0; i < Calculator.MethodPowerData.size(); i++) {
			textArea.append(Calculator.MethodPowerData.get(i) + "\n");
		}
		
		// CaculatorInc.Incmethoddata=null;
		panel.setLayout(null);
		panel.add(textArea);
		textArea.setMaximumSize(new Dimension(30, 30));

		TextArea textArea_1 = new TextArea();
		textArea_1.setEnabled(false);
		textArea_1.setEditable(false);
		// textArea_1.setText("\n");
		textArea_1.setMaximumSize(new Dimension(30, 30));
		textArea_1.setBounds(208, 0, 206, 220);
		panel.add(textArea_1);

		JLabel label = new JLabel("New label");
		label.setBounds(0, 0, 512, 75);
		frame.getContentPane().add(label);
		label.setIcon(new ImageIcon("D:\\zpw\\adt-bundle-windows-x86-20140321\\workplace\\myplugin\\icons\\jnu.JPG"));

		textField = new JTextField();
		textField.setBounds(25, 95, 277, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("搜索");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("");
				String search = textField.getText().trim();
				for (int i = 0; i < Calculator.MethodPowerData.size(); i++) {
					int m = Calculator.MethodPowerData.get(i).indexOf(" ", 0);
					String method = Calculator.MethodPowerData.get(i).substring(0, m);
					ConsoleFactory.printToConsole("method:" + method + "\n", true);
					int p = method.indexOf(search, 0);
					ConsoleFactory.printToConsole(p + "\n", true);
					if (p != -1)
						textArea.append(Calculator.MethodPowerData.get(i) + "\n");
				}
			}
		});
		btnNewButton.setBounds(312, 97, 66, 23);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\u663E\u793A\u5168\u90E8");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textArea.setText("");
				for (int i = 0; i < Calculator.MethodPowerData.size(); i++)
					textArea.append(Calculator.MethodPowerData.get(i) + "\n");
			}
		});
		btnNewButton_1.setBounds(388, 97, 93, 23);
		/*
		frame.getContentPane().add(btnNewButton_1);
		for (int i = 0; i < Calculator.allmethoddata.size(); i++)
			textArea_1.append(Calculator.allmethoddata.get(i) + "\n");
		// textArea.setText("\n");
		frame.setVisible(true);
		*/
		String com1 = "cmd.exe /c d: && cd D:\\zpw\\adt-bundle-windows-x86-20140321\\workplace && adb uninstall edu.umich.PowerTutor";
		ConsoleFactory.printToConsole(com1, true);
		try {
			Runtime.getRuntime().exec(com1);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
