package com.tuyrt.myarch.test.binding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hi.dhl.binding.databind
import com.hi.dhl.binding.viewbind
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.RecycleItemProductBinding
import com.tuyrt.myarch.databinding.RecycleItemProductFooterBinding
import com.tuyrt.myarch.databinding.RecycleItemProductHeaderBinding
import com.tuyrt.myarch.test.binding.model.Product


/**
 *  这是 DataBinging 和 ViewBinding在 Adapter 中的使用例子，
 *  通过扩展 RecyclerView.ViewHolder 来使用 DataBinding 和 ViewBinding，
 *  即所有与 RecyclerView.ViewHolder 相关的 Adapter 都可以使用（ListAdapter、PagingDataAdapter、RecyclerView.Adapter 等等）
 */
class ProductAdapter : ListAdapter<Product, RecyclerView.ViewHolder>(Product.CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflateView(parent, viewType)
        return when (viewType) {
            R.layout.recycle_item_product_header -> ProductViewHolderHeader(view)
            R.layout.recycle_item_product_footer -> ProductViewHolderFooter(view)
            else -> ProductViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if (holder is ProductViewHolder) {
            holder.bindData(data, position)
        }

        when {
            holder is ProductViewHolderHeader -> holder.bindData(data, position)
            holder is ProductViewHolderFooter -> holder.bindData(null, position)
            holder is ProductViewHolder -> {
                val data = getItem(position)
                holder.bindData(data, position)
            }
        }

    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> R.layout.recycle_item_product_header
        itemCount - 1 -> R.layout.recycle_item_product_footer
        else -> R.layout.recycle_item_product
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }
}

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: RecycleItemProductBinding by databind()

    fun bindData(data: Product?, position: Int) {
        binding.apply {
            product = data
            executePendingBindings()
        }
    }
}


class ProductViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {

    val binding: RecycleItemProductHeaderBinding by viewbind()

    fun bindData(data: Product?, position: Int) {
        binding.apply {
            name.text = "通过 ViewBinding 绑定的 head"
        }
    }
}

class ProductViewHolderFooter(view: View) : RecyclerView.ViewHolder(view) {

    val binding: RecycleItemProductFooterBinding by viewbind()

    fun bindData(data: Product?, position: Int) {
        binding.apply {
            name.text = "通过 ViewBinding 绑定的 footer"
        }
    }
}

