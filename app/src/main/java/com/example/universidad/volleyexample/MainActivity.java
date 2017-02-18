package com.example.universidad.volleyexample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoanSummaryListFragment.OnLoanSummaryListFragmentClickListener {
    boolean isTablet = false;

    LoanSummaryListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.loan_detail_holder) != null) {
            this.isTablet = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (this.listFragment == null) {

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            this.listFragment = new LoanSummaryListFragment();
            this.listFragment.callback = this;

            transaction.replace(R.id.loan_summary_list_holder, this.listFragment);
            transaction.commit();

            this.requestList();
        }
    }

    public void onLoanSummaryListFragmentClick(int position) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("JSONContent", this.listFragment.adapter.loans.get(position).toString());

        LoanDetailFragment detailFragment = new LoanDetailFragment();
        detailFragment.setArguments(bundle);

        transaction.replace(this.isTablet ? R.id.loan_detail_holder : R.id.loan_summary_list_holder, detailFragment);
        if (!this.isTablet) { transaction.addToBackStack(null); }
        transaction.commit();

        return;
    }

    private void requestList() {
        JsonObjectRequest objectRequest = new JsonObjectRequest("http://api.kivaws.org/v1/loans/newest.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("loans");

                    if (isTablet) {
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        Bundle bundle = new Bundle();
                        bundle.putString("JSONContent", array.getJSONObject(0).toString());

                        LoanDetailFragment detailFragment = new LoanDetailFragment();
                        detailFragment.setArguments(bundle);

                        transaction.replace(R.id.loan_detail_holder, detailFragment);
                        transaction.commit();
                    }

                    for (int i = 0; i < array.length(); i++) {
                        listFragment.addLoan((JSONObject) array.get(i));
                    }
                } catch (JSONException e) {
                    Toast toast = Toast.makeText(MainActivity.this, "Could not read loans information", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(MainActivity.this, "Could not fetch loans information", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(objectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (!this.isTablet && item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack();

            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
