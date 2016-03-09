package application.model;

public class Runway
{

	// From Redeclaration
	private static final double CLEARWAY         = 0.0; //75.0
	private static final double STOPWAY          = 0.0; //60.0
	private static final double RESA             = 240.0; //240.0
	private static final double STRIP_END        = 60.0;
	private static final double STRIP_WIDTH      = 50.0;
	private static final double CAG_WIDTH        = 75.0;
	private static final double BLAST_PROTECTION = 300.0; //300.0
	private static final double RUNWAY_STRIP     = 150.0; //150.0
	private static final double ANGLE_OF_SLOPE   = 50.0;
	//private static final double STRIP_END = 50.0;
	//private static final double RESA     = 200.0;
	private final int      runwayID;
	private final String   alignment;
	private final double   TORA;
	private final double   TODA;
	private final double   ASDA;
	private final double   LDA;
	private final double   displacedThreshold;
	private final String   explanation;
	private       Obstacle obstacle;


	public Runway(final int runwayID, final String alignment, final double TORA, final double TODA, final double ASDA, final double LDA, final double displacedThreshold)
	{
		if (alignment == null || alignment.equals(""))
			throw new IllegalArgumentException();

		this.runwayID = runwayID;
		this.alignment = alignment;
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
		this.displacedThreshold = displacedThreshold;
		this.explanation = null;

		if (alignment.equals("27R"))
		{
			obstacle = new Obstacle("27RTest", 12.0, 3646.0, 300.0);
		}
		if (alignment.equals("09L"))
		{
			obstacle = new Obstacle("09LTest", 12.0, -50.0, 300.0);
		}
	}


	public Runway(final int runwayID, final String alignment, final double TORA, final double TODA, final double ASDA, final double LDA, final double displacedThreshold,
			final String explanation)
	{
		if (alignment == null || alignment.equals(""))
			throw new IllegalArgumentException();

		this.runwayID = runwayID;
		this.alignment = alignment;
		this.TORA = TORA;
		this.TODA = TODA;
		this.ASDA = ASDA;
		this.LDA = LDA;
		this.displacedThreshold = displacedThreshold;
		this.explanation = explanation;

		if (alignment.equals("27R"))
		{
			obstacle = new Obstacle("27RTest", 12.0, 3646.0, 300.0);
		}
		if (alignment.equals("09L"))
		{
			obstacle = new Obstacle("09LTest", 12.0, -50.0, 300.0);
		}

	}


	public static Integer calculateSecondaryAlignment(Integer firstPosition)
	{
		if (firstPosition > 17)
		{
			return firstPosition - 18;
		}
		else
		{
			return firstPosition + 18;
		}
	}


	public static String calculateSecondaryPosition(final String primaryPosition)
	{
		String secondaryPosition = "";
		switch (primaryPosition)
		{
			case "L":
				secondaryPosition = "R";
				break;
			case "C":
				secondaryPosition = "C";
				break;
			case "R":
				secondaryPosition = "L";
		}
		return secondaryPosition;
	}


	public static double getClearway()
	{
		return CLEARWAY;
	}


	public static double getStopway()
	{
		return STOPWAY;
	}


	public static double getResa()
	{
		return RESA;
	}


	public static double getStripEnd()
	{
		return STRIP_END;
	}


	public static double getBlastProtection()
	{
		return BLAST_PROTECTION;
	}


	public static double getRunwayStrip()
	{
		return RUNWAY_STRIP;
	}


	public static double getAngleOfSlope()
	{
		return ANGLE_OF_SLOPE;
	}


	public static double getStripWidth()
	{
		return STRIP_WIDTH;
	}


	public static double getCagWidth()
	{
		return CAG_WIDTH;
	}


	public final void removeObstacle()
	{
		if (obstacle != null)
		{
			obstacle = null;
		}
		else
		{
			throw new IllegalStateException("No obstacle to remove");
		}
	}


	public final int getRunwayID()
	{
		return runwayID;
	}


	public final String getAlignment()
	{
		return alignment;
	}


	public final double getTORA()
	{
		return TORA;
	}


	public final double getTODA()
	{
		return TODA;
	}


	public final double getASDA()
	{
		return ASDA;
	}


	public final double getLDA()
	{
		return LDA;
	}


	public final double getDisplacedThreshold()
	{
		return displacedThreshold;
	}


