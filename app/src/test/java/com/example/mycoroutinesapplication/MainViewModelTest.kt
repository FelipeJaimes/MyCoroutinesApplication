package com.example.mycoroutinesapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var vm : MainViewModel

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        vm = MainViewModel(testDispatcher)
    }

    @After
    fun tearDown(){
        testDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun `Success if user and pass are not empty`(){
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<Boolean>>()
            vm.loginResult.observeForever(observer)

            vm.onSubmitClicked("user", "pass")

            verify(observer).onChanged(true)
        }
    }

    @Test
    fun `Failure if user and pass are empty`(){
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<Boolean>>()
            vm.loginResult.observeForever(observer)

            vm.onSubmitClicked("", "")

            verify(observer).onChanged(false)
        }
    }

    @Test
    fun `Failure if user is empty and pass is not`(){
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<Boolean>>()
            vm.loginResult.observeForever(observer)

            vm.onSubmitClicked("", "pass")

            verify(observer).onChanged(false)
        }
    }

    @Test
    fun `Failure if pass is empty and user is not`(){
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<Boolean>>()
            vm.loginResult.observeForever(observer)

            vm.onSubmitClicked("user", "")

            verify(observer).onChanged(false)
        }
    }
}