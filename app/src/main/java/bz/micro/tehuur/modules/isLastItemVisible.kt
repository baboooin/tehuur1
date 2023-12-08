package bz.micro.tehuur.modules

import androidx.compose.foundation.lazy.LazyListState
import kotlin.math.abs

fun LazyListState.isReadyToLoad(countLoad:Int): Boolean { //LazyListState
    return  this.layoutInfo.totalItemsCount -  this.firstVisibleItemIndex < abs(countLoad/2) +1
}
