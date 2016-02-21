package application.model;

import javafx.beans.property.IntegerProperty;

public class RunwayDetails
{

	private IntegerProperty displacedThreshold;
	private IntegerProperty clearway;
	private IntegerProperty stopway;
	private IntegerProperty resa;
	private IntegerProperty stripEnd;
	private IntegerProperty blastProtection;
	private IntegerProperty runwayStrip;


	public RunwayDetails()
	{
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