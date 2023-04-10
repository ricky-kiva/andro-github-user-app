package com.rickyslash.githubuserapp.helper

import com.rickyslash.githubuserapp.database.FavUser
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavUserDiffCallbackTest {

    @Mock
    private lateinit var oldFavUserList: List<FavUser>

    @Mock
    private lateinit var newFavUserList: List<FavUser>

    private val callback: FavUserDiffCallback by lazy { FavUserDiffCallback(oldFavUserList, newFavUserList) }

    @Test
    fun testGetOldListSize() {
        Mockito.`when`(oldFavUserList.size).thenReturn(5)
        val result = callback.oldListSize
        assertEquals(5, result)
    }

    @Test
    fun testGetNewListSize() {
        Mockito.`when`(newFavUserList.size).thenReturn(7)
        val result = callback.newListSize
        assertEquals(7, result)
    }

    @Test
    fun testAreItemsTheSame() {

        val oldUser = FavUser(1, "John", "http://old-image.com")
        val newUser = FavUser(1, "John", "http://new-image.com")

        Mockito.`when`(oldFavUserList[0]).thenReturn(oldUser)
        Mockito.`when`(newFavUserList[0]).thenReturn(newUser)

        val result = callback.areItemsTheSame(0, 0)
        assertTrue(result)
    }

    @Test
    fun testAreContentsTheSame() {
        val oldUser = FavUser(1, "John", "http://old-image.com")
        val newUser = FavUser(1, "John", "http://new-image.com")

        Mockito.`when`(oldFavUserList[0]).thenReturn(oldUser)
        Mockito.`when`(newFavUserList[0]).thenReturn(newUser)

        val result = callback.areContentsTheSame(0, 0)
        assertFalse(result)
    }
}
