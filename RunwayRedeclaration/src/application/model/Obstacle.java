package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class Obstacle
{

	private final StringProperty name;
	private final DoubleProperty height, width, length;


	public Obstacle(final StringProperty name, final DoubleProperty height, final DoubleProperty width, final DoubleProperty length)
	{
		this.name = name;
		this.height = height;
		this.width = width;
		this.length = length;
	}


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