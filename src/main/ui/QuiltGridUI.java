package ui;

import exceptions.BlockUnavailableException;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import model.Quilt;

import java.util.ArrayList;
import java.util.List;

/*
Represents the quilt grid visualization area of the application, where users can add and delete blocks and see
what their quilt looks like
 */

public class QuiltGridUI extends GridPane {

    QuiltAppGUI quiltAppGUI;
    List<BlockSpace> blockSpaces;
    Quilt quilt;

    // EFFECTS: creates an empty QuiltGridUI pane
    protected QuiltGridUI(QuiltAppGUI quiltAppGUI) {
        this.quiltAppGUI = quiltAppGUI;
        this.blockSpaces = new ArrayList<>();
        this.quilt = null;
    }

    // MODIFIES: this
    // EFFECTS: resets the QuiltGridUI to initial state, ready for a new quilt
    protected void clearQuiltGrid() {
        this.getChildren().clear();
        blockSpaces.clear();
    }

    // MODIFIES: this
    // EFFECTS: initializes a new quilt grid of block spaces of appropriate size for current quilt
    protected void initializeQuiltGrid() {
        quilt = quiltAppGUI.getQuilt();
        int blocksAcross = quilt.getNumBlocksAcross();
        int blocksDown = quilt.getNumBlocksDown();
        double blockSpaceSize = Math.min(this.getWidth() / blocksAcross, this.getHeight() / blocksDown);
        blockSpaceSize -= (blockSpaceSize % 3);

        clearQuiltGrid();

        this.setAlignment(Pos.CENTER);

        for (int i = 0; i < blocksDown; i++) {
            for (int j = 0; j < blocksAcross; j++) {
                int blockIndex = j + (blocksAcross * i);
                BlockSpace blockSpace = new BlockSpace(this, blockSpaceSize, blockIndex);
                blockSpaces.add(blockSpace);
                this.add(blockSpace, j, i);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a block to quilt and renders the block
    public void addBlockToQuilt(String blockName, int blockIndex) throws BlockUnavailableException {
        quilt.addBlock(blockName, blockIndex);
        renderBlock(blockIndex);
    }

    // MODIFIES: this
    // EFFECTS: removes a block from the quilt and resets the appropriate block space to initial state
    public void removeBlockFromQuilt(int blockIndex) {
        quilt.removeBlock(blockIndex);
        blockSpaces.get(blockIndex).resetBlockSpace();
    }

    // MODIFIES: this
    // EFFECTS: renders an image of the block at given index on the appropriate block space
    private void renderBlock(int blockIndex) {
        BlockSpace blockSpace = blockSpaces.get(blockIndex);
        blockSpace.paintBlockSpace(quilt.getBlocks().get(blockIndex));
    }

    // MODIFIES: this
    // EFFECTS: renders an image of the current quilt onto quilt grid
    public void renderQuilt() {
        if (quilt != null) {
            for (int i = 0; i < blockSpaces.size(); i++) {
                renderBlock(i);
            }
        }
    }
}
