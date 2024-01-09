package coding.faizal.ecommerce.domain.usecase.address

import coding.faizal.ecommerce.domain.model.local.address.LabelAddress

interface AddressUseCase {

    fun getAllLabelAddress() : List<LabelAddress>
}