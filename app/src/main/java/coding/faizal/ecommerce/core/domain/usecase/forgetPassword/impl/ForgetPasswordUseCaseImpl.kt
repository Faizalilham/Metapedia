package coding.faizal.ecommerce.core.domain.usecase.forgetPassword.impl

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.password.ForgetPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.NewPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.ResetPassword
import coding.faizal.ecommerce.core.domain.repository.forgetpassword.IForgetPasswordRepository
import coding.faizal.ecommerce.core.domain.usecase.forgetPassword.ForgetPasswordUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgetPasswordUseCaseImpl @Inject constructor(
private val IForgetPasswordRepository : IForgetPasswordRepository
) : ForgetPasswordUseCase {
    override fun forgetPassword(email: String): Flow<Resource<ForgetPassword>> = IForgetPasswordRepository.forgetPassword(email)

    override fun createNewPassword(otp: String, password: String): Flow<Resource<NewPassword>> = IForgetPasswordRepository.createNewPassword(otp,password)

    override fun resetPassword(token: String, newPassword: String): Flow<Resource<ResetPassword>> = IForgetPasswordRepository.resetPassword(token,newPassword)
}