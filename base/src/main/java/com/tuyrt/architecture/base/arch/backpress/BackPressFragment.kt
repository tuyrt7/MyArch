package com.tuyrt.architecture.base.arch.backpress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tuyrt.architecture.base.arch.BaseActivity
import com.tuyrt.architecture.capacity.log.KLog
import com.tuyrt.architecture.ext.safeAs

/**
 * Created by tuyrt7 on 2021/4/1.
 */
class BackPressFragment : Fragment() {

    var needBackPress = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.safeAs<BaseActivity>()?.registerBackPress(viewLifecycleOwner, object : BackPressObserver {
            override fun onBackPress(): Boolean {
                if (needBackPress) {
                    doSomeLogic()
                    return true
                }
                return false
            }
        })
    }

    private fun doSomeLogic() {
        KLog.d("拦截回退，do ..")
    }
}