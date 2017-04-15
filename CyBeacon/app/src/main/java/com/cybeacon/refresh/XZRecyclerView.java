package com.cybeacon.refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.cybeacon.refresh.footer.XZLoadMoreFooter;
import com.cybeacon.refresh.header.XZRefreshHeader;


public class XZRecyclerView extends IRecyclerView implements OnLoadMoreListener,OnRefreshListener{
    private XZRefreshHeader refreshHeadView;
    private XZLoadMoreFooter refreshFooterView;

    private OnRecyclerViewRefreshListener onRecyclerViewRefreshListener;

    public XZRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public XZRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XZRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void refreshComplete(){
        setRefreshing(false);
        refreshFooterView.setStatus(XZLoadMoreFooter.Status.GONE);
    }


    public void loadMoreComplete(){
        refreshFooterView.setStatus(XZLoadMoreFooter.Status.GONE);
    }
    /**
     * 初始化一些配置,原库的作者封装的不够方便
     * 这里统一处理一下
     * @param context 上下文
     */
    private void init(Context context){
        setOnLoadMoreListener(this);
        setOnRefreshListener(this);
        refreshHeadView = new XZRefreshHeader(context);
        refreshFooterView = new XZLoadMoreFooter(context);
        setRefreshHeaderView(refreshHeadView);
        setLoadMoreFooterView(refreshFooterView);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (onRecyclerViewRefreshListener != null){
            refreshFooterView.setStatus(XZLoadMoreFooter.Status.LOADING);
            onRecyclerViewRefreshListener.onLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        if (onRecyclerViewRefreshListener != null){
            onRecyclerViewRefreshListener.onRefresh();
        }
    }

    public interface OnRecyclerViewRefreshListener{

        /**
         * 上拉加载回调
         */
        void onLoadMore();

        /**
         * 下拉刷新回调
         */
        void onRefresh();
    }

    public OnRecyclerViewRefreshListener getOnRecyclerViewRefreshListener() {
        return onRecyclerViewRefreshListener;
    }

    public void setOnRecyclerViewRefreshListener(OnRecyclerViewRefreshListener onRecyclerViewRefreshListener) {
        this.onRecyclerViewRefreshListener = onRecyclerViewRefreshListener;
    }
}
