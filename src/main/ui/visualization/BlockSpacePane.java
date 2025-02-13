package ui.visualization;

import exceptions.BlockUnavailableException;
import exceptions.SlotOutOfBoundsException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import model.blocks.Block;
import ui.QuiltApp;

/*
Represents a single block space in the quilt grid, capable of having blocks added and removed from it
 */

public class BlockSpacePane extends StackPane {

    private static final Image DELETE_ICON = new Image("file:./data/icons/delete.png");

    private QuiltApp quiltApp;
    private Button deleteButton;
    private double blockSpaceSize;
    private int blockIndex;

    // MODIFIES: this
    // EFFECTS: creates a block space with specific index link to quilt blocks list and methods to add and remove blocks
    public BlockSpacePane(QuiltApp quiltApp, double blockSpaceSize, int blockIndex) {
        this.quiltApp = quiltApp;
        this.blockSpaceSize = blockSpaceSize;
        this.blockIndex = blockIndex;
        this.deleteButton = initializeDeleteButton();
        makeBlockSpace();
    }

    // MODIFIES: this
    // EFFECTS: creates a block space with all associated event handlers
    private void makeBlockSpace() {
        setBlockSpaceLayout();
        setBlockSpacePreDropEventHandlers();
        setBlockSpaceDropEventHandler();
    }

    // MODIFIES: this
    // EFFECTS: creates a block space with a label and delete button
    private void setBlockSpaceLayout() {
        this.getStyleClass().add("block-space");

        this.setPrefSize(blockSpaceSize, blockSpaceSize);
        this.setMaxSize(blockSpaceSize, blockSpaceSize);
        this.setMinSize(blockSpaceSize, blockSpaceSize);

        StackPane.setAlignment(deleteButton, Pos.TOP_RIGHT);

        Label blockSpaceLabel = new Label(Integer.toString(blockIndex + 1));

        this.getChildren().addAll(deleteButton, blockSpaceLabel);
    }

    // MODIFIES: this
    // EFFECTS: sets ability for block space to accept a dragged quilt block and add to the visualization
    // NOTE: code for .setOnDrag* methods copied from Oracle Drag and Drop Tutorial
    //       available from https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
    private void setBlockSpacePreDropEventHandlers() {
        this.setOnDragOver(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                this.setStyle("-fx-border-color: #d197fc;");
            }
            event.consume();
        });

        this.setOnDragExited(event -> {
            this.setStyle("-fx-border-color: #bbc2cf;");
            event.consume();
        });
    }

    // MODIFIES: this
    // EFFECTS: sets ability for block space to accept a dragged quilt block and add to the visualization
    // NOTE: code for .setOnDrag* methods copied from Oracle Drag and Drop Tutorial
    //       available from https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm
    private void setBlockSpaceDropEventHandler() {
        this.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    quiltApp.handleAddBlockToQuilt(db.getString(), blockIndex);
                } catch (BlockUnavailableException | SlotOutOfBoundsException e) {
                    event.consume();
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a delete button visible only on hover, button invisible & disabled when initialized
    private Button initializeDeleteButton() {
        deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(DELETE_ICON));
        deleteButton.setStyle("-fx-background-color: transparent;");
        disableDeleteButton();

        deleteButton.setOnAction(event -> {
            try {
                quiltApp.handleRemoveBlockFromQuilt(blockIndex);
            } catch (SlotOutOfBoundsException e) {
                event.consume();
            }
        });

        this.setOnMouseEntered(event -> deleteButton.toFront());
        this.setOnMouseExited(event -> deleteButton.toBack());

        return deleteButton;
    }

    // MODIFIES: this
    // EFFECTS: disables delete button (so it cannot be used when a quilt block is not present in block space)
    private void disableDeleteButton() {
        deleteButton.setVisible(false);
        deleteButton.setDisable(true);
    }

    // MODIFIES: this
    // EFFECTS: enables the delete button so it can be used when a quilt block is present in the block space
    private void enableDeleteButton() {
        deleteButton.setVisible(true);
        deleteButton.setDisable(false);
    }

    // EFFECTS: resets block space to initial state, without any quilt block images
    public void resetBlockSpace() {
        this.getChildren().clear();
        this.setBlockSpaceLayout();
        disableDeleteButton();
    }

    // MODIFIES: this
    // EFFECTS: adds image of given block to block space and enables delete button so block can be removed
    public void paintBlockSpace(Block block) {
        if (block != null) {
            if (this.getChildren().size() > 2) {
                resetBlockSpace();
            }
            BlockImage blockImage = new BlockImage(block, blockSpaceSize);
            blockImage.setPrefSize(blockSpaceSize, blockSpaceSize);
            this.getChildren().add(blockImage);
            enableDeleteButton();
        }
    }

}
