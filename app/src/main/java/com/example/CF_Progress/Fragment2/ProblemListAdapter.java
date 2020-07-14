package com.example.CF_Progress.Fragment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.CF_Progress.R;

import java.util.List;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> {

    private static ClickListener clickListener;
    Context context;
    List<String> problemNames;

    public ProblemListAdapter(Context context, List<String> problemNames) {
        this.context = context;
        this.problemNames = problemNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.sample_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.problemTV.setText(problemNames.get(position));
    }

    @Override
    public int getItemCount() {
        return problemNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView problemTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problemTV = itemView.findViewById(R.id.textViewId);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.OnItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void OnItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ProblemListAdapter.clickListener = clickListener;
    }
}
