package com.technologies.ghusers.user_test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.technologies.ghusers.base.CoroutinesTestRule
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.network.Resource
import com.technologies.ghusers.core.network.UsersRepository
import com.technologies.ghusers.utils.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @MockK(relaxed = true)
    lateinit var context: Context

    @MockK
    lateinit var userRepository: UsersRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        setupMockResponses()

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }


    private fun setupMockResponses() {
        every { userRepository.getUsers(0, 5) } returns getUsers(0)
        every { userRepository.getUsers(5, 5) } returns getUsers(-1)
        every { userRepository.insertUsers(mockUsers.subList(0, 5)) } returns getUsers(0)
        every { userRepository.insertNote(noteSample) } returns getUserNote(2)
        every { userRepository.getUserDetails("defunkt") } returns getUserData("defunkt")
        every { userRepository.getUserDetails("test") } returns getUserData("test")
    }


    @Test
    fun `Fetch user list success`() {
        runBlocking {

            userRepository.apply {

                val response = getUsers(0, 5)

                verify {
                    userRepository.getUsers(0, 5)
                }

                assertEquals(
                    Resource.success(
                        data = mockUsers.subList(0, 5)
                    ), response.first()
                )

                confirmVerified(userRepository)
            }
        }
    }

    @Test
    fun `Fetch user list empty`() {
        runBlocking {

            userRepository.apply {

                val response = getUsers(5, 5)

                verify {
                    userRepository.getUsers(5, 5)
                }

                assertEquals(
                    Resource.success(
                        data = emptyList<User>()
                    ), response.first()
                )

                confirmVerified(userRepository)
            }
        }
    }

    @Test
    fun `Fetch user details success`() {
        runBlocking {

            userRepository.apply {

                val response = getUserDetails("defunkt")

                verify {
                    userRepository.getUserDetails("defunkt")
                }

                assertEquals(
                    Resource.success(
                        data = userDetailsDefunkt to noteSample
                    ), response.first()
                )

                confirmVerified(userRepository)
            }
        }

    }

    @Test
    fun `Fetch user details null`() {
        runBlocking {

            userRepository.apply {

                val response = getUserDetails("test")

                verify {
                    userRepository.getUserDetails("test")
                }

                assertEquals(
                    Resource.error(
                        data = null,
                        message = "No user details found"
                    ), response.first()
                )

                confirmVerified(userRepository)
            }
        }

    }

    @Test
    fun `Insert users to db`() {
        runBlocking {

            userRepository.apply {

                val insert = insertUsers(mockUsers.subList(0, 5))

                verify {
                    userRepository.insertUsers(mockUsers.subList(0, 5))
                }

                assertEquals(
                    Resource.success(
                        data = mockUsers.subList(0, 5)
                    ), insert.first()
                )

                confirmVerified(userRepository)
            }

        }
    }


    @Test
    fun `Insert notes to db`() {
        runBlocking {

            userRepository.apply {
                val insert = insertNote(noteSample)

                verify {
                    userRepository.insertNote(noteSample)
                }

                assertEquals(
                    Resource.success(
                        data = noteSample
                    ), insert.first()
                )

                confirmVerified(userRepository)
            }

        }
    }
}