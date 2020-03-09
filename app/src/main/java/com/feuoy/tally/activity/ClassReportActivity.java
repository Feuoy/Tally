package com.feuoy.tally.activity;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.feuoy.tally.R;

import com.feuoy.tally.bean.ClassReportBean;
import com.feuoy.tally.bean.RecordBean;
import com.feuoy.tally.util.GlobalUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class ClassReportActivity extends AppCompatActivity implements OnChartValueSelectedListener {


    private final String TAG = "ClassReportActivity";


    Calendar now = Calendar.getInstance();
    String nowYear = String.valueOf(now.get(Calendar.YEAR));
    String nowMonth = String.valueOf(now.get(Calendar.MONTH) + 1).length() == 1 ?
            ("0" + (now.get(Calendar.MONTH) + 1))
            : String.valueOf(now.get(Calendar.MONTH) + 1);
    String yearMonth = nowYear + "-" + nowMonth;


    PieChart pieChart;

    List<PieEntry> entries = new ArrayList<>();


    int[] colors = new int[]{
            Color.argb(200, 244, 67, 54),
            Color.argb(200, 156, 39, 176),
            Color.argb(200, 103, 58, 183),
            Color.argb(200, 63, 81, 181),

            Color.argb(200, 33, 150, 243),
            Color.argb(200, 3, 169, 244),
            Color.argb(200, 0, 188, 212),
            Color.argb(200, 0, 150, 136),

            Color.argb(200, 139, 195, 74),
            Color.argb(200, 205, 220, 57),
            Color.argb(200, 255, 235, 59),
            Color.argb(200, 255, 193, 7),

            Color.argb(200, 255, 152, 0),
            Color.argb(200, 255, 87, 34),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_report);


        pieChart = (PieChart) findViewById(R.id.pichart);


        // 设置中间圆盘的颜色
        pieChart.setHoleColor(Color.rgb(241, 241, 201));
        // 是否使用百分比
        pieChart.setUsePercentValues(false);
        // 设置动画
        pieChart.animateXY(1500, 1500);
        pieChart.spin(2000, 0, 120, null);
        // 设置左下方描述是否可见
        pieChart.getLegend().setEnabled(false);
        // undo all highlights
        pieChart.highlightValues(null);


        // 数据
        List<ClassReportBean> theClassReport = getClassReport(yearMonth);
        for (int i = 0; i < theClassReport.size(); i++) {
            entries.add(new PieEntry((float) theClassReport.get(i).getAmount(), theClassReport.get(i).getCategory()));
        }


        // set
        PieDataSet set = new PieDataSet(entries, "");
        // 给每个区块的颜色
        set.setColors(colors);
        // 间隔
        set.setSliceSpace(2f);


        // data
        PieData data = new PieData(set);
        // value字体大小
        data.setValueTextSize(20);
        data.setValueTextColor(Color.rgb(255, 255, 255));


        // pieChart
        pieChart.setData(data);
        // 是否显示圆盘中间文字
        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextSize(20);
        pieChart.setCenterTextColor(Color.rgb(3, 169, 244));
        pieChart.setCenterTextTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        pieChart.setCenterText("总支出" + "\n\n" + getTotalCost(yearMonth));


        // 描述信息
        Description description = new Description();
        description.setText(yearMonth.substring(0, 4) + "年" + yearMonth.substring(5, 7) + "月" + " · 分类支出");
        description.setTextAlign(Paint.Align.RIGHT);
        description.setTextSize(20);
        description.setTextColor(Color.rgb(3, 169, 244));
        pieChart.setDescription(description);


        // refresh
        pieChart.invalidate();


        // 更换年月
        handleDoneClassReport();
    }


    // 获取每个ClassReportBean
    private List<ClassReportBean> getClassReport(String year_month) {


        // 列出所有分类名的classReports，初始化年月，金额
        List<ClassReportBean> classReports = new ArrayList<>();

        String[] costTitle = {
                "一般", "餐饮",
                "购物", "交通",
                "娱乐", "居家",
                "通讯", "零食",
                "数码", "医疗",
                "书籍", "住房",
                "礼金", "理财",
        };

        for (String title : costTitle) {
            classReports.add(new ClassReportBean(year_month, title, 0));
        }


        // 遍历reconds
        LinkedList<RecordBean> records = GlobalUtil.getInstance().dbHelper.getRecordsByType(1);

        for (int i = 0; i < records.size(); i++) {

            String record_year_month = records.get(i).getDate().substring(0, 7); // 2019-08

            // 筛选年月
            if (!record_year_month.equals(year_month)) {
                continue;
            }

            // 将分类名相同的，加金额
            for (int j = 0; j < classReports.size(); j++) {
                if (records.get(i).getCategory().equals(classReports.get(j).getCategory())) {
                    classReports.get(j).setAmount(classReports.get(j).getAmount() + records.get(i).getAmount());
                }
            }
        }


        // 新建一个classReports，将金额为0的删除
        List<ClassReportBean> classReports_ture = new ArrayList<>();

        for (ClassReportBean cr : classReports) {
            if (cr.getAmount() != 0) {
                classReports_ture.add(cr);
            }
        }


        return classReports_ture;
    }


    // 获取当月总支出
    private double getTotalCost(String year_month) {

        double totalCost = 0;

        LinkedList<RecordBean> records = GlobalUtil.getInstance().dbHelper.getRecordsByType(1);

        for (int i = 0; i < records.size(); i++) {

            String record_year_month = records.get(i).getDate().substring(0, 7); // 2019-08

            // 筛选年月
            if (!record_year_month.equals(year_month)) {
                continue;
            }

            // 总金额
            totalCost += records.get(i).getAmount();
        }

        return totalCost;
    }


    // 查询，按年月
    private void handleDoneClassReport() {
        findViewById(R.id.done_to_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        ClassReportActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {

                                // 处理月份
                                String selectedMonth_true = String.valueOf(selectedMonth + 1).length() == 1 ?
                                        ("0" + (selectedMonth + 1))
                                        : String.valueOf(selectedMonth + 1);

                                yearMonth = selectedYear + "-" + selectedMonth_true;

                                List<ClassReportBean> theClassReports = getClassReport(yearMonth);

                                if (theClassReports.size() != 0) {

                                    // 更新图表
                                    updateChart(theClassReports);

                                } else {
                                    Toast.makeText(getApplicationContext(), " 查询的月份还没有记录哦 ~ ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH));


                builder.setTitle("")
                        .setMinYear(2000)
                        .setMaxYear(2030)
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                                // on month selected
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {
                                // on year selected
                            }
                        })
                        .build()
                        .show();
            }
        });
    }


    // 查询确定，刷新表
    public void updateChart(List<ClassReportBean> theClassReport) {

        entries = new ArrayList<>();

        for (int i = 0; i < theClassReport.size(); i++) {
            entries.add(new PieEntry((float) theClassReport.get(i).getAmount(), theClassReport.get(i).getCategory()));
        }

        // set
        PieDataSet set = new PieDataSet(entries, "");
        // 给每个区块的颜色
        set.setColors(colors);
        // 间隔
        set.setSliceSpace(2f);


        // data
        PieData data = new PieData(set);
        // value字体大小
        data.setValueTextSize(20);
        data.setValueTextColor(Color.rgb(255, 255, 255));


        // pieChart
        pieChart.setData(data);
        pieChart.setCenterText("总支出" + "\n\n" + getTotalCost(yearMonth));


        // 描述信息
        Description description = new Description();
        description.setText(yearMonth.substring(0, 4) + "年" + yearMonth.substring(5, 7) + "月" + " · 分类支出");
        description.setTextAlign(Paint.Align.RIGHT);
        description.setTextSize(20);
        description.setTextColor(Color.rgb(3, 169, 244));
        pieChart.setDescription(description);


        // 设置动画
        pieChart.animateXY(1500, 1500);
        pieChart.spin(2000, 0, 120, null);


        // refresh
        pieChart.invalidate();
    }


    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e The selected Entry
     * @param h The corresponding highlight object that contains information
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }


    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    @Override
    public void onNothingSelected() {

    }

}
