package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import application.model.Runway;

public class RunwayModel
{

	@Test
	public void testRunwayProperties()
	{
		// Exact values don't matter, just want to ensure
		// they are stored and returned

		double TORA = 20.0;
		double TODA = 21.0;
		double ASDA = 23.4;
		double LDA = 26.2;
		double displacedThreshold = 29.0;

		int runwayID = 14;
		String alignment = "L";
		Runway r = new Runway(runwayID, alignment, TORA, TODA, ASDA, LDA, displacedThreshold);

		assertEquals("Should return parameter", runwayID, r.getRunwayID());
		assertEquals("Should return parameter", alignment, r.getAlignment());
		assertEquals(TODA, r.getTODA(), 0.0);
		assertEquals(TORA, r.getTORA(), 0.0);
		assertEquals(ASDA, r.getASDA(), 0.0);
		assertEquals(LDA, r.getLDA(), 0.0);
		assertEquals(displacedThreshold, r.getDisplacedThreshold(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRunwayNullAlignment()
	{
		double TORA = 20.0;
		double TODA = 20.0;
		double ASDA = 20.0;
		double LDA = 20.0;
		double displacedThreshold = 20.0;

		int runwayID = 14;
		new Runway(runwayID, null, TORA, TODA, ASDA, LDA, displacedThreshold);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRunwayEmptyAlignment()
	{
		double TORA = 20.0;
		double TODA = 20.0;
		double ASDA = 20.0;
		double LDA = 20.0;
		double displacedThreshold = 20.0;

		int runwayID = 14;
		new Runway(runwayID, "", TORA, TODA, ASDA, LDA, displacedThreshold);
	}
}
