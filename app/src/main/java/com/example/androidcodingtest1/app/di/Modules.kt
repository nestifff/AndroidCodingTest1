package com.example.androidcodingtest1.app.di

import com.example.androidcodingtest1.app.detail.DetailsViewModel
import com.example.androidcodingtest1.app.list.ListViewModel
import com.example.androidcodingtest1.app.list.model.ListItem
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val uiModule = module {
    viewModel {
        ListViewModel()
    }
    viewModel { (item: ListItem) ->
        DetailsViewModel(item = item)
    }
}
