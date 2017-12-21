package ga;

import java.util.StringTokenizer;

import org.jgap.*;

import util.Point;

@SuppressWarnings("serial")
public class WorldValue extends BaseGene implements Gene, java.io.Serializable {

	
	private static final String TOKEN_SEPARATOR = ":";
	private double maxValue;
	private double center;
	
	private Double value;
	
	public WorldValue(Configuration a_conf,double maxValue,double center) throws InvalidConfigurationException{
		super(a_conf);
		this.maxValue = maxValue;
		this.center = center;
	
	}

	@Override
	public int compareTo(Object arg0) {
		
		 // If the other allele is null, we're bigger.
        // ------------------------------------------
        if( arg0 == null )
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // QuarterGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if( value == null )
        {
            if ( ((WorldValue) arg0).value == null )
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        // Otherwise, we just take advantage of the Integer.compareTo()
        // method.
        // ------------------------------------------------------------
        return value.compareTo(((WorldValue) arg0).value );
	}

	@Override
	public void applyMutation(int arg0, double arg1) {
		setAllele(getConfiguration().getRandomGenerator().nextInt((int) (maxValue*1000))/1000);
		
	}

	@Override
	public String getPersistentRepresentation() throws UnsupportedOperationException {
		 return new Double( maxValue ).toString() +
                 TOKEN_SEPARATOR + value.toString();
	}

	@Override
	public void setAllele(Object arg0) {
		value = (Double) arg0;
		
	}

	@Override
	public void setToRandomValue(RandomGenerator rng) {
		double offset = new Double(rng.nextInt((int) (maxValue*1000))/1000);
		value = offset + center;
		
	}

	@Override
	public void setValueFromPersistentRepresentation(String arg0)
			throws UnsupportedOperationException, UnsupportedRepresentationException {
		 // We're expecting to find the maximum number of quarters that this
        // Gene can represent, followed by a colon, followed by the actual
        // number of quarters currently represented.
        // -----------------------------------------------------------------
        StringTokenizer tokenizer = new StringTokenizer( arg0,
                                                         TOKEN_SEPARATOR );
        // Make sure there are exactly two tokens.
        // ---------------------------------------
        if( tokenizer.countTokens() != 2 )
        {
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Two tokens expected." );
        }

        try
        {
            // Parse the two tokens as integers.
            // ---------------------------------
            maxValue = Double.parseDouble( tokenizer.nextToken() );
            value = new Double( tokenizer.nextToken() );
        }
        catch( NumberFormatException e )
        {
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Expecting integer values." );
        }
		
	}

	@Override
	protected Object getInternalValue() {
		return value;
	}

	public void cleanup()
	{
	        // There's no cleanup necessary for this implementation.
	        // -----------------------------------------------------
	}
	
	/**
     * Calculates the hash-code for this QuarterGene.
     *
     * @return the hash-code of this QuarterGene
     */
    public int hashCode()
    {
        return value.hashCode();
    }
	
	
	/**
     * Retrieves the value represented by this Gene. The actual type
     * of the value is implementation-dependent.
     *
     * @return the value of this Gene.
     */
    public Object getAllele()
    {
        return value;
    }
	
    public boolean equals( Object otherQuarterGene )
    {
        return otherQuarterGene instanceof WorldValue && 
               compareTo( otherQuarterGene ) == 0;
    }

    
	@Override
	protected Gene newGeneInternal() {
		try {
	        // We construct the new QuarterGene with the same maximum number
	        // of quarters that this Gene was constructed with.
	        // -------------------------------------------------------------
	        return new WorldValue(getConfiguration(),maxValue, center);
	      } catch (InvalidConfigurationException ex) {
	        throw new IllegalStateException(ex.getMessage());
	      }
	}
	
}
