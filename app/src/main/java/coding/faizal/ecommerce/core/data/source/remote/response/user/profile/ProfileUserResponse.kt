package coding.faizal.ecommerce.core.data.source.remote.response.user.profile

import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress

data class ProfileUserResponse(
    val id : Int,
    val name : String,
    val email : String,
    val addresses : List<coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress>,
    val isAdmin : Boolean,
    val isVerified : Boolean,
    val cart : Any,
    val wishlist : Any
)
