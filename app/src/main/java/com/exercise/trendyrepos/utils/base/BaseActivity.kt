package com.exercise.trendyrepos.utils.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment
import com.exercise.trendyrepos.utils.extension.toast
import com.exercise.trendyrepos.R

abstract class BaseActivity<VB : ViewDataBinding, V : IBase.ViewModel<*>> : IBase.View<V>,
    AppCompatActivity() {
    lateinit var binding: VB
    private var progress: Dialog? = null
    private var navHostFragment: NavHostFragment? = null

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract val navHostId: Int
    abstract fun getBindingVariable(): Int

    @get:NavigationRes
    abstract val navigationGraphId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerStateListeners()
        performDataBinding()
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

    private fun initNavigationGraph() {
        try {
            navHostFragment = supportFragmentManager.findFragmentById(navHostId) as NavHostFragment?
            navHostFragment?.navController?.apply {
                graph = navInflater.inflate(navigationGraphId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e.message)
        }
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(getBindingVariable(), viewModel)
        initNavigationGraph()
        binding.executePendingBindings()
    }

    fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            toast(msg)
        }
    }

    override fun onDestroy() {
        progress?.dismiss()
        unregisterStateListeners()
        viewModel.viewState.loaderState.removeObservers(this)
        super.onDestroy()
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
