package com.example.universidad.volleyexample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by universidad on 2/3/17.
 */

public class LoanSummaryAdapter extends RecyclerView.Adapter<LoanSummaryAdapter.ViewHolder> {
    private List<JSONObject> loans = new ArrayList<JSONObject>();

    public void addLoan(JSONObject loan) {
        this.loans.add(loan);
        this.notifyItemInserted(this.loans.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView amountView;
        public TextView useView;

        public ViewHolder(View view) {
            super(view);

            this.nameView = (TextView) view.findViewById(R.id.name);
            this.amountView = (TextView) view.findViewById(R.id.amount);
            this.useView = (TextView) view.findViewById(R.id.use);
        }
    }

    public LoanSummaryAdapter() {
    }

    @Override
    public LoanSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_loan_summary, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Log.d("LoanSummaryAdapter", "Hallo");
            String country = this.loans.get(position).getJSONObject("location").getString("country");
            holder.nameView.setText(this.loans.get(position).getString("name") + " - " + country);
            holder.amountView.setText("$" + this.loans.get(position).getInt("loan_amount"));
            holder.useView.setText(this.loans.get(position).getString("use"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }
}
