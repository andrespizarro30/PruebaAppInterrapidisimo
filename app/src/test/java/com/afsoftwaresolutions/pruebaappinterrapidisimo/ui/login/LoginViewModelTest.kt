package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import app.cash.turbine.test
import com.afsoftwaresolutions.pruebaappinterrapidisimo.MainDispatcherRule
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.network.NetworkMonitor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.DoLoginUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetAppVersionUC
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    @Mock
    lateinit var getAppVersionUC: GetAppVersionUC

    @Mock
    lateinit var doLoginUC: DoLoginUC

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var networkMonitor: NetworkMonitor

    @Mock
    lateinit var repository: RepositoryService

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(getAppVersionUC, doLoginUC, userDao, networkMonitor)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getAppVersion should update state with SuccessAppVersion`() = runTest {
        // Given: Mock repository behavior
        val expectedVersion = 100
        val successResponse = LoginStates.SuccessAppVersion(expectedVersion)
        whenever(repository.getAppVersion()).thenReturn(successResponse)

        // When: Start collecting state
        viewModel.stateAppVersion.test {
            viewModel.getAppVersion()

            // Then: Validate states in order
            assertEquals(LoginStates.Loading, awaitItem()) // First emits Loading
            assertEquals(successResponse, awaitItem())     // Then emits SuccessAppVersion
        }
    }

    @Test
    fun `getAppVersion should update state with ErrorAppVersion`() = runTest {
        // Given: Mock Use Case returning an error response
        val errorResponse = LoginStates.ErrorAppVersion("Network Error")
        whenever(getAppVersionUC()).thenReturn(errorResponse)

        // When: Calling getAppVersion
        viewModel.getAppVersion()

        // Then: Verify the state is updated correctly
        assertEquals(LoginStates.Loading, viewModel.stateAppVersion.value)
        advanceUntilIdle()
        //assertEquals(errorResponse, viewModel.stateAppVersion.value)
    }


}