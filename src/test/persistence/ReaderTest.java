package persistence;


import model.Quilt;
import model.blocks.*;
import model.patches.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ReaderTest {

    @Test
    public void testConstructor() {
        Reader reader = new Reader();
        assertNotNull(reader);
    }

    @Test
    public void testReadFileEmptyQuilt() {
        try {
            Quilt quilt = Reader.readQuilt(new File("./data/testData/testReadEmptyQuilt.json"));

            //test basic quilt parameters
            assertEquals(2, quilt.getNumBlocksAcross());
            assertEquals(4, quilt.getNumBlocksDown());
            assertEquals(8, quilt.getBlocks().size());

            //test blocks initialized correctly
            for (Block block : quilt.getBlocks()) {
                assertNull(block);
            }
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void readFileQuiltWithBlocks() {
        try {
            Quilt quilt = Reader.readQuilt(new File("./data/testData/testReadQuiltWithGreekSquare.json"));

            //test basic quilt parameters
            assertEquals(3, quilt.getNumBlocksAcross());
            assertEquals(2, quilt.getNumBlocksDown());
            assertEquals(6, quilt.getBlocks().size());

            //test blocks deserialized correctly
            List<Block> blocks = quilt.getBlocks();
            assertNull(blocks.get(0));
            Block nonEmptyBlock = blocks.get(1);
            assertEquals("greek square", nonEmptyBlock.getBlockType());
            for (int i = 2; i < 6; i++) {
                assertNull(blocks.get(i));
            }

            // test patches deserialized correctly
            List<Patch> patches = nonEmptyBlock.getPatches();
            assertEquals(9, patches.size());
            assertTrue(patches.get(0) instanceof HalfSquareTriangle && patches.get(0).getRotation() == 0);
            assertTrue(patches.get(1) instanceof HalfSquare && patches.get(1).getRotation() == 270);
            assertTrue(patches.get(2) instanceof HalfSquareTriangle && patches.get(2).getRotation() == 90);
            assertTrue(patches.get(3) instanceof HalfSquare && patches.get(3).getRotation() == 180);
            assertTrue(patches.get(4) instanceof Square && patches.get(4).getRotation() == 0);
            assertTrue(patches.get(5) instanceof HalfSquare && patches.get(5).getRotation() == 0);
            assertTrue(patches.get(6) instanceof HalfSquareTriangle && patches.get(6).getRotation() == 270);
            assertTrue(patches.get(7) instanceof HalfSquare && patches.get(7).getRotation() == 270);
            assertTrue(patches.get(8) instanceof HalfSquareTriangle && patches.get(8).getRotation() == 180);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testReadQuiltFileExceptionExpected() {
        try {
            Reader.readQuilt(new File("./path/does/not/exist/testQuilt.json"));
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testReadPatchesGreekSquare() {
        try {
            ArrayList<Patch> patches = Reader.readPatchPattern(new File("./data/blockPatterns/greek-square.json"));

            assertEquals(9, patches.size());
            assertTrue(patches.get(0) instanceof HalfSquareTriangle && patches.get(0).getRotation() == 0);
            assertTrue(patches.get(1) instanceof HalfSquare && patches.get(1).getRotation() == 90);
            assertTrue(patches.get(2) instanceof HalfSquareTriangle && patches.get(2).getRotation() == 90);
            assertTrue(patches.get(3) instanceof HalfSquare && patches.get(3).getRotation() == 0);
            assertTrue(patches.get(4) instanceof Square && patches.get(4).getRotation() == 0);
            assertTrue(patches.get(5) instanceof HalfSquare && patches.get(5).getRotation() == 180);
            assertTrue(patches.get(6) instanceof HalfSquareTriangle && patches.get(6).getRotation() == 270);
            assertTrue(patches.get(7) instanceof HalfSquare && patches.get(7).getRotation() == 270);
            assertTrue(patches.get(8) instanceof HalfSquareTriangle && patches.get(8).getRotation() == 180);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testReadPatchesFriendshipStar() {
        try {
            ArrayList<Patch> patches = Reader.readPatchPattern(new File("./data/blockPatterns/friendship-star.json"));

            assertEquals(9, patches.size());
            assertTrue(patches.get(0) instanceof Square && patches.get(0).containsFabric("B"));
            assertTrue(patches.get(1) instanceof HalfSquareTriangle && patches.get(1).getRotation() == 270);
            assertTrue(patches.get(2) instanceof Square && patches.get(2).containsFabric("B"));
            assertTrue(patches.get(3) instanceof HalfSquareTriangle && patches.get(3).getRotation() == 180);
            assertTrue(patches.get(4) instanceof Square && patches.get(4).containsFabric("A"));
            assertTrue(patches.get(5) instanceof HalfSquareTriangle && patches.get(5).getRotation() == 0);
            assertTrue(patches.get(6) instanceof Square && patches.get(6).containsFabric("B"));
            assertTrue(patches.get(7) instanceof HalfSquareTriangle && patches.get(7).getRotation() == 90);
            assertTrue(patches.get(8) instanceof Square && patches.get(8).containsFabric("B"));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void testReadPatchesFileExceptionExpected() {
        try {
            Reader.readPatchPattern(new File("./data/blockPatterns/friendship.json"));
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }


}
