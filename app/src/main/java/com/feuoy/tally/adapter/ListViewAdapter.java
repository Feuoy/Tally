package com.feuoy.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feuoy.tally.R;
import com.feuoy.tally.bean.RecordBean;
import com.feuoy.tally.util.DateUtil;
import com.feuoy.tally.util.GlobalUtil;

import java.util.LinkedList;


// 做查阅界面记录列表的Adapter
public class ListViewAdapter extends BaseAdapter {


    private LinkedList<RecordBean> records = new LinkedList<>();

    private LayoutInflater mInflater;

    private Context mContext;


    public ListViewAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    public void setData(LinkedList<RecordBean> records) {
        this.records = records;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewHolder holder;

        // 添加数据
        if (convertView == null) {

            // 构造cell_list_view
            convertView = mInflater.inflate(R.layout.cell_list_view, null);

            // 由索引从records找到record
            RecordBean recordBean = (RecordBean) getItem(position);

            // put record into convertView for holder
            holder = new ListViewHolder(convertView, recordBean);

            // this convertView, setTag holder
            convertView.setTag(holder);

        } else {
            holder = (ListViewHolder) convertView.getTag();
        }

        notifyDataSetChanged();

        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return records.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getCount() {
        return records.size();
    }

}


// Holder
class ListViewHolder {

    TextView amountTV;
    ImageView categoryIcon;
    TextView remarkTV;
    TextView timeStampTV;


    public ListViewHolder(View itemView, RecordBean record) {

        amountTV = itemView.findViewById(R.id.textView_amount);
        categoryIcon = itemView.findViewById(R.id.imageView_category);
        remarkTV = itemView.findViewById(R.id.textView_remark);
        timeStampTV = itemView.findViewById(R.id.textView_time);

        // 金额
        if (record.getType() == 1) {
            amountTV.setText("- " + record.getAmount());
        } else {
            amountTV.setText("+ " + record.getAmount());
        }

        // 图标
        categoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(record.getCategory()));
        // 图标背景
        if (record.getType() == 1) {
            categoryIcon.setBackgroundResource(R.drawable.circle_cost);
        } else {
            categoryIcon.setBackgroundResource(R.drawable.circle_income);
        }

        // 备注
        remarkTV.setText(record.getRemark());

        // 时间戳
        timeStampTV.setText(DateUtil.timeStampLongToHourMinute(record.getTimeStamp()));
    }

}
