package coding.faizal.ecommerce.core.domain.model.remote.password

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ForgetPassword(
    val email : String,
    val otp : String
): Parcelable
