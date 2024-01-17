package coding.faizal.ecommerce.core.domain.usecase.authentication

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.authentication.login.User
import coding.faizal.ecommerce.core.domain.model.remote.authentication.register.Register
import kotlinx.coroutines.flow.Flow

interface AuthenticationUseCase {

    fun praLogin(email : String) : Flow<Resource<Any>>

    fun doLogin(email : String,password : String) : Flow<Resource<User>>

    fun praRegister(email : String) : Flow<Resource<Register>>

    fun doRegister(token : String,email : String,name :String, password : String) : Flow<Resource<User>>
}