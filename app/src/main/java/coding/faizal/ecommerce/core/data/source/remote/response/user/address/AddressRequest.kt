package coding.faizal.ecommerce.core.data.source.remote.response.user.address

import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress

data class AddressRequest(
    val addresses : List<coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress>
)
