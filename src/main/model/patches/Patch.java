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

    protected int rotation;
    protected List<String> fabrics;


    // REQUIRES: rotation is 0, 90, 180, or 270 degrees
    // EFFECTS: creates a patch of the given rotation (in degrees), with up to two fabrics
    protected Patch(int rotation) {
        this.rotation = rotation;
        this.fabrics = new ArrayList<>(2);

        fabrics.add("A");
        fabrics.add("B");
    }

    // EFFECTS: Creates a patch; used only when deserializing patch data from JSON using GSON
    protected Patch() { }

    //getters
    public int getRotation() {
        return rotation;
    }

    public List<String> getFabrics() {
        return fabrics;
    }

    // EFFECTS: returns true if patch contains given fabric, false otherwise
    public boolean containsFabric(String fabric) {
        return fabrics.contains(fabric);
    }

    // REQUIRES: patchzSize >= 0
    // EFFECTS: returns total fabric (in square inches) needed of given fabric to make the patch of given size
    public double calculateFabric(String fabric, double patchSize) {
        if (containsFabric(fabric)) {
            return calculateFabric(patchSize);
        } else {
            return 0;
        }
    }

    // REQUIRES: patchzSize >= 0
    // subclass-specific calculations for total fabric needed
    protected abstract double calculateFabric(double patchSize);

    // subclass-specific type information
    public abstract String getType();

}
