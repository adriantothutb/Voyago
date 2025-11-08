package com.voyago.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.voyago.app.ui.navigation.VoyagoNavGraph
import com.voyago.app.ui.theme.VoyagoTheme

/**
 * Hlavná (a jediná) Activity aplikácie
 *
 * Single Activity Architecture:
 * - Jedna Activity pre celú aplikáciu
 * - Všetky obrazovky sú Composables
 * - Navigácia cez Jetpack Navigation Compose
 *
 * Výhody:
 * - Jednoduchšia správa životného cyklu
 * - Rýchlejšie prechody medzi obrazovkami
 * - Zdieľanie ViewModels medzi obrazovkami
 * - Moderný prístup odporúčaný Google
 *
 * Životný cyklus:
 * 1. onCreate() - vytvorenie Activity
 * 2. Splash screen - zobrazí sa splash
 * 3. setContent() - vytvorenie Compose UI
 * 4. Navigation graph - setup navigácie
 * 5. Theme wrapper - aplikovanie témy
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate - vytvorenie Activity
     *
     * Volá sa raz pri vytvorení Activity
     * Tu inicializujeme UI a nastavenia
     *
     * @param savedInstanceState Bundle s uloženým stavom (pri recreate)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // ===========================================
        // 1. SPLASH SCREEN
        // ===========================================

        /**
         * Splash Screen API (Android 12+)
         *
         * Automaticky zobrazí splash screen pri štarte
         * - Zobrazí ikonu z ic_launcher_foreground
         * - Pozadie podľa Theme.App.Starting
         * - Trvanie: windowSplashScreenAnimationDuration (500ms)
         *
         * Musí byť zavolaný PRED super.onCreate()
         */
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // ===========================================
        // 2. COMPOSE UI SETUP
        // ===========================================

        /**
         * setContent - nastaví Compose UI
         *
         * Nahradí tradičné setContentView(R.layout.activity_main)
         * Celé UI je v Compose, žiadne XML layouts
         */
        setContent {
            // ===========================================
            // 3. THEME WRAPPER
            // ===========================================

            /**
             * VoyagoTheme - aplikuje Material 3 tému
             *
             * Poskytuje:
             * - Farebné schémy (light/dark mode)
             * - Typografiu
             * - Shapes
             * - Všetky Material 3 komponenty
             */
            VoyagoTheme {
                /**
                 * Surface - základný container
                 *
                 * Poskytuje:
                 * - Pozadie podľa témy
                 * - Správne farby pre dark/light mode
                 */
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ===========================================
                    // 4. NAVIGATION SETUP
                    // ===========================================

                    /**
                     * NavController - controller pre navigáciu
                     *
                     * rememberNavController():
                     * - Vytvorí NavController
                     * - Prežije recomposition
                     * - Spravuje back stack
                     * - Umožňuje navigáciu medzi obrazovkami
                     */
                    val navController = rememberNavController()

                    /**
                     * VoyagoNavGraph - navigation graph
                     *
                     * Definuje všetky obrazovky a navigáciu medzi nimi
                     * Štartovacia obrazovka: TripsScreen
                     */
                    VoyagoNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }

    /**
     * onDestroy - zničenie Activity
     *
     * Vyčistenie resources (ak potrebné)
     * ViewModels sa automaticky vyčistia
     */
    override fun onDestroy() {
        super.onDestroy()
        // Cleanup kód (ak potrebný)
    }
}

/**
 * Poznámky k Single Activity Architecture:
 *
 * Tradičný prístup (multi-activity):
 * - TripsActivity.kt
 * - TripDetailActivity.kt
 * - AddTripActivity.kt
 * - MapActivity.kt
 *
 * Moderný prístup (single activity + Compose):
 * - MainActivity.kt (jedna Activity)
 * - TripsScreen.kt (Composable)
 * - TripDetailScreen.kt (Composable)
 * - AddTripScreen.kt (Composable)
 * - MapScreen.kt (Composable)
 *
 * Výhody moderného prístupu:
 * ✅ Rýchlejšia navigácia (bez Intent overhead)
 * ✅ Animácie medzi obrazovkami
 * ✅ Zdieľanie dát cez ViewModels
 * ✅ Jednoduchšia správa stavu
 * ✅ Menej boilerplate kódu
 * ✅ Lepší support pre tablets/foldables
 */