//TODO: function to add blocks to multiple spots on the quilt
//TODO: ability to change size of quilt after creating
//TODO: ability to save color of quilt

package ui;

import exceptions.BlockUnavailableException;
import exceptions.IllegalQuiltSizeException;
import exceptions.SlotOutOfBoundsException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Quilt;
import model.patches.Patch;
import persistence.Reader;
import persistence.Writer;
import ui.design.BlockChooserPane;
import ui.design.CalculationsPane;
import ui.design.ColorPickerPane;
import ui.design.ControlsPane;
import ui.navigation.MenuPane;
import ui.visualization.QuiltGridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
Represents the GUI for Patchwork
 */

public class QuiltApp extends Application {

    private static final String QUILT_FILE = "./data/myquilt.json";

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = WINDOW_WIDTH * 2 / 3;


    private Quilt quilt;
    private Stage window;
    private MenuPane quiltMenu;
    private ControlsPane quiltControls;
    private BlockChooserPane blockChooser;
    private ColorPickerPane quiltColors;
    private CalculationsPane quiltCalculations;
    private QuiltGridPane quiltGrid;

    //EFFECTS: launches the application
    public static void main(String[] args) {
        launch(args);
    }

    // MODIFIES: this
    // EFFECTS: initializes the overall application window and graphics; no quilt is immediately loaded
    @Override
    public void start(Stage window) {
        this.window = window;
        this.quilt = null;
        this.quiltMenu = new MenuPane(this);
        this.quiltControls = new ControlsPane(this);
        this.blockChooser = new BlockChooserPane(WINDOW_HEIGHT / 5);
        this.quiltColors = new ColorPickerPane(this);
        this.quiltCalculations = new CalculationsPane(this);
        this.quiltGrid = new QuiltGridPane(this);
        setParameters();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: sets application parameters, including title of window and exit procedures
    private void setParameters() {
        window.setTitle("Patchwork");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
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

        layout.setTop(quiltMenu);
        layout.setLeft(initializeDesignArea());
        layout.setCenter(initializeQuiltArea());

        return layout;
    }

    // EFFECTS: creates and returns the quilt design and information pane for the application
    private Pane initializeDesignArea() {
        VBox designArea = new VBox();
        designArea.getStyleClass().addAll("design");

        designArea.setPrefWidth(WINDOW_WIDTH * .40);

        designArea.getChildren().addAll(
                quiltControls,
                blockChooser,
                quiltColors,
                quiltCalculations);

        return designArea;
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the quilt visualization pane for the application
    private StackPane initializeQuiltArea() {
        StackPane quiltArea = new StackPane();
        quiltArea.setPadding(new Insets(30, 30, 30, 30));

        quiltArea.getChildren().add(quiltGrid);

        return quiltArea;
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

    // EFFECTS: responds to save button press by saving quilt to file if a quilt has been started
    public void handleSaveQuiltButtonPressed() {
        if (quilt != null) {
            saveQuilt();
        } else {
            Alert saveAlert = new Alert(Alert.AlertType.WARNING);
            saveAlert.setContentText("You need to make a quilt before you can save one!");
            saveAlert.showAndWait();
        }
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
    // EFFECTS: responds to load button press by loading quilt from file
    public void handleLoadQuiltButtonPressed() {
        loadQuilt();

        quiltColors.setColorPickerA(quilt.getFabricColours()[0]);
        quiltColors.setColorPickerB(quilt.getFabricColours()[1]);

        int blocksAcross = quilt.getNumBlocksAcross();
        int blocksDown = quilt.getNumBlocksDown();
        double blockSize = quilt.getBlockSize();
        quiltGrid.initializeQuiltGrid(blocksAcross, blocksDown);
        handleRenderQuilt();

        quiltControls.updateQuiltInfoOnLoad(blocksAcross, blocksDown, blockSize);
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

    // MODIFIES: this
    // EFFECTS: responds to colorPicker action by changing the color of the quilt fabric and re-rendering quilt
    public void handleColorPickerAction(Color fabricA, Color fabricB) {
        if (quilt != null) {
            quilt.setFabricColours(fabricA.toString(), fabricB.toString());
        }
        handleRenderQuilt();
    }

    // EFFECTS: returns a string detailing how much fabric is needed
    public String handleCalculateFabricButtonPressed() {
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

    // MODIFIES: this
    // EFFECTS: responds to start new quilt button press by starting a new quilt and rendering it
    public void handleStartNewQuiltButtonPressed(int blocksAcross, int blocksDown, double blockSize)
            throws IllegalQuiltSizeException {
        quilt = new Quilt(blocksAcross, blocksDown, blockSize);
        quiltGrid.initializeQuiltGrid(blocksAcross, blocksDown);
        handleRenderQuilt();
    }

    // MODIFIES: this
    // EFFECTS: responds to reset quilt button press by resetting quilt to null and wiping render
    public void handleResetQuiltButtonPressed() {
        quilt = null;
        quiltGrid.clearQuiltGrid();
        quiltColors.setColorPickerA("#bbc2cf");
        quiltColors.setColorPickerB("#d1d6df");
    }

    // MODIFIES: this
    // EFFECTS: adds a block to quilt and asks quiltGrid to render the block
    public void handleAddBlockToQuilt(String blockName, int blockIndex) throws BlockUnavailableException,
            SlotOutOfBoundsException {
        quilt.addBlock(blockName, blockIndex);
        quiltGrid.renderBlock(blockIndex, quilt.getBlocks().get(blockIndex));
    }

    // MODIFIES: this
    // EFFECTS: removes a block from the quilt and resets the appropriate block space to initial state
    public void handleRemoveBlockFromQuilt(int blockIndex) throws SlotOutOfBoundsException {
        quilt.removeBlock(blockIndex);
        quiltGrid.resetBlockSpace(blockIndex);
    }

    // MODIFIES: this
    // EFFECTS: tells quiltGrid to render an image of the quilt
    public void handleRenderQuilt() {
        if (quilt != null) {
            quiltGrid.renderQuilt(quilt.getBlocks());
        }
    }
}
