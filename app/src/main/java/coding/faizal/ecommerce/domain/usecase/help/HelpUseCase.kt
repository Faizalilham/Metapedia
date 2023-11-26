package coding.faizal.ecommerce.domain.usecase.help

import coding.faizal.ecommerce.domain.model.local.help.Help

interface HelpUseCase {

    fun getAllData() : List<Help>
}