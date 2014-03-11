import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Main
{
	public static void main(String[] args)
	{	
		InstancePool train = new InstancePool(openFile("ticdata.txt"));
		LSClassifier.train(train);
		InstancePool test = new InstancePool(openFile("ticeval.txt"));
		LSClassifier.classify(test);
	}
	
	public static FileInputStream openFile(String filename)
	{
		File file = new File("./"+filename);
		FileInputStream  fis=null;
		try
		{
			fis = new FileInputStream(filename);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return fis;
	}
}
