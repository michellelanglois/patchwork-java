package util;

import model.patches.Patch;
import model.patches.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PatchPatternTest {

    private PatchPattern testPatchPattern;

    @BeforeEach
    public void runBefore() {
        testPatchPattern = new PatchPattern();
    }

    @Test
    public void testGetPatternBadName() {
        List<Patch> pattern = testPatchPattern.getPattern("greek", 4);
        assertEquals(0, pattern.size());
    }

    @Test
    public void testGetPatternGreekSquare() {
        List<Patch> pattern = testPatchPattern.getPattern("greek square", 3);
        assertEquals(9, pattern.size());

        for (Patch p : pattern) {
            assertEquals(3, p.getFinishedSideLength());
        }

        assertTrue(pattern.get(0) instanceof HalfSquareTriangle && pattern.get(0).getRotation() == 0);
        assertTrue(pattern.get(1) instanceof HalfSquare && pattern.get(1).getRotation() == 270);
        assertTrue(pattern.get(2) instanceof HalfSquareTriangle && pattern.get(2).getRotation() == 90);
        assertTrue(pattern.get(3) instanceof HalfSquare && pattern.get(3).getRotation() == 180);
        assertTrue(pattern.get(4) instanceof Square && pattern.get(4).getRotation() == 0);
        assertTrue(pattern.get(5) instanceof HalfSquare && pattern.get(5).getRotation() == 0);
        assertTrue(pattern.get(6) instanceof HalfSquareTriangle && pattern.get(6).getRotation() == 270);
        assertTrue(pattern.get(7) instanceof HalfSquare && pattern.get(7).getRotation() == 270);
        assertTrue(pattern.get(8) instanceof HalfSquareTriangle && pattern.get(8).getRotation() == 180);

    }

    @Test
    public void testGetPatternFriendshipStar() {
        List<Patch> pattern = testPatchPattern.getPattern("friendship star", 4);
        assertEquals(9, pattern.size());

        for (Patch p : pattern) {
            assertEquals(4, p.getFinishedSideLength());
        }

        assertTrue(pattern.get(0) instanceof Square && pattern.get(0).containsFabric("A"));
        assertTrue(pattern.get(1) instanceof HalfSquareTriangle && pattern.get(1).getRotation() == 270);
        assertTrue(pattern.get(2) instanceof Square && pattern.get(2).containsFabric("A"));
        assertTrue(pattern.get(3) instanceof HalfSquareTriangle && pattern.get(3).getRotation() == 180);
        assertTrue(pattern.get(4) instanceof Square && pattern.get(4).containsFabric("B"));
        assertTrue(pattern.get(5) instanceof HalfSquareTriangle && pattern.get(5).getRotation() == 0);
        assertTrue(pattern.get(6) instanceof Square && pattern.get(6).containsFabric("A"));
        assertTrue(pattern.get(7) instanceof HalfSquareTriangle && pattern.get(7).getRotation() == 90);
        assertTrue(pattern.get(8) instanceof Square && pattern.get(8).containsFabric("A"));
    }

    @Test
    public void testGetPatternCheckerboard() {
        List<Patch> pattern = testPatchPattern.getPattern("checkerboard", 2.5);
        assertEquals(9, pattern.size());

        for (Patch p : pattern) {
            assertEquals(2.5, p.getFinishedSideLength());
            assertEquals(0, p.getRotation());
        }

        assertTrue(pattern.get(0) instanceof Square && pattern.get(0).containsFabric("A"));
        assertTrue(pattern.get(1) instanceof Square && pattern.get(1).containsFabric("B"));
        assertTrue(pattern.get(2) instanceof Square && pattern.get(2).containsFabric("A"));
        assertTrue(pattern.get(3) instanceof Square && pattern.get(3).containsFabric("B"));
        assertTrue(pattern.get(4) instanceof Square && pattern.get(4).containsFabric("A"));
        assertTrue(pattern.get(5) instanceof Square && pattern.get(5).containsFabric("B"));
        assertTrue(pattern.get(6) instanceof Square && pattern.get(6).containsFabric("A"));
        assertTrue(pattern.get(7) instanceof Square && pattern.get(7).containsFabric("B"));
        assertTrue(pattern.get(8) instanceof Square && pattern.get(8).containsFabric("A"));
    }

}
