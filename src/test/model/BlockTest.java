package model;

import exceptions.BlockUnavailableException;
import model.blocks.Block;
import model.patches.Patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {

    private Block greekSquareBlock;
    private Block friendshipStarBlock;
    private Block checkerboardBlock;

    @BeforeEach
    public void runBefore() {
        try {
            greekSquareBlock = new Block("greek square");
            friendshipStarBlock = new Block("friendship star");
            checkerboardBlock = new Block("checkerboard");
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testConstructorKnownBlock() {
        assertEquals("greek square", greekSquareBlock.getBlockType());
        assertEquals(9, greekSquareBlock.getPatches().size());
        assertEquals(4, greekSquareBlock.countPatches(Patch.HALF_TRIANGLE));
        assertEquals(4, greekSquareBlock.countPatches(Patch.HALF_SQUARE));
        assertEquals(1, greekSquareBlock.countPatches(Patch.SQUARE));
    }

    @Test
    public void testConstructorUnknownBlock() {
        try {
            Block unknownBlock = new Block("amistake");
            fail("BlockUnavailableException should have been thrown");
        } catch (BlockUnavailableException e) {
            // all good
        }
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
        assertEquals((5 * 2.5 * 2.5), checkerboardBlock.calculateFabric("A", 6));
        assertEquals((4 * 2.5 * 2.5), checkerboardBlock.calculateFabric("B", 6));
    }

    @Test
    public void testCalculateFabricContainsFabricFriendshipStar() {
        assertEquals(41, friendshipStarBlock.calculateFabric("A", 7.5));
        assertEquals(68, friendshipStarBlock.calculateFabric("B", 7.5));
    }

    @Test
    public void testCalculateFabricContainsFabricGreekSquare() {
        assertEquals(125.75, greekSquareBlock.calculateFabric("A", 12));
        assertEquals(105.5, greekSquareBlock.calculateFabric("B", 12));
    }

    @Test
    public void testCalculateFabricsDoesNotContainsFabric() {
        assertEquals(0, checkerboardBlock.calculateFabric("C", 10));
    }

}
