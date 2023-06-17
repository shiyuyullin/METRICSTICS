package calculator.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import helper.CustomMath;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        CustomMath math = CustomMath.getInstance();
        System.out.println(math.maximum(3,4));
    }
}