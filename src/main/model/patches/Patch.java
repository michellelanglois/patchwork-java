package model.patches;

import java.util.ArrayList;
import java.util.List;

/*
Represents one patch of a quilt block, with an orientation, a finished size, and list of up to two fabrics
Fabrics are initially hard-coded to "A" and "B"; this will be augmented in future versions
 */

public abstract class Patch {

    public static final String HALF_TRIANGLE = "HalfSquareTriangle";
    public static final String HALF_SQUARE = "HalfSquare";
    public static final String SQUARE = "Square";

    protected double finishedSideLength;
    protected int rotation;
    protected List<String> fabrics;


    // REQUIRES: rotation is 0, 90, 180, or 270 degrees
    //           finishedSideLength > 0; pre-determined by the block size and number of patches (passed in from block)
    // EFFECTS: creates a patch of the given size (in inches), and rotation (in degrees), with up to two fabrics
    protected Patch(double finishedSideLength, int rotation) {
        this.finishedSideLength = finishedSideLength;
        this.rotation = rotation;
        this.fabrics = new ArrayList<>(2);

        fabrics.add("A");
        fabrics.add("B");
    }

    // EFFECTS: Creates a patch; used only when deserializing patch data from JSON using GSON
    protected Patch() { }

    //getters
    public double getFinishedSideLength() {
        return finishedSideLength;
    }

    public int getRotation() {
        return rotation;
    }

    public List<String> getFabrics() {
        return fabrics;
    }

    // setters
    public void setFinishedSideLength(double sideLength) {
        this.finishedSideLength = sideLength;
    }

    // EFFECTS: returns true if patch contains given fabric, false otherwise
    public boolean containsFabric(String fabric) {
        return fabrics.contains(fabric);
    }

    // EFFECTS: returns total fabric (in square inches) needed of given fabric to make the patch
    public double calculateFabric(String fabric) {
        if (containsFabric(fabric)) {
            return calculateFabric();
        } else {
            return 0;
        }
    }

    // subclass-specific calculations for total fabric needed
    protected abstract double calculateFabric();

    // subclass-specific type information
    public abstract String getType();

}
