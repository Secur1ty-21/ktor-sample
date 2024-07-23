package com.android.ktorsample.feature.http.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.ktorsample.databinding.FragmentHttpBinding
import com.android.ktorsample.feature.http.presentation.HttpViewModel
import com.android.ktorsample.feature.http.presentation.model.HttpCatsEvent
import com.android.ktorsample.feature.http.presentation.model.HttpCatsState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HttpFragment : Fragment() {
    private var _binding: FragmentHttpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HttpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHttpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnGetRandomCatFact.setOnClickListener {
            viewModel.obtainEvent(HttpCatsEvent.OnBtnGetRandomCatFactClick)
        }
        binding.btnGetCatBreeds.setOnClickListener {
            viewModel.obtainEvent(HttpCatsEvent.OnBtnGetBreeds)
        }
        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { state ->
                binding.lblRandomCatFact.isVisible = (state is HttpCatsState.Loading).not()
                binding.progress.isVisible = state is HttpCatsState.Loading
                state?.let { renderState(state) }
            }
        }
    }

    private fun renderState(state: HttpCatsState) {
        when (state) {
            is HttpCatsState.CatFact -> binding.lblRandomCatFact.text = state.fact.fact
            is HttpCatsState.BreedList -> {
                val text = state.breedList.joinToString("\n") { breed ->
                    "breed - ${breed.breed}\n"
                        .plus("coat - ${breed.coat}\n")
                        .plus("country - ${breed.country}\n")
                        .plus("origin - ${breed.origin}\n")
                        .plus("patter - ${breed.pattern}\n")
                }
                binding.lblRandomCatFact.text = text
            }
            is HttpCatsState.Error -> binding.lblRandomCatFact.text = state.errorType.name
            is HttpCatsState.Loading -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}