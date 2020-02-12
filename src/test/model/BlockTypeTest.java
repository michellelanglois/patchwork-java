package model;

import model.blocks.BlockType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockTypeTest {

    BlockType blockType;

    @BeforeEach
    public void runBefore() {
        blockType = BlockType.CHECKERBOARD;
    }

    @Test
    public void testConstructor() {
        assertEquals("checkerboard", blockType.getBlockName());
    }

    @Test
    public void testBlockMapCreation() {
        assertEquals(3, BlockType.BLOCK_MAP.size());

        assertTrue(BlockType.BLOCK_MAP.containsKey("checkerboard"));
        assertTrue(BlockType.BLOCK_MAP.containsValue(BlockType.CHECKERBOARD));

        assertTrue(BlockType.BLOCK_MAP.containsKey("friendship star"));
        assertTrue(BlockType.BLOCK_MAP.containsValue(BlockType.FRIENDSHIP_STAR));

        assertTrue(BlockType.BLOCK_MAP.containsKey("greek square"));
        assertTrue(BlockType.BLOCK_MAP.containsValue(BlockType.GREEK_SQUARE));

        assertFalse(BlockType.BLOCK_MAP.containsKey("a"));

    }
}
