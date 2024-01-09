package coding.faizal.ecommerce.data.source.remote.network.auth

import coding.faizal.ecommerce.data.source.remote.response.SingleResponse
import coding.faizal.ecommerce.data.source.remote.response.forgetPassword.*
import coding.faizal.ecommerce.data.source.remote.response.login.LoginRequest
import coding.faizal.ecommerce.data.source.remote.response.login.LoginResponse
import coding.faizal.ecommerce.data.source.remote.response.login.PraLoginRequest
import coding.faizal.ecommerce.data.source.remote.response.profile.AddressRequest
import coding.faizal.ecommerce.data.source.remote.response.profile.EditProfileRequest
import coding.faizal.ecommerce.data.source.remote.response.profile.ProfileUserResponse
import coding.faizal.ecommerce.data.source.remote.response.register.*
import coding.faizal.ecommerce.data.source.remote.response.verification.VerificationRequest
import coding.faizal.ecommerce.data.source.remote.response.verification.VerificationResponse
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import retrofit2.http.*

interface ApiAuthService {

    @POST("user/otp/send")
    suspend fun praRegister(@Body registerRequest: RegisterRequest) : SingleResponse<RegisterResponse>

    @POST("user/otp/verify")
    suspend fun verificationOtp(@Body verificationRequest: VerificationRequest) : SingleResponse<VerificationResponse>

    @POST("user/register")
    suspend fun doRegister(
        @Header("Authorization") token : String,
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
    suspend fun getCurrentUser(@Header("Authorization") token : String,) : SingleResponse<ProfileUserResponse>

    @PUT("user/profile/edit")
    suspend fun updateProfileUsername(
        @Header("Authorization") token : String,@Body editProfileRequest: EditProfileRequest) : SingleResponse<Any>

    @PUT("user/profile/edit")
    suspend fun updateProfileAddress(
        @Header("Authorization") token : String,@Body addresses : AddressRequest) : SingleResponse<Any>

    @POST("user/password/reset")
    suspend fun resetPassword(
        @Header("Authorization") token : String,@Body resetPasswordRequest: ResetPasswordRequest) : SingleResponse<ResponseDataUser<ResetPasswordResponse>>
}