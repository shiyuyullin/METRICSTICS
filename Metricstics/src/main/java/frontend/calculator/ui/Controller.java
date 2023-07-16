package frontend.calculator.ui;


import backend.model.*;
import frontend.Utils.StringCheckingUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import backend.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private TextArea mainDisplay;
    @FXML
    private TextArea resultDisplay;

    private List<Double> inputs;

    private String action;

    ComputeObserver head;
    ComputeObserver minimum;
    ComputeObserver maximum;
    ComputeObserver mode;
    ComputeObserver median;
    ComputeObserver mean;
    ComputeObserver mad;
    ComputeObserver stdev;

    // This method will be called after the application is initialized (started)
    @FXML
    public void initialize(){
        //Initialize the modules.
        head = new ComputeEmpty();
        minimum = new ComputeMinimum();
        maximum = new ComputeMaximum();
        mode = new ComputeMode();
        median = new ComputeMedian();
        mean = new ComputeMean();
        mad = new ComputeMeanAbsoluteDeviation();
        stdev = new ComputeStandardDeviation();

        //Attach Observer chain.
        head.addObserver(minimum);
        head.addObserver(maximum);
        head.addObserver(mode);
        head.addObserver(median);
        head.addObserver(mean);
        mean.addObserver(mad);
        mean.addObserver(stdev);

        // make the window for displaying results read-only
        resultDisplay.setEditable(false);
    }

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
        resultDisplay.setText("");
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
            }
        }
        // sorting in ascending order
        inputs.sort(Comparator.comparing(Double::doubleValue));
        for(Double d : inputs){
            System.out.println(d);
        }
        // Clearing mainDisplay
        mainDisplay.setText("");
        Event inputEvent = new Event.EventBuilder().setInputs(inputs).build();
        //Deliver input.
        head.update(inputEvent);
        switch (action) {
            case "Min" -> resultDisplay.setText("Min: " + minimum.getOutputValue());
            case "Max" -> resultDisplay.setText("Max: " + maximum.getOutputValue());
            case "Mode" -> resultDisplay.setText("Mode: " + mode.getOutputList().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            case "Mean" -> resultDisplay.setText("Mean: " + mean.getOutputValue());
            case "Median" -> resultDisplay.setText("Median: " + median.getOutputValue());
            case "Stdev" -> resultDisplay.setText("Stdev: " + stdev.getOutputValue());
            case "Mad" -> resultDisplay.setText("Mad: " + mad.getOutputValue());
        }
    }

    public void onFunctionClick(MouseEvent mouseEvent){
        Button funtionButton = (Button) mouseEvent.getSource();
        action = funtionButton.getText();
    }
}