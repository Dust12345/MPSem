package ga;
import java.util.Vector;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;

import util.Point;

public class GeneticAlgo
{
	
	public Vector<Point> intermediateGoals;
	
	int numberOfRobots;
	
	int maxEvolutionCycles = 1000;
	
	double targetFitness = 5000;
	
	public GeneticAlgo()
	{
	
	}	
	
	public void start() throws InvalidConfigurationException
	{
		Vector<Chromosome> bestC = new Vector<Chromosome>();
		
		for(int i=0;i<intermediateGoals.size();i++)
		{
			Chromosome c = evolveAtPoint(intermediateGoals.get(i));
			bestC.add(c);
		}
	}
	
	private Chromosome evolveAtPoint(Point p) throws InvalidConfigurationException{
		Configuration conf = new DefaultConfiguration();

		FitnessFunction myFunc =
		    new PathOptimizer(p);

		conf.setFitnessFunction( myFunc );	
	
		Gene[] sampleGenes = new WorldValue[ numberOfRobots*2 ];

		for(int i=0;i<sampleGenes.length;i=i+2)
		{
			sampleGenes[i] = new WorldValue( conf, 1 ,p.x);
			sampleGenes[i+1] = new WorldValue( conf, 1 ,p.y); 
		}		

		Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);

		conf.setSampleChromosome( sampleChromosome );

		conf.setPopulationSize( 500 );
		
		Genotype population = Genotype.randomInitialGenotype( conf );
		
		for(int i=0;i<maxEvolutionCycles;i++)
		{
			population.evolve();
			Chromosome bestSolutionSoFar = (Chromosome) population.getFittestChromosome();
			
			double fitness = bestSolutionSoFar.getFitnessValue();
			
			if(fitness >= targetFitness)
			{
				return bestSolutionSoFar;
			}
			
		}
		
		
		
		return null;
	}
	
}
