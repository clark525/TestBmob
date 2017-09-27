package com.example.clark.testbmob.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clark.testbmob.R;
import com.example.clark.testbmob.bean.News;
import com.example.clark.testbmob.constant.PreferenceConst;
import com.example.clark.testbmob.util.PreferenceUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listview = (ListView) findViewById(R.id.listview);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddnewsActivity.class));
            }
        });
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listview.setAdapter(null);
                BmobQuery<News> bmobQuery = new BmobQuery<>();
                bmobQuery.findObjects(new FindListener<News>() {
                    @Override
                    public void done(List<News> list, BmobException e) {
                        if (e == null) {
                            listview.setAdapter(new NewsAdapter(MainActivity.this, list));
                        } else {
                            Toast.makeText(MainActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        BmobQuery<News> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if (e == null) {
                    listview.setAdapter(new NewsAdapter(MainActivity.this, list));
                } else {
                    Toast.makeText(MainActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class NewsAdapter extends BaseAdapter {

        private List<News> mList;
        private Context mContext;

        public NewsAdapter(Context context, List<News> list) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public News getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v;
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                v = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
                viewHolder.newsitem_ll = (LinearLayout) v.findViewById(R.id.newsitem_ll);
                viewHolder.newsitem_title_tv = (TextView) v.findViewById(R.id.newsitem_title_tv);
                viewHolder.newsitem_content_tv = (TextView) v.findViewById(R.id.newsitem_content_tv);
                viewHolder.newsitem_time_tv = (TextView) v.findViewById(R.id.newsitem_time_tv);
                v.setTag(viewHolder);
            } else {
                v = convertView;
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.newsitem_title_tv.setText(mList.get(position).getTitle());
            viewHolder.newsitem_content_tv.setText(mList.get(position).getContent());
            viewHolder.newsitem_time_tv.setText(mList.get(position).getUpdatedAt());

            viewHolder.newsitem_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, AddnewsActivity.class)
                            .putExtra("data", mList.get(position)));
                }
            });

            viewHolder.newsitem_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mList.get(position).delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                findViewById(R.id.refresh).performClick();
                            } else {
                                Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    return true;
                }
            });
            return v;
        }
    }

    private static class ViewHolder {
        private TextView newsitem_title_tv;
        private TextView newsitem_content_tv;
        private TextView newsitem_time_tv;
        private LinearLayout newsitem_ll;
    }
}
