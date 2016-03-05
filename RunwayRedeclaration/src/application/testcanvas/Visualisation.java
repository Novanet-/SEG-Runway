package application.testcanvas;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by jackclarke on 05/03/2016.
 */
public class Visualisation extends Application {
    double tora = 2986.0;
    double toda = 2986.0;
    double asda = 2986.0;
    double lda = 3346.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Top Down Visualisation");
        Group root = new Group();
        Canvas canvas = new Canvas(720, 300);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        paintRunway(graphicsContext);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void paintRunway(GraphicsContext graphicsContext) {
        //graphicsContext.setFill(Color.rgb(214,218,219)); //Background colour
        graphicsContext.setFill(Color.rgb(77,77,77)); //Runway colour
        //graphicsContext.strokeLine(10, 10, 10, 50);
        graphicsContext.fillRect(50, 100, 620, 100);

        //Draw lines
        graphicsContext.setStroke(Color.WHITE);
        graphicsContext.setLineWidth(5);
        graphicsContext.strokeLine(70, 150, 100, 150);
        graphicsContext.strokeLine(130, 150, 160, 150);
        graphicsContext.strokeLine(190, 150, 220, 150);
        graphicsContext.strokeLine(250, 150, 280, 150);
        graphicsContext.strokeLine(310, 150, 340, 150);
        graphicsContext.strokeLine(370, 150, 400, 150);
        graphicsContext.strokeLine(430, 150, 460, 150);
        graphicsContext.strokeLine(490, 150, 520, 150);
        graphicsContext.strokeLine(550, 150, 580, 150);
        graphicsContext.strokeLine(610, 150, 640, 150);

        //Calculate TORA, TODA, ASDA, LDA
        double pixelRatio = 600.0/tora;
        int toraPixel = 600;
        int todaPixel = (int) Math.round(toda * pixelRatio);
        int asdaPixel = (int) Math.round(asda * pixelRatio);
        int ldaPixel = (int) Math.round(lda * pixelRatio);

        System.out.println(pixelRatio);

        //draw TORA
        graphicsContext.setStroke(Color.rgb(255,138,138));
        graphicsContext.strokeLine(50, 210, 50+toraPixel, 210);

        //draw TODA
        graphicsContext.setStroke(Color.rgb(255,190,50));
        graphicsContext.strokeLine(50, 220, 50+todaPixel, 220);

        //draw ASDA
        graphicsContext.setStroke(Color.rgb(255,240,40));
        graphicsContext.strokeLine(50, 230, 50+asdaPixel, 230);

        //draw LDA
        graphicsContext.setStroke(Color.rgb(180,225,35));
        graphicsContext.strokeLine(50, 240, 50+ldaPixel, 240);
    }

}
