package bz.micro.tehuur.modules

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import bz.micro.tehuur.models.TeModel
import bz.micro.tehuur.screens.LS1

@Composable
fun List(city: String?){
    Text( text = city?:"no city" )
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavGraph(model: TeModel) {
    val uri = "https://tehuur.web.app"
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "full") {

        composable(route = "full") {
            TeScaffold(
                title = "List",
                icon = Icons.Rounded.Menu,
                navClick = { model.nextPage()} ){
                LS1(model = model)
            }
        }

        composable(route = "Splash") {
            TeScaffold(
                title = "Splash",
                icon = Icons.Rounded.Menu,
                navClick = { Log.d("------","Click")})  {
            }

        }
        composable(
              route = "list/{city}",
              arguments = listOf(navArgument("city") { type = NavType.StringType }),
              deepLinks = listOf(navDeepLink { uriPattern = "$uri/city={city}" })
        ) {
            backStackEntry ->
            val arguments = backStackEntry.arguments
            List(city = arguments?.getString("city"))
        }
    }
}