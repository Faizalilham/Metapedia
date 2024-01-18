package coding.faizal.ecommerce.core.presentation.ui.register.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.core.presentation.viewmodel.authentication.AuthenticationViewModel
import coding.faizal.ecommerce.databinding.ActivityRegisterBinding
import coding.faizal.ecommerce.extensions.doubleSpanText
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.ReusableTextWatcher
import coding.faizal.ecommerce.utils.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private var _binding : ActivityRegisterBinding? = null
    private val binding get () = _binding!!

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    private var dataIntent : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataIntent = intent.getStringExtra(DATA_REGISTER)
        binding.text.doubleSpanText(binding.text,resources.getString(R.string.text_sub_register),41,59,66,84,this,{
            Toast.makeText(this, "Syarat", Toast.LENGTH_SHORT).show()
        }){
            Toast.makeText(this, "Privasi", Toast.LENGTH_SHORT).show()
        }

        if(dataIntent != null){
            binding.etEmail.setText(dataIntent)
        }

        handleValidation()
        back()
        doRegister()

    }

    private fun doRegister(){
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val name = etFullname.text.toString()
                val password = etNewPassword.text.toString()
                authenticationViewModel.doRegister(email,name,password)
                UiUtil.hideKeyboard(this@RegisterActivity, currentFocus ?: View(this@RegisterActivity))
                registerResult()
            }
        }
    }

    private fun handleValidation(){
        lifecycleScope.launch {
            authenticationViewModel.areFieldsValid.collect { isValid ->
                binding.btnRegister.isEnabled = isValid
            }

        }

        binding.etFullname.addTextChangedListener(ReusableTextWatcher(binding.etFullname) {
            authenticationViewModel.updateFullNameInput(it)
        })

        binding.etNewPassword.addTextChangedListener(ReusableTextWatcher(binding.etNewPassword) {
            authenticationViewModel.updatePasswordInput(it)
        })
    }

    private fun registerResult(){
        lifecycleScope.launch {
            authenticationViewModel.registerResult.collect{ resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is coding.faizal.ecommerce.core.data.Resource.Loading -> {
                            binding.loadingPanel.visibility = View.VISIBLE
                        }
                        is coding.faizal.ecommerce.core.data.Resource.Success -> {
                            binding.loadingPanel.visibility = View.GONE

                            Toast.makeText(this@RegisterActivity, resource.message, Toast.LENGTH_SHORT).show()
                            navigateToHome(this@RegisterActivity)
                            finish()
                        }
                        is coding.faizal.ecommerce.core.data.Resource.Error -> {
                            binding.loadingPanel.visibility = View.GONE
                            val errorMessage = resource.message

                            Toast.makeText(this@RegisterActivity, "$errorMessage", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }

    }

    private fun back(){
        binding.imgBack.setOnClickListener {
            navigateToHome(this)
            finish()
        }
    }

    companion object{
        const val DATA_REGISTER = "data_register"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}