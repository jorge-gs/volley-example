package com.example.universidad.volleyexample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        final LoanSummaryListFragment fragment = new LoanSummaryListFragment();
        transaction.add(R.id.loan_summary_list_holder, fragment);
        transaction.commit();

        JsonObjectRequest objectRequest = new JsonObjectRequest("http://api.kivaws.org/v1/loans/newest.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("loans");

                    for (int i = 0; i < array.length(); i++) {
                        fragment.addLoan((JSONObject) array.get(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MainActivity", "Error: " + error.getMessage());
            }
        });
        VolleySingleton.getInstance(this).getRequestQueue().add(objectRequest);
        //this.requestQueue.add(objectRequest);
    }
}
