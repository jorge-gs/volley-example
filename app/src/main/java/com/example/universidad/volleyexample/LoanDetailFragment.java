package com.example.universidad.volleyexample;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by universidad on 2/11/17.
 */

public class LoanDetailFragment extends Fragment {
    JSONObject json;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loan_detail, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsonString = getArguments().getString("JSONContent");
        if (jsonString != null) {
            try {
                json = new JSONObject(jsonString);
            } catch (JSONException exception) {
                //TODO: Show error message
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout);
        if (toolbarLayout != null) {
            try {
                toolbarLayout.setTitle(json.getString("name"));
            } catch (JSONException exception) {
                //TODO: Show error message
            }
        }

        try {
            this.requestImage();

            ((TextView) this.getActivity().findViewById(R.id.country)).setText(json.getJSONObject("location").getString("country"));
            ((TextView) this.getActivity().findViewById(R.id.amount)).setText("$" + json.getInt("loan_amount"));
            ((TextView) this.getActivity().findViewById(R.id.use)).setText(json.getString("use"));
        } catch (JSONException exception) {
            //TODO: handle exception
        }
    }

    private void requestImage() throws JSONException {
            JSONObject imageJSON = json.getJSONObject("image");
            final int imageID = imageJSON.getInt("id");
            final int templateID = imageJSON.getInt("template_id");

            JsonObjectRequest templateRequest = new JsonObjectRequest("http://api.kivaws.org/v1/templates/images.json", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray templates = response.getJSONArray("templates");
                        for (int i = 0; i < templates.length(); i++) {
                            JSONObject template = templates.getJSONObject(i);
                            int id = template.getInt("id");
                            String pattern = template.getString("pattern");
                            if (templateID == id) {
                                setImageURL(pattern, imageID);
                            }
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: Display error message
                    Log.d("LoanDetailFragment", error.getMessage());
                }
            });

            VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(templateRequest);
    }

    private void setImageURL(String pattern, int id) {
        pattern = pattern.replace("<size>", "400");
        pattern = pattern.replace("<id>", "" + id);

        ((NetworkImageView) this.getActivity().findViewById(R.id.app_bar_image)).setImageUrl(pattern, VolleySingleton.getInstance(this.getActivity()).getImageLoader());
    }

}
