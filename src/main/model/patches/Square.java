package model.patches;

import model.Quilt;

/*
Represents a square patch of a quilt block, with an orientation, a finished size, and list of one fabric
Square blocks are made up of a single square of fabric, based on the style parameter (style A or style B)
Square blocks always start off not rotated, unlike half-squares and half-square triangles
  ---- ----
 |         |
 | A or B  |
 |         |
  ---- ----
 */

public class Square extends Patch {

    // REQUIRES: style must be "A" or "B"
    //           finishedSideLength is pre-determined by the block size and number of patches (passed in from block)
    // EFFECTS: creates a square patch of given size (in inches) and style ("A" or "B")
    public Square(double finishedSideLength, String fabric) {
        super(finishedSideLength, 0);

        if (fabric.equals("A")) {
            fabrics.set(1, null);
        } else {
            fabrics.set(0, null);
        }
    }

    // EFFECTS: Creates a square patch; used only when deserializing patch data from JSON using GSON
    private Square() { }

    @Override
    // EFFECTS: calculates the total fabric (in square inches) needed to make the patch
    protected double calculateFabric() {
        double unfinishedSideLength = finishedSideLength + (Quilt.SEAM_ALLOWANCE * 2);
        return unfinishedSideLength * unfinishedSideLength;
    }

    @Override
    // EFFECTS: returns the type of the patch
    public String getType() {
        return Patch.SQUARE;
    }

}
