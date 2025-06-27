package com.hereliesaz.nhen

import android.content.Intent
import android.service.quicksettings.TileService

class BlurTileService : TileService() {
    override fun onClick() {
        qsTile?.updateTile()
        startActivityAndCollapse(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}