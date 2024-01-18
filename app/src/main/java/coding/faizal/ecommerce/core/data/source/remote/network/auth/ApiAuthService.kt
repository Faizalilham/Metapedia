package coding.faizal.ecommerce.core.data.source.remote.network.auth


import coding.faizal.ecommerce.core.data.source.remote.response.SingleResponse
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.login.LoginRequest
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.login.LoginResponse
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.pralogin.PraLoginRequest
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.praregister.RegisterRequest
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.praregister.RegisterResponse
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.register.RegisterRequestAfterVerification
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.register.RegisterResponseAfterVerification
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.register.ResponseDataUser
import coding.faizal.ecommerce.core.data.source.remote.response.password.createnew.NewPasswordRequest
import coding.faizal.ecommerce.core.data.source.remote.response.password.createnew.NewPasswordResponse
import coding.faizal.ecommerce.core.data.source.remote.response.password.forget.ForgetPasswordRequest
import coding.faizal.ecommerce.core.data.source.remote.response.password.forget.ForgetPasswordResponse
import coding.faizal.ecommerce.core.data.source.remote.response.password.reset.ResetPasswordRequest
import coding.faizal.ecommerce.core.data.source.remote.response.password.reset.ResetPasswordResponse
import coding.faizal.ecommerce.core.data.source.remote.response.user.address.AddressRequest
import coding.faizal.ecommerce.core.data.source.remote.response.user.profile.EditProfileRequest
import coding.faizal.ecommerce.core.data.source.remote.response.user.profile.ProfileUserResponse
import coding.faizal.ecommerce.core.data.source.remote.response.verification.VerificationRequest
import coding.faizal.ecommerce.core.data.source.remote.response.verification.VerificationResponse
import retrofit2.http.*

interface ApiAuthService {

    @POST("user/otp/send")
    suspend fun praRegister(@Body registerRequest: RegisterRequest) : SingleResponse<RegisterResponse>

    @POST("user/otp/verify")
    suspend fun verificationOtp(@Body verificationRequest: VerificationRequest) : SingleResponse<VerificationResponse>

    @POST("user/register")
    suspend fun doRegister(
        @Body registerRequestAfterVerification: RegisterRequestAfterVerification,
    ) : SingleResponse<ResponseDataUser<RegisterResponseAfterVerification>>

    @POST("user/login")
    suspend fun doLogin(@Body loginRequest: LoginRequest) : SingleResponse<ResponseDataUser<LoginResponse>>

    @POST("user/check-email")
    suspend fun praLogin(@Body loginRequest: PraLoginRequest) : SingleResponse<Any>

    @POST("user/password/forgot")
    suspend fun forgetPassword(@Body forgetPassword: ForgetPasswordRequest) : SingleResponse<ForgetPasswordResponse>

    @POST("user/password/create-new")
    suspend fun createNewPassword(@Body newPasswordRequest: NewPasswordRequest) : SingleResponse<ResponseDataUser<NewPasswordResponse>>

    @GET("user/profile")
    suspend fun getCurrentUser() : SingleResponse<ProfileUserResponse>

    @PUT("user/profile/edit")
    suspend fun updateProfileUsername(@Body editProfileRequest: EditProfileRequest
    ) : SingleResponse<Any>

    @PUT("user/profile/edit")
    suspend fun updateProfileAddress(@Body addresses : AddressRequest
    ) : SingleResponse<Any>

    @POST("user/password/reset")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest
    ) : SingleResponse<ResponseDataUser<ResetPasswordResponse>>
}