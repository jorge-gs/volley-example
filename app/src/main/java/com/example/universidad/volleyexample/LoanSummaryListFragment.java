package com.example.universidad.volleyexample;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

/**
 * Created by universidad on 2/3/17.
 */

public class LoanSummaryListFragment extends Fragment {
    public interface OnLoanSummaryListFragmentClickListener {
        void onLoanSummaryListFragmentClick(int position);
    }

    public OnLoanSummaryListFragmentClickListener callback;
    public LoanSummaryAdapter adapter;

    public void addLoan(JSONObject loan) {
        this.adapter.addLoan(loan);
    }

    public LoanSummaryListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adapter = new LoanSummaryAdapter();
        this.adapter.setCallback(this.callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_summary_list, container, false);

        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(this.adapter);
        }

        return view;
    }
}
