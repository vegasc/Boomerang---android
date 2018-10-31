package com.alekseyrobul.boomerang.helpers

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        @JvmStatic
        fun getCurrentDate(format: String = "dd:mm:YYYY:mm:ss"): String {
            val simpleDateFormat = SimpleDateFormat(format)
            return simpleDateFormat.format(Calendar.getInstance().time)
        }
    }
}