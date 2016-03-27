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
public class ImportController
{

	//TODO: Reject bad XML files
	//TEST COMMENT


	public Airport importAirport(Main mainApp, DocumentBuilderFactory dbf)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import Airport");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showOpenDialog(mainApp.getMsStage());

		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(file);
			Element root = dom.getDocumentElement();

			String idString = getTextValue(root, "id");
			String name = getTextValue(root, "name");

			final Airport a = new Airport(Integer.parseInt(idString), name);

			Element runwaysNode = (Element) root.getElementsByTagName("runways").item(0);

			for (int i = 0; i < runwaysNode.getElementsByTagName("runway").getLength(); i++)
			{
				Element runwayElement = (Element) runwaysNode.getElementsByTagName("runway").item(i);

				a.addRunway(importRunwayElement(runwayElement));
			}

			return a;

			// TODO: handle these exceptions properly
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (SAXException se)
		{
			se.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		return null;
	}


	public Runway importRunway(Main mainApp, DocumentBuilderFactory dbf)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import Runway");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showOpenDialog(mainApp.getMsStage());

		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(file);
			Element root = dom.getDocumentElement();

			return importRunwayElement(root);

			//TODO: handle obstacles

			// TODO: handle these exceptions properly
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (SAXException se)
		{
			se.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}


	public Runway importRunwayElement(Element runwayElement)
	{
		Runway runway = new Runway(Integer.parseInt(getTextValue(runwayElement, "id")), getTextValue(runwayElement, "alignment"),
				Double.parseDouble(getTextValue(runwayElement, "tora")), Double.parseDouble(getTextValue(runwayElement, "toda")),
				Double.parseDouble(getTextValue(runwayElement, "asda")), Double.parseDouble(getTextValue(runwayElement, "lda")),
				Double.parseDouble(getTextValue(runwayElement, "displaced_threshold")));

		Element obstacleElement = (Element) runwayElement.getElementsByTagName("obstacle").item(0);

		if (obstacleElement != null)
		{
			runway.setObstacle(importObstacleElement(obstacleElement));
		}

		return runway;
	}


	//TODO: Make obstacles import to Primary and Secondary runway
	public Obstacle importObstacle(Main mainApp, DocumentBuilderFactory dbf)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import Obstacle");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showOpenDialog(mainApp.getMsStage());

		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(file);
			Element root = dom.getDocumentElement();

			return importObstacleElement(root);

			// TODO: handle these exceptions properly
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (SAXException se)
		{
			se.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}


	public Obstacle importObstacleElement(Element obstacleElement)
	{
		return new Obstacle(getTextValue(obstacleElement, "name"), Double.parseDouble(getTextValue(obstacleElement, "height")),
				Double.parseDouble(getTextValue(obstacleElement, "displacement_position")), Double.parseDouble(getTextValue(obstacleElement, "centre_position")),
				Double.parseDouble(getTextValue(obstacleElement, "blast_protection")));
	}


	private String getTextValue(Element elem, String tag)
	{
		// TODO: no null checks!
		NodeList nodes = elem.getElementsByTagName(tag);
		Element e = (Element) nodes.item(0);
		return e.getFirstChild().getNodeValue();
	}

}