package com.apgsga.apitestingsample

import org.springframework.stereotype.Component
import java.util.*


@Component
class Calculator {
    fun calculate(calcString: String): Int {
        // delete white spaces
        var s = calcString
        s = s.replace(" ".toRegex(), "")
        val stack = Stack<String>()
        val arr = s.toCharArray()
        var sb = StringBuilder()
        for (i in arr.indices) {
            if (arr[i] == ' ') continue
            if (arr[i] in '0'..'9') {
                sb.append(arr[i])
                if (i == arr.size - 1) {
                    stack.push(sb.toString())
                }
            } else {
                if (sb.isNotEmpty()) {
                    stack.push(sb.toString())
                    sb = StringBuilder()
                }
                if (arr[i] != ')') {
                    stack.push(String(charArrayOf(arr[i])))
                } else {
                    // when meet ')', pop and calculate
                    val t = ArrayList<String>()
                    while (!stack.isEmpty()) {
                        val top = stack.pop()
                        if (top == "(") {
                            break
                        } else {
                            t.add(0, top)
                        }
                    }
                    var temp = 0
                    temp = if (t.size == 1) t[0].toInt() else {
                        getTemp(t, temp)
                    }
                    stack.push(temp.toString())
                }
            }
        }
        val t = ArrayList<String>()
        while (!stack.isEmpty()) {
            val elem = stack.pop()
            t.add(0, elem)
        }
        val temp = 0
        return getTemp(t, temp)
    }

    private fun getTemp(t: ArrayList<String>, lastTemp: Int): Int {
        var temp = lastTemp
        var i = t.size - 1
        while (i > 0) {
            temp += if (t[i - 1] == "-") -t[i].toInt() else {
                t[i].toInt()
            }
            i -= 2
        }
        temp += t[0].toInt()
        return temp
    }
}