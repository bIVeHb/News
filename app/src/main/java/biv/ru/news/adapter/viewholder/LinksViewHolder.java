package biv.ru.news.adapter.viewholder;

/**
 * Created by bIVeHb on 05.03.2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import biv.ru.news.R;

/**
 * Created by bIVeHb on 27.02.2018.
 */

public class LinksViewHolder extends RecyclerView.ViewHolder{

    private CardView mCardView;
    public TextView mTextTitle;
    public TextView mTextNumber;

    public LinksViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.cardViewLinks);
        mTextTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        mTextNumber = (TextView) itemView.findViewById(R.id.txtNumber);
    }

    public interface LinkClickListener{
        void onLinkClick(@NonNull List<String> links);
    }

    // Передаем сюда модель из адаптера
    public void bindView(@NonNull List<String> links, LinkClickListener linkClickListener, Context context){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkClickListener != null){
                    linkClickListener.onLinkClick(links);
                }
            }
        });
    }
}

