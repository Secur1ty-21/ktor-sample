package com.android.ktorsample.feature.websocket.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.ktorsample.databinding.FragmentWebsocketBinding
import com.android.ktorsample.feature.websocket.presentation.WebsocketViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebsocketFragment: Fragment() {
    private var _binding: FragmentWebsocketBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<WebsocketViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebsocketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}