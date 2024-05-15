package com.example.fibonacciassignment.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fibonacciassignment.databinding.FragmentMainBinding
import com.example.fibonacciassignment.ui.page.MainScreen
import com.example.fibonacciassignment.ui.theme.FibonacciAssignmentTheme

class MainFragment : Fragment() {

    private lateinit var _viewModel: MainViewModel
    private val _binding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initViewModel()

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initViewModel() {
        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun initView() {
        _binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                FibonacciAssignmentTheme {
                    MainScreen(viewModel = _viewModel)
                }
            }
        }
    }

}