package model.patches;

import model.Patch;
import model.Quilt;

/*
Represents a half-square patch of a quilt block with an orientation, a finished size, and list of two fabrics
Half-square blocks are made up of two rectangles of fabric
Half-square block fabric assignment (unrotated block) is as follows:
  ---- ----
 |    |    |
 | A  | B  |
 |    |    |
  ---- ----
 */

public class HalfSquare extends Patch {

    // REQUIRES: rotation is 0, 90, 180, or 270 degrees
    //           finishedSideLength is pre-determined by the block size and number of patches (passed in from block)
    // EFFECTS: creates a half-square patch of given size (inches) & rotation (deg), with two fabrics
    public HalfSquare(double finishedSideLength, int rotation) {
        super(finishedSideLength, rotation);
        this.type = HALF_SQUARE;
    }

    // EFFECTS: calculates the total fabric (in square inches) needed to make one rectangle of the patch
    @Override
    protected double getCalculation() {
        double heightPerRectangle = finishedSideLength + (Quilt.SEAM_ALLOWANCE * 2);
        double widthPerRectangle = (finishedSideLength / 2) + (Quilt.SEAM_ALLOWANCE * 2);
        return heightPerRectangle * widthPerRectangle;
    }

}
