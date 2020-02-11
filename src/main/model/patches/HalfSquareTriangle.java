package model.patches;

import model.Quilt;

/*
Represents a half-square triangle patch of a quilt block, with an orientation, a finished size, and list of two fabrics
Half-square triangle blocks are made up of two right-angle triangles of fabric
Half-square triangle block fabric assignment (unrotated block) is as follows:
  ---- ----
 |     /   |
 | A  /    |
 |   /  B  |
  ---- ----
 */

public class HalfSquareTriangle extends Patch {

    // REQUIRES: rotation is 0, 90, 180, or 270 degrees
    //           finishedSideLength is pre-determined by the block size and number of patches (passed in from block)
    // EFFECTS: creates a half-square triangle patch of given side length (inches) & rotation (deg), with two fabrics
    public HalfSquareTriangle(double finishedSideLength, int rotation) {
        super(finishedSideLength, rotation);
        this.type = HALF_TRIANGLE;
    }

    // EFFECTS: calculates the total fabric (in square inches) needed to make one triangle of the patch
    @Override
    protected double getCalculation() {
        double unfinishedSideLength = finishedSideLength + (Quilt.SEAM_ALLOWANCE * 2);
        double startingSquareSideLength = unfinishedSideLength + 1;
        return startingSquareSideLength * startingSquareSideLength / 2;
    }
}
