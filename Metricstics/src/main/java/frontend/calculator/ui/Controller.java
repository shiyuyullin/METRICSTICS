package frontend.calculator.ui;


import backend.model.*;
import frontend.Utils.StringCheckingUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private TextArea mainDisplay;
    @FXML
    private TextArea resultDisplay;

    private String previousInput;

    final private String DELIMITER = " ";

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
        mainDisplay.setText(mainDisplay.getText() + DELIMITER);
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
        final int truncate = numbers.lastIndexOf(DELIMITER);
        numbers = numbers.substring(0, truncate);
        mainDisplay.setText(numbers);
    }

    public void onClearClick(MouseEvent mouseEvent){
        mainDisplay.setText("");
        resultDisplay.setText("");
        previousInput = null;
    }

    public void onDotClick(MouseEvent mouseEvent){
        insertDot();
    }

    public void onComputeClick(String action){
        updateInput();
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
    private void updateInput(){
        // checking if there is input or not
        if(mainDisplay.getText().length() == 0){
            mainDisplay.setText("Please enter some input data.");
            return;
        }
        // retrieving inputs
        final String input = mainDisplay.getText();
        //no need to update if the inputs are the same
        if (input.equals(previousInput)){
            return;
        }
        previousInput = input;

        final String[] inputArr = input.split(DELIMITER+"+");
        List<Double> inputs = new ArrayList<>();
        // parsing inputs
        for(String str : inputArr){
            try{
                final double temp = Double.parseDouble(str);
                inputs.add(temp);
            }
            catch (NumberFormatException e){
                System.err.println("skipping: " + str);
            }
        }
        // sorting in ascending order
        inputs.sort(Comparator.comparing(Double::doubleValue));

        // Clearing mainDisplay
        final Event inputEvent = new Event.EventBuilder().setInputs(inputs).build();
        //Deliver input.
        head.update(inputEvent);
    }

    public void onFunctionClick(MouseEvent mouseEvent){
        final Button funtionButton = (Button) mouseEvent.getSource();
        final String action = funtionButton.getText();
        onComputeClick(action);
    }
}