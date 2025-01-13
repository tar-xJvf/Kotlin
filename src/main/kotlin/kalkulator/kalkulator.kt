package kalkulator

import kotlin.system.exitProcess
import java.util.*

fun main() {

    val expression = StringBuilder()
    var ulangiProgram = "Y"
    var x: Int
    var y: Int

    println("--------------------------------------------------------------------------------------")
    println("Kalkulator Sederhana")
    println("Cara menggunakan\nketik '=' untuk melakukan operasi dan 'keluar' untuk keluar dari menu")
    println("--------------------------------------------------------------------------------------")

    while (ulangiProgram.equals("Y") || (ulangiProgram.equals("y"))) {
        println("1. Kalkulator")
        println("2. Menghitung luas persegi Panjang")
        println("3. Keluar")
        print("Masukkan pilihan menu: ")
        val inputMenu = readlnOrNull().toString().trim()
        when (inputMenu) {
            "1" -> {

                while (true) {
                    print("Silakan input data: ")
                    val inputData = readlnOrNull()?.trim()
                    if (inputData.equals("keluar", ignoreCase = true)) {
                        break
                    }

                    if (inputData == "=") {

                        try {
                            val result = evaluateExpression(expression.toString())
                            println("Hasil perhitungan: $result")
                            expression.clear()
                        } catch (e: Exception) {
                            println("Ekspresi tidak valid!,Silakan coba lagi")
                            expression.clear()
                        }
                        continue
                    }

                    if (inputData != null && inputData.matches(Regex("[0-9+\\-*/.()]+"))) {
                        expression.append(inputData)
                        println("Data sekarang: $expression")

                    } else {
                        println("Input data tidak valid.Hanya angka dan operator aritmatika")
                    }


                }
            }

            "2" -> {
                while (true) {

                    println("Menghitung luas persegi panjang")
                    while (true) {

                        try {
                            print("Masukkan nilai pertama: ")
                            x = readln().toInt()
                            break
                        } catch (e: NumberFormatException) {
                            println("Masukkan hanya angka!")
                        }
                    }
                    while (true) {
                        try {
                            print("Masukkan nilai kedua: ")
                            y = readln().toInt()
                            break
                        } catch (e: NumberFormatException) {
                            println("Masukkan hanya angka!")
                        }
                    }

                    val luasPersegiPanjang = x * y
                    println("Hasilnya $luasPersegiPanjang")
                    exitProcess(0)
                }
            }

            "3" -> {
                println("Bye")
                exitProcess(0)
            }

            else -> println("Pilihan salah")
        }
        print("Ingin mengulangi? (Y/N): ")
        ulangiProgram = readlnOrNull().toString().trim()

    }

}

fun evaluateExpression(expression: String): Double {
    val tokenList = tokenize(expression)
    val postfixTokens = convertToRPN(tokenList)
    return evaluateRPN(postfixTokens)
}

fun tokenize(input: String): List<String> {
    val tokenPattern = Regex("[0-9.]+|[+\\-*/()]")
    return tokenPattern.findAll(input).map { it.value }.toList()
}

fun convertToRPN(tokenList: List<String>): List<String> {
    val outputQueue = mutableListOf<String>()
    val opt = Stack<String>()
    val operatorPrecedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)

    for (currentToken in tokenList) {
        when {
            currentToken.matches(Regex("[0-9.]+")) -> outputQueue.add(currentToken)
            currentToken in operatorPrecedence.keys -> {
                while (opt.isNotEmpty() &&
                    (operatorPrecedence[opt.peek()] ?: 0) >= operatorPrecedence[currentToken]!!
                ) {
                    outputQueue.add(opt.pop())
                }
                opt.push(currentToken)
            }

            currentToken == "(" -> opt.push(currentToken)
            currentToken == ")" -> {
                while (opt.isNotEmpty() && opt.peek() != "(") {
                    outputQueue.add(opt.pop())
                }
                if (opt.isNotEmpty() && opt.peek() == "(") opt.pop()
            }
        }
    }

    while (opt.isNotEmpty()) {
        outputQueue.add(opt.pop())
    }

    return outputQueue
}

fun evaluateRPN(postfixTokens: List<String>): Double {
    val evaluationStack = Stack<Double>()

    for (currentToken in postfixTokens) {
        when {
            currentToken.matches(Regex("[0-9.]+")) -> evaluationStack.push(currentToken.toDouble())
            currentToken in listOf("+", "-", "*", "/") -> {
                val value2 = evaluationStack.pop()
                val value1 = evaluationStack.pop()
                evaluationStack.push(
                    when (currentToken) {
                        "+" -> value1 + value2
                        "-" -> value1 - value2
                        "*" -> value1 * value2
                        "/" -> value1 / value2
                        else -> throw IllegalArgumentException("Operator tidak valid,Silakan diulangi")
                    }
                )
            }
        }
    }

    return evaluationStack.pop()
}

