package outputProcessing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import outputResult.ConsoleFactory;
import powertesting.CalculateWindow_Manual;

public class ConsoleResult {
	public static ArrayList<String> localmethoddata = new ArrayList<String>();

	// public String method[]={"android/content/res/MainActivity.onCreate"};
	// public String power[]={"1mj"};
	public ConsoleResult(String method, String power) throws IOException {
		/*
		 * int end=0; if(ChooseTestWindow2.ProjectUnderTestPath!=null)
		 * end=ChooseTestWindow2.ProjectUnderTestPath.lastIndexOf("\\",
		 * ChooseTestWindow2.ProjectUnderTestPath.length()); else
		 * end=ChooseTestWindow.TestPath.lastIndexOf("\\",ChooseTestWindow.
		 * TestPath.length()); int begin=0;
		 */
		String projectname = CalculateWindow_Manual.projectName;

		String methodname = ConsoleResult.GetMathod(method);
		System.out.print(methodname + "\n");

		LineCounter counter = new LineCounter();

		String classname = ConsoleResult.GetClass(method);

		int linenumber = counter.GetLineNum(projectname, methodname, classname + ".java");

		String pathname = ConsoleResult.GetPath(method, projectname);

		// method power path
		ConsoleFactory.printToConsole(
				method + "\t" + power + "\t\n" + pathname + "(" + classname + ".java:" + linenumber + ")\n", true);
		String path;
		path = "D:\\mobiletest\\path.txt";
		FileWriter fw = new FileWriter(path, true);
		BufferedWriter bw = new BufferedWriter(fw);
		String s = projectname + " " + methodname + " " + power + " " + pathname + " " + linenumber + " \n";
		localmethoddata.add(s);
		bw.append(s);
		bw.close();
	}

	public static String GetPath(String method, String projectname) {
		int begin = 0;
		int end = 0;
		end = method.lastIndexOf("/", method.length());
		// method=method.substring(begin,end);

		begin = method.lastIndexOf("/", end - 1);
		String projectname1 = method.substring(begin, end);

		return projectname1.replace("/", "\\") + "\\src\\" + method.replace("/", "\\");

	}

	public static String GetClass(String method) {
		int begin = 0;
		int end = method.length();

		if ((end = method.lastIndexOf(";", method.length())) != -1) {
		} else
			end = method.length();

		end = method.lastIndexOf(".", end);
		begin = method.lastIndexOf("/", end);
		String s = method.substring(begin + 1, end);
		int begin2 = 0;
		if ((begin2 = s.indexOf("$", 0)) != -1 || (begin2 = s.indexOf("&", 0)) != -1) {
			s = s.substring(0, begin2);
		}

		return s;
	}

	public static String GetMathod(String method) {
		int end = method.length();

		if ((end = method.lastIndexOf(";", method.length())) != -1) {
		} else
			end = method.length();

		end = method.lastIndexOf(".", end);
		// begin=method.lastIndexOf("/",end);
		return method.substring(end + 1, method.length());
	}

}