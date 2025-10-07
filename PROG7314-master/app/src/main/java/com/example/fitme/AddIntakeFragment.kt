package com.example.fitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitme.databinding.FragmentAddIntakeBinding

class AddIntake : Fragment() {
    private var _binding: FragmentAddIntakeBinding? = null
    private val binding: FragmentAddIntakeBinding get() = _binding!!
    private lateinit var foodIntakeDao: FoodIntakeDao

    private val viewModel: ProgressViewModel by lazy {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProgressViewModel(foodIntakeDao) as T
            }
        }
        ViewModelProvider(requireActivity(), factory)[ProgressViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddIntakeBinding.inflate(inflater,container,false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodIntakeDao = FitMeDatabase.getDatabase(requireContext()).foodIntakeDao()

        binding.btnSaveIntake.setOnClickListener {
            val food = binding.etFoodName.text.toString()
            val calories = binding.etCalories.text.toString().toIntOrNull() ?: 0

            viewModel.onEvent(ProgressEvent.AddFoodIntake(food, calories))

            Toast.makeText(requireContext(), "Intake added!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}