package com.technologies.ghusers.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.technologies.ghusers.core.extensions.observe
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * To act as a super class for all other fragments.
 * Passing ViewDataBinding to include initialization which is common for all fragments
 * that's using databinding
 * Sample usage SampleFragment<FragmentSampleBinding>
 */
abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected val gson = Gson()

    @get:LayoutRes
    protected abstract val layoutRes: Int

    protected lateinit var baseView: View

    protected lateinit var binding: V

    protected abstract fun onCreated(savedInstance: Bundle?)

    protected abstract fun getViewModel(): BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        baseView = binding.root

        return baseView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated(savedInstanceState)
        initBaseObserver()
    }

    protected fun showMessage(message: String, positive: Boolean, neutral: Boolean = false) {
        activity?.let {
            (it as BaseActivity<*>).showMessage(message, positive, neutral)
        }
    }

    protected fun showLoading(loading: Boolean) {
        activity?.let { a -> (a as BaseActivity<*>).showLoading(loading) }
    }

    private fun initBaseObserver() {
        getViewModel()?.apply {
            observe(loading) {
                it?.apply { showLoading(this) }
            }
            observe(error) {
                it?.apply { showMessage(this, false) }
            }

            (activity?.application as? App)?.internetConnectionStream
                ?.onEach {
                    setHasInternetConnection(it)
                }?.launchIn(lifecycleScope)
        }

    }
}