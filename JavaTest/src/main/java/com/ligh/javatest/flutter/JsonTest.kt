package com.ligh.javatest.flutter

import java.io.File

fun main() {
    val filePath  = "D:\\code\\flutter\\testFlutter\\android\\app\\src\\main\\kotlin\\com\\example\\compat\\json.kt"
    if (filePath.isEmpty()) {
        println("请提供 Kotlin Bean 文件路径")
        return
    }

    val inputFile = File(filePath)
    if (!inputFile.exists()) {
        println("文件不存在: $filePath")
        return
    }


    val lines = inputFile.readLines()
    val dartFile = File(inputFile.parentFile, inputFile.nameWithoutExtension.lowercase() + ".dart")
    val output = StringBuilder()

    output.appendLine("import 'package:json_annotation/json_annotation.dart';")
    output.appendLine("part '${dartFile.nameWithoutExtension}.g.dart';")
    output.appendLine()

    var inClass = false
    var className = ""
    var pendingSerializedName: String? = null
    val fieldLines = mutableListOf<String>()
    val classes = mutableListOf<Pair<String, List<String>>>()

    for (line in lines.map { it.trim() }) {
        if (line.startsWith("@Serializable")) {
            inClass = true
            pendingSerializedName = null
            fieldLines.clear()
            continue
        }

        if (inClass && line.startsWith("data class")) {
            className = Regex("""data class (\w+)""").find(line)?.groupValues?.get(1) ?: ""
            continue
        }

        if (inClass && line.startsWith("@SerializedName")) {
            pendingSerializedName = Regex("""@SerializedName\("(.+?)"\)""").find(line)?.groupValues?.get(1)
            continue
        }

        if (inClass && (line.startsWith("val ") || line.startsWith("var "))) {
            val annotated = if (pendingSerializedName != null) {
                "@JsonKey(name: '$pendingSerializedName')\n" +
                        "  final ${convertField(line)}"
            } else {
                "  final ${convertField(line)}"
            }
            fieldLines.add(annotated)
            pendingSerializedName = null
            continue
        }

        if (inClass && line.startsWith(")")) {
            // 结束 class
            classes.add(Pair(className, fieldLines.toList()))
            inClass = false
        }
    }

    // 输出 Dart class
    for ((cls, fields) in classes) {
        output.appendLine("@JsonSerializable()")
        output.appendLine("class $cls {")
        val constructorParams = mutableListOf<String>()
        for (f in fields) {
            output.appendLine(f)
            val fieldName = f.substringAfterLast(" ").removeSuffix(";")
            constructorParams.add("required this.$fieldName")
        }
        output.appendLine()
        output.appendLine("  $cls({${constructorParams.joinToString(", ")}});")
        output.appendLine("  factory $cls.fromJson(Map<String, dynamic> json) => _\$${cls}FromJson(json);")
        output.appendLine("  Map<String, dynamic> toJson() => _\$${cls}ToJson(this);")
        output.appendLine("}\n")
    }

    dartFile.writeText(output.toString())
    println("✅ Dart 文件生成成功: ${dartFile.absolutePath}")
}

fun convertField(line: String): String {
    val cleanedLine = line.split("//")[0].trim().removeSuffix(",")
    val parts = cleanedLine.split(":")
    val name = parts[0].split(" ").last().trim()
    val kotlinType = parts[1].trim()
    val dartType = kotlinToDartType(kotlinType)
    return "$dartType $name;"
}


fun kotlinToDartType(type: String): String {
    val nullable = type.endsWith("?")
    val core = type.removeSuffix("?").trim()
    val dartType = when {
        core.startsWith("List<") -> {
            val inner = core.substringAfter("List<").substringBeforeLast(">")
            "List<${kotlinToDartType(inner)}>"
        }
        core.startsWith("Map<") -> "Map<String, dynamic>"
        core == "String" -> "String"
        core in listOf("Int", "Double", "Float", "Long") -> "num"
        core == "Boolean" -> "bool"
        else -> core
    }
    return if (nullable) "$dartType?" else dartType
}