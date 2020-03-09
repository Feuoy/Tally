package com.feuoy.tally.util;

import android.content.Context;

import com.feuoy.tally.R;
import com.feuoy.tally.activity.MainActivity;
import com.feuoy.tally.bean.CategoryResBean;
import com.feuoy.tally.db.DBHelper;

import java.util.LinkedList;


public class GlobalUtil {


    // mainActivity实例
    public MainActivity mainActivity;

    private static GlobalUtil instance;

    public DBHelper dbHelper;

    private Context context;

    // 收支资源链表
    public LinkedList<CategoryResBean> costRes = new LinkedList<>();
    public LinkedList<CategoryResBean> earnRes = new LinkedList<>();


    // 支出分类名
    private static String[] costTitle = {
            "一般", "餐饮",
            "购物", "交通",
            "娱乐", "居家",
            "通讯", "零食",
            "数码", "医疗",
            "书籍", "住房",
            "礼金", "理财",
    };

    // 支出图标资源，黑色
    private static int[] costIconResBlack = {
            R.drawable.icon_cost_general_black,
            R.drawable.icon_cost_food_black,
            R.drawable.icon_cost_shopping_black,
            R.drawable.icon_cost_transportation_black,
            R.drawable.icon_cost_amusement_black,
            R.drawable.icon_cost_groceries_black,
            R.drawable.icon_cost_phone_black,
            R.drawable.icon_cost_snack_black,
            R.drawable.icon_cost_3c_black,
            R.drawable.icon_cost_medical_black,
            R.drawable.icon_cost_book_black,
            R.drawable.icon_cost_housing_black,
            R.drawable.icon_cost_giftcash_black,
            R.drawable.icon_cost_financing_black,
    };

    // 支出图标资源名，白色
    private static int[] costIconResWhite = {
            R.drawable.icon_cost_general_white,
            R.drawable.icon_cost_food_white,
            R.drawable.icon_cost_shopping_white,
            R.drawable.icon_cost_transportation_white,
            R.drawable.icon_cost_amusement_white,
            R.drawable.icon_cost_groceries_white,
            R.drawable.icon_cost_phone_white,
            R.drawable.icon_cost_snack_white,
            R.drawable.icon_cost_3c_white,
            R.drawable.icon_cost_medical_white,
            R.drawable.icon_cost_book_white,
            R.drawable.icon_cost_housing_white,
            R.drawable.icon_cost_giftcash_white,
            R.drawable.icon_cost_financing_white,
    };


    // 收入分类名
    private static String[] earnTitle = {
            "工资", "副业",
            "理财", "礼金",
            "其它",
    };


    // 收入图标资源，黑色
    private static int[] earnIconResBlack = {
            R.drawable.icon_income_salary_black,
            R.drawable.icon_income_parttime_black,
            R.drawable.icon_income_financing_black,
            R.drawable.icon_income_giftcash_black,
            R.drawable.icon_income_other_black,
    };

    // 收入图标资源，白色
    private static int[] earnIconResWhite = {
            R.drawable.icon_income_salary_white,
            R.drawable.icon_income_parttime_white,
            R.drawable.icon_income_financing_white,
            R.drawable.icon_income_giftcash_white,
            R.drawable.icon_income_other_white,
    };


    public int getResourceIcon(String category) {
        for (CategoryResBean res : costRes) {
            if (res.title.equals(category)) {
                return res.resWhite;
            }
        }

        for (CategoryResBean res : earnRes) {
            if (res.title.equals(category)) {
                return res.resWhite;
            }
        }

        return costRes.get(0).resWhite;
    }


    public void setContext(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context, DBHelper.DB_NAME, null, 1);

        for (int i = 0; i < costTitle.length; i++) {
            // 保证只赋值一次
            if (costRes.size() < costIconResWhite.length) {
                costRes.add(new CategoryResBean(costTitle[i], costIconResBlack[i], costIconResWhite[i]));
            }
        }

        for (int i = 0; i < earnTitle.length; i++) {
            if (earnRes.size() < earnIconResWhite.length) {
                earnRes.add(new CategoryResBean(earnTitle[i], earnIconResBlack[i], earnIconResWhite[i]));
            }
        }
    }


    public Context getContext() {
        return context;
    }


    private GlobalUtil (){}


    public static GlobalUtil getInstance() {
        if (instance == null) {
            instance = new GlobalUtil();
        }
        return instance;
    }


}