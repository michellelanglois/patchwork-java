package model;

import java.util.ArrayList;
import java.util.List;

/*
Represents a quilt of a specific number of blocks across and down, and the blocks it contains
The list of blocks can be understood as mapping to a spot on the quilt grid as follows:
 ---- ---- ----
|    |    |    |
|  0 | 1  | 2  |
 ---- ---- ----
|    |    |    |
|  3 | 4  | 5  |
 ---- ---- ----
|    |    |    |
|  6 | 7  | 8  |
 ---- ---- ----
 */

public class Quilt {

    public static final double SEAM_ALLOWANCE = 0.25;
    public static final double BINDING_WIDTH = 2.5;
    public static final String[] AVAILABLE_FABRICS = {"A", "B"};

    private int numBlocksAcross;
    private int numBlocksDown;
    private double blockSize;
    private List<Block> blocks;

    // REQUIRES: numBlocksAcross and numBlocksDown > 0; blockSize > 0
    // EFFECTS: Creates a quilt grid with space for given number of blocks across/down of given side length (in inches)
    public Quilt(int numBlocksAcross, int numBlocksDown, double blockSize) {
        this.numBlocksAcross = numBlocksAcross;
        this.numBlocksDown = numBlocksDown;
        this.blockSize = blockSize;

        int totalBlocks = numBlocksAcross * numBlocksDown;
        this.blocks = new ArrayList<>(totalBlocks);
        for (int i = 0; i < totalBlocks; i++) {
            blocks.add(i, null);
        }
    }

    // getters

    public int getNumBlocksAcross() {
        return numBlocksAcross;
    }

    public int getNumBlocksDown() {
        return numBlocksDown;
    }

    public int getTotalBlocks() {
        return numBlocksAcross * numBlocksDown;
    }

    public double getWidth() {
        return numBlocksAcross * blockSize;
    }

    public double getLength() {
        return numBlocksDown * blockSize;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    // REQUIRES: slot is >= 0 and < totalBlocks, name is in Block.AVAILABLE_BLOCKS
    // MODIFIES: this
    // EFFECTS: constructs block of given name in the given slot of the quilt; replaces current block if one exists
    public void addBlock(String name, int slot) {
        Block blockToAdd = new Block(name, blockSize);
        blocks.set(slot, blockToAdd);
    }

    // REQUIRES: slot is >= 0 and < totalBlocks
    // MODIFIES: this
    // EFFECTS: removes block from the given slot of the quilt
    public void removeBlock(int slot) {
        blocks.set(slot, null);
    }

    // EFFECTS: calculates the total number of patches needed of given type
    public int countPatches(String patchType) {
        int total = 0;
        for (Block b : blocks) {
            if (b != null) {
                total += b.countPatches(patchType);
            }
        }
        return total;
    }

    // EFFECTS: calculates the total fabric needed of given type, rounded up to nearest square inch
    public double calculateFabric(String fabric) {
        double total = 0;
        for (Block b : blocks) {
            if (b != null) {
                total += b.calculateFabric(fabric);
            }
        }
        return Math.ceil(total);
    }

    // EFFECTS: calculates the total backing needed for the quilt, rounded up to nearest square inch
    public double calculateTotalBacking() {
        double total = (getLength() + 3) * (getWidth() + 3);
        return Math.ceil(total);
    }

    // EFFECTS: calculates the total binding length needed using regular binding method, rounded up to nearest inch
    public double calculateBindingLength() {
        double total = (getLength() * 2) + (getWidth() * 2) + 10;
        return Math.ceil(total);
    }

    // EFFECTS: calculates the total fabric needed for binding, rounded up to nearest square inch
    public double calculateTotalBinding() {
        double total = calculateBindingLength() * BINDING_WIDTH;
        return Math.ceil(total);
    }

    // EFFECTS: returns a string representing the quilt in the form Block 1: friendship star
    public String blockListToString() {
        String blockListString = "";
        for (int i = 0; i < blocks.size(); i++) {
            Block blockAtIndex = blocks.get(i);
            if (blockAtIndex == null) {
                blockListString += ("Block " + (i + 1) + ": " + "empty\n");
            } else {
                blockListString += ("Block " + (i + 1) + ": " + blockAtIndex.getName() + "\n");
            }
        }
        return blockListString;
    }

}
