package com.tianfang.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianfang.common.dto.DateDto;
import com.google.common.base.Objects;

public class DateUtils extends org.apache.commons.lang.time.DateUtils
{
    public static final String Y = "yyyy";
    public static final String M = "M";
    public static final String D = "d";
    public static final String YMD = "yyyyMMdd";
    public static final String MD_SLASH = "M/d";
    public static final String MDHM = "MM月dd日H点m分";
    public static final String YMD_POINT = "yyyy.MM.dd";
    public static final String YMD_SLASH = "yyyy/MM/dd";
    public static final String YMD_DASH = "yyyy-MM-dd";
    public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
    public static final String YDM_SLASH = "yyyy/dd/MM";
    public static final String YDM_DASH = "yyyy-dd-MM";
    public static final String HM = "HHmm";
    public static final String HM_COLON = "HH:mm";
    public static final String HH = "HH";
    public static final long DAY = 24 * 60 * 60 * 1000L;
    public static final long MIN = 24 * 1000L;
    public static final long HOUR = 60 * 60 * 1000L;

    public static final String[] weekDays = {"日", "一", "二", "三", "四", "五", "六" };
    public static final String[] weekDaysOf = {"7", "1", "2", "3", "4", "5", "6" };

    public static final String TODAY = "今天";
    public static final String TOMORROW = "明天";

    private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

    // public static void main(String[] args) {
    // System.out.println(format(new Date(), D));
    // }

    private DateUtils()
    {
    }

    public static DateFormat getFormat(String pattern)
    {
        DateFormat format = DFS.get(pattern);
        if (format == null)
        {
            format = new SimpleDateFormat(pattern);
            DFS.put(pattern, format);
        }
        return format;
    }

    public static Date parse(String source, String pattern)
    {
        if (source == null)
        {
            return null;
        }
        Date date;
        try
        {
            date = getFormat(pattern).parse(source);
        }
        catch (ParseException e)
        {
            return null;
        }
        return date;
    }

    public static String format(Date date, String pattern)
    {
        if (date == null)
        {
            return null;
        }
        return getFormat(pattern).format(date);
    }

