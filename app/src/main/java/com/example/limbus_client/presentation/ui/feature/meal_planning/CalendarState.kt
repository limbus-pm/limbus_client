package com.example.limbus_client.presentation.ui.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.util.*

enum class CalendarViewType {
    DAILY,
    WEEKLY,
    MONTHLY
}

data class CalendarState(
    val currentDate: Date = Date(),
    val viewType: CalendarViewType = CalendarViewType.DAILY,
    val selectedDate: Date = Date(),
    val showDatePicker: Boolean = false
)

class MutableCalendarState {
    var currentDate by mutableStateOf(Date())
    var viewType by mutableStateOf(CalendarViewType.DAILY)
    var selectedDate by mutableStateOf(Date())
    var showDatePicker by mutableStateOf(false)

    fun toCalendarState(): CalendarState {
        return CalendarState(
            currentDate = currentDate,
            viewType = viewType,
            selectedDate = selectedDate,
            showDatePicker = showDatePicker
        )
    }

    fun updateState(
        currentDate: Date? = null,
        viewType: CalendarViewType? = null,
        selectedDate: Date? = null,
        showDatePicker: Boolean? = null
    ) {
        currentDate?.let { this.currentDate = it }
        viewType?.let { this.viewType = it }
        selectedDate?.let { this.selectedDate = it }
        showDatePicker?.let { this.showDatePicker = it }
    }
}

@Composable
fun rememberMutableCalendarState(
    initialDate: Date = Date(),
    initialViewType: CalendarViewType = CalendarViewType.DAILY
): MutableCalendarState {
    return remember {
        MutableCalendarState().apply {
            currentDate = initialDate
            viewType = initialViewType
            selectedDate = initialDate
        }
    }
}

object CalendarManager {
    private val spanishLocale = Locale("es", "ES")

    fun formatDateForDaily(date: Date): String {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = getMonthName(calendar.get(Calendar.MONTH))
        return "$day de $month"
    }

    fun formatDateForWeekly(startDate: Date, endDate: Date): String {
        val startCal = Calendar.getInstance(spanishLocale)
        startCal.time = startDate
        val endCal = Calendar.getInstance(spanishLocale)
        endCal.time = endDate

        val startDay = startCal.get(Calendar.DAY_OF_MONTH)
        val startMonth = getShortMonthName(startCal.get(Calendar.MONTH))
        val endDay = endCal.get(Calendar.DAY_OF_MONTH)
        val endMonth = getShortMonthName(endCal.get(Calendar.MONTH))

        return "$startDay $startMonth - $endDay $endMonth"
    }

    fun formatDateForMonthly(date: Date): String {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date
        val month = getMonthName(calendar.get(Calendar.MONTH))
        val year = calendar.get(Calendar.YEAR)
        return "$month $year"
    }

    fun getWeekRange(date: Date): Pair<Date, Date> {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date
        calendar.firstDayOfWeek = Calendar.SUNDAY
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = calendar.time

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        val endOfWeek = calendar.time

        return Pair(startOfWeek, endOfWeek)
    }

    fun getMonthRange(date: Date): Pair<Date, Date> {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonth = calendar.time

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endOfMonth = calendar.time

        return Pair(startOfMonth, endOfMonth)
    }

    fun navigateDate(currentDate: Date, viewType: CalendarViewType, forward: Boolean): Date {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = currentDate

        when (viewType) {
            CalendarViewType.DAILY -> {
                calendar.add(Calendar.DAY_OF_MONTH, if (forward) 1 else -1)
            }
            CalendarViewType.WEEKLY -> {
                calendar.add(Calendar.WEEK_OF_YEAR, if (forward) 1 else -1)
            }
            CalendarViewType.MONTHLY -> {
                calendar.add(Calendar.MONTH, if (forward) 1 else -1)
            }
        }

        return calendar.time
    }

