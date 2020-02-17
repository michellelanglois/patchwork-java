package persistence;

import java.io.FileWriter;

/*
Specifies behaviour for all classes that should be saveable to file
 */

public interface Saveable {

    // MODIFIES: fileWriter
    // EFFECTS: writes the saveable to file
    void save(FileWriter fileWriter);

}
