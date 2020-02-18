package model;

import exceptions.BlockUnavailableException;
import model.blocks.Block;
import model.patches.Patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {

    private Block greekSquareBlock;
    private Block friendshipStarBlock;
    private Block checkerboardBlock;

    @BeforeEach
    public void runBefore() {
        try {
            greekSquareBlock = new Block("greek square", 12);
            friendshipStarBlock = new Block("friendship star", 7.5);
            checkerboardBlock = new Block("checkerboard", 6);
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testConstructorKnownBlock() {
        assertEquals("greek square", greekSquareBlock.getBlockType());
        assertEquals(12, greekSquareBlock.getFinishedSize());
        assertEquals(9, greekSquareBlock.getPatches().size());
        assertEquals(4, greekSquareBlock.countPatches(Patch.HALF_TRIANGLE));
        assertEquals(4, greekSquareBlock.countPatches(Patch.HALF_SQUARE));
        assertEquals(1, greekSquareBlock.countPatches(Patch.SQUARE));
        for (Patch p : greekSquareBlock.getPatches()) {
            assertEquals(4, p.getFinishedSideLength());
        }
    }

    @Test
    public void testConstructorUnknownBlock() {
        try {
            Block unknownBlock = new Block("amistake", 6);
            fail("BlockUnavailableException should have been thrown");
        } catch (BlockUnavailableException e) {
            // all good
        }
    }

    @Test
    public void testGetFinishedPatchSize() {
        assertEquals(4.0, greekSquareBlock.getFinishedPatchSize());
        assertEquals(2.5, friendshipStarBlock.getFinishedPatchSize());
        assertEquals(2.0, checkerboardBlock.getFinishedPatchSize());
    }

    @Test
    public void testCountPatchesContainsPatch() {
        assertEquals(4, greekSquareBlock.countPatches(Patch.HALF_TRIANGLE));
        assertEquals(4, greekSquareBlock.countPatches(Patch.HALF_SQUARE));
        assertEquals(1, greekSquareBlock.countPatches(Patch.SQUARE));
    }

    @Test
    public void testCountPatchesDoesNotContainsPatch() {
        assertEquals(0, checkerboardBlock.countPatches(Patch.HALF_TRIANGLE));
        assertEquals(0, greekSquareBlock.countPatches("abd"));
    }

    @Test
    public void testCalculateFabricContainsFabricCheckerboard() {
        assertEquals((5 * 2.5 * 2.5), checkerboardBlock.calculateFabric("A"));
        assertEquals((4 * 2.5 * 2.5), checkerboardBlock.calculateFabric("B"));
    }

    @Test
    public void testCalculateFabricContainsFabricFriendshipStar() {
        assertEquals(41, friendshipStarBlock.calculateFabric("A"));
        assertEquals(68, friendshipStarBlock.calculateFabric("B"));
    }

    @Test
    public void testCalculateFabricContainsFabricGreekSquare() {
        assertEquals(125.75, greekSquareBlock.calculateFabric("A"));
        assertEquals(105.5, greekSquareBlock.calculateFabric("B"));
    }

    @Test
    public void testCalculateFabricsDoesNotContainsFabric() {
        assertEquals(0, checkerboardBlock.calculateFabric("C"));
    }

    @Test
    public void testGetPatchesFromPatternBadFile() {
        try {
            greekSquareBlock.getPatchesFromPattern("corruptedFile");
        } catch (BlockUnavailableException e) {
            // all good
        }
    }

}
