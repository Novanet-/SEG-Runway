package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

public class Obstacle
{

	private final StringProperty name;
	private final DoubleProperty height;
	private final DoubleProperty position;
	private final DoubleProperty blastProtection;


	public Obstacle(final StringProperty name, final DoubleProperty height, final DoubleProperty position, final DoubleProperty blastProtection)
	{
		this.name = name;
		this.height = height;
		this.position = position;
		this.blastProtection = new SimpleDoubleProperty(50.0);
	}


	/**
	 * @return
	 */
	public final String getName()
	{
		return name.getValueSafe();
	}


	/**
	 * @return
	 */
	public final double getHeight()
	{
		return height.getValue();
	}


	public double getPosition()
	{
		return position.get();
	}


	public final double getBlastProtection()
	{
		return blastProtection.get();
	}


	@Override
	public String toString()
	{
		return "Obs{" + name.getValue() +
				", h=" + height.getValue() +
				", p=" + position.getValue() +
				'}';
	}
}