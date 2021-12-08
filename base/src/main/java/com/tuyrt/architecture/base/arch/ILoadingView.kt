package com.tuyrt.architecture.base.arch


interface ILoadingView {

    fun showLoading(text: String?)

    fun dismissLoading()
}