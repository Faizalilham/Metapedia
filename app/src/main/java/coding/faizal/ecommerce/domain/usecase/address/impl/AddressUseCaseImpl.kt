package coding.faizal.ecommerce.domain.usecase.address.impl

import coding.faizal.ecommerce.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.domain.repository.address.IAddressRepository
import coding.faizal.ecommerce.domain.usecase.address.AddressUseCase
import javax.inject.Inject

class AddressUseCaseImpl @Inject constructor(private val IAddressRepository : IAddressRepository) : AddressUseCase {
    override fun getAllLabelAddress(): List<LabelAddress> = IAddressRepository.getAllLabelAddress()
}