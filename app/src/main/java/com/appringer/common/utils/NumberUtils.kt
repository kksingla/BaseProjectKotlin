package com.appringer.stylist.utils

import java.text.DecimalFormat
import java.text.Format
import java.text.NumberFormat
import java.util.*

object NumberUtils {
    fun formattedPrice(price: Int): String {
        val format: Format = DecimalFormat.getCurrencyInstance(Locale("en", "in"))
        if (format is NumberFormat) {
            format.maximumFractionDigits = 0
        }
        return "${format.format(price)} /-"
    }
}