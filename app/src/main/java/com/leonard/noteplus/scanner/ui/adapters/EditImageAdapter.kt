package com.leonard.noteplus.scanner.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.leonard.noteplus.R
import com.leonard.noteplus.scanner.data.Document
import kotlinx.android.synthetic.main.edit_image_adapter_item.view.*

class EditImageAdapter(var ctx: Context, var listener:EditImageListener, var glide: RequestManager) : RecyclerView.Adapter<EditImageAdapter.EditImageViewHolder>(){

    private var data:List<Document>?=null


    interface EditImageListener{
        fun onImageClicked(document: Document)
    }

    fun setData(list: List<com.itextpdf.text.Document>){
        data = list
        notifyDataSetChanged()
    }

    fun setCurrentPosition(position:Int){
        selectedPosition = position
        notifyDataSetChanged()
    }

    private fun convertToDp(size:Int): Int {
        val scale = ctx.resources.displayMetrics.density
        return (size * scale + 0.5f).toInt()
    }

    inner class EditImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditImageViewHolder  =
        EditImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.edit_image_adapter_item, parent, false)
        )

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: EditImageViewHolder, position: Int) {

        val item = data?.get(position)

        holder.itemView.apply {
            glide.apply {
                load(item?.bitmap)
                    .into(imgEditItem)
            }

            if (position == selectedPosition){
                imgEditItem.background = ctx.resources.getDrawable(R.drawable.edit_image_selected_background)
                imgEditItem.setPadding(convertToDp(5),convertToDp(5), convertToDp(5), convertToDp(5))
            } else{
                imgEditItem.background = null
                imgEditItem.setPadding(convertToDp(0),convertToDp(0), convertToDp(0), convertToDp(0))
            }

            setOnClickListener {
                item?.let { it1 ->
                    listener.onImageClicked(it1)
                }
                selectedPosition = position
                notifyDataSetChanged()
            }



        }

    }

    companion object{
        var selectedPosition = 0
    }
}