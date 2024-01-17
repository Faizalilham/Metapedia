package coding.faizal.ecommerce.utils

import coding.faizal.ecommerce.core.data.source.remote.response.authentication.login.LoginResponse
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.praregister.RegisterResponse
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.register.RegisterResponseAfterVerification
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.register.ResponseDataUser
import coding.faizal.ecommerce.core.data.source.remote.response.password.createnew.NewPasswordResponse
import coding.faizal.ecommerce.core.data.source.remote.response.password.forget.ForgetPasswordResponse
import coding.faizal.ecommerce.core.data.source.remote.response.password.reset.ResetPasswordResponse
import coding.faizal.ecommerce.core.data.source.remote.response.product.ProductResponse
import coding.faizal.ecommerce.core.data.source.remote.response.product.payment.PaymentResponse
import coding.faizal.ecommerce.core.data.source.remote.response.user.profile.ProfileUserResponse
import coding.faizal.ecommerce.core.data.source.remote.response.verification.VerificationResponse
import coding.faizal.ecommerce.core.domain.model.remote.authentication.login.User
import coding.faizal.ecommerce.core.domain.model.remote.authentication.register.Register
import coding.faizal.ecommerce.core.domain.model.remote.password.ForgetPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.NewPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.ResetPassword
import coding.faizal.ecommerce.core.domain.model.remote.product.*
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.verify.Verification
import coding.faizal.ecommerce.data.source.remote.response.product.ProductOrderResponse
import coding.faizal.ecommerce.data.source.remote.response.product.WishlistResponse


object DataMapper {

    fun mapFromRegisterResponseToEntities(output : RegisterResponse) : Register {
        return Register(
            output.email,
            output.otp
        )
    }

    fun mapFromVerificationResponseToEntities(output : VerificationResponse) : Verification {
        return Verification(
            output.token,
            output.otpCreatedAt
        )
    }

    fun mapFromRegisterResponseToEntities(output : ResponseDataUser<RegisterResponseAfterVerification>) : User {
        return User(
            output.user.id,
            output.user.name,
            output.user.email,
            output.user.isAdmin,
            output.user.address,
            output.user.token
        )
    }

    fun mapFromLoginResponseToEntities(output : LoginResponse) : User {
        return User(
            0,
            output.name,
            output.email,
            output.isAdmin,
            output.address,
            output.token
        )
    }

    fun mapFromForgetPasswordResponseToEntities(output : ForgetPasswordResponse) : ForgetPassword {
        return ForgetPassword(
            output.email,
            output.otp,
        )
    }

    fun mapFromCreateNewPasswordResponseToEntities(output : NewPasswordResponse) : NewPassword {
        return NewPassword(
            output.id,
            output.name,
            output.email,
            output.isAdmin,
            output.addresses
        )
    }

    fun mapFromResetPasswordResponseToEntities(output : ResetPasswordResponse) : ResetPassword {
        return ResetPassword(
            output.id,
            output.name,
            output.email,
            output.image,
            output.isAdmin,
            output.addresses,

            )
    }

    fun mapFromProfileUserResponseToEntities(output : ProfileUserResponse) : ProfileUser {
        return ProfileUser(
            output.id,
            output.name,
            output.email,
            output.addresses,
            output.isAdmin,
            output.isVerified
        )
    }

    fun mapFromListProductResponseToEntities(output : List<ProductResponse>) : List<Product> {
        val list = mutableListOf<Product>()
        output.map{ product ->
            product.apply {
                val products = Product(
                    images,
                    _id,
                    name,
                    subtitle,
                    description,
                    variants,
                    category,
                    price,
                    featured,
                    imgSrc
                )
                list.add(products)
            }
        }
        return list
    }

    fun mapFromSingleProductResponseToEntities(output : ProductResponse) : Product {
        return Product(
            output.images,
            output._id,
            output.name,
            output.subtitle,
            output.description,
            output.variants,
            output.category,
            output.price,
            output.featured,
            output.imgSrc
        )
    }

    fun mapFromListWishlistResponseToEntities(output : List<WishlistResponse>) : ListWishlist {
        val list = mutableListOf<Wishlist>()
        output.map{ ot ->
            ot.apply {
                val wishlist = Wishlist(
                    product
                )
                list.add(wishlist)
            }
        }
        return ListWishlist(list)
    }

    fun mapFromProductOrderResponseToEntities(output : ProductOrderResponse) : ProductOrder {

        return ProductOrder(
            output.id,
            output.user,
            output.orderItems,
            output.paymentMethod
        )
    }

    fun mapFromProductPaymentResponseToEntities(output : PaymentResponse) : Payment {

        return Payment(
            output.data.token,
            output.data.token
        )
    }


}