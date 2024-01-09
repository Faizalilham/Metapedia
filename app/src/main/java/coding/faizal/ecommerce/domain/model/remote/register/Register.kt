package coding.faizal.ecommerce.domain.model.remote.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    val email : String,
    val otp : String
):Parcelable
