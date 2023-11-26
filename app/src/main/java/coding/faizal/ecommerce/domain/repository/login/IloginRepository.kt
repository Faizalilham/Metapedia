package coding.faizal.ecommerce.domain.repository.login

interface IloginRepository {

    fun doLogin(email : String,password : String)
}