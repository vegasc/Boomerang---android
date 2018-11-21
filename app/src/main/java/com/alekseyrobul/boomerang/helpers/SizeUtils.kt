package com.alekseyrobul.boomerang.helpers

import java.text.DecimalFormat

class SizeUtils {
    companion object {
        private const val SPACE_KB = 1024.0
        private const val SPACE_MB = 1024 * SPACE_KB
        private const val SPACE_GB = 1024 * SPACE_MB
        private const val SPACE_TB = 1024 * SPACE_GB

        class SizeResult(val size:String, val type:String)

        @JvmStatic
        fun getSize(bytes: Long): SizeResult? {
            val nf = DecimalFormat()
            nf.maximumFractionDigits = 2
            return when {
                bytes < SPACE_KB -> SizeResult(nf.format(bytes), "Byte(s)")
                bytes < SPACE_MB -> SizeResult(nf.format(bytes / SPACE_KB), "KB")
                bytes < SPACE_GB -> SizeResult(nf.format(bytes / SPACE_MB), "MB")
                bytes < SPACE_TB -> SizeResult(nf.format(bytes / SPACE_GB), "GB")
                else -> SizeResult(nf.format(bytes / SPACE_TB), "TB")
            }

        }
    }
}