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

    // REQUIRES: fabric must be "A" or "B"
    // EFFECTS: creates a square patch of given fabric ("A" or "B")
    public Square(String fabric) {
        super(0);

        if (fabric.equals("A")) {
            fabrics.set(1, null);
        } else {
            fabrics.set(0, null);
        }
    }

    // EFFECTS: Creates a square patch; used only when deserializing patch data from JSON using GSON
    private Square() { }

    @Override
    // REQUIRES: patchzSize >= 0
    // EFFECTS: calculates the total fabric (in square inches) needed to make the patch of given size
    protected double calculateFabric(double patchSize) {
        double unfinishedSideLength = patchSize + (Quilt.SEAM_ALLOWANCE * 2);
        return unfinishedSideLength * unfinishedSideLength;
    }

    @Override
    // EFFECTS: returns the type of the patch
    public String getType() {
        return Patch.SQUARE;
    }

}
