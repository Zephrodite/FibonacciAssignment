package com.example.fibonacciassignment.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fibonacciassignment.R
import com.example.fibonacciassignment.domain.Fibonacci
import com.example.fibonacciassignment.domain.Fibonacci.Companion.fibonacciListExample
import com.example.fibonacciassignment.ui.theme.Apple
import com.example.fibonacciassignment.ui.theme.DeepCarminePink
import com.example.fibonacciassignment.ui.theme.FibonacciAssignmentTheme

@Composable
fun ScrollableColumnListComponent(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier.fillMaxWidth(),
    listState: LazyListState = rememberLazyListState(),
    addedItem: Fibonacci? = null,
    poppedItem: Fibonacci? = null,
    dataList: List<Fibonacci>,
    onItemClick: (Fibonacci) -> Unit = {},
    onAnimateToItem: () -> Unit = {},
    itemHeaderContent: @Composable (Fibonacci) -> Unit = { NormalTextHeader() }
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(dataList) { item ->
            FibonacciItem(
                modifier = itemModifier,
                fibonacci = item,
                isJustAdded = addedItem == item,
                isJustPopped = poppedItem == item,
                onItemClick = onItemClick,
                itemHeaderContent = itemHeaderContent
            )
        }
        onAnimateToItem.invoke()
    }
}

@Composable
fun FibonacciItem(
    modifier: Modifier = Modifier,
    fibonacci: Fibonacci,
    isJustAdded: Boolean = false,
    isJustPopped: Boolean = false,
    onItemClick: (Fibonacci) -> Unit = {},
    itemHeaderContent: @Composable (Fibonacci) -> Unit = { NormalTextHeader() }
) {
    val localDensity = LocalDensity.current
    var rowHeightDp by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .height(rowHeightDp + 32.dp)
            .background(
                when {
                    isJustAdded -> Apple
                    isJustPopped -> DeepCarminePink
                    else -> Color.Transparent
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onItemClick.invoke(fibonacci)
            },
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp)
                .align(Alignment.Center)
                .onGloballyPositioned { coordinates ->
                    rowHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemHeaderContent.invoke(fibonacci)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = when (fibonacci.shape) {
                    Fibonacci.Shape.Circle -> painterResource(id = R.drawable.ic_circle)
                    Fibonacci.Shape.Square -> painterResource(id = R.drawable.ic_square)
                    Fibonacci.Shape.Cross -> painterResource(id = R.drawable.ic_cross)
                },
                contentDescription = "",
            )
        }
    }
}

@Composable
fun NormalTextHeader(
    modifier: Modifier = Modifier,
    title: String = "item",
    subTitle: String? = null,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        if (subTitle != null) {
            Text(
                modifier = Modifier,
                text = subTitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun ScrollableColumnListComponentPreview() {
    FibonacciAssignmentTheme {
        ScrollableColumnListComponent(
            modifier = Modifier.background(Color.White),
            dataList = fibonacciListExample(),
        )
    }
}

@Preview
@Composable
fun FibonacciItemPreview() {
    FibonacciAssignmentTheme {
        FibonacciItem(
            modifier = Modifier.background(Color.White),
            fibonacci = fibonacciListExample().first()
        )
    }
}

@Preview
@Composable
fun NormalTextHeaderPreview() {
    FibonacciAssignmentTheme {
        NormalTextHeader(
            modifier = Modifier.background(Color.White),
            title = "Title",
            subTitle = "Subtitle"
        )
    }
}