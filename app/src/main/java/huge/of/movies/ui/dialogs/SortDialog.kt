package huge.of.movies.ui.dialogs

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import huge.of.movies.databinding.DialogSortBinding
import huge.of.movies.domain.models.SortField
import huge.of.movies.ui.adapters.SortFieldAdapters
import huge.of.movies.ui.decorations.LineDecoration

fun createSortDialog(
    activity: Activity,
    parent: ViewGroup,
    getSelectedSortField: () -> SortField,
    selectSortField: (SortField) -> Unit,
){
    val dialogBinding = DialogSortBinding.inflate(LayoutInflater.from(activity), parent, false)

    dialogBinding.sortFieldList.apply {
        adapter = SortFieldAdapters(
            getSelectedSortField = getSelectedSortField,
            selectSortField = selectSortField,
        )
    }
    dialogBinding.sortFieldList.layoutManager = LinearLayoutManager(activity)
    dialogBinding.sortFieldList.addItemDecoration(LineDecoration(activity))

    val alert = AlertDialog.Builder(activity)
        .setView(dialogBinding.root)
        .create()



    dialogBinding.btnCancel.setOnClickListener {
        alert.dismiss()
    }

    alert.show()
}