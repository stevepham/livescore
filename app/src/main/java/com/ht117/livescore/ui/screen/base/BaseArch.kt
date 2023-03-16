package com.ht117.livescore.ui.screen.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ht117.data.AppErr
import com.ht117.data.model.UiState
import com.ht117.livescore.R

interface IView<T> {
    fun render(uiState: UiState<T>)
}

abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupLogic()
    }

    override fun onResume() {
        super.onResume()
        handleData()
    }

    open fun setupToolbar() {}

    open fun setupView() {}

    open fun setupLogic() {}

    open fun handleData() {}

    fun processError(err: AppErr): String {
        return when (err) {
            AppErr.LostNetworkErr -> {
               getString(R.string.err_no_network)
            }
            is AppErr.NetworkErr -> {
                err.message
            }
            else -> {
                getString(R.string.err_unknown)
            }
        }
    }

    fun navigate(dest: Int, bundle: Bundle? = null, option: NavOptions? = null) {
        findNavController().navigate(dest, bundle, option)
    }

    fun navigateBack() {
        findNavController().navigateUp()
    }
}
