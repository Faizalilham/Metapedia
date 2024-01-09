package coding.faizal.ecommerce.domain.usecase.forgetPassword.impl

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ForgetPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.NewPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ResetPassword
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.repository.forgetpassword.IForgetPasswordRepository
import coding.faizal.ecommerce.domain.usecase.forgetPassword.ForgetPasswordUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgetPasswordUseCaseImpl @Inject constructor(
private val IForgetPasswordRepository : IForgetPasswordRepository
) : ForgetPasswordUseCase {
    override fun forgetPassword(email: String): Flow<Resource<ForgetPassword>> = IForgetPasswordRepository.forgetPassword(email)

    override fun createNewPassword(otp: String, password: String): Flow<Resource<NewPassword>> = IForgetPasswordRepository.createNewPassword(otp,password)

    override fun resetPassword(token: String, newPassword: String): Flow<Resource<ResetPassword>> = IForgetPasswordRepository.resetPassword(token,newPassword)
}