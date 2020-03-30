package model;

import exceptions.BlockUnavailableException;
import model.blocks.BlockMap;

import java.util.ArrayList;
import java.util.List;

import model.patches.Patch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockMapTest {

    @Test
    public void testConstructor() {
        BlockMap blockMap = new BlockMap();
        assertNotNull(blockMap);
    }

    @Test
    public void testBlockMapCreation() {
        assertTrue(BlockMap.getAvailableBlockMap().size() > 0);
        assertEquals(BlockMap.listAvailableBlocks().size(), BlockMap.getAvailableBlockMap().size());

        assertTrue(BlockMap.getAvailableBlockMap().containsKey("checkerboard"));
        assertTrue(BlockMap.getAvailableBlockMap().containsValue("./data/blockPatterns/checkerboard.json"));

        assertTrue(BlockMap.getAvailableBlockMap().containsKey("friendship star"));
        assertTrue(BlockMap.getAvailableBlockMap().containsValue("./data/blockPatterns/friendship-star.json"));

        assertTrue(BlockMap.getAvailableBlockMap().containsKey("greek square"));
        assertTrue(BlockMap.getAvailableBlockMap().containsValue("./data/blockPatterns/greek-square.json"));

        assertFalse(BlockMap.getAvailableBlockMap().containsKey("a"));
    }

    @Test
    public void testGetBlockPatchPatternValidBlock() {
        try {
            List<Patch> patches = BlockMap.getBlockPatchPattern("greek square");
            assertEquals(9, patches.size());
        } catch (BlockUnavailableException e) {
            fail("BlockUnavailableException should not have been thrown.");
        }
    }

    @Test
    public void testGetBlockPatchPatternInValidBlock() {
        try {
            List<Patch> patches = BlockMap.getBlockPatchPattern("a");
            fail("BlockUnavailableException should have been thrown.");
        } catch (BlockUnavailableException e) {
            // all good
        }
    }

    @Test
    public void testGetBlockFilesEmptyDirectory() {
        ArrayList<String> result = BlockMap.getBlockFileNames("./data/testData/emptyFolder");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBlockFilesMixedDirectory() {
        ArrayList<String> result = BlockMap.getBlockFileNames("./data/testData/folderWithFilesAndNotFiles");
        assertEquals(2, result.size());
        assertTrue(result.contains("./data/testData/folderWithFilesAndNotFiles/checkerboard.json"));
        assertTrue(result.contains("./data/testData/folderWithFilesAndNotFiles/friendship-star.json"));
        assertFalse(result.contains("./data/testData/folderWithFilesAndNotFiles/notAFile"));
    }

    @Test
    public void testGetBlockFilesNotADirectory() {
        ArrayList<String> result = BlockMap.getBlockFileNames("./data/testData/checkerboard.json");
        assertTrue(result.isEmpty());
    }

}
