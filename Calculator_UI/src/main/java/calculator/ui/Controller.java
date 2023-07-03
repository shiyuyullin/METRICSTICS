package calculator.ui;


import Utils.StringCheckingUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private TextArea mainDisplay;

    private List<Double> inputs;

    private String action;

    public void insertNumber(String number) {
        mainDisplay.setText(mainDisplay.getText() + number);
    }

    public void insertSpace(){
        mainDisplay.setText(mainDisplay.getText() + " ");
    }

    public void insertDot(){
        mainDisplay.setText(mainDisplay.getText() + ".");
    }

    public void onMouseClick(MouseEvent mouseEvent) {
        String[] wordsList = new String[]{"Please", "enter", "some", "input", "data."};
        if(StringCheckingUtils.stringContainsItemFromList(mainDisplay.getText(), wordsList)){
            mainDisplay.setText("");
        }
        Button button = (Button) mouseEvent.getSource();
        String number = button.getText();
        insertNumber(number);
    }

    public void onNextClick(MouseEvent mouseEvent){
        insertSpace();
    }

    // Removing a previously entered number
    // ex. if the numbers are 11 22 33, this actions will remove 33
    public void onDeleteClick(MouseEvent mouseEvent){
        String numbers = mainDisplay.getText();
        String[] numbersArr = numbers.split(" ");
        StringBuilder newNumbers = new StringBuilder();
        for(int i = 0; i < numbersArr.length-1; i++){
            if(!numbersArr[i].equals(" ")){
                newNumbers.append(numbersArr[i]).append(" ");
            }
        }
        mainDisplay.setText(newNumbers.toString());
    }

    public void onClearClick(MouseEvent mouseEvent){
        mainDisplay.setText("");
    }

    public void onDotClick(MouseEvent mouseEvent){
        insertDot();
    }

    public void onComputeClick(MouseEvent mouseEvent){
        // checking if there is input or not
        if(mainDisplay.getText().length() == 0){
            mainDisplay.setText("Please enter some input data.");
        }
        // retrieving inputs
        String input = mainDisplay.getText();
        String[] inputArr = input.split(" ");
        inputs = new ArrayList<>();
        // parsing inputs
        for(String str : inputArr){
            double temp;
            try{
                temp = Double.parseDouble(str);
                inputs.add(temp);
            }
            catch (NumberFormatException e){
                System.out.println("skipping");
                continue;
            }
        }
        for(Double d : inputs){
            System.out.println(d);
        }
    }
}