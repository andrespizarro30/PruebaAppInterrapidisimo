package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import android.util.Log
import app.cash.turbine.test
import com.afsoftwaresolutions.pruebaappinterrapidisimo.MainDispatcherRule
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.network.NetworkMonitor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.DoLoginUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetAppVersionUC
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
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

    val headers = mapOf(
        "Usuario" to "pam.meredy21",
        "Identificacion" to "987204545",
        "Accept" to "text/json",
        "IdUsuario" to "pam.meredy21",
        "IdCentroServicio" to "1295",
        "NombreCentroServicio" to "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA30#7-45",
        "IdAplicativoOrigen" to "9",
        "Content-Type" to "application/json"
    )

    @Before
    fun setUp() {
        viewModel = LoginViewModel(getAppVersionUC, doLoginUC, userDao, networkMonitor)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getAppVersion should update state with SuccessAppVersion`() = runTest {

        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // Given
        val expectedVersion = 100
        val successResponse = LoginStates.SuccessAppVersion(expectedVersion)
        whenever(getAppVersionUC()).thenReturn(successResponse)


        val job = launch { viewModel.stateAppVersion.test {
            //Then
            assertEquals(LoginStates.Loading, awaitItem())
            assertEquals(successResponse, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }}

        // When
        viewModel.getAppVersion()

        testDispatcher.scheduler.advanceUntilIdle()

        job.join()

        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `getAppVersion should update state with ErrorAppVersion`() = runTest {

        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // Given
        val errorResponse = LoginStates.ErrorAppVersion("Network Error")
        whenever(getAppVersionUC()).thenReturn(errorResponse)

        val job = launch {
            viewModel.stateAppVersion.test {
                //Then
                assertEquals(LoginStates.Loading, awaitItem())  // First emission should be Loading
                assertEquals(errorResponse, awaitItem())  // Second emission should be the error response
                cancelAndIgnoreRemainingEvents()
            }
        }

        // When
        viewModel.getAppVersion()

        testDispatcher.scheduler.advanceUntilIdle()

        job.join()

        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `doLogin should update state with SuccessLogin`() = runTest {

        val testDispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        // Given
        val loginData = LoginDataModel(
            mac = "",
            nomAplicacion = "Controller APP",
            password = "SW50ZXIyMDIx\n",
            path = "",
            usuario = "cGFtLm1lcmVkeTIx\n"
        )
        val userDataModel = UserDataModel(Identificacion = "82345678",Usuario = "Usuario Prueba App", Nombre = "Prueba App",user = "cGFtLm1lcmVkeTIx\n", password = "SW50ZXIyMDIx\n")
        val successResponse = LoginStates.SuccessLogin(userDataModel)

        whenever(doLoginUC(headers, loginData)).thenReturn(successResponse)

        val job = launch {
            //Then
            viewModel.stateLoginProcess.test {
                assertEquals(LoginStates.Loading, awaitItem())
                assertEquals(successResponse, awaitItem())  // Second emission should be SuccessLogin
                cancelAndIgnoreRemainingEvents()
            }
        }

        // When:
        viewModel.doLogin(headers, loginData)

        testDispatcher.scheduler.advanceUntilIdle()

        job.join()

        Dispatchers.resetMain()
    }


}