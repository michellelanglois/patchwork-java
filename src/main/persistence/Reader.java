package persistence;


import model.Quilt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
A reader that can read quilt data from a file and turn it into a quilt object
This reader uses an external library, Gson, to deserialize JSON objects
 */

public class Reader {

    // EFFECTS: reads data from JSON file, deserializes the JSON objects into a Quilt object, and returns the Quilt
    public static Quilt readQuilt(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            FileReader fileReader = new FileReader(file);
            GsonConfigured gsonConfigured = new GsonConfigured();
            return gsonConfigured.getGson().fromJson(fileReader, Quilt.class);
        }
    }


}
