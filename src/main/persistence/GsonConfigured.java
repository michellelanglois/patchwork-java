package persistence;

import model.patches.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;


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

    // getter
    public Gson getGson() {
        return gson;
    }

    // MODIFIES: this
    // EFFECTS: configures the Gson object to serialize null values, pretty print JSON, and recognize Patch subclasses
    private Gson configureGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(makePatchRuntimeTypeAdapterFactory());
        return gsonBuilder.create();
    }

    // EFFECTS: creates a RuntimeTypeAdapterFactory capable of recognizing Patch subclasses during serialization
    //          and deserialization
    // NOTE: code to created RuntimeTypeAdapterFactory adapted from Gson tutorial available at:
    // https://futurestud.io/tutorials/how-to-deserialize-a-list-of-polymorphic-objects-with-gson
    private RuntimeTypeAdapterFactory<Patch> makePatchRuntimeTypeAdapterFactory() {
        RuntimeTypeAdapterFactory<Patch> patchAdapter = RuntimeTypeAdapterFactory.of(Patch.class, "gsonType");
        patchAdapter.registerSubtype(Square.class, "a");
        patchAdapter.registerSubtype(HalfSquare.class, "b");
        patchAdapter.registerSubtype(HalfSquareTriangle.class, "c");
        return patchAdapter;
    }

}
