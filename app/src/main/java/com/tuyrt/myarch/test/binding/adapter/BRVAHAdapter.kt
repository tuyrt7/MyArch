package com.tuyrt.myarch.test.binding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hi.dhl.binding.viewbind
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.RecycleItemProductBinding
import com.tuyrt.myarch.test.binding.model.Product

/**
 * Created by tuyrt7 on 2021/12/27.
 * 说明：
 */
class BRVAHAdapter : BaseQuickAdapter<Product, BRVAHViewHolder>(0) {
    override fun convert(holder: BRVAHViewHolder, item: Product) {
        holder.bindData(item)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BRVAHViewHolder {
        val view = inflateView(parent, viewType)
        return BRVAHViewHolder(view)
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }

    override fun getDefItemViewType(position: Int): Int = R.layout.recycle_item_product
}


class BRVAHViewHolder(view: View) : BaseViewHolder(view) {
    val binding: RecycleItemProductBinding by viewbind()

    fun bindData(data: Product) {
        binding.apply {
            product = data
            executePendingBindings()
        }
    }

}