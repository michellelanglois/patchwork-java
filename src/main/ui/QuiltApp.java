package ui;

import model.Block;
import model.Quilt;
import model.patches.Patch;

import java.util.Arrays;
import java.util.Scanner;

/*
Quilt designer application

NOTE: Code for this UI is based on code provided by CPSC 210 staff in the TellerApp and FitLifeGymChain programs
 */

public class QuiltApp {

    private Quilt quilt;
    private Scanner input;
    private boolean runProgram;

    public QuiltApp() {
        this.runProgram = true;
        this.input = new Scanner(System.in);
        enterMainMenu();
    }

    // MODIFIES: this
    // EFFECTS: runs the quilt app user interface in the console
    private void enterMainMenu() {
        System.out.println("*** WELCOME TO PATCHWORK ***\n");

        while (runProgram) {
            displayMainMenu();
            String command = input.next().toLowerCase();

            if (command.equals("q")) {
                runProgram = false;
                stopQuiltApp();
            } else if (command.equals("n")) {
                startNewQuilt();
            } else {
                System.out.println("Sorry! That's not a valid command.");
            }
        }
    }

    // EFFECTS: prints out the main menu for the quilt app
    private void displayMainMenu() {
        System.out.println("----------");
        System.out.println("\nDo you want to start a new quilt or quit for the day?");
        System.out.println("n --> start a new quilt");
        System.out.println("q --> quit");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to start a new quilt, then constructs quilt based on user input
    private void startNewQuilt() {
        System.out.println("\nLet's get creative!");
        System.out.println("\nHow many blocks across do you want your quilt to be?");
        int blocksAcross = selectNumOfBlocks();

        System.out.println("\nHow many blocks down do you want your quilt to be?");
        int blocksDown = selectNumOfBlocks();

        System.out.println("\nHow long (in inches) do you want the side of a block to be?");
        System.out.println("HINT: multiples of 1.5\" work best!");
        double blockSize = selectSizeOfBlocks();

        System.out.println("\nAwesome! You've started your quilt.");

        this.quilt = new Quilt(blocksAcross, blocksDown, blockSize);
        designQuilt();
    }

    // EFFECTS: prompts user to input a number of blocks down or across for the quilt, and returns that number
    public int selectNumOfBlocks() {
        int numberOfBlocks = 0;
        while (numberOfBlocks <= 0) {
            System.out.println("Please enter a whole number greater than 0.");
            if (input.hasNextInt()) {
                numberOfBlocks = input.nextInt();
            } else {
                System.out.println("That's not a number!");
                input.next();
            }
        }
        return numberOfBlocks;
    }

    // EFFECTS: prompts user to input a block size for the quilt, and returns that number
    public double selectSizeOfBlocks() {
        double sizeOfBlocks = 0.0;
        while (sizeOfBlocks <= 0.0) {
            System.out.println("Please enter a number greater than 0. You can use decimals.");
            if (!input.hasNextDouble()) {
                System.out.println("That's not a number!");
            } else {
                sizeOfBlocks = input.nextDouble();
            }
        }
        return sizeOfBlocks;
    }

    // MODIFIES: this
    // EFFECTS: prompts user through the process of designing their quilt
    public void designQuilt() {
        boolean designQuilt = true;
        while (designQuilt) {
            displayQuiltMenu();
            String command = input.next().toLowerCase();

            if (command.equals("b")) {
                designQuilt = false;
                enterMainMenu();
            } else if (command.equals("q")) {
                designQuilt = false;
                runProgram = false;
                stopQuiltApp();
            } else {
                processCommand(command);
            }
        }
    }
    
    // EFFECTS: prints out the quilt construction menu for the quilting app
    public void displayQuiltMenu() {
        System.out.println("\n----------");
        System.out.println("\nWhat do you want to do next?");
        System.out.println("\n*** I want to create! ***");
        System.out.println("     a --> add a block to my quilt (or change one)");
        System.out.println("     d --> delete a block from my quilt");
        System.out.println("     v --> view a list of blocks in my quilt");
        System.out.println("\n*** Do the math for me! ***");
        System.out.println("     f --> calculate total fabric requirements");
        System.out.println("     p --> calculate total number of patches needed");
        System.out.println("\n*** I'm tired. ***");
        System.out.println("     b --> go back to main menu");
        System.out.println("     q --> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from quilt menu
    public void processCommand(String command) {
        switch (command) {
            case "a":
                handleAddBlock();
                break;
            case "d":
                handleDeleteBlock();
                break;
            case "f":
                printFabricNeeded();
                break;
            case "p":
                printPatchesNeeded();
                break;
            case "v":
                printQuiltAsList();
                break;
            default:
                System.out.println("Sorry! That's not a valid command. Try again?");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles user input for block addition
    private void handleAddBlock() {
        System.out.println("\nTime to get creative! Adding a block to your quilt, or changing an existing one, is"
                + " simple. Just choose a block name a slot, and we'll do the hard work.");
        printAvailableBlocks();

        System.out.println("\nWhat block do you want to add?");
        String blockChoice = selectBlockToAdd();

        System.out.println("\nWhere do you want to add it?");
        int slotChoice = selectSlot();
        int slotIndex = slotChoice - 1;

        quilt.addBlock(blockChoice, slotIndex);
        System.out.println("\nAmazing! You just added a " + blockChoice + " to slot " + slotChoice + ". Looking good!");
    }

    // MODIFIES: this
    // EFFECTS: handles user input for block deletion
    private void handleDeleteBlock() {
        System.out.println("\nChanging your mind is never a bad thing!");
        System.out.println("\nWhat is the slot number for the block you want to delete?");
        int slotChoice = selectSlot();
        int slotIndex = slotChoice - 1;

        quilt.removeBlock(slotIndex);
        System.out.println("\nAll done! You just removed the block in slot " + slotChoice + ". Pick a new one!");
    }

    // EFFECT: prompts user to select a slot to add or delete a block, returns slot
    private int selectSlot() {
        int slotChoice = 0;
        int maxSlot = quilt.getTotalBlocks();
        while (slotChoice < 1 || slotChoice > maxSlot) {
            System.out.println("Please enter a number between 1 and " +  maxSlot + ".");
            if (input.hasNextInt()) {
                slotChoice = input.nextInt();
            } else {
                System.out.println("That's not a number!");
                input.next();
            }
        }
        return slotChoice;
    }

    // EFFECT: prompts user to select a block to add and returns the block name
    private String selectBlockToAdd() {
        String blockChoice = input.nextLine().toLowerCase();
        while (!Arrays.asList(Block.AVAILABLE_BLOCKS).contains(blockChoice)) {
            System.out.println("Type the name of the block exactly as listed above.");
            blockChoice = input.nextLine().toLowerCase();
        }
        return blockChoice;
    }

    // EFFECTS: prints out a numbered list of available blocks
    private void printAvailableBlocks() {
        int i = 1;
        System.out.println("\nHere are the blocks you can add to your quilt:");
        for (String blockName : Block.AVAILABLE_BLOCKS) {
            System.out.println("     [" + i + "] " + blockName);
            i++;
        }
    }

    // EFFECTS: prints out the list of blocks in the user's quilt
    private void printQuiltAsList() {
        System.out.println("\nYour quilt currently looks like this:");
        System.out.println(quilt.blockListToString());
    }

    // EFFECTS: prints out the number of patches needed of each patch type
    private void printPatchesNeeded() {
        System.out.println("\nYou need:");
        System.out.println(quilt.countPatches(Patch.SQUARE) + " square patches");
        System.out.println(quilt.countPatches(Patch.HALF_SQUARE) + " half-square patches");
        System.out.println(quilt.countPatches(Patch.HALF_TRIANGLE) + " half-square triangle patches");
        warnIfQuiltNotFull();
    }

    // EFFECTS: prints out the quantity of fabric needed for the quilt
    private void printFabricNeeded() {
        System.out.println("\nYou need:");
        for (String fabric : Quilt.AVAILABLE_FABRICS) {
            String formattedAmount =  String.format("%,.0f", quilt.calculateFabric(fabric));
            System.out.println(formattedAmount + " square inches of fabric " + fabric);
        }
        String formattedBackingAmount = String.format("%,.0f", quilt.calculateTotalBacking());
        String formattedBindingAmount = String.format("%,.0f", quilt.calculateTotalBinding());
        System.out.println(formattedBackingAmount + " square inches of backing fabric");
        System.out.println(formattedBindingAmount + " square inches of binding fabric");
        warnIfQuiltNotFull();
    }

    // EFFECTS: prints out a warning to the user if the quilt is not fully designed
    private void warnIfQuiltNotFull() {
        if (quilt.getBlocks().contains(null)) {
            System.out.println("\nCAREFUL! These calculations aren't complete because you haven't filled your quilt.");
        }
    }

    // EFFECTS: stops receiving user input
    private void stopQuiltApp() {
        System.out.println("\nThanks for using Patchwork!");
        input.close();
    }

}