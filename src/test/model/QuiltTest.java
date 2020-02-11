package model;

import model.patches.Patch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuiltTest {

    Quilt testQuilt;

    @BeforeEach
    public void runBefore() {
        testQuilt = new Quilt(4, 5,6.0);
    }

    @Test
    public void testConstructor() {
        assertEquals(20, testQuilt.getNumBlocksAcross() * testQuilt.getNumBlocksDown());
        assertEquals(20, testQuilt.getBlocks().size());
        for (Block b: testQuilt.getBlocks()) {
            assertNull(b);
        }
    }

    @Test
    public void testAddBlockToEmptyQuiltPos0() {
        testQuilt.addBlock("friendship star", 0);
        Block targetBlockSlot = testQuilt.getBlocks().get(0);
        assertEquals("friendship star", targetBlockSlot.getName());
        assertEquals(6.0, targetBlockSlot.getFinishedSize());
        assertEquals(4, targetBlockSlot.countPatches(Patch.HALF_TRIANGLE));
        for (int i = 1; i < testQuilt.getTotalBlocks(); i++) {
            assertNull(testQuilt.getBlocks().get(i));
        }
    }

    @Test
    public void testAddBlockToEmptyQuiltPosInMiddle() {
        testQuilt.addBlock("checkerboard", 6);
        Block targetBlockSlot = testQuilt.getBlocks().get(6);
        assertEquals("checkerboard", targetBlockSlot.getName());
        assertEquals(6.0, targetBlockSlot.getFinishedSize());
        assertEquals(0, targetBlockSlot.countPatches(Patch.HALF_SQUARE));
        for (int i = 0; i < 6; i++) {
            assertNull(testQuilt.getBlocks().get(i));
        }
        for (int i = 7; i < testQuilt.getTotalBlocks(); i++) {
            assertNull(testQuilt.getBlocks().get(i));
        }
    }

    @Test
    public void testAddBlockToNonEmptyQuiltPos() {
        testQuilt.addBlock("checkerboard", 6);
        Block targetBlockSlot = testQuilt.getBlocks().get(6);
        assertEquals("checkerboard", targetBlockSlot.getName());
        testQuilt.addBlock("greek square", 6);
        Block newTargetBlockSlot = testQuilt.getBlocks().get(6);
        assertEquals("greek square", newTargetBlockSlot.getName());
        assertEquals(6.0, newTargetBlockSlot.getFinishedSize());
        assertEquals(4, newTargetBlockSlot.countPatches(Patch.HALF_SQUARE));
        for (int i = 0; i < 6; i++) {
            assertNull(testQuilt.getBlocks().get(i));
        }
        for (int i = 7; i < testQuilt.getTotalBlocks(); i++) {
            assertNull(testQuilt.getBlocks().get(i));
        }
    }

    @Test
    public void testRemoveBlockFromEmptyPos() {
        testQuilt.removeBlock(3);
        Block targetBlockSlot = testQuilt.getBlocks().get(3);
        assertNull(targetBlockSlot);
    }

    @Test
    public void testRemoveBlockFromNonEmptyPos() {
        testQuilt.addBlock("checkerboard", 4);
        testQuilt.removeBlock(4);
        Block targetBlockSlot = testQuilt.getBlocks().get(4);
        assertNull(targetBlockSlot);
    }

    @Test
    public void testCountPatchesEmptyQuilt() {
        assertEquals(0, testQuilt.countPatches(Patch.HALF_SQUARE));
    }

    @Test
    public void testCountPatchesNonEmptyQuilt() {
        testQuilt.addBlock("checkerboard", 1);
        testQuilt.addBlock("friendship star", 5);
        assertEquals(14, testQuilt.countPatches(Patch.SQUARE));
        assertEquals(4, testQuilt.countPatches(Patch.HALF_TRIANGLE));
        assertEquals(0, testQuilt.countPatches(Patch.HALF_SQUARE));
    }

    @Test
    public void testCalculateFabricEmptyQuilt() {
        assertEquals(0, testQuilt.calculateFabric("A"));
    }

    @Test
    public void testCalculateFabricNonEmptyQuiltCheckerboards() {
        testQuilt.addBlock("checkerboard", 4);
        testQuilt.addBlock("checkerboard", 2);
        assertEquals(63.0, testQuilt.calculateFabric("A"));
        assertEquals(50.0, testQuilt.calculateFabric("B"));
    }

    @Test
    public void testCalculateFabricNonEmptyQuiltVarious() {
        testQuilt.addBlock("friendship star", 1);
        testQuilt.addBlock("greek square", 9);
        assertEquals(89.0, testQuilt.calculateFabric("A"));
        assertEquals(77.0, testQuilt.calculateFabric("B"));
    }

    @Test
    public void testCalculateTotalBacking() {
        assertEquals(891.0, testQuilt.calculateTotalBacking());
    }

    @Test
    public void testCalculateBindingLength() {
        assertEquals(118, testQuilt.calculateBindingLength());
    }

    @Test
    public void testCalculateTotalBinding() {
        assertEquals(295, testQuilt.calculateTotalBinding());
    }

    @Test
    public void testBlockListToString() {
        Quilt smallQuilt = new Quilt(2, 2, 3);
        smallQuilt.addBlock("friendship star", 2);
        String expectedString = "Block 1: empty\nBlock 2: empty\nBlock 3: friendship star\nBlock 4: empty\n";
        assertEquals(expectedString, smallQuilt.blockListToString());
    }



}