package application.model;

public class Obstacle
{

	private final String	name;
	private final double	height;
	private final double	position;
	private final double	blastProtection;

	public Obstacle(final String name, final double height, final double position,
			final double blastProtection)
	{
		this.name = name;
		this.height = height;
		this.position = position;
		this.blastProtection = 50.0;
	}

	/**
	 * @return
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public final double getHeight()
	{
		return height;
	}

	public double getPosition()
	{
		return position;
	}

	public final double getBlastProtection()
	{
		return blastProtection;
	}

	@Override
	public String toString()
	{
		return "Obs{" + getName() + ", h=" + getHeight() + ", p=" + getPosition() + '}';
	}
}