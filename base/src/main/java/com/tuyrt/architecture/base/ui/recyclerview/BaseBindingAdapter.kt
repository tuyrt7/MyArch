package com.tuyrt.architecture.base.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *  基于DataBinding的RecyclerView Adapter封装
 */
abstract class BaseBindingAdapter<B : ViewDataBinding, T> : BaseAdapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding: B = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )
        return BaseViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItem(holder, getItems()!![position], position)
    }

    override fun onBindItem(holder: BaseViewHolder, item: T, position: Int) {
        val binding: B = DataBindingUtil.getBinding(holder.itemView)!!
        onBindItem(binding, getItems()!![position], position)
        binding.executePendingBindings()
        getItemClickListener()?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.onItemClick(
                    binding,
                    getItems()!![position],
                    position
                )
            }
        }
    }

    abstract fun onBindItem(binding: B?, item: T, position: Int)
}