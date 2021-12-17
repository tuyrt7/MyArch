package com.tuyrt.myarch.test.banner

import android.os.Bundle
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.ext.loadFragments
import com.tuyrt.myarch.R
import com.tuyrt.myarch.test.banner.home.HomeFragment

/**
 * Created by tuyrt7 on 2021/12/16.
 * 说明：
 */
class BannerActivity : BaseActivity(R.layout.activity_banner) {

    override fun initial(savedInstanceState: Bundle?) {
        val frag1 = HomeFragment.newInstance()
        loadFragments(R.id.container, 0, supportFragmentManager, mutableListOf(frag1))
    }

    override fun createObserver() {

    }
}