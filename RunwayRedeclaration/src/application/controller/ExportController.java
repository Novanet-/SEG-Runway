package application.controller;

import application.Main;
import application.model.Airport;
import application.model.Obstacle;
import application.model.Runway;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


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

    public void exportImage(Main mainApp, Canvas canvas) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Visualisation");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(mainApp.getMsStage());


        try {
            ImageIO.write(SwingFXUtils.fromFXImage(generateImage(canvas), null), "png", file);
            // TODO: handle these exceptions properly
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public WritableImage generateImage(Canvas canvas) {
        Double canvasWidth = canvas.getWidth();
        Double canvasHeight = canvas.getHeight();

        WritableImage wim = new WritableImage(
                canvasWidth.intValue(),
                canvasHeight.intValue());

        canvas.snapshot(null, wim);

        return wim;
    }

    public void exportPDF(Main mainApp, Canvas topDownCanvas, Canvas sideOnCanvas, String airportName, Runway runway) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export PDF");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showSaveDialog(mainApp.getMsStage());


        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();

            FileOutputStream fop = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, fop);
            document.open();

            PdfContentByte cb = writer.getDirectContent();

            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font fontBodyHead = new Font(Font.FontFamily.HELVETICA, 10, Font.UNDERLINE);
            Font fontBody = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            Font fontDesc = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            Font fontFoot = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

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


            Paragraph c1Para = new Paragraph(
                    "Obstacle name: " + runway.getObstacle().getName() + "\n" +
                            "Obstacle height: " + runway.getObstacle().getHeight() + "m\n" +
                            "Distance from Threshold: " + runway.getObstacle().getDisplacementPosition() + "m\n" +
                            "Distance from Centre Line: " + runway.getObstacle().getCentrePosition() + "m\n",
                    fontBody);

            PdfPCell c1 = new PdfPCell(c1Para);
            c1.setBorder(0);
            table.addCell(c1);

            Paragraph c2Para = new Paragraph(
                    "Displaced Threshold: " + runway.getDisplacedThreshold() + "m\n" +
                            "Angle of Slope: " + runway.getAngleOfSlope() + "°\n" +
                            "Blast Allowance: " + runway.getObstacle().getBlastProtection() + "m\n" +
                            "Stopway: " + runway.getStopway() + "m\n" +
                            "Strip Width: " + runway.getStripWidth() + "m\n" +
                            "Clear and Graded Area Width: " + runway.getCagWidth() + "m\n",
                    fontBody

            );

            PdfPCell c2 = new PdfPCell(c2Para);
            c2.setBorder(0);
            table.addCell(c2);

            firstPage.add(table);

            addEmptyLine(firstPage, 1);

            //TODO export actual recalculated values
            Paragraph toraCalc = new Paragraph("New TORA = 3902.0 - 0.0 - (12.51 * 50.0) - 60.0 - 0.0 = 3216.5m", fontBody);
            Paragraph todaCalc = new Paragraph("New TODA = 3902.0 - 0.0 - (12.51 * 50.0) - 60.0 - 0.0 = 3216.5m", fontBody);
            Paragraph asdaCalc = new Paragraph("New ASDA = 3902.0 - 0.0 - (12.51 * 50.0) - 60.0 - 0.0 = 3216.5m", fontBody);
            Paragraph ldaCalc = new Paragraph("New LDA = 3595.0 - 0.0 - 0.0 - 240.0 - 60.0 = 3295.0m", fontBody);

            firstPage.add(toraCalc);
            firstPage.add(todaCalc);
            firstPage.add(asdaCalc);
            firstPage.add(ldaCalc);

            addEmptyLine(firstPage, 2);

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
        } catch (IOException io) {
            io.printStackTrace();
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
