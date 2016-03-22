package application.model;

public class Obstacle
{

	//TODO: implement distance from centreline

	private final String name;
	private final double height;
	private final double displacementPosition;
	private final double centrePosition;
	private final double blastProtection;


	public Obstacle(final String name, final double height, final double displacementPosition, final double blastProtection, double centrePosition)
	{
		this.name = name;
		this.height = height;
		this.displacementPosition = displacementPosition;
		this.centrePosition = centrePosition;
		this.blastProtection = 300.0;
	}


	public String getName()
	{
		return name;
	}


	public final double getHeight()
	{
		return height;
	}


	public final double getDisplacementPosition()
	{
		return displacementPosition;
	}

	public final double getCentrePosition() {
		return centrePosition;
	}

	public final double getBlastProtection()
	{
		return blastProtection;
	}


	@Override
	public final String toString()
	{
		return name + ": Height =" + height + ", Pos = " + displacementPosition;
	}
}