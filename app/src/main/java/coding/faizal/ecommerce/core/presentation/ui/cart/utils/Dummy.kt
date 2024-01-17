package coding.faizal.ecommerce.core.presentation.ui.cart.utils

import de.starkling.shoppingcart.models.Saleable

data class Dummy(private var id:String,private var name:String,private var price:Float,var isChecked : Boolean) : Saleable {

    override var itemQuantity: Int = 1

    override fun getId(): String {
        return id
    }

    override fun getName(): String {
        return name
    }

    override fun getPrice(): Float {
        return price
    }

    override fun getQuantity(): Int {
        return itemQuantity
    }

    override fun getTotal(): Float {
        return price * itemQuantity
    }


}
