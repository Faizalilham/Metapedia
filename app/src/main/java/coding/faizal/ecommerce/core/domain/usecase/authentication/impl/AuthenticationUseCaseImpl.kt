package coding.faizal.ecommerce.core.domain.usecase.authentication.impl

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.authentication.login.User
import coding.faizal.ecommerce.core.domain.model.remote.authentication.register.Register
import coding.faizal.ecommerce.core.domain.repository.authentication.IAuthenticationRepository
import coding.faizal.ecommerce.core.domain.usecase.authentication.AuthenticationUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationUseCaseImpl @Inject constructor(
    private val IAuthenticationRepository : IAuthenticationRepository
) : AuthenticationUseCase {
    override fun praLogin(email: String): Flow<Resource<Any>> {
       return IAuthenticationRepository.praLogin(email)
    }

    override fun doLogin(email: String, password: String): Flow<Resource<User>> {
        return IAuthenticationRepository.doLogin(email,password)
    }

    override fun praRegister(email: String): Flow<Resource<Register>> {
        return IAuthenticationRepository.praRegister(email)
    }

    override fun doRegister(
        email: String,
        name: String,
        password: String
    ): Flow<Resource<User>> {
        return IAuthenticationRepository.doRegister(email,name,password)
    }
}