	@Override
	public final String toString()
	{
		return "Runway{" + getAlignment() + '}';
	}


	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Runway))
			return false;

		Runway o = (Runway) other;
		return getRunwayID() == o.getRunwayID() &&
				java.util.Objects.equals(getAlignment(), o.getAlignment()) &&
				getTORA() == o.getTORA() &&
				getTODA() == o.getTODA() &&
				getASDA() == o.getASDA() &&
				getLDA() == o.getLDA() &&
				getDisplacedThreshold() == o.getDisplacedThreshold() &&
				java.util.Objects.equals(getObstacle(), o.getObstacle());
	}


	public Obstacle getObstacle()
	{
		return obstacle;
	}


	public final void setObstacle(Obstacle obstacle)
	{
		this.obstacle = obstacle;
	}


	public Runway redeclare()
	{
		final Obstacle o = getObstacle();
		if (o.getPosition() > TORA / 2.0)
		{
			return redeclareTowards(o);
		}
		else
		{
			return redeclareAway(o);
		}
	}


	private Runway redeclareTowards(Obstacle o)
	{
		// Distance from Threshold - Slope Calculation - Strip End
		// + getDisplacedThreshold() term seems to make sense
		// based on scenarios
		final double newTORA = o.getPosition() + getDisplacedThreshold() - (o.getHeight() * ANGLE_OF_SLOPE) - STRIP_END;

		// Distance from Threshold - RESA - Strip End
		final double newLDA = o.getPosition() - RESA - STRIP_END;

		StringBuilder sb = new StringBuilder("This Redeclaration (Towards) Explained:\n");
		sb.append("New_TORA = obstacle_distance_from_threshold + displaced_Threshold - slope - strip_End\n");
		sb.append("Where slope = obstacle_height * height_ratio\n");
		sb.append("New_TORA = ");
		sb.append(o.getPosition());
		sb.append(" + ");
		sb.append(getDisplacedThreshold());
		sb.append(" - (");
		sb.append(o.getHeight());
		sb.append(" * 50) - ");
		sb.append(STRIP_END);
		sb.append(" = ");
		sb.append(newTORA);
		sb.append("\nNew_TODA = New_TORA\nNew_ASDA = New_TORA\n");
		sb.append("New_LDA = obstacle_distance_from_threshold - RESA - strip_end\n");
		sb.append("New_LDA = ");
		sb.append(o.getPosition());
		sb.append(" - ");
		sb.append(RESA);
		sb.append(" - ");
		sb.append(STRIP_END);
		sb.append(" = ");
		sb.append(newLDA);

		Runway ret = new Runway(getRunwayID(), getAlignment(), newTORA, newTORA, newTORA, newLDA, getDisplacedThreshold(), sb.toString());
		ret.setObstacle(o);
		return ret;
	}


	private Runway redeclareAway(Obstacle o)
	{
		// Original TORA - Blast Protection - Dist from Threshold -
		// Displaced Threshold
		final double newTORA = getTORA() - o.getBlastProtection() - o.getPosition() - getDisplacedThreshold();

		// (R) TORA + STOPWAY
		final double newTODA = newTORA + STOPWAY;

		// (R) TORA + CLEARWAY
		final double newASDA = newTORA + CLEARWAY;

		// Original LDA - Dist from Threshold - Strip End - Slope Calculation
		final double newLDA = getLDA() - o.getPosition() - STRIP_END - (o.getHeight() * ANGLE_OF_SLOPE);

		StringBuilder sb = new StringBuilder("This Redeclaration (Away) Explained:\n");
		sb.append("New_TORA = TORA - blast_protection - obstacle_position - displaced_threshold\n");
		sb.append("New_TORA = ");
		sb.append(getTORA());
		sb.append(" - ");
		sb.append(o.getBlastProtection());
		sb.append(" - ");
		sb.append(o.getPosition());
		sb.append(" - ");
		sb.append(getDisplacedThreshold());
		sb.append(" = ");
		sb.append(newTORA);
		sb.append("\nNew_TODA = New_TORA + STOPWAY\n");
		sb.append("New_TODA = ");
		sb.append(newTORA);
		sb.append(" + ");
		sb.append(STOPWAY);
		sb.append(" = ");
		sb.append(newTODA);
		sb.append("\nNew_ASDA = New_TORA + CLEARWAY\n");
		sb.append("New_ASDA = ");
		sb.append(newTODA);
		sb.append(" + ");
		sb.append(CLEARWAY);
		sb.append(" = ");
		sb.append(newASDA);
		sb.append("\nNew_LDA = LDA - obstacle_positon - strip_end - slope\n");
		sb.append("Where slope = obstacle_height * height_ratio\n");
		sb.append("New_LDA = ");
		sb.append(LDA);
		sb.append(" - ");
		sb.append(o.getPosition());
		sb.append(" - ");
		sb.append(STRIP_END);
		sb.append(" - (");
		sb.append(o.getHeight());
		sb.append(" * 50) = ");
		sb.append(newLDA);

		Runway ret = new Runway(getRunwayID(), getAlignment(), newTORA, newTODA, newASDA, newLDA, getDisplacedThreshold(), sb.toString());
		ret.setObstacle(o);
		return ret;
	}


	public String getExplanation()
	{
		return explanation;
	}
}