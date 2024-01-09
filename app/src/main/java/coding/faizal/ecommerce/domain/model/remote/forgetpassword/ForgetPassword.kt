package coding.faizal.ecommerce.domain.model.remote.forgetpassword

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ForgetPassword(
    val email : String,
    val otp : String
): Parcelable
