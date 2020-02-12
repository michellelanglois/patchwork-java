package model.blocks;

/*
Represents all blocks pre-programmed into Patchwork

NOTE: Code for the creation of the BLOCK_MAP is based on a pattern from Joshua Bloch, Effective Java
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum BlockType {
    FRIENDSHIP_STAR("friendship star"),
    GREEK_SQUARE("greek square"),
    CHECKERBOARD("checkerboard");

    private String blockName;

    public static final Map<String, BlockType> BLOCK_MAP;

    // EFFECTS: constructs instance of enum with associated block name
    BlockType(String blockName) {
        this.blockName = blockName;
    }

    // getter
    public String getBlockName() {
        return blockName;
    }

    // EFFECTS: creates a map with block names as keys and block instances as values, for iteration outside the class
    //          creates map once at program start
    static {
        Map<String, BlockType> map = new HashMap<>();
        for (BlockType block : BlockType.values()) {
            map.put(block.blockName, block);
        }
        BLOCK_MAP = Collections.unmodifiableMap(map);
    }

}
