package coding.faizal.ecommerce.core.domain.model.remote.verify

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Verification(
    val token : String,
    val otpCreatedAt : String
):Parcelable
