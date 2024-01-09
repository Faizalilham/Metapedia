package coding.faizal.ecommerce.domain.model.remote.profileuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileUser (
    val id : Int,
    val name : String,
    val email : String,
    val addresses : List<UserAddress>,
    val isAdmin : Boolean,
    val isVerified : Boolean,
        ): Parcelable

