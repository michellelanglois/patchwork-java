package ui.design;

import exceptions.IllegalQuiltSizeException;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Quilt;
import ui.QuiltApp;


/*
Represents the control panel to create a new quilt
 */

public class ControlsPane extends VBox {

    private QuiltApp quiltApp;
    private Spinner<Integer> blocksAcross;
    private Spinner<Integer> blocksDown;
    private Spinner<Double> blockSize;
    private Button startNewButton;
    private Button resetButton;
    private Label quiltSizeLabel;
    private Label blocksAcrossLabel;
    private Label blocksDownLabel;
    private Label blocksSizeLabel;

    // EFFECTS: creates a new quilt control pane with component parts and associated event listeners
    public ControlsPane(QuiltApp quiltApp) {
        this.quiltApp = quiltApp;
        this.blocksAcross = initializeIntSpinner();
        this.blocksDown = initializeIntSpinner();
        this.blockSize = initializeDoubleSpinner();
        this.startNewButton = initializeStartButton();
        this.resetButton = initializeResetButton();
        this.quiltSizeLabel = new Label();
        this.quiltSizeLabel.getStyleClass().add("quilt-size-label");
        this.blocksAcrossLabel = new Label("Blocks across:");
        this.blocksDownLabel = new Label("Blocks down:");
        this.blocksSizeLabel = new Label("Block size (inches): ");

        initializeLayout();
    }

    // EFFECTS: creates and returns the control pane for starting a new quilt
    private void initializeLayout() {
        this.getStyleClass().add("vbox");

        Label quiltControlLabel = new Label("Let's get creative! What size quilt do you want to make?");
        quiltControlLabel.getStyleClass().add("design-label");

        GridPane quiltControls = new GridPane();
        initializeQuiltControlsLayout(quiltControls);
        quiltControls.getStyleClass().add("new-quilt-controls");

        this.getChildren().addAll(quiltControlLabel, quiltControls);

    }

    // MODIFIES: this
    // EFFECTS: creates the layout for the new quilt control pane
    private GridPane initializeQuiltControlsLayout(GridPane quiltControls) {
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

        quiltControls.getColumnConstraints().addAll(column0, column1, column2, column3, column4);
        quiltControls.getChildren().addAll(blocksAcross, blocksDown, blockSize, startNewButton, resetButton,
                blocksAcrossLabel, blocksDownLabel, blocksSizeLabel, quiltSizeLabel);
        return quiltControls;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the start new quilt button
    private Button initializeStartButton() {
        Button startNewButton = new Button("Create");
        startNewButton.setTooltip(new Tooltip("Create a new quilt of chosen size"));

        startNewButton.setOnAction(event -> {
            int blocksAcross = this.blocksAcross.getValue();
            int blocksDown = this.blocksDown.getValue();
            double blockSize = this.blockSize.getValue();
            try {
                quiltApp.handleStartNewQuiltButtonPressed(blocksAcross, blocksDown, blockSize);
            } catch (IllegalQuiltSizeException e) {
                event.consume();
            }
            disableControls();
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
            quiltApp.handleResetQuiltButtonPressed();
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
                calculateExpectedSize();
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
                calculateExpectedSize();
            }
        });

        return spinner;
    }

    // MODIFIES: this
    // EFFECTS: calculates total size of quilt so this can be displayed by GUI
    private void calculateExpectedSize() {
        double width = blocksAcross.getValue() * blockSize.getValue() + (Quilt.BINDING_WIDTH - 0.5) * 2;
        double height = blocksDown.getValue() * blockSize.getValue() + (Quilt.BINDING_WIDTH - 0.5) * 2;
        this.quiltSizeLabel.setText("Total size: \n" + width + "\" x " + height + "\"");
    }

    // MODIFIES: this
    // EFFECTS: updates information displayed in controls based on loaded quilt
    public void updateQuiltInfoOnLoad(int blocksAcross, int blocksDown, double blockSize) {
        this.blocksAcross.getValueFactory().setValue(blocksAcross);
        this.blocksDown.getValueFactory().setValue(blocksDown);
        this.blockSize.getValueFactory().setValue(blockSize);
        calculateExpectedSize();
        disableControls();
    }

}