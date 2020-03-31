package ui.navigation;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import ui.QuiltApp;

public class MenuPane extends HBox {

    private static final Image PATCHWORK_LOGO = new Image("file:./data/icons/quilt.png");

    private QuiltApp quiltApp;
    private Button saveButton;
    private Button loadButton;

    public MenuPane(QuiltApp quiltApp) {
        this.quiltApp = quiltApp;
        this.saveButton = initializeSaveButton();
        this.loadButton = initializeLoadButton();
        initializeLayout();
    }

    // EFFECTS: creates and returns the menu pane for the application
    private void initializeLayout() {
        this.getStyleClass().add("menu");

        Pane titleArea = initializeTitleArea();
        Pane menuControlArea = initializeMenuControlArea();

        HBox.setHgrow(titleArea, Priority.ALWAYS);
        HBox.setHgrow(menuControlArea, Priority.ALWAYS);

        this.getChildren().addAll(titleArea, menuControlArea);
    }

    // EFFECTS: creates and returns the title pane for the application
    private Pane initializeTitleArea() {
        HBox titleArea = new HBox();
        titleArea.getStyleClass().add("menu-title");

        ImageView patchworkIcon = new ImageView(PATCHWORK_LOGO);
        Label patchworkTitle = new Label("PATCHWORK");

        titleArea.getChildren().addAll(patchworkIcon, patchworkTitle);

        return titleArea;
    }

    // EFFECTS: creates and returns the menu control pane for the application
    private Pane initializeMenuControlArea() {
        HBox menuControlArea = new HBox();
        menuControlArea.getStyleClass().add("menu-controls");

        menuControlArea.getChildren().addAll(loadButton, saveButton);

        return menuControlArea;
    }

    // EFFECTS: creates a button capable of saving data to file
    private Button initializeSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("menu-button");
        saveButton.setTooltip(new Tooltip("Saves your current quilt"));

        saveButton.setOnAction(event -> quiltApp.handleSaveQuiltButtonPressed());

        return saveButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a button capable of loading data from file
    private Button initializeLoadButton() {
        Button loadButton = new Button("Load");
        loadButton.getStyleClass().add("menu-button");
        loadButton.setTooltip(new Tooltip("Loads your last saved quilt"));

        loadButton.setOnAction(event -> quiltApp.handleLoadQuiltButtonPressed());

        return loadButton;
    }
}
