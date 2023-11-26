package coding.faizal.ecommerce.domain.usecase.login

import coding.faizal.ecommerce.domain.repository.login.IloginRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val iLoginRepository: IloginRepository) : LoginUsecase {

    override fun doLogin(email: String, password: String) {
        iLoginRepository.doLogin(email, password)
    }

}