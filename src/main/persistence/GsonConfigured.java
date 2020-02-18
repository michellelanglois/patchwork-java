package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import model.patches.*;

/*
A configured Gson object capable of deserializing JSON objects for this project.
Configurations to the standard Gson object include:
- serializing null values
- pretty printing JSON to file so that the files are human-readable
- creating a RuntimeTypeAdapterFactory to recognize Patch subclasses
 */

public class GsonConfigured {

    private Gson gson;

    public GsonConfigured() {
        this.gson = configureGson();
    }

    // MODIFIES: this
    // EFFECTS: configures the Gson object to serialize null values, pretty print JSON, and recognize Patch subclasses
    private Gson configureGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // required so that Gson will serialize null values in patches, blocks, and quilt
        gsonBuilder.serializeNulls();
        // required to make .json file more readable to humans
        gsonBuilder.setPrettyPrinting();
        // required so that Gson can distinguish Patch subclasses when serializing and deserializing
        gsonBuilder.registerTypeAdapterFactory(makePatchRuntimeTypeAdapterFactory());
        return gsonBuilder.create();
    }

    // EFFECTS: creates a RuntimeTypeAdapterFactory capable of recognizing Patch subclasses during serialization
    //          and deserialization
    private RuntimeTypeAdapterFactory<Patch> makePatchRuntimeTypeAdapterFactory() {
        RuntimeTypeAdapterFactory<Patch> patchAdapter = RuntimeTypeAdapterFactory.of(Patch.class, "gsonType");
        patchAdapter.registerSubtype(Square.class, "a");
        patchAdapter.registerSubtype(HalfSquare.class, "b");
        patchAdapter.registerSubtype(HalfSquareTriangle.class, "c");
        return patchAdapter;
    }

    // getter
    public Gson getGson() {
        return gson;
    }

}
