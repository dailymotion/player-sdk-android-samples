package com.dailymotion.sample.player.sdk

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.dailymotion.sample.player.sdk.screenselection.ScreenSelectionFragment
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext


class MainActivity : AppCompatActivity() {

    companion object {
        private const val CONSENT_STRING = "CPxvk2aPxvk2aImAAAENCZCMAP_AAH_AAAAAI7Nd_X__bX9n-_7_6ft0eY1f9_r37uQzDhfNs-8F3L_W_LwX32E7NF36tq4KmR4ku1bBIQNtHMnUDUmxaolVrzHsak2cpyNKJ_JkknsZe2dYGF9Pn9lD-YKZ7_5_9_f52T_9_9_-39z3_9f___dv_-__-vjf_599n_v9fV_78_Kf9______-____________8Edmu_r__tr-z_f9_9P26PMav-_1793IZhwvm2feC7l_rfl4L77Cdmi79W1cFTI8SXatgkIG2jmTqBqTYtUSq15j2NSbOU5GlE_kyST2MvbOsDC-nz-yh_MFM9_8_-_v87J_-_-__b-57_-v___u3__f__Xxv_8--z_3-vq_9-flP-_______f___________-AA.II7Nd_X__bX9n-_7_6ft0eY1f9_r37uQzDhfNs-8F3L_W_LwX32E7NF36tq4KmR4ku1bBIQNtHMnUDUmxaolVrzHsak2cpyNKJ_JkknsZe2dYGF9Pn9lD-YKZ7_5_9_f52T_9_9_-39z3_9f___dv_-__-vjf_599n_v9fV_78_Kf9______-____________8A"
    }

    private var titleTextView: TextView? = null
    private var backButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Initialize Google Cast sdk */
        CastContext.getSharedInstance(this)

        /*
         * The consent string is passed in manually for demo purposes only, this functionality
         * needs to be managed by a compliant CMP and must be used to use IAB TCF2 for the creation
         * and storage of the transparency & consent string. As Dailymotion fully supports the IAB
         * TCF2 standard, our native SDKs and our Player can access this string received from the
         * CMP in-app.
         */
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .getString("IABTCF_TCString", CONSENT_STRING)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        titleTextView = toolbar.findViewById(R.id.toolbarTextView)
        backButton = toolbar.findViewById(R.id.toolbarBackButton)
        backButton?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ScreenSelectionFragment>(R.id.fragment_container_view)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )
        return true
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        titleTextView?.setText(titleId)
    }

    fun showToolbarBackButton(showBackButton: Boolean) {
        backButton?.let { b ->
            val visibility = if (showBackButton) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            b.visibility = visibility
        }
    }
}