    /**
     * @param year 年
     * @param month 月(1-12)
     * @param day 日(1-31)
     * @return 输入的年、月、日是否是有效日期
     */
    public static boolean isValid(int year, int month, int day)
    {
        if (month > 0 && month < 13 && day > 0 && day < 32)
        {
            // month of calendar is 0-based
            int mon = month - 1;
            Calendar calendar = new GregorianCalendar(year, mon, day);
            if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == mon
                && calendar.get(Calendar.DAY_OF_MONTH) == day)
            {
                return true;
            }
        }
        return false;
    }

    private static Calendar convert(Date date)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 返回指定年数位移后的日期
     */
    public static Date yearOffset(Date date, int offset)
    {
        return offsetDate(date, Calendar.YEAR, offset);
    }

    /**
     * 返回指定月数位移后的日期
     */
    public static Date monthOffset(Date date, int offset)
    {
        return offsetDate(date, Calendar.MONTH, offset);
    }

    /**
     * 返回指定天数位移后的日期
     */
    public static Date dayOffset(Date date, int offset)
    {
        return offsetDate(date, Calendar.DATE, offset);
    }

    /**
     * 返回指定日期相应位移后的日期
     * 
     * @param date 参考日期
     * @param field 位移单位，见 {@link Calendar}
     * @param offset 位移数量，正数表示之后的时间，负数表示之前的时间
     * @return 位移后的日期
     */
    public static Date offsetDate(Date date, int field, int offset)
    {
        Calendar calendar = convert(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    /**
     * 返回当月第一天的日期
     */
    public static Date firstDay(Date date)
    {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 返回当月最后一天的日期
     */
    public static Date lastDay(Date date)
    {
        Calendar calendar = convert(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 返回两个日期间的差异天数
     * 
     * @param date1
     *            参照日期
     * @param date2
     *            比较日期
     * @return 参照日期与比较日期之间的天数差异，正数表示参照日期在比较日期之后，0表示两个日期同天，负数表示参照日期在比较日期之前
     */
    public static int dayDiff(Date date1, Date date2)
    {
        long diff = date1.getTime() - date2.getTime();
        return (int) (diff / DAY);
    }

    /*
     * 分钟比较
     */
    public static int minDiff(Date date1, Date date2)
    {
        long diff = date1.getTime() - date2.getTime();
        return (int) diff / (60 * 1000);
    }

    /*
     * 秒比较
     *  */
    public static int secondsDiff(Date date1, Date date2)
    {
        long diff = 0;
        if (date2 != null)
        {
            diff = date1.getTime() - date2.getTime();
        }
        else
        {
            diff = date1.getTime() - date1.getTime();
        }

        return (int) diff / 1000;
    }

    /**
     * 此方法描述的是：判断当前日期是周几
     * 
     * @author: cwftalus@163.com
     * @version: 2015年3月26日 上午9:18:13
     */
    public static String dayForWeek(String pTime, String pattern) throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayForWeek < 0)
        {
            dayForWeek = 0;
        }
        // System.out.println(c.get(Calendar.DAY_OF_WEEK)+"=="+dayForWeek+"=="+(c.get(Calendar.DAY_OF_WEEK)
        // - 1));
        return weekDays[dayForWeek];
    }

    /**
     * 
     * 此方法描述的是：获取当前日期 1，2，3，4，5，6，7 对应周一到周日
     * 
     * @author: cwftalus@163.com
     * @version: 2015年3月26日 上午11:39:42
     */
    public static Short dayForWeek(Date pTime) throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat(MD_SLASH);
        Calendar c = Calendar.getInstance();
        c.setTime(pTime);
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayForWeek < 0)
        {
            dayForWeek = 0;
        }
        return Short.valueOf(weekDaysOf[dayForWeek]);
    }

    /**
     * 
     * 此方法描述的是：
     * 
     * @author: cwftalus@163.com
     * @version: 2015年11月5日 上午9:40:20
     * @date 当前日期
     * @offset 往后延迟几天
     */

    public static List<DateDto> queryDayAndWeek(Date date, int offset) throws Exception
    {
        List<DateDto> dateList = new ArrayList<DateDto>();
        for (int i = 0; i < offset; i++)
        {
            DateDto dateDto = new DateDto();
            Date offsetDate = dayOffset(date, i);//, MD_SLASH;
            String tdate = format(offsetDate, YMD);
            String tday = format(offsetDate, D);
            dateDto.setDay(tday);
            //			if (Objects.equal(i, 0)) {
            //				dateDto.setWeek(TODAY);
            //			} else if (Objects.equal(i, 1)) {
            //				dateDto.setWeek(TOMORROW);
            //			} else {
            //				dateDto.setWeek(dayForWeek(tdate,MD_SLASH));
            //			}
            dateDto.setDayForWeek(dayForWeek(offsetDate));
            dateDto.setWeek(dayForWeek(tdate, YMD));
            dateDto.setDate(format(offsetDate, YMD_DASH));
            dateList.add(dateDto);
        }
        return dateList;
    }

    /**     
     * afterHourDate：判断设置有效时限是否超过有效期  
     * @param hour 有效时限  小时
     * @param date 有效期开始时间
     * @return  true:过期 fasle:未过期
     * @author Vincent
     * @date 2015年5月22日 下午4:40:53	 
     */
    public static boolean afterHourDate(int hour, Date date)
    {
        try
        {
            long lStartTime = date.getTime();
            long lEndTime = System.currentTimeMillis();
            System.out.println(((lEndTime - lStartTime) / HOUR));
            if (((lEndTime - lStartTime) / HOUR) < hour)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (Exception ex)
        {
            return true;
        }
    }

    public static void main(String[] args) throws Exception
    {

        String d1 = "2015-05-22 12:00";
        long lStartTime = parse(d1, YMD_DASH_WITH_TIME).getTime();
        long lEndTime = System.currentTimeMillis();
        System.out.println(((lEndTime - lStartTime) / HOUR));

        String t1 = "2015-05-05 12:08";
        String t2 = "2015-05-05 12:10";

        System.out.println(minDiff(parse(t2, YMD_DASH_WITH_TIME), parse(t1, YMD_DASH_WITH_TIME)));

        long l = parse(t2, YMD_DASH_WITH_TIME).getTime() - parse(t1, YMD_DASH_WITH_TIME).getTime();

        long day = l / (24 * 60 * 60 * 1000);

        long hour = (l / (60 * 60 * 1000) - day * 24);

        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);

        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        //System.out.println(l / (60 * 1000) + "-" + day + "天" + hour + "小时" + min + "分" + s + "秒");
        //		List<DateDto> result = queryDayAndWeek(new Date(), 7);
        //		for(DateDto d : result){
        //			System.out.println(d.getWeek());
        //		}
        //		System.out.println(format(parse("2015-12-12 12:00",YMD_DASH_WITH_TIME), DateUtils.MDHM));
        //		System.out.println(parse("2015-12-12 12:00", MDHM));
        //		String str = new java.text.SimpleDateFormat("MM月dd日 H时m分").format(new java.util.Date());
        //        String yeah = str.substring(0,4); //取年
        //        String yue = str.substring(str.indexOf("年")+1,str.indexOf("月")); //取月
        //        String ri = str.substring(str.indexOf("月")+1,str.indexOf("日")); //取日
        //        
        //        System.out.println(str);

        //		Date date = new Date();
        //		for (int i = 0; i < 7; i++) {
        //			String tdate = format(dayOffset(date, i), MD_SLASH);
        //			try {
        //				System.out.println(tdate + "---" + dayForWeek(tdate));
        //			} catch (Exception e) {
        //				// TODO Auto-generated catch block
        //				e.printStackTrace();
        //			}
        //		}
    }

    /**      
     * 字符串转换到时间格式
     * @param dateStr 需要转换的字符串
     * @param formatStr 需要格式的目标字符串
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date StringToDate(String dateStr, String formatStr)
    {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try
        {
            date = sdf.parse(dateStr);
        }
        catch (java.text.ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 与现在时间比较   单位:分钟
     * @param dates
     * @return
     */
    public static long diffNowMin(Date dates)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dates);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        long time1 = c1.getTimeInMillis();
        long time2 = c2.getTimeInMillis();
        long diff = time2 - time1;
        long diffMin = diff / (60 * 1000);
        return diffMin;
    }

    /**
     * 与现在时间比较   单位:秒
     * @param dates
     * @return
     */
    public static long diffNowSecond(Date dates)
    {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dates);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        long time1 = c1.getTimeInMillis();
        long time2 = c2.getTimeInMillis();
        long diff = time2 - time1;
        long diffMin = diff / 1000;
        return diffMin;
    }

}
