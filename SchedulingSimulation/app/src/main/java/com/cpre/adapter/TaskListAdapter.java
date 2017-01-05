package com.cpre.adapter;

import android.content.Context;

import com.cpre.R;
import com.cpre.algorithms.Task;
import com.cpre.base.adapter.BaseCommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * author Ming
 */

public class TaskListAdapter extends BaseCommonAdapter<Task> {
    public TaskListAdapter(Context context, int layoutId, List<Task> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Task task, int position) {
        holder.setText(R.id.taskName,"task "+position);
        holder.setText(R.id.taskAttr,"C:" + task.getC()+" P:"+task.getP()+" D:"+task.getD());
    }
}
