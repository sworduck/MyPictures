package com.pictures.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.pictures.domain.PictureData
import com.pictures.presentation.databinding.FragmentPictureListBinding
import com.pictures.presentation.adapter.FragmentAdapter
import com.pictures.presentation.adapter.SearchLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PictureListFragment : Fragment() {

    private val vm: PictureListViewModel by viewModels()
    private val searchAdapter: FragmentAdapter = FragmentAdapter(::onFeaturedClick)
    private lateinit var binding: FragmentPictureListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPictureListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        initRecyclerView()
        binding.mainRetryButton.setOnClickListener {
            searchAdapter.refresh()
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            searchAdapter.loadStateFlow
                .collectLatest { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            binding.searchRecycler.isVisible = true
                            binding.layoutErrorNetwork.isVisible = false
                        }

                        is LoadState.Error -> {
                            binding.searchRecycler.isVisible = false
                            binding.layoutErrorNetwork.isVisible = true
                        }

                        is LoadState.NotLoading -> {
                            binding.searchRecycler.isVisible = true
                            binding.layoutErrorNetwork.isVisible = false
                        }
                    }
                }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            searchAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        setupView()
    }

    private fun setupView() {
        lifecycleScope.launch {
            vm.pictureList.cachedIn(lifecycleScope)
                .collectLatest {
                    searchAdapter.submitData(it)
                }
        }
    }

    private fun initRecyclerView() {
        binding.searchRecycler.adapter = searchAdapter.withLoadStateFooter(
            footer = SearchLoadStateAdapter { searchAdapter.retry() }
        )
    }

    private fun onFeaturedClick(picture: PictureData) =
        if (picture.favorite) vm.removePicture(picture)
        else vm.addPicture(picture)

}