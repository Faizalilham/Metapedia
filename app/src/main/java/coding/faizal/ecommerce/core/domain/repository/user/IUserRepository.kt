package coding.faizal.ecommerce.core.domain.repository.user

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getCurrentUser() : Flow<Resource<ProfileUser>>

    fun updateUsername(username : String) : Flow<Resource<Any>>

    fun updateAddress(address : List<UserAddress>) :  Flow<Resource<Any>>

    fun getListAddress() : Flow<Resource<List<UserAddress>>>

    fun getAllLabelAddress() : List<LabelAddress>

}