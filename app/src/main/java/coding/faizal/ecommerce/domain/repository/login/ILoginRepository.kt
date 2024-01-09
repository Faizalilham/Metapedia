package coding.faizal.ecommerce.domain.repository.login

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.login.User
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {

    fun praLogin(email : String) : Flow<Resource<Any>>
    fun doLogin(email : String,password : String) : Flow<Resource<User>>
}