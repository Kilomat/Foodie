package foodie.project_training.com.foodie.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import foodie.project_training.com.foodie.MainActivity;
import foodie.project_training.com.foodie.Meal.model.Meal;
import foodie.project_training.com.foodie.Momentum.model.Momentum;
import foodie.project_training.com.foodie.Restaurant.model.Restaurant;
import foodie.project_training.com.foodie.User.model.User;

/**
 * Created by beau- on 10/04/2016.
 */
public class FoodieLink {

    private Context context;
    private MaterialDialog dialog;
    private static final String PREFS_NAME = "PrefsFile";

    public FoodieLink(Context context, MaterialDialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void createUser(String email, String password) {

        User user = new User(email, password);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        CustomStringRequest request = new CustomStringRequest(Request.Method.POST,
                FoodiePath._USERS_,
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  objectResp = new JSONObject(response);
                            Toast.makeText(context, objectResp.getString("ok"), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void authUser(final String email, final String password) {

        User user = new User(email, password);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("password", user.getPassword());

        CustomStringRequest authRequest = new CustomStringRequest(Request.Method.POST,
                    FoodiePath._USERS_AUTH_,
                    jsonObject,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject  obj = new JSONObject(response);
                                SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("JWT", obj.getString("JWT"));
                                editor.putString("UID", obj.getString("uid"));
                                editor.commit();

                                dialog.dismiss();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            if (isNetworkAvailable()) {
                                if (error.networkResponse == null)
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                                else {
                                    try {
                                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                                        JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                        String errorString = errorObject.getString("error");
                                        Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                            }
                        }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(authRequest);
    }

    public void getUser(String _id, String jwt, final ServerCallBack callBack) {

        CustomStringRequest authRequest = new CustomStringRequest(Request.Method.GET,
                FoodiePath._USERS_ + "/" + _id + "/" + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(authRequest);

    }

    public void updateUser(User user, String jwt) {

        final JsonObject object = new JsonObject();
        object.addProperty("password", user.getPassword());
        object.addProperty("lastName", user.getLastName());
        object.addProperty("firstName", user.getFirstName());
        object.addProperty("adress", user.getAddress());
        object.addProperty("city", user.getCity());
        object.addProperty("zipcode", user.getZipcode());
        object.addProperty("bio", user.getBio());
        object.addProperty("gender", user.getGender());
        object.addProperty("phone", user.getPhone());

        CustomStringRequest request = new CustomStringRequest(Request.Method.PUT,
                FoodiePath._USERS_ + "/" + user.getId() + "/" + jwt,
                object,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  objectResp = new JSONObject(response);
                            Toast.makeText(context, objectResp.getString("ok"), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void monitorFriends(String userId, boolean friend, String jwt) {

        final JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("friend", friend);

        CustomStringRequest request = new CustomStringRequest(Request.Method.PUT,
                FoodiePath._USERS_ + "/" + userId + "/" + jwt,
                object,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  objectResp = new JSONObject(response);
                            Toast.makeText(context, objectResp.getString("ok"), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void addMoment(String content, String location, String jwt) {

        Momentum momentum = new Momentum();

        momentum.setContent(content);
        momentum.setLocation(location);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("content", momentum.getContent());
        jsonObject.addProperty("location", momentum.getLocation());

        CustomStringRequest request = new CustomStringRequest(Request.Method.POST,
                FoodiePath._MOMENT_ + jwt,
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject  objectResp = new JSONObject(response);
                            Toast.makeText(context, objectResp.getString("ok"), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void deleteMoment(Momentum momentum, String jwt, final ServerCallBack callBack) {

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("deleted", true);

        CustomStringRequest request = new CustomStringRequest(Request.Method.DELETE,
                FoodiePath._MOMENT_ + String.valueOf(momentum.getId()) + "/" + jwt,
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void getMomentByUser(String _id, String jwt, final ServerCallBack callBack) {

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                FoodiePath._MOMENT_ + _id + "/" + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    public void addRestaurant(Restaurant restaurant, String jwt, final ServerCallBack callBack) {

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", restaurant.getName());
        jsonObject.addProperty("adress", restaurant.getAddress());
        jsonObject.addProperty("city", restaurant.getCity());
        jsonObject.addProperty("description", restaurant.getDescription());
        jsonObject.addProperty("places", restaurant.getPlace());

        CustomStringRequest request = new CustomStringRequest(Request.Method.POST,
                FoodiePath._RESTAURANTS_ + jwt,
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callBack.onSuccess(new JSONObject(response));
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void updateRestaurant(Restaurant restaurant, String jwt, final ServerCallBack callBack) {

        final JsonObject object = new JsonObject();
        object.addProperty("name", restaurant.getName());
        object.addProperty("adress", restaurant.getAddress());
        object.addProperty("city", restaurant.getCity());
        object.addProperty("description", restaurant.getDescription());
        object.addProperty("places", restaurant.getPlace());

        CustomStringRequest request = new CustomStringRequest(Request.Method.PUT,
                FoodiePath._RESTAURANTS_ + restaurant.getId() + "/" + jwt,
                object,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    public void getInfoRestaurant(String restauId, String jwt, final ServerCallBack callBack) {

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                FoodiePath._RESTAURANTS_ + restauId + "/" + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void getRestaurants(String jwt, final ServerCallBack callBack) {

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                FoodiePath._RESTAURANTS_ + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void getRestaurantsByUser(String _id, String jwt, final ServerCallBack callBack) {

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                FoodiePath._RESTAURANTS_ + FoodiePath._USER_ + _id + "/" + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void addMeal(Meal meal, String jwt, final ServerCallBack callBack) {

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("restaurantId", meal.getRestaurantId().toString());
        jsonObject.addProperty("title", meal.getTitle());
        jsonObject.addProperty("description", meal.getDescription());
        jsonObject.addProperty("price", meal.getPrice());
        jsonObject.addProperty("city", meal.getCity());

        CustomStringRequest request = new CustomStringRequest(Request.Method.POST,
                FoodiePath._MEALS_ + jwt,
                jsonObject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callBack.onSuccess(new JSONObject(response));
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void getMeals(String mealId, String jwt, final ServerCallBack callBack) {

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                FoodiePath._MEALS_ + mealId + "/" + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void getMealsByRestaurant(String restaurantId, String jwt, final ServerCallBack callBack) {

        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                FoodiePath._MEALS_ + "restaurant/" + restaurantId + "/" + jwt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void monitorMeal(Meal meal, String jwt, final ServerCallBack callBack) {

        final JsonObject object = new JsonObject();
        object.addProperty("title", meal.getTitle());
        object.addProperty("description", meal.getDescription());
        object.addProperty("price", meal.getPrice());
        object.addProperty("active", meal.isActive());

        CustomStringRequest request = new CustomStringRequest(Request.Method.PUT,
                FoodiePath._MEALS_ + meal.getId().toString() + "/" + jwt,
                object,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public void participateToMeal(String mealId, boolean participation, String jwt, final ServerCallBack callBack) {

        final JsonObject object = new JsonObject();
        object.addProperty("mealId", mealId);
        object.addProperty("participation", participation);

        CustomStringRequest request = new CustomStringRequest(Request.Method.PUT,
                FoodiePath._MEALS_ + "participation/" + jwt,
                object,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            callBack.onSuccess(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();

                        if (isNetworkAvailable()) {
                            if (error.networkResponse == null)
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                            else {
                                try {
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    JSONObject errorObject = new JSONObject(new String(error.networkResponse.data));
                                    String errorString = errorObject.getString("error");
                                    Toast.makeText(context, "ERROR " + statusCode + " : " + errorString, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

}
