package com.yc.wsjt.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.RoleInfo;
import com.yc.wsjt.ui.adapter.RoleListAdapter;

import java.util.ArrayList;
import java.util.List;


public class RoleSelectDialog extends Dialog {

    private Context mContext;

    RecyclerView roleListView;

    RoleListAdapter roleListAdapter;

    private List<RoleInfo> list = new ArrayList<>();

    public interface ChooseRoleListener {
        void chooseRole(int pos, String name, String head, int localHead);
    }

    public ChooseRoleListener chooseRoleListener;

    public void setChooseRoleListener(ChooseRoleListener chooseRoleListener) {
        this.chooseRoleListener = chooseRoleListener;
    }

    public RoleSelectDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public RoleSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;

        RoleInfo myRole = new RoleInfo();
        myRole.setNickname(App.getApp().mUserInfo != null ? App.getApp().mUserInfo.getNickName(): "未知用户");
        myRole.setAvatar(App.getApp().mUserInfo != null ? App.getApp().mUserInfo.getFace() : "");
        list.add(myRole);

        if (App.getApp().qunChatInfo != null && App.getApp().qunChatInfo.getRoleList() != null) {
            if (list != null) {
                list.addAll(App.getApp().qunChatInfo.getRoleList());
            }else{
                list = App.getApp().qunChatInfo.getRoleList();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_select_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        roleListView = findViewById(R.id.role_list);
        roleListAdapter = new RoleListAdapter(mContext, list);
        roleListView.addItemDecoration(new NormalDecoration(ContextCompat.getColor(mContext, R.color.line_color), SizeUtils.dp2px(0.5f)));
        roleListView.setLayoutManager(new LinearLayoutManager(mContext));
        roleListView.setAdapter(roleListAdapter);

        roleListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                chooseRoleListener.chooseRole(position, roleListAdapter.getData().get(position).getNickname(), roleListAdapter.getData().get(position).getAvatar(), roleListAdapter.getData().get(position).getAvatarLocalImg());
                dismiss();
            }
        });
    }
}