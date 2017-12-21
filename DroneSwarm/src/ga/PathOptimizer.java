package ga;
import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import util.Point;

//the fitness function
@SuppressWarnings("serial")
public class PathOptimizer  extends FitnessFunction {

	double vMax = Double.MAX_VALUE;
	
	Point intermediateGoal;
	
	double p;
	
	double q;
	
	double n;
	
	double k;
	
	double l;
	
	
	public PathOptimizer(Point intermediateGoal)
	{
		this.intermediateGoal = intermediateGoal;
	}
	

	
	@Override
	protected double evaluate(IChromosome arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private boolean checkCollision(){
		return true;
	}
	
	private double sumDi(Chromosome c)
	{
		double sum =0;
		
		for(int i=0;i<c.size();i=i+2)
		{			
			WorldValue v1 = (WorldValue) c.getGene(i);
			WorldValue v2 = (WorldValue) c.getGene(i+1);
			
			sum += di((double)v1.getAllele(),(double)v2.getAllele());
		}
		
		return sum;
	}
	
	private double dist(double x0, double y0,double x1, double y1)
	{
		return Math.sqrt(Math.pow(x1 - x0,2)+Math.pow(y1 - y0,2));
	}
	
	private double distToClosestRobot(Chromosome c,int robotIndex ,double x, double y)
	{
		double closest = Double.MAX_VALUE;		
		
		for(int i=0;i<c.size();i=i+2)
		{
			if(i != robotIndex)
			{
				WorldValue v1 = (WorldValue) c.getGene(i);
				WorldValue v2 = (WorldValue) c.getGene(i+1);
				
				double d = dist(x,y,(double)v1.getAllele(),(double)v2.getAllele());
				
				if(d<closest){
					closest = d;
				}
			}
		}
		
		return closest;
	}
	
	private double x(Chromosome c,int robotIndex ,double x, double y)
	{
		double distToClosest = distToClosestRobot(c,robotIndex,x,y);
		return Math.pow(distToClosest,2);
		
	}
	
	private double sumX(Chromosome c)
	{
		double sum = 0;
		
		for(int i=0;i<c.size();i=i+2)
		{			
			WorldValue v1 = (WorldValue) c.getGene(i);
			WorldValue v2 = (WorldValue) c.getGene(i+1);
			sum += x(c,i,(double)v1.getAllele(),(double)v2.getAllele());
		}
		return sum;
	}
	
	private double di(double x, double y)
	{
		return Math.abs(intermediateGoal.x - x)+ Math.abs(intermediateGoal.y - y);
	}
	
	private double distToClosestObject(double x, double y)
	{
		return 1;
	}
	
	private double rep(double x, double y)
	{
		double distToClosest = distToClosestObject(x,y);
		
		if(distToClosest<q)
		{
			return 0.5*n*((1/distToClosest)-(1/q));
		}
		else
		{
			return 0;
		}
	}
	
	private double sumRep(Chromosome c)
	{
		double sum =0;
		
		for(int i=0;i<c.size();i=i+2)
		{			
			WorldValue v1 = (WorldValue) c.getGene(i);
			WorldValue v2 = (WorldValue) c.getGene(i+1);
			
			sum += rep((double)v1.getAllele(),(double)v2.getAllele());
		}
		
		return sum;
	}
	
	private double Vd(Chromosome c)
	{
		if(checkCollision())
		{
			return vMax;
		}
		else
		{
			return sumDi(c)+p*sumRep(c)+0.5*k*sumX(c);
		}
	}
	
}

