package model.patches;

import model.Patch;
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

    private String style;

    // REQUIRES: style must be "A" or "B"
    //           finishedSideLength is pre-determined by the block size and number of patches (passed in from block)
    // EFFECTS: creates a square patch of given size (in inches) and style ("A" or "B")
    public Square(double finishedSideLength, String style) {
        super(finishedSideLength, 0);
        this.style = style;
        this.type = SQUARE;

        if (style.equals("A")) {
            fabrics.set(1, null);
        } else {
            fabrics.set(0, null);
        }
    }

    @Override
    // EFFECTS: calculates the total fabric (in square inches) needed to make the patch
    protected double getCalculation() {
        double unfinishedSideLength = finishedSideLength + (Quilt.SEAM_ALLOWANCE * 2);
        return unfinishedSideLength * unfinishedSideLength;
    }

}
