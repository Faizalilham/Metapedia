package coding.faizal.ecommerce.core.presentation.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import coding.faizal.ecommerce.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SlideImageAdapter(private val context: Context) :
    SliderViewAdapter<SlideImageAdapter.SliderAdapterVH>() {

    private var mSliderItems: List<Int> = listOf(
        R.drawable.image_slider_one,
        R.drawable.image_slider_second,
    )


    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem = mSliderItems[position]

        viewHolder.imageViewBackground.setImageResource(sliderItem)

        viewHolder.itemView.setOnClickListener {
            Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) :
        SliderViewAdapter.ViewHolder(itemView) {

        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
    }
}

