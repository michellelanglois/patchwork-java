package ui.design;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.QuiltApp;

public class CalculationsPane extends VBox {

    private QuiltApp quiltApp;

    public CalculationsPane(QuiltApp quiltApp) {
        this.quiltApp = quiltApp;
        initializeLayout();
    }

    // EFFECTS: creates and returns a pane where users can calculate how much fabric is needed
    private void initializeLayout() {
        this.getStyleClass().add("vbox");

        Label calculationLabel = new Label("Quilt math sucks. Let someone else do it for you.");
        calculationLabel.getStyleClass().add("design-label");

        Pane calculationDisplay = initializeCalculationDisplay();

        this.getChildren().addAll(calculationLabel, calculationDisplay);
    }

    // EFFECTS: creates and returns a pane where users can see how much fabric they need
    private Pane initializeCalculationDisplay() {
        VBox calculationDisplayArea = new VBox();
        calculationDisplayArea.getStyleClass().add("vbox");

        Button calculateButton = new Button("Calculate");
        TextArea calculateAnswer = new TextArea();
        calculateAnswer.setEditable(false);
        calculateAnswer.setWrapText(true);

        calculateButton.setOnAction(event -> calculateAnswer.setText(quiltApp.handleCalculateFabricButtonPressed()));

        calculationDisplayArea.getChildren().addAll(calculateAnswer, calculateButton);

        return calculationDisplayArea;
    }

}
