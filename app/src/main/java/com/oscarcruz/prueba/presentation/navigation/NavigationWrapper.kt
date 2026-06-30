package com.oscarcruz.prueba.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.oscarcruz.prueba.presentation.features.Acuerdo.AcuerdoView
import com.oscarcruz.prueba.presentation.features.login.LoginView
import kotlinx.serialization.Serializable


@Serializable object LoginDestination
@Serializable object AcuerdoDestination

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        // Splash -> Tour
        composable<LoginDestination> {
            LoginView(
                navigateAcuerdo = {
                    navController.navigateAndClear(AcuerdoDestination)
                }
            )
        }

        // Tour -> Permissions
        composable<AcuerdoDestination> {
            AcuerdoView(

            )
        }



    }
}

/**
 * Extensión para navegar limpiando el stack anterior.
 * Ideal para flujos lineales
 */
inline fun <reified T : Any> NavHostController.navigateAndClear(route: T) {
    navigate(route) {
        currentDestination?.route?.let { currentRoute ->
            popUpTo(currentRoute) {
                inclusive = true
            }
        }
        launchSingleTop = true
    }
}