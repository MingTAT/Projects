package com.ttu.ttu.sign.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ttu.ttu.R;

import java.util.List;

/**
 * @author Ming
 */

public class BeaconListAdapter extends BaseQuickAdapter<Beacon,BaseViewHolder> {
    public BeaconListAdapter(@LayoutRes int layoutResId, @Nullable List<Beacon> data) {
        super(layoutResId, data);
    }

    public BeaconListAdapter(@Nullable List<Beacon> data) {
        super(data);
    }

    public BeaconListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    protected void convert(BaseViewHolder helper, Beacon item) {
        helper.setText(R.id.beaconName,"mac:"+item.getMacAddress()+" uuid:"+item.getProximityUUID() );
    }
}
