package com.dante.knowledge.news.presenter;

import com.dante.knowledge.news.interf.NewsDetailPresenter;
import com.dante.knowledge.news.interf.NewsDetailView;
import com.dante.knowledge.news.interf.NewsModel;
import com.dante.knowledge.news.interf.OnLoadDetailListener;
import com.dante.knowledge.news.model.FreshDetail;
import com.dante.knowledge.news.model.FreshItem;
import com.dante.knowledge.news.model.FreshNews;
import com.dante.knowledge.news.model.FreshNewsModel;

/**
 * helps to present fresh news detail page
 */
public class FreshDetailPresenter implements NewsDetailPresenter<FreshItem>, OnLoadDetailListener<FreshDetail>{

    private NewsModel<FreshItem, FreshNews, FreshDetail> mNewsModel;
    private NewsDetailView<FreshDetail> newsDetailView;

    public FreshDetailPresenter(NewsDetailView<FreshDetail> newsDetailView) {
        this.mNewsModel = new FreshNewsModel();
        this.newsDetailView = newsDetailView;
    }

    @Override
    public void loadNewsDetail(FreshItem freshItem) {
        newsDetailView.showProgress();
        mNewsModel.getNewsDetail(freshItem, this);
    }

    @Override
    public void onDetailSuccess(FreshDetail detailNews) {
        newsDetailView.showDetail(detailNews);

    }

    @Override
    public void onFailure(String msg, Exception e) {
        newsDetailView.showLoadFailed(msg);
        newsDetailView.hideProgress();
    }
}
