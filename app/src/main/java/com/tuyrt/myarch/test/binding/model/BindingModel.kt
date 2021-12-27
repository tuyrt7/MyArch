package com.tuyrt.myarch.test.binding.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val name: String, val account: String) : Parcelable

data class Product(val id: Int, val name: String) {
    companion object {
        val CALLBACK: DiffUtil.ItemCallback<Product> = object : DiffUtil.ItemCallback<Product>() {
            // 判断两个Objects 是否代表同一个item对象， 一般使用Bean的id比较
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            // 判断两个Objects 是否有相同的内容。
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = true
        }
    }
}