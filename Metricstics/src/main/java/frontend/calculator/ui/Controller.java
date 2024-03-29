package frontend.calculator.ui;


import backend.model.*;
import frontend.Utils.StringCheckingUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
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

    public void onMouseClick(MouseEvent mouseEvent) {
        String[] wordsList = new String[]{"Please", "enter", "some", "input", "data."};
        if(StringCheckingUtils.stringContainsItemFromList(mainDisplay.getText(), wordsList)){
            mainDisplay.setText("");
        }
        Button button = (Button) mouseEvent.getSource();
        String number = button.getText();
        addToDisplay(number);
    }

    public void onNextClick(MouseEvent mouseEvent){
        addToDisplay(DELIMITER);
    }

    // Removing a previously entered number
    // ex. if the numbers are 11 22 33, this actions will remove 33
    public void onDeleteClick(MouseEvent mouseEvent){
        String numbers = mainDisplay.getText();
        final int truncate = numbers.lastIndexOf(DELIMITER);
        numbers = numbers.substring(0, truncate).trim();
        mainDisplay.setText(numbers);
    }

    public void onClearClick(MouseEvent mouseEvent){
        mainDisplay.setText("");
        resultDisplay.setText("");
        previousInput = null;
    }

    public void onDotClick(MouseEvent mouseEvent){
        final String decimal = ".";
        addToDisplay(decimal);
    }

    public void onFunctionClick(MouseEvent mouseEvent){
        final Button funtionButton = (Button) mouseEvent.getSource();
        final String action = funtionButton.getText();

        updateInput();
        switch (action) {
            case "Min"    -> resultDisplay.setText("Min: "    + minimum.getOutputValue());
            case "Max"    -> resultDisplay.setText("Max: "    + maximum.getOutputValue());
            case "Mode"   -> resultDisplay.setText("Mode: "   + mode.getOutputList().stream().map(String::valueOf).collect(Collectors.joining(", ")));
            case "Mean"   -> resultDisplay.setText("Mean: "   + mean.getOutputValue());
            case "Median" -> resultDisplay.setText("Median: " + median.getOutputValue());
            case "Stdev"  -> resultDisplay.setText("Stdev: "  + stdev.getOutputValue());
            case "Mad"    -> resultDisplay.setText("Mad: "    + mad.getOutputValue());
        }
    }

    private void addToDisplay(String content){
        mainDisplay.setText(mainDisplay.getText() + content);
    }

    //Results of computation are cached, so updateInput can guard for it until the inputs get dirtied.
    private void updateInput(){
        // checking if there is input or not
        if(mainDisplay.getText().length() == 0){
            mainDisplay.setText("Please enter some input data.");
            return;
        }
        // retrieving inputs
        final String input = mainDisplay.getText().trim();
        //no need to update if the input is unchanged
        if (input.equals(previousInput)){
            return;
        }
        else{
            previousInput = input;
        }

        final String[] inputs = input.split(DELIMITER+"+");
        // parsing inputs
        final List<Double> numbers = Arrays.stream(inputs)
                .filter(str -> str.matches("-?\\d+\\.?\\d*|\\.\\d+")) //validate
                .map(Double::parseDouble) //convert
                .sorted(Comparator.comparingDouble(Double::doubleValue)) // sorting in ascending order
                .toList();

        //Deliver input.
        final Event inputEvent = new Event.EventBuilder().setInputs(numbers).build();
        head.update(inputEvent);
    }
}
