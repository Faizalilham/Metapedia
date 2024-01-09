package coding.faizal.ecommerce.domain.usecase.register.impl

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.register.Register
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.repository.register.IRegisterRepository
import coding.faizal.ecommerce.domain.usecase.register.RegisterUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(private val IRegisterRepository : IRegisterRepository
): RegisterUseCase {
    override fun postEmailRegister(email : String): Flow<Resource<Register>> {
        return IRegisterRepository.postEmailRegister(email)
    }

    override fun doRegister(token : String, email: String, name: String, password: String): Flow<Resource<User>> {
        return IRegisterRepository.doRegister(token,email,name,password)
    }
}