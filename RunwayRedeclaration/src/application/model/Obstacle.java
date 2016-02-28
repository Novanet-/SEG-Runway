package application.model;

public class Obstacle
{

	//TODO: implement distance from centreline

	private final String name;
	private final double height;
	private final double position;
	private final double blastProtection;


	public Obstacle(final String name, final double height, final double position, final double blastProtection)
	{
		this.name = name;
		this.height = height;
		this.position = position;
		this.blastProtection = 50.0;
	}


	/**
	 * @return
	 */
	private String getName()
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


	public final double getPosition()
	{
		return position;
	}


	public final double getBlastProtection()
	{
		return blastProtection;
	}


	@Override
	public final String toString()
	{
		return "Obs{" + name + ", h=" + height + ", p=" + position + '}';
	}
}