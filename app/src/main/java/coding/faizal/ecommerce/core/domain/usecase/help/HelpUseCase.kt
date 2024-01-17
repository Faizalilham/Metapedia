package coding.faizal.ecommerce.core.domain.usecase.help

import coding.faizal.ecommerce.core.domain.model.local.help.Help

interface HelpUseCase {

    fun getAllData() : List<coding.faizal.ecommerce.core.domain.model.local.help.Help>
}