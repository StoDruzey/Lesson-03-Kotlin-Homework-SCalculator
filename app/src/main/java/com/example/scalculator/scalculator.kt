fun main() {
    result(getData())
}

fun getData(): List<String> {
    println("enter an arithmetic expression without brackets then press 'enter': ")
    var string: String? = readLine()
    string = string ?: ""
    string = string.replace("""\s+""".toRegex(), "") //all spaces are removed
    val operands = mutableListOf<String>()
    if (string.isNotBlank()) {
        val regex = Regex("""-?\d+(\.\d+)?[*/\-+]?""")
        regex.findAll(string).forEach {
            operands.add(it.value)
        }
    }
    operands[operands.size - 1] = operands.last().replace("""[*/\-+]?$""".toRegex(), "")
    //removing the last symbol of the last operand if action
    return operands
}

fun result(ops: List<String>) {
    val operands = ops.toMutableList()
    var i = 0
    var size = operands.size
    //first multiply and divide
    while (i < (size - 1)) {
        val num1 = operands[i].dropLast(1).toDouble()
        var num2: Double
        var act2: String
        if (i == size - 2) {
            num2 = operands[i + 1].toDouble()
            act2 = ""
        } else {
            num2 = operands[i + 1].dropLast(1).toDouble()
            act2 = operands[i + 1].takeLast(1)
        }
        when (operands[i].takeLast(1)) {
            "*" -> {
                operands[i + 1] = (num1 * num2).toString() + act2
                operands.removeAt(i)
                --size
                --i
            }
            "/" -> {
                if (num2 == 0.0) {
                    println("error: divide by zero")
                    return
                }
                operands[i + 1] = (num1 / num2).toString() + act2
                operands.removeAt(i)
                --size
                --i
            }
        }
        ++i
    }
    //then add and subtract
    size = operands.size
    i = 0
    while (i < (size - 1)) {
        val num1 = operands[i].dropLast(1).toDouble()
        var num2: Double
        var act2: String
        if (i == size - 2) {
            num2 = operands[i + 1].toDouble()
            act2 = ""
        } else {
            num2 = operands[i + 1].dropLast(1).toDouble()
            act2 = operands[i + 1].takeLast(1)
        }
        when (operands[i].takeLast(1)) {
            "+" -> {
                operands[i + 1] = (num1 + num2).toString() + act2
                operands.removeAt(i)
                --size
                --i
            }
            "-" -> {
                operands[i + 1] = (num1 - num2).toString() + act2
                operands.removeAt(i)
                --size
                --i
            }
        }
        ++i
    }
    ops.forEach { print(it) }
    println(" = ${operands[0].toString()}")
}
