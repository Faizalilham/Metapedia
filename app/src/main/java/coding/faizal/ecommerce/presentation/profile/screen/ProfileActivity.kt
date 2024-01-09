package coding.faizal.ecommerce.presentation.profile.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityProfileBinding
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.menu.screen.MenuActivity
import coding.faizal.ecommerce.presentation.profile.viewmodel.ProfileViewModel
import coding.faizal.ecommerce.utils.Dialog.showDialogLogout
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToAddress
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToBase
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToEditProfile
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraLogin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private var _binding : ActivityProfileBinding? = null
    private val binding get() = _binding!!

    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private var i : ProfileUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentDataUser()
        logout()
        navigation()
        getProfileUser()

    }

    private fun getProfileUser() {
        authPreferencesViewModel.getToken().observe(this) {
            if (it != null) {
                profileViewModel.getCurrentUser("Bearer $it")
                Log.d("TOKEN",it)

                setProfileUser()
            }
        }
    }

    private fun getIntentDataUser(){
        i = intent.getParcelableExtra<ProfileUser>(MenuActivity.MENU)
        i?.let { setupViewProfile(it) }
    }

    private fun setupViewProfile(user : ProfileUser){
        binding.apply {
            tvUsername.text = user.name
            tvEmail.text = user.email
        }
    }

    private fun logout(){
        binding.linearLogout.setOnClickListener {
            showDialogLogout(this){
                authPreferencesViewModel.deleteIsLogin()
                navigateToPraLogin(this)
                finish()
            }
        }
    }

    private fun setProfileUser(){
        lifecycleScope.launch {
            profileViewModel.profileResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateShimmerVisibility(true)
                        binding.linearProfile.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            navigation()
                            setupViewProfile(resource.data)
                        }
                        updateShimmerVisibility(false)
                        binding.linearProfile.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                        Toast.makeText(this@ProfileActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                        updateShimmerVisibility(false)
                    }
                }
            }
        }
    }

    private fun updateShimmerVisibility(isLoading: Boolean) {
        binding.linearShimmer.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainer.stopShimmer()
        }
    }

    private fun navigation(){
        binding.apply {
            imgBack.setOnClickListener {  finish() }
            linearProfile.setOnClickListener {
                navigateToEditProfile(this@ProfileActivity, data = i)
            }
            linearAddress.setOnClickListener { navigateToAddress(this@ProfileActivity) }
            linearSecurity.setOnClickListener { navigateToBase(this@ProfileActivity, PROFILE_DATA,
                PROFILE_DATA) }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object{
        const val PROFILE_DATA = "profile_data"
    }


    override fun onResume() {
        super.onResume()
        setProfileUser()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}