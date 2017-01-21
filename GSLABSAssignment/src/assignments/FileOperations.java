package assignments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

public class FileOperations 
{
	static String File1 = null;
	static String File2 = null;
  @Test
  public static void fileReadWrite() throws IOException 
  {
		File1 = "D:\\File1.txt";
		//create file
		File file = new File(File1);
		file.createNewFile();
		
		//writing into file
		FileWriter fw = new FileWriter(File1);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Writing in the file...");
		bw.newLine();
        bw.write("continuing writing in the file on the next line...");
        bw.close();
        
        //Reading from file
        FileReader fr = new FileReader(File1);
        BufferedReader br = new BufferedReader(fr);
        String fileContents="";
        while((fileContents = br.readLine())!=null)
        {
        	System.out.println(fileContents);
        }
        
        System.out.println("Successful!!!");
        br.close();
  }
  
  @Test
  public static void filePermissions() throws IOException
  {
	    File2 = "D:\\File2.txt";  
	    try
	    {
	    	FileWriter fw = new FileWriter(File2);
	    	BufferedWriter bw = new BufferedWriter(fw);
	        bw.newLine();
	        bw.write("Trying to write in a read only file...");
	        bw.close();
	    }
	    catch(IOException e)
	    {
	    	System.out.println("Cannot write into file");
	    }
		
  }
}
