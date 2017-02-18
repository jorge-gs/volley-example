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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            //Intent intent = new Intent(this, MainActivity.class);
            //this.navigateUpTo(intent);
            super.onBackPressed();

            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
