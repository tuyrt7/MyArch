package com.tuyrt.myarch.test.banner.home

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hi.dhl.binding.viewbind
import com.stx.xhb.androidx.XBanner
import com.tuyrt.architecture.base.arch.BaseFragment
import com.tuyrt.architecture.ext.showToolbar
import com.tuyrt.architecture.ext.toast
import com.tuyrt.myarch.R
import com.tuyrt.myarch.databinding.FragmentHomeBinding
import com.tuyrt.myarch.logic.data.entity.ArticlesBean

/**
 *   @auther : Aleyn
 *   time   : 2019/11/02
 */
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewbind()
    private val viewModel: HomeViewModel by viewModels()

    private val mAdapter by lazy { HomeListAdapter() }
    private var page: Int = 0
    private lateinit var banner: XBanner

    private val srlHome by lazy { binding.srlHome }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initial(savedInstanceState: Bundle?) {

        binding.run {
            showToolbar(toolbarHome)
            with(rvHome) {
                layoutManager = LinearLayoutManager(context)
                adapter = mAdapter
                //banner
                banner = XBanner(context)
                banner.minimumWidth = MATCH_PARENT
                banner.layoutParams =
                    ViewGroup.LayoutParams(MATCH_PARENT, resources.getDimension(R.dimen.dp_120).toInt())
                banner.loadImage(GlideImageLoader())
            }
        }

        mAdapter.apply {
            addHeaderView(banner)
            loadMoreModule.setOnLoadMoreListener(this@HomeFragment::loadMore)
            setOnItemClickListener { adapter, _, position ->
                val item = adapter.data[position] as ArticlesBean
                toast(item.title)
                /*val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("url", item.link)
                startActivity(intent)*/
            }
        }
        srlHome.setOnRefreshListener {
            dropDownRefresh()
        }
    }

    override fun createObserver() {
        viewModel.mBanners.observeWater(this) {
            onSuccess {
                banner.setBannerData(it)
            }
        }

        viewModel.projectData.observeWater(this) {
            onSuccess {
                if (it.curPage == 1) mAdapter.setNewInstance(it.datas)
                else mAdapter.addData(it.datas)
                if (it.curPage == it.pageCount) mAdapter.loadMoreModule.loadMoreEnd()
                else mAdapter.loadMoreModule.loadMoreComplete()
                page = it.curPage
            }

            onFinish {
                if (srlHome.isRefreshing) srlHome.isRefreshing = false
            }
        }
    }

    override fun lazyInit() {
        viewModel.run {

            getBanner()

            getHomeList(page)
        }
    }

    /**
     * 下拉刷新
     */
    private fun dropDownRefresh() {
        page = 0
        srlHome.isRefreshing = true
        viewModel.getHomeList(page, true)
        viewModel.getBanner(true)
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        viewModel.getHomeList(page + 1)
    }

}
