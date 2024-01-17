package coding.faizal.ecommerce.core.domain.repository.forgetpassword

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.password.ForgetPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.NewPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.ResetPassword
import kotlinx.coroutines.flow.Flow

interface IForgetPasswordRepository {

    fun forgetPassword(email : String) : Flow<Resource<ForgetPassword>>

    fun createNewPassword(otp : String, password : String) : Flow<Resource<NewPassword>>

    fun resetPassword(token : String,newPassword : String) : Flow<Resource<ResetPassword>>
}