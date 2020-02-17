package util;

import model.blocks.BlockType;
import model.patches.Patch;
import model.patches.*;

import java.util.ArrayList;
import java.util.List;

/*
Pre-set patterns of patches for common quilting blocks
 */

public class PatchPattern {

    // REQUIRES: size must be > 0
    // EFFECTS: returns the patch pattern for the given block name if pattern has been coded below, else empty list
    public List<Patch> getPattern(BlockType blockType, double size) {
        if (blockType == BlockType.GREEK_SQUARE) {
            return getGreekSquarePattern(size);
        } else if (blockType == BlockType.CHECKERBOARD) {
            return getCheckerboardPattern(size);
        } else {
            return getFriendshipStarPattern(size);
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
        greekSquarePatches.add(new Square(size, "A"));
        greekSquarePatches.add(new HalfSquare(size, 0));
        greekSquarePatches.add(new HalfSquareTriangle(size, 270));
        greekSquarePatches.add(new HalfSquare(size, 270));
        greekSquarePatches.add(new HalfSquareTriangle(size, 180));

        return greekSquarePatches;
    }

    // REQUIRES: size > 0
    // EFFECTS: returns list of patches that make up a checkerboard block, in the given size
    //    ........********........
    //    ........********........
    //    ........********........
    //    ........********........
    //    ********........********
    //    ********........********
    //    ********........********
    //    ********........********
    //    ........********........
    //    ........********........
    //    ........********........
    //    ........********........

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
        friendshipStarPatches.add(new Square(size, "B"));
        friendshipStarPatches.add(new HalfSquareTriangle(size,270));
        friendshipStarPatches.add(new Square(size, "B"));
        friendshipStarPatches.add(new HalfSquareTriangle(size, 180));
        friendshipStarPatches.add(new Square(size, "A"));
        friendshipStarPatches.add(new HalfSquareTriangle(size, 0));
        friendshipStarPatches.add(new Square(size, "B"));
        friendshipStarPatches.add(new HalfSquareTriangle(size, 90));
        friendshipStarPatches.add(new Square(size, "B"));

        return friendshipStarPatches;
    }

}
