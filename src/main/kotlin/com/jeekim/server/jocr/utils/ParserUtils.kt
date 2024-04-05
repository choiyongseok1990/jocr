package com.jeekim.server.jocr.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object ParserUtils {
    private const val EMPTY = ""
    private const val WHITE_SPACE = " "
    private val BRACKETS_SET = setOf(
        "[", "]", "{", "}", "(", ")"
    )
    private val ASTERISK = "*"

    fun getStringValue(any: Any?): String? {
        if (any != null) {
            val stringValue = any.toString().trim()

            if (stringValue.isNotBlank()) {
                return stringValue
            }
        }

        return null
    }

    fun getStringValueWithoutWhiteSpace(any: Any?): String {
        if (any != null) {
            val stringValue = any.toString().trim()

            if (stringValue.isNotBlank()) {
                return stringValue.replace(WHITE_SPACE, EMPTY)
            }
        }

        return EMPTY
    }

    fun getStringList(values: Any?): MutableList<String> {
        try {
            val result = mutableListOf<String>()
            val valuesObjectList = values as? List<String>

            valuesObjectList?.let {
                for (valuesString in it) {
                    val valuesArray = valuesString.split(",")

                    for (value in valuesArray) {
                        if (valuesString == "0" || valuesString == ",") {
                            continue
                        }

                        if (value.isNotBlank()) {
                            result.add(value.trim())
                        }
                    }
                }
                return result
            }
        } catch (e: Exception) {
            return mutableListOf()
        }

        return mutableListOf()
    }

    fun removeParenthesis(valueString: String?): String? {
        if (valueString == null) {
            return null
        }

        val index: Int = valueString.indexOf("(")
        if (index != -1) {
            return valueString.substring(0, index).trim()
        }

        return valueString
    }


    fun deletePatternFromString(word: String, delete: String): String {
        return word.replaceFirst(
            Regex(Pattern.quote(delete)),
            Matcher.quoteReplacement(EMPTY)
        ).trim()
    }

    fun clearBrackets(word: String): String {
        var resultWord = word
        val containingBrackets = BRACKETS_SET.filter { word.contains(it) }

        for (bracket in containingBrackets) {
            resultWord = resultWord.replace(bracket, "")
        }

        return resultWord
    }

    fun removeAsterisk(patientRrn: String): String {
        return patientRrn.replace(ASTERISK, EMPTY)
    }
}