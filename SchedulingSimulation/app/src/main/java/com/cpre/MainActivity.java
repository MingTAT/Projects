package com.cpre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpre.adapter.TaskListAdapter;
import com.cpre.algorithms.EDF;
import com.cpre.algorithms.LLC;
import com.cpre.algorithms.RMS;
import com.cpre.algorithms.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author Ming
 */
public class MainActivity extends AppCompatActivity {

    private EditText computationInput;
    private EditText periodInput;
    private EditText deadLineInput;
    private Button addTaskButton;
    private Button scheduleButton;

    private RecyclerView taskListView;

    List<Task> taskList = new ArrayList<Task>();

    private TaskListAdapter taskListAdapter;

    private int idCounter = 0;

    /**
     * computation input
     */
    private int c;

    /**
     * period input
     */
    private int p;

    /**
     * deadLine input
     */
    private int d;
    /**
     * remove all task button
     */
    private Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        bindEvent();
    }

    /**
     * button onclick listener
     */
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addTaskButton:
                    if (!checkParams()) return;
                    addTask();
                    break;
                case R.id.scheduleButton:
                    if (taskList != null && taskList.size() > 0){
                        Intent intent = new Intent(MainActivity.this,ScheduleActivity.class);
                        intent.putExtra("taskList", (Serializable) taskList);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "task list is empty,please add task first!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.removeButton:
                    taskList.clear();
                    idCounter = 0;
                    if (taskListAdapter != null) taskListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };



    /**
     * add task to task list
     */
    private void addTask() {
        idCounter++;
        c = Integer.parseInt(computationInput.getText().toString());
        p = Integer.parseInt(periodInput.getText().toString());
        d = Integer.parseInt(deadLineInput.getText().toString());
        taskList.add(new Task(idCounter, c, p, d));
        if (taskListAdapter == null){
            taskListAdapter = new TaskListAdapter(MainActivity.this,R.layout.item_task_layout,taskList);
            taskListView.setAdapter(taskListAdapter);
        }else {
            taskListAdapter.notifyDataSetChanged();
        }
        Toast.makeText(MainActivity.this, "add task success", Toast.LENGTH_SHORT).show();
    }

    /**
     * check input params
     */
    private boolean checkParams() {
        if (TextUtils.isEmpty(computationInput.getText().toString())) {
            Toast.makeText(MainActivity.this, "C can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(periodInput.getText().toString())) {
            Toast.makeText(MainActivity.this, "P can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(deadLineInput.getText().toString())) {
            Toast.makeText(MainActivity.this, "D can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * bind event
     */
    private void bindEvent() {
        addTaskButton.setOnClickListener(onClickListener);
        scheduleButton.setOnClickListener(onClickListener);
        removeButton.setOnClickListener(onClickListener);
        taskListView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
    }

    /**
     * bind view
     */
    private void bindView() {
        computationInput = (EditText) findViewById(R.id.computationInput);
        periodInput = ((EditText) findViewById(R.id.periodInput));
        deadLineInput = ((EditText) findViewById(R.id.deadLineInput));
        addTaskButton = ((Button) findViewById(R.id.addTaskButton));
        scheduleButton = ((Button) findViewById(R.id.scheduleButton));
        removeButton = ((Button) findViewById(R.id.removeButton));
        taskListView = ((RecyclerView) findViewById(R.id.taskListView));
    }
}
