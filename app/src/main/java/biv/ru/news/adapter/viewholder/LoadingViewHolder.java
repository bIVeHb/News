package biv.ru.news.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import biv.ru.news.R;

/**
 * Created by bIVeHb on 12.03.2018.
 */

public class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar mProgressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}
