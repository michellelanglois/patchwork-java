//TODO: function to add blocks to multiple spots on the quilt
//TODO: ability to change size of quilt after creating
//TODO: ability to save color of quilt

package ui;

import exceptions.BlockUnavailableException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Quilt;
import model.blocks.Block;
import model.blocks.BlockType;
import model.patches.Patch;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
Represents the GUI for Patchwork
 */

public class QuiltAppGUI extends Application {

    protected static Color FABRIC_A_COLOUR = Color.web("#bbc2cf");
    protected static Color FABRIC_B_COLOUR = Color.web("#d1d6df");

    private static final String QUILT_FILE = "./data/myquilt.json";

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = WINDOW_WIDTH * 2 / 3;

    private static final Image PATCHWORK_LOGO = new Image("file:./data/icons/quilt.png");
    protected static final Image DELETE_ICON = new Image("file:./data/icons/delete.png");

    private Quilt quilt;
    private Stage window;
    private NewQuiltControlsUI quiltControls;
    private QuiltGridUI quiltGrid;

    //EFFECTS: launches the application
    public static void main(String[] args) {
        launch(args);
    }

    // getters and setters
    public void setQuilt(Quilt quilt) {
        this.quilt = quilt;
    }

    public Quilt getQuilt() {
        return this.quilt;
    }

    public QuiltGridUI getQuiltGrid() {
        return quiltGrid;
    }

