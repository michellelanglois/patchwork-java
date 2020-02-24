package persistence;

import java.io.FileWriter;
import java.io.IOException;

/*
Specifies behaviour for all classes that should be saveable to file
NOTE: Code for this class is copied from the TellerApp project
 */

public interface Saveable {

    // MODIFIES: fileWriter
    // EFFECTS: writes the saveable to file
    void save(FileWriter fileWriter) throws IOException;

}
