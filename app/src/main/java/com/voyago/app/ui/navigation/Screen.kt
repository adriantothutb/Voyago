// Definícia routes
package com.voyago.app.ui.navigation

/**
 * Sealed class definujúca všetky obrazovky v aplikácii
 *
 * Navigation v Compose:
 * - Každá obrazovka má unikátnu route (string)
 * - Routes môžu obsahovať parametre (napr. tripId)
 * - Type-safe navigation pomocou sealed class
 *
 * Sealed class výhody:
 * - Kompilátor pozná všetky možné obrazovky
 * - Nemôžeš vytvoriť neplatnú route
 * - Autocomplete v IDE
 * - Refactoring friendly
 *
 * Route pattern:
 * - Statická route: "trips" -> TripsScreen
 * - Route s parametrom: "trip_detail/{tripId}" -> TripDetailScreen
 * - Voliteľný parameter: "add_trip?tripId={tripId}" -> AddTripScreen v edit mode
 */
sealed class Screen(val route: String) {

    /**
     * Obrazovka so zoznamom všetkých ciest
     *
     * Route: "trips"
     * Parametre: žiadne
     * Príklad: voyago://trips
     */
    object Trips : Screen("trips")

    /**
     * Obrazovka s detailom konkrétnej cesty
     *
     * Route: "trip_detail/{tripId}"
     * Parametre:
     * - tripId: Long (povinný) - ID cesty
     *
     * Príklad: voyago://trip_detail/123
     */
    object TripDetail : Screen("trip_detail/{tripId}") {
        /**
         * Vytvorí route s konkrétnym tripId
         *
         * @param tripId ID cesty
         * @return String route pripravená na navigáciu
         *
         * Použitie:
         * navController.navigate(Screen.TripDetail.createRoute(123))
         */
        fun createRoute(tripId: Long): String {
            return "trip_detail/$tripId"
        }

        /**
         * Názov parametru v route
         *
         * Použitie pri získavaní parametru z BackStackEntry:
         * val tripId = backStackEntry.arguments?.getString(ARG_TRIP_ID)
         */
        const val ARG_TRIP_ID = "tripId"
    }

    /**
     * Obrazovka na pridanie novej cesty alebo editáciu existujúcej
     *
     * Route: "add_trip?tripId={tripId}"
     * Parametre:
     * - tripId: Long? (voliteľný) - ak je null, vytvárame novú cestu, inak editujeme
     *
     * Príklady:
     * - Nová cesta: voyago://add_trip
     * - Editácia: voyago://add_trip?tripId=123
     */
    object AddTrip : Screen("add_trip?tripId={tripId}") {
        /**
         * Vytvorí route pre vytvorenie novej cesty
         *
         * @return String route bez parametrov
         */
        fun createRoute(): String {
            return "add_trip"
        }

        /**
         * Vytvorí route pre editáciu existujúcej cesty
         *
         * @param tripId ID cesty na editáciu
         * @return String route s tripId parametrom
         */
        fun createRouteWithId(tripId: Long): String {
            return "add_trip?tripId=$tripId"
        }

        /**
         * Názov parametru v route
         */
        const val ARG_TRIP_ID = "tripId"
    }

    /**
     * Obrazovka s mapou všetkých ciest
     *
     * Route: "map"
     * Parametre: žiadne
     * Príklad: voyago://map
     */
    object Map : Screen("map")

    /**
     * Obrazovka s profilom a štatistikami
     *
     * Route: "profile"
     * Parametre: žiadne
     * Príklad: voyago://profile
     */
    object Profile : Screen("profile")
}

/**
 * Helper extension funkcie pre NavController
 *
 * Zjednodušujú navigáciu - namiesto dlhých route stringov používame type-safe metódy
 */

/**
 * Naviguje na detail cesty
 *
 * Použitie:
 * navController.navigateToTripDetail(tripId = 123)
 */
fun androidx.navigation.NavController.navigateToTripDetail(tripId: Long) {
    this.navigate(Screen.TripDetail.createRoute(tripId))
}

/**
 * Naviguje na pridanie novej cesty
 *
 * Použitie:
 * navController.navigateToAddTrip()
 */
fun androidx.navigation.NavController.navigateToAddTrip() {
    this.navigate(Screen.AddTrip.createRoute())
}

/**
 * Naviguje na editáciu cesty
 *
 * Použitie:
 * navController.navigateToEditTrip(tripId = 123)
 */
fun androidx.navigation.NavController.navigateToEditTrip(tripId: Long) {
    this.navigate(Screen.AddTrip.createRouteWithId(tripId))
}

/**
 * Naviguje späť a popne celý back stack až po Trips
 *
 * Použitie po uložení cesty:
 * navController.navigateBackToTrips()
 */
fun androidx.navigation.NavController.navigateBackToTrips() {
    this.navigate(Screen.Trips.route) {
        // Pop všetko až po Trips
        popUpTo(Screen.Trips.route) {
            inclusive = false // Trips obrazovku nenechaj na stacku
        }
    }
}

