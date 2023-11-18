package com.lutfi.storyapp

import com.lutfi.storyapp.data.api.response.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "photo + $i",
                "createdAt + $i",
                "name + $i",
                "desc + $i",
                i.toDouble(),
                "id + $i",
                i.toDouble(),
            )
            items.add(story)
        }
        return items
    }
}