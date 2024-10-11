package com.training.starthub.ui.base

import androidx.recyclerview.widget.DiffUtil

/**
 * BaseDiffUtil is a utility class that extends DiffUtil.Callback to provide efficient data change
 * calculations for RecyclerView adapters.
 *
 * @param T The type of data items to be compared.
 * @property oldList The old list of data items.
 * @property newList The new list of data items.
 * @property checkIfSameItem A function that determines whether two items in the list are the same.
 * @property checkIfSameContent A function that determines whether the content of two items in the list is the same.
 */
open class BaseDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val checkIfSameItem: (oldItem: T, newItem: T) -> Boolean,
    private val checkIfSameContent: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.Callback() {

    /**
     * @return The size of the old list.
     */
    override fun getOldListSize() = oldList.size

    /**
     * @return The size of the new list.
     */
    override fun getNewListSize() = newList.size

    /**
     * Determines whether two items in the data set are the same.
     *
     * @param oldItemPosition The old data item.
     * @param newItemPosition The new data item.
     * @return `true` if the items are the same, `false` otherwise. By default, it checks for equality using the `equals` method.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkIfSameItem(oldList[oldItemPosition], newList[newItemPosition])
    }

    /**
     * Determines whether the content of two items in the data set is the same. This function is used by DiffUtil to
     * determine whether the content of two items has changed.
     *
     * @param oldItemPosition The old data item.
     * @param newItemPosition The new data item.
     * @return `true` if the content of the items is the same, `false` otherwise. By default, it always returns `true`.
     * Subclasses can override this method to provide custom content comparison logic.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return checkIfSameContent(oldList[oldItemPosition], newList[newItemPosition])
    }
}