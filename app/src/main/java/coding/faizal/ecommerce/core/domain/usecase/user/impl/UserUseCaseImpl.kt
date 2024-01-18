package coding.faizal.ecommerce.core.domain.usecase.user.impl

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.core.domain.repository.user.IUserRepository
import coding.faizal.ecommerce.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val IUserRepository : IUserRepository) :
    UserUseCase {

    override fun getCurrentUser(): Flow<Resource<ProfileUser>>  = IUserRepository.getCurrentUser()

    override fun updateUsername(username : String): Flow<Resource<Any>> = IUserRepository.updateUsername(username)

    override fun updateAddress(address : List<UserAddress>): Flow<Resource<Any>> = IUserRepository.updateAddress(address)

    override fun getListAddress(): Flow<Resource<List<UserAddress>>> = IUserRepository.getListAddress()

    override fun getAllLabelAddress(): List<LabelAddress> = IUserRepository.getAllLabelAddress()
}