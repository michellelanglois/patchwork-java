package model;

import model.blocks.BlockType;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTypeTest {

    @Test
    public void testConstructor() {
        BlockType blockType = new BlockType();
        assertNotNull(blockType);
    }

    @Test
    public void testBlockMapCreation() {
        assertTrue(BlockType.getAvailableBlockMap().size() > 0);

        assertTrue(BlockType.getAvailableBlockMap().containsKey("checkerboard"));
        assertTrue(BlockType.getAvailableBlockMap().containsValue("./data/blockPatterns/checkerboard.json"));

        assertTrue(BlockType.getAvailableBlockMap().containsKey("friendship star"));
        assertTrue(BlockType.getAvailableBlockMap().containsValue("./data/blockPatterns/friendship-star.json"));

        assertTrue(BlockType.getAvailableBlockMap().containsKey("greek square"));
        assertTrue(BlockType.getAvailableBlockMap().containsValue("./data/blockPatterns/greek-square.json"));

        assertFalse(BlockType.getAvailableBlockMap().containsKey("a"));
    }

    @Test
    public void testIsValidBlock() {
        assertTrue(BlockType.isAvailableBlock("checkerboard"));
        assertFalse(BlockType.isAvailableBlock("a"));
    }

    @Test
    public void testGetBlockFilesEmptyDirectory() {
        ArrayList<String> result = BlockType.getBlockFileNames("./data/testData/emptyFolder");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBlockFilesMixedDirectory() {
        ArrayList<String> result = BlockType.getBlockFileNames("./data/testData/folderWithFilesAndNotFiles");
        assertEquals(2, result.size());
        assertTrue(result.contains("./data/testData/folderWithFilesAndNotFiles/checkerboard.json"));
        assertTrue(result.contains("./data/testData/folderWithFilesAndNotFiles/friendship-star.json"));
        assertFalse(result.contains("./data/testData/folderWithFilesAndNotFiles/notAFile"));
    }

    @Test
    public void testGetBlockFilesNotADirectory() {
        ArrayList<String> result = BlockType.getBlockFileNames("./data/testData/checkerboard.json");
        assertTrue(result.isEmpty());
    }

}
