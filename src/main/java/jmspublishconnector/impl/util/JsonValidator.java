package jmspublishconnector.impl.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static jmspublishconnector.impl.util.RequestExceptionHandler.requestException;

public class JsonValidator {

    public static boolean isJSONValid(String test) {
        try {
            JSONObject jsonObject = new JSONObject(test);

            // Check if input json cotains all neccessary keys
            if(jsonObject.has("msg")){
                if(!(jsonObject.has("bkey") || jsonObject.has("new_bkey"))){
                    requestException("JSON_ERROR");
                    return false;
                }
            } else {
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
