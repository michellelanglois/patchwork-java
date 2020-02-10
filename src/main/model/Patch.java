package model;

import java.util.ArrayList;
import java.util.List;

/*
Represents one patch of a quilt block, with an orientation, a finished size, and list of up to two fabrics
Fabrics are initially hard-coded to "A" and "B"; this will be augmented in future versions
 */

public abstract class Patch {

    public static final String HALF_TRIANGLE = "HST";
    public static final String HALF_SQUARE = "HSQ";
    public static final String SQUARE = "SQR";

    protected String type;
    protected double finishedSideLength;
    protected int rotation;
    protected List<String> fabrics;


    // REQUIRES: rotation is 0, 90, 180, or 270 degrees
    //           finishedSideLength is pre-determined by the block size and number of patches (passed in from block)
    // EFFECTS: creates a patch of the given type, size (in inches), and rotation (in degrees), with up to two fabrics
    //          patch type is filled in by subclass constructors
    protected Patch(double finishedSideLength, int rotation) {
        this.type = null;
        this.finishedSideLength = finishedSideLength;
        this.rotation = rotation;
        this.fabrics = new ArrayList<String>(2);

        fabrics.add("A");
        fabrics.add("B");
    }

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

    public String getType() {
        return type;
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
            return getCalculation();
        } else {
            return 0;
        }
    }

    // subclass-specific calculations for total fabric needed
    protected abstract double getCalculation();

}
