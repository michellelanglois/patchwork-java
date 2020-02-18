package persistence;

import exceptions.BlockUnavailableException;
import model.Quilt;
import model.blocks.*;
import model.patches.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WriterTest {

    private static final String TEST_FILE = "./data/testData/testWriteQuilt.json";
    private Writer testWriter;
    private Quilt testQuilt;

    @BeforeEach
    void runBefore() throws BlockUnavailableException {
        testQuilt = new Quilt(2, 2, 4);
        testQuilt.addBlock("friendship star", 3);
        try {
            testWriter = new Writer(new File(TEST_FILE));
            testWriter.write(testQuilt);
            testWriter.close();
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteQuiltNewFileNoExceptionExpected() {
        try {
            // read quilt back to ensure data saved correctly
            Quilt readQuilt = Reader.readQuilt(new File(TEST_FILE));
            assertEquals(2, readQuilt.getNumBlocksAcross());
            assertEquals(2, readQuilt.getNumBlocksDown());
            assertEquals(4, readQuilt.getBlocks().size());

            Block nonEmptyBlock = readQuilt.getBlocks().get(3);
            assertEquals("friendship star", nonEmptyBlock.getBlockType());
            assertEquals(4, nonEmptyBlock.countPatches(Patch.HALF_TRIANGLE));
        } catch (IOException e) {
            fail("IOException should have not been thrown");
        }
    }

    @Test
    void testWriteQuiltOldFileNoExceptionExpected() throws BlockUnavailableException{
        try {
            // add another block and try to write to the same file
            testQuilt.addBlock("checkerboard", 2);
            testWriter = new Writer(new File(TEST_FILE));
            testWriter.write(testQuilt);
            testWriter.close();

            // read quilt back to ensure data saved correctly
            Quilt readQuilt = Reader.readQuilt(new File(TEST_FILE));
            assertEquals(2, readQuilt.getNumBlocksAcross());
            assertEquals(2, readQuilt.getNumBlocksDown());
            assertEquals(4, readQuilt.getBlocks().size());
            Block nonEmptyBlock3 = readQuilt.getBlocks().get(3);
            assertEquals("friendship star", nonEmptyBlock3.getBlockType());
            assertEquals(4, nonEmptyBlock3.countPatches(Patch.HALF_TRIANGLE));
            Block nonEmptyBlock2 = readQuilt.getBlocks().get(2);
            assertEquals("checkerboard", nonEmptyBlock2.getBlockType());
            assertEquals(9, nonEmptyBlock2.countPatches(Patch.SQUARE));
        } catch (IOException e) {
            fail("IOException should have not been thrown");
        }
    }

    @Test
    void testWriteQuiltExceptionExpectedBadFileName() {
        try {
            Writer badFileWriter = new Writer(new File("./data/testData")); // given directory, not file!
            badFileWriter.write(testQuilt);
            badFileWriter.close();
            fail("IOException due to bad file name should have been thrown");
        } catch (IOException e) {
            // all good;
        }
    }

}
