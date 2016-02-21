package application.model;

import javafx.beans.property.DoubleProperty;

public class RunwayParameters
{

	private final DoubleProperty TORA;
	private final DoubleProperty TODA;
	private final DoubleProperty ASDA;
	private final DoubleProperty LDA;


	public RunwayParameters(final DoubleProperty TORA, final DoubleProperty TODA, final DoubleProperty ASDA, final DoubleProperty LDA)
	{
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
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