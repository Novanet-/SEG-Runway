//package tests;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import application.model.Runway;
//import application.model.RunwayParameters;
//
//public class RunwayModel
//{
//
//	@Test
//	public void testRunwayProperties()
//	{
//		// Exact values don't matter, just want to ensure
//		// they are stored and returned
//
//		double TORA = 20.0;
//		double TODA = 20.0;
//		double ASDA = 20.0;
//		double LDA = 20.0;
//		double displacedThreshold = 20.0;
//		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);
//
//		int runwayID = 14;
//		String alignment = "L";
//		Runway r = new Runway(rP, runwayID, alignment);
//
//		assertEquals("Should return parameter", runwayID, r.getRunwayID());
//		assertEquals("Should return parameter", alignment, r.getAlignment());
//		assertEquals("Should return paramter", rP, r.getRunwayParameters());
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void testRunwayNullParameters() {
//		int runwayID = 14;
//		String alignment = "L";
//		new Runway(null, runwayID, alignment);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void testRunwayNullAlignment() {
//		double TORA = 20.0;
//		double TODA = 20.0;
//		double ASDA = 20.0;
//		double LDA = 20.0;
//		double displacedThreshold = 20.0;
//		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);
//
//		int runwayID = 14;
//		new Runway(rP, runwayID, null);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void testRunwayEmptyAlignment() {
//		double TORA = 20.0;
//		double TODA = 20.0;
//		double ASDA = 20.0;
//		double LDA = 20.0;
//		double displacedThreshold = 20.0;
//		RunwayParameters rP = new RunwayParameters(TORA, TODA, ASDA, LDA, displacedThreshold);
//
//		int runwayID = 14;
//		new Runway(rP, runwayID, "");
//	}
//}
