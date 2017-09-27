package com.example.clark.testbmob.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clark.testbmob.R;
import com.example.clark.testbmob.bean.JavaQuestions;
import com.example.clark.testbmob.bean.Person;
import com.example.clark.testbmob.constant.PreferenceConst;
import com.example.clark.testbmob.util.PreferenceUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    TextView tv;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv = (TextView) findViewById(R.id.tv);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<JavaQuestions> javaQuery = new BmobQuery<>();
                javaQuery.order("-createdAt");
                //设置每页数据个数
                javaQuery.setLimit(10);


                javaQuery.findObjects(new FindListener<JavaQuestions>() {
                    @Override
                    public void done(List<JavaQuestions> list, BmobException e) {
                        if (e == null) {
                            Log.e("testbmob_result", list.get(0).getAnswer());
                            tv.setText(list.get(0).getAnswer());
                        } else {
                            Log.e("testbmob_result", "___null");
                        }
                    }
                });


            }
        });
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.register:
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }

                Person p2 = new Person();
                p2.setUsername(username.getText().toString());
                p2.setPassword(password.getText().toString());

                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            PreferenceUtil.setPrefString(LoginActivity.this, PreferenceConst.OBJECTID, objectId);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.login:
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }
                BmobQuery<Person> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("username", username.getText().toString());
//                bmobQuery.addWhereEqualTo("password",password.getText().toString());

                bmobQuery.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if (e == null) {
                            if (list.size() > 0) {
                                if (list.get(0).getPassword().equals(password.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    PreferenceUtil.setPrefString(LoginActivity.this, PreferenceConst.OBJECTID, list.get(0).getObjectId());
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                break;
        }

    }
}
