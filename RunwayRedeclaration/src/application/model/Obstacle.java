package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class Obstacle
{

	private StringProperty name;
	private DoubleProperty height, width, length;


	public String getName()
	{
		return name.getValueSafe();
	}


	public double getHeight()
	{
		return height.getValue();
	}


	public double getWidth()
	{
		return width.getValue();
	}


	public double getLength()
	{
		return length.getValue();
	}

}