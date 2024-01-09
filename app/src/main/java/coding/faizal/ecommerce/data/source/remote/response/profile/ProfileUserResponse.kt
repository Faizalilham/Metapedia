package coding.faizal.ecommerce.data.source.remote.response.profile

import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress

data class ProfileUserResponse(
    val id : Int,
    val name : String,
    val email : String,
    val addresses : List<UserAddress>,
    val isAdmin : Boolean,
    val isVerified : Boolean,
    val cart : Any,
    val wishlist : Any
)
