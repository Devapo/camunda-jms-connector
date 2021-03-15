package jmspublishconnector.impl.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static jmspublishconnector.impl.util.RequestExceptionHandler.requestException;

public class JsonValidator {

    public static boolean isJSONValid(String test) {
        try {
            JSONObject jsonObject = new JSONObject(test);
            // Check if input json cotains all neccessary keys
            if (jsonObject.length() != 2){
                requestException("JSON_ERROR");
                return false;
            }
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                requestException("JSON_ERROR");
                return false;
            }
        }
        return true;
    }
}
