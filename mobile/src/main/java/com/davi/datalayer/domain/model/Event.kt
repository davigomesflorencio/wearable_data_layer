package com.davi.datalayer.domain.model

import androidx.annotation.StringRes

data class Event(
    @StringRes val title: Int,
    val text: String
)