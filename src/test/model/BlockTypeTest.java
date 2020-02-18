package model;

import model.blocks.BlockType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BlockTypeTest {

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

}
