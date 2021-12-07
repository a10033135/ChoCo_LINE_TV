package idv.fan.choco.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.socks.library.KLog

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : Fragment(),
    BaseContract.View {
    protected lateinit var mFragmentNavigationListener: FragmentNavigationListener
    var presenter: P? = null

    override fun getFragmentActivity(): FragmentActivity? {
        return activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigationListener) {
            mFragmentNavigationListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        KLog.i(getTAG(), "onViewCreated")
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unsubscribe()
    }

    override fun getFragmentNavigation(): FragmentNavigationListener {
        return mFragmentNavigationListener
    }

    interface FragmentNavigationListener {
        fun getCurrentFragment(): Fragment?

        fun pushFragment(fragment: Fragment)

        fun popFragment()
    }
}