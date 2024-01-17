import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.databinding.ListItemCartBinding
import coding.faizal.ecommerce.databinding.ListItemCartShimmerBinding
import coding.faizal.ecommerce.presentation.ui.cart.utils.Dummy
import de.starkling.shoppingcart.widget.CounterView

class CartListAdapter(private val items: List<Dummy>, private val onItemClick: OnItemCheckListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_ACTUAL = 0
    private val ITEM_SHIMMER = 1

    interface OnItemCheckListener {
        fun onItemCheck(item: Dummy)
        fun onItemUncheck(item: Dummy)
    }

    var isSelectedAll = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            ITEM_ACTUAL -> {
                CartViewHolder(ListItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            ITEM_SHIMMER -> {
                CartShimmerViewHolder(ListItemCartShimmerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if(holder is CartViewHolder){

           val currentItem = items[position]

           holder.binding.counterView.counterValue = 1
           holder.binding.counterView.addCounterValueChangeListener(object : CounterView.CounterValueChangeListener{

               override fun onValueDelete(count: Int) {
                   currentItem.itemQuantity = count

               }

               override fun onValueAdd(count: Int) {
                   currentItem.itemQuantity = count
               }
           })

           holder.binding.cbProduct.isChecked = isSelectedAll



           holder.itemView.setOnClickListener {
               holder.binding.cbProduct.isChecked = !holder.binding.cbProduct.isChecked
               if (holder.binding.cbProduct.isChecked) {
                   onItemClick.onItemCheck(currentItem)
               } else {
                   onItemClick.onItemUncheck(currentItem)
               }

           }
       }else if(holder is CartShimmerViewHolder){
            holder.binding.shimmerViewContainer.startShimmer()
       }
    }

    fun selectAll() {
        isSelectedAll = true
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return if (items.isNotEmpty()) {
            ITEM_ACTUAL
        } else {
            ITEM_SHIMMER
        }
    }

    override fun getItemCount(): Int  = if(items.isEmpty()) 12 else items.size

    inner class CartViewHolder(val binding : ListItemCartBinding) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.cbProduct.isClickable = false

        }
    }

    inner class CartShimmerViewHolder(val binding : ListItemCartShimmerBinding) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.shimmerViewContainer.startShimmer()

        }
    }


}
