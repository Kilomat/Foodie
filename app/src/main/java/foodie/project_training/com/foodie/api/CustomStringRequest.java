package foodie.project_training.com.foodie.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by beau- on 16/04/2016.
 */
public class CustomStringRequest<T> extends StringRequest {
    private JsonObject jsonObject;

    public CustomStringRequest(int method,
                               String url,
                               JsonObject jsonObject,
                               Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.jsonObject = jsonObject;
    }

    public CustomStringRequest(int method,
                               String url,
                               Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return jsonObject.toString().getBytes();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
}
