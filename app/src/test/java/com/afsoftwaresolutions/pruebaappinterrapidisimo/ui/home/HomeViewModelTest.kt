package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import app.cash.turbine.test
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.network.NetworkMonitor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.LocalitiesDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.SchemasDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetLocalitiesUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetSchemasUC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import javax.xml.validation.Schema

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val userDao: UserDao = mock()
    private val getLocalities: GetLocalitiesUC = mock()
    private val getSchemasUC: GetSchemasUC = mock()
    private val schemasDao: SchemasDao = mock()
    private val localitiesDao: LocalitiesDao = mock()
    private val networkMonitor: NetworkMonitor = mock()

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        whenever(networkMonitor.isConnected).thenReturn(MutableLiveData(true))
        viewModel = HomeViewModel(userDao, getLocalities, getSchemasUC, schemasDao, localitiesDao, networkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `doLoginOffLine should update state with SuccessUserData`() = runTest {
        // Given
        val user = "testUser"
        val password = "testPass"
        val mockUser = mock<UserDataModel>()
        whenever(userDao.getUserByUserAndPassword(user, password)).thenReturn(mockUser)

        val job = launch {
            viewModel.stateUserData.test {
                //Then
                assertEquals(HomeStates.Loading, awaitItem()) // First emission should be Loading
                assertEquals(HomeStates.SuccessUserData(mockUser), awaitItem()) // Second emission should be SuccessUserData
                cancelAndIgnoreRemainingEvents()
            }
        }

        // When
        viewModel.doLoginOffLine(user, password)

        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }

    @Test
    fun `doLoginOffLine should update state with ErrorUserData`() = runTest {
        // Given
        whenever(userDao.getUserByUserAndPassword("user", "pass")).thenReturn(null)

        val job = launch {
            viewModel.stateUserData.test {
                //Then
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(HomeStates.ErrorUserData("Error"), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        // When
        viewModel.doLoginOffLine("user", "pass")
        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }

    @Test
    fun `getLocalitiesData should update state with SuccessLocalitiesData`() = runTest {
        val mockLocalities = listOf(mock<LocalitiesDataModel>())
        val successResponse = HomeStates.SuccessLocalitiesData(mockLocalities)

        whenever(getLocalities()).thenReturn(successResponse)

        val job = launch {
            viewModel.stateLocalitiesData.test {
                //Then
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(successResponse, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getLocalitiesData()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(localitiesDao).insertLocality(mockLocalities[0])

        job.join()
    }

    @Test
    fun `getLocalitiesData should update state with ErrorLocalitiesData`() = runTest {
        val errorResponse = HomeStates.ErrorLocalitiesData("Network Error")
        whenever(getLocalities()).thenReturn(errorResponse)

        val job = launch {
            viewModel.stateLocalitiesData.test {
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(errorResponse, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getLocalitiesData()
        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }

    @Test
    fun `getSchemasData should update state with SuccessSchemasData`() = runTest {
        val mockSchemas = listOf(mock<SchemasDataModel>())
        val successResponse = HomeStates.SuccessSchemasData(mockSchemas)

        whenever(getSchemasUC()).thenReturn(successResponse)

        val job = launch {
            viewModel.stateSchemasData.test {
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(successResponse, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getSchemasData()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(schemasDao).insertSchema(mockSchemas[0])

        job.join()
    }

    @Test
    fun `getSchemasData should update state with ErrorSchemasData`() = runTest {
        val errorResponse = HomeStates.ErrorSchemasData("Network Error")
        whenever(getSchemasUC()).thenReturn(errorResponse)

        val job = launch {
            viewModel.stateSchemasData.test {
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(errorResponse, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getSchemasData()
        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }

    @Test
    fun `getLocalitiesDataOffline should update state with SuccessLocalitiesData`() = runTest {
        val mockLocalities = listOf(mock<LocalitiesDataModel>())
        whenever(localitiesDao.getLocalities()).thenReturn(mockLocalities)

        val job = launch {
            viewModel.stateLocalitiesData.test {
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(HomeStates.SuccessLocalitiesData(mockLocalities), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getLocalitiesDataOffline()
        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }

    @Test
    fun `getSchemasDataOffline should update state with SuccessSchemasData`() = runTest {
        val mockSchemas = listOf(mock<SchemasDataModel>())
        whenever(schemasDao.getSchemas()).thenReturn(mockSchemas)

        val job = launch {
            viewModel.stateSchemasData.test {
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(HomeStates.SuccessSchemasData(mockSchemas), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getSchemasDataOffline()
        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }

    @Test
    fun `getSchemasDataOffline should update state with ErrorSchemasData when no data available`() = runTest {
        whenever(schemasDao.getSchemas()).thenReturn(null)

        val job = launch {
            viewModel.stateSchemasData.test {
                assertEquals(HomeStates.Loading, awaitItem())
                assertEquals(HomeStates.ErrorSchemasData("Sin red disponible, no hay datos guardados"), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

        viewModel.getSchemasDataOffline()
        testDispatcher.scheduler.advanceUntilIdle()

        job.join()
    }
}