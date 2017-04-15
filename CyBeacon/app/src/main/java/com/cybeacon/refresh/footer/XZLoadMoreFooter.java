package com.cybeacon.refresh.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.cybeacon.R;


public class XZLoadMoreFooter extends FrameLayout {
    public XZLoadMoreFooter(Context context) {
        super(context);
        init(context);
    }

    public XZLoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XZLoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private  void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.pull_to_load_footer, this, true);
        mLoadingView = findViewById(R.id.pull_to_load_footer_content);
        setStatus(Status.GONE);
    }

    private Status mStatus;

    private View mLoadingView;

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
        change();
    }

    public boolean canLoadMore() {
        return mStatus == Status.GONE || mStatus == Status.ERROR;
    }

    private void change() {
        switch (mStatus) {
            case GONE:
                mLoadingView.setVisibility(GONE);
                break;

            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                break;

            case ERROR:
                mLoadingView.setVisibility(GONE);
                break;

            case THE_END:
                mLoadingView.setVisibility(GONE);
                break;
        }
    }

    public enum Status {
        GONE, LOADING, ERROR, THE_END
    }

}
