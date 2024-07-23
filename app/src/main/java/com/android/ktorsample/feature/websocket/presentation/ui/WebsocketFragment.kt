package com.android.ktorsample.feature.websocket.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.ktorsample.R
import com.android.ktorsample.databinding.FragmentWebsocketBinding
import com.android.ktorsample.feature.websocket.presentation.WebsocketViewModel
import com.android.ktorsample.feature.websocket.presentation.model.WebsocketEvent
import com.android.ktorsample.feature.websocket.presentation.model.WebsocketState
import kotlinx.coroutines.launch
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
        binding.btnSendMessage.setOnClickListener {
            if (binding.editMessage.text?.isBlank() == true) {
                binding.layoutEditMessage.error = getString(R.string.websocket_edit_message_error)
            } else {
                binding.layoutEditMessage.error = null
                viewModel.obtainEvent(WebsocketEvent.OnBtnSendMessageClick(
                    message = binding.editMessage.text?.toString() ?: ""
                ))
            }
        }
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: WebsocketState) {
        when (state) {
            is WebsocketState.Default -> {}
            is WebsocketState.Loading -> {
                binding.progress.show()
            }
            is WebsocketState.Message -> {
                binding.progress.hide()
                binding.lblMessageFromWebsocket.text = state.msg
            }
            is WebsocketState.Error -> {
                binding.progress.hide()
                binding.lblMessageFromWebsocket.text = getString(R.string.websocket_error_message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.obtainEvent(WebsocketEvent.OnDestroyView)
        _binding = null
    }
}