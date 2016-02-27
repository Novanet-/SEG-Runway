package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import application.model.Runway;
import application.model.RunwayDetails;
import application.model.RunwayParameters;

public class RunwayModel
{

	@Test
	public void testRunwayProperties()
	{
		// Exact values don't matter, just want to ensure
		// they are stored and returned

		double TORA = 20.0;
		double TODA = 20.0;
		double ASDA = 20.0;
		double LDA = 20.0;
		double displacedThreshold = 20.0;
		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);

		int displacedThresholdI = 20;
		int clearway = 20;
		int stopway = 20;
		int resa = 20;
		int stripEnd = 20;
		int blastProtection = 20;
		int runwayStrip = 20;
		RunwayDetails rD = new RunwayDetails(displacedThresholdI, clearway, stopway, resa, stripEnd, blastProtection, runwayStrip);

		int runwayID = 14;
		String alignment = "L";
		Runway r = new Runway(rP, rD, runwayID, alignment);

		assertEquals("Should return parameter", runwayID, r.getRunwayID());
		assertEquals("Should return parameter", alignment, r.getAlignment());
		assertEquals("Should return paramter", rP, r.getRunwayParameters());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRunwayNullParameters() {
		int displacedThresholdI = 20;
		int clearway = 20;
		int stopway = 20;
		int resa = 20;
		int stripEnd = 20;
		int blastProtection = 20;
		int runwayStrip = 20;
		RunwayDetails rD = new RunwayDetails(displacedThresholdI, clearway, stopway, resa, stripEnd, blastProtection, runwayStrip);

		int runwayID = 14;
		String alignment = "L";
		new Runway(null, rD, runwayID, alignment);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRunwayNullDetails() {
		double TORA = 20.0;
		double TODA = 20.0;
		double ASDA = 20.0;
		double LDA = 20.0;
		double displacedThreshold = 20.0;
		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);

		int runwayID = 14;
		String alignment = "L";
		new Runway(rP, null, runwayID, alignment);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRunwayNullAlignment() {
		double TORA = 20.0;
		double TODA = 20.0;
		double ASDA = 20.0;
		double LDA = 20.0;
		double displacedThreshold = 20.0;
		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);

		int displacedThresholdI = 20;
		int clearway = 20;
		int stopway = 20;
		int resa = 20;
		int stripEnd = 20;
		int blastProtection = 20;
		int runwayStrip = 20;
		RunwayDetails rD = new RunwayDetails(displacedThresholdI, clearway, stopway, resa, stripEnd, blastProtection, runwayStrip);

		int runwayID = 14;
		new Runway(rP, rD, runwayID, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRunwayEmptyAlignment() {
		double TORA = 20.0;
		double TODA = 20.0;
		double ASDA = 20.0;
		double LDA = 20.0;
		double displacedThreshold = 20.0;
		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);

		int displacedThresholdI = 20;
		int clearway = 20;
		int stopway = 20;
		int resa = 20;
		int stripEnd = 20;
		int blastProtection = 20;
		int runwayStrip = 20;
		RunwayDetails rD = new RunwayDetails(displacedThresholdI, clearway, stopway, resa, stripEnd, blastProtection, runwayStrip);

		int runwayID = 14;
		new Runway(rP, rD, runwayID, "");
	}
}
