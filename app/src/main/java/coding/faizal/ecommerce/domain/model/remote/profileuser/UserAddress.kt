package coding.faizal.ecommerce.domain.model.remote.profileuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAddress(
    val street : String,
    val city : String,
    val country : String,
    val postalCode : String
):Parcelable
