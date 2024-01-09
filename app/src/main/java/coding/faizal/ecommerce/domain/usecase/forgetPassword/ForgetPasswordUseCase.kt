package coding.faizal.ecommerce.domain.usecase.forgetPassword

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ForgetPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.NewPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ResetPassword
import coding.faizal.ecommerce.domain.model.remote.login.User
import kotlinx.coroutines.flow.Flow

interface ForgetPasswordUseCase {

    fun forgetPassword(email : String) : Flow<Resource<ForgetPassword>>

    fun createNewPassword(otp : String, password : String) : Flow<Resource<NewPassword>>

    fun resetPassword(token : String,newPassword : String) : Flow<Resource<ResetPassword>>
}