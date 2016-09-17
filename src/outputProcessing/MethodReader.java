package outputProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import powertesting.CalculateWindow_Manual;

public class MethodReader {
	public MethodReader() {
	}

	public String readHtml(String filepath) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "GB2312"));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void getMethodTextFromHtml(String filepath) throws IOException {
		String text = readHtml(filepath);
		int begin = text.indexOf("Inclusive elapsed times");
		int end;
		String path = CalculateWindow_Manual.outputPath + "\\method.txt";
		String percent;
		String method;
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		int over = text.indexOf("</pre>", begin);
		while ((end = text.indexOf("]  ", begin)) != -1 && end < over) {;
			begin = text.lastIndexOf("[", end);
			if (end > begin) {
				if (text.substring(begin + 1, end).equals("0")) {
					begin = end + 1;
					continue;
				}

				// 获取该方法的CPU使用率
				begin = end + 1;
				end = text.indexOf("%", begin);
				begin = end - 5;
				percent = text.substring(begin, end + 1);

				// 获取该方法的方法名
				begin = end + 1;
				begin = text.indexOf("/", begin);
				begin = text.lastIndexOf(" ", begin);
				end = text.indexOf(" ", begin + 1);
				method = text.substring(begin + 1, end);

				// 如果不是待测程序的方法,则跳过
				if (!method.startsWith(CalculateWindow_Manual.packageName.replace('.', '/'))) {
					begin = end + 1;
					continue;
				}
				else {
					System.out.print(percent);
					System.out.print(" "+method);
					System.out.print(" begin: " + begin);
					System.out.println(" over: " + over);
					String line = percent + " " + method + "\n";
					bw.append(line + "\r\n");
					begin = end + 1;
				}
			}
		}
		System.out.println("over");
		bw.close();	
	}
}