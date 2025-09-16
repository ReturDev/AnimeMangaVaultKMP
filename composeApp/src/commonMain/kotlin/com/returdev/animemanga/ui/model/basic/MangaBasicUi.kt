package com.returdev.animemanga.ui.model.basic

import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.ui.model.core.MangaType
import com.returdev.animemanga.ui.model.core.VisualMediaType

data class MangaBasicUi(
    override val id : Int,
    override val image : ImageType.NormalImage,
    override val title : String,
    override val type : MangaType,
    override val score : Float
) : VisualMediaBasicUi()