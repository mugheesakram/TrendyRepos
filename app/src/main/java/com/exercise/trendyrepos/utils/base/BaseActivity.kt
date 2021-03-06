package com.exercise.trendyrepos.utils.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, V : IBase.ViewModel> : IBase.View<V>,
    AppCompatActivity() {
    lateinit var binding: VB
    private var progress: Dialog? = null

    @LayoutRes
    abstract fun getLayoutId(): Int
    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        progress?.dismiss()
        super.onDestroy()
    }
}
