package com.dashboard.kotlin

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.Toast
import com.dashboard.kotlin.clashhelper.ClashStatus
import com.dashboard.kotlin.clashhelper.ClashStatus.Status.*
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class QuickSettingsService : TileService() {

    override fun onClick() {
        when (qsTile.state) {
            // on -> off
            Tile.STATE_ACTIVE -> {
                qsTile.state = Tile.STATE_INACTIVE
                ClashStatus.stopWithBack {
                    Toast.makeText(this, "Clash stopped", Toast.LENGTH_SHORT).show()
                }
                qsTile.updateTile()
            }
            // off -> on
            Tile.STATE_INACTIVE -> {
                qsTile.state = Tile.STATE_ACTIVE
                ClashStatus.startWithBack {
                    Toast.makeText(this, "Clash started", Toast.LENGTH_SHORT).show()
                }
                qsTile.updateTile()
            }
        }

    }

    override fun onStartListening() {
        updateTile()
    }

    override fun onStopListening() {
        updateTile()
    }

    private fun updateTile() {
        ClashStatus.getRunStatus { status ->
            when (status) {
                Running -> qsTile.state = Tile.STATE_ACTIVE
                Stop -> qsTile.state = Tile.STATE_INACTIVE
                CmdRunning -> qsTile.state = Tile.STATE_INACTIVE
            }
            qsTile.updateTile()
        }
    }
}