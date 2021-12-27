package com.tuyrt.architecture.base.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * @author: tuyrt7 on 2020/11/2.
 * 说明：RecyclerView.Adapter 基类 ( DataBinding )
 */
abstract class BaseSimpleAdapter<B : ViewDataBinding, T>(private val itemLayoutId: Int) : RecyclerView.Adapter<ViewHolder>() {

    private var mDataList = mutableListOf<T>()
    private var mItemClick: ((adapter: BaseSimpleAdapter<*, *>, view: View, position: Int) -> Unit)? = null

    fun setOnItemClickListener(block: (adapter: BaseSimpleAdapter<*, *>, view: View, position: Int) -> Unit) {
        this.mItemClick = block
    }

    fun getItems(): MutableList<T> {
        return mDataList
    }

    @JvmOverloads
    fun setItems(dataList: List<T>?, notify: Boolean = true) {
        mDataList.clear()
        if (!dataList.isNullOrEmpty()) {
            mDataList.addAll(dataList)
        }
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun addItems(dataList: MutableList<T>, notify: Boolean) {
        val start = mDataList.size
        dataList.forEach { item ->
            mDataList.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, mDataList.size)
        }
    }

    fun refreshItem(item: T) {
        val index = mDataList.indexOf(item)
        if (index >= 0) {
            notifyItemChanged(index)
        }
    }

    fun refreshItem(position: Int) {
        if (position >= 0 && position < mDataList.size) {
            notifyItemChanged(position)
        }
    }

    fun addItem(item: T) {
        addItemAt(-1, item, true)
    }

    fun addItemAt(index: Int = -1, item: T, notify: Boolean) {
        if (index > 0) {
            mDataList.add(index, item)
        } else {
            mDataList.add(item)
        }
        val notifyPos = if (index > 0) index else mDataList.size - 1
        if (notify) {
            notifyItemChanged(notifyPos)
        }
    }

    fun removeItemAt(index: Int): T? {
        return if (index >= 0 && index < mDataList.size) {
            val remove = mDataList.removeAt(index)
            notifyItemRemoved(index)
            remove
        } else {
            null
        }
    }

    fun removeItem(item: T) {
        val index = mDataList.indexOf(item)
        removeItemAt(index)
    }

    fun clear() {
        mDataList.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: T = getItem(position)

        // 绑定数据
        getBinding(holder.itemView)?.let {
            onBindItem(it, data, position)
            it.executePendingBindings()
        }

        mItemClick?.let { click ->
            holder.itemView.setOnClickListener {
                click.invoke(this, it, position)
            }
        }

        onBindViewHolder2(holder, position)
    }

    fun getBinding(view: View): B? = DataBindingUtil.getBinding(view)/*.bind(view) */

    fun getItem(@IntRange(from = 0) position: Int): T = mDataList[position]

    abstract fun onBindItem(itemBinding: B, item: T, position: Int)

    protected open fun onBindViewHolder2(holder: ViewHolder, position: Int) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: B = DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false)
        return object : RecyclerView.ViewHolder(binding.root) {}
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }
}

