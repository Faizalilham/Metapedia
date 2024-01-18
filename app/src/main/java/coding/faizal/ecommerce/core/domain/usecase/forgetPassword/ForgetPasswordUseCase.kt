package coding.faizal.ecommerce.core.domain.usecase.forgetPassword

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.password.ForgetPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.NewPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.ResetPassword
import kotlinx.coroutines.flow.Flow

interface ForgetPasswordUseCase {

    fun forgetPassword(email : String) : Flow<Resource<ForgetPassword>>

    fun createNewPassword(otp : String, password : String) : Flow<Resource<NewPassword>>

    fun resetPassword(newPassword : String) : Flow<Resource<ResetPassword>>
}