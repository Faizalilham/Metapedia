package coding.faizal.ecommerce.preferences

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthPreferencesViewModel @Inject constructor(@ApplicationContext context : Context) : ViewModel() {

    private val authPreferences = AuthPreferences(context)


    fun setToken(token : String){
        viewModelScope.launch {
            authPreferences.setToken(token)
        }
    }

    fun getToken () = authPreferences.getToken().asLiveData()


    fun setIsLogin(){
        viewModelScope.launch {
            authPreferences.setIsLogin()
        }
    }

    fun getIsLogin () = authPreferences.getIsLogin().asLiveData()

    fun deleteIsLogin(){
        viewModelScope.launch {
            authPreferences.deleteIsLogin()
        }
    }
}