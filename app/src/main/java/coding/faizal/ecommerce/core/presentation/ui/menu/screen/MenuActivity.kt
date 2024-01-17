package coding.faizal.ecommerce.core.presentation.ui.menu.screen


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.databinding.ActivityMenuBinding
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.presentation.viewmodel.authentication.AuthPreferencesViewModel
import coding.faizal.ecommerce.presentation.ui.cart.screen.CartActivity
import coding.faizal.ecommerce.presentation.ui.detailproduct.screen.DetailProduct
import coding.faizal.ecommerce.presentation.ui.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.ui.home.screen.HomeActivity.Companion.HOME
import coding.faizal.ecommerce.presentation.viewmodel.user.UserViewModel
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHelp
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHistorySearch
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private var _binding : ActivityMenuBinding? = null
    private val binding get() = _binding!!

    private val userViewModel by viewModels<UserViewModel>()
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
                        userViewModel.getCurrentUser("Bearer $it")
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
            userViewModel.profileResult.collect{ resource ->
                when (resource) {
                    is coding.faizal.ecommerce.core.data.Resource.Loading -> {
                        updateShimmerVisibility(true)
                        binding.linearProfile.visibility = View.GONE
                    }
                    is coding.faizal.ecommerce.core.data.Resource.Success -> {
                        if (resource.data != null) {
                            updateShimmerVisibility(false)
                            binding.linearProfile.visibility = View.VISIBLE

                            intentDataUser()
                            navigation(resource.data)
                            setupView(resource.data.name, resource.data.email)

                        }

                    }
                    is coding.faizal.ecommerce.core.data.Resource.Error -> {
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

    private fun navigation(data : coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser){
        binding.apply {
            imgBack.setOnClickListener {
                finish()
//                if (Build.VERSION.SDK_INT >= 34) {
//                    this@MenuActivity.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.slide_down, R.anim.stay)
//                }
            }
            linearProfile.setOnClickListener { navigateToProfile(this@MenuActivity,data = data) }
            linearFavorite.setOnClickListener { navigateToHome(this@MenuActivity, HomeActivity.FAVORITE) }
            linearTransaction.setOnClickListener { navigateToHome(this@MenuActivity, HomeActivity.TRANSACTION) }
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