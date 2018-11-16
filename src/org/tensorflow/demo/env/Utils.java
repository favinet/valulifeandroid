package org.tensorflow.demo.env;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;

import java.util.List;
import java.util.Locale;

public class Utils {

    public static Bitmap getBitmapFromFile(String filePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        Bitmap result = BitmapFactory.decodeFile(filePath, options);

        return result;
    }

    public static String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("I found these things:\n\n");

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
                message.append("\n");
            }
        } else {
            message.append("nothing");
        }

        return message.toString();
    }
}
