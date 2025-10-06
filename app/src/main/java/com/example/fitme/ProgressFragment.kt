package com.example.fitme

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitme.databinding.FragmentProgressBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch


class ProgressFragment : Fragment() {
    private var _binding: FragmentProgressBinding? = null
    private val binding: FragmentProgressBinding get() = _binding!!

    private lateinit var foodIntakeDao: FoodIntakeDao

    private val viewModel: ProgressViewModel by lazy {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProgressViewModel(foodIntakeDao) as T
            }
        }
        ViewModelProvider(this, factory)[ProgressViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProgressBinding.inflate(inflater,container,false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodIntakeDao = FitMeDatabase.getDatabase(requireContext()).foodIntakeDao()

        observeViewModel()
        viewModel.onEvent(ProgressEvent.LoadProgressData)

        binding.btnAddIntake.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, AddIntake())
                .addToBackStack(null)
                .commit()
        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.tvCaloriesValue.text = "${state.totalCalories} Cals"
                binding.progressCalories.progress = state.totalCalories
                binding.tvTotalCalories.text = "Total: ${state.totalCalories}"

                // build inline list (no adapter)
                displayFoodList(state.todayIntake)

                // draw line chart
                drawLineChart(state.days, state.weeklyCalories)

                state.errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayFoodList(foodList: List<FoodIntake>) {
        val container = binding.foodListRecyclerView // we’ll use it as a LinearLayout container
        container.removeAllViews()

        for (food in foodList) {
            val itemLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(16, 8, 16, 8)
            }

            val nameView = TextView(requireContext()).apply {
                text = food.foodName
                textSize = 16f
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            }

            val caloriesView = TextView(requireContext()).apply {
                text = "${food.calories} kcal"
                textSize = 16f
                setTextColor(Color.DKGRAY)
            }

            itemLayout.addView(nameView)
            itemLayout.addView(caloriesView)
            container.addView(itemLayout)
        }
    }

    private fun drawLineChart(days: List<String>, calories: List<Int>) {
        val lineChart: LineChart = binding.lineChart
        val entries = calories.mapIndexed { index, value ->
            Entry(
                index.toFloat(),
                value.toFloat()
            )
        }

        val dataSet = LineDataSet(entries, "Calorie Intake").apply {
            color = Color.RED
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.RED)
            setDrawValues(false)
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData

        // X-Axis formatting
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(days)
        xAxis.setDrawGridLines(false)
        xAxis.labelRotationAngle = -45f
        xAxis.textColor = Color.BLACK

        lineChart.axisLeft.textColor = Color.BLACK
        lineChart.axisRight.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}