package com.dante.knowledge.news.model;

import com.dante.knowledge.net.API;
import com.dante.knowledge.net.GsonUtil;
import com.dante.knowledge.net.Net;
import com.dante.knowledge.news.interf.NewsModel;
import com.dante.knowledge.news.interf.OnLoadDetailListener;
import com.dante.knowledge.news.interf.OnLoadNewsListener;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * deals with the fresh news' data work
 */
public class FreshNewsModel implements NewsModel<FreshItem, FreshNews, FreshDetail> {
    /**
     * clear page record to zero and start new request
     */
    public static final int TYPE_FRESH = 0;
    /**
     * a continuous request with increasing one page each time
     */
    public static final int TYPE_CONTINUOUS = 1;

    private int page;
    private long lastGetTime;
    public static final int GET_DURATION = 2000;

    @Override
    public void getNews(int type, final OnLoadNewsListener<FreshNews> listener) {
        lastGetTime = System.currentTimeMillis();

        if (type == TYPE_FRESH) {
            page = 1;//如果是全新请求，就初始化page为1
        }
        getFreshNews(listener);
    }

    private void getFreshNews(final OnLoadNewsListener<FreshNews> listener) {
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                if (System.currentTimeMillis()-lastGetTime< GET_DURATION){
                    Net.get(API.FRESH_NEWS + page, this, API.TAG_FRESH);
                    return;
                }
                listener.onFailure("load fresh news failed", e);
            }

            @Override
            public void onResponse(String response) {
                FreshNews news = GsonUtil.parseFreshNews(response);
                listener.onNewsSuccess(news);
                page++;
            }
        };

        Net.get(API.FRESH_NEWS + page, callback, API.TAG_FRESH);
    }

    @Override
    public void getNewsDetail(FreshItem freshItem, final OnLoadDetailListener<FreshDetail> listener) {

        Net.get(API.FRESH_NEWS_DETAIL + freshItem.getId(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load fresh detail failed", e);

            }

            @Override
            public void onResponse(String response) {
                FreshDetail detail = GsonUtil.parseFreshDetail(response);
                listener.onDetailSuccess(detail);
            }
        }, API.TAG_FRESH_DETAIL);
    }
}
