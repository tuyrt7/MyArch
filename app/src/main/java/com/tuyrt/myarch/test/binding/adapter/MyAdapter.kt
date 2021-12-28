package com.tuyrt.myarch.test.binding.adapter

import com.tuyrt.architecture.base.ui.recyclerview.BaseSimpleAdapter
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.RecycleItemProductBinding
import com.tuyrt.myarch.test.binding.model.Product

/**
 * Created by tuyrt7 on 2021/12/27.
 * 说明：
 */
class MyAdapter : BaseSimpleAdapter<RecycleItemProductBinding, Product>(R.layout.recycle_item_product) {

    override fun onBindItem(itemBinding: RecycleItemProductBinding, item: Product, position: Int) {
        itemBinding.product = item
    }
}