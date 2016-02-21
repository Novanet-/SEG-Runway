package application.model;

import javafx.beans.property.IntegerProperty;

public class RunwayDetails
{

	private final IntegerProperty displacedThreshold;
	private final IntegerProperty clearway;
	private final IntegerProperty stopway;
	private final IntegerProperty resa;
	private final IntegerProperty stripEnd;
	private final IntegerProperty blastProtection;
	private final IntegerProperty runwayStrip;


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


	public int getDisplacedThreshold()
	{
		return displacedThreshold.getValue();
	}


	public int getClearway()
	{
		return clearway.getValue();
	}


	public int getStopway()
	{
		return stopway.getValue();
	}


	public int getResa()
	{
		return resa.getValue();
	}


	public int getStripEnd()
	{
		return stripEnd.getValue();
	}


	public int getBlastProtection()
	{
		return blastProtection.getValue();
	}


	public int getRunwayStrip()
	{
		return runwayStrip.getValue();
	}

}