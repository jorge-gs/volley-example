package com.example.universidad.volleyexample;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by universidad on 2/3/17.
 */

public class LoanSummaryAdapter extends RecyclerView.Adapter<LoanSummaryAdapter.ViewHolder> {
    private String[][] data;

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

    public LoanSummaryAdapter(String[][] data) {
        this.data = data;
    }

    @Override
    public LoanSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_loan_summary, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameView.setText(this.data[position][0]);
        holder.amountView.setText(this.data[position][1]);
        holder.useView.setText(this.data[position][2]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
