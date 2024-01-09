package coding.faizal.ecommerce.data.source.local.repository.address

import coding.faizal.ecommerce.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.domain.repository.address.IAddressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepository @Inject constructor() : IAddressRepository {

    override fun getAllLabelAddress(): List<LabelAddress> {
        return listOf(
            LabelAddress(
                1,
                "Rumah",
                false,
            ),
            LabelAddress(
                2,
                "Apartemen",
                false,
            ),
            LabelAddress(
                3,
                "Kantor",
                false,
            ),
            LabelAddress(
                4,
                "Kos",
                false,
            ),
        )
    }
}