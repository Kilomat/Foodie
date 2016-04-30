package foodie.project_training.com.foodie.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by beau- on 16/04/2016.
 */
public class GsonRequest<T> extends JsonRequest<T> {
    private final Gson gson;
    private final Type type;
    private final String body;
    private final Response.Listener<T> listener;
    private final Class<T> clazz = null;
    private final Map<String, String> headers = null;


/*    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }*/
/*
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }*/

    public GsonRequest(int method, String url, String body, Type type, Gson gson,
                       Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, body, listener, errorListener);
        this.gson = gson;
        this.type = type;
        this.body = body;
        this.listener = listener;
    }

    /*
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    public String getBodyContentType(){
        return "application/json";
    }
*/

    @Override
    public byte[] getBody() {
        return body.getBytes();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            System.out.println("response : " + json);
            return (Response<T>) Response.success(
                  gson.fromJson(json, type),
//                    new JSONObject(json),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } /*catch (JSONException e) {
            return Response.error(new ParseError(e));
        }*/
    }
}