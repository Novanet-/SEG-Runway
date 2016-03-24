package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by jackclarke on 24/03/2016.
 */
public class ImportController {

    public Airport importAirport(Main mainApp, DocumentBuilderFactory dbf) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Airport");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(mainApp.getMsStage());

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(file);
            Element root = dom.getDocumentElement();

            String idString = getTextValue(root, "id");
            String name = getTextValue(root, "name");

            final Airport a = new Airport(Integer.parseInt(idString), name);

            Element runwaysNode = (Element) root.getElementsByTagName("runways").item(0);
            Element obstacleElement;

            for (int i = 0; i < runwaysNode.getElementsByTagName("runway").getLength(); i++) {
                Element runwayElement = (Element) runwaysNode.getElementsByTagName("runway").item(i);

                obstacleElement = (Element) runwayElement.getElementsByTagName("obstacle").item(0);

                int runwayID = Integer.parseInt(getTextValue(runwayElement, "id"));

                a.addRunway(new Runway(
                        runwayID,
                        getTextValue(runwayElement, "alignment"),
                        Double.parseDouble(getTextValue(runwayElement, "tora")),
                        Double.parseDouble(getTextValue(runwayElement, "toda")),
                        Double.parseDouble(getTextValue(runwayElement, "asda")),
                        Double.parseDouble(getTextValue(runwayElement, "lda")),
                        Double.parseDouble(getTextValue(runwayElement, "displaced_threshold"))
                ));

                if (obstacleElement != null) {
                    Runway runway = a.getRunways().get(a.getRunways().size() - 1);
                    runway.setObstacle(new Obstacle(
                            getTextValue(obstacleElement, "name"),
                            Double.parseDouble(getTextValue(obstacleElement, "height")),
                            Double.parseDouble(getTextValue(obstacleElement, "displacement_position")),
                            Double.parseDouble(getTextValue(obstacleElement, "centre_position")),
                            Double.parseDouble(getTextValue(obstacleElement, "blast_protection"))
                    ));
                }
            }

            return a;

            // TODO: handle these exceptions properly
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return null;
    }


    public Runway importRunway(Main mainApp, DocumentBuilderFactory dbf) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Runway");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(mainApp.getMsStage());

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(file);
            Element root = dom.getDocumentElement();

            String idS = getTextValue(root, "id");
            String alignment = getTextValue(root, "alignment");
            String toraS = getTextValue(root, "tora");
            String todaS = getTextValue(root, "toda");
            String asdaS = getTextValue(root, "asda");
            String ldaS = getTextValue(root, "lda");
            String dTS = getTextValue(root, "displaced_threshold");

            int id = Integer.parseInt(idS);
            int tora = Integer.parseInt(toraS);
            int toda = Integer.parseInt(todaS);
            int asda = Integer.parseInt(asdaS);
            int lda = Integer.parseInt(ldaS);
            int displacedThreshold = Integer.parseInt(dTS);

            final Runway r = new Runway(id, alignment, tora, toda, asda, lda, displacedThreshold);

            //TODO: handle obstacles

            return r;

            // TODO: handle these exceptions properly
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //TODO: Make obstacles import to Primary and Secondary runway
    public Obstacle importObstacle(Main mainApp, DocumentBuilderFactory dbf) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Obstacle");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(mainApp.getMsStage());

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(file);
            Element root = dom.getDocumentElement();

            String name = getTextValue(root, "name");
            String heightS = getTextValue(root, "height");
            String displacementPositionS = getTextValue(root, "displacement_position");
            String centrePositionS = getTextValue(root, "centre_position");
            String blastProtectionS = getTextValue(root, "blast_protection");

            double height = Double.parseDouble(heightS);
            double displacementPosition = Double.parseDouble(displacementPositionS);
            double centrePosition = Double.parseDouble(centrePositionS);
            double blastProtection = Double.parseDouble(blastProtectionS);

            Obstacle obstacle = new Obstacle(name, height, displacementPosition, centrePosition, blastProtection);

            return obstacle;

            // TODO: handle these exceptions properly
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String getTextValue(Element elem, String tag) {
        // TODO: no null checks!
        NodeList nodes = elem.getElementsByTagName(tag);
        Element e = (Element) nodes.item(0);
        return e.getFirstChild().getNodeValue();
    }

}