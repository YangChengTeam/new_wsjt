package com.yc.wsjt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.PhoneUtils;
import com.jaeger.library.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.yc.wsjt.App;
import com.yc.wsjt.R;
import com.yc.wsjt.bean.ChatDataInfo;
import com.yc.wsjt.bean.ContactPerson;
import com.yc.wsjt.bean.RoleInfoRet;
import com.yc.wsjt.common.Constants;
import com.yc.wsjt.presenter.Presenter;
import com.yc.wsjt.presenter.RoleInfoPresenterImp;
import com.yc.wsjt.ui.adapter.ContactsAdapter;
import com.yc.wsjt.ui.custom.QuickIndexBar;
import com.yc.wsjt.util.Utils;
import com.yc.wsjt.view.RoleInfoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangdinghui on 2019/5/13.
 */
public class ChooseRoleActivity extends BaseActivity implements RoleInfoView {

    @BindView(R.id.tv_title)
    TextView mTitleTv;

    @BindView(R.id.address_expandable_listview)
    ExpandableListView mExpandableListView;

    @BindView(R.id.quick_index_bar)
    QuickIndexBar mQuickIndexBar;

    @BindView(R.id.text_dialog)
    TextView mTextDialog;

    ArrayList<ArrayList<ContactPerson>> contactPersonsList;

    private ArrayList<ContactPerson> personList;

    private String[] userNames = {"刀白凤", "丁春秋", "马夫人", "马五德", "小翠", "于光豪", "段延庆", "段誉", "段正淳", "范禹"};

    private int[] userHeads = {R.mipmap.c1, R.mipmap.c2, R.mipmap.c3, R.mipmap.c4, R.mipmap.c5, R.mipmap.c6, R.mipmap.c7, R.mipmap.c8, R.mipmap.c9, R.mipmap.c10};

    private int personType = 0;//0自己，1对方

    private RoleInfoPresenterImp roleInfoPresenterImp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_role;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initVars() {
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initViews() {
        mTitleTv.setText(getResources().getText(R.string.role_list_txt));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            personType = bundle.getInt("person_type", 0);
        }

        roleInfoPresenterImp = new RoleInfoPresenterImp(this, this);
        roleInfoPresenterImp.getRoleList();

        /**设置group不可点击*/
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//注意：一定要返回true，返回true这个方法才有效。
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int group, int child, long l) {
                //Logger.i(group + "---" + child + "---" + contactPersonsList.get(group).get(child).mName);
                try {
                    if (personType > 1) {
                        App.getApp().setTempPerson(contactPersonsList.get(group).get(child));
                    } else {
                        ChatDataInfo chatDataInfo = new ChatDataInfo();
                        chatDataInfo.setDeviceId(PhoneUtils.getDeviceId());

                        if (personType == 0) {
                            chatDataInfo.setPersonName(contactPersonsList.get(group).get(child).mName);
                            chatDataInfo.setPersonHead(contactPersonsList.get(group).get(child).mHead);
                        } else {
                            chatDataInfo.setOtherPersonName(contactPersonsList.get(group).get(child).mName);
                            chatDataInfo.setOtherPersonHead(contactPersonsList.get(group).get(child).mHead);
                        }

                        if (mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId(),0) != null) {
                            ChatDataInfo temp = mAppDatabase.chatDataInfoDao().getItemById(PhoneUtils.getDeviceId(),0);
                            Logger.i("已存在记录--->" + JSON.toJSONString(temp));
                            if (personType == 0) {
                                temp.setPersonName(chatDataInfo.getPersonName());
                                temp.setPersonHead(chatDataInfo.getPersonHead());
                            }

                            if (personType == 1) {
                                temp.setOtherPersonName(chatDataInfo.getOtherPersonName());
                                temp.setOtherPersonHead(chatDataInfo.getOtherPersonHead());
                            }

                            mAppDatabase.chatDataInfoDao().update(temp.getPersonName(), temp.getPersonHead(), temp.getOtherPersonName(), temp.getOtherPersonHead(), temp.getId());
                        } else {
                            //TODO,此处暂时不新增聊天资料设置
//                            chatDataInfo.setFriendType(0);
//                            chatDataInfo.setMessageDisturb(false);
//                            chatDataInfo.setReceiverOpen(false);
//                            chatDataInfo.setShowWeiXinMoney(true);//显示
//                            chatDataInfo.setFontChange(true);
//
//                            mAppDatabase.chatDataInfoDao().insert(chatDataInfo);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                finish();
                return false;
            }
        });

        setIndexBar();
    }

    /**
     * 设置setIndexBar
     */
    private void setIndexBar() {
        mQuickIndexBar.setTextView(mTextDialog);//设置textDialog
        mQuickIndexBar.setPaddingTop(15);//设置顶部padding
        mQuickIndexBar.setPaddingBottom(15);//设置底部padding

        /**设置监听器*/
        mQuickIndexBar.setOnLetterChangedListener(new QuickIndexBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                for (int i = 0; i < contactPersonsList.size(); i++) {
                    if (letter.equals(contactPersonsList.get(i).get(0).firstLetter)) {
                        mExpandableListView.setSelectedGroup(i);
                    }
                }
            }

            @Override
            public void onLetterGone() {
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(RoleInfoRet tData) {
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            if (tData.getData() != null) {
                //模拟测试数据
                personList = new ArrayList<>();
                for (int i = 0; i < tData.getData().size(); i++) {
                    ContactPerson contactPerson = new ContactPerson(tData.getData().get(i).getNickname());
                    contactPerson.setmHead(Constants.BASE_IMAGE_URL + tData.getData().get(i).getAvatar());
                    personList.add(contactPerson);
                }
                Logger.i("person list --->");
                Logger.i(JSON.toJSONString(personList));
                //对数据进行排序并转换成二维数组
                contactPersonsList = Utils.getSortDataList(personList);
                /**设置适配器*/
                mExpandableListView.setAdapter(new ContactsAdapter(this, contactPersonsList));

                /**展开所有条目*/
                for (int i = 0; i < contactPersonsList.size(); i++) {
                    mExpandableListView.expandGroup(i);
                }
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
