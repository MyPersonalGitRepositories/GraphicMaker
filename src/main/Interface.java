package main;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class Interface {

    @FXML
    private TextField parametr;

    @FXML
    private TextField step;

    @FXML
    private LineChart<Number, Number> lineChar;

    @FXML
    private TextField leftLimit;

    @FXML
    private TextField rightLimit;

    @FXML
    private Button build;

    @FXML
    private Button saveButton;

    private static double function(double x, double a){
        return (a*(Math.sqrt(Math.pow(x,3))));
    }

    @FXML
    void buildHandle(ActionEvent event) {
        double a;
        double step ;
        double leftLimit;
        double rightLimit;
        if(parametr.getText().equals("")||this.step.getText().equals("") || this.leftLimit.getText().equals("") || this.rightLimit.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Введіть коректні дані");
            alert.showAndWait();
            return;
        }
        try{
            rightLimit = Double.valueOf(this.rightLimit.getText());
            leftLimit = Double.valueOf(this.leftLimit.getText());
            if(rightLimit <= leftLimit)throw new IllegalArgumentException();
         a = Double.valueOf(parametr.getText());
        step = Double.valueOf(this.step.getText());
        if(step<0.001)throw new Exception();
        if(leftLimit < 0) throw new Exception();
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Введіть коректні дані");
            alert.showAndWait();
            return;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Введіть коректні дані");
            alert.showAndWait();
            return;
        }

        lineChar.getData().clear();
        XYChart.Series<Number,Number> series = new XYChart.Series<Number, Number>();
        for (double i = leftLimit; i <= rightLimit ; i+= step) {
            series.getData().add(new XYChart.Data<>(i, function(i,a)));
        }

        series.setName("Функція: f(x) = a*x^(3/2)" +
                "\nПараметр a = " + a + "\nКрок = " + step +
                "\nДіапазон = [ " + leftLimit + " ; " + rightLimit + " ]" );
        lineChar.getData().add(series);
    }


    @FXML
    void saveButtonHandle(ActionEvent event) {

        WritableImage image = lineChar.snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        try {
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException e) {

        }

    }

}
