package com.appringer.common.utils

import com.appringer.common.helper.LoggerHelper
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val mLOCALE = Locale.getDefault()

    fun getTodayStart(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR, cal.getActualMinimum(Calendar.HOUR))
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND))
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE))
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND))
        LoggerHelper.log("TS", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }

    fun getTodayEnd(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR, cal.getActualMaximum(Calendar.HOUR))
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND))
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE))
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND))

        LoggerHelper.log("TS", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }

    fun getYesterday(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR, cal.getActualMinimum(Calendar.HOUR))
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND))
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE))
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND))

        cal.add(Calendar.DATE, -1)
        LoggerHelper.log("YS", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }

    fun getWeekStartDate(offset: Int = 0): Long {
        val cal = Calendar.getInstance()
        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            cal.add(Calendar.DATE, -1)
        }
        cal.set(Calendar.HOUR, cal.getActualMinimum(Calendar.HOUR))
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND))
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE))
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND))

        cal.add(Calendar.DATE, offset)
        LoggerHelper.log("WSD", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }


    fun getWeekEndDate(offset: Int = 0): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = getWeekStartDate()

        cal.add(Calendar.DATE, 6)
        cal.add(Calendar.DATE, offset)
        LoggerHelper.log("WED", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }

    fun getMonthStartDate(offset: Int = 0): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
        cal.set(Calendar.HOUR, cal.getActualMinimum(Calendar.HOUR))
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND))
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE))
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND))

        cal.add(Calendar.MONTH, offset)
        LoggerHelper.log("MSD", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }


    fun getMonthEndDate(offset: Int = 0): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        cal.set(Calendar.HOUR, cal.getActualMaximum(Calendar.HOUR))
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY))
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND))
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE))
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND))

        cal.add(Calendar.MONTH, offset)
        LoggerHelper.log("MED", getDateTimeDisplayFormat(cal.timeInMillis))
        return cal.timeInMillis
    }

    fun getDateTimeDisplayFormat(timeInMs: Long): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMs
            val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm a", mLOCALE)
            sdf.format(calendar.time)
        } catch (var4: Exception) {
            ""
        }
    }

    fun getDateDisplayFormat(timeInMs: Long): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMs
            val sdf = SimpleDateFormat("dd-MMM-yyyy", mLOCALE)
            sdf.format(calendar.time)
        } catch (var4: Exception) {
            "Jan 1, 2017"
        }
    }

    fun getTimeDisplayFormat(timeInMs: Long): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeInMs
            val sdf = SimpleDateFormat("hh:mm a", mLOCALE)
            sdf.format(calendar.time).toUpperCase()
        } catch (var4: Exception) {
            ""
        }
    }

    fun getCurrentTimeInMs(): Any {
        return System.currentTimeMillis()
    }

}