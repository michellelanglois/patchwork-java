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
        List<String> blockFileNames = getBlockFileNames();
        Map<String, String> map = new HashMap<>();
        for (String fileName : blockFileNames) {
            String blockName = formatBlockName(fileName);
            map.put(blockName, fileName);
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

    // EFFECTS: returns the block file name associated with the block
    public static String getBlockFileName(String blockName) {
        return getAvailableBlockMap().get(blockName);
    }

    // EFFECTS: helper method to return a current list of files in the block patterns folder
    private static File[] getBlockFiles() {
        File folder = new File(patternFileFolder);
        return folder.listFiles();
    }

    // EFFECTS: helper method to return a current list of file names in the block patterns folder
    private static List<String> getBlockFileNames() {
        File[] listOfFiles = getBlockFiles();
        List<String> listOfFileNames = new ArrayList<>();
        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    listOfFileNames.add(patternFileFolder + listOfFile.getName());
                }
            }
        }
        return listOfFileNames;
    }

    // EFFECTS: helper method to format block file names as strings that can be used throughout the program
    private static String formatBlockName(String fileName) {
        int startIndex = patternFileFolder.length();
        int nameStopIndex = fileName.length() - patternFileType.length();
        return fileName.substring(startIndex, nameStopIndex).replace("-", " ");
    }

}
