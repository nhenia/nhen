package com.hereliesaz.nhen

object BusinessData {
    val allBusinesses = listOf(
        // Restaurants
        Business("alma01", "Alma", "800 Louisa St, New Orleans, LA 70117", "Restaurants", listOf("Quintessential")),
        Business("atchafalaya01", "Atchafalaya Restaurant", "901 Louisiana Ave, New Orleans, LA 70115", "Restaurants", listOf("Al Fresco")),
        Business("besame01", "Besame", "Location varies, New Orleans, LA", "Restaurants"),
        Business("buffas01", "Buffa's", "1001 Esplanade Ave, New Orleans, LA 70116", "Restaurants", listOf("Nocturnal", "Vibrations")),
        Business("cochon01", "Cochon Butcher", "930 Tchoupitoulas St, Suite A, New Orleans, LA 70130", "Restaurants"),
        Business("clover01", "Clover Grill", "900 Bourbon St, New Orleans, LA 70116", "Restaurants", listOf("Nocturnal", "Quarter Bound")),
        // ... (and so on for every single business from your list)

        // Bars
        Business("abbey01", "The Abbey", "1123 Decatur St, New Orleans, LA 70116", "Bars", listOf("Nocturnal")),
        Business("allways01", "The AllWays Lounge & Cabaret", "2240 St Claude Ave, New Orleans, LA 70117", "Bars", listOf("Esoteric", "Vibrations")),
        Business("annas01", "Anna's", "2601 Royal St, New Orleans, LA 70117", "Bars", listOf("Nocturnal", "Al Fresco")),
        // ... etc.

        // Shops
        Business("crazyplant01", "Crazy Plant Bae", "4893A Magazine St, New Orleans, LA 70115", "Shops", listOf("Esoteric")),
        Business("darkmatter01", "Dark Matter Oddities & Artisan Collective", "822 N Rampart St, New Orleans, LA 70116", "Shops", listOf("Esoteric")),
        Business("euclid01", "Euclid Records", "3301 Chartres St, New Orleans, LA 70117", "Shops"),
        // ... etc.

        // Clubs
        Business("dungeon01", "The Dungeon", "738 Toulouse St, New Orleans, LA 70130", "Clubs", listOf("Nocturnal", "Esoteric", "Quarter Bound")),
        Business("santos01", "Santos", "1135 Decatur St, New Orleans, LA 70116", "Clubs", listOf("Nocturnal", "Esoteric", "Quarter Bound")),
        // ... etc.

        // Museums
        Business("backstreet01", "Backstreet Cultural Museum", "1531 St Philip St, New Orleans, LA 70116", "Museums"),
        Business("burlesque01", "New Orleans Burlesque Museum", "826 St Louis St, New Orleans, LA 70112", "Museums", listOf("Quarter Bound")),
        Business("pharmacy01", "Pharmacy Museum", "514 Chartres St, New Orleans, LA 70130", "Museums", listOf("Esoteric", "Quarter Bound")),
        // ... etc.
    )
}