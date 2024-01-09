package coding.faizal.ecommerce.domain.usecase.profile.impl

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.domain.repository.profile.IProfileRepository
import coding.faizal.ecommerce.domain.usecase.profile.ProfileUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(private val IProfileRepository : IProfileRepository) :ProfileUseCase {

    override fun getCurrentUser(token: String): Flow<Resource<ProfileUser>>  = IProfileRepository.getCurrentUser(token)

    override fun updateUsername(token: String,username : String): Flow<Resource<Any>> = IProfileRepository.updateUsername(token,username)

    override fun updateAddress(token: String,address : List<UserAddress>): Flow<Resource<Any>> = IProfileRepository.updateAddress(token,address)

    override fun getListAddress(token: String): Flow<Resource<List<UserAddress>>> = IProfileRepository.getListAddress(token)
}