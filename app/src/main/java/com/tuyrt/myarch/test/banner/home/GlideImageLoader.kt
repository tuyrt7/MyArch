package com.tuyrt.myarch.test.banner.home

import android.view.View
import android.widget.ImageView
import coil.load
import com.stx.xhb.androidx.XBanner
import com.tuyrt.myarch.logic.data.entity.BannerBean

/**
 *   @auther : Aleyn
 *   time   : 2019/09/05
 */
class GlideImageLoader : XBanner.XBannerAdapter {

    override fun loadBanner(banner: XBanner?, model: Any?, view: View?, position: Int) {
        (view as ImageView).load((model as BannerBean).xBannerUrl.toString())
    }

}