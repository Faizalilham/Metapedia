package coding.faizal.ecommerce.domain.usecase.help.impl

import coding.faizal.ecommerce.domain.model.local.help.Help
import coding.faizal.ecommerce.domain.repository.help.IHelpRepository
import coding.faizal.ecommerce.domain.usecase.help.HelpUseCase
import javax.inject.Inject

class HelpUseCaseImpl @Inject constructor(private val IHelpRepository : IHelpRepository) : HelpUseCase  {
    override fun getAllData(): List<Help>  = IHelpRepository.getAllData()

}