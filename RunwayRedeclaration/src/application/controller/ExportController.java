package application.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

/**
 * Created by jackclarke on 24/03/2016.
 */
public class ExportController
{

	private static void addEmptyLine(Paragraph paragraph, int number)
	{
		for (int i = 0; i < number; i++)
		{
			paragraph.add(new Paragraph(" "));
		}
	}


	public void exportAirport(Main mainApp, DocumentBuilderFactory dbf, Airport airport, int exportType)
	{

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export Airport");
		fileChooser.setInitialFileName(airport.getAirportName());

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showSaveDialog(mainApp.getMsStage());

		try
		{
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
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		catch (javax.xml.transform.TransformerException te)
		{
			te.printStackTrace();
		}
	}


	public Node exportAirportElement(Document dom, Airport airport, int exportType)
	{
		Element airportElement = dom.createElement("airport");

		airportElement.appendChild(getTextElements(dom, "id", Integer.toString(airport.getAirportID())));
		airportElement.appendChild(getTextElements(dom, "name", airport.getAirportName()));

		Element runwaysElement = dom.createElement("runways");

		switch (exportType)
		{
			case 0:
				for (Runway runway : airport.getRunways())
				{
					runwaysElement.appendChild(exportRunwayElement(dom, runway, 0));
				}
				airportElement.appendChild(runwaysElement);
				break;
			case 1:
				for (Runway runway : airport.getRunways())
				{
					runwaysElement.appendChild(exportRunwayElement(dom, runway, 1));
				}
				airportElement.appendChild(runwaysElement);
				break;
			case 2:
				break;
		}

		return airportElement;
	}


	public void exportRunway(Main mainApp, DocumentBuilderFactory dbf, String airportName, Runway runway, int exportType)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export Runway");
		fileChooser.setInitialFileName(airportName + " - " + runway.getAlignment());

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showSaveDialog(mainApp.getMsStage());

		try
		{
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
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		catch (javax.xml.transform.TransformerException te)
		{
			te.printStackTrace();
		}
	}


	public Node exportRunwayElement(Document dom, Runway runway, int exportType)
	{

		Element runwayElement = dom.createElement("runway");

		runwayElement.appendChild(getTextElements(dom, "id", Integer.toString(runway.getRunwayID())));
		runwayElement.appendChild(getTextElements(dom, "alignment", runway.getAlignment()));
		runwayElement.appendChild(getTextElements(dom, "tora", Double.toString(runway.getTORA())));
		runwayElement.appendChild(getTextElements(dom, "toda", Double.toString(runway.getTODA())));
		runwayElement.appendChild(getTextElements(dom, "asda", Double.toString(runway.getASDA())));
		runwayElement.appendChild(getTextElements(dom, "lda", Double.toString(runway.getLDA())));
		runwayElement.appendChild(getTextElements(dom, "displaced_threshold", Double.toString(runway.getDisplacedThreshold())));

		switch (exportType)
		{
			case 0:
				runwayElement.appendChild(exportObstacleElement(dom, runway.getObstacle()));
				break;
			case 1:
				break;
		}

		return runwayElement;
	}


	public void exportObstacle(Main mainApp, DocumentBuilderFactory dbf, Obstacle obstacle)
	{

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export Obstacle");
		fileChooser.setInitialFileName(obstacle.getName());

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showSaveDialog(mainApp.getMsStage());

		try
		{
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
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		catch (javax.xml.transform.TransformerException te)
		{
			te.printStackTrace();
		}
	}


	public Node exportObstacleElement(Document dom, Obstacle obstacle)
	{
		Element obstacleElement = dom.createElement("obstacle");

		obstacleElement.appendChild(getTextElements(dom, "name", obstacle.getName()));
		obstacleElement.appendChild(getTextElements(dom, "height", Double.toString(obstacle.getHeight())));
		obstacleElement.appendChild(getTextElements(dom, "displacement_position", Double.toString(obstacle.getDisplacementPosition())));
		obstacleElement.appendChild(getTextElements(dom, "centre_position", Double.toString(obstacle.getCentrePosition())));
		obstacleElement.appendChild(getTextElements(dom, "blast_protection", Double.toString(obstacle.getBlastProtection())));

		return obstacleElement;
	}


	private Node getTextElements(Document dom, String name, String value)
	{
		Element node = dom.createElement(name);
		node.appendChild(dom.createTextNode(value));
		return node;
	}


	public void exportImage(Main mainApp, Canvas canvas)
	{

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Visualisation");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showSaveDialog(mainApp.getMsStage());

		try
		{
			ImageIO.write(SwingFXUtils.fromFXImage(generateImage(canvas), null), "png", file);
			// TODO: handle these exceptions properly
		}
		catch (NumberFormatException nfe)
		{
			nfe.printStackTrace();
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}


	public WritableImage generateImage(Canvas canvas)
	{
		Double canvasWidth = canvas.getWidth();
		Double canvasHeight = canvas.getHeight();

		WritableImage wim = new WritableImage(canvasWidth.intValue(), canvasHeight.intValue());

		canvas.snapshot(null, wim);

		return wim;
	}


	public void exportPDF(Main mainApp, Canvas topDownCanvas, Canvas sideOnCanvas, String airportName, Runway runway)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export PDF");

		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"), new FileChooser.ExtensionFilter("All Files", "*.*"));
		File file = fileChooser.showSaveDialog(mainApp.getMsStage());

		try
		{
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();

			FileOutputStream fop = new FileOutputStream(file);
			PdfWriter writer = PdfWriter.getInstance(document, fop);
			document.open();

			Font fontHeader = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
			Font fontBodyHead = new Font(Font.FontFamily.HELVETICA, 10, Font.UNDERLINE);
			Font fontBody = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
			Font fontDesc = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
			Font fontFoot = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

			Font toraColor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(255, 138, 138));
			Font todaColor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(255, 190, 50));
			Font asdaColor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(255, 240, 40));
			Font ldaColor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(180, 225, 35));
			Font dtColor = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, new BaseColor(150, 210, 255));
			Font obstacleColor = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(179, 45, 0));

			Paragraph firstPage = new Paragraph();
			Paragraph description;
			// We add one empty line
			// Lets write a big header
			firstPage.add(new Paragraph("Redeclaration for " + airportName, fontHeader));

			addEmptyLine(firstPage, 1);

			firstPage.add(new Paragraph("Calculations for runway " + runway.getAlignment() + "\n", fontBodyHead));

			addEmptyLine(firstPage, 1);

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100);

			Paragraph c1Para = new Paragraph();
			c1Para.add(new Chunk("Obstacle\n", obstacleColor));
			c1Para.add(new Chunk("Name: " + runway.getObstacle().getName() + "\n" +
					"Height: " + runway.getObstacle().getHeight() + "m\n" +
					"Distance from Threshold: " + runway.getObstacle().getDisplacementPosition() + "m\n" +
					"Distance from Centre Line: " + runway.getObstacle().getCentrePosition() + "m\n", fontBody));

			PdfPCell c1 = new PdfPCell(c1Para);
			c1.setBorder(0);
			table.addCell(c1);

			Paragraph c2Para = new Paragraph("Displaced Threshold: " + runway.getDisplacedThreshold() + "m\n" +
					"Angle of Slope: " + Runway.getAngleOfSlope() + "°\n" +
					"Blast Allowance: " + runway.getObstacle().getBlastProtection() + "m\n" +
					"Stopway: " + Runway.getStopway() + "m\n" +
					"Strip Width: " + Runway.getStripWidth() + "m\n" +
					"Clear and Graded Area Width: " + Runway.getCagWidth() + "m\n", fontBody

			);

			PdfPCell c2 = new PdfPCell(c2Para);
			c2.setBorder(0);
			table.addCell(c2);

			firstPage.add(table);

			Runway recalcRunway = runway.redeclare();

			firstPage.add(new Paragraph("Recalculated values", fontBodyHead));

			Paragraph recalcValues = new Paragraph();

			recalcValues.add(new Chunk("TORA", toraColor));
			recalcValues.add(new Chunk(" = " +
					runway.getTORA() +
					" - " +
					runway.getObstacle().getBlastProtection() +
					" - " +
					runway.getObstacle().getDisplacementPosition() +
					" - " +
					runway.getDisplacedThreshold() +
					" = " +
					recalcRunway.getTORA() +
					"m\n", fontBody));
			recalcValues.add(new Chunk("TODA", todaColor));
			recalcValues.add(new Chunk(" = " +
					runway.getTORA() +
					" - " +
					runway.getObstacle().getBlastProtection() +
					" - " +
					runway.getObstacle().getDisplacementPosition() +
					" - " +
					runway.getDisplacedThreshold() +
					" + " +
					Runway.getStopway() +
					" = " +
					recalcRunway.getTODA() +
					"m\n", fontBody));
			recalcValues.add(new Chunk("ASDA", asdaColor));
			recalcValues.add(new Chunk(" = " +
					runway.getTORA() +
					" - " +
					runway.getObstacle().getBlastProtection() +
					" - " +
					runway.getObstacle().getDisplacementPosition() +
					" - " +
					runway.getDisplacedThreshold() +
					" + " +
					Runway.getClearway() +
					" = " +
					recalcRunway.getASDA() +
					"m\n", fontBody));
			recalcValues.add(new Chunk("LDA", ldaColor));
			recalcValues.add(new Chunk(" = " +
					runway.getLDA() +
					" - " +
					runway.getObstacle().getDisplacementPosition() +
					" - " +
					Runway.getStripEnd() +
					" - (" +
					runway.getObstacle().getHeight() +
					" * " +
					Runway.getAngleOfSlope() +
					") = " +
					recalcRunway.getLDA() +
					"m\n", fontBody));

			recalcValues.add(new Chunk("Displacement Threshold ", dtColor));
			recalcValues.add(new Chunk("may also be shown in visualisation", fontDesc));
			firstPage.add(recalcValues);

			addEmptyLine(firstPage, 1);

			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			ImageIO.write(SwingFXUtils.fromFXImage(generateImage(topDownCanvas), null), "png", byteOutput);
			Image topView = Image.getInstance(byteOutput.toByteArray());
			topView.scaleToFit(520, 400);
			firstPage.add(topView);
			description = new Paragraph("Top-down visualisation", fontDesc);
			description.setAlignment(1);
			firstPage.add(description);

			byteOutput.reset();
			ImageIO.write(SwingFXUtils.fromFXImage(generateImage(sideOnCanvas), null), "png", byteOutput);
			Image sideView = Image.getInstance(byteOutput.toByteArray());
			sideView.scaleToFit(520, 400);
			firstPage.add(sideView);
			description = new Paragraph("Side-on visualisation", fontDesc);
			description.setAlignment(1);
			firstPage.add(description);

			addEmptyLine(firstPage, 1);
			// Will create: Report generated by: _name, _date
			firstPage.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					fontFoot));

			document.add(firstPage);

			// Start a new page
			//document.newPage();

			document.close();
			fop.flush();
			fop.close();
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
		catch (DocumentException de)
		{
			de.printStackTrace();
		}
	}
}
