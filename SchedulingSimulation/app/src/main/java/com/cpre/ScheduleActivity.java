package com.cpre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cpre.adapter.SchedulerResultAdapter;
import com.cpre.algorithms.EDF;
import com.cpre.algorithms.LLC;
import com.cpre.algorithms.RMS;
import com.cpre.algorithms.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * author Ming
 */
public class ScheduleActivity extends AppCompatActivity {

    RMS rmsScheduler;
    EDF edfScheduler;
    LLC llcScheduler;

    List<Task> taskList;
    /**
     * rms list view
     */
    private RecyclerView rmsListView;
    /**
     * edf list view
     */
    private RecyclerView edfListView;
    /**
     * llf list view
     */
    private RecyclerView llfListView;
    /**
     * empty tip view
     */
    private TextView rmsListEmptyTip;
    /**
     * edf empty tip view
     */
    private TextView edfListEmptyTip;
    /**
     * llf empty tip view
     */
    private TextView llfListEmptyTip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_layout);
        taskList = (List<Task>) getIntent().getSerializableExtra("taskList");
        if (taskList != null && taskList.size() > 0){
            bindView();
            bindData();
        }
    }

    /**
     * bind data
     */
    private void bindData() {
        scheduleTask();
        // set up adapter
        if (rmsScheduler!=null && rmsScheduler.getIDAry() != null) {
            rmsListView.setAdapter(new SchedulerResultAdapter(ScheduleActivity.this,R.layout.item_schedule_layout,getIntegerList(rmsScheduler.getIDAry())));
        }else {
            //empty view
            rmsListView.setVisibility(View.GONE);
            rmsListEmptyTip.setText("This task set is not schedulable in RMS");
            rmsListEmptyTip.setVisibility(View.VISIBLE);
        }
        if (edfScheduler != null && edfScheduler.getIDAry() != null){
            edfListView.setAdapter(new SchedulerResultAdapter(ScheduleActivity.this,R.layout.item_schedule_layout,getIntegerList(edfScheduler.getIDAry())) );
        }else {
            //empty view
            edfListView.setVisibility(View.GONE);
            edfListEmptyTip.setText("This task set is not schedulable in EDF");
            edfListEmptyTip.setVisibility(View.VISIBLE);
        }
        if (llcScheduler != null && llcScheduler.getIDAry() != null){
            llfListView.setAdapter(new SchedulerResultAdapter(ScheduleActivity.this,R.layout.item_schedule_layout,getIntegerList(llcScheduler.getIDAry())) );
        }else {
            //empty view
            llfListView.setVisibility(View.GONE);
            llfListEmptyTip.setText("This task set is not schedulable in LLF");
            llfListEmptyTip.setVisibility(View.VISIBLE);
        }
    }

    /**
     * bind  view
     */
    private void bindView() {
        rmsListView = ((RecyclerView) findViewById(R.id.rmsListView));
        edfListView = ((RecyclerView) findViewById(R.id.edfListView));
        llfListView = ((RecyclerView) findViewById(R.id.llfListView));
        rmsListEmptyTip = ((TextView) findViewById(R.id.rmsListEmptyTip));
        edfListEmptyTip = ((TextView) findViewById(R.id.edfListEmptyTip));
        llfListEmptyTip = ((TextView) findViewById(R.id.llfListEmptyTip));

        rmsListView.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this,LinearLayoutManager.HORIZONTAL,false));
        edfListView.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this,LinearLayoutManager.HORIZONTAL,false));
        llfListView.setLayoutManager(new LinearLayoutManager(ScheduleActivity.this,LinearLayoutManager.HORIZONTAL,false));
    }

    /**
     * schedule task
     */
    private void scheduleTask() {
        rmsScheduler = new RMS(taskList);
        edfScheduler = new EDF(taskList);
        llcScheduler = new LLC(taskList);
    }

    /**
     * get list of id
     * @param ids  id array
     * @return  list of id
     */
    private List<Integer> getIntegerList(int []ids){
        List<Integer> list = new ArrayList();
        if (ids != null && ids.length > 0){
            for (int id : ids) {
                list.add(id);
            }
        }
        return list;
    }
}
