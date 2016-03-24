package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by jackclarke on 24/03/2016.
 */
public class ExportController {

    public void exportAirport(Main mainApp, DocumentBuilderFactory dbf, Airport airport, int exportType) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Airport");
        fileChooser.setInitialFileName(airport.getAirportName());

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(mainApp.getMsStage());

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();

            dom.appendChild(exportAirportElement(dom, airport, exportType));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(dom);
            StreamResult newfile = new StreamResult(file);
            transformer.transform(source, newfile);

            // TODO: handle these exceptions properly
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (javax.xml.transform.TransformerException te) {
            te.printStackTrace();
        }
    }

    public Node exportAirportElement(Document dom, Airport airport, int exportType) {
        Element airportElement = dom.createElement("airport");

        airportElement.appendChild(getTextElements(dom, "id", Integer.toString(airport.getAirportID())));
        airportElement.appendChild(getTextElements(dom, "name", airport.getAirportName()));

        Element runwaysElement = dom.createElement("runways");

        switch (exportType) {
            case 0: //TODO: Fix this
                for (Runway runway : airport.getRunways()) {
                    runwaysElement.appendChild(exportRunwayElement(dom, runway, 0));
                }
                airportElement.appendChild(runwaysElement);
                break;
            case 1: //TODO: Fix this
                for (Runway runway : airport.getRunways()) {
                    runwaysElement.appendChild(exportRunwayElement(dom, runway, 1));
                }
                airportElement.appendChild(runwaysElement);
                break;
            case 2:
                break;
        }

        return airportElement;
    }

    public void exportRunway(Main mainApp, DocumentBuilderFactory dbf, String airportName, Runway runway, int exportType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Runway");
        fileChooser.setInitialFileName(airportName + " - " + runway.getAlignment());

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(mainApp.getMsStage());


        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();

            dom.appendChild(exportRunwayElement(dom, runway, exportType));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(dom);
            StreamResult newfile = new StreamResult(file);
            transformer.transform(source, newfile);

            // TODO: handle these exceptions properly
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (javax.xml.transform.TransformerException te) {
            te.printStackTrace();
        }
    }

    public Node exportRunwayElement(Document dom, Runway runway, int exportType) {

        Element runwayElement = dom.createElement("runway");

        runwayElement.appendChild(getTextElements(dom, "id", Integer.toString(runway.getRunwayID())));
        runwayElement.appendChild(getTextElements(dom, "alignment", runway.getAlignment()));
        runwayElement.appendChild(getTextElements(dom, "tora", Double.toString(runway.getTORA())));
        runwayElement.appendChild(getTextElements(dom, "toda", Double.toString(runway.getTODA())));
        runwayElement.appendChild(getTextElements(dom, "asda", Double.toString(runway.getASDA())));
        runwayElement.appendChild(getTextElements(dom, "lda", Double.toString(runway.getLDA())));
        runwayElement.appendChild(getTextElements(dom, "displaced_threshold", Double.toString(runway.getDisplacedThreshold())));

        switch (exportType) {
            case 0: //TODO: Fix this
                runwayElement.appendChild(exportObstacleElement(dom, runway.getObstacle()));
                break;
            case 1: //TODO: Fix this
                break;
        }

        return runwayElement;
    }

    public void exportObstacle(Main mainApp, DocumentBuilderFactory dbf, Obstacle obstacle) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Obstacle");
        fileChooser.setInitialFileName(obstacle.getName());

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(mainApp.getMsStage());


        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.newDocument();

            dom.appendChild(exportObstacleElement(dom, obstacle));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(dom);
            StreamResult newfile = new StreamResult(file);
            transformer.transform(source, newfile);

            // TODO: handle these exceptions properly
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (javax.xml.transform.TransformerException te) {
            te.printStackTrace();
        }
    }

    public Node exportObstacleElement(Document dom, Obstacle obstacle) {
        Element obstacleElement = dom.createElement("obstacle");

        obstacleElement.appendChild(getTextElements(dom, "name", obstacle.getName()));
        obstacleElement.appendChild(getTextElements(dom, "height", Double.toString(obstacle.getHeight())));
        obstacleElement.appendChild(getTextElements(dom, "displacement_position", Double.toString(obstacle.getDisplacementPosition())));
        obstacleElement.appendChild(getTextElements(dom, "centre_position", Double.toString(obstacle.getCentrePosition())));
        obstacleElement.appendChild(getTextElements(dom, "blast_protection", Double.toString(obstacle.getBlastProtection())));

        return obstacleElement;
    }

    private Node getTextElements(Document dom, String name, String value) {
        Element node = dom.createElement(name);
        node.appendChild(dom.createTextNode(value));
        return node;
    }
}
