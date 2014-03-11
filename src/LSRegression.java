import java.util.ArrayList;

/**
 * Implementation of Stochastic Gradient Descent algorithm using least squares error evaluation
 * Magkos Rafail-Georgios 3100098
 * Mpogdanos Michail 3100123
 * Ntatsev Ntaniel 3100131
 */

public class LSRegression
{
	private static final double h = 0.0001; //learning rate
	private static final int maxIterations = 5000; //maximum number of iterations
	private static final double tol = 0.001; //error tolerance
	private static ArrayList<Double> w;
	
	public static void train(InstancePool trainingInst)
	{
		long start=System.currentTimeMillis();
		randomiseWeights(trainingInst.getFeaturesNum());
		int iterationsNum=0;
		double s1 = 0,s2=0;
		int i;
		do
		{
			i=0;
			if(iterationsNum%2==0)
				s1=0;
			else
				s2=0;
			for(;i<trainingInst.getNumOfInstances();i++)
			{
				double fval=fValue(trainingInst.getInstance(i));
				if(iterationsNum%2==0)
				{
					s1+=errorEval(fval,trainingInst.getInstance(i).getCategory());
				}
				else
				{
					s2+=errorEval(fval,trainingInst.getInstance(i).getCategory());
				}
				updateWeights(fval,trainingInst.getInstance(i));
			}
			iterationsNum++;
			if(iterationsNum == maxIterations)
			{
				break;
			}
		}
		while(Math.abs(s1-s2)>=tol);
		System.out.println("Done training in "+(System.currentTimeMillis()-start)+"ms.Iterations number:"+iterationsNum);
		System.out.println("Weight vector\n"+w);
	}
	
	public static void evaluate(InstancePool testingInst)
	{
		int success=0;
		for(int i=0;i<testingInst.getNumOfInstances();i++)
		{
			if(Math.round(fValue(testingInst.getInstance(i)))==testingInst.getInstance(i).getCategory())
				success++;
		}
		double perc = (double)success/testingInst.getNumOfInstances();
		System.out.println("Found a total of "+success+" matches("+(perc*100.0)+"%)");
	}
	
	private static void randomiseWeights(int numOfFeatures)
	{
		w = new ArrayList<Double>();
		for(int i=0;i<numOfFeatures;i++)
		{
			w.add(Math.random());
		}
	}
	
	private static double fValue(Instance inst)
	{
		Double f=0.0;
		for(int i=0;i<inst.featuresNum();i++)
		{
			f+=(inst.getFeature(i)*w.get(i));
		}
		return f;
	}
	
	private static double errorEval(double fval,int y)
	{
		return 0.5*Math.pow(fval-y,2);
	}
	
	private static void updateWeights(double fval,Instance inst)
	{
		ArrayList<Double> newWeights = new ArrayList<Double>();
		for(int i=0;i<w.size();i++)
		{
			double wi=w.get(i);
			wi-=(h*(fval-inst.getCategory())*inst.getFeature(i));
			newWeights.add(wi);
		}
		w.clear();
		w.addAll(newWeights);
	}
}