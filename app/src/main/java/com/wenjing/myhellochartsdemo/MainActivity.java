package com.wenjing.myhellochartsdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    ColumnChartView mChartView;

    public final static String[] xValues = new String[]{"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private int month;
    private SubcolumnValue subcolumnValue;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initChartViews();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initChartViews() {
        List<Column> columnList = new ArrayList<>(); //柱子列表
        List<SubcolumnValue> subcolumnValueList;     //子柱列表（即一个柱子，因为一个柱子可分为多个子柱）
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            subcolumnValueList = new ArrayList<>();
            //获取数据处理
            if (i == 1) {
                subcolumnValue = new SubcolumnValue();
                subcolumnValue.setValue(6f);
                if (i <= month) {
                    subcolumnValue.setColor(getResources().getColor(R.color.colorAccent));
                } else {
                    subcolumnValue.setColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                subcolumnValueList.add(subcolumnValue);
            }
            if (i == 2) {
                subcolumnValue = new SubcolumnValue();
                subcolumnValue.setValue(6.4f);
                if (i <= month) {
                    subcolumnValue.setColor(getResources().getColor(R.color.colorAccent));

                } else {
                    subcolumnValue.setColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                subcolumnValueList.add(subcolumnValue);
            }
            if (i >= 3) {
                subcolumnValue = new SubcolumnValue();
                subcolumnValue.setValue(7f);
                if (i <= month) {
                    subcolumnValue.setColor(getResources().getColor(R.color.colorAccent));
                } else {
                    subcolumnValue.setColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                subcolumnValueList.add(subcolumnValue);
            }


            Column column = new Column(subcolumnValueList);
            columnList.add(column);
            //是否有数据标注
            column.setHasLabels(true);//☆☆☆☆☆设置列标签
            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            //TODO 这一步是能让圆柱标注数据显示带小数的重要一步
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(1);
            column.setFormatter(chartValueFormatter);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i - 1).setLabel(xValues[i]));
        }


        //图形数据加载
        ColumnChartData columnChartData = new ColumnChartData(columnList);
        mChartView.setZoomEnabled(true);//手势缩放
        mChartView.setInteractive(true);//设置图表是可以交互的（拖拽，缩放等效果的前提）
        mChartView.setZoomType(ZoomType.HORIZONTAL);//设置缩放方向
        //        mChartView.setValueSelectionEnabled(true);
        //        mChartView.setClickable(true);

        Axis axisX = new Axis(axisValues);//x轴
        Axis axisY = new Axis();//y轴
        //是否显示网格线 两个必须都设置
        axisY.setHasLines(true);
        axisY.hasLines();

        //设置轴名称
        axisX.setName("时间/月");
        axisY.setName("利息");

        axisX.setTextColor(getResources().getColor(R.color.colorAccent));
        axisY.setTextColor(getResources().getColor(R.color.colorAccent));
        //设置倾斜显示在柱子内部
        //        axisX.setInside(true);
        //        axisX.setHasTiltedLabels(true);

        columnChartData.setFillRatio(0.5F);//参数即为柱子宽度的倍数，范围只能在0到1之间
        columnChartData.setAxisXBottom(axisX);
        columnChartData.setAxisYLeft(axisY);
        //把数据放到控件中
        mChartView.setColumnChartData(columnChartData);

        //设置竖线
        Viewport v = mChartView.getMaximumViewport();
        v.top = 7.4f;
        v.bottom = 5.5f;
        mChartView.setCurrentViewport(v);
    }
}
