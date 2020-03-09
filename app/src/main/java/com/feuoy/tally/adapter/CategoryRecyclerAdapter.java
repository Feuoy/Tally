package com.feuoy.tally.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.feuoy.tally.R;
import com.feuoy.tally.bean.CategoryResBean;
import com.feuoy.tally.bean.RecordBean;
import com.feuoy.tally.util.GlobalUtil;

import java.util.LinkedList;


// 做添加界面分类图标的Adapter
public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    // 点击监听接口，联系adapter和activity
    public interface OnCategoryClickListener {
        void onClick(String category);
    }

    public OnCategoryClickListener onCategoryClickListener;

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }


    private Context mContext;

    private LayoutInflater mInflater;

    // 图标资源链表，初始化costRes
    private LinkedList<CategoryResBean> cellList = GlobalUtil.getInstance().costRes;

    // 被选的分类名
    private String selected = "";


    public CategoryRecyclerAdapter(Context context) {
        GlobalUtil.getInstance().setContext(context);

        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);

        selected = cellList.get(0).title;
    }


    public String getSelected() {
        return selected;
    }


    public void setSelected(String selected) {
        this.selected = selected;
    }


    // 更改收支类型
    public void changeType(RecordBean.RecordType type) {
        if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
            cellList = GlobalUtil.getInstance().costRes;
        } else {
            cellList = GlobalUtil.getInstance().earnRes;
        }

        selected = cellList.get(0).title;

        notifyDataSetChanged();
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_category, parent, false);

        CategoryViewHolder myViewHolder = new CategoryViewHolder(view);

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        // 先通过索引找res
        final CategoryResBean res = cellList.get(position);

        // 给holder对应res内容
        holder.imageView.setImageResource(res.resBlack);
        holder.textView.setText(res.title);

        // holder listener
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 赋值selected
                selected = res.title;

                notifyDataSetChanged();

                // imageView 被点击，触发相应 onCategoryClickListener 监听
                if (onCategoryClickListener != null) {
                    onCategoryClickListener.onClick(res.title);
                }
            }
        });

        // 给holder不同背景颜色
        if (holder.textView.getText().toString().equals(selected)) {
            holder.background.setBackgroundResource(R.drawable.bg_bule);
        } else {
            holder.background.setBackgroundResource(R.color.colorPrimary);
        }
    }


    @Override
    public int getItemCount() {
        return cellList.size();
    }

}


// Holder
class CategoryViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout background;
    TextView textView;
    ImageView imageView;


    public CategoryViewHolder(View itemView) {
        super(itemView);

        background = itemView.findViewById(R.id.cell_background);
        imageView = itemView.findViewById(R.id.imageView_category);
        textView = itemView.findViewById(R.id.textView_category);
    }

}
