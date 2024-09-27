package com.niranjan.khatri.kmptutorial.android.ui


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AnimatedSwipeDismiss(
    modifier: Modifier = Modifier,
    item: T,
    background: @Composable (dismissedValue: DismissValue) -> Unit,
    content: @Composable (dismissedValue: DismissValue) -> Unit,
    directions: Set<DismissDirection> = setOf(DismissDirection.EndToStart),
    enter: EnterTransition = expandVertically(),
    exit: ExitTransition = shrinkVertically(
        animationSpec = tween(
            durationMillis = 500,
        )
    ),
    onDismiss: (T) -> Unit
) {
    val dismissState = rememberDismissState { dismissValue ->
        if (dismissValue.equals(DismissValue.DismissedToStart)) {
            onDismiss(item)
        }
        0f
    }
    val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

    AnimatedVisibility(
        modifier = modifier,
        visible = !isDismissed,
        enter = enter,
        exit = exit
    ) {
        SwipeToDismiss(
            modifier = modifier,
            state = dismissState,
            directions = directions,
            background = { background(dismissState.currentValue) },
            dismissContent = { content(dismissState.currentValue) }
        )
    }
}

 */