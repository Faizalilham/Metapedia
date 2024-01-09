package coding.faizal.ecommerce.domain.model.remote.forgetpassword

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResetPassword (
    val id : String,
    val name : String,
    val email : String,
    val image : String,
    val isAdmin : Boolean,
    val addresses : List<String>,
): Parcelable
