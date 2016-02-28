package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Airport
{

	private final int                    airportID;
	private final String                 airportName;
	private final ObservableList<Runway> runways;


	/**
	 * @param airportID
	 * @param airportName
	 */
	public Airport(final int airportID, final String airportName)
	{
		if (airportName == null || airportID < 0 || "".equals(airportName))
		{
			throw new IllegalArgumentException();
		}

		this.airportID = airportID;
		this.airportName = airportName;

		final Collection<Runway> list = new ArrayList<Runway>();
		runways = FXCollections.observableArrayList(list);
		createTestRunway(this);
	}


	private static void createTestRunway(final Airport airport)
	{
		final double primaryTORA = 3884.0;
		final double primaryTODA = 3962.0;
		final double primaryASDA = 3884.0;
		final double primaryLDA = 3884.0;
		final double primaryDisplacedThreshold = 0.0;

		final double secondaryTORA = 3902.0;
		final double secondaryTODA = 3902.0;
		final double secondaryASDA = 3902.0;
		final double secondaryLDA = 3595.0;
		final double secondaryDisplacedThreshold = 306.0;

		// TODO: work out what to do with displaced threshold and the other
		// paramters of RunwayDetails
		// TODO: once the previous task is done, create a new Runway
		// object with the paramters and details objects along with the
		// runway alignment, then add that object to the list of the given
		// airport

		final Runway primaryRunway = new Runway(0, 27 + "R", primaryTORA, primaryTODA, primaryASDA, primaryLDA, primaryDisplacedThreshold);
		final Runway secondaryRunway = new Runway(1, "09L", secondaryTORA, secondaryTODA, secondaryASDA, secondaryLDA, secondaryDisplacedThreshold);
		airport.addRunway(primaryRunway);
		airport.addRunway(secondaryRunway);

	}


	/**
	 * @return
	 */
	private int getAirportID()
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
	public final List<Runway> getRunways()
	{
		return Collections.unmodifiableList(runways);
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
		return airportID + " - " + airportName;
	}

}
