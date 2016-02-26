package application.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class Airport
{

	private final IntegerProperty        airportID;
	private final StringProperty         airportName;
	private final ObservableList<Runway> runways;


	/**
	 * @param airportID
	 * @param airportName
	 */
	public Airport(final IntegerProperty airportID, final StringProperty airportName)
	{
		if (airportID == null ||
				airportName == null ||
				airportID.getValue() < 0 ||
				airportName.getValue().equals(""))
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
		primaryParameters = new RunwayParameters(new SimpleDoubleProperty(primaryTORA), new SimpleDoubleProperty(primaryTODA), new SimpleDoubleProperty(primaryASDA),
				new SimpleDoubleProperty(primaryLDA), new SimpleDoubleProperty(primaryDisplacedThreshold));
		secondaryParameters = new RunwayParameters(new SimpleDoubleProperty(secondaryTORA), new SimpleDoubleProperty(secondaryTODA), new SimpleDoubleProperty(secondaryASDA),
				new SimpleDoubleProperty(secondaryLDA), new SimpleDoubleProperty(secondaryDisplacedThreshold));

		// TODO: work out what to do with displaced threshold and the other
		// paramters of RunwayDetails
		// TODO: once the previous task is done, create a new Runway
		// object with the paramters and details objects along with the
		// runway alignment, then add that object to the list of the given
		// airport

		Runway primaryRunway = new Runway(primaryParameters, null, new SimpleIntegerProperty(0), new SimpleStringProperty(27 + "R"));
		Runway secondaryRunway = new Runway(secondaryParameters, null, new SimpleIntegerProperty(1), new SimpleStringProperty("09L"));
		airport.getRunways().add(primaryRunway);
		airport.getRunways().add(secondaryRunway);

	}


	/**
	 * @return
	 */
	public final int getAirportID()
	{
		return airportID.getValue();
	}


	/**
	 * @return
	 */
	public final String getAirportName()
	{
		return airportName.getValueSafe();
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
		return airportID.getValue() + " - " + airportName.getValue();
	}

}
