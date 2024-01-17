package coding.faizal.ecommerce.core.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coding.faizal.ecommerce.R

class CarouselAdapter(private val imageList: ArrayList<Int>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<CarouselAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.carousel_image_layout_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if (position == imageList.size - 1) {
            viewPager2.setCurrentItem(0, false)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}