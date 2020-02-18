package model.blocks;

/*
Represents all blocks pre-programmed into Patchwork
 */

import java.io.File;
import java.util.*;

public class BlockType {

    private static final String patternFileFolder = "./data/blockPatterns/";
    private static final String patternFileType = ".json";
    private static Map<String, String> blockMap = makeAvailableBlockMap();

    // MODIFIES: this
    // EFFECTS: creates a map with block names as keys and block instances as values, for iteration outside the class
    //          creates map once at program start
    // NOTE: Code for the creation of the BLOCK_MAP is based on a pattern from Joshua Bloch, Effective Java
    private static Map<String, String> makeAvailableBlockMap() {
        ArrayList<String> blockFiles = getBlockFileNames(patternFileFolder);
        Map<String, String> map = new HashMap<>();
        for (String file : blockFiles) {
            String blockName = formatBlockName(file);
            map.put(blockName, file);
        }
        return Collections.unmodifiableMap(map);
    }

    // EFFECTS: returns block map
    public static Map<String, String> getAvailableBlockMap() {
        return blockMap;
    }

    // EFFECTS: returns true if block name given is a pre-programmed block
    public static boolean isAvailableBlock(String blockName) {
        return getAvailableBlockMap().containsKey(blockName);
    }

    // EFFECTS: returns the file where a block pattern is stored
    public static String getBlockFileName(String blockType) {
        return blockMap.get(blockType);
    }

    // EFFECTS: helper method to return a current list of file names in the block patterns folder
    public static ArrayList<String> getBlockFileNames(String folder) {
        File[] allFiles = new File(folder).listFiles();
        ArrayList<String> filteredFiles = new ArrayList<>();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isFile()) {
                    filteredFiles.add(file.toString());
                }
            }
        }
        return filteredFiles;
    }

    // EFFECTS: helper method to format block file names as strings that can be used throughout the program
    private static String formatBlockName(String fileName) {
        int startIndex = patternFileFolder.length();
        int nameStopIndex = fileName.length() - patternFileType.length();
        return fileName.substring(startIndex, nameStopIndex).replace("-", " ");
    }

}
