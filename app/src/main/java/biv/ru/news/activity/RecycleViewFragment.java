package biv.ru.news.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import biv.ru.news.R;
import biv.ru.news.adapter.ILoadMore;
import biv.ru.news.adapter.RvLinksAdapter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by bIVeHb on 05.03.2018.
 */


public class RecycleViewFragment extends Fragment {

    private List<String> mListTitles = new ArrayList<>();
    private List<String> mListLinks = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;


    private String mUrl;

    public RecycleViewFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public RecycleViewFragment(String url, Context context) {
        mListTitles.clear();
        mUrl = url;
        mContext = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        RxExample myRx = new RxExample();
        myRx.example0(mUrl);

        Log.i("News", "mListLinks size = " + String.valueOf(mListLinks.size()));


        if (mListLinks.size() == 0)
            return null;
        else
            getTitles2(0, 5, myRx);




        //Log.i("News", mListTitles.toString());
        Log.i("News", "mListTitles size = " + String.valueOf(mListTitles.size()));
        //Log.i("News", "mListLinks size = " + String.valueOf(mListLinks.size()));


        //getTitles();

        View rootView = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        RecyclerView mRecyclerViewLinks = (RecyclerView) rootView.findViewById(R.id.recyclerViewLinks);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerViewLinks.setLayoutManager(llm);


        mRecyclerViewLinks.setNestedScrollingEnabled(false);
        mRecyclerViewLinks.setItemViewCacheSize(50);
        mRecyclerViewLinks.setDrawingCacheEnabled(true);
        mRecyclerViewLinks.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        RvLinksAdapter adapter = new RvLinksAdapter(mListTitles, mRecyclerViewLinks, mContext);
        mRecyclerViewLinks.setAdapter(adapter);

        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if (mListTitles.size() <= 50) {
                    mListTitles.add(null);
                    adapter.notifyItemInserted(mListTitles.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mListTitles.remove(mListTitles.size() - 1);
                            adapter.notifyItemRemoved(mListTitles.size());

                            //add more titles
                            int index = mListTitles.size();
                            int end = index + 5;
                            getTitles2(index, end, myRx);
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    }, 1000);
                } else {
                    Toast.makeText(mActivity, "Load data completed !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


/*    // get title from list of links
    private List<String> getTitles() {

        for (int j = 0; j < mListLinks.size(); j++) {
            try {
                String url = mListLinks.get(j);
                if (!url.startsWith("http://")) {
                    url = "http://" + url;
                }

                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .get();
                String title = document.title();
                mListTitles.add(title);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("TopNews", "afterCompile mListTitles size = " + String.valueOf(mListTitles.size()));
        return mListTitles;
    }*/

/*    //get list of links from URL
    private List<String> getListLinks(String url) {

        List<String> listString = new ArrayList<>();
        List<String> html = new ArrayList<>();

        Elements links = null;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();
            links = doc.getElementsByClass("wrapper");

            Log.i("TopNews", "(method getListLinks)links size = " + String.valueOf(links.size()));

            for (int k = 0; k < links.size(); k++) {
                html.add(links.get(k).toString());
            }

            Log.i("TopNews", "(method getListLinks)mHtml size = " + String.valueOf(html.size()));

            for (int i = 0; i < html.size(); i++) {

                Pattern p = Pattern.compile("(\\\\n)(.*?)[\\\\\\t]");
                Matcher m = p.matcher(html.get(i));

                while (m.find()) {

                    listString.add(m.group(2));
                }
            }

            Log.i("TopNews", "afterCompile mListLinks size = " + String.valueOf(listString.size()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        return listString;
    }*/

    public void getTitles2 (int indexStart, int indexEnd, RxExample example){

        Log.i("News", "mListLinks.subList size = " + String.valueOf(mListLinks.subList(indexStart, indexEnd)));


        Observable<String> observable = Observable.from(mListLinks.subList(indexStart, indexEnd));

        Action1<String> action = new Action1<String>() {
            @Override
            public void call(String s) {
                example.example2(s);
            }
        };

        observable.subscribe(action);



    }

    class RxExample {

        RxExample() {
        }

/*        mListLinks = myRx.getLinks.call(mUrl);

        for (int i = 0; i < mListLinks.size(); i++) {
            mListTitles.add(myRx.getTitle.call(mListLinks.get(i)));
        }*/

        Func1<List<String>, List<String>> getTitles = new Func1<List<String>, List<String>>() {
            String url = "";
            List<String> listTitles = new ArrayList<>();

            @Override
            public List<String> call(List<String> urls) {
                for (int i = 0; i < urls.size(); i++) {
                    url = urls.get(i);
                    try {
                        if (!url.startsWith("http://")) {
                            url = "http://" + url;
                        }
                        Document document = Jsoup.connect(url)
                                .userAgent("Mozilla/5.0")
                                .get();
                        String title = document.title();
                        listTitles.add(title);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.i("News", "Rx getTitles listTitles size = " + String.valueOf(listTitles.size()));
                }
                return listTitles;

            }
        };

        Func1<String, String> getTitle = new Func1<String, String>() {

            String title = "";

            @Override
            public String call(String url) {

                try {
                    if (!url.startsWith("http://")) {
                        url = "http://" + url;
                    }
                    Document document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .get();
                    title = document.title();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return title;
            }
        };

        Func1<String, List<String>> getLinks = new Func1<String, List<String>>() {
            @Override
            public List<String> call(String url) {
                List<String> listString = new ArrayList<>();
                List<String> html = new ArrayList<>();

                Elements links = null;

                try {
                    Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .get();
                    links = doc.getElementsByClass("wrapper");


                    for (int k = 0; k < links.size(); k++) {
                        html.add(links.get(k).toString());
                    }


                    for (int i = 0; i < html.size(); i++) {

                        Pattern p = Pattern.compile("(\\\\n)(.*?)[\\\\\\t]");
                        Matcher m = p.matcher(html.get(i));

                        while (m.find()) {

                            //getTitle.call(m.group(2));
                            //listString.add(getTitle.call(m.group(2)));
                            listString.add(m.group(2));
                        }
                    }

                    Log.i("News", "Rx getLinks listLinks size = " + String.valueOf(listString.size()));


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return listString;
            }
        };

        void example0(String url) {
            queryURLs(url)
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> urls) {
                            Log.i("News", "queryURLs example0");
                            mListLinks.clear();
                            mListLinks.addAll(urls);
                        }
                    });
        }

        void example1() {
            queryTitles()
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> titles) {
                            Log.i("News", "queryTitles example1");
                            mListTitles.clear();
                            mListTitles.addAll(titles);
                        }
                    });
        }

