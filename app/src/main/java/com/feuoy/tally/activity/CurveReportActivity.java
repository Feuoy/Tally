package com.feuoy.tally.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.feuoy.tally.R;
import com.feuoy.tally.bean.CurveReportBean;
import com.feuoy.tally.bean.RecordBean;
import com.feuoy.tally.util.GlobalUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import static com.feuoy.tally.bean.CurveReportBean.RecordType.RECORD_TYPE_EXPENSE;
import static com.feuoy.tally.bean.CurveReportBean.RecordType.RECORD_TYPE_INCOME;


public class CurveReportActivity extends AppCompatActivity implements OnChartValueSelectedListener {


    private final String TAG = "CurveReportActivity";


    public LineChart lineChart;

    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴

    private Legend legend;              //图例


    Calendar now = Calendar.getInstance();
    String nowYear = String.valueOf(now.get(Calendar.YEAR));

    ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve_report);


        lineChart = (LineChart) findViewById(R.id.pichart_2);


        // X轴
        xAxis = lineChart.getXAxis();
        // 显示在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 坐标最小值
        xAxis.setAxisMinimum(0f);
        // 间隔
        xAxis.setGranularity(1f);
        // 网格线否
        xAxis.setDrawGridLines(true);
        // 月份
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.valueOf((int) value + 1).concat("月");
            }
        });


        // 去掉右侧Y轴
        rightYaxis = lineChart.getAxisRight();
        rightYaxis.setEnabled(false);
        rightYaxis.setDrawGridLines(false);
        rightYaxis.setAxisMinimum(0f);


        // 左侧网格线
        leftYAxis = lineChart.getAxisLeft();
        // 保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        // 网格线有
        leftYAxis.setDrawGridLines(true);


        // 折线标签
        legend = lineChart.getLegend();
        // 显示类型，LINE CIRCLE SQUARE EMPTY 等
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(40f);
        legend.setFormLineWidth(40f);
        legend.setFormToTextSpace(10f);
        // 字大小
        legend.setTextSize(10f);
        // 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 是否绘制在图表里面
        legend.setDrawInside(false);


        // lineChart
        // 是否展示网格线
        lineChart.setDrawGridBackground(true);
        // 是否显示边界
        lineChart.setDrawBorders(false);
        // 是否可以拖动
        lineChart.setDragEnabled(true);
        // 是否有触摸事件
        lineChart.setTouchEnabled(true);
        // 设置XY轴动画效果
        lineChart.animateX(1500);
        lineChart.animateY(1500);
        // 背景
        lineChart.setBackgroundColor(Color.rgb(241, 241, 201));
        lineChart.setGridBackgroundColor(Color.rgb(241, 241, 201));


        // set
        sets = new ArrayList<ILineDataSet>();
        sets.add(forExpense(nowYear, RECORD_TYPE_EXPENSE));
        sets.add(forIncome(nowYear, RECORD_TYPE_INCOME));


        // data
        LineData data = new LineData(sets);
        lineChart.setData(data);


        // 描述信息
        Description description = new Description();
        description.setText(nowYear + "年 · 分类支出");
        description.setTextAlign(Paint.Align.RIGHT);
        description.setTextSize(20);
        description.setTextColor(Color.rgb(3, 169, 244));
        lineChart.setDescription(description);


        // refresh
        lineChart.invalidate();


        // 更换年
        handleDoneCurveReport();
    }


    // 支出set
    public LineDataSet forExpense(String year, CurveReportBean.RecordType type) {

        // 支出curveReports
        List<CurveReportBean> curveReports = getCurveReports(year, type);

        // value
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < 12; i++) {
            double val = curveReports.get(i).getAmount();
            values.add(new Entry(i, (float) val));
        }

        // set
        LineDataSet set = new LineDataSet(values, "支出");
        // 圆点
        set.setDrawCircles(true);
        // 圆点大小
        set.setCircleRadius(3f);
        // 圆点实心
        set.setDrawCircleHole(false);
        // 圆点值尺寸
        set.setValueTextSize(15f);
        // 线宽
        set.setLineWidth(2f);
        // 平滑
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        // 填充
        set.setDrawFilled(true);
        set.setFormSize(15.f);
        set.setFormLineWidth(1f);
        // 曲度
        set.setCubicIntensity(0.8f);
        // 圆点颜色
        set.setCircleColor(Color.argb(250, 3, 169, 244));
        // 曲线颜色
        set.setColor(Color.argb(250, 3, 169, 244));

        return set;
    }


    // 收入set
    public LineDataSet forIncome(String year, CurveReportBean.RecordType type) {

        // 收入curveReports
        List<CurveReportBean> curveReports = getCurveReports(year, type);

        // value
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < 12; i++) {
            double val = curveReports.get(i).getAmount();
            values.add(new Entry(i, (float) val));
        }

        // set
        LineDataSet set = new LineDataSet(values, "收入");
        // 圆点
        set.setDrawCircles(true);
        // 圆点大小
        set.setCircleRadius(3f);
        // 圆点实心
        set.setDrawCircleHole(false);
        // 圆点值尺寸
        set.setValueTextSize(15f);
        // 线宽
        set.setLineWidth(1f);
        // 平滑
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        // 填充
        set.setDrawFilled(true);
        set.setFormSize(15.f);
        set.setFormLineWidth(1f);
        // 曲度
        set.setCubicIntensity(0.8f);
        // 圆点颜色
        set.setCircleColor(Color.argb(250, 255, 87, 34));
        // 曲线颜色
        set.setColor(Color.argb(200, 255, 87, 34));

        return set;
    }


    // 获取每个CurveReportBean
    private List<CurveReportBean> getCurveReports(String year, CurveReportBean.RecordType type) {

        // 支出/收入
        int type_int = type == RECORD_TYPE_EXPENSE ? 1 : 2;


        // 列出所有月份的curveReports，初始化月份，金额
        List<CurveReportBean> curveReports = new ArrayList<>();

        String[] months = {
                "01", "02", "03", "04",
                "05", "06", "07", "08",
                "09", "10", "11", "12",
        };

        for (String month : months) {
            curveReports.add(new CurveReportBean(year, month, type, 0));
        }


        // 根据类型查询reconds，再遍历
        LinkedList<RecordBean> records;

        if (type == RECORD_TYPE_EXPENSE) {
            records = GlobalUtil.getInstance().dbHelper.getRecordsByType(1);
        } else {
            records = GlobalUtil.getInstance().dbHelper.getRecordsByType(2);
        }

        for (int i = 0; i < records.size(); i++) {

            String record_year = records.get(i).getDate().substring(0, 4); // 2019
            String record_month = records.get(i).getDate().substring(5, 7); // 08

            // 筛选年份
            if (!record_year.equals(year)) {
                continue;
            }

            // 月份相同的，加金额
            for (int j = 0; j < curveReports.size(); j++) {
                if (record_month.equals(curveReports.get(j).getMonth())) {
                    curveReports.get(j).setAmount(curveReports.get(j).getAmount() + records.get(i).getAmount());
                }
            }
        }


        Log.d("curveReports------", curveReports.toString());


        return curveReports;
    }


    // 查询，按年
    private void handleDoneCurveReport() {
        findViewById(R.id.done_to_read_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        CurveReportActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {

                                nowYear = "" + selectedYear;

                                List<CurveReportBean> theClassReport_EXPENSE = getCurveReports(nowYear, RECORD_TYPE_EXPENSE);
                                List<CurveReportBean> theClassReport_INCOME = getCurveReports(nowYear, RECORD_TYPE_INCOME);

                                // 更新图表
                                updateChart(theClassReport_EXPENSE, theClassReport_INCOME);
                            }
                        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH));

                builder.setTitle("")
                        .setMinYear(2000)
                        .setMaxYear(2030)
                        .showYearOnly()
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
    public void updateChart(List<CurveReportBean> theClassReport_EXPENSE, List<CurveReportBean> theClassReport_INCOME) {

        // sets
        sets = new ArrayList<ILineDataSet>();

        sets.add(forExpense(nowYear, RECORD_TYPE_EXPENSE));
        sets.add(forIncome(nowYear, RECORD_TYPE_INCOME));

        // data
        LineData data = new LineData(sets);
        lineChart.setData(data);

        // 描述信息
        Description description = new Description();
        description.setText(nowYear + "年 · 分类支出");
        description.setTextAlign(Paint.Align.RIGHT);
        description.setTextSize(20);
        description.setTextColor(Color.rgb(3, 169, 244));
        lineChart.setDescription(description);

        // 设置XY轴动画效果
        lineChart.animateX(1500);
        lineChart.animateY(1500);

        lineChart.invalidate();
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
