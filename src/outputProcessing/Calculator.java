package outputProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import outputResult.ConsoleFactory;

import powertesting.CalculateWindow_Manual;

public class Calculator {
	public static ArrayList<String> MethodPowerData = new ArrayList<String>();
	public static ArrayList<String> MethodList = new ArrayList<String>();
	public static ArrayList<String> PowerList = new ArrayList<String>();
	public static ArrayList<Double> PowerListNum = new ArrayList<Double>();
	public static ArrayList<String> HistoryMethodData = new ArrayList<String>();
	public static ArrayList<String> HistoryMethod = new ArrayList<String>();
	public static ArrayList<String> HistoryPower = new ArrayList<String>();
	public static ArrayList<Double> HistoryPowerNum = new ArrayList<Double>();
	public static String packagename;
	public static String appname;

	//计算各个方法总的百分比
	private double totalPercent(String MethodPath) throws IOException{
		double totalPercent = 0;
		String str;
		int end;
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(MethodPath)));
		
		while((str=br.readLine())!=null){
			end = str.indexOf("%");
			String cpuPowerPercent = str.substring(0, end).trim();
			double p = Double.parseDouble(df.format(Double.parseDouble(cpuPowerPercent)));
			totalPercent += p;
			br.readLine();
		}
		br.close();
		
		return totalPercent;
		
	}
	@SuppressWarnings("resource")
	public ArrayList<Double> getPowerTextFromMethodText(String MethodPath, String PowerTutorPath, String outputPath)
			throws IOException {
		ArrayList<Double> ResultData = new ArrayList<Double>();
		int begin, end;
		double p;
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(MethodPath)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));

		String str;
		String methodName = ""; // 方法名称
		String cpuPowerPercent = ""; // CPU百分比

		while ((str = br.readLine())!=null) {
			//读取各个方法的CPU使用率和方法名(百分号被去除)
			begin = 0;
			end = str.indexOf("%", begin);
			cpuPowerPercent = str.substring(begin, end).trim();
			begin = end + 2;
			methodName = str.substring(begin, str.length());

			p = Double.parseDouble(df.format(Double.parseDouble(cpuPowerPercent)));
			p = (p / totalPercent(MethodPath)) * readCPUtotalpower(PowerTutorPath);
			String ps = df.format(p);
			p = Double.parseDouble(ps);
			bw.append(ps);
			bw.append(" J\t");
			bw.append(methodName);
			bw.append("\r\n");
			String s = methodName + "\t" + ps +"J";
			
			MethodPowerData.add(s);
			MethodList.add(methodName);
			PowerListNum.add(p);
			ResultData.add(p);
			PowerList.add("" + p + "J");
			
			br.readLine();
		}
		bw.append("total power: ");
		bw.append("" + readCPUtotalpower(PowerTutorPath));
		
		MethodPowerData.add("total power: " + readCPUtotalpower(PowerTutorPath) + "J");
		bw.append("\r\n");
		bw.close();
		return ResultData;
	}
	
	@SuppressWarnings("resource")
	public ArrayList<Double> getHistoryTextFromMethodText(String MethodPath, String PowerTutorPath)
			throws IOException {
		ArrayList<Double> ResultData = new ArrayList<Double>();
		int begin, end;
		double p;
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(MethodPath)));


		String str;
		String methodName = ""; // 方法名称
		String cpuPowerPercent = ""; // CPU百分比

		while ((str = br.readLine())!=null) {
			//读取各个方法的CPU使用率和方法名(百分号被去除)
			begin = 0;
			end = str.indexOf("%", begin);
			cpuPowerPercent = str.substring(begin, end).trim();
			begin = end + 2;
			methodName = str.substring(begin, str.length());

			p = Double.parseDouble(df.format(Double.parseDouble(cpuPowerPercent)));
			p = (p / totalPercent(MethodPath)) * readCPUtotalpower(PowerTutorPath);
			String ps = df.format(p);
			p = Double.parseDouble(ps);
			String s = methodName + "\t" + ps +"J";
			
			HistoryMethodData.add(s);
			HistoryMethod.add(methodName);
			HistoryPowerNum.add(p);
			ResultData.add(p);
			HistoryPower.add("" + p + "J");
			
			br.readLine();
		}
		HistoryMethodData.add("total power: " + readCPUtotalpower(PowerTutorPath) + "J");
		return ResultData;
	}

