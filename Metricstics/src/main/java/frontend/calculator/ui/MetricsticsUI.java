package frontend.calculator.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MetricsticsUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MetricsticsUI.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 523, 460);
        stage.setTitle("Metricstics");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}