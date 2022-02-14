package com.technologies.ghusers.feature.users.details


data class DetailsForm(
    var notes: String? = null
)

data class DetailsFormState(
    var notesError: String? = null,
    var onNotesSaved: Boolean = false,
    var isDataValid: Boolean = false
)
