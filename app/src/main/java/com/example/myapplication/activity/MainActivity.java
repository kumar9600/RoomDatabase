package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.adapter.Adapter;
import com.example.myapplication.listener.RecyclerTouchListener;
import com.example.myapplication.model.Result;
import com.example.myapplication.viewmodel.AddStoryViewModel;
import com.example.myapplication.model.StoriesDbModel;
import com.example.myapplication.utils.MySingleton;
import com.example.myapplication.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myapplication.utils.Utils.STORY;
import static com.example.myapplication.utils.Utils.STUDENTTOKEN;
import static com.example.myapplication.utils.Utils.USERTOKEN;
import static com.example.myapplication.utils.Utils.isNetworkAvailable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerStories)
    RecyclerView recyclerStories;

    private LinearLayoutManager layoutManager;
    private List<StoriesDbModel> storiesList = new ArrayList<>();
    private Adapter adapter;

    private String session = "";
    private int totalCount = 10;
    private int pageNumber = 1;

    private AddStoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(MainActivity.this).get(AddStoryViewModel.class);
        initListener();
        initAdapter();
        checkIfAlreadyLogin();
    }

    private void initListener() {
        recyclerStories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastInScreen = firstVisibleItemPosition + visibleItemCount;
                if (lastInScreen == totalCount) {
                    totalCount += 10;
                    pageNumber += 1;
                    getStories();
                }
            }
        });

        recyclerStories.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerStories, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*StoriesDbModel model = storiesList.get(position);
                viewModel.getSingleStory(model);*/
            }

            @Override
            public void onLongClick(View view, int position) {
                StoriesDbModel dbModel = storiesList.get(position);
                viewModel.deleteStory(MainActivity.this, dbModel, adapter, position, storiesList);
            }
        }));
    }

    private void initAdapter() {
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerStories.setLayoutManager(layoutManager);
        adapter = new Adapter(this, storiesList);
        recyclerStories.setAdapter(adapter);
    }

    private void checkIfAlreadyLogin() {
        SharedPreferences preferences = getSharedPreferences(Utils.PREF, MODE_PRIVATE);
        boolean isLogin = preferences.getBoolean(Utils.IS_LOGIN, false);
        if (!isLogin) {
            login();
        } else {
            if (isNetworkAvailable(this)) {
                getStories();
            } else {
                viewModel.getAllStoriesList().observe(this, new Observer<List<StoriesDbModel>>() {
                    @Override
                    public void onChanged(List<StoriesDbModel> storiesDbModels) {
                        storiesList.clear();
                        adapter.addItems(storiesDbModels);
                    }
                });
            }
        }
    }

    private void login() {

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        JSONObject params = new JSONObject();
        JSONObject baseJSon = new JSONObject();
        try {
            params.put("login", "5123456F");
            params.put("password", "123456");
            params.put("login_type", "parent");
            baseJSon.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Login Excpetion  : " + e);
        }

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Logging in...");
        dialog.show();

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, Utils.LOGIN, baseJSon, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e(TAG, "response : " + response);
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject resultJson = jsonObject.getJSONObject("result");
                    String responseStr = resultJson.getString("response");
                    dialog.dismiss();
                    if (responseStr.equalsIgnoreCase("success")) {
                        Result result = new Gson().fromJson(resultJson.toString(), Result.class);
                        SharedPreferences preferences = getSharedPreferences(Utils.PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Utils.SESSION, result.getSessionId());
                        editor.putInt(USERTOKEN, result.getUserToken());
                        editor.putInt(Utils.STUDENTTOKEN, result.getChildren().get(0).getStudentToken());
                        editor.putBoolean(Utils.IS_LOGIN, true);
                        editor.commit();
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        getStories();
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Login Exception : " + e);
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Json Excpetion", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e(TAG, "Login Exception : " + e);
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Cant login", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });

        loginRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(loginRequest);
    }

    private void getStories() {

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences preferences = getSharedPreferences(Utils.PREF, MODE_PRIVATE);
        session = preferences.getString(Utils.SESSION, "");
        session = Utils.SESSION + "=" + session;
        int userToken = preferences.getInt(USERTOKEN, 0);
        int studentToken = preferences.getInt(Utils.STUDENTTOKEN, 0);

        JSONObject params = new JSONObject();
        JSONObject baseJSon = new JSONObject();
        try {
            params.put("login_type", "parent");
            params.put(USERTOKEN, userToken);
            params.put(STUDENTTOKEN, studentToken);
            params.put("page", pageNumber);
            params.put("status", "approved");
            baseJSon.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Login Excpetion  : " + e);
        }

        JsonObjectRequest storyRequest = new JsonObjectRequest(Request.Method.POST, STORY, baseJSon, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e(TAG, "response : " + response);
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject resultJson = jsonObject.getJSONObject("result");
                    String responseStr = resultJson.getString("response");
                    if (responseStr.equalsIgnoreCase("success")) {
                        Result results = new Gson().fromJson(resultJson.toString(), Result.class);
                        List<StoriesDbModel> stories = results.getStories();
                        insertDataInPosition(stories);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, " Exception : " + e);
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e) {
                    Log.e(TAG, " Exception : " + e);
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Exception : " + error);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap map = new HashMap();
                map.put("Cookie", session);
                return map;
            }
        };
        storyRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(storyRequest);
    }

    private void insertDataInPosition(List<StoriesDbModel> stories) {
        int beginIndex = storiesList.size();
        storiesList.addAll(stories);
        int lastIndex = storiesList.size();
        adapter.notifyItemRangeChanged(beginIndex, lastIndex, stories);
        if (!stories.isEmpty()) {
            for (int i = 0; i < stories.size(); i++) {
                StoriesDbModel dbModel = stories.get(i);
             /*   List<String> images = dbModel.getMedia();
                if (!images.isEmpty()) {
                    for (int j = 0; j < images.size(); i++) {
                        dbModel.setStoriesImages(images.get(0));
                    }
                }*/
                viewModel.addStory(dbModel);
            }
        }
        Log.e(TAG, "Data inserted");
    }
}
