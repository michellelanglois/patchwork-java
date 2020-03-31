package ui.design;

import exceptions.BlockUnavailableException;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.blocks.Block;
import model.blocks.BlockMap;
import ui.visualization.BlockImage;

public class BlockChooserPane extends VBox {

    public BlockChooserPane(int height) {
        initializeLayout(height);
    }

    // EFFECTS: creates and returns a pane where users can choose blocks for their quilt
    private void initializeLayout(int height) {
        this.getStyleClass().add("vbox");

        Label blockChooserLabel = new Label("Choose your favourite. Add a block to your quilt.");
        blockChooserLabel.getStyleClass().add("design-label");

        ScrollPane blockScroller = initializeBlockScroller(height);

        this.getChildren().addAll(blockChooserLabel, blockScroller);
    }

    // EFFECTS: creates and returns a scroll bar with available blocks
    private ScrollPane initializeBlockScroller(int height) {
        ScrollPane blockScroller = new ScrollPane();
        Tooltip tooltip = new Tooltip("Drag and drop blocks to design your quilt");
        Tooltip.install(blockScroller, tooltip);

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
        for (String blockName : BlockMap.listAvailableBlocks()) {
            try {
                Block block = new Block(blockName);
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
