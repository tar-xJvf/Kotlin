package kalkulator

fun main() {
    var result: Int
    var y: Int
    var x: Int

    val greeting = "Aritmatika Dasar\n"
    println("Sample $greeting")
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

    print("pilih operasi (+ - * ÷[/]): ")
    when (readln()) {
        "+" -> {
            result = x + y
            print("Hasil penjumlahan dari nilai $x dan nilai $y adalah: $result")
        }

        "-" -> {
            result = x - y
            print("Hasil pengurangan dari nilai $x dan nilai $y adalah: $result")
        }

        "*" -> {
            result = x * y
            print("Hasil perkalian dari nilai $x dan nilai $y adalah: $result")
        }

        "/" -> {
            result = x / y
            print("Hasil pembagian dari nilai $x dan nilai $y adalah: $result")
        }

        else -> {
            print("Operasi tidak valid")
        }
    }

}

