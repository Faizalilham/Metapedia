package coding.faizal.ecommerce.core.domain.repository.help

import coding.faizal.ecommerce.core.domain.model.local.help.Help


interface IHelpRepository {

    fun getAllData() : List<Help>
}