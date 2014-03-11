import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstancePool
{
	private ArrayList<Instance> instances;
	private Map<String,Integer> encodeFeatures;
	private Map<String,Integer> encodeCategory;
	private int[] idFGenerator;
	private int idCGenerator;
	
	public InstancePool(FileInputStream fis)
	{
		instances = new ArrayList<Instance>();
		encodeFeatures = new HashMap<String,Integer>();
		encodeCategory = new HashMap<String,Integer>();
		idCGenerator = 1;
		createInstances(fis);
	}

	private void createInstances(FileInputStream fis)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line=null;
		try
		{
			Instance inst = null;
			//TODO might remove this if and support only numerical data sets.
			if((line = br.readLine())!=null)
			{
				String [] split = line.split(",");
				idFGenerator = new int[split.length-1];
				for(int i=0;i<idFGenerator.length;i++)
					idFGenerator[i]=1;
			}
			else
			{
				System.err.println("Fatal error!First line is null.Program will exit now");
				System.exit(1);
			}
			fis.getChannel().position(0); //reset stream to read the file again
			br = new BufferedReader(new InputStreamReader(fis));
			while((line = br.readLine()) != null)
			{
				String [] split = line.split(",");
				inst = new Instance();
				int i=0;
				for(;i<split.length-1;i++)
				{
					try
					{
						inst.addFeature(Integer.parseInt(split[i].trim()));
					}
					catch(NumberFormatException e)
					{
						inst.addFeature(encodeFeature(i,split[i].trim()));
					}
				}
				inst.setCategory(Integer.parseInt(split[i].trim()));
				instances.add(inst);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public int getNumOfInstances()
	{
		return instances.size();
	}
	
	public int getFeaturesNum()
	{
		return instances.get(0).featuresNum();
	}
	
	//TODO consider removing this function and support only numerical data sets
	private int encodeFeature(int index,String feature)
	{
		if(encodeFeatures.containsKey(feature))
			return encodeFeatures.get(feature);
		else
		{
			encodeFeatures.put(feature, idFGenerator[index]);
			return idFGenerator[index]++;
		}
	}
	
	//TODO consider removing this function and support only numerical data sets
	private int encodeCategory(String category)
	{
		if(encodeCategory.containsKey(category))
			return encodeCategory.get(category);
		else
		{
			encodeCategory.put(category, idCGenerator);
			return idCGenerator++;
		}
	}
	
	public Instance getInstance(int index)
	{
		return instances.get(index);
	}
	
	//TODO remove when done
	public String toString()
	{
		String str="";
		for(Instance a:instances)
		{
			for(int i=0;i<a.featuresNum();i++)
			{
				System.out.print(a.getFeature(i)+" ");
			}
			System.out.print(a.getCategory());
			System.out.println();
		}
		return str;
	}
}
