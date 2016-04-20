package tests;

import org.junit.Test;

import application.model.Obstacle;

import static org.junit.Assert.assertEquals;

public class ObstacleModel
{
	@Test
	public void testObstacleProperties()
	{
		String name = "test2";
		double height = 25.0;
		double dispPos = 10.0;
		double centrePos = 100;
		double blastProt = 40;
		
		Obstacle o = new Obstacle(name, height, dispPos, centrePos, blastProt);
		
		assertEquals("Should return parameter", name, o.getName());
		assertEquals(height, o.getHeight(), 0.0);
		assertEquals(dispPos, o.getDisplacementPosition(), 0.0);
		assertEquals(centrePos, o.getCentrePosition(), 0.0);
		assertEquals(blastProt, o.getBlastProtection(), 0.0);
	}
}
