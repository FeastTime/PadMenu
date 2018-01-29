package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.CommentListViewAdapter;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by chen on 2017/10/8.
 */

public class CommentActivity extends BaseActivity {

    ListView contentListView;

    @Bind(R.id.input_message)
    EditText inputMessage;

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

    public void sendMessage(View view){

        String inputMessageStr = inputMessage.getText().toString();
        if (TextUtils.isEmpty(inputMessageStr)){
            return;
        }

        HashMap<String, String > requestData = new HashMap<>();
        requestData.put("message", inputMessageStr);
        requestData.put("type", "1");

        UtilTools.requestByWebSocket(this, requestData);
    }
}
