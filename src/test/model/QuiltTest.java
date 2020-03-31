package model;

import exceptions.BlockUnavailableException;
import exceptions.IllegalQuiltSizeException;
import exceptions.SlotOutOfBoundsException;
import model.blocks.Block;
import model.patches.Patch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuiltTest {

    private Quilt testQuilt;

    @BeforeEach
    public void runBefore() {
        try {
            testQuilt = new Quilt(4, 5, 6.0);
        } catch (IllegalQuiltSizeException e) {
            fail("IllegalQuiltSizeException should not have been thrown.");
        }
    }

    @Test
    public void testConstructor() {
        assertEquals(20, testQuilt.getNumBlocksAcross() * testQuilt.getNumBlocksDown());
        assertEquals(20, testQuilt.getBlocks().size());
        assertEquals(6.0, testQuilt.getBlockSize());
        for (Block b: testQuilt.getBlocks()) {
            assertNull(b);
        }
        assertNull(testQuilt.getFabricColours()[0]);
        assertNull(testQuilt.getFabricColours()[1]);
    }

    @Test
    public void testConstructorWithNumBlocksAcrossZero() {
        try {
            new Quilt(0, 10, 5);
            fail("IllegalQuiltSizeException should have been thrown.");
        } catch (IllegalQuiltSizeException e) {
            // all good
        }
    }

    @Test
    public void testConstructorWithNumBlocksAcrossSubZero() {
        try {
            new Quilt(-1, 10, 5);
            fail("IllegalQuiltSizeException should have been thrown.");
        } catch (IllegalQuiltSizeException e) {
            // all good
        }
    }

    @Test
    public void testConstructorWithNumBlocksDownZero() {
        try {
            new Quilt(10, 0, 5);
            fail("IllegalQuiltSizeException should have been thrown.");
        } catch (IllegalQuiltSizeException e) {
            // all good
        }
    }

    @Test
    public void testConstructorWithNumBlocksDownSubZero() {
        try {
            new Quilt(10, -1, 5);
            fail("IllegalQuiltSizeException should have been thrown.");
        } catch (IllegalQuiltSizeException e) {
            // all good
        }
    }

    @Test
    public void testConstructorWithBlockSizeZero() {
        try {
            new Quilt(10, 10, 0);
            fail("IllegalQuiltSizeException should have been thrown.");
        } catch (IllegalQuiltSizeException e) {
            // all good
        }
    }

    @Test
    public void testConstructorWithBlockSizeSubZero() {
        try {
            new Quilt(10, 10, -1);
            fail("IllegalQuiltSizeException should have been thrown.");
        } catch (IllegalQuiltSizeException e) {
            // all good
        }
    }

    @Test
    public void testSetFabricColours() {
        testQuilt.setFabricColours("blue", "green");
        assertEquals(testQuilt.getFabricColours()[0], "blue");
        assertEquals(testQuilt.getFabricColours()[1], "green");
    }

    @Test
    public void testAddBlockToEmptyQuiltPos0() {
        try {
            testQuilt.addBlock("friendship star", 0);
            Block targetBlockSlot = testQuilt.getBlocks().get(0);
            assertEquals("friendship star", targetBlockSlot.getBlockType());
            assertEquals(4, targetBlockSlot.countPatches(Patch.HALF_TRIANGLE));
            for (int i = 1; i < testQuilt.getTotalBlocks(); i++) {
                assertNull(testQuilt.getBlocks().get(i));
            }
        } catch (SlotOutOfBoundsException e) {
            fail("SlotOutOfBoundsException should not have been thrown.");
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testAddBlockToEmptyQuiltPosInMiddle() {
        try {
            testQuilt.addBlock("checkerboard", 6);
            Block targetBlockSlot = testQuilt.getBlocks().get(6);
            assertEquals("checkerboard", targetBlockSlot.getBlockType());
            assertEquals(0, targetBlockSlot.countPatches(Patch.HALF_SQUARE));
            for (int i = 0; i < 6; i++) {
                assertNull(testQuilt.getBlocks().get(i));
            }
            for (int i = 7; i < testQuilt.getTotalBlocks(); i++) {
                assertNull(testQuilt.getBlocks().get(i));
            }
        } catch (SlotOutOfBoundsException e) {
            fail("SlotOutOfBoundsException should not have been thrown.");
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testAddBlockToNonEmptyQuiltPos() {
        try {
            testQuilt.addBlock("checkerboard", 6);
            Block targetBlockSlot = testQuilt.getBlocks().get(6);
            assertEquals("checkerboard", targetBlockSlot.getBlockType());
            testQuilt.addBlock("greek square", 6);

            Block newTargetBlockSlot = testQuilt.getBlocks().get(6);
            assertEquals("greek square", newTargetBlockSlot.getBlockType());
            assertEquals(4, newTargetBlockSlot.countPatches(Patch.HALF_SQUARE));
            for (int i = 0; i < 6; i++) {
                assertNull(testQuilt.getBlocks().get(i));
            }
            for (int i = 7; i < testQuilt.getTotalBlocks(); i++) {
                assertNull(testQuilt.getBlocks().get(i));
            }
        } catch (SlotOutOfBoundsException e) {
            fail("SlotOutOfBoundsException should not have been thrown.");
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testAddBlockToQuiltSlotSubZero() {
        try {
            testQuilt.addBlock("checkerboard", -1);
            fail("SlotOutOfBoundsException should have been thrown");
        } catch (SlotOutOfBoundsException e) {
            // all good
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testAddBlockToQuiltSlotTooHigh() {
        try {
            testQuilt.addBlock("checkerboard", (testQuilt.getTotalBlocks()));
            fail("SlotOutOfBoundsException should have been thrown");
        } catch (SlotOutOfBoundsException e) {
            // all good
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown");
        }
    }

    @Test
    public void testRemoveBlockFromEmptyPos() {
        try {
            testQuilt.removeBlock(3);
            Block targetBlockSlot = testQuilt.getBlocks().get(3);
            assertNull(targetBlockSlot);
        } catch (SlotOutOfBoundsException e) {
            fail("SlotOutOfBoundsException should have been thrown");
        }
    }

    @Test
    public void testRemoveBlockFromNonEmptyPos() {
        try {
            testQuilt.addBlock("checkerboard", 4);
            testQuilt.removeBlock(4);
            Block targetBlockSlot = testQuilt.getBlocks().get(4);
            assertNull(targetBlockSlot);
        } catch (SlotOutOfBoundsException e) {
            fail("SlotOutOfBoundsException should not have been thrown");
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown.");
        }
    }

    @Test
    public void testRemoveBlockFromQuiltSlotSubZero() {
        try {
            testQuilt.removeBlock(-1);
            fail("SlotOutOfBoundsException should have been thrown");
        } catch (SlotOutOfBoundsException e) {
            // all good
        }
    }

    @Test
    public void testRemoveBlockFromQuiltSlotTooHigh() {
        try {
            testQuilt.removeBlock((testQuilt.getTotalBlocks()));
            fail("SlotOutOfBoundsException should have been thrown");
        } catch (SlotOutOfBoundsException e) {
            // all good
        }
    }

    @Test
    public void testCountPatchesEmptyQuilt() {
        assertEquals(0, testQuilt.countPatches(Patch.HALF_SQUARE));
    }

    @Test
    public void testCountPatchesNonEmptyQuilt() {
        try {
            testQuilt.addBlock("checkerboard", 1);
            testQuilt.addBlock("friendship star", 5);
            assertEquals(14, testQuilt.countPatches(Patch.SQUARE));
            assertEquals(4, testQuilt.countPatches(Patch.HALF_TRIANGLE));
            assertEquals(0, testQuilt.countPatches(Patch.HALF_SQUARE));
        } catch (Exception e) {
            fail("No exceptions should have been thrown.");
        }
    }

    @Test
    public void testCalculateFabricEmptyQuilt() {
        assertEquals(0, testQuilt.calculateFabric("A"));
    }

    @Test
    public void testCalculateFabricNonEmptyQuiltCheckerboards() {
        try {
            testQuilt.addBlock("checkerboard", 4);
            testQuilt.addBlock("checkerboard", 2);
            assertEquals(63.0, testQuilt.calculateFabric("A"));
            assertEquals(50.0, testQuilt.calculateFabric("B"));
        } catch (Exception e) {
            fail("No exceptions should have been thrown.");
        }
    }

    @Test
    public void testCalculateFabricNonEmptyQuiltVarious() {
        try {
            testQuilt.addBlock("friendship star", 1);
            testQuilt.addBlock("greek square", 9);
            assertEquals(77.0, testQuilt.calculateFabric("A"));
            assertEquals(89.0, testQuilt.calculateFabric("B"));
        } catch (Exception e) {
            fail("No exceptions should have been thrown.");
        }
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

}