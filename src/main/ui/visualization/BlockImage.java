package ui.visualization;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.List;

import model.blocks.Block;
import model.patches.HalfSquare;
import model.patches.HalfSquareTriangle;
import model.patches.Patch;
import model.patches.Square;
import ui.design.ColorPickerPane;

/*
Represents the image of a quilt block
 */

public class BlockImage extends GridPane {


    // EFFECTS: creates a quilt block image of given side length (in pixels) as a grid pane
    public BlockImage(Block block, double size) {
        renderBlock(block, size);
    }

    // MODIFIES: this
    // EFFECTS: renders a single block of patches
    private void renderBlock(Block block, double size) {
        List<Patch> patches = block.getPatches();
        for (int i = 0; i < patches.size(); i++) {
            Patch patch = patches.get(i);
            StackPane patchImage = renderPatch(patch, size / 3.0);
            int row = i / 3;
            int column = i % 3;
            this.add(patchImage, column, row);
        }
    }

    // EFFECTS: renders a single patch of given size (in pixels) as a stack pane
    private StackPane renderPatch(Patch patch, double size) {
        StackPane patchImage = new StackPane();
        patchImage.setAlignment(Pos.TOP_LEFT);

        Shape baseSquare = new Rectangle(size, size);
        baseSquare.setFill(ColorPickerPane.FABRIC_B_COLOUR);
        patchImage.getChildren().add(baseSquare);
        patchImage.setRotate(patch.getRotation());

        if (patch instanceof Square && patch.getFabrics().contains("A")) {
            baseSquare.setFill(ColorPickerPane.FABRIC_A_COLOUR);
        } else if (patch instanceof HalfSquare) {
            Shape halfSquare = new Rectangle(size / 2.0, size);
            halfSquare.setFill(ColorPickerPane.FABRIC_A_COLOUR);
            patchImage.getChildren().add(halfSquare);
        } else if (patch instanceof HalfSquareTriangle) {
            Shape halfTriangle = new Polygon(0.0, 0.0, 0.0, size, size, 0.0);
            halfTriangle.setFill(ColorPickerPane.FABRIC_A_COLOUR);
            patchImage.getChildren().add(halfTriangle);
        }
        return patchImage;
    }
}
