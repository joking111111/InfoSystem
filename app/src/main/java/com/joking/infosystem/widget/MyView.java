package com.joking.infosystem.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joking.infosystem.R;


/**
 * 功能简述：
 * 功能详细描述：
 *
 * @author JoKing
 */

public class MyView extends RelativeLayout {

    private static final String TAG = "MyView";

    private static final String SYSTEM = "http://schemas.android.com/apk/res/android";
    private static final String USER = "http://schemas.android.com/apk/res-auto";

    private ImageView btn;
    private TextView text;
    private int type = -1;
    private int id = -1;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.myview, this);
        btn = (ImageView) findViewById(R.id.btn);
        text = (TextView) findViewById(R.id.text);

        if (null != attrs) {
            int layout_width = attrs.getAttributeIntValue(SYSTEM, "layout_width",
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int layout_height = attrs.getAttributeIntValue(SYSTEM, "layout_height",
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(layout_width, layout_height);
            setLayoutParams(lp);

            String content = attrs.getAttributeValue(SYSTEM, "text");
            setText(content);

            boolean is_title = attrs.getAttributeBooleanValue(USER, "isTitle", false);
            isTitle(is_title);

            boolean btn_visibility = attrs.getAttributeBooleanValue(USER, "btn_visibility", true);
            setBtnVisibility(btn_visibility);

            // DEBUG
//            int attributeCount = attrs.getAttributeCount();
//            Log.i(TAG, "attributeCount=" + attributeCount);
//            for (int i = 0; i < attributeCount; i++) {
//                String attributeName = attrs.getAttributeName(i);
//                String attributeValue = attrs.getAttributeValue(i);
//
//                Log.i(TAG, "MyView: " + attributeName + "=" + attributeValue);
//            }
        }
    }

    public void recyle() {
        // TODO: 2016/12/18 回收，直接置空不知道可行不
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setText(String content) {
        text.setText(content);
    }

    public String getText() {
        return text.getText().toString();
    }

    public void isTitle(boolean is_title) {
        if (is_title) {
            text.setTextAppearance(getContext(), R.style.TitleStyle);
            btn.setImageResource(android.R.drawable.ic_input_add);
//            Glide.with(getContext()).load(android.R.drawable.ic_input_add).into(btn);// 出问题了
        } else {
            text.setTextAppearance(getContext(), R.style.DetailStyle);
            btn.setImageResource(android.R.drawable.ic_delete);
//            Glide.with(getContext()).load(android.R.drawable.ic_delete).into(btn);
        }
//        text.setTextSize(getResources().getDimension(R.dimen.title_size));// 实际不是20sp
//        text.setTextSize(20);
//        text.setTextColor(getResources().getColor(R.color.black));
    }

    public void setBtnVisibility(boolean isVisible) {
        if (isVisible) {
            btn.setVisibility(VISIBLE);
        } else {
            btn.setVisibility(INVISIBLE);
        }
    }

    public void setBtnOnClickListener(OnClickListener listener) {
        if (null != listener) {
            btn.setOnClickListener(listener);
        }
    }
}
