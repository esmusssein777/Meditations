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
import java.util.List;
import java.util.Map;

/**
 * APP的入口类
 */
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
                            mCalendarView.removeSchemeDate(mCalendarView.getSelectedCalendar());//删除已有的
                            //将数据添加进数据库
                            int res = setDataCalendar(curColor);
                            //将颜色添加上
                            Calendar calendarView = setSchemeCalendar(curColor);
                            Map<String, Calendar> map = new HashMap<>();
                            map.put(calendarView.toString(), calendarView);
                            mCalendarView.addSchemeDate(map);
                        }
                    });
            boomMenuButton.addBuilder(builder);

        }
    }

    /**
     * 初始化日历数据
     */
    @Override
    protected void initData() {
        Map<String, Calendar> map = new HashMap<>();
        //从数据库中将数据查询出来放入这里
        CalendarDao dao = CalendarDao.getInstance(this);
        List<CalendarData> list = dao.getAllCalendar();
        for (CalendarData calendarData : list) {
            map.put(getSchemeCalendar(calendarData.getYear(), calendarData.getMonth(), calendarData.getDay(), calendarData.getScoreDay()).toString(),
                    getSchemeCalendar(calendarData.getYear(), calendarData.getMonth(), calendarData.getDay(), calendarData.getScoreDay()));
        }
        mCalendarView.setSchemeDate(map);
    }

    /**
     * 添加颜色
     * @param color 颜色
     * @return
     */
    private Calendar setSchemeCalendar(int color) {
        Calendar calendar = mCalendarView.getSelectedCalendar();
        //查询数据库，将数据放入
        CalendarDao dao = CalendarDao.getInstance(this);
        CalendarData calendarData = dao.getCalendarByTime(calendar.getYear(), calendar.getMonth(), calendar.getDay());
        String colors = calendarData.getScoreDay();
        if (colors != null && !"".equals(colors)) {
            String[] colorArray = colors.split(",");
            if (colorArray != null && colorArray.length > 0) {
                calendar.setSchemeColor(Integer.valueOf(colorArray[0]));//如果单独标记颜色、则会使用这个颜色
                calendar.setScheme("记");
                for (int i = 0; i < colorArray.length; i++) {
                    calendar.addScheme(Integer.valueOf(colorArray[i]), "记");
                }
            }
        }
        return calendar;
    }

    /**
     * 将颜色改变存入数据库
     * @param color
     * @return
     */
    private int setDataCalendar(int color) {
        String score = String.valueOf(color);//记录分数
        Calendar calendar = mCalendarView.getSelectedCalendar();
        CalendarDao dao = CalendarDao.getInstance(this);
        CalendarData calendarData = dao.getCalendarByTime(calendar.getYear(), calendar.getMonth(), calendar.getDay());
        //将数据放入数据库中
        if (calendarData == null || calendarData.getId() == null) {//数据库中没有就加一条数据
            calendarData = new CalendarData();
            calendarData.setYear(calendar.getYear());
            calendarData.setMonth(calendar.getMonth());
            calendarData.setDay(calendar.getDay());
            calendarData.setScoreDay(score);
            int result = dao.addCalendar(calendarData);
            return result;
        }
        //否则就更新数据
        String colors = calendarData.getScoreDay();
        if (colors == null || "".equals(colors)) {
            calendarData.setScoreDay(score);
        } else {
            calendarData.setScoreDay(score + "," + calendarData.getScoreDay());
        }
        int result = dao.updateCalendar(calendarData);
        return result;
    }

    /**
     * 初始化时渲染页面
     * @param year
     * @param month
     * @param day
     * @param colors
     * @return
     */
    private Calendar getSchemeCalendar(int year, int month, int day, String colors) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        if (colors != null && !"".equals(colors)) {
            String[] colorArray = colors.split(",");
            if (colorArray != null && colorArray.length > 0) {
                calendar.setSchemeColor(Integer.valueOf(colorArray[0]));//如果单独标记颜色、则会使用这个颜色
                calendar.setScheme("记");
                for (int i = 0; i < colorArray.length; i++) {
                    calendar.addScheme(Integer.valueOf(colorArray[i]), "记");
                }
            }
        }
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

