package com.exercise.trendyrepos.ui.splash

import android.animation.Animator
import android.content.Intent
import androidx.activity.viewModels
import com.exercise.trendyrepos.R
import com.exercise.trendyrepos.databinding.ActivitySplashBinding
import com.exercise.trendyrepos.ui.dashboard.main.DashboardActivity
import com.exercise.trendyrepos.utils.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, ISplash.ViewModel>(), ISplash.View {
    override val viewModel: ISplash.ViewModel by viewModels<SplashVM>()
    override fun getLayoutId() = R.layout.activity_splash
    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()

        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
        })
    }
}
