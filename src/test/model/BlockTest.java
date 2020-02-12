package model;

import model.blocks.Block;
import model.blocks.BlockType;
import model.patches.Patch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest {

    private Block greekSquareBlock;
    private Block friendshipStarBlock;
    private Block checkerboardBlock;

    @BeforeEach
    public void runBefore() {
        greekSquareBlock = new Block(BlockType.GREEK_SQUARE, 12);
        friendshipStarBlock = new Block(BlockType.FRIENDSHIP_STAR, 7.5);
        checkerboardBlock = new Block(BlockType.CHECKERBOARD, 6);
    }

    @Test
    public void testConstructorKnownBlock() {
        assertEquals("greek square", greekSquareBlock.getBlockType());
        assertEquals(12, greekSquareBlock.getFinishedSize());
        assertEquals(9, greekSquareBlock.getPatches().size());
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
        assertEquals(68, friendshipStarBlock.calculateFabric("A"));
        assertEquals(41, friendshipStarBlock.calculateFabric("B"));
    }

    @Test
    public void testCalculateFabricContainsFabricGreekSquare() {
        assertEquals(105.5, greekSquareBlock.calculateFabric("A"));
        assertEquals(125.75, greekSquareBlock.calculateFabric("B"));
    }

    @Test
    public void testCalculateFabricsDoesNotContainsFabric() {
        assertEquals(0, checkerboardBlock.calculateFabric("C"));
    }

}
