package coding.faizal.ecommerce.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import coding.faizal.ecommerce.preferences.AuthPreferences.Companion.authName
import coding.faizal.ecommerce.preferences.AuthPreferences.Companion.loginCheck
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.token : DataStore<Preferences> by preferencesDataStore(name = authName)
private val Context.loginCheck : DataStore<Preferences> by preferencesDataStore(name = loginCheck)
class AuthPreferences(@ApplicationContext private val context: Context) {

    private val tokenKey = stringPreferencesKey("tokenKey")
    private val loginKey = booleanPreferencesKey("loginKey")

    suspend fun setToken(token :String){
        context.token.edit {
            it[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return context.token.data.map {
            it[tokenKey] ?: "undefined"
        }
    }

    suspend fun setIsLogin(){
        context.loginCheck.edit {
            it[loginKey] = true
        }
    }

    fun getIsLogin(): Flow<Boolean> {
        return context.loginCheck.data.map {
            it[loginKey] ?: false
        }
    }


    suspend fun deleteIsLogin(){
        context.loginCheck.edit {
            it.clear()
        }
    }

    companion object{
        const val authName = "token"
        const val loginCheck = "loginCheck"
    }
}