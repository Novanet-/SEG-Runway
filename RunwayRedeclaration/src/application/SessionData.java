package application;

import application.model.Airport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by Will on 21/02/2016.
 */
public class SessionData
{

	private ObservableList<Airport> airportList;


	public SessionData()
	{
		ArrayList<Airport> list = new ArrayList<Airport>();
		airportList = FXCollections.observableArrayList(list);

	}


	public SessionData(final ObservableList<Airport> airportList)
	{
		this.airportList = airportList;
	}


	public ObservableList<Airport> getAirportList()
	{
		return airportList;
	}


	public void setAirportList(final ObservableList<Airport> airportList)
	{
		this.airportList = airportList;
	}
}
