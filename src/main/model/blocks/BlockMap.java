package model.blocks;

/*
Represents a map of all blocks pre-programmed into Patchwork
 */

import exceptions.BlockUnavailableException;
import model.patches.Patch;
import persistence.Reader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BlockMap {

    private static final String patternFileFolder = "./data/blockPatterns/";
    private static final String patternFileType = ".json";
    private static Map<String, String> blockMap = makeAvailableBlockMap();

    // MODIFIES: this
    // EFFECTS: creates a map with block names as keys and block pattern file as values, for use by other classes
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

    // EFFECTS: returns the list of patches in the pattern for the given blockType
    //          throws BlockUnavailableException if desired block doesn't exist or cannot be read from file
    public static List<Patch> getBlockPatchPattern(String blockType) throws BlockUnavailableException {
        try {
            File patternFile = new File(blockMap.get(blockType));
            return Reader.readPatchPattern(patternFile);
        } catch (NullPointerException | IOException e) {
            throw new BlockUnavailableException();
        }
    }

    // EFFECTS: returns block map
    public static Map<String, String> getAvailableBlockMap() {
        return blockMap;
    }

    // EFFECTS: returns a set of all available blocks
    public static Set<String> listAvailableBlocks() {
        return blockMap.keySet();
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
