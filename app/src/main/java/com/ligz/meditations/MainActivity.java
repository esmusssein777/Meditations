package com.ligz.meditations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.ligz.meditations.base.activity.BaseActivity;
import com.ligz.meditations.data.CalendarDao;
import com.ligz.meditations.model.CalendarData;
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

    BoomMenuButton boomMenuButton;




    private static int[] menu_title = {R.string.memu_title_complete, R.string.memu_title_not_complete, R.string.memu_title_rest};
    private static final int[] menu_content = {R.string.memu_content_complete, R.string.memu_content_not_complete, R.string.memu_content_rest};
    private static int[] menu_icon = {R.mipmap.menu_complete};
    private static final int[] menu_color = {R.color.green, R.color.red, R.color.yellow};
    private static final String[] menu_score = {"1","2","3"};//对用的分数

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

        boomMenuButton = (BoomMenuButton)findViewById(R.id.boom_day);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(menu_icon[0])
                    .normalTextRes(menu_title[i])
                    .subNormalTextRes(menu_content[i])
                    .normalColorRes(menu_color[i])
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            int curColor = scheme_color[index];
                            //将数据添加进去
                            int res = setDataCalendar(curColor);
                            if (res > 0) {

                            }
                            //将颜色添加上
                            mCalendarView.removeSchemeDate(mCalendarView.getSelectedCalendar());//删除已有的
                            Calendar calendarView = setSchemeCalendar(curColor, "假");
                            Map<String, Calendar> map = new HashMap<>();
                            map.put(calendarView.toString(), calendarView);

                            mCalendarView.addSchemeDate(map);
                        }
                    });
            boomMenuButton.addBuilder(builder);

        }


    }

    @Override
    protected void initData() {

        Map<String, Calendar> map = new HashMap<>();
        //TODO:从数据库中将数据查询出来放入这里
        mCalendarView.setSchemeDate(map);
    }

    /**
     * 添加颜色
     * @param color
     * @param text
     * @return
     */
    private Calendar setSchemeCalendar(int color, String text) {

        Calendar calendar = mCalendarView.getSelectedCalendar();
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        CalendarDao dao = CalendarDao.getInstance(this);
        CalendarData data = dao.getCalendarByTime(calendar.getYear(), calendar.getMonth(), calendar.getDay());
        String score = data.getScoreDay();
        for (String s : score.split(",")) {
            if (!"".equals(s)) {
                calendar.addScheme(Integer.valueOf(s), "记");
            }
        }

        return calendar;
    }

    private int setDataCalendar(int color) {
        Calendar calendar = mCalendarView.getSelectedCalendar();
        CalendarDao dao = CalendarDao.getInstance(this);
        //将数据放入数据库中
        CalendarData calendarData = new CalendarData();
        calendarData.setYear(calendar.getYear());
        calendarData.setMonth(calendar.getMonth());
        calendarData.setDay(calendar.getDay());
        String score = String.valueOf(color);
        calendarData.setScoreDay(score + ",");
        int result = dao.addCalendar(calendarData);
        return result;
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

