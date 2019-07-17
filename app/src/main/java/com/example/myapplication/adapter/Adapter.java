package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.StoriesDbModel;

import java.util.Collections;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = Adapter.class.getSimpleName();

    private Activity context;
    private LayoutInflater inflater;
    private List<StoriesDbModel> dbModels = Collections.emptyList();

    public Adapter(Activity context, List<StoriesDbModel> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dbModels = list;
    }

    public void addItems(List<StoriesDbModel> dbModels) {
        int beginIndex = this.dbModels.size();
        this.dbModels.addAll(dbModels);
        int lastIndex = this.dbModels.size();
        this.notifyItemRangeChanged(beginIndex, lastIndex);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.stories_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        StoriesDbModel story = dbModels.get(position);
        viewHolder.textView.setText("" + story.getShortDesc());
    }

    @Override
    public int getItemCount() {
        return dbModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
        }

    }
}
