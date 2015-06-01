package com.example.administrator.threadtesting;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.threadtesting.adapter.ThreadTestCaseAdapter;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;


public class MainActivity extends Activity implements IOnItemClickListener {

    private RecyclerView mRvThreadTestCase;
    private TextView mTvTestCaseResult;

    private ThreadTestCaseAdapter mAdapter;
    private Object mLock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        setListener();
        init();
    }

    private void findView() {
        mRvThreadTestCase = (RecyclerView) findViewById(R.id.rv_test_case);
        mTvTestCaseResult = (TextView) findViewById(R.id.tv_test_result);
    }

    private void setListener() {

    }

    private void init() {
        mAdapter = new ThreadTestCaseAdapter(this, getResources().getStringArray(R.array.test_case_array));

        mAdapter.setOnItemClickListener(this);
        mTvTestCaseResult.setMovementMethod(new ScrollingMovementMethod());
        mRvThreadTestCase.setAdapter(mAdapter);
        mRvThreadTestCase.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(View view, int pos) {
        mTvTestCaseResult.setText("");

        switch(pos) {
            case 0: {
                int coreNums = Runtime.getRuntime().availableProcessors();
                int keepAliveTime = 1;
                TimeUnit timeUnit = TimeUnit.SECONDS;
                LinkedBlockingQueue<Runnable> workingQueue = new LinkedBlockingQueue<>();
                ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(coreNums, coreNums, keepAliveTime, timeUnit, workingQueue);

                for(int i = 0 ; i < 20 ; i++) {
                    poolExecutor.execute(createTestRunnable(i));
                }
            }
            break;

            case 1: {
                ExecutorService executor = Executors.newCachedThreadPool();
                final StringBuilder result = new StringBuilder("");

                for(int i = 0 ; i < 20 ; i++) {
                    executor.execute(createTestRunnable(i));
                }
            }
            break;
        }
    }

    private Runnable createTestRunnable(final int index) {
       Runnable taskRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Yomi", "index = " + index);
                synchronized (mLock) {
                    Log.d("Yomi", "inside critical section " + index);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvTestCaseResult.append(index + "\n");
                        }
                    });
                }
            }
        };

        return taskRunnable;
    }

}
