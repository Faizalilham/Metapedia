package coding.faizal.ecommerce.core.domain.usecase.user.impl

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.domain.repository.user.IUserRepository
import coding.faizal.ecommerce.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val IUserRepository : IUserRepository) :UserUseCase {

    override fun getCurrentUser(token: String): Flow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser>>  = IUserRepository.getCurrentUser(token)

    override fun updateUsername(token: String,username : String): Flow<coding.faizal.ecommerce.core.data.Resource<Any>> = IUserRepository.updateUsername(token,username)

    override fun updateAddress(token: String,address : List<coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress>): Flow<coding.faizal.ecommerce.core.data.Resource<Any>> = IUserRepository.updateAddress(token,address)

    override fun getListAddress(token: String): Flow<coding.faizal.ecommerce.core.data.Resource<List<coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress>>> = IUserRepository.getListAddress(token)

    override fun getAllLabelAddress(): List<coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress> = IUserRepository.getAllLabelAddress()
}