package coding.faizal.ecommerce.domain.repository.help

import coding.faizal.ecommerce.domain.model.local.help.Help

interface IHelpRepository {

    fun getAllData() : List<Help>
}