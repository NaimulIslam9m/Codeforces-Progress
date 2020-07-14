package com.example.CF_Progress.Fragment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CF_Progress.R;

import java.util.List;

public class HandleListAdapter extends RecyclerView.Adapter<HandleListAdapter.ViewHolder> {

    private static ClickListener clickListener;
    Context context;
    List<String> handleNames;

    public HandleListAdapter(Context context, List<String> handleNames) {
        this.context = context;
        this.handleNames = handleNames;
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
        holder.handleTV.setText(handleNames.get(position));
    }

    @Override
    public int getItemCount() {
        return handleNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView handleTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            handleTV = itemView.findViewById(R.id.textViewId);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.OnItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.OnItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public interface ClickListener {
        void OnItemClick(int position, View v);
        void OnItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        HandleListAdapter.clickListener = clickListener;
    }
}
