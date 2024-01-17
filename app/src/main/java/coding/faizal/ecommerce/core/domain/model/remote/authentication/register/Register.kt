package coding.faizal.ecommerce.core.domain.model.remote.authentication.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    val email : String,
    val otp : String
):Parcelable
