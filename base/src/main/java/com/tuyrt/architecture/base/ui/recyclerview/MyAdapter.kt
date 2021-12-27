package com.tuyrt.architecture.base.ui.recyclerview

import com.tuyrt.architecture.R
import com.tuyrt.architecture.base.binding.adapter.Product
import com.tuyrt.architecture.databinding.RecycleItemProductBinding

/**
 * Created by tuyrt7 on 2021/12/27.
 * 说明：
 */
class MyAdapter : BaseSimpleAdapter<Product, RecycleItemProductBinding>(R.layout.recycle_item_product) {

    override fun onBindItem(itemBinding: RecycleItemProductBinding, item: Product, position: Int) {
        itemBinding.product = item
    }

}