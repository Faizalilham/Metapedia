package coding.faizal.ecommerce.domain.model.local.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LabelAddress(
    val id : Int,
    val name : String,
    var isSelected : Boolean
):Parcelable
