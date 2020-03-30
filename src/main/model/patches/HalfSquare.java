package model.patches;

import model.Quilt;

/*
Represents a half-square patch of a quilt block with an orientation, a finished size, and list of two fabrics
Half-square blocks are made up of two rectangles of fabric
Half-square block fabric assignment (un-rotated block) is as follows:
  ---- ----
 |    |    |
 | A  | B  |
 |    |    |
  ---- ----
 */

public class HalfSquare extends Patch {

    // REQUIRES: rotation is 0, 90, 180, or 270 degrees
    // EFFECTS: creates a half-square patch of given rotation (deg), with two fabrics
    public HalfSquare(int rotation) {
        super(rotation);
    }

    // EFFECTS: Creates a half-square patch; used only when deserializing patch data from JSON using GSON
    private HalfSquare() { }

    // REQUIRES: patchzSize >= 0
    // EFFECTS: calculates the total fabric (in square inches) needed to make one rectangle of the patch of given size
    @Override
    protected double calculateFabric(double patchSize) {
        double unfinishedHeightPerRectangle = patchSize + (Quilt.SEAM_ALLOWANCE * 2);
        double unfinishedWidthPerRectangle = (patchSize / 2) + (Quilt.SEAM_ALLOWANCE * 2);
        return unfinishedHeightPerRectangle * unfinishedWidthPerRectangle;
    }

    @Override
    // EFFECTS: returns the type of the patch
    public String getType() {
        return Patch.HALF_SQUARE;
    }

}
