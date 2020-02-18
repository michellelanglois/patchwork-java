package persistence;


import com.google.gson.reflect.TypeToken;
import model.Quilt;
import model.patches.Patch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
A reader that can read data from a file and turn it into objects (quilt, blocks, patches)
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

    // EFFECTS: reads data from JSON file, deserializes data into a list of Patch objects, & returns the list
    public static ArrayList<Patch> readPatchPattern(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            FileReader fileReader = new FileReader(file);
            GsonConfigured gsonConfigured = new GsonConfigured();
            // code to deserialize as an ArrayList of a certain type adapted from Gson tutorial available at:
            // https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
            Type patchListType = new TypeToken<ArrayList<Patch>>(){}.getType();
            return gsonConfigured.getGson().fromJson(fileReader, patchListType);
        }
    }

}
