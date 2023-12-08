package bz.micro.tehuur.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import bz.micro.tehuur.ui.theme.TehuurTheme

@Composable
fun Splash(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "here must been splash",
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxSize().wrapContentHeight(align = Alignment.CenterVertically)
        )
}
@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    TehuurTheme {
        Splash("Android")
    }
}