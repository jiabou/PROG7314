package com.example.fitme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.example.fitme.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Edit Profile button
        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.btnEditProfile)
        }

        // Change Password button
        binding.btnChangePassword.setOnClickListener {
            findNavController().navigate(R.id.btnChangePassword)
        }

        // About Us button
        binding.btnAboutUs.setOnClickListener {
            findNavController().navigate(R.id.btnAboutUs)
        }

        // Privacy Policy button
        binding.btnPrivacyPolicy.setOnClickListener {
            findNavController().navigate(R.id.btnPrivacyPolicy)
        }

        // Terms and Conditions button
        binding.btnTerms.setOnClickListener {
            findNavController().navigate(R.id.btnTerms)
        }

        // Push Notifications toggle
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            // Handle notifications setting here
        }

        // Dark Mode toggle
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Handle dark mode toggle
        }

        // Toolbar back button
        binding.settingsToolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