    // MODIFIES: this
    // EFFECTS: initializes the overall application window and graphics; no quilt is immediately loaded
    @Override
    public void start(Stage window) {
        this.window = window;
        this.quilt = null;
        setParameters();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: sets application parameters, including title of window and exit procedures
    private void setParameters() {
        window.setTitle("Patchwork");
        window.getIcons().add(PATCHWORK_LOGO);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    // EFFECTS: if quilt != null, displays a confirmation box to user to prompt save, then closes program
    private void closeProgram() {
        if (quilt != null) {
            boolean answer = ConfirmBox.display("Exit", "Do you want to save your quilt before exiting?");
            if (answer) {
                saveQuilt();
            }
        }
        window.close();
    }

    // MODIFIES: this
    // EFFECTS: creates application graphics; initializes underlying scene and sets its base pane and CSS styles
    private void initializeGraphics() {
        Pane layout = initializeLayout();
        Scene applicationWindow = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
        applicationWindow.getStylesheets().add(getClass().getResource("patchwork.css").toExternalForm());
        window.setScene(applicationWindow);
        window.show();
    }

    // EFFECTS: creates and returns base layout pane with individual components set
    private Pane initializeLayout() {
        BorderPane layout = new BorderPane();
        layout.getStyleClass().add("root");

        layout.setTop(initializeMenuArea());
        layout.setLeft(initializeDesignArea());
        layout.setCenter(initializeQuiltArea());

        return layout;
    }

    // EFFECTS: creates and returns the menu pane for the application
    private Pane initializeMenuArea() {
        HBox menu = new HBox();
        menu.getStyleClass().add("menu");

        Pane titleArea = initializeTitleArea();
        Pane menuControlArea = initializeMenuControlArea();

        HBox.setHgrow(titleArea, Priority.ALWAYS);
        HBox.setHgrow(menuControlArea, Priority.ALWAYS);

        menu.getChildren().addAll(titleArea, menuControlArea);

        return menu;
    }

    // EFFECTS: creates and returns the quilt design and information pane for the application
    private Pane initializeDesignArea() {
        VBox designArea = new VBox();
        designArea.getStyleClass().addAll("design");

        designArea.setPrefWidth(WINDOW_WIDTH * .40);

        designArea.getChildren().addAll(
                initializeNewQuiltControlSubArea(),
                initializeBlockChooserSubArea(),
                initializeColorPickerSubArea(),
                initializeCalculationSubArea());

        return designArea;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the quilt visualization pane for the application
    private StackPane initializeQuiltArea() {
        StackPane quiltArea = new StackPane();
        quiltArea.setPadding(new Insets(30, 30, 30, 30));
        quiltGrid = new QuiltGridUI(this);

        quiltArea.getChildren().add(quiltGrid);

        return quiltArea;
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

        menuControlArea.getChildren().addAll(initializeLoadButton(), initializeSaveButton());

        return menuControlArea;
    }

    // EFFECTS: creates a button capable of saving data to file
    private Node initializeSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("menu-button");
        saveButton.setTooltip(new Tooltip("Saves your current quilt"));

        saveButton.setOnAction(event -> {
            if (quilt != null) {
                saveQuilt();
            } else {
                Alert saveAlert = new Alert(Alert.AlertType.WARNING);
                saveAlert.setContentText("You need to make a quilt before you can save one!");
                saveAlert.showAndWait();
            }
        });

        return saveButton;
    }

    // EFFECTS: saves the current quilt to file if possible
    private void saveQuilt() {
        try {
            Writer writer = new Writer(new File(QUILT_FILE));
            writer.write(quilt);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a button capable of loading data from file
    private Node initializeLoadButton() {
        Button loadButton = new Button("Load");
        loadButton.getStyleClass().add("menu-button");
        loadButton.setTooltip(new Tooltip("Loads your last saved quilt"));

        loadButton.setOnAction(event -> {
            loadQuilt();
            this.quiltGrid.initializeQuiltGrid();
            this.quiltGrid.renderQuilt();
            this.quiltControls.updateQuiltInfoOnLoad();
        });

        return loadButton;
    }

    // MODIFIES: this
    // EFFECTS: loads a quilt from file if a saved quilt exists; otherwise, alerts user
    private void loadQuilt() {
        try {
            quilt = Reader.readQuilt(new File(QUILT_FILE));
        } catch (FileNotFoundException e) {
            Alert loadAlert = new Alert(Alert.AlertType.WARNING);
            loadAlert.setContentText("You don't have a saved quilt yet");
            loadAlert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: creates and returns the control pane for starting a new quilt
    private Pane initializeNewQuiltControlSubArea() {
        VBox quiltControlSubArea = new VBox();
        quiltControlSubArea.getStyleClass().add("vbox");

        Label quiltControlLabel = new Label("Let's get creative! What size quilt do you want to make?");
        quiltControlLabel.getStyleClass().add("design-label");

        this.quiltControls = new NewQuiltControlsUI(this);
        quiltControls.getStyleClass().add("new-quilt-controls");

        quiltControlSubArea.getChildren().addAll(quiltControlLabel, quiltControls);

        return quiltControlSubArea;
    }

    // EFFECTS: creates and returns the control pane for changing the color of the quilt
    private Pane initializeColorPickerSubArea() {
        VBox colorPickerSubArea = new VBox();
        colorPickerSubArea.getStyleClass().add("vbox");

        Label colorPickerLabel = new Label("Time to color your world.");
        colorPickerLabel.getStyleClass().add("design-label");

        Pane colorPickers = initializeColorPickers();

        colorPickerSubArea.getChildren().addAll(colorPickerLabel, colorPickers);

        return colorPickerSubArea;
    }

    // MODIFIES: this
    // EFFECTS: creates a returns color pickers capable of changing the color of the quilt
    private Pane initializeColorPickers() {
        HBox colorPickerBox = new HBox(20);
        Tooltip tooltip = new Tooltip("Choose colours for your quilt");
        Tooltip.install(colorPickerBox, tooltip);

        Label fabricALabel = new Label("Fabric A");
        Label fabricBLabel = new Label("Fabric B");
        ColorPicker colorPickerA = new ColorPicker(FABRIC_A_COLOUR);
        ColorPicker colorPickerB = new ColorPicker(FABRIC_B_COLOUR);

        colorPickerBox.getChildren().addAll(fabricALabel, colorPickerA, fabricBLabel, colorPickerB);

        colorPickerA.setOnAction(event -> {
            if (quilt != null) {
                FABRIC_A_COLOUR = colorPickerA.getValue();
                quiltGrid.renderQuilt();
            }
        });

        colorPickerB.setOnAction(event -> {
            if (quilt != null) {
                FABRIC_B_COLOUR = colorPickerB.getValue();
                quiltGrid.renderQuilt();
            }
        });

        return colorPickerBox;
    }

    // EFFECTS: creates and returns a pane where users can calculate how much fabric is needed
    private Pane initializeCalculationSubArea() {
        VBox calculationSubArea = new VBox();
        calculationSubArea.getStyleClass().add("vbox");

        Label calculationLabel = new Label("Quilt math sucks. Let someone else do it for you.");
        calculationLabel.getStyleClass().add("design-label");

        Pane calculationDisplay = initializeCalculationDisplay();

        calculationSubArea.getChildren().addAll(calculationLabel, calculationDisplay);

        return calculationSubArea;
    }

    // EFFECTS: creates and returns a pane where users can see how much fabric they need
    private Pane initializeCalculationDisplay() {
        VBox calculationDisplayArea = new VBox();
        calculationDisplayArea.getStyleClass().add("vbox");

        Button calculateButton = new Button("Calculate");
        TextArea calculateAnswer = new TextArea();
        calculateAnswer.setEditable(false);
        calculateAnswer.setWrapText(true);

        calculateButton.setOnAction(event -> calculateAnswer.setText(getFabricCalculations()));

        calculationDisplayArea.getChildren().addAll(calculateAnswer, calculateButton);

        return calculationDisplayArea;
    }

    // EFFECTS: returns a string detailing how much fabric is needed
    private String getFabricCalculations() {
        String answer = "";
        if (quilt != null) {
            double fabricAmountA = quilt.calculateFabric("A");
            double fabricAmountB = quilt.calculateFabric("B");
            double fabricAmountBacking = quilt.calculateTotalBacking();
            double fabricAmountBinding = quilt.calculateTotalBinding();
            answer += ("FABRIC NEEDED: \n"
                    + "You need " + fabricAmountA + " square inches of fabric A, \nand "
                    + fabricAmountB + " square inches of fabric B.\n"
                    + "You need " + fabricAmountBacking + " square inches of backing fabric, \n"
                    + "and " + fabricAmountBinding + " square inches of binding fabric.\n"
                    + "\nPATCHES NEEDED: \n"
                    + "You need " + quilt.countPatches(Patch.SQUARE) + " square patches, "
                    + quilt.countPatches(Patch.HALF_SQUARE) + " half-square patches, and "
                    + quilt.countPatches(Patch.HALF_TRIANGLE) + " half-square triangle patches.");
        } else {
            answer = "You need to create or load a quilt first!";
        }
        return answer;
    }

    // EFFECTS: creates and returns a pane where users can choose blocks for their quilt
    private Pane initializeBlockChooserSubArea() {
        VBox blockChooserSubArea = new VBox();
        blockChooserSubArea.getStyleClass().add("vbox");

        Label blockChooserLabel = new Label("Choose your favourite. Add a block to your quilt.");
        blockChooserLabel.getStyleClass().add("design-label");

        ScrollPane blockScroller = initializeBlockScroller();

        blockChooserSubArea.getChildren().addAll(blockChooserLabel, blockScroller);

        return blockChooserSubArea;
    }

    // EFFECTS: creates and returns a scroll bar with available blocks
    private ScrollPane initializeBlockScroller() {
        ScrollPane blockScroller = new ScrollPane();
        Tooltip tooltip = new Tooltip("Drag and drop blocks to design your quilt");
        Tooltip.install(blockScroller, tooltip);

        int height = WINDOW_HEIGHT / 5;
        blockScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        blockScroller.setPrefHeight(height);

        HBox blockImages = renderAvailableBlocks((height * .70) - (height * .70 % 3));
        blockScroller.setContent(blockImages);

        return blockScroller;
    }

    // EFFECTS: creates horizontal pane of available blocks that can be dragged
    // NOTE: code for .setOnDragDetected method copied from Oracle Drag and Drop Tutorial
    //      available from https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
    private HBox renderAvailableBlocks(double height) {
        HBox blockImages = new HBox();
        blockImages.getStyleClass().add("block-images");
        for (String blockName : BlockType.getAvailableBlockMap().keySet()) {
            try {
                Block block = new Block(blockName, 0);
                BlockImage blockImage = new BlockImage(block, height);
                blockImage.setOnDragDetected(event -> {
                    Dragboard db = blockImage.startDragAndDrop(TransferMode.ANY);
                    db.setDragView(blockImage.snapshot(null, null));
                    ClipboardContent content = new ClipboardContent();
                    content.putString(block.getBlockType());
                    db.setContent(content);
                    event.consume();
                });
                blockImages.getChildren().add(blockImage);
            } catch (BlockUnavailableException e) {
                e.printStackTrace();
            }
        }
        return blockImages;
    }
}
