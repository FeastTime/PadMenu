package com.feasttime.dishmap;

import android.os.Bundle;
import android.widget.ListView;

import com.feasttime.dishmap.adapter.CommentListViewAdapter;

import java.util.ArrayList;

/**
 * Created by chen on 2017/10/8.
 */

public class CommentActivity extends BaseActivity {
    ListView contentListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        contentListView = (ListView) this.findViewById(R.id.activity_comment_content_lv);
        ArrayList<String> testData = new ArrayList<String>();
        testData.add("小红");
        testData.add("多少");
        testData.add("发");
        testData.add("是的方式");
        testData.add("氛围");
        testData.add("吃大餐");

        CommentListViewAdapter commentListViewAdapter = new CommentListViewAdapter(this,testData);
        contentListView.setAdapter(commentListViewAdapter);
    }
}
