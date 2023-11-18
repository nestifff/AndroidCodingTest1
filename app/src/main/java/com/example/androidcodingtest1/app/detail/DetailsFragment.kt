package com.example.androidcodingtest1.app.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidcodingtest1.R
import com.example.androidcodingtest1.app.detail.DetailsViewModel.Effect
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private val detailsViewModel: DetailsViewModel by viewModel {
        parametersOf(args.item)
    }

    private val textView by lazy {
        requireView().findViewById<TextView>(R.id.textView)
    }
    private val loader by lazy {
        requireView().findViewById<CircularProgressIndicator>(R.id.loader)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.uiState.collect {
                    updateState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailsViewModel.uiEffect.collect {
                    handleEffect(it)
                }
            }
        }
    }

    private fun updateState(state: DetailsViewModel.State) {
        loader.isVisible = state.item == null
        textView.isVisible = state.item != null
        textView.text = state.item?.title ?: ""
    }

    private fun handleEffect(effect: Effect) {
        when (effect) {
            is Effect.NavigateBack -> findNavController().navigateUp()
        }
    }
}
