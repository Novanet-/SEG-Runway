package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;

public class Runway
{

    private final RunwayParameters runwayParameters;
    //    private final RunwayDetails runwayDetails;
    private final IntegerProperty runwayID;
    private final StringProperty alignment;
    private final ObservableList<Obstacle> obstacleArray;

    public Runway(final RunwayParameters runwayParameters, final RunwayDetails runwayDetails,
                  final IntegerProperty runwayID, final StringProperty alignment)
    {
        this.runwayParameters = runwayParameters;
        // this.runwayDetails = runwayDetails;
        this.runwayID = runwayID;
        this.alignment = alignment;
        final Collection<Obstacle> list = new ArrayList<Obstacle>();
        obstacleArray = FXCollections.observableArrayList(list);
    }

    /**
     * @return
     */
    public final int getRunwayID()
    {
        return runwayID.getValue();
    }

    /**
     * @return
     */
    public final String getAlignment()
    {
        return alignment.getValueSafe();
    }

    /**
     * @return
     */
    public final RunwayParameters getRunwayParameters()
    {
        return runwayParameters;
    }

    /**
     * @return
     */
//    public final RunwayDetails getRunwayDetails()
//    {
//        return runwayDetails;
//    }

    /**
     * @return
     */
    public final ObservableList<Obstacle> getObstacles()
    {
        return obstacleArray;
    }

    /**
     * @param obstacle
     */
    public final void addObstacle(Obstacle obstacle)
    {
        obstacleArray.add(obstacle);
    }

    /**
     * @return
     */
    @Override
    public String toString()
    {
        return "Runway{" + alignment.getValue() + '}';
    }
}