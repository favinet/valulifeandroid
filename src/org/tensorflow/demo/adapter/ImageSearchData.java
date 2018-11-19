package org.tensorflow.demo.adapter;

import android.content.Context;

import java.util.HashMap;

public class ImageSearchData {

    private static ImageSearchData instance;

    private ImageSearchData imageSearchData = null;
    private HashMap<String, String> loginParams = new HashMap<>();

    public static ImageSearchData getInstance() {
        if (instance == null) {
            instance = new ImageSearchData();
        }
        return instance;
    }

    public void setCurrentUserVO(Context context, ImageSearchData imageSearchData)
    {
        this.imageSearchData = imageSearchData;

    }

    public ImageSearchData getCurrentImageSearchVO(Context context)
    {
        if(imageSearchData == null)
        {
            if(imageSearchData == null)
            {
                imageSearchData = new ImageSearchData();
            }
        }
        return this.imageSearchData;
    }


}
