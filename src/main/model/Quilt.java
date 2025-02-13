package model;

import exceptions.IllegalQuiltSizeException;
import exceptions.SlotOutOfBoundsException;
import model.blocks.Block;
import persistence.GsonConfigured;
import persistence.Saveable;
import exceptions.BlockUnavailableException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Represents a quilt of a specific number of blocks across and down, and the blocks it contains
The list of blocks can be understood as mapping to a spot on the quilt grid as follows, for example:
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

public class Quilt implements Saveable {

    public static final double SEAM_ALLOWANCE = 0.25;
    public static final double BINDING_WIDTH = 2.5;

    private int numBlocksAcross;
    private int numBlocksDown;
    private double blockSize;
    private List<Block> blocks;
    private String[] fabricColours;

    // EFFECTS: Creates a quilt grid with space for given number of blocks across/down of given side length (in inches)
    public Quilt(int numBlocksAcross, int numBlocksDown, double blockSize) throws IllegalQuiltSizeException {
        if (numBlocksAcross <= 0 || numBlocksDown <= 0 || blockSize <= 0) {
            throw new IllegalQuiltSizeException();
        } else {
            this.numBlocksAcross = numBlocksAcross;
            this.numBlocksDown = numBlocksDown;
            this.blockSize = blockSize;

            // ensures list of blocks is always exactly as long as the number of blocks in the quilt, so that blocks can
            // be added in the right slots
            int totalBlocks = numBlocksAcross * numBlocksDown;
            this.blocks = new ArrayList<>(totalBlocks);
            for (int i = 0; i < totalBlocks; i++) {
                blocks.add(i, null);
            }

            fabricColours = new String[]{null, null};
        }
    }

    // EFFECTS: Creates a quilt; used only when deserializing quilt data from JSON using Gson
    private Quilt() { }

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

    public double getBlockSize() {
        return blockSize;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public String[] getFabricColours() {
        return fabricColours;
    }

    // setters
    public void setFabricColours(String colour1, String colour2) {
        fabricColours = new String[]{colour1, colour2};
    }

    // MODIFIES: this
    // EFFECTS: constructs block of given name in the given slot of the quilt; replaces current block if one exists
    public void addBlock(String blockType, int slot) throws BlockUnavailableException, SlotOutOfBoundsException {
        if (slot < 0 || slot >= getTotalBlocks()) {
            throw new SlotOutOfBoundsException();
        } else {
            Block blockToAdd = new Block(blockType);
            blocks.set(slot, blockToAdd);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes block from the given slot of the quilt
    public void removeBlock(int slot) throws SlotOutOfBoundsException {
        if (slot < 0 || slot >= getTotalBlocks()) {
            throw new SlotOutOfBoundsException();
        } else {
            blocks.set(slot, null);
        }
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
                total += b.calculateFabric(fabric, blockSize);
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

    // MODIFIES: fileWriter
    // EFFECTS: converts the quilt to JSON and writes the JSON data to fileWriter
    @Override
    public void save(FileWriter fileWriter) throws IOException {
        // turns the quilt into JSON data
        GsonConfigured gsonConfigured = new GsonConfigured();
        String jsonQuilt = gsonConfigured.getGson().toJson(this);
        // writes the JSON data to fileWriter
        fileWriter.write(jsonQuilt);
        fileWriter.close();
    }

}
