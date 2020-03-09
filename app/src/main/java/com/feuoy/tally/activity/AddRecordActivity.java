package com.feuoy.tally.activity;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feuoy.tally.R;
import com.feuoy.tally.adapter.CategoryRecyclerAdapter;
import com.feuoy.tally.bean.RecordBean;
import com.feuoy.tally.util.DateUtil;
import com.feuoy.tally.util.GlobalUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;

import static com.feuoy.tally.R.color.mdtp_accent_color;
import static com.feuoy.tally.util.DateUtil.dateStrToYearMonthDay;


// 实现图像按钮、分类图标点击监听、DatePickerDialog日期选择器
public class AddRecordActivity extends AppCompatActivity
        implements View.OnClickListener,
        CategoryRecyclerAdapter.OnCategoryClickListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {


    private static String TAG = "AddRecordActivity";


    // 金额
    private TextView amountText;
    // 备注
    private EditText remarkText;
    // 收支类型
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
    // 备注
    private String remark = "";
    // 日期
    private String date = DateUtil.getNowYearMonthDay();


    // 用户输入金额
    private String userInput = "";

    // 今日，日期选择器用
    Calendar nowDayOrEditDay = Calendar.getInstance();


    // 分类名称
    private String category = "一般";
    // 分类图标recyclerView
    private RecyclerView recyclerView;
    // 分类图标adapter
    private CategoryRecyclerAdapter adapter;


    RecordBean record = new RecordBean();

    // 是否从编辑记录进来
    private boolean isEdit = false;


    // 首次创建
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        getSupportActionBar().setElevation(0);


        amountText = (TextView) findViewById(R.id.textView_amount);
        remarkText = (EditText) findViewById(R.id.editText);
        remarkText.setText(remark);

        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);

        handleCalendar();
        handleDot();
        handleBackspace();
        handleTypeChange();
        handleDone();


        // recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        // adapter
        adapter = new CategoryRecyclerAdapter(this);
        // set adapter for recyclerView
        recyclerView.setAdapter(adapter);

        // gridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        // set GridLayoutManager for recyclerView
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter.notifyDataSetChanged();
        // Listener
        adapter.setOnCategoryClickListener(this);


        // 处理编辑记录进来
        doEdit();
    }


    // 处理编辑记录进来，复现原数据
    public void doEdit() {

        // 从Intent拿相应record
        RecordBean record = (RecordBean) getIntent().getSerializableExtra("record");

        if (record != null) {

            isEdit = true;
            this.record = record;

            // 金额
            userInput = String.valueOf(record.getAmount());
            // 金额显示
            amountText.setText(userInput);

            // 如果没有点日历，用原来的日历记录
            date = record.getDate();
            // 如果要点击日历，初始化nowDayOrEditDay为原来的日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateStrToYearMonthDay(date));
            nowDayOrEditDay = calendar;


            // 备注、备注显示，统一这里
            remarkText.setText(record.getRemark());

            // 收支类型、收支类型显示，统一这里
            ImageButton button = findViewById(R.id.keyboard_type);
            if (record.getType() == 1) {
                type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                button.setImageResource(R.drawable.cost);
            } else {
                type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                button.setImageResource(R.drawable.income);
            }
            adapter.changeType(type);

            // 分类、分类显示，统一这里
            adapter.setSelected(record.getCategory());
            category = adapter.getSelected();
            adapter.notifyDataSetChanged();
            adapter.onCategoryClickListener.onClick(category);
        }
    }


    // 刷新金额，根据用户输入改变金额显示
    private void updateAmountText() {

        // 如果含有小数点
        if (userInput.contains(".")) {

            // “1000.”————补充.00
            if (userInput.split("\\.").length == 1) {
                amountText.setText(userInput + "00");
            }

            // “1000.3”————补充.0
            else if (userInput.split("\\.")[1].length() == 1) {
                amountText.setText(userInput + "0");
            }

            // “1000.30”————正常
            else if (userInput.split("\\.")[1].length() == 2) {
                amountText.setText(userInput);
            }

        } else {
            // 如果为空
            if (userInput.equals("")) {
                amountText.setText("0.00");
            }

            // 整数补充.00
            else {
                amountText.setText(userInput + ".00");
            }
        }
    }


    // 点击某分类
    @Override
    public void onClick(String category) {
        this.category = category;
    }


    // 点击某数字
    @Override
    public void onClick(View v) {

        // 实际输入值
        String input;

        switch (v.getId()) {
            case R.id.keyboard_one:
                input = "1";
                break;
            case R.id.keyboard_two:
                input = "2";
                break;
            case R.id.keyboard_three:
                input = "3";
                break;
            case R.id.keyboard_four:
                input = "4";
                break;
            case R.id.keyboard_five:
                input = "5";
                break;
            case R.id.keyboard_six:
                input = "6";
                break;
            case R.id.keyboard_seven:
                input = "7";
                break;
            case R.id.keyboard_eight:
                input = "8";
                break;
            case R.id.keyboard_nine:
                input = "9";
                break;
            case R.id.keyboard_zero:
                input = "0";
                break;
            default:
                input = "";
        }


        // 有效输入值
        // 如果键入金额原来含有小数点
        if (userInput.contains(".")) {
            // 只处理“1000”或“1000.12”
            if (
                    userInput.split("\\.").length == 1
                            || userInput.split("\\.")[1].length() < 2
            ) {
                userInput += input;
            }
        } else {
            userInput += input;
        }


        // 刷新金额
        updateAmountText();
    }


    // 小数点
    private void handleDot() {
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一开始不能加小数点
                if (userInput.length() == 0) {
                    userInput += "";
                } else {
                    // 如果含有小数点不能再加
                    if (!userInput.contains(".")) {
                        userInput += ".";
                    }
                }

            }
        });
    }


    // 退格
    private void handleBackspace() {
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 输入长度大于0
                if (userInput.length() > 0) {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }

                // 上面删除完输入长度依然大于0，判断最后是否是小数点，是的话再退掉小数点
                if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.') {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }

                // 刷新金额
                updateAmountText();
            }
        });
    }


    // 收支类型
    private void handleTypeChange() {
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton button = findViewById(R.id.keyboard_type);

                if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
                    // 换类型
                    type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                    // 换图标
                    button.setImageResource(R.drawable.income);
                } else {
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                    button.setImageResource(R.drawable.cost);
                }

                adapter.changeType(type);
                category = adapter.getSelected();
                adapter.notifyDataSetChanged();
            }
        });
    }


    // 完成
    private void handleDone() {
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 金额不能为空
                if (!userInput.equals("")) {

                    // set record
                    if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
                        record.setType(1);
                    } else {
                        record.setType(2);
                    }
                    double amount = Double.valueOf(userInput);
                    record.setAmount(amount);
                    record.setCategory(adapter.getSelected());
                    record.setRemark(
                            remarkText.getText().toString().equals("") ?
                                    adapter.getSelected()
                                    : remarkText.getText().toString()
                    );
                    record.setDate(date);


                    // 如果不是从编辑记录进来，添加记录
                    if (!isEdit) {
                        GlobalUtil.getInstance().dbHelper.insertRecord(record);
                    }
                    // 如果从编辑记录进来，编辑记录
                    else {
                        GlobalUtil.getInstance().dbHelper.updateRecord(record.getUuid(), record);
                    }


                    // 直接finish掉，栈底有一个新的mainactivity
                    finish();


                } else {
                    Toast.makeText(getApplicationContext(), " 金额不可为空 ~ ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // 日历
    private void handleCalendar() {
        findViewById(R.id.calendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<String> validDates = GlobalUtil.getInstance().dbHelper.getValidDate();

                Calendar[] validDays = DateUtil.datesToDays(validDates);

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        AddRecordActivity.this,
                        nowDayOrEditDay.get(Calendar.YEAR), // Initial year selection
                        nowDayOrEditDay.get(Calendar.MONTH), // Initial month selection
                        nowDayOrEditDay.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );

                dpd.setHighlightedDays(validDays);

                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.setAccentColor(getResources().getColor(mdtp_accent_color));

                // If you're calling this from an AppCompatActivity
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
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

        date = year_ok + "-" + month_ok + "-" + day_ok;

//        Log.d("onDateSet--", year_ok + "-" + month_ok + "-" + day_ok);
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
