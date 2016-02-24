package application.model;

import javafx.beans.property.IntegerProperty;

public class RunwayDetails
{

	private final IntegerProperty displacedThreshold;
	private final IntegerProperty clearway; //75.0
	private final IntegerProperty stopway; //60.0
	private final IntegerProperty resa; //240.0
	private final IntegerProperty stripEnd;
	private final IntegerProperty blastProtection; //300.0
	private final IntegerProperty runwayStrip; //150.0
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
	public RunwayDetails(final IntegerProperty displacedThreshold, final IntegerProperty clearway, final IntegerProperty stopway, final IntegerProperty resa,
			final IntegerProperty stripEnd, final IntegerProperty blastProtection, final IntegerProperty runwayStrip)
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
		return displacedThreshold.getValue();
	}


	/**
	 * @return
	 */
	public final int getClearway()
	{
		return clearway.getValue();
	}


	/**
	 * @return
	 */
	public final int getStopway()
	{
		return stopway.getValue();
	}


	/**
	 * @return
	 */
	public final int getResa()
	{
		return resa.getValue();
	}


	/**
	 * @return
	 */
	public final int getStripEnd()
	{
		return stripEnd.getValue();
	}


	/**
	 * @return
	 */
	public final int getBlastProtection()
	{
		return blastProtection.getValue();
	}


	/**
	 * @return
	 */
	public final int getRunwayStrip()
	{
		return runwayStrip.getValue();
	}

}