package com.returdev.animemanga.ui.model.basic

import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.ui.model.core.AnimeType

data class AnimeBasicUi(
    override val id : Int,
    override val image : ImageType.NormalImage,
    override val title : String,
    override val type : AnimeType,
    override val score : Float
) : VisualMediaBasicUi()