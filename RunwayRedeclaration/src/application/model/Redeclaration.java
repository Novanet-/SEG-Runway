package application.model;

public class Redeclaration {

	public static Runway redeclareParameters(Runway runway) {
		final RunwayParameters runwayParameters = runway.getRunwayParameters();
		final Obstacle obstacle = runway.getObstacles().get(0);

		// if takeOffAway() and landingOver()
		// double newTORA =
		// runwayParameters.getTORA() - obstacle.getBlastProtection() -
		// ((Obstacle) obstacle).getDistanceFromThreshold() -
		// runwayParameters.getDisplacedThreshold();
		// double newTODA = newTORA + STOPWAY;
		// double newASDA = newTORA + CLEARWAY;
		// double newLDA = runwayParameters.getLDA() -
		// obstacle.getDistanceFromThreshold() - stripEnd() -
		// slopeCalculation();

		// if takeOffTowards() and landingTowards()
		// double newTORA = obstacle.getDistanceFromThreshold() -
		// slopeCalculation() - stripEnd();
		// double newTODA = newTORA;
		// double newASDA = newTORA;
		// double newLDA = obstacle.getDistanceFromThreshold() -
		// obstacle.getRESA - stripEnd();

		// 09L (Take Off Away, Landing Over):
		// TORA = Original TORA - Blast Protection - Distance from Threshold -
		// Displaced Threshold
		// = 3902 - 300 - -50 - 306
		// = 3346
		// ASDA = (R) TORA + STOPWAY
		// = 3346
		// TODA = (R) TORA + CLEARWAY
		// = 3346
		// LDA = Original LDA - Distance from Threshold – Strip End - Slope
		// Calculation
		// = 3595 - -50 - 60 - 12*50
		// = 2985
		// 27R (Take Off Towards, Landing Towards)
		// TORA = Distance from Threshold - Slope Calculation - Strip End
		// = 3646 - 60 - 12*50
		// = 2986
		// ASDA = (R) TORA
		// = 2986
		// TODA = (R) TORA
		// = 2986
		// LDA = Distance from Threshold - RESA - Strip End
		// = 3646 - 240 - 60
		// = 3346
		return null;
	}

}
