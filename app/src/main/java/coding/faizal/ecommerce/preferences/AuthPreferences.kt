package coding.faizal.ecommerce.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import coding.faizal.ecommerce.preferences.AuthPreferences.Companion.authName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.token : DataStore<Preferences> by preferencesDataStore(name = authName)
class AuthPreferences(@ApplicationContext private val context: Context) {

    private val tokenKey = stringPreferencesKey("tokenKey")

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

    suspend fun deleteToken(){
        context.token.edit {
            it.clear()
        }
    }

    companion object{
        const val authName = "token"
    }
}