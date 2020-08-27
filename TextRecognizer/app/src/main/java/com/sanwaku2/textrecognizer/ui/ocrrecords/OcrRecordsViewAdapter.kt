package com.sanwaku2.textrecognizer.ui.ocrrecords

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sanwaku2.textrecognizer.R
import com.sanwaku2.textrecognizer.databinding.RecyclerViewGridItemBinding

class OcrRecordsViewAdapter(
    private val viewModel: OcrRecordsViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<OcrRecordsViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerViewGridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return viewModel.ocrRecords.value?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<RecyclerViewGridItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycler_view_grid_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = viewModel
        holder.binding.position = position
        holder.binding.lifecycleOwner = lifecycleOwner
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, url: String) {
    val resources = view.context.resources
    val progress = CircularProgressDrawable(view.context)
    progress.strokeWidth =
        resources.getDimensionPixelSize(R.dimen.ocr_item_image_progress_stroke_Width).toFloat()
    progress.centerRadius =
        resources.getDimensionPixelSize(R.dimen.ocr_item_image_progress_center_rad).toFloat()

    progress.start()

    Glide.with(view.context)
        .load(url)
        .placeholder(progress)
        .override(resources.getDimensionPixelSize(R.dimen.ocr_item_image_size))
        .centerCrop()
        .into(view)
}