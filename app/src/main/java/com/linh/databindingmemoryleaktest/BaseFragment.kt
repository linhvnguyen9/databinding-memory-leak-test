package com.linh.databindingmemoryleaktest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment<DB: ViewDataBinding> : Fragment() {
    private var _binding: DB? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getLayoutId(): Int

    fun <T> LiveData<T>.observe(observer: Observer<T>) {
        this.observe(viewLifecycleOwner, observer)
    }
}