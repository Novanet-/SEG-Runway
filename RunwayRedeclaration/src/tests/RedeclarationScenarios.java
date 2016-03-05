package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import application.model.Obstacle;
import application.model.Redeclaration;
import application.model.Runway;

public class RedeclarationScenarios
{
	// TODO: finish and check values for scenarios 2-4
	@Test
	public void test1Scenario09L()
	{
		// Values from Scenario 1
		// 09L Take off away, land over

		double delta = 0.01;

		double TORA = 3902;
		double TODA = 3902;
		double ASDA = 3902;
		double LDA = 3595;
		double blastProtect = 300.0;
		double distFromThresh = -50; // Negative - before thresh
		double displacedThresh = 306;
		double obstHeight = 12.0;

		double newTORA = 3346;
		double newASDA = 3346;
		double newTODA = 3346;
		double newLDA = 2985;

		Runway r = new Runway(0, "09L", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);

		Runway newR = Redeclaration.redeclareParameters(r);

		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}

	@Test
	public void test1Scenario27R()
	{
		// Values from Scenario 1
		// 27R Take off towards, land towards
		
		double delta = 0.01;

		double TORA = 3902;
		double TODA = 3902;
		double ASDA = 3902;
		double LDA = 3595;
		double blastProtect = 300.0;
		double distFromThresh = 3646; // Positive - after thresh
		double displacedThresh = 306;
		double obstHeight = 12;
		
		double newTORA = 2986;
		double newASDA = 2986;
		double newTODA = 2986;
		double newLDA = 3346;
		
		Runway r = new Runway(0, "27R", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);
		
		Runway newR = Redeclaration.redeclareParameters(r);
		
		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}
	
	@Test
	public void test2Scenario09R()
	{
		// Values from Scenario 2
		// 09R Take off towards, land towards

		double delta = 0.01;

		double TORA = 3660;
		double TODA = 3660;
		double ASDA = 3660;
		double LDA = 3660;
		double blastProtect = 300.0;
		double distFromThresh = 2853;
		double displacedThresh = 307;
		double obstHeight = 25.0;

		double newTORA = 1850;
		double newASDA = 1850;
		double newTODA = 1850;
		double newLDA = 2553;

		Runway r = new Runway(0, "09R", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);

		Runway newR = Redeclaration.redeclareParameters(r);

		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}

	@Test
	public void test2Scenario27L()
	{
		// Values from Scenario 2
		// 27L Take off towards, land towards
		
		double delta = 0.01;

		double TORA = 3660;
		double TODA = 3660;
		double ASDA = 3660;
		double LDA = 3660;
		double blastProtect = 300.0;
		double distFromThresh = 500;
		double displacedThresh = 307;
		double obstHeight = 25;
		
		double newTORA = 2860;
		double newASDA = 2860;
		double newTODA = 2860;
		double newLDA = 1850;
		
		Runway r = new Runway(0, "27L", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);
		
		Runway newR = Redeclaration.redeclareParameters(r);
		
		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}
	
	@Test
	public void test3Scenario09R()
	{
		// Values from Scenario 3
		// 09R Take off away, land over

		double delta = 0.01;

		double TORA = 3660;
		double TODA = 3660;
		double ASDA = 3660;
		double LDA = 3353;
		double blastProtect = 300.0;
		double distFromThresh = -50; // Negative - before thresh
		double displacedThresh = 306;
		double obstHeight = 15.0;

		double newTORA = 2903;
		double newASDA = 2903;
		double newTODA = 2903;
		double newLDA = 2393;

		Runway r = new Runway(0, "09L", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);

		Runway newR = Redeclaration.redeclareParameters(r);

		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}

	@Test
	public void test3Scenario27L()
	{
		// Values from Scenario 1
		// 27R Take off towards, land towards
		
		double delta = 0.01;

		double TORA = 3902;
		double TODA = 3902;
		double ASDA = 3902;
		double LDA = 3595;
		double blastProtect = 300.0;
		double distFromThresh = 3646; // Positive - after thresh
		double displacedThresh = 306;
		double obstHeight = 12;
		
		double newTORA = 2986;
		double newASDA = 2986;
		double newTODA = 2986;
		double newLDA = 3346;
		
		Runway r = new Runway(0, "27R", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);
		
		Runway newR = Redeclaration.redeclareParameters(r);
		
		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}
	
	@Test
	public void test4Scenario09L()
	{
		// Values from Scenario 1
		// 09L Take off away, land over

		double delta = 0.01;

		double TORA = 3902;
		double TODA = 3902;
		double ASDA = 3902;
		double LDA = 3595;
		double blastProtect = 300.0;
		double distFromThresh = -50; // Negative - before thresh
		double displacedThresh = 306;
		double obstHeight = 12.0;

		double newTORA = 3346;
		double newASDA = 3346;
		double newTODA = 3346;
		double newLDA = 2985;

		Runway r = new Runway(0, "09L", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);

		Runway newR = Redeclaration.redeclareParameters(r);

		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}

	@Test
	public void test4Scenario27R()
	{
		// Values from Scenario 1
		// 27R Take off towards, land towards
		
		double delta = 0.01;

		double TORA = 3902;
		double TODA = 3902;
		double ASDA = 3902;
		double LDA = 3595;
		double blastProtect = 300.0;
		double distFromThresh = 3646; // Positive - after thresh
		double displacedThresh = 306;
		double obstHeight = 12;
		
		double newTORA = 2986;
		double newASDA = 2986;
		double newTODA = 2986;
		double newLDA = 3346;
		
		Runway r = new Runway(0, "27R", TORA, TODA, ASDA, LDA, displacedThresh);
		Obstacle o = new Obstacle("tiny_plane", obstHeight, distFromThresh, blastProtect);
		r.removeObstacle();
		r.addObstacle(o);
		
		Runway newR = Redeclaration.redeclareParameters(r);
		
		assertEquals(newTORA, newR.getTORA(), delta);
		assertEquals(newTODA, newR.getTODA(), delta);
		assertEquals(newASDA, newR.getASDA(), delta);
		assertEquals(newLDA, newR.getLDA(), delta);
	}
}