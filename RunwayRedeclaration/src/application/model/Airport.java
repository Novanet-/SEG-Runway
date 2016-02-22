package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class Airport
{

    private final IntegerProperty airportID;
    private final StringProperty airportName;
    private final ObservableList<Runway> runways;

    /**
     * @param airportID
     * @param airportName
     */
    public Airport(final IntegerProperty airportID, final StringProperty airportName)
    {
        this.airportID = airportID;
        this.airportName = airportName;
        final Collection<Runway> list = new ArrayList<Runway>();
        runways = FXCollections.observableArrayList(list);
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
