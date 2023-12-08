package bz.micro.tehuur.screens



import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import bz.micro.tehuur.models.TeModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import bz.micro.tehuur.modules.CardList
import bz.micro.tehuur.modules.Skeleton
import bz.micro.tehuur.modules.isReadyToLoad


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun LS1(model: TeModel = viewModel()) {
    val listState = rememberLazyListState()
    val todoListState = model.elementList.collectAsState()
    val loading = model.loading.collectAsState()
    val configuration = LocalConfiguration.current

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect {
             Log.d("Scroll", listState.firstVisibleItemIndex.toString())
                if (listState.isReadyToLoad(model.listAmount.value)) {
                    Log.d("Scroll", "last item")
                    model.nextPage()
                }
                if (listState.isScrollInProgress) {
                        Log.d("Scroll","${listState.layoutInfo.viewportStartOffset} - ${listState.firstVisibleItemScrollOffset}")

                } }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(),
        state = listState,
        userScrollEnabled = true) {

        items(items = todoListState.value, itemContent = { item ->
            CardList(item = item)
        })
        if (loading.value) { item(key="0") {
            Skeleton(w = configuration.screenWidthDp.dp,h= 200.dp, r = 12.dp )
        }}
    }
}