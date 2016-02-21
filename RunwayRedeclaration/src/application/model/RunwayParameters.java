package application.model;

import javafx.beans.property.DoubleProperty;

public class RunwayParameters
{

	private DoubleProperty TORA;
	private DoubleProperty TODA;
	private DoubleProperty ASDA;
	private DoubleProperty LDA;


	public RunwayParameters()
	{
	}


	public double getTORA()
	{
		return TORA.getValue();
	}


	public double getTODA()
	{
		return TODA.getValue();
	}


	public double getASDA()
	{
		return ASDA.getValue();
	}


	public double getLDA()
	{
		return LDA.getValue();
	}

}