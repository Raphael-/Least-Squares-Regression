import java.util.ArrayList;

public class Instance
{
	private ArrayList<Integer> features;
	private int category;

	public Instance()
	{
		features = new ArrayList<Integer>();
		features.add(1); //x0 is always 1
	}

	public void addFeature(int val)
	{
		features.add(val);
	}

	public int featuresNum()
	{
		return features.size();
	}

	public int getFeature(int i)
	{
		return features.get(i);
	}

	public void setCategory(int cat)
	{
		category = cat;
	}
	
	public int getCategory()
	{
		return category;
	}
}
