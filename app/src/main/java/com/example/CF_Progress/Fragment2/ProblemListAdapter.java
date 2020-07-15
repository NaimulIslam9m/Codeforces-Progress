package com.example.CF_Progress.Fragment2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.CF_Progress.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ViewHolder> implements Filterable {

    private static ClickListener clickListener;
    Context context;
    List<String> problemNames;
    List<String> problemNamesAll;


    public ProblemListAdapter(Context context, List<String> problemNames, List<String> problemNamesAll) {
        this.context = context;
        this.problemNames = problemNames;
        this.problemNamesAll = problemNamesAll;
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(problemNamesAll);
            } else {
                for (String problemsName: problemNamesAll) {
                    if (problemsName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(problemsName);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        // runs on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            problemNames.clear();
            problemNames.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

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
