package com.joking.infosystem.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joking.infosystem.App;
import com.joking.infosystem.R;
import com.joking.infosystem.bean.StuBase;
import com.joking.infosystem.widget.MyView;

import java.lang.reflect.Field;

/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class ViewUtil {
    public static MyView createMyView(Context context, StuBase stuBase, int type) {
        MyView myView = new MyView(context);
        myView.setText(stuBase.getContent());
        myView.setType(type);
        myView.setId(stuBase.getId());
        return myView;
    }

    public static Snackbar createSnackbar(View view, String text, int duration, int colorId) {
        // 创建实例
        Snackbar snackbar = Snackbar.make(view, text, duration);

        // 设置动作按钮颜色
        int color = view.getContext().getResources().getColor(colorId);
        snackbar.setActionTextColor(color);

        // 获取 snackbar 视图
        View snackbarView = snackbar.getView();

        // 改变 snackbar 文本颜色
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
        textView.setTextColor(color);

        // 改变 snackbar 背景
        snackbarView.setBackgroundColor(view.getContext().getResources().getColor(R.color.mask));

        return snackbar;
    }

    public static AlertDialog createDialog(Context context,
                                           String title,
                                           View view,
                                           DialogInterface.OnClickListener ensure) {

        //        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        alertDialog.addContentView(view, lp);

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton("确定", ensure)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(App.getContext(), "取消", Toast.LENGTH_SHORT).show();
                        setCancelable(dialog, true);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(App.getContext(), "取消", Toast.LENGTH_SHORT).show();
                        setCancelable(dialog, true);
                    }
                })
                .setView(view)
                .create();
    }

    public static void setCancelable(DialogInterface dialog, boolean cancelable) {
        try {
            Field field = dialog.getClass()
                    .getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, cancelable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
