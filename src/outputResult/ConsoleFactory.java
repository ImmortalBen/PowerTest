package outputResult;

import org.eclipse.ui.console.*;



public class ConsoleFactory implements IConsoleFactory {

	private static MessageConsole console = new MessageConsole("", null);
	static boolean exists = false;

	/**
	 * ����:�򿪿���̨
	 * */
	public void openConsole() {
		showConsole();
	}

	/** */
	/**
	 * ����:��ʾ����̨
	 * */
	private static void showConsole() {
		if (console != null) {
			// �õ�Ĭ�Ͽ���̨������
			IConsoleManager manager = ConsolePlugin.getDefault()
					.getConsoleManager();

			// �õ����еĿ���̨ʵ��
			IConsole[] existing = manager.getConsoles();
			exists = false;
			// �´�����MessageConsoleʵ�������ھͼ��뵽����̨������������ʾ����
			for (int i = 0; i < existing.length; i++) {
				if (console == existing[i])
					exists = true;
			}
			if (!exists) {
				manager.addConsoles(new IConsole[] { console });
			}

			// console.activate();

		}
	}

	/** */
	/**
	 * ����:�رտ���̨
	 * */
	public static void closeConsole() {
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		if (console != null) {
			manager.removeConsoles(new IConsole[] { console });
		}
	}

	/**
	 * ��ȡ����̨
	 * 
	 * @return
	 */
	public static MessageConsole getConsole() {

		showConsole();

		return console;
	}
	
	/**
	 * �����̨��ӡһ����Ϣ�����������̨��
	 * 
	 * @param message
	 * @param activate
	 *            �Ƿ񼤻����̨
	 */
	public static void printToConsole(String message, boolean activate) {
		MessageConsoleStream printer = ConsoleFactory.getConsole()
				.newMessageStream();
		printer.setActivateOnWrite(activate);
		printer.println("��ʾ��" + message );
		System.out.println("��ʾ��" + message );
	}

	
	
}

