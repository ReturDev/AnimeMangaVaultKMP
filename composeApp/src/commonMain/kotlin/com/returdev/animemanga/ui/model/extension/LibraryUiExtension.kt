package com.returdev.animemanga.ui.model.extension

import com.returdev.animemanga.domain.model.library.UserLibraryStatusModel
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI

/**
 * Maps a [UserLibraryStatusUI] from the UI layer to the corresponding
 * [UserLibraryStatusModel] in the domain layer.
 *
 * @receiver The [UserLibraryStatusUI] instance to convert.
 * @return The equivalent [UserLibraryStatusModel] instance.
 */
fun UserLibraryStatusUI.toDomain(): UserLibraryStatusModel =
    UserLibraryStatusModel.valueOf(this.name)
