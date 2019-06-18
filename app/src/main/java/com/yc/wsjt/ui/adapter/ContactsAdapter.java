package com.yc.wsjt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ContactPerson;

import java.util.ArrayList;

import retrofit2.http.PUT;

/**
 * Created by fighting on 2017/5/17.
 */

public class ContactsAdapter extends BaseExpandableListAdapter {

    private final Context mContext;

    private final ArrayList<ArrayList<ContactPerson>> mDataList;

    public ContactsAdapter(Context context, ArrayList<ArrayList<ContactPerson>> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getGroupCount() {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.contacts_view_group, null);
            textView = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(textView);
        } else {
            textView = (TextView) convertView.getTag();
        }
        textView.setText(mDataList.get(groupPosition).get(0).firstLetter);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = null;
        ImageView mUserHeadIv = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.contacts_view_child, null);
            textView = convertView.findViewById(R.id.tv_user_name);
            mUserHeadIv = convertView.findViewById(R.id.iv_user_head);

            convertView.setTag(R.id.tv_user_name, textView);
            convertView.setTag(R.id.iv_user_head, mUserHeadIv);
        } else {
            textView = (TextView) convertView.getTag(R.id.tv_user_name);
            mUserHeadIv = (ImageView) convertView.getTag(R.id.iv_user_head);
        }
        textView.setText(mDataList.get(groupPosition).get(childPosition).mName);
        if (mDataList.get(groupPosition).get(childPosition) != null) {
            Glide.with(mContext).load(mDataList.get(groupPosition).get(childPosition).mHead).into(mUserHeadIv);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
