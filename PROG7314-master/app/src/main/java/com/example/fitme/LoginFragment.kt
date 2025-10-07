package com.example.fitme

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fitme.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private lateinit var database: FitMeDatabase
    private lateinit var userDao: UserDao
    private lateinit var sessionManager: SessionManager

    // Initialize ViewModel
    private val viewModel: LoginViewModel by lazy {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(userDao) as T
            }
        }
        ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize DB + SessionManager
        database = FitMeDatabase.getDatabase(requireContext())
        userDao = database.userDao()
        sessionManager = SessionManager(requireContext())

        setupEventListeners()
        observeViewModel()

    }

    private fun setupEventListeners() {
        // Username and Password input handling
        binding.etUsername.onTextChanged {
            viewModel.onEvent(LoginEvent.checkUsername(it))
        }

        binding.etPassword.onTextChanged {
            viewModel.onEvent(LoginEvent.checkPassword(it))
        }

        // Login button click
        binding.btnLogin.setOnClickListener {
            sessionManager.clearSession()
            viewModel.onEvent(LoginEvent.Login)
        }

        // Navigate to RegisterFragment
//        binding.btnGoToRegister.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container_view, RegisterFragment())
//                .commit()
//        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginState.collectLatest { state ->
                // Show errors
                state.errorMessage?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }

                // Handle success
                if (state.isSuccess) {
                    lifecycleScope.launch {
                        try {
                            val username = state.username
                            val user = withContext(Dispatchers.IO) {
                                var user = userDao.getUserByUsername(username)
                                if (user == null && Patterns.EMAIL_ADDRESS.matcher(username)
                                        .matches()
                                ) {
                                    user = userDao.getUserByEmail(username)
                                }
                                user
                            }

                            if (user != null) {
                                sessionManager.saveUserSession(
                                    user.userId,
                                    user.email,
                                    user.username
                                )

                                Toast.makeText(
                                    requireContext(),
                                    "Login successful!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Navigate to HomeFragment
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container_view, HomeFragment())
                                    .commit()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "User not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                "Error retrieving user data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Extension to simplify text change handling
     */
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