package com.example.bakery.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.bakery.BR

abstract class BaseDialogFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> :
    DialogFragment() {
    private var isShowing = false

    protected lateinit var viewBinding: ViewBinding

    //ViewModel using in screen
    protected abstract val viewModel: ViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    open val canceledOnTouchOutside: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            root.isClickable = true
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
        if (savedInstanceState != null) {
            if (isAdded) {
                dismiss()
            }
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)
        dialog?.setCancelable(canceledOnTouchOutside)
        showNavigationBar()
    }

    private val handler = Handler(Looper.getMainLooper())

    /**
     * take safe to show, if dialog was added it will dismiss before show
     * */
    open fun show(fragmentManager: FragmentManager, isShow: Boolean) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            doShow(fragmentManager, isShow)
        }, 30)
    }

    private fun doShow(fragmentManager: FragmentManager, isShow: Boolean) {
        val tag = javaClass.name
        if (isShowing) return
        if (isShow) {
            isShowing = true
        }
        try {
            fragmentManager.findFragmentByTag(tag)?.apply {
                dismiss()
                // FIX Listen dialog show after prev dialog dismissed
                if (isShow) {
                    val ft = parentFragmentManager.beginTransaction()
                    ft.runOnCommit {
                        try {
                            show(fragmentManager, tag)
                            isShowing = false
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                    ft.commit()
                } else {
                    isShowing = false
                }
                return
            }
            if (!isShow) {
                if (isAdded) {
                    dismiss()
                }
                return
            }
            val doShow = {
                if (!isAdded) {
                    try {
                        show(fragmentManager, tag)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
            if (isAdded) {
                val ft = parentFragmentManager.beginTransaction()
                ft.remove(this)
                ft.runOnCommit {
                    doShow()
                }
                ft.commitAllowingStateLoss()
            } else {
                doShow()
            }
            isShowing = false
        } catch (ex: Exception) {
            ex.printStackTrace()
            isShowing = false
        }
    }

    private fun showNavigationBar() {
        val windowInsetsController =
            dialog?.window?.let { WindowCompat.getInsetsController(it, it.decorView) }
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController?.show(WindowInsetsCompat.Type.navigationBars())
    }
}
