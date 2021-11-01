package com.exercise.trendyrepos.utils.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.exercise.trendyrepos.utils.extension.toast
import com.exercise.trendyrepos.R

abstract class BaseFragment<T : ViewDataBinding, V : IBase.ViewModel<*>> : IBase.View<V>,
    Fragment() {
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int
    lateinit var mViewDataBinding: T
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initiateViewBinding(inflater, container, getLayoutId())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.loaderState.observe(this, {
            it?.let {
                when (it) {
                    is String -> {
                        showToast(it)
                    }
                }
            }
        })
    }

    fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            requireActivity().toast(msg)
        }
    }

    private fun initiateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutResId: Int,
    ): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        registerStateListeners()
        mViewDataBinding = DataBindingUtil.setContentView(this.requireActivity(), getLayoutId())
        mViewDataBinding.setVariable(getBindingVariable(), viewModel)
        mViewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        mViewDataBinding.executePendingBindings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterStateListeners()
    }

    private fun registerStateListeners() {
        if (viewModel is BaseViewModel<*>) {
            viewModel.registerLifecycleOwner(this)
        }
    }

    private fun unregisterStateListeners() {
        if (viewModel is BaseViewModel<*>) {
            viewModel.unregisterLifecycleOwner(this)
        }
    }

}
