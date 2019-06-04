package com.ligz.meditations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.ligz.meditations.base.activity.BaseActivity;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener {

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    private int mYear;

    CalendarView mCalendarView;

    BoomMenuButton forenoon;

    BoomMenuButton afternoon;

    BoomMenuButton night;

    private static int[] menu_title = {R.string.memu_title_complete, R.string.memu_title_not_complete, R.string.memu_title_rest};
    private static int[] menu_content = {R.string.memu_content_complete, R.string.memu_content_not_complete, R.string.memu_content_rest};
    private static int[] menu_icon = {R.mipmap.menu_complete};
    private static int[] menu_color = {R.color.green, R.color.red, R.color.yellow};

    private static int[] scheme_color = {0xFF4DAF51, 0xFFEA1F64, 0xFFFF9800};

    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {

        setStatusBarDarkMode();
        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mTextLunar = (TextView) findViewById(R.id.tv_lunar);
        mTextCurrentDay = (TextView) findViewById(R.id.tv_current_day);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });

        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        forenoon = (BoomMenuButton)findViewById(R.id.boom_forenoon);
        afternoon = (BoomMenuButton)findViewById(R.id.boom_afternoon);
        night = (BoomMenuButton)findViewById(R.id.boom_night);
        for (int i = 0; i < forenoon.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(menu_icon[0])
                    .normalTextRes(menu_title[i])
                    .subNormalTextRes(menu_content[i])
                    .normalColorRes(menu_color[i])
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(MainActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                            int curYear = mCalendarView.getCurYear();
                            int curMonth = mCalendarView.getCurMonth();
                            int curDay =  mCalendarView.getCurDay();
                            int curColor = scheme_color[index];
                            Map<String, Calendar> map = new HashMap<>();
                            map.put(getSchemeCalendar(curYear, curMonth, curDay, curColor, "假").toString(),
                                    getSchemeCalendar(curYear, curMonth, curDay, curColor, "假"));
                            //此方法在巨大的数据量上不影响遍历性能，推荐使用
                            mCalendarView.addSchemeDate(map);
                        }
                    });
            forenoon.addBuilder(builder);
            afternoon.addBuilder(builder);
            night.addBuilder(builder);
        }


    }

    @Override
    protected void initData() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
/*        for (int y = 1997; y < 2082; y++) {
            for (int m = 1; m <= 12; m++) {
                map.put(getSchemeCalendar(y, m, 1, 0xFF40db25, "假").toString(),
                        getSchemeCalendar(y, m, 1, 0xFF40db25, "假"));
            }
        }*/
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(color, "假");
        //calendar.addScheme(day%2 == 0 ? 0xFF00CD00 : 0xFFD15FEE, "节");
        //calendar.addScheme(day%2 == 0 ? 0xFF660000 : 0xFF4169E1, "记");
        return calendar;
    }

    @Override
    public void onClick(View v) {
/*        switch (v.getId()) {
            case R.id.ll_flyme:
                CustomActivity.show(this);
                break;
        }*/
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }
}

