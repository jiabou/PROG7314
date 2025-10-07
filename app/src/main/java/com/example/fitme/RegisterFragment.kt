package com.example.fitme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fitme.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText
    private lateinit var etDob: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var database: FitMeDatabase
    private lateinit var userDao: UserDao

    // initialize for ViewModel
    private val viewModel: UserViewModel by lazy {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(userDao) as T
            }
        }
        ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init database + DAO
        database = FitMeDatabase.getDatabase(requireContext())
        userDao = database.userDao()

        setupEventListeners()
        observeViewModel()
    }

    private fun setupEventListeners() {
        // Text change listeners push events into ViewModel
        binding.etUsername.onTextChanged { viewModel.onEvent(UserEvent.setUsername(it)) }
        binding.etEmail.onTextChanged { viewModel.onEvent(UserEvent.setEmail(it)) }
        binding.etPassword.onTextChanged { viewModel.onEvent(UserEvent.setPassword(it)) }
        binding.etHeight.onTextChanged {
            val h = it.toDoubleOrNull() ?: 0.0
            viewModel.onEvent(UserEvent.setHeight(h))
        }
        binding.etWeight.onTextChanged {
            val w = it.toDoubleOrNull() ?: 0.0
            viewModel.onEvent(UserEvent.setWeight(w))
        }
        binding.etDob.onTextChanged { viewModel.onEvent(UserEvent.setDob(it)) }
        binding.etPhone.onTextChanged { viewModel.onEvent(UserEvent.setPhone(it)) }


        // Register button
        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(UserEvent.createUser)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.userState.collect { state ->
                // Errors
                state.errorMessage?.let { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }

                // Success
                if (state.isSuccess) {
                    Toast.makeText(requireContext(), "Account created!", Toast.LENGTH_LONG).show()

                    // Clear form
                    binding.etUsername.text.clear()
                    binding.etEmail.text.clear()
                    binding.etPassword.text.clear()
                    binding.etHeight.text.clear()
                    binding.etWeight.text.clear()
                    binding.etDob.text.clear()
                    binding.etPhone.text.clear()

                    // Navigate to LoginFragment
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, LoginFragment())
                        .commit()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun android.widget.EditText.onTextChanged(listener: (String) -> Unit) {
        this.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                listener(s.toString())
            }
        })
    }
}