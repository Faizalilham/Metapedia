package coding.faizal.ecommerce.domain.usecase.register

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.register.Register
import coding.faizal.ecommerce.domain.model.remote.login.User
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun postEmailRegister(email : String) : Flow<Resource<Register>>

    fun doRegister(token : String, email : String,name :String, password : String) : Flow<Resource<User>>
}