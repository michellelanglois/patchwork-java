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
    // EFFECTS: creates a half-square triangle patch of given rotation (deg), with two fabrics
    public HalfSquareTriangle(int rotation) {
        super(rotation);
    }

    // EFFECTS: Creates a half-square triangle patch; used only when deserializing patch data from JSON using GSON
    private HalfSquareTriangle() { }

    @Override
    // REQUIRES: patchSize >= 0
    // EFFECTS: calculates the total fabric (in square inches) needed to make one triangle of the patch
    protected double calculateFabric(double patchSize) {
        double unfinishedSideLength = patchSize + (Quilt.SEAM_ALLOWANCE * 2);
        double startingSquareSideLength = unfinishedSideLength + 1;
        return startingSquareSideLength * startingSquareSideLength / 2;
    }

    @Override
    // EFFECTS: returns the type of the patch
    public String getType() {
        return Patch.HALF_TRIANGLE;
    }
}
