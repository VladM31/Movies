package huge.of.movies.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import huge.of.movies.R
import huge.of.movies.domain.models.SortField

class SortFieldAdapters(
    private val getSelectedSortField: () -> SortField,
    private val selectSortField: (SortField) -> Unit,
) : RecyclerView.Adapter<SortFieldAdapters.SortFieldViewHolder>(){


    class SortFieldViewHolder(
        private val text: TextView
    ): ViewHolder(text) {
        @SuppressLint("ResourceAsColor")
        fun bind(sortField: SortField, isSelected: Boolean) {
            text.text = sortField.toTitleText()
            if (isSelected) {
                text.setTextColor(R.color.black)
            } else {
                text.setTextColor(R.color.gray)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortFieldViewHolder {
        return SortFieldViewHolder(TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(7)
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textSize = 30f
        })
    }

    override fun getItemCount(): Int = SortField.entries.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SortFieldViewHolder, position: Int) {
        holder.bind(SortField.entries[position], getSelectedSortField() == SortField.entries[position])
        holder.itemView.setOnClickListener {
            selectSortField(SortField.entries[position])
            notifyDataSetChanged()
        }
    }
}