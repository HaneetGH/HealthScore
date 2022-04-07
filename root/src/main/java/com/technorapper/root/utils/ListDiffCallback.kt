package com.technorapper.root.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.technorapper.root.data.data_model.LocationTable
import com.technorapper.root.data.data_model.lablist.Lab

class ListDiffCallback(
    private val oldList: List<Lab>,
    private val newList: List<Lab>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].labId === newList.get(newItemPosition).labId
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldPosition]
        val (_, value1, name1) = newList[newPosition]

        return name == name1 && value == value1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}