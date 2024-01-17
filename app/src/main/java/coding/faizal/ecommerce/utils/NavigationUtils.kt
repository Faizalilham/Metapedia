package coding.faizal.ecommerce.utils

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import coding.faizal.ecommerce.R
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.presentation.ui.addaddress.screen.AddAddressActivity
import coding.faizal.ecommerce.presentation.ui.address.screen.AddressActivity
import coding.faizal.ecommerce.presentation.ui.baseedit.screen.BaseEditActivity
import coding.faizal.ecommerce.presentation.ui.cart.screen.CartActivity
import coding.faizal.ecommerce.presentation.ui.category.screen.CategoryActivity
import coding.faizal.ecommerce.presentation.ui.done.DoneActivity
import coding.faizal.ecommerce.presentation.ui.editprofile.screen.EditProfileActivity
import coding.faizal.ecommerce.presentation.ui.forgetpassword.screen.ForgetPasswordActivity
import coding.faizal.ecommerce.presentation.ui.help.screen.HelpActivity
import coding.faizal.ecommerce.presentation.ui.history.screen.HistorySearchActivity
import coding.faizal.ecommerce.presentation.ui.home.screen.HomeActivity
import coding.faizal.ecommerce.presentation.ui.home.screen.HomeFragment
import coding.faizal.ecommerce.presentation.ui.login.screen.LoginActivity
import coding.faizal.ecommerce.presentation.ui.menu.screen.MenuActivity
import coding.faizal.ecommerce.presentation.ui.newpassword.screen.NewPasswordActivity
import coding.faizal.ecommerce.presentation.ui.pralogin.screen.PraLoginActivity
import coding.faizal.ecommerce.presentation.ui.praregister.screen.PraRegisterActivity
import coding.faizal.ecommerce.presentation.ui.profile.screen.ProfileActivity
import coding.faizal.ecommerce.presentation.ui.register.screen.RegisterActivity
import coding.faizal.ecommerce.presentation.ui.search.screen.SearchActivity
import coding.faizal.ecommerce.presentation.ui.verification.screen.VerificationActivity

object NavigationUtils {
    fun navigateToCart(activity: Activity) {
        val intent = Intent(activity, CartActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToSearch(activity: Activity, key : String = HomeFragment.HOME_DATA, data : String? = null) {
        val intent = Intent(activity, SearchActivity::class.java)
        if(data != null){
            intent.putExtra(key,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToMenu(activity: Activity, key : String = HomeActivity.HOME, data : String? = null) {
        val intent = Intent(activity, MenuActivity::class.java)
        if(data != null){
            intent.putExtra(key,data)
        }
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_up, R.anim.stay)
    }

    fun navigateToCategory(activity: Activity,data : String) {
        val intent = Intent(activity, CategoryActivity::class.java)
        intent.putExtra("category",data)
        activity.startActivity(intent)
    }

    fun navigateToPraLogin(activity: Activity,data : String? = null) {
        val intent = Intent(activity, PraLoginActivity::class.java)
        if(data != null){
            intent.putExtra(PraRegisterActivity.DATA_REGISTER,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToLogin(activity: Activity, data : String?) {
        val intent = Intent(activity, LoginActivity::class.java)
        if(data != null){
            intent.putExtra(PraLoginActivity.EXTRA_EMAIL,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToRegister(activity: Activity,data : String? = null) {
        val intent = Intent(activity, RegisterActivity::class.java)
        if(data != null){
            intent.putExtra(RegisterActivity.DATA_REGISTER,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToPraRegister(activity: Activity, data : String? = null) {
        val intent = Intent(activity, PraRegisterActivity::class.java)
        if(data != null){
            intent.putExtra(PraRegisterActivity.DATA_REGISTER,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToHistorySearch(activity: Activity) {
        val intent = Intent(activity, HistorySearchActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToHome(activity: Activity,data : String? = null) {
        val intent = Intent(activity, HomeActivity::class.java)
        if(data != null){
            intent.putExtra(HomeActivity.FAVORITE,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToProfile(activity: Activity, key : String? = MenuActivity.MENU, data : coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser? = null) {
        val intent = Intent(activity, ProfileActivity::class.java)
        if(data != null){
            intent.putExtra(key,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToForgetPassword(activity: Activity) {
        val intent = Intent(activity, ForgetPasswordActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToNewPassword(activity: Activity,key : String,otp : String? = null) {
        val intent = Intent(activity, NewPasswordActivity::class.java)
        if(otp != null){
            intent.putExtra(key,otp)
        }
        activity.startActivity(intent)
    }

    inline fun <reified T : Parcelable> navigateToVerification(activity: Activity,key : String, data: T? = null,isRegister : Boolean = true) {
        val intent = Intent(activity, VerificationActivity::class.java)
        if (data != null) {
            intent.putExtra(key, data)
            intent.putExtra("isRegister",isRegister)
        }
        activity.startActivity(intent)
    }


    fun navigateToDone(activity: Activity) {
        val intent = Intent(activity, DoneActivity::class.java)
        activity.startActivity(intent)

    }

    fun navigateToHelp(activity: Activity) {
        val intent = Intent(activity, HelpActivity::class.java)
        activity.startActivity(intent)

    }

    fun navigateToAddress(activity: Activity) {
        val intent = Intent(activity, AddressActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToBase(activity: Activity, key : String? = ProfileActivity.PROFILE_DATA, data : String? = null) {
        val intent = Intent(activity, BaseEditActivity::class.java)
        if(data != null){
            intent.putExtra(key,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToEditProfile(activity: Activity, key : String? = ProfileActivity.PROFILE_DATA, data : coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser? = null) {
        val intent = Intent(activity, EditProfileActivity::class.java)
        if(data != null){
            intent.putExtra(key,data)
        }
        activity.startActivity(intent)
    }

    fun navigateToAddAddress(activity: Activity) {
        val intent = Intent(activity, AddAddressActivity::class.java)
        activity.startActivity(intent)
    }

}