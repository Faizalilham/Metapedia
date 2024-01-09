package coding.faizal.ecommerce.domain.repository.address

import coding.faizal.ecommerce.domain.model.local.address.LabelAddress

interface IAddressRepository {

    fun getAllLabelAddress() : List<LabelAddress>
}