    fun getFormattedDateText(date: Date, viewType: CalendarViewType): String {
        return when (viewType) {
            CalendarViewType.DAILY -> formatDateForDaily(date)
            CalendarViewType.WEEKLY -> {
                val (start, end) = getWeekRange(date)
                formatDateForWeekly(start, end)
            }
            CalendarViewType.MONTHLY -> formatDateForMonthly(date)
        }
    }

    fun getViewTypeLabel(viewType: CalendarViewType): String {
        return when (viewType) {
            CalendarViewType.DAILY -> "DIARIO"
            CalendarViewType.WEEKLY -> "SEMANAL"
            CalendarViewType.MONTHLY -> "MENSUAL"
        }
    }

    fun getNextViewType(currentType: CalendarViewType): CalendarViewType {
        return when (currentType) {
            CalendarViewType.DAILY -> CalendarViewType.WEEKLY
            CalendarViewType.WEEKLY -> CalendarViewType.MONTHLY
            CalendarViewType.MONTHLY -> CalendarViewType.DAILY
        }
    }

    fun getDaysInWeek(date: Date): List<Date> {
        val (startOfWeek, _) = getWeekRange(date)
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = startOfWeek

        return (0..6).map {
            val dayDate = calendar.time
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            dayDate
        }
    }

    fun getDaysInMonth(date: Date): List<Date> {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return (1..daysInMonth).map {
            calendar.set(Calendar.DAY_OF_MONTH, it)
            calendar.time
        }
    }

    fun isToday(date: Date): Boolean {
        val today = Calendar.getInstance()
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = date

        return today.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == dateCalendar.get(Calendar.DAY_OF_YEAR)
    }

    fun isSameWeek(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance(spanishLocale)
        cal1.time = date1
        val cal2 = Calendar.getInstance(spanishLocale)
        cal2.time = date2

        return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }

    fun isSameMonth(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance(spanishLocale)
        cal1.time = date1
        val cal2 = Calendar.getInstance(spanishLocale)
        cal2.time = date2

        return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }

    fun getShortDayName(date: Date): String {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "DOM"
            Calendar.MONDAY -> "LUN"
            Calendar.TUESDAY -> "MAR"
            Calendar.WEDNESDAY -> "MIÉ"
            Calendar.THURSDAY -> "JUE"
            Calendar.FRIDAY -> "VIE"
            Calendar.SATURDAY -> "SÁB"
            else -> ""
        }
    }

    fun getMonthName(date: Date): String {
        val calendar = Calendar.getInstance(spanishLocale)
        calendar.time = date
        val month = getMonthName(calendar.get(Calendar.MONTH))
        val year = calendar.get(Calendar.YEAR)
        return "$month $year"
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            Calendar.JANUARY -> "Enero"
            Calendar.FEBRUARY -> "Febrero"
            Calendar.MARCH -> "Marzo"
            Calendar.APRIL -> "Abril"
            Calendar.MAY -> "Mayo"
            Calendar.JUNE -> "Junio"
            Calendar.JULY -> "Julio"
            Calendar.AUGUST -> "Agosto"
            Calendar.SEPTEMBER -> "Septiembre"
            Calendar.OCTOBER -> "Octubre"
            Calendar.NOVEMBER -> "Noviembre"
            Calendar.DECEMBER -> "Diciembre"
            else -> ""
        }
    }

    private fun getShortMonthName(month: Int): String {
        return when (month) {
            Calendar.JANUARY -> "Ene"
            Calendar.FEBRUARY -> "Feb"
            Calendar.MARCH -> "Mar"
            Calendar.APRIL -> "Abr"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "Jun"
            Calendar.JULY -> "Jul"
            Calendar.AUGUST -> "Ago"
            Calendar.SEPTEMBER -> "Sep"
            Calendar.OCTOBER -> "Oct"
            Calendar.NOVEMBER -> "Nov"
            Calendar.DECEMBER -> "Dic"
            else -> ""
        }
    }
}