package idv.fan.cathaybk.ui.base

import androidx.fragment.app.FragmentActivity

interface BaseContract {

    interface View {
        /* 取得 Fragment 導航作為切換頁面使用 */
        fun getFragmentNavigation(): BaseFragment.FragmentNavigationListener
        fun getFragmentActivity(): FragmentActivity?
        fun getTAG(): String
    }

    interface Presenter<V : View> {
        fun attachView(view: V)
        fun subscribe()
        fun unsubscribe()
    }
}