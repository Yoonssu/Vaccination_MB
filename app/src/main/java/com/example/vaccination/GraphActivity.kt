package com.example.vaccination

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vaccination.databinding.ActivityGraphBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class GraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 라인에 들어갈 숫자
        val line_values: ArrayList<Entry> = ArrayList()

        // 데이터 추가 (2018 ~ 2024)
        val dataPoints = listOf(
            2f, 3f, 4f, 15f, 18f, 22f, 17f // 2018, 2019, 2020, 2021, 2022, 2023, 2024
        )

        dataPoints.forEachIndexed { index, value ->
            line_values.add(Entry(2018f + index, value))
        }

        // 선 그래프에 대한 여러 설정
        val linedataset = LineDataSet(line_values, "연도별 데이터")
        linedataset.color = Color.GREEN
        linedataset.lineWidth = 3f
        linedataset.setCircleColor(Color.MAGENTA)

        val linedata = LineData(linedataset)
        binding.lineChart.data = linedata

        // 축 설정
        val xAxis = binding.lineChart.xAxis
        xAxis.granularity = 1f
        xAxis.labelCount = 7
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        }

        binding.lineChart.invalidate() // 그래프 갱신
    }
}
