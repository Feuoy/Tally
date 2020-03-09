package com.feuoy.tally.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feuoy.tally.R;
import com.feuoy.tally.adapter.ListViewAdapter;
import com.feuoy.tally.bean.RecordBean;
import com.feuoy.tally.util.DateUtil;
import com.feuoy.tally.util.GlobalUtil;

import java.util.LinkedList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


// 查阅界面下部分
public class MainFragment extends Fragment implements AdapterView.OnItemLongClickListener {


    private View fragmentView;

    private Context mContext;


    private TextView dayTextView;

    private ListView recordListView;

    private ListViewAdapter listViewAdapter;


    private LinkedList<RecordBean> records = new LinkedList<>();

    private String date = "";


    public MainFragment() {}


    @SuppressLint("ValidFragment")
    public MainFragment(String date, Context context) {
        this.date = date;
        this.mContext = context;

        records = GlobalUtil.getInstance().dbHelper.getRecordsByDate(date);
    }


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // 从 R.layout.fragment_main 构造 fragmentView
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        // 然后初始化 fragmentView
        initView(mContext);

        return fragmentView;
    }


    // 初始化View
    private void initView(Context context) {

        this.mContext = context;


        dayTextView = (TextView) fragmentView.findViewById(R.id.day_text);
        recordListView = (ListView) fragmentView.findViewById(R.id.listView);


        dayTextView.setText(DateUtil.dateStrToChineseYearMonthDay(date));

        listViewAdapter = new ListViewAdapter(mContext);
        listViewAdapter.setData(records);
        recordListView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();

        // Listener
        recordListView.setOnItemLongClickListener(this);
    }


    // 重新加载fragment
    public void reloadMainFragment(Context context) {

        this.mContext = context;

        records = GlobalUtil.getInstance().dbHelper.getRecordsByDate(date);


        listViewAdapter.setData(records);

        Activity activity = (Activity) mContext;
        recordListView = activity.findViewById(R.id.listView);

        recordListView.setAdapter(listViewAdapter);
    }


    // 当日总支出
    public int getTotalCost() {
        double totalCost = 0;
        for (RecordBean record : records) {
            if (record.getType() == 1) {
                totalCost += record.getAmount();
            }
        }
        return (int) totalCost;
    }


    // recordListView长按监听
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(position);
        return false;
    }


    // recordListView的弹窗
    private void showDialog(int index) {

        final String[] options = {" 编辑 ~ ", " 删除 ~ "};

        // 被选的记录
        final RecordBean selectedRecord = records.get(index);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.create();

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // 编辑按钮
                if (which == 0) {

                    // 和添加记录activity处理一样，先开启一个新的mainactivity，再开启一个addRecordactivity，最后销毁原来的mainactivity

                    MainActivity ma = new MainActivity();
                    Intent intent_ma = new Intent(GlobalUtil.getInstance().mainActivity, ma.getClass());
                    startActivity(intent_ma);

                    Intent intent = new Intent(getActivity(), AddRecordActivity.class);
                    Bundle extra = new Bundle();
                    extra.putSerializable("record", selectedRecord);
                    intent.putExtras(extra);
                    startActivity(intent);

                    GlobalUtil.getInstance().mainActivity.finish();

                    // 删除按钮
                } else if (which == 1) {

                    String uuid = selectedRecord.getUuid();
                    GlobalUtil.getInstance().dbHelper.deleteRecord(uuid);

                    // 刷新fragment
                    reloadMainFragment(mContext);

                    // 刷新上部分
                    GlobalUtil.getInstance().mainActivity.updateHeader();
                }
            }
        });

        // 不要弹窗按钮
        builder.setNegativeButton("", null);

        builder.create().show();
    }

}
