data class Student(
    val id: String,
    val name: String,
    val age: Int,
    val major: String,
    val gpa: Double
)

val students = mutableListOf(
    Student("SV01", "Nguyễn Võ Thanh Tú", 20, "CNTT", 8.5),
    Student("SV02", "Võ Thị Thu Trang", 22, "Kinh Tế", 7.2),
    Student("SV03", "Võ Tần Thiên Tân", 19, "CNTT", 9.1),
    Student("SV04", "Phạm Võ Gia Hân", 21, "Ngôn Ngữ", 6.5),
    Student("SV05", "Phạm Võ Gia Nhi", 23, "CNTT", 7.8)
)

fun printHeader() {
    println(String.format("%-10s | %-25s | %-5s | %-15s | %-5s", "ID", "Full Name", "Age", "Major", "GPA"))
    println("-".repeat(70))
}

fun printStudent(s: Student) {
    println(String.format("%-10s | %-25s | %-5d | %-15s | %.2f", s.id, s.name, s.age, s.major, s.gpa))
}

fun printList(list: List<Student>) {
    printHeader()
    if (list.isEmpty()) println("Không có sinh viên nào thỏa mãn!")
    else list.forEach { printStudent(it) }
}

fun addStudent() {
    try {
        print("Nhập ID: ")
        val id = readln().trim()

        // Kiểm tra ID trùng lặp
        if (students.any { it.id.equals(id, ignoreCase = true) }) {
            println("=> Lỗi: ID '$id' đã tồn tại!")
            return
        }

        print("Nhập Tên: ")
        val name = readln().trim()
        print("Nhập Tuổi: ")
        val age = readln().trim().toInt()
        print("Nhập Ngành: ")
        val major = readln().trim()
        print("Nhập GPA: ")
        val gpa = readln().trim().toDouble()

        students.add(Student(id, name, age, major, gpa))
        println("=> Thêm sinh viên thành công!")
    } catch (e: Exception) {
        println("=> Lỗi: Dữ liệu tuổi hoặc GPA không hợp lệ!")
    }
}

fun displayAndSort() {
    println("--- 1. Mặc định | 2. Xếp GPA giảm dần | 3. Xếp theo tuổi | 4. Xếp theo tên (A-Z) | 5. Top 3 GPA | 6. Thống kê GPA ---")
    print("Chọn kiểu hiển thị: ")
    when (readln().toIntOrNull()) {
        1 -> printList(students)
        2 -> printList(students.sortedByDescending { it.gpa })
        3 -> printList(students.sortedBy { it.age })
        4 -> printList(students.sortedBy { it.name })
        5 -> printList(students.sortedByDescending { it.gpa }.take(3))
        6 -> {
            println("=> Số sinh viên GPA >= 8.0: ${students.count { it.gpa >= 8.0 }}")
            println("=> Số sinh viên GPA < 5.0: ${students.count { it.gpa < 5.0 }}")
        }
        else -> println("=> Lựa chọn không hợp lệ!")
    }
}

fun searchStudent() {
    println("--- 1. Tìm theo phần tên | 2. Tìm theo ngành | 3. Tìm GPA (7.0 -> 8.5) ---")
    print("Chọn kiểu tìm kiếm: ")
    when (readln().toIntOrNull()) {
        1 -> {
            print("Nhập từ khóa tên: ")
            val keyword = readln().trim().lowercase()
            printList(students.filter { it.name.lowercase().contains(keyword) })
        }
        2 -> {
            print("Nhập ngành: ")
            val major = readln().trim()
            // Sửa lỗi: trim() cả hai vế để tránh lệch do khoảng trắng thừa trong dữ liệu
            printList(students.filter { it.major.trim().equals(major.trim(), ignoreCase = true) })
        }
        3 -> printList(students.filter { it.gpa in 7.0..8.5 })
        else -> println("=> Lựa chọn không hợp lệ!")
    }
}

fun calculateAvgGPA() {
    println("--- 1. Trung bình toàn trường | 2. Trung bình theo ngành ---")
    print("Chọn: ")
    when (readln().toIntOrNull()) {
        1 -> {
            val avg = if (students.isNotEmpty()) students.map { it.gpa }.average() else 0.0
            println("=> GPA trung bình toàn trường: %.2f".format(avg))
        }
        2 -> {
            print("Nhập ngành cần tính: ")
            val major = readln().trim()
            // Sửa lỗi: trim() cả hai vế để tránh lệch do khoảng trắng thừa trong dữ liệu
            val filtered = students.filter { it.major.trim().equals(major.trim(), ignoreCase = true) }
            if (filtered.isEmpty()) {
                println("=> Không tìm thấy sinh viên ngành $major!")
            } else {
                val avg = filtered.map { it.gpa }.average()
                println("=> GPA trung bình ngành $major: %.2f".format(avg))
            }
        }
        else -> println("=> Lựa chọn không hợp lệ!")
    }
}

fun findHighestGPAOrOldest() {
    println("--- 1. Sinh viên có GPA cao nhất | 2. Sinh viên lớn tuổi nhất ---")
    print("Chọn: ")
    when (readln().toIntOrNull()) {
        1 -> {
            val best = students.maxByOrNull { it.gpa }
            if (best != null) printList(listOf(best))
            else println("=> Không có sinh viên nào trong hệ thống!")
        }
        2 -> {
            val oldest = students.maxByOrNull { it.age }
            if (oldest != null) printList(listOf(oldest))
            else println("=> Không có sinh viên nào trong hệ thống!")
        }
        else -> println("=> Lựa chọn không hợp lệ!")
    }
}

fun removeStudent() {
    print("Nhập ID sinh viên cần xóa: ")
    val id = readln().trim()
    val isRemoved = students.removeIf { it.id.equals(id, ignoreCase = true) }
    if (isRemoved) println("=> Xóa thành công sinh viên có ID: $id")
    else println("=> Không tìm thấy sinh viên!")
}

fun main() {
    while (true) {
        println("\n========== STUDENT MANAGEMENT ==========")
        println("1. Add student (Thêm sinh viên mới)")
        println("2. Display all students (Hiển thị tất cả sinh viên)")
        println("3. Search student (Tìm kiếm sinh viên)")
        println("4. Calculate average GPA (Tính điểm GPA trung bình)")
        println("5. Find student with highest GPA / Oldest (Tìm sinh viên có GPA cao nhất) ")
        println("6. Remove student")
        println("0. Exit")
        println("========================================")
        print("Choose: ")

        when (readln().toIntOrNull()) {
            1 -> addStudent()
            2 -> displayAndSort()
            3 -> searchStudent()
            4 -> calculateAvgGPA()
            5 -> findHighestGPAOrOldest()
            6 -> removeStudent()
            0 -> {
                println("Đã thoát chương trình. Hẹn gặp lại!")
                return
            }
            else -> println("=> Lựa chọn không hợp lệ, vui lòng chọn từ 0-6!")
        }
    }
}