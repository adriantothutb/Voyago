// Navigation setup
package com.voyago.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.voyago.app.ui.screens.trips.TripsScreen

/**
 * Zjednodušená navigácia - len TripsScreen
 */
@Composable
fun VoyagoNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Trips.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Trips Screen
        composable(route = Screen.Trips.route) {
            TripsScreen(
                onTripClick = { tripId ->
                    // TODO: navigácia na detail
                },
                onAddClick = {
                    // TODO: navigácia na add trip
                }
            )
        }

        // Ostatné screeny pridáme neskôr
    }
}

