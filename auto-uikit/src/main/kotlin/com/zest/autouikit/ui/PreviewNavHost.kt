package com.zest.autouikit.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zest.autouikit.UiPreview

@Composable
internal fun PreviewNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "groups", modifier = modifier) {
        composable("groups") {
            GroupsScreen(
                groups = UiPreview.previewModel?.previewGroups.orEmpty(),
                onSelect = {
                    navController.navigate("components/${it.name}")
                }
            )
        }
        composable(
            route = "components/{group}",
            arguments = listOf(navArgument("group") { type = NavType.StringType })
        ) { navBackStackEntry ->
            ComponentsScreen(navBackStackEntry.arguments?.getString("group").orEmpty())
        }
    }
}