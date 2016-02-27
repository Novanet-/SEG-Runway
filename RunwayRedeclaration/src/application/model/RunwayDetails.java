package application.model;

public class RunwayDetails
{

	private final int displacedThreshold;
	private final int clearway; //75.0
	private final int stopway; //60.0
	private final int resa; //240.0
	private final int stripEnd;
	private final int blastProtection; //300.0
	private final int runwayStrip; //150.0
	//Slope angle 1:50.0


	/**
	 * @param displacedThreshold
	 * @param clearway
	 * @param stopway
	 * @param resa
	 * @param stripEnd
	 * @param blastProtection
	 * @param runwayStrip
	 */
	public RunwayDetails(final int displacedThreshold, final int clearway, final int stopway, final int resa,
			final int stripEnd, final int blastProtection, final int runwayStrip)
	{
		this.displacedThreshold = displacedThreshold;
		this.clearway = clearway;
		this.stopway = stopway;
		this.resa = resa;
		this.stripEnd = stripEnd;
		this.blastProtection = blastProtection;
		this.runwayStrip = runwayStrip;
	}


	/**
	 * @return
	 */
	public final int getDisplacedThreshold()
	{
		return displacedThreshold;
	}


	/**
	 * @return
	 */
	public final int getClearway()
	{
		return clearway;
	}


	/**
	 * @return
	 */
	public final int getStopway()
	{
		return stopway;
	}


	/**
	 * @return
	 */
	public final int getResa()
	{
		return resa;
	}


	/**
	 * @return
	 */
	public final int getStripEnd()
	{
		return stripEnd;
	}


	/**
	 * @return
	 */
	public final int getBlastProtection()
	{
		return blastProtection;
	}


	/**
	 * @return
	 */
	public final int getRunwayStrip()
	{
		return runwayStrip;
	}

}