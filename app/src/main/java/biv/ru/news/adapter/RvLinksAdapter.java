package biv.ru.news.adapter;

/**
 * Created by bIVeHb on 05.03.2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



import biv.ru.news.R;
import biv.ru.news.adapter.viewholder.LinksViewHolder;

import static android.content.ContentValues.TAG;


/**
 * Created by bIVeHb on 27.02.2018.
 */

public class RvLinksAdapter extends RecyclerView.Adapter<LinksViewHolder> {

    private List<String> mLinks = new ArrayList<>();

    private Context mContext;


    @Nullable
    private LinksViewHolder.LinkClickListener mLinkClickListener;

    public RvLinksAdapter (List<String> links, Context context) {
        mLinks.clear();
        mLinks.addAll(links);
        mContext = context;
    }

    @Override
    public LinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_links, parent, false);
        return new LinksViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LinksViewHolder holder, int position) {
        holder.bindView(mLinks, mLinkClickListener, mContext);
        holder.mTextTitle.setText(mLinks.get(position));
        holder.mTextNumber.setText(String.valueOf(position + 1));
    }


    public void setLinkClickListener(@NonNull LinksViewHolder.LinkClickListener linkClickListener){
        mLinkClickListener = linkClickListener;
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

