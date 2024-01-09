package coding.faizal.ecommerce.presentation.menu.screen

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.databinding.ActivityMenuBinding
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.preferences.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.cart.screen.CartActivity
import coding.faizal.ecommerce.presentation.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.home.screen.HomeActivity.Companion.HOME
import coding.faizal.ecommerce.presentation.menu.viewmodel.MenuViewModel
import coding.faizal.ecommerce.utils.NavigationUtils
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHelp
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHistorySearch
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraLogin
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToPraRegister
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToProfile
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToRegister
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private var _binding : ActivityMenuBinding? = null
    private val binding get() = _binding!!

    private val menuViewModel by viewModels<MenuViewModel>()
    private val authPreferencesViewModel by viewModels<AuthPreferencesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.getStringExtra(HOME) == null){
            cekIsFromHome(intent.getStringExtra(CartActivity.CART))
            cekIsFromHome(intent.getStringExtra(DetailProduct.DETAIL))
        }

        getProfileUser()


    }

    private fun intentDataUser(){
        binding.linearProfile.setOnClickListener {
            navigateToProfile(this,)
        }
    }

    private fun getProfileUser() {
        authPreferencesViewModel.getToken().observe(this) {
            if (it != null) {
                authPreferencesViewModel.getIsLogin().observe(this){log ->
                    if(log){
                        menuViewModel.getCurrentUser("Bearer $it")
                    }else{
                        binding.linearLogin.visibility = View.GONE
                        binding.linearShimmer.visibility = View.GONE
                    }
                }
                setProfileUser()
            } else {
                updateVisibility(false)
            }
        }

        authPreferencesViewModel.getIsLogin().observe(this) {
            updateVisibility(it == true)
        }

        updateShimmerVisibility(false)
    }


    private fun setProfileUser(){
        lifecycleScope.launch {
            menuViewModel.menuResult.collect{ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateShimmerVisibility(true)
                        binding.linearProfile.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        if (resource.data != null) {
                            updateShimmerVisibility(false)
                            binding.linearProfile.visibility = View.VISIBLE

                            intentDataUser()
                            navigation(resource.data)
                            setupView(resource.data.name, resource.data.email)

                        }

                    }
                    is Resource.Error -> {
                        val errorMessage = resource.message
                        Toast.makeText(this@MenuActivity, "$errorMessage", Toast.LENGTH_SHORT).show()
                        updateShimmerVisibility(false)
                    }
                }
            }
        }
    }

    private fun updateVisibility(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            binding.linearLogin.visibility = View.GONE
        } else {
            binding.linearLogin.visibility = View.VISIBLE
            binding.linearProfile.visibility = View.GONE
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



    private fun setupView(name : String,email : String){
        binding.apply {
            tvName.text = name
            tvEmail.text = email
        }
    }

    private fun navigation(data : ProfileUser){
        binding.apply {
            imgBack.setOnClickListener {
                finish()
//                if (Build.VERSION.SDK_INT >= 34) {
//                    this@MenuActivity.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.slide_down, R.anim.stay)
//                }
            }
            linearProfile.setOnClickListener { navigateToProfile(this@MenuActivity,data = data) }
            linearFavorite.setOnClickListener { navigateToHome(this@MenuActivity,HomeActivity.FAVORITE) }
            linearTransaction.setOnClickListener { navigateToHome(this@MenuActivity,HomeActivity.TRANSACTION) }
            linearHistorySearch.setOnClickListener { navigateToHistorySearch(this@MenuActivity) }
            linearMetacare.setOnClickListener { navigateToHelp(this@MenuActivity) }
        }
    }

    private fun cekIsFromHome(data : String?){
        binding.apply {
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                if(data != null)  binding.expandableLayout.expand() else binding.expandableLayout.collapse()
            }
        }
    }

    companion object{
       const val MENU = "menu"
    }

    override fun finish() {
        super.finish()
//        if (Build.VERSION.SDK_INT >= 34) {
//            this@MenuActivity.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.slide_down, R.anim.stay)
//        }
        overridePendingTransition(R.anim.slide_down, R.anim.stay)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}