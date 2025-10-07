package com.example.fitme

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.fitme.databinding.FragmentPasswordChangeBinding

class PasswordChangeFragment : Fragment() {

    private var _binding: FragmentPasswordChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordChangeBinding.inflate(inflater, container, false)
        val root = binding.root

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
        return root
    }

    private fun setupListeners() {
        // Back button in toolbar
        binding.changePasswordToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Change password button
        binding.btnChangePassword.setOnClickListener {
            val current = binding.etCurrentPassword.text.toString()
            val new = binding.etNewPassword.text.toString()
            val confirm = binding.etConfirmPassword.text.toString()

            if (validateInputs(current, new, confirm)) {
                // TODO: Implement actual password change logic
                Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show()
                // Clear fields
                binding.etCurrentPassword.text?.clear()
                binding.etNewPassword.text?.clear()
                binding.etConfirmPassword.text?.clear()
            }
        }
    }

    private fun validateInputs(current: String, new: String, confirm: String): Boolean {
        if (TextUtils.isEmpty(current)) {
            binding.etCurrentPassword.error = "Current password is required"
            return false
        }
        if (TextUtils.isEmpty(new)) {
            binding.etNewPassword.error = "New password is required"
            return false
        }
        if (new.length < 6) {
            binding.etNewPassword.error = "Password must be at least 6 characters"
            return false
        }
        if (new != confirm) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
