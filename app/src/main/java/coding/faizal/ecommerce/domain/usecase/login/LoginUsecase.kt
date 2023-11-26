package coding.faizal.ecommerce.domain.usecase.login

interface LoginUsecase {

    fun doLogin(email : String,password : String)
}