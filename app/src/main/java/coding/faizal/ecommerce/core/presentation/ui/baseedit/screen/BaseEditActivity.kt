package coding.faizal.ecommerce.core.presentation.ui.baseedit.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.databinding.ActivityBaseEditBinding
import coding.faizal.ecommerce.presentation.viewmodel.authentication.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.viewmodel.user.UserViewModel
import coding.faizal.ecommerce.presentation.ui.editprofile.screen.EditProfileActivity
import coding.faizal.ecommerce.presentation.viewmodel.forgetpassword.ForgetPasswordViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BaseEditActivity : AppCompatActivity() {

    private var _binding : ActivityBaseEditBinding? = null
    private val binding get() = _binding!!

    private var username : String? = null

    private val userViewModel by viewModels<UserViewModel>()
    private val forgetPasswordViewModel by viewModels<ForgetPasswordViewModel>()
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBaseEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra(EditProfileActivity.EDIT_PROFILE_DATA)
        if(username != null){
            binding.etUsername.setText(username)
        }
        setupView()
        doChangeUsername()
        doResetPassword()

    }

    private fun setupView() {
        if (username != null) {
            toggleVisibility(View.GONE, View.VISIBLE)
        } else {
            toggleVisibility(View.VISIBLE, View.GONE)
        }
    }

    private fun toggleVisibility(resetPasswordVisibility: Int, changeUsernameVisibility: Int) {
        binding.linearResetPassword.visibility = resetPasswordVisibility
        binding.linearChangeUsername.visibility = changeUsernameVisibility
    }

    private fun showErrorState(editText: TextInputLayout, errorMessage: String) {
        editText.apply {
            setBoxStrokeColorStateList(resources.getColorStateList(R.color.red))
            helperText = errorMessage
            setHelperTextColor(ContextCompat.getColorStateList(context, R.color.red))
        }
    }

    private fun doChangeUsername() {
        binding.btnChangeUsername.setOnClickListener {
            val username = binding.etUsername.text.toString()
            if (username.length < 8) {
                showErrorState(binding.etInputUsername, "Username minimal 8 karakter")
            } else {
                authPreferencesViewModel.getToken().observe(this) {
                    if (it != null) {
                        userViewModel.updateUsername("Bearer $it", username)
                    }
                }
                changeUsernameResult()
            }
        }
    }

    private fun doResetPassword() {
        binding.btnChangePassword.setOnClickListener {
            val password = binding.etPassword.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            if (password.length >= 8 && repeatPassword.length >= 8) {
                if (password != repeatPassword) {
                    showErrorState(binding.etInputPassword, "Password tidak cocok")
                    showErrorState(binding.etInputRepeatPassword, "Password tidak cocok")
                } else {
                    authPreferencesViewModel.getToken().observe(this) {
                        if (it != null) {
                            userViewModel.updateUsername("Bearer $it", password)
                        }
                    }
                    resetPasswordResult()
                }
            } else {
                showErrorState(binding.etInputPassword, "Password minimal 8 karakter")
                showErrorState(binding.etInputRepeatPassword, "Password minimal 8 karakter")
            }
        }
    }

    private fun changeUsernameResult() {
        lifecycleScope.launch {
            userViewModel.updateUsernameResult.collect { resource ->
                handleResourceResult(resource,"Update username successfully")
            }
        }
    }

    private fun resetPasswordResult() {
        lifecycleScope.launch {
            forgetPasswordViewModel.resetPasswordResult.collect { resource ->
                handleResourceResult(resource,"Reset password successfully")
            }
        }
    }

    private fun handleResourceResult(resource: coding.faizal.ecommerce.core.data.Resource<*>, message : String) {
        when (resource) {
            is coding.faizal.ecommerce.core.data.Resource.Loading -> {
                binding.loadingPanel.visibility = View.VISIBLE
            }
            is coding.faizal.ecommerce.core.data.Resource.Success -> {
                binding.loadingPanel.visibility = View.GONE
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish()
            }
            is coding.faizal.ecommerce.core.data.Resource.Error -> {
                binding.loadingPanel.visibility = View.GONE
                val errorMessage = resource.message
                Toast.makeText(this@BaseEditActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}