package com.tuyrt.myarch.test.banner.home

import com.tuyrt.architecture.base.arch.BaseViewModel
import com.tuyrt.architecture.capacity.network.StateLiveData
import com.tuyrt.architecture.ext.launchFlowAndCollect
import com.tuyrt.myarch.logic.data.Repository
import com.tuyrt.myarch.logic.data.entity.BannerBean
import com.tuyrt.myarch.logic.data.entity.HomeListBean

class HomeViewModel : BaseViewModel() {

    val mBanners = StateLiveData<List<BannerBean>>()

    val projectData = StateLiveData<HomeListBean>()

    fun getBanner(refresh: Boolean = false) {
        launchFlowAndCollect({ Repository.getBannerData(refresh) }) {
            mBanners.value = it
        }
    }

    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getHomeList(page: Int, refresh: Boolean = false) {
        launchFlowAndCollect({ Repository.getHomeList(page, refresh) }) {
            projectData.value = it
        }
    }
}
