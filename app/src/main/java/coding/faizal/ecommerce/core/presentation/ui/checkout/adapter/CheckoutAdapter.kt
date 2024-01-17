package coding.faizal.ecommerce.core.presentation.ui.checkout.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coding.faizal.ecommerce.data.source.remote.response.product.OrderItemRequest
import coding.faizal.ecommerce.databinding.ListItemCheckoutBinding
import coding.faizal.ecommerce.databinding.ListItemCheckoutShimmerBinding

class CheckoutAdapter(private var data : List<OrderItemRequest>,private val name : String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_ACTUAL = 0
    private val ITEM_SHIMMER = 1

    inner class CheckoutViewHolder(val binding: ListItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<OrderItemRequest>, position: Int) {
            binding.apply {
                tvProductName.text = name
                tvProductPrice.text = data[position].price
                tvVariant.text = data[position].variant
            }
        }
    }

    inner class CheckoutShimmerViewHolder(val binding: ListItemCheckoutShimmerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun startShimmerEffect() {
            binding.shimmerViewContainer.startShimmer()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_ACTUAL -> {
                CheckoutViewHolder(
                    ListItemCheckoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ITEM_SHIMMER -> {
                CheckoutShimmerViewHolder(
                    ListItemCheckoutShimmerBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CheckoutViewHolder) {
            if (data.isNotEmpty()) {
                holder.bind(data, position)
            }
        } else if (holder is CheckoutShimmerViewHolder) {
            holder.startShimmerEffect()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isNotEmpty()) {
            ITEM_ACTUAL
        } else {
            ITEM_SHIMMER
        }
    }

    override fun getItemCount(): Int = if (data.isEmpty()) 12 else data.size

}