package outputProcessing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 

public class LineCounter {
    List<File> list = new ArrayList<File>();
    public static int linenumber = 1;
   // public static URL pathname; 
    FileReader fr = null;
    BufferedReader br = null;
 
    public int counter(String projectName,String method,String classname) {
//        String path = System.getProperty("user.dir");
       // String path = LineCounter.class.getResource("/").getPath();  // ͬ�¸�path
       // path = path.substring(0, path.length() - 24) + projectName + "/src";
    
    //	int end=ChooseTestWindow.TestPath.lastIndexOf("\\",ChooseTestWindow.TestPath.length());
    //	String path=ChooseTestWindow.TestPath.substring(0,end)+"\\src";   //"D:\\zpw\\adt-bundle-windows-x86-20140321\\workplace\\UMLogger\\src";
    	String path=projectName+"\\src";
    	System.out.println("Path:"+path);
        File file = new File(path);
        File files[] = null;
        files = file.listFiles();
        addFile(files);
        isDirectory(files);
        int linenumber=readLinePerFile(method.trim(),classname);
       // System.out.println("Totle:" + linenumber + "��");
        return linenumber;
    }
 
    // �ж��Ƿ���Ŀ¼
    public void isDirectory(File[] files) {
        for (File s : files) {
            if (s.isDirectory()) {
                File file[] = s.listFiles();
                addFile(file);
                isDirectory(file);
                continue;
            }
        }
    }
 
    //��src�������ļ���֯��list
    public void addFile(File file[]) {
        for (int index = 0; index < file.length; index++) {
            list.add(file[index]);
            // System.out.println(list.size());
        }
    }
     
    //��ȡ�ǿհ���
    public int readLinePerFile(String method,String classname) {
        try {
            for (File s : list) {
                linenumber=0;
                if (s.isDirectory()) {
                    continue;
                }
                if(s.getName().equals(classname))
                {
                fr = new FileReader(s);
                br = new BufferedReader(fr);
                String i="";
                int j=1;
                while ((i = br.readLine()) != null) {
                    int begin=0;
                    int end=0;
                	if ((end=i.indexOf("(",begin)) != -1)
                	{	
                		if((begin=i.lastIndexOf(" ",end))!=-1)
                		{ 
                			String s1=i.substring(begin+1,end).trim();
                			    if(s1.equals(method.trim()))
                			    {   linenumber=j;
                			       
                			       
                			    }
                			    
                		}
                    }
                	j++;
                	if(linenumber!=0)
                		break;
                }
                if(linenumber!=0)
                {
                System.out.print(s.getName());
                System.out.println("\t\t��" + LineCounter.linenumber + "��");
              //  pathname=File.class.getResource("");
               // System.out.print(pathname+"\n");
                return linenumber;
                }
              // 
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e) {
                }
            }
        }
		return linenumber;
    }
 
    //�Ƿ��ǿ���
    public boolean isBlankLine(String i) {
        if (i.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }
     
    public int GetLineNum(String projectname,String methodname,String classname){
        LineCounter lc = new LineCounter();
        //projectname = "D:\\zpw\\adt-bundle-windows-x86-20140321\\workplace\\calendar_demo";     //���ﴫ�������Ŀ����
      //  methodname="formatDate";
        return linenumber=lc.counter(projectname,methodname,classname);
    }
    
    public static void main(String[] args) throws IOException{
    	//ConsoleResult con=new ConsoleResult();
      	// con.ConsoleResult("com/example/calendar_demo/StringUtil.FixString","1mj");
    	LineCounter con=new LineCounter();
     	 int i=con.GetLineNum("l2048", "onPostExecute","MainActivity.java");//onPostExecute showUpdateDialog onClick NotificationUpdateActivity
     System.out.print(i);
    }
    
}