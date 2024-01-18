package coding.faizal.ecommerce.core.presentation.ui.cart.screen


import CartListAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coding.faizal.ecommerce.core.presentation.ui.cart.utils.Dummy
import coding.faizal.ecommerce.databinding.ActivityCartBinding
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToHome
import coding.faizal.ecommerce.utils.NavigationUtils.navigateToMenu
import de.starkling.shoppingcart.managers.CartManager


class CartActivity : AppCompatActivity() {
    private var _binding : ActivityCartBinding? = null
    private val binding get() = _binding!!

    private var products  = ArrayList<Dummy>(arrayListOf(
        Dummy("","",10.0f,false),
        Dummy("","",20.0f,false)
    ))
    private lateinit var cartAdapter: CartListAdapter
    private val currentSelectedItems: MutableList<Dummy> = mutableListOf()
    private lateinit var cart: CartManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cart = CartManager.getInstance(applicationContext)
        setRecycler()
        checkAll()
        binding.imgBack.setOnClickListener {  finish() }
        headerIntent()

    }

    private fun headerIntent(){
        binding.imgMenu.setOnClickListener { navigateToMenu(this, CART, CART) }
        binding.imgFavorite.setOnClickListener { navigateToHome(this,"favorite") }
    }

    private fun checkAll(){
        binding.cbAll.setOnCheckedChangeListener { _, b ->
            if(b) {
                cartAdapter.selectAll()
                currentSelectedItems.clear()
                currentSelectedItems.addAll(products)
                showCollapse()

            }else{
                hiddenCollapse()

            }
        }
    }

    private fun showCollapse(){
        if(currentSelectedItems.size > 0){
            binding.expandableLayout.expand()

        }

        binding.cbAll.isChecked = currentSelectedItems.size == products.size
        binding.productCount.text = "${currentSelectedItems.size}"
    }

    private fun hiddenCollapse(){
        if(currentSelectedItems.size == 0){
            binding.expandableLayout.collapse()

        }
        binding.cbAll.isChecked = currentSelectedItems.size == products.size
        binding.productCount.text = "${currentSelectedItems.size}"
    }

    private fun setRecycler(){
        cartAdapter = CartListAdapter(products,object : CartListAdapter.OnItemCheckListener{
            override fun onItemCheck(item: Dummy) {
              currentSelectedItems.add(item)
                showCollapse()
                binding.tvTotalPrice.text = "${currentSelectedItems.size * item.getPrice() }"
            }

            override fun onItemUncheck(item: Dummy) {
                currentSelectedItems.remove(item)
                hiddenCollapse()

            }

        })

        binding.rvCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }



        cart.subscribeCartTotal().observe(this, Observer {
            binding.apply {

                val price = "Rp${it.totalAmount}"
                tvTotalPrice.text = price
            }
        })

    }

    companion object{
        const val CART = "cart"
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}