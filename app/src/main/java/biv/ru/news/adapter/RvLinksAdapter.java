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
import biv.ru.news.adapter.viewholder.LoadingViewHolder;

import static android.content.ContentValues.TAG;


/**
 * Created by bIVeHb on 27.02.2018.
 */

public class RvLinksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mLinks = new ArrayList<>();

    private Context mContext;

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private ILoadMore mLoadMore;
    private boolean mIsLoading;
    private Context mActivity;

    private int mVisibleThreshold = 5;
    private int mLastVisibleItem;
    private int mTotalItemCount;


    @Nullable
    private LinksViewHolder.LinkClickListener mLinkClickListener;

    public RvLinksAdapter (List<String> links, RecyclerView recyclerView, Context activity) {
        mLinks.clear();
        mLinks.addAll(links);
        this.mActivity = activity;
        //mContext = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCount = linearLayoutManager.getItemCount();
                mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)){
                    if (mLoadMore != null){
                        mLoadMore.onLoadMore();
                    }
                    mIsLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_links, parent, false);
            return new LinksViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LinksViewHolder) {
            LinksViewHolder viewHolder = (LinksViewHolder) holder;
            viewHolder.mTextNumber.setText(String.valueOf(position + 1));
            viewHolder.mTextTitle.setText(mLinks.get(position));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.mProgressBar.setIndeterminate(true);
        }
    }


    public void setLinkClickListener(@NonNull LinksViewHolder.LinkClickListener linkClickListener){
        mLinkClickListener = linkClickListener;
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public void setLoaded() {
        mIsLoading = false;
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int layoutPosition = holder.getLayoutPosition();
        Log.d(TAG, "onViewAttachedToWindow: getayoutPosition = " + layoutPosition);

        layoutPosition = holder.getAdapterPosition();
        Log.d(TAG, "onViewAttachedToWindow: getAdapterPosition = " + layoutPosition);
    }

    @Override
    public int getItemViewType(int position) {
        return mLinks.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.mLoadMore = loadMore;
    }

}

