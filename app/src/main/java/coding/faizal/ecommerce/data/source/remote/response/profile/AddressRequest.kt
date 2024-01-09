package coding.faizal.ecommerce.data.source.remote.response.profile

import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress

data class AddressRequest(
    val addresses : List<UserAddress>
)
