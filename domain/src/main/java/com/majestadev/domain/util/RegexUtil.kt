package com.majestadev.domain.util

class RegexUtil {
    companion object {
        fun extractIdFromUrl(url: String): Int {
            val matchResult = Regex("[0-9]+$").find(url)
            return matchResult?.let { it.groupValues[0].toInt() } ?: 0
        }

        fun extractFromBrackets(row: String): String {
            val matchResult = Regex("(?<=\\[).+?(?=\\])").find(row)
            return matchResult?.let { it.groupValues[0] } ?: ""
        }
    }
}