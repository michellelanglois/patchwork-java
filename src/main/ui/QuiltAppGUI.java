package ui;

import exceptions.BlockUnavailableException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
import java.util.ArrayList;
import java.util.List;

//credit Oracle tutorial docs for drag and drop
public class QuiltAppGUI extends Application {

    private static Color FABRIC_A_COLOUR = Color.web("#C9D1DB");
    private static Color FABRIC_B_COLOUR = Color.web("#939AA5");

    private static final String QUILT_FILE = "./data/myquilt.json";

    private static final int WINDOW_WIDTH = 1500;
    private static final int WINDOW_HEIGHT = WINDOW_WIDTH * 2 / 3;

    private static final Image ICON = new Image("file:./data/icons/quilt.png");
    private static final Image CANCEL = new Image("file:./data/icons/trash.png");

    Quilt quilt;
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        this.window = window;
        this.quilt = null;

        setApplicationParameters();
        initializeGraphics();
    }

    private void setApplicationParameters() {
        window.setTitle("Patchwork");
        //Icons made by <a href="https://www.flaticon.com/authors/becris" title="Becris">Becris</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
        window.getIcons().add(ICON);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    private void initializeGraphics() {
        BorderPane applicationLayout = new BorderPane();
        Pane topMenuControls = initializeMenuControls();
        Pane leftDesignControls = initializeDesignControls();
        Pane centerQuiltViewer = initializeQuiltPane();

        applicationLayout.setTop(topMenuControls);
        applicationLayout.setLeft(leftDesignControls);
        applicationLayout.setCenter(centerQuiltViewer);

        Scene applicationWindow = new Scene(applicationLayout, WINDOW_WIDTH, WINDOW_HEIGHT);

        applicationWindow.getStylesheets().add(getClass().getResource("patchwork.css").toExternalForm());

        window.setScene(applicationWindow);
        window.show();
    }

    private Pane initializeMenuControls() {
        HBox menuControls = new HBox(30);
        menuControls.setAlignment(Pos.CENTER_LEFT);
        menuControls.setPadding(new Insets(10, 10, 10, 10));
        menuControls.getStyleClass().add("header");

        ImageView iconPane = new ImageView();
        iconPane.setImage(ICON);

        Label patchworkTitle = new Label("PATCHWORK");

        menuControls.getChildren().addAll(
                iconPane,
                patchworkTitle,
                initializeSaveButton(),
                initializeLoadButton());

        return menuControls;
    }

    private Node initializeSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            if (quilt != null) {
                saveQuilt();
            }
        });
        saveButton.setTooltip(new Tooltip("Saves your current quilt"));
        saveButton.getStyleClass().add("button-menu");
        return saveButton;
    }

    private Node initializeLoadButton() {
        Button loadButton = new Button("Load");
        loadButton.setOnAction(event -> {
            clearQuiltGrid();
            loadQuilt();
            renderQuiltGrid();
            renderQuilt();
        });
        loadButton.setTooltip(new Tooltip("Loads your last saved quilt"));
        loadButton.getStyleClass().add("button-menu");
        return loadButton;
    }

    private VBox initializeDesignControls() {
        VBox designControls = new VBox(20);
        designControls.setPadding(new Insets(10, 10, 10, 10));
        designControls.setPrefWidth(WINDOW_WIDTH * .40);

        Label newQuiltControlLabel = new Label("Let's get creative! What size quilt do you want to make?");
        newQuiltControlLabel.getStyleClass().add("label-subheader");
        Pane newQuiltControls = initializeNewQuiltControls();
        Label blockChooserLabel = new Label("Choose your favourite. Add a block to your quilt.");
        blockChooserLabel.getStyleClass().add("label-subheader");
        ScrollPane blockChooser = initializeBlockChooser();
        Label colorPickerLabel = new Label("Time to colour your world.");
        colorPickerLabel.getStyleClass().add("label-subheader");
        HBox colorPicker = initializeColorPicker();
        Label calculationPaneLabel = new Label("Quilt math sucks. Let someone else do it for you.");
        calculationPaneLabel.getStyleClass().add("label-subheader");
        Pane calculationPane = initializeCalculationPane();

        designControls.getChildren().addAll(newQuiltControlLabel, newQuiltControls, blockChooserLabel, blockChooser,
                colorPickerLabel, colorPicker, calculationPaneLabel, calculationPane);

        return designControls;
    }

    private GridPane initializeNewQuiltControls() {
        GridPane newQuiltControls = new GridPane();
        newQuiltControls.setPadding(new Insets(10, 10, 10, 10));
        newQuiltControls.setVgap(8);
        newQuiltControls.setHgap(10);

        Label blocksAcrossLabel = new Label("Blocks across:");
        Label blocksDownLabel = new Label("Blocks down:");
        Label blocksSizeLabel = new Label("Block size (inches): ");
        Spinner<Integer> blocksAcross = new Spinner<>(1, 36, 1);
        Spinner<Integer> blocksDown = new Spinner<>(1, 36, 1);
        Spinner<Double> blockSize = new Spinner<>(3.0, 18.0, 3.0, 1.5);

        Button startNewQuiltButton = new Button("Create");
        startNewQuiltButton.setTooltip(new Tooltip("Create a new quilt of chosen size"));

        Button resetStartNewQuiltButton = new Button("Restart");
        resetStartNewQuiltButton.setTooltip(new Tooltip("Allows you to create a different quilt"));
        resetStartNewQuiltButton.setDisable(true);

        startNewQuiltButton.setOnAction(event -> {
            quilt = new Quilt(blocksAcross.getValue(), blocksDown.getValue(), blockSize.getValue());
            clearQuiltGrid();
            renderQuiltGrid();
            renderQuilt();
            blocksAcross.setDisable(true);
            blocksDown.setDisable(true);
            blockSize.setDisable(true);
            startNewQuiltButton.setDisable(true);
            resetStartNewQuiltButton.setDisable(false);
        });

        resetStartNewQuiltButton.setOnAction(event -> {
            quilt = null;
            clearQuiltGrid();
            blocksAcross.setDisable(false);
            blocksDown.setDisable(false);
            blockSize.setDisable(false);
            startNewQuiltButton.setDisable(false);
            resetStartNewQuiltButton.setDisable(true);
        });

        GridPane.setConstraints(blocksAcrossLabel, 0, 0);
        GridPane.setConstraints(blocksDownLabel, 0, 1);
        GridPane.setConstraints(blocksSizeLabel, 0, 2);
        GridPane.setConstraints(blocksAcross, 1, 0);
        GridPane.setConstraints(blocksDown, 1, 1);
        GridPane.setConstraints(blockSize, 1, 2);
        GridPane.setConstraints(startNewQuiltButton, 2, 1);
        GridPane.setConstraints(resetStartNewQuiltButton, 2, 2);

        newQuiltControls.getChildren().addAll(blocksAcrossLabel, blocksDownLabel, blocksSizeLabel,
                blocksAcross, blocksDown, blockSize, startNewQuiltButton, resetStartNewQuiltButton);

        return newQuiltControls;
    }

    private ScrollPane initializeBlockChooser() {
        ScrollPane blockScroller = new ScrollPane();
        int height = WINDOW_HEIGHT / 5;
        blockScroller.setPadding(new Insets(10, 10, 10, 10));
        Tooltip tooltip = new Tooltip("Drag and drop blocks to design your quilt");
        Tooltip.install(blockScroller, tooltip);
        blockScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        blockScroller.setPrefHeight(height);

        HBox blockImages = renderAvailableBlocks(height - 20);

        blockScroller.setContent(blockImages);

        return blockScroller;
    }

    private HBox initializeColorPicker() {
        HBox colorPickerBox = new HBox(20);
        colorPickerBox.setPadding(new Insets(10, 10, 10, 10));
        Tooltip tooltip = new Tooltip("Choose colours for your quilt");
        Tooltip.install(colorPickerBox, tooltip);
        Label fabricALabel = new Label("Fabric A");
        Label fabricBLabel = new Label("Fabric B");
        ColorPicker colorPickerA = new ColorPicker();
        ColorPicker colorPickerB = new ColorPicker();
        colorPickerBox.getChildren().addAll(fabricALabel, colorPickerA, fabricBLabel, colorPickerB);

        colorPickerA.setOnAction(event -> {
            FABRIC_A_COLOUR = colorPickerA.getValue();
            if (quilt != null) {
                renderQuilt();
            }
        });

        colorPickerB.setOnAction(event -> {
            FABRIC_B_COLOUR = colorPickerB.getValue();
            if (quilt != null) {
                renderQuilt();
            }
        });

        return colorPickerBox;
    }

    private VBox initializeCalculationPane() {
        VBox calculationPane = new VBox(20);
        calculationPane.setPadding(new Insets(10, 10, 10, 10));

        Button calculateButton = new Button("Tell me how much of everything I need!");
        TextArea calculateAnswer = new TextArea();
        calculateAnswer.setEditable(false);

        calculateButton.setOnAction(event -> {
            if (quilt != null) {
                double fabricAmountA = quilt.calculateFabric("A");
                double fabricAmountB = quilt.calculateFabric("B");
                double fabricAmountBacking = quilt.calculateTotalBacking();
                double fabricAmountBinding = quilt.calculateTotalBinding();
                calculateAnswer.setText("FABRIC NEEDED: \n"
                        + "You need " + fabricAmountA + " square inches of fabric A.\n"
                        + "You need " + fabricAmountB + " square inches of fabric B.\n"
                        + "You need " + fabricAmountBacking + " square inches of backing fabric.\n"
                        + "You need " + fabricAmountBinding + " square inches of binding fabric.\n"
                        + "\nPATCHES NEEDED: \n"
                        + "You need " + quilt.countPatches(Patch.SQUARE) + " square patches.\n"
                        + "You need " + quilt.countPatches(Patch.HALF_SQUARE) + " half-square patches.\n"
                        + "You need " + quilt.countPatches(Patch.HALF_TRIANGLE) + " half-square triangle patches.");
            } else {
                calculateAnswer.setText("You need to create or load a quilt first!");
            }
        });

        calculationPane.getChildren().addAll(calculateAnswer, calculateButton);

        return calculationPane;

    }

    private StackPane initializeQuiltPane() {
        StackPane quiltPane = new StackPane();
        quiltPane.setPadding(new Insets(10, 10, 10, 10));


        GridPane emptyQuiltGrid = new GridPane();
        emptyQuiltGrid.setId("quiltGrid");

        quiltPane.getChildren().add(emptyQuiltGrid);

        return quiltPane;
    }

    private HBox renderAvailableBlocks(double height) {
        HBox blockImages = new HBox(10);
        blockImages.setPadding(new Insets(10, 10, 10, 10));

        // create list of dummy template blocks
        List<Block> blocks = new ArrayList<>();
        for (String blockName : BlockType.getAvailableBlockMap().keySet()) {
            try {
                blocks.add(new Block(blockName, 0));
            } catch (BlockUnavailableException e) {
                continue;
            }
        }

        // render images of blocks
        double blockSize = height * 3 / 4;
        for (Block block : blocks) {
            GridPane blockImage = renderBlock(block, blockSize);

            blockImage.setOnDragDetected(event -> {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = blockImage.startDragAndDrop(TransferMode.ANY);
                db.setDragView(blockImage.snapshot(null, null));

                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(block.getBlockType());
                db.setContent(content);
                event.consume();
            });

            blockImages.getChildren().add(blockImage);
        }

        return blockImages;
    }

    private GridPane renderBlock(Block block, double size) {
        GridPane blockImage = new GridPane();

        List<Patch> patches = block.getPatches();
        for (int i = 0; i < patches.size(); i++) {
            Patch patch = patches.get(i);
            StackPane patchImage = renderPatch(patch, size / 3.0);
            int row = i / 3;
            int column = i % 3;
            blockImage.add(patchImage, column, row);
        }
        return blockImage;
    }

    private StackPane renderPatch(Patch patch, double size) {
        StackPane patchImage = new StackPane();
        patchImage.setAlignment(Pos.TOP_LEFT);

        Shape baseSquare = new Rectangle(size, size);
        baseSquare.setFill(FABRIC_B_COLOUR);
        patchImage.getChildren().add(baseSquare);
        patchImage.setRotate(patch.getRotation());

        if (patch.getType().equals(Patch.SQUARE) && patch.getFabrics().contains("A")) {
            baseSquare.setFill(FABRIC_A_COLOUR);
        } else if (patch.getType().equals(Patch.HALF_SQUARE)) {
            Shape halfSquare = new Rectangle(size / 2.0, size);
            halfSquare.setFill(FABRIC_A_COLOUR);
            patchImage.getChildren().add(halfSquare);
        } else if (patch.getType().equals(Patch.HALF_TRIANGLE)) {
            Shape halfTriangle = new Polygon(0.0, 0.0, 0.0, size, size, 0.0);
            halfTriangle.setFill(FABRIC_A_COLOUR);
            patchImage.getChildren().add(halfTriangle);
        }
        return patchImage;
    }

    private void loadQuilt() {
        try {
            quilt = Reader.readQuilt(new File(QUILT_FILE));
        } catch (FileNotFoundException e) {
            System.out.println("Sorry! You don't have a quilt saved. Why don't you start a new one?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveQuilt() {
        try {
            Writer writer = new Writer(new File(QUILT_FILE));
            writer.write(quilt);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearQuiltGrid() {
        GridPane quiltGrid = (GridPane) window.getScene().lookup("#quiltGrid");
        quiltGrid.getChildren().clear();
    }

    private void renderQuilt() {
        GridPane quiltGrid = (GridPane) window.getScene().lookup("#quiltGrid");
        for (int i = 0; i < quilt.getTotalBlocks(); i++) {
            Block block = quilt.getBlocks().get(i);
            if (block != null) {
                StackPane blockSpace = (StackPane) quiltGrid.getChildren().get(i);
                double size = blockSpace.getPrefHeight();
                GridPane blockImage = renderBlock(block, size);
                blockImage.setPrefWidth(blockSpace.getPrefWidth());
                blockImage.setPrefHeight(blockSpace.getPrefHeight());
                blockSpace.getChildren().add(blockImage);
                Button deleteButton = (Button) blockSpace.getChildren().get(0);
                deleteButton.setDisable(false);
                deleteButton.setVisible(true);
            }
        }
    }

    private void renderQuiltGrid() {
        GridPane quiltGrid = (GridPane) window.getScene().lookup("#quiltGrid");
        quiltGrid.setAlignment(Pos.CENTER);
        int blocksAcross = quilt.getNumBlocksAcross();
        int blocksDown = quilt.getNumBlocksDown();
        double blockSpaceSize = Math.min(quiltGrid.getWidth() / blocksAcross, quiltGrid.getHeight() / blocksDown);
        blockSpaceSize -= (blockSpaceSize % 3);

        for (int i = 0; i < blocksDown; i++) {
            for (int j = 0; j < blocksAcross; j++) {
                int blockSlot = j + (blocksAcross * i) + 1;
                quiltGrid.add(makeBlockSpace(blockSpaceSize, blockSlot), j, i);
            }
        }
    }

    private StackPane makeBlockSpace(double blockSpaceSize, int blockSlot) {
        StackPane blockSpace = new StackPane();

        Button deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(CANCEL));
        deleteButton.setStyle("-fx-background-color: transparent;");

        blockSpace.getChildren().add(deleteButton);
        StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);
        deleteButton.setVisible(false);
        deleteButton.setDisable(true);

        blockSpace.setOnMouseEntered(event -> deleteButton.toFront());

        blockSpace.setOnMouseExited(event -> deleteButton.toBack());

        deleteButton.setOnAction(event -> {
            quilt.removeBlock(blockSlot - 1);
            clearQuiltGrid();
            renderQuiltGrid();
            renderQuilt();
        });

        Label blockSpaceLabel = new Label(Integer.toString(blockSlot));
        blockSpace.getChildren().add(blockSpaceLabel);
        blockSpace.setStyle("-fx-border-color: grey");
        blockSpace.setPrefSize(blockSpaceSize, blockSpaceSize);
        blockSpace.setMaxSize(blockSpaceSize, blockSpaceSize);
        blockSpace.setMinSize(blockSpaceSize, blockSpaceSize);

        blockSpace.setOnDragOver(event -> {
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != blockSpace && event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        blockSpace.setOnDragEntered(event -> {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != blockSpace && event.getDragboard().hasString()) {
                blockSpaceLabel.setTextFill(Color.GREEN);
            }
            event.consume();
        });

        blockSpace.setOnDragExited(event -> {
            /* the drag-and-drop gesture entered the target */
            /* show to the user that it is an actual gesture target */
            blockSpaceLabel.setTextFill(Color.BLACK);
            event.consume();
        });

        blockSpace.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    quilt.addBlock(db.getString(), blockSlot - 1);
                    renderQuilt();
                } catch (BlockUnavailableException e) {
                    event.consume();
                }
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);
            event.consume();
        });


        return blockSpace;
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Exit", "Do you want to save your quilt before exiting?");
        if (answer) {
            saveQuilt();
        }
        window.close();
    }
}
