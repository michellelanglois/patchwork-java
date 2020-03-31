package ui.visualization;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import model.blocks.Block;
import ui.QuiltApp;

import java.util.ArrayList;
import java.util.List;

/*
Represents the quilt grid visualization area of the application, where users can add and delete blocks and see
what their quilt looks like
 */

public class QuiltGridPane extends GridPane {

    private QuiltApp quiltApp;
    private List<BlockSpacePane> blockSpaces;

    // EFFECTS: creates an empty QuiltGridUI pane
    public QuiltGridPane(QuiltApp quiltApp) {
        this.quiltApp = quiltApp;
        this.blockSpaces = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: resets the QuiltGridUI to initial state, ready for a new quilt
    public void clearQuiltGrid() {
        this.getChildren().clear();
        blockSpaces.clear();
    }

    // MODIFIES: this
    // EFFECTS: initializes a new quilt grid of block spaces of appropriate size for current quilt
    public void initializeQuiltGrid(int blocksAcross, int blocksDown) {
        double blockSpaceSize = Math.min(this.getWidth() / blocksAcross, this.getHeight() / blocksDown);
        blockSpaceSize -= (blockSpaceSize % 3);

        clearQuiltGrid();

        this.setAlignment(Pos.CENTER);

        for (int i = 0; i < blocksDown; i++) {
            for (int j = 0; j < blocksAcross; j++) {
                int blockIndex = j + (blocksAcross * i);
                BlockSpacePane blockSpace = new BlockSpacePane(quiltApp, blockSpaceSize, blockIndex);
                blockSpaces.add(blockSpace);
                this.add(blockSpace, j, i);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: renders an image of the block at given index on the appropriate block space
    public void renderBlock(int blockIndex, Block block) {
        blockSpaces.get(blockIndex).paintBlockSpace(block);
    }

    // MODIFIES: this
    // EFFECTS: resets the given block space
    public void resetBlockSpace(int blockIndex) {
        blockSpaces.get(blockIndex).resetBlockSpace();
    }

    // MODIFIES: this
    // EFFECTS: renders an image of the current quilt onto quilt grid
    public void renderQuilt(List<Block> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            renderBlock(i, blocks.get(i));
        }
    }
}
