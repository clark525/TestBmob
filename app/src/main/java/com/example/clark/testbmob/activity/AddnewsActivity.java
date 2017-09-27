package com.example.clark.testbmob.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clark.testbmob.R;
import com.example.clark.testbmob.bean.News;
import com.example.clark.testbmob.bean.Person;
import com.example.clark.testbmob.constant.PreferenceConst;
import com.example.clark.testbmob.util.PreferenceUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddnewsActivity extends AppCompatActivity {

    private EditText title, content;

    private boolean is_update = false;
    private News oldNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);

        if (getIntent().getExtras() != null) {
            oldNews = (News) getIntent().getSerializableExtra("data");
            title.setText(oldNews.getTitle());
            content.setText(oldNews.getContent());
            is_update = true;
        }


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(content.getText())) {
                    return;
                } else {
                    News news = new News();
                    news.setTitle(title.getText().toString());
                    news.setContent(content.getText().toString());
                    news.setUserid(PreferenceUtil.getPrefString(AddnewsActivity.this, PreferenceConst.OBJECTID, ""));
                    if (is_update) {
                        news.update(oldNews.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddnewsActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddnewsActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        news.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AddnewsActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddnewsActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                }
            }
        });
    }
}
