package com.feuoy.tally.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.feuoy.tally.R;

import com.feuoy.tally.adapter.MainViewPagerAdapter;
import com.feuoy.tally.util.DateUtil;
import com.feuoy.tally.util.GlobalUtil;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;

import static com.feuoy.tally.R.color.mdtp_accent_color;


public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {


    private static final String TAG = "MainActivity";


    // 上部的 TickerView 数字动效控件
    private TickerView amountText;
    // 上部的周几
    private TextView dateText;


    private ViewPager viewPager;

    private MainViewPagerAdapter mainViewPagerAdapter;


    // 当前页索引
    private int currentPagerPosition = 0;


    // 查阅日期
    private String readDate = DateUtil.getNowYearMonthDay();
    // 查阅索引
    int readDateIndex = 0;


    Context mContext;


    // 首次创建
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // new一个GlobalUtil实例，set这个mainActivity上下文给GlobalUtil实例
        GlobalUtil.getInstance().setContext(getApplicationContext());
        // 再把GlobalUtil实例的mainActivity，属性值为这个mainActivity
        GlobalUtil.getInstance().mainActivity = this;

        // 设置顶部栏z轴和下面一致，更好看
        getSupportActionBar().setElevation(0);

        amountText = (TickerView) findViewById(R.id.amount_text);
        amountText.setCharacterLists(TickerUtils.provideNumberList());

        dateText = (TextView) findViewById(R.id.date_text);


        viewPager = (ViewPager) findViewById(R.id.view_pager);

        this.mContext = MainActivity.this;
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mContext);
        mainViewPagerAdapter.notifyDataSetChanged();

        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.setOnPageChangeListener(this);

        viewPager.setCurrentItem(mainViewPagerAdapter.getLatsIndex());
        mainViewPagerAdapter.notifyDataSetChanged();


        // 冒泡按钮Listener
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        // 刷新上部
        updateHeader();
    }


    // 冒泡弹窗
    public void showDialog() {

        final String[] options = {"添加", "查阅", "类别报表", "收支曲线"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.create();

        builder.setItems(options, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 添加
                if (which == 0) {

                    // 打开AddRecordActivity
                    // 这里由于要销毁原来的mainactivity，但要点击返回的效果是mainactivity没有被销毁，新的mainactivity又不能明显跳转中出现，处理如下
                    // 栈里有一个现在的mainactivity，先开启一个新的mainactivity，再开启一个addRecordactivity，最后销毁原来的mainactivity

                    MainActivity ma = new MainActivity();
                    Intent intent_ma = new Intent(MainActivity.this, ma.getClass());
                    startActivity(intent_ma);

                    Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                    startActivity(intent);

                    finish();

                    // 查阅
                } else if (which == 1) {
                    LinkedList<String> dates = GlobalUtil.getInstance().dbHelper.getValidDate();
                    Calendar[] days = DateUtil.datesToDays(dates);
                    doCalendar(days);

                    // 类别报表
                } else if (which == 2) {
                    Intent intent = new Intent(MainActivity.this, ClassReportActivity.class);
                    startActivity(intent);

                    // 收支曲线
                } else if (which == 3) {
                    Intent intent = new Intent(MainActivity.this, CurveReportActivity.class);
                    startActivity(intent);
                }
            }
        });


        // 弹窗按钮
        builder.setNegativeButton("", null);


        // show Dialog
        builder.create().show();
    }


    // 刷新上部
    public void updateHeader() {
        // 当日支出总金额
        String amount = String.valueOf(mainViewPagerAdapter.getTotalCost(currentPagerPosition));
        amountText.setText(amount);

        // 当日周几
        String date = mainViewPagerAdapter.getDate(currentPagerPosition);
        dateText.setText(DateUtil.dateStrToWeekday(date));
    }


    // 当前落在某个下部Page
    @Override
    public void onPageSelected(int position) {
        currentPagerPosition = position;
        updateHeader();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


/*    // 暂时未用这种实现方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        // 更新Adapter
//        mainViewPagerAdapter.reloadMainViewPagerAdapter(mContext);
//        mainViewPagerAdapter.notifyDataSetChanged();

        // 刷新上部
        updateHeader();
    }*/


    // 日历
    private void doCalendar(Calendar[] days) {

        Calendar now = Calendar.getInstance();

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        dpd.setSelectableDays(days);

        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(getResources().getColor(mdtp_accent_color));

        // If you're calling this from an AppCompatActivity
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");

        // 随后去执行onDateSet()
    }


    /**
     * @param view        The view associated with this listener.
     * @param year        The year that was set.
     * @param monthOfYear The month that was set (0-11) for compatibility
     *                    with {@link Calendar}.
     * @param dayOfMonth  The day of the month that was set.
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        Log.d("onDateSet--", year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");

        String year_ok = String.valueOf(year);
        String month_ok = String.valueOf((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1));
        String day_ok = String.valueOf(dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);

        readDate = year_ok + "-" + month_ok + "-" + day_ok;

//        Log.d("onDateSet--", year_ok + "-" + month_ok + "-" + day_ok);

        readDateIndex = mainViewPagerAdapter.getIndexByDate(readDate);

//        Log.d("readDateIndex--", "" + readDateIndex);

        if (readDateIndex != -1) {
            viewPager.setCurrentItem(readDateIndex);
        } else {
            Toast.makeText(getApplicationContext(), " 这天还没有记录哦 ~ ", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Called when pointer capture is enabled or disabled for the current window.
     *
     * @param hasCapture True if the window has pointer capture.
     */
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}

