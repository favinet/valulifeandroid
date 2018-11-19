package org.tensorflow.demo.datainterface;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;

public class DataManager {

    private static DataManager mInstance;

    public DataInterface api;
    private Gson gson;
    private JsonObject ohcObj, buzzObj, pinObj;

    public static DataManager getInstance(Context context) {
        if (DataManager.mInstance == null) {
            synchronized (DataManager.class) {
                DataManager.mInstance = new DataManager(context);
            }
        }

        return DataManager.mInstance;
    }

    public DataManager(Context context)
    {
        api = DataInterface.getInstance();
        gson = new Gson();

    }

}
