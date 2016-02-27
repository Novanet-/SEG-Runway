package application.model;

import java.util.ArrayList;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Airport
{

	private final int airportID;
	private final String airportName;
	private final ObservableList<Runway> runways;


	/**
	 * @param airportID
	 * @param airportName
	 */
	public Airport(final int airportID, final String airportName)
	{
		if (airportName == null || airportID < 0 || airportName.equals(""))
			throw new IllegalArgumentException();
		
		this.airportID = airportID;
		this.airportName = airportName;
		
		final Collection<Runway> list = new ArrayList<Runway>();
		runways = FXCollections.observableArrayList(list);
		createTestRunway(this);
	}


	private void createTestRunway(final Airport airport)
	{
		double primaryTORA = 3884;
		double primaryTODA = 3962;
		double primaryASDA = 3884;
		double primaryLDA = 3884;
		double primaryDisplacedThreshold = 0;

		double secondaryTORA = 3902;
		double secondaryTODA = 3902;
		double secondaryASDA = 3902;
		double secondaryLDA = 3595;
		double secondaryDisplacedThreshold = 306;

		RunwayParameters primaryParameters, secondaryParameters;
		primaryParameters = new RunwayParameters(primaryTORA, primaryTODA, primaryASDA, primaryLDA,
				primaryDisplacedThreshold);
		secondaryParameters = new RunwayParameters(secondaryTORA, secondaryTODA, secondaryASDA, secondaryLDA,
				secondaryDisplacedThreshold);

		// TODO: work out what to do with displaced threshold and the other
		// paramters of RunwayDetails
		// TODO: once the previous task is done, create a new Runway
		// object with the paramters and details objects along with the
		// runway alignment, then add that object to the list of the given
		// airport

		Runway primaryRunway = new Runway(primaryParameters, null, 0, Integer.toString(27) + "R");
		Runway secondaryRunway = new Runway(secondaryParameters, null, 1, "09L");
		airport.getRunways().add(primaryRunway);
		airport.getRunways().add(secondaryRunway);

	}


	/**
	 * @return
	 */
	public final int getAirportID()
	{
		return airportID;
	}


	/**
	 * @return
	 */
	public final String getAirportName()
	{
		return airportName;
	}


	/**
	 * @return
	 */
	public final ObservableList<Runway> getRunways()
	{
		return runways;
	}


	/**
	 * @param runway
	 */
	public final void addRunway(Runway runway)
	{
		runways.add(runway);
	}


	/**
	 * @return
	 */
	@Override
	public final String toString()
	{
		return getAirportID() + " - " + getAirportName();
	}

}
