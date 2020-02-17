package persistence;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
A writer that can write quilt data to a file
 */

public class Writer {

    private FileWriter fileWriter;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes saveable to file
    public void write(Saveable saveable) throws IOException {
        saveable.save(fileWriter);
    }

    // MODIFIES: this
    // EFFECTS: close file writer
    public void close() throws IOException {
        fileWriter.close();
    }


}
