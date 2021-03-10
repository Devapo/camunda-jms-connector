package jmspublishconnector.impl.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonValidator {

    List<String> jsonKeys = List.of("bkey", "new_bkey", "msg");

    public static boolean isJSONValid(String test) {
        try {
            JSONObject jsonObject = new JSONObject(test);

            // Check if input json cotains all neccessary keys
            if(jsonObject.has("msg")){
                if(!(jsonObject.has("bkey") || jsonObject.has("new_bkey"))){
                    return false;
                }
            } else {
                return false;
            }

        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
