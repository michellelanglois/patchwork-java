package ui.design;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ui.QuiltApp;


public class ColorPickerPane extends VBox {

    public static Color FABRIC_A_COLOUR = Color.web("#bbc2cf");
    public static Color FABRIC_B_COLOUR = Color.web("#d1d6df");

    private QuiltApp quiltApp;
    private ColorPicker colorPickerA;
    private ColorPicker colorPickerB;

    public ColorPickerPane(QuiltApp quiltApp) {
        this.quiltApp = quiltApp;
        this.colorPickerA = new ColorPicker(FABRIC_A_COLOUR);
        this.colorPickerB = new ColorPicker(FABRIC_B_COLOUR);
        initializeLayout();
    }

    // setters
    public void setColorPickerA(String colour) {
        Color hexColour = Color.web(colour);
        colorPickerA.setValue(hexColour);
        FABRIC_A_COLOUR = hexColour;
    }

    public void setColorPickerB(String colour) {
        Color hexColour = Color.web(colour);
        colorPickerB.setValue(hexColour);
        FABRIC_B_COLOUR = hexColour;
    }

    // EFFECTS: creates and returns the control pane for changing the color of the quilt
    private void initializeLayout() {
        this.getStyleClass().add("vbox");

        Label colorPickerLabel = new Label("Time to color your world.");
        colorPickerLabel.getStyleClass().add("design-label");

        Pane colorPickers = initializeColorPickerBox();

        this.getChildren().addAll(colorPickerLabel, colorPickers);
    }

    // MODIFIES: this
    // EFFECTS: creates a returns color pickers capable of changing the color of the quilt
    private Pane initializeColorPickerBox() {
        HBox colorPickerBox = new HBox(20);
        Tooltip tooltip = new Tooltip("Choose colours for your quilt");
        Tooltip.install(colorPickerBox, tooltip);

        Label fabricALabel = new Label("Fabric A");
        Label fabricBLabel = new Label("Fabric B");

        colorPickerBox.getChildren().addAll(fabricALabel, colorPickerA, fabricBLabel, colorPickerB);

        colorPickerA.setOnAction(event -> {
            FABRIC_A_COLOUR = colorPickerA.getValue();
            quiltApp.handleColorPickerAction(FABRIC_A_COLOUR, FABRIC_B_COLOUR);
        });

        colorPickerB.setOnAction(event -> {
            FABRIC_B_COLOUR = colorPickerB.getValue();
            quiltApp.handleColorPickerAction(FABRIC_A_COLOUR, FABRIC_B_COLOUR);
        });

        return colorPickerBox;
    }
}
