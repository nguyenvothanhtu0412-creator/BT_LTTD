package com.ute.studentprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.model.Student
import com.ute.studentprofile.utils.toAcademicRanking
import com.ute.studentprofile.utils.toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Dữ liệu mẫu ban đầu
    private var currentStudent = Student(
        id = "2415053122144",
        name = "Nguyễn Võ Thanh Tú",
        className = "126LTTD03",
        email = "2415053122144@sv.ute.udn.vn",
        gpa = 2.5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Gán dữ liệu sinh viên lên giao diện
        bindStudentData(currentStudent)

        // 2. Xử lý sự kiện cập nhật GPA
        binding.btnUpdateGpa.setOnClickListener {
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()

            // Validate dữ liệu nhập
            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast("Điểm GPA không hợp lệ!")
                return@setOnClickListener
            }

            // Cập nhật lại object bất biến thông qua copy()
            currentStudent = currentStudent.copy(gpa = newGpa)

            // Vẽ lại UI và báo thành công
            bindStudentData(currentStudent)
            toast("Cập nhật điểm thành công!")
        }
    }

    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} | Lớp: ${student.className}"
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }
}