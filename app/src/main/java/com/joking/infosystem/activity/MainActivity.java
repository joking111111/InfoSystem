package com.joking.infosystem.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.joking.infosystem.adapter.StuInfoAdapter;
import com.joking.infosystem.bean.StuDetail;
import com.joking.infosystem.bean.StuInfo;
import com.joking.infosystem.bean.StuPrize;
import com.joking.infosystem.bean.StuScore;
import com.joking.infosystem.R;
import com.joking.infosystem.util.ViewUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private StuInfoAdapter mAdapter;
    private List<StuInfo> result = new ArrayList<>();

    private String[] Names = {"诸葛亮", "王朗", "江总", "谷阿莫", "金坷拉"};
    private int[] Pics = {R.drawable.duck, R.drawable.elephant,
            R.drawable.frog, R.drawable.rabbit, R.drawable.turtle};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StuInfoAdapter();
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View content = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.dialog_new_input, null);
                final AutoCompleteTextView name = (AutoCompleteTextView) content.findViewById(R.id.name);
                final AutoCompleteTextView clazz = (AutoCompleteTextView) content.findViewById(R.id.clazz);
                final AutoCompleteTextView NO_id = (AutoCompleteTextView) content.findViewById(R.id.NO_id);

                ViewUtil.createDialog(MainActivity.this, "新建", content,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (add(name, clazz, NO_id)) {
                                    Toast.makeText(MainActivity.this, "新建成功", Toast.LENGTH_SHORT).show();
                                    ViewUtil.setCancelable(dialog, true);
                                } else {
                                    Toast.makeText(MainActivity.this, "新建失败", Toast.LENGTH_SHORT).show();
                                    ViewUtil.setCancelable(dialog, false);
                                }
                            }
                        }).show();
            }
        });
    }

    private boolean add(AutoCompleteTextView name, AutoCompleteTextView clazz, AutoCompleteTextView NO_id) {
        // TODO: 2016/12/18 添加检查，但是inputType已经检查了。
        String text_name = name.getText().toString();
        String text_clazz = clazz.getText().toString();
        String text_NO_id = NO_id.getText().toString();

        boolean flag = true;
        if (TextUtils.isEmpty(text_name)) {
            name.setError(getString(R.string.not_null));
            flag = false;
        }
        if (TextUtils.isEmpty(text_clazz)) {
            clazz.setError(getString(R.string.not_null));
            flag = false;
        }
        if (TextUtils.isEmpty(text_NO_id)) {
            NO_id.setError(getString(R.string.not_null));
            flag = false;
        }
        if (!flag) {
            return false;
        }

        int item_clazz;
        int item_NO_id;
        try {
            item_clazz = Integer.valueOf(text_clazz);
            item_NO_id = Integer.valueOf(text_NO_id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Random random = new Random();

        StuInfo stuInfo = new StuInfo();
        stuInfo.setName(text_name);
        stuInfo.setClass_id(item_clazz);
        stuInfo.setNO_id(item_NO_id);
        stuInfo.setPic_id(Pics[random.nextInt(5)]);
        // TODO: 2016/12/18 部分刷新
        mAdapter.addItem(stuInfo);

        return stuInfo.save();
    }

    private boolean search(String query) {
        // TODO: 2016/12/17 搜索
        result.clear();
        List<StuInfo> s = DataSupport.where("name = ? or class_id = ? or NO_id = ?",
                query, query, query).find(StuInfo.class);

        result.addAll(s);

        if (result.size() > 0) {
            refresh();
            return true;
        } else {
            // 查找失败
            return false;
        }
    }

    private void refresh() {
        if (result.size() > 0) {
            mAdapter.refresh(result);
        } else {
            mAdapter.refresh(DataSupport.findAll(StuInfo.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        MenuItem menuItem = menu.findItem(R.id.search);//在菜单中找到对应控件的item
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(getResources().getString(R.string.search_input));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当搜索内容为空时此方法不会调用
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("all")) {
                    // TODO: 2016/12/20 搜索内容为空，搜索全部
                    Toast.makeText(MainActivity.this, "查找所有", Toast.LENGTH_SHORT).show();
                    result.clear();
                    refresh();
                } else if (search(query)) {
                    Toast.makeText(MainActivity.this, "查找成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "查找失败", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            // 关闭或清空内容也会回调这方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    onQueryTextSubmit(newText);
//                }

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.search:
//                View content = LayoutInflater.from(this).inflate(R.layout.dialog_add_input, null);
//                final AutoCompleteTextView search = (AutoCompleteTextView) content.findViewById(R.id.search);
//                ViewUtil.createDialog(this, "搜索", content,
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String text = search.getText().toString();
//                                if (search(search)) {
//                                    Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
//                                } else {
//
//                                    if (TextUtils.isEmpty(text)) {
//                                        // 搜索内容为空，搜索全部
//                                        Toast.makeText(MainActivity.this, "查找所有", Toast.LENGTH_SHORT).show();
////                                        Snackbar.make(CoordinatorLayout, "查找所有", Snackbar.LENGTH_SHORT).show();
//                                        result.clear();
//                                        refresh();
//                                    } else {
//                                        // 搜索内容不为空，择说明搜索失败
//                                        Toast.makeText(MainActivity.this, "查找失败", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        }).show();
//                break;
            case R.id.test:
                for (int i = 0; i < 5; i++) {
                    StuInfo stuInfo = new StuInfo(Names[i], 2014211190 + i, 2014210990 + i, Pics[i]);
                    stuInfo.save();
                    for (int j = 0; j < 4; j++) {
                        StuDetail stuDetail = new StuDetail(stuInfo.getNO_id(), "中共党员");
                        stuDetail.save();
                        StuPrize stuPrize = new StuPrize(stuInfo.getNO_id(), "三好学生");
                        stuPrize.save();
                        StuScore stuScore = new StuScore(stuInfo.getNO_id(), "通信原理" + j + "w分");
                        stuScore.save();
                    }
                }
                refresh();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO: 2016/12/18 搜索后回到这界面怎么办
        Log.i(TAG, "onStart: ");
        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy: ");
        mAdapter.recycle();
        mAdapter = null;
        result.clear();
        result = null;
    }
}