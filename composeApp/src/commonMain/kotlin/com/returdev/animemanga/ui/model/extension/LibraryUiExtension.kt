package com.returdev.animemanga.ui.model.extension

import com.returdev.animemanga.domain.model.library.UserLibraryStatusModel
import com.returdev.animemanga.ui.model.user.UserLibraryStatusUI

/**
 * Converts a [UserLibraryStatusUI] value from the UI layer into its corresponding
 * [UserLibraryStatusModel] domain-layer representation.
 *
 * Internally uses the enum name to perform a direct mapping.
 *
 * @receiver The UI-layer library status.
 * @return The equivalent domain-layer library status.
 */
fun UserLibraryStatusUI.toDomain(): UserLibraryStatusModel =
    UserLibraryStatusModel.valueOf(this.name)

/**
 * Converts a [UserLibraryStatusModel] value from the domain layer into its corresponding
 * [UserLibraryStatusUI] UI-layer representation.
 *
 * Uses the enum constant name to perform a one-to-one mapping.
 *
 * @receiver The domain-layer library status.
 * @return The equivalent UI-layer library status.
 */
fun UserLibraryStatusModel.toUI(): UserLibraryStatusUI =
    UserLibraryStatusUI.valueOf(this.name)

