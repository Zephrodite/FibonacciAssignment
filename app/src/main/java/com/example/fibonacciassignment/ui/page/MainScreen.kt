package com.example.fibonacciassignment.ui.page

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fibonacciassignment.R
import com.example.fibonacciassignment.presentation.MainUiState
import com.example.fibonacciassignment.presentation.MainViewModel
import com.example.fibonacciassignment.ui.component.NormalTextHeader
import com.example.fibonacciassignment.ui.component.ScrollableColumnListComponent
import com.example.fibonacciassignment.ui.theme.FibonacciAssignmentTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel? = null) {
    val uiState = viewModel?.uiState ?: MainUiState()
    val mainList = uiState.mainList
    val subList = uiState.subList
    val poppedFibonacciIndex = uiState.poppedFibonacciIndex
    val addedFibonacciIndex = uiState.addedFibonacciIndex

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val mainListState = rememberLazyListState()
    val subListState = rememberLazyListState()
    var isAnimateToAddedItem by remember { mutableStateOf(false) }
    var isAnimateToPoppedItem by remember { mutableStateOf(false) }

    val indexText = stringResource(R.string.index)
    val numberText = stringResource(R.string.number)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.fibonacci),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    ScrollableColumnListComponent(
                        listState = subListState,
                        dataList = subList,
                        addedItem = uiState.addedFibonacci,
                        onItemClick = { fibonacci ->
                            coroutineScope.launch {
                                sheetState.hide()
                                viewModel?.onPoppedItem(fibonacci)
                                isAnimateToPoppedItem = true
                            }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        onAnimateToItem = {
                            coroutineScope.launch {
                                if (isAnimateToAddedItem && addedFibonacciIndex != null) {
                                    subListState.animateScrollToItem(addedFibonacciIndex)
                                    isAnimateToAddedItem = false
                                }
                            }
                        },
                        itemHeaderContent = { fibonacci ->
                            val fibonacciNumberContent = "$numberText ${fibonacci.number}"
                            val fibonacciIndexContent = "$indexText ${fibonacci.index}"

                            NormalTextHeader(
                                title = fibonacciNumberContent,
                                subTitle = fibonacciIndexContent
                            )
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        ScrollableColumnListComponent(
            modifier = Modifier.padding(innerPadding),
            listState = mainListState,
            poppedItem = uiState.poppedFibonacci,
            dataList = mainList,
            onItemClick = { fibonacci ->
                showBottomSheet = true
                viewModel?.onAddedItem(fibonacci)
                isAnimateToAddedItem = true
            },
            onAnimateToItem = {
                coroutineScope.launch {
                    if (isAnimateToPoppedItem && poppedFibonacciIndex != null) {
                        mainListState.animateScrollToItem(poppedFibonacciIndex)
                        isAnimateToPoppedItem = false
                    }
                }
            },
            itemHeaderContent = { fibonacci ->
                val fibonacciContent =
                    "$indexText ${fibonacci.index}, $numberText ${fibonacci.number}"

                NormalTextHeader(title = fibonacciContent)
            }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    FibonacciAssignmentTheme {
        MainScreen()
    }
}