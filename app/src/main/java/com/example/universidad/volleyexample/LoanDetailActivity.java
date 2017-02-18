package com.example.universidad.volleyexample;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoanDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);

        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra("JSONContent")) {
            //TODO: Pass JSON info

            FragmentManager manager = this.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putString("JSONContent", intent.getStringExtra("JSONContent"));

            LoanDetailFragment fragment = new LoanDetailFragment();
            fragment.setArguments(bundle);

            transaction.add(R.id.loan_detail_holder, fragment);
            //transaction.addToBackStack(null);
            transaction.commit();
        } else {
            //TODO: display error
        }

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //if (getArguments().containsKey("JSONContent")) {
          //  Activity activity = this.getActivity();

            try {
                JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("JSONContent"));
                final int templateID = jsonObject.getJSONObject("image").getInt("template_id");
                final int imageID = jsonObject.getJSONObject("image").getInt("id");

                //CollapsingToolbarLayout appBarLayout = (Collap) findViewById(R.id.toolbar);
                //if (appBarLayout != null) {
                    JsonObjectRequest request = new JsonObjectRequest("http://api.kivaws.org/v1/templates/images.json", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("LoanDetailFragment", "Getting templates");
                                JSONArray templates = response.getJSONArray("templates");
                                for (int i = 0; i < templates.length(); i++) {
                                    int id = templates.getJSONObject(i).getInt("id");
                                    String pattern = templates.getJSONObject(i).getString("pattern");
                                    //TODO: Comparar ID
                                    if (id == templateID) {
                                        loadImage(pattern, imageID);
                                        return;
                                    }
                                }

                                Log.d("LoanDetailFragment", "No templates found");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("LoanDetailFragment", "Error");
                        }
                    });
                //}

                VolleySingleton.getInstance(this).addToRequestQueue(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        //} else {
          //  Log.d("LoanDetailFragment", "Should have some argument");
        //}*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            this.navigateUpTo(intent);

            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
