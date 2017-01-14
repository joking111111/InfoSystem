package com.joking.infosystem.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joking.infosystem.bean.StuBase;
import com.joking.infosystem.bean.StuDetail;
import com.joking.infosystem.bean.StuInfo;
import com.joking.infosystem.bean.StuPrize;
import com.joking.infosystem.bean.StuScore;
import com.joking.infosystem.R;
import com.joking.infosystem.util.BeanUtil;
import com.joking.infosystem.util.ViewUtil;
import com.joking.infosystem.widget.MyView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    public static final int TYPE_DETAIL = 0;
    public static final int TYPE_PRIZE = 1;
    public static final int TYPE_SCORE = 2;

    private StuInfo mStuInfo;
    private boolean isUpdate = false;

    private LinearLayout ll_detail;
    private LinearLayout ll_prize;
    private LinearLayout ll_score;

    private List<MyView> contents = new ArrayList<>();
    private MyView list_detail;
    private MyView list_prize;
    private MyView list_score;

    private List<StuBase> tempAdd = new ArrayList<>();
    private List<StuBase> tempDelete = new ArrayList<>();

    public static void actionStart(Context context, String id, int... flags) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(StuInfo.ID, id);
        if (flags.length > 0) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        initListener();
    }

    private void setDeleteListener(final MyView myView) {
        myView.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = myView.getType();
                int id = myView.getId();

                getLL(type).removeView(myView);
                contents.remove(myView);

                Class<? extends StuBase> clazz = BeanUtil.getClazz(type);
                StuBase first = DataSupport.where("id = ?", "" + id).findFirst(clazz);

                // 防止冲突！不用考虑性能。。
//                if (tempAdd.contains(first)) {
//                    tempAdd.remove(first);
//                }
                tempDelete.add(first);
                first.delete();
            }
        });
    }

    private void initListener() {
        // TODO: 2016/12/18 设置监听只需一次
        list_detail.setBtnOnClickListener(new MyOnclickListener(TYPE_DETAIL));
        list_prize.setBtnOnClickListener(new MyOnclickListener(TYPE_PRIZE));
        list_score.setBtnOnClickListener(new MyOnclickListener(TYPE_SCORE));
    }

    private class MyOnclickListener implements View.OnClickListener {
        private int type;

        public MyOnclickListener(int type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            View content = LayoutInflater.from(DetailActivity.this).inflate(R.layout.dialog_add_input, null);
            final AutoCompleteTextView newer = (AutoCompleteTextView) content.findViewById(R.id.search);
            ViewUtil.createDialog(DetailActivity.this, "添加", content,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String text = newer.getText().toString();
                            // 这里不用禁用
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(DetailActivity.this, "失败", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                addView(type, text);
                            }
                        }
                    }).show();
        }
    }

    private void addView(int type, String content) {
        StuBase newer = BeanUtil.createBean(type);
        newer.setNO_id(mStuInfo.getNO_id());
        newer.setContent(content);

        // 防止冲突！不用考虑性能。。
//            if (tempDelete.contains(newer)) {
//                tempDelete.remove(newer);
//            }
        tempAdd.add(newer);
        newer.save();

        MyView myView = ViewUtil.createMyView(DetailActivity.this, newer, type);
        myView.setBtnVisibility(true);
        setDeleteListener(myView);

        getLL(type).addView(myView);
        contents.add(myView);
    }

    private LinearLayout getLL(int type) {
        switch (type) {
            case TYPE_DETAIL:
                return ll_detail;
            case TYPE_PRIZE:
                return ll_prize;
            case TYPE_SCORE:
                return ll_score;
            default:
                break;
        }
        return null;
    }

    private void initViews() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(StuInfo.ID);
        if (!TextUtils.isEmpty(id)) {
            mStuInfo = DataSupport.where("id = ?", id).findFirst(StuInfo.class);
            List<StuDetail> stuDetails = DataSupport
                    .where("NO_id = ?", "" + mStuInfo.getNO_id()).find(StuDetail.class);
            List<StuPrize> stuPrizes = DataSupport
                    .where("NO_id = ?", "" + mStuInfo.getNO_id()).find(StuPrize.class);
            List<StuScore> stuScores = DataSupport
                    .where("NO_id = ?", "" + mStuInfo.getNO_id()).find(StuScore.class);

            ImageView stu_pic = (ImageView) findViewById(R.id.iv_pic);
            TextView stu_class = (TextView) findViewById(R.id.tv_class);
            TextView stu_NO = (TextView) findViewById(R.id.tv_NO);

//            item_stu_pic.setImageResource(mStuInfo.getPic_id());
            Glide.with(this).load(mStuInfo.getPic_id()).into(stu_pic);
            stu_class.setText("" + mStuInfo.getClass_id());
            stu_NO.setText("" + mStuInfo.getNO_id());

            list_detail = (MyView) findViewById(R.id.list_detail);
            list_prize = (MyView) findViewById(R.id.list_prize);
            list_score = (MyView) findViewById(R.id.list_score);

            ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
            ll_prize = (LinearLayout) findViewById(R.id.ll_prize);
            ll_score = (LinearLayout) findViewById(R.id.ll_score);

            addViews(ll_detail, stuDetails, TYPE_DETAIL);
            addViews(ll_prize, stuPrizes, TYPE_PRIZE);
            addViews(ll_score, stuScores, TYPE_SCORE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        if (mStuInfo != null) {
            toolbar.setTitle(mStuInfo.getName());
        }
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationContentDescription("返回");
        setSupportActionBar(toolbar);
    }

    private void addViews(LinearLayout ll, List<? extends StuBase> list, int type) {
        for (StuBase stuBase : list) {
            MyView myView = ViewUtil.createMyView(this, stuBase, type);
            setDeleteListener(myView);

            contents.add(myView);
            ll.addView(myView);
        }
    }

    private void save() {
        // TODO: 2016/12/18 保存
        // 由于是异步保存，需要时间的，所以不能先save再delete？
        for (StuBase s : tempDelete) {
            // 崩是因为添加又删除后，这个对象根本没调用过save方法
//                    if (s.isSaved()) {
//                        s.delete();
//                    }
            s.delete();
        }
        for (StuBase s : tempAdd) {
            s.save();
        }
        tempAdd.clear();
        tempDelete.clear();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isUpdate) {
            menu.findItem(R.id.edit).setVisible(false);
            menu.findItem(R.id.delete).setVisible(false);
            menu.findItem(R.id.save).setVisible(true);
        } else {
            menu.findItem(R.id.edit).setVisible(true);
            menu.findItem(R.id.delete).setVisible(true);
            menu.findItem(R.id.save).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                save();// 也保存
                finish();
                break;
            case R.id.edit:
                isUpdate = true;
                changeState(isUpdate);
                invalidateOptionsMenu();
                break;
            case R.id.delete:
                isDelete();
                break;
            case R.id.save:
                save();
                isUpdate = false;
                changeState(isUpdate);
                invalidateOptionsMenu();
                Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void changeState(boolean isUpdate) {
        // TODO: 2016/12/18 通知状态变化
        for (MyView myView : contents) {
            myView.setBtnVisibility(isUpdate);
        }
        list_detail.setBtnVisibility(isUpdate);
        list_prize.setBtnVisibility(isUpdate);
        list_score.setBtnVisibility(isUpdate);
    }

    private void isDelete() {
        new AlertDialog.Builder(this).setTitle("确认删除?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BeanUtil.deleteByNO_id(mStuInfo.getNO_id());

                        mStuInfo.delete();
                        mStuInfo = null;
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 2016/12/18 清空集合
        tempAdd.clear();
        tempDelete.clear();
        contents.clear();
        contents = null;
        tempAdd = null;
        tempDelete = null;
    }
}