/*
	@SuppressWarnings("resource")
	public ArrayList<Double> readHistoryFile(String MethodPath, String PowerTutorPath, String outputPath)
			throws IOException {
		ArrayList<Double> ResultData = new ArrayList<Double>();
		int begin = 0;
		int end = 0;
		double p;
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; // 用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。

		FileWriter fw = new FileWriter(outputPath);
		BufferedWriter bw = new BufferedWriter(fw);

		String str = "";
		String str1 = ""; // 方法名称
		String str2 = ""; // CPU百分比
		fis = new FileInputStream(MethodPath);// FileInputStream
		// 从文件系统中的某个文件中获取字节
		isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
		br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new
										// InputStreamReader的对象
		while ((str = br.readLine()) != null) {
			if (str.equals(""))
				continue;
			begin = 0;
			end = str.indexOf("%", begin);
			str2 = str.substring(begin, end);
			ConsoleFactory.printToConsole("percent: " + str2.trim(), true);
			begin = end + 2;
			str1 = str.substring(begin, str.length());
			ConsoleFactory.printToConsole("method: " + str1 + "\n", true);

			int end1;
			int end2 = 0;

			if (CalculateWindow_Manual.packageName != null)

			{
				end1 = CalculateWindow_Manual.packageName.lastIndexOf("/", CalculateWindow_Manual.packageName.length());
				end2 = str1.lastIndexOf("/", str1.length());
				packagename = CalculateWindow_Manual.packageName.substring(0, end1);
			} else// if(MainWindow.tag==2)//(ChooseOutputWindow.TestPackageName!=null)
				packagename = "com/example/l2048";
			String packagename1 = packagename.replace(".", "/");// packagename=com/example/canlendar_demo
			String temp = str1.substring(0, end2);
			System.out.print("packagename1=" + packagename1 + "\n");
			System.out.print("temp=" + temp + "\n");

			p = Double.parseDouble(str2.trim());
			df.format(p);
			p = (p / 100.00) * CalculatorOld.totalpower;
			String ps = df.format(p);
			p = Double.parseDouble(ps);
			bw.append(ps);
			bw.append("\t");
			bw.append(str1);
			bw.append("\r\n");
			System.out.print("Caculator.unit:" + CalculatorOld.unit + "\n");

			String s = str1 + "\t" + ps + CalculatorOld.unit;
			System.out.print("s:" + s + "\n");
			Historymethoddata.add(s);
			if (temp.equals(packagename1)) {
				Historylocalmethod.add(str1);
				Historylocaldata.add(p);
				ResultData.add(p);
				Historylocalpower.add("" + p + CalculatorOld.unit);
			}
		}
		bw.append("total power: ");
		bw.append("" + readCPUtotalpower(PowerTutorPath));
		ConsoleFactory.printToConsole("Power_Inc: " + CalculatorOld.totalpower + CalculatorOld.unit + "\n", true);
		Historymethoddata.add("total power: " + CalculatorOld.totalpower + CalculatorOld.unit);
		bw.append(CalculatorOld.unit);
		bw.append("\r\n");
		System.out.print("power caculation finish");
		bw.close();
		return ResultData;
	}
*/
	public static void main(String[] args) {
	}
	
	public static double readCPUtotalpower(String filePath) throws IOException {
		double p = 0; //各个方法的电量
		int begin;
		int end;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		try {
			String str;
			String appname;	//应用名
			String power;	//电量(包括单位)
			String powerNum;//电量数值的字符串
			while ((str = br.readLine())!=null) {
				end = 0;
				begin = str.indexOf("] ", 0) + 2;
				end = str.indexOf(" ", begin);
				appname = str.substring(begin, end);
				if (appname.trim().equals(CalculateWindow_Manual.projectName)) {
					begin = end + 1;
					power = str.substring(begin);
					end = str.indexOf(" ", begin);
					powerNum = str.substring(begin,end);
					p = Double.parseDouble(powerNum);
				}
				br.readLine();
			}
			br.close();
			return p;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
