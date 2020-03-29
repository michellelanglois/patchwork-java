package ui;

import exceptions.IllegalQuiltSizeException;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Quilt;

/*
Represents the control panel to create a new quilt
 */

public class NewQuiltControlsUI extends GridPane {

    QuiltAppGUI quiltAppGUI;
    Spinner<Integer> blocksAcross;
    Spinner<Integer> blocksDown;
    Spinner<Double> blockSize;
    Button startNewButton;
    Button resetButton;
    Label quiltSizeLabel;
    Label blocksAcrossLabel;
    Label blocksDownLabel;
    Label blocksSizeLabel;

    // EFFECTS: creates a new quilt control pane with component parts and associated event listeners
    public NewQuiltControlsUI(QuiltAppGUI quiltAppGUI) {
        this.quiltAppGUI = quiltAppGUI;
        this.blocksAcross = initializeIntSpinner();
        this.blocksDown = initializeIntSpinner();
        this.blockSize = initializeDoubleSpinner();
        this.startNewButton = initializeStartButton();
        this.resetButton = initializeResetButton();
        this.quiltSizeLabel = new Label();
        quiltSizeLabel.getStyleClass().add("quilt-size-label");
        this.blocksAcrossLabel = new Label("Blocks across:");
        this.blocksDownLabel = new Label("Blocks down:");
        this.blocksSizeLabel = new Label("Block size (inches): ");

        initializeQuiltControlsLayout();
    }

    // MODIFIES: this
    // EFFECTS: creates the layout for the new quilt control pane
    private void initializeQuiltControlsLayout() {
        GridPane.setConstraints(blocksAcrossLabel, 0, 0);
        GridPane.setConstraints(blocksDownLabel, 0, 1);
        GridPane.setConstraints(blocksSizeLabel, 0, 2);
        GridPane.setConstraints(blocksAcross, 1, 0);
        GridPane.setConstraints(blocksDown, 1, 1);
        GridPane.setConstraints(blockSize, 1, 2);
        GridPane.setConstraints(quiltSizeLabel, 3, 1, 2, 2, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(startNewButton, 3, 0, 1, 1, HPos.RIGHT, VPos.BASELINE);
        GridPane.setConstraints(resetButton, 4, 0, 1, 1, HPos.LEFT, VPos.BASELINE);

        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(30);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(15);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(15);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(15);

        this.getColumnConstraints().addAll(column0, column1, column2, column3, column4);
        this.getChildren().addAll(blocksAcross, blocksDown, blockSize, startNewButton, resetButton, blocksAcrossLabel,
                blocksDownLabel, blocksSizeLabel, quiltSizeLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the start new quilt button
    private Button initializeStartButton() {
        Button startNewButton = new Button("Create");
        startNewButton.setTooltip(new Tooltip("Create a new quilt of chosen size"));

        startNewButton.setOnAction(event -> {
            try {
                Quilt quilt = new Quilt(blocksAcross.getValue(), blocksDown.getValue(), blockSize.getValue());
                quiltAppGUI.setQuilt(quilt);
                quiltAppGUI.getQuiltGrid().initializeQuiltGrid();
                quiltAppGUI.getQuiltGrid().renderQuilt();
                disableControls();
            } catch (IllegalQuiltSizeException e) {
                event.consume();
            }
        });

        return startNewButton;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the reset new quilt button, which is initially disabled (until a new quilt is made)
    private Button initializeResetButton() {
        Button resetButton = new Button("Restart");
        resetButton.setTooltip(new Tooltip("Allows you to create a different quilt"));
        resetButton.setDisable(true);
        resetButton.setOnAction(event -> {
            quiltAppGUI.setQuilt(null);
            quiltAppGUI.getQuiltGrid().clearQuiltGrid();
            enableControls();
        });

        return resetButton;
    }

    // MODIFIES: this
    // EFFECTS: enables controls to create a new quilt
    private void enableControls() {
        blocksAcross.setDisable(false);
        blocksDown.setDisable(false);
        blockSize.setDisable(false);
        startNewButton.setDisable(false);
        resetButton.setDisable(true);
    }

    // MODIFIES: this
    // EFFECTS: disables controls to create a new quilt
    private void disableControls() {
        blocksAcross.setDisable(true);
        blocksDown.setDisable(true);
        blockSize.setDisable(true);
        startNewButton.setDisable(true);
        resetButton.setDisable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates a spinner capable of updating the quilt size label
    private Spinner<Integer> initializeIntSpinner() {
        Spinner<Integer> spinner = new Spinner<>(1, 25, 1);
        spinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                calculateSize();
            }
        });

        return spinner;
    }

    // MODIFIES: this
    // EFFECTS: creates a spinner capable of updating the quilt size label
    private Spinner<Double> initializeDoubleSpinner() {
        Spinner<Double> spinner = new Spinner<>(3.0, 18.0, 3.0, 1.5);
        spinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                calculateSize();
            }
        });

        return spinner;
    }

    // MODIFIES: this
    // EFFECTS: calculates total size of quilt so this can be displayed by GUI
    private void calculateSize() {
        double width = blocksAcross.getValue() * blockSize.getValue() + (Quilt.BINDING_WIDTH - 0.5) * 2;
        double height = blocksDown.getValue() * blockSize.getValue() + (Quilt.BINDING_WIDTH - 0.5) * 2;
        this.quiltSizeLabel.setText("Total size: \n" + width + "\" x " + height + "\"");
    }

    // MODIFIES: this
    // EFFECTS: updates information displayed in controls based on loaded quilt
    public void updateQuiltInfoOnLoad() {
        Quilt quilt = quiltAppGUI.getQuilt();
        this.blocksAcross.getValueFactory().setValue(quilt.getNumBlocksAcross());
        this.blocksDown.getValueFactory().setValue(quilt.getNumBlocksDown());
        this.blockSize.getValueFactory().setValue(quilt.getBlockSize());
        calculateSize();
        disableControls();
    }
}