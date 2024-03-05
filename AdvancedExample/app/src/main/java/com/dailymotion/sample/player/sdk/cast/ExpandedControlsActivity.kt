package com.dailymotion.sample.player.sdk.cast

import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity
import android.view.Menu
import com.dailymotion.sample.player.sdk.R
import com.google.android.gms.cast.framework.CastButtonFactory

/**
 * An example of extending [ExpandedControllerActivity] to add a cast button.
 */
class ExpandedControlsActivity : ExpandedControllerActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_cast_expanded_controller, menu)
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item)
        return true
    }
}