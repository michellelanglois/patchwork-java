package util;

import model.Patch;
import model.patches.*;

import java.util.ArrayList;
import java.util.List;

/*
Pre-set patterns of patches for common quilting blocks
 */

public class PatchPattern {

    // REQUIRES: size must be > 0, name must be in Blocks.AVAILABLE_BLOCKS
    // EFFECTS: returns the patch pattern for the given block name if pattern has been coded below, else empty list
    public List<Patch> getPattern(String name, double size) {
        if (name.equals("greek square")) {
            return getGreekSquarePattern(size);
        } else if (name.equals("checkerboard")) {
            return getCheckerboardPattern(size);
        } else if (name.equals("friendship star")) {
            return getFriendshipStarPattern(size);
        } else {
            return new ArrayList<>();
        }
    }

    // REQUIRES: size > 0
    // EFFECTS: returns list of patches that make up a greek square block, in the given size
    //    ......**........**......
    //    ....****........****....
    //    ...******************...
    //    .**********************.
    //    ....****........****....
    //    ....****........****....
    //    ....****........****....
    //    ....****........****....
    //    .**********************.
    //    ...******************...
    //    ....****........****....
    //    ......**........**......
    public List<Patch> getGreekSquarePattern(double size) {
        ArrayList<Patch> greekSquarePatches = new ArrayList<>();
        greekSquarePatches.add(new HalfSquareTriangle(size, 0));
        greekSquarePatches.add(new HalfSquare(size, 270));
        greekSquarePatches.add(new HalfSquareTriangle(size, 90));
        greekSquarePatches.add(new HalfSquare(size, 180));
        greekSquarePatches.add(new Square(size, "B"));
        greekSquarePatches.add(new HalfSquare(size, 0));
        greekSquarePatches.add(new HalfSquareTriangle(size, 270));
        greekSquarePatches.add(new HalfSquare(size, 270));
        greekSquarePatches.add(new HalfSquareTriangle(size, 180));

        return greekSquarePatches;
    }

    // REQUIRES: size > 0
    // EFFECTS: returns list of patches that make up a checkerboard block, in the given size
    //    ********........********
    //    ********........********
    //    ********........********
    //    ********........********
    //    ........********........
    //    ........********........
    //    ........********........
    //    ........********........
    //    ********........********
    //    ********........********
    //    ********........********
    //    ********........********
    public List<Patch> getCheckerboardPattern(double size) {
        ArrayList<Patch> checkerboardPatches = new ArrayList<>();
        checkerboardPatches.add(new Square(size, "A"));
        checkerboardPatches.add(new Square(size, "B"));
        checkerboardPatches.add(new Square(size, "A"));
        checkerboardPatches.add(new Square(size, "B"));
        checkerboardPatches.add(new Square(size, "A"));
        checkerboardPatches.add(new Square(size, "B"));
        checkerboardPatches.add(new Square(size, "A"));
        checkerboardPatches.add(new Square(size, "B"));
        checkerboardPatches.add(new Square(size, "A"));

        return checkerboardPatches;
    }

    // REQUIRES: size > 0
    // EFFECTS: returns list of patches that make up a friendship star block, in the given size
    //    ********.***************
    //    ********...*************
    //    ********....************
    //    ********......**********
    //    *******...............**
    //    *****...............****
    //    ****...............*****
    //    **...............*******
    //    **********......********
    //    ************....********
    //    *************...********
    //    ***************.********
    public List<Patch> getFriendshipStarPattern(double size) {
        ArrayList<Patch> friendshipStarPatches = new ArrayList<>();
        friendshipStarPatches.add(new Square(size, "A"));
        friendshipStarPatches.add(new HalfSquareTriangle(size,270));
        friendshipStarPatches.add(new Square(size, "A"));
        friendshipStarPatches.add(new HalfSquareTriangle(size, 180));
        friendshipStarPatches.add(new Square(size, "B"));
        friendshipStarPatches.add(new HalfSquareTriangle(size, 0));
        friendshipStarPatches.add(new Square(size, "A"));
        friendshipStarPatches.add(new HalfSquareTriangle(size, 90));
        friendshipStarPatches.add(new Square(size, "A"));

        return friendshipStarPatches;
    }

}
