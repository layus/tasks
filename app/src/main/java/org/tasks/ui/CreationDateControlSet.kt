package org.tasks.ui

import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import org.tasks.R
import org.tasks.compose.edit.InfoRow
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CreationDateControlSet : TaskEditControlFragment() {
    @Inject lateinit var locale: Locale

    override fun bind(parent: ViewGroup?): View =
        (parent as ComposeView).apply {
            setContent {
                MdcTheme {
                    InfoRow(
                        creationDate = viewModel.creationDate,
                        modificationDate = viewModel.modificationDate,
                        completionDate = viewModel.completionDate,
                        locale = locale,
                    )
                }
            }
        }

    override fun controlId() = TAG

    companion object {
        const val TAG = R.string.TEA_ctrl_creation_date
    }
}