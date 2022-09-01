package com.dashboard.kotlin

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import com.dashboard.kotlin.clashhelper.ClashStatus
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class QuickSettingsService : TileService() {

    private var toggleState = STATE_ON
    override fun onClick() {
        ClashStatus.switch().run {
            updateTile()
        }
    }

    override fun onStartListening() {
        updateTile()
    }

    override fun onStopListening() {
        updateTile()
    }

    private fun updateTile() {
        ClashStatus.getRunStatus {
            if (it == ClashStatus.Status.Running || it == ClashStatus.Status.CmdRunning) {
                // ON
                toggleState = STATE_ON
                qsTile.state = Tile.STATE_ACTIVE
                qsTile.updateTile()
                qsTile.label = applicationContext.getString(R.string.tile_label_running)
                Toast.makeText(this, "Clash已关闭", Toast.LENGTH_SHORT).show()
            } else {
                // OFF
                toggleState = STATE_OFF
                qsTile.state = Tile.STATE_INACTIVE
                qsTile.label = applicationContext.getString(R.string.tile_label_run)
                qsTile.updateTile()
                Toast.makeText(this, "Clash已开启", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val STATE_OFF = 0
        private const val STATE_ON = 1
    }
}