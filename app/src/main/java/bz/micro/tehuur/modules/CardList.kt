package bz.micro.tehuur.modules

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bz.micro.tehuur.models.Record


val mystyle = TextStyle(
//color = Color,
fontSize = 10.sp,
fontWeight = FontWeight(10),
)

fun convertStringToBitmap(base64Str: String?): Bitmap? {
    val decodedString = Base64.decode(base64Str, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

@Composable
fun CardList(item : Record ) {
        ElevatedCard(modifier = Modifier

            .fillMaxWidth()
            .height(300.dp)
            .padding(10.dp), ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                bitmap = convertStringToBitmap(item.img)!!.asImageBitmap(), contentDescription = null,
                contentScale = ContentScale.FillWidth)
    Text( text =item.city, style = mystyle)

        }
}