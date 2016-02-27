package application.model;

public class RunwayParameters
{

	private final double TORA;
	private final double TODA;
	private final double ASDA;
	private final double LDA;
	private final double displacedThreshold;


	/**
	 * @param TORA
	 * @param TODA
	 * @param ASDA
	 * @param LDA
	 */
	public RunwayParameters(final double TORA, final double TODA, final double ASDA, final double LDA, final double displacedThreshold)
	{
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
		this.displacedThreshold = displacedThreshold;
	}


	/**
	 * @return
	 */
	public final double getTORA()
	{
		return TORA;
	}


	/**
	 * @return
	 */
	public final double getTODA()
	{
		return TODA;
	}


	/**
	 * @return
	 */
	public final double getASDA()
	{
		return ASDA;
	}


	/**
	 * @return
	 */
	public final double getLDA()
	{
		return LDA;
	}


	public final double getDisplacedThreshold()
	{
		return displacedThreshold;
	}

}