        void example2(String url) {
            queryTitle(url)
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String title) {
                            Log.i("News", "queryTitle example2");
                            //mListTitles.clear();
                            mListTitles.add(title);
                        }
                    });
        }

        // строка объявляет Observable метод, который принимает в виде входного параметра ссылку на сайт для парсинга
        // и возвращает результат парсинга в виде списка ссылок <List> с указанного сайта;
        Observable<List<String>> queryURLs(String url) {
            //  создает Observable, возвращающего список ссылок;
            return Observable.create(
                    // строка объявляет интерфейс OnSubscribe с одним методом (см. ниже), который вызовется при подписке;
                    new Observable.OnSubscribe<List<String>>() {
                        // перегружает метод call, который будет вызываться после подписки Subscriber;
                        @Override
                        public void call(Subscriber<? super List<String>> subscriber) {
                            // вызывает метод onNext для передачи данных Subscriber всякий раз, когда порождаются данные.
                            // Этот метод принимает в качестве параметра объект, испускаемый Observable;
                            subscriber.onNext(getLinks.call(url));
                            // Observable вызывает метод onCompleted() после того, как вызывает onNext в последний раз,
                            // если не было обнаружено никаких ошибок;
                            //subscriber.onError(new IOException("no permission / no internet / etc"));
                            subscriber.onCompleted();

                        }
                        // subscribeOn(Schedulers.io()) — метод subscribeOn подписывает всех Observable выше по цепочке на планировщик Schedulers.io();
                        // observeOn(AndroidSchedulers.mainThread()) — метод observeOn позволяет получить результат в основном потоке приложения.
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

        Observable<List<String>> queryTitles() {
            return Observable.create(new Observable.OnSubscribe<List<String>>() {
                @Override
                public void call(Subscriber<? super List<String>> subscriber) {
                    Log.i("News", "Before queryTitles mListLinks.size = " + String.valueOf(mListLinks.size()));
                    subscriber.onNext(getTitles.call(mListLinks));
                    subscriber.onCompleted();
                    Log.i("News", "After queryTitles mListLinks.size = " + String.valueOf(mListLinks.size()));
                }
            }).subscribeOn(Schedulers.io());
        }

        Observable<String> queryTitle(String url) {
            return Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    //Log.i("News", "queryTitles mListLinks.size = " + String.valueOf(mListLinks.size()));
                    subscriber.onNext(getTitle.call(url));
                    subscriber.onCompleted();
                }
            }).subscribeOn(Schedulers.io());
        }
    }
}

