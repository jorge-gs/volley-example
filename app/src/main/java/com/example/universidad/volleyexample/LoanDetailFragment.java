package com.example.universidad.volleyexample;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

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
        try {
            String jsonString = getArguments().getString("JSONContent");
            json = new JSONObject(jsonString);
        } catch (Exception exeption) {
            this.displayError("Could not display loan information");

            Intent intent = new Intent(container.getContext(), MainActivity.class);
            this.getActivity().navigateUpTo(intent);
        }

        return inflater.inflate(R.layout.fragment_loan_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Toolbar toolbar = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);

        MainActivity activity = (MainActivity) (getActivity() instanceof MainActivity ? getActivity() : null);
        if (activity != null && !activity.isTablet) {
            ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        try {
            CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) this.getActivity().findViewById(R.id.toolbar_layout);
            toolbarLayout.setTitle(json.getString("name"));

            this.requestImage();

            ((TextView) this.getActivity().findViewById(R.id.country)).setText(json.getJSONObject("location").getString("country"));
            ((TextView) this.getActivity().findViewById(R.id.amount)).setText("$" + json.getInt("loan_amount"));
            ((TextView) this.getActivity().findViewById(R.id.use)).setText(json.getString("use"));
        } catch (Exception exception) {
            displayError("Could not display loan information");
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
                        displayError("Could not process image");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: Display error message
                    displayError("Could not fetch image");
                }
            });

            VolleySingleton.getInstance(this.getActivity()).addToRequestQueue(templateRequest);
    }

    private void setImageURL(String pattern, int id) {
        pattern = pattern.replace("<size>", "400");
        pattern = pattern.replace("<id>", "" + id);

        NetworkImageView imageView = (NetworkImageView) this.getActivity().findViewById(R.id.app_bar_image);
        if (imageView != null) {
            imageView.setImageUrl(pattern, VolleySingleton.getInstance(this.getActivity()).getImageLoader());
        }
    }

    private void displayError(String message) {
        Toast toast = Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
