package com.returdev.animemanga.ui.model.basic

import com.returdev.animemanga.domain.model.core.ImageType
import com.returdev.animemanga.ui.model.core.VisualMediaType

sealed class VisualMediaBasicUi() {

    abstract val id : Int
    abstract val image : ImageType.NormalImage
    abstract val title : String
    abstract val type : VisualMediaType
    abstract val score : Float

}
