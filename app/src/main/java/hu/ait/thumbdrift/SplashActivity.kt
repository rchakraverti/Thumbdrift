package hu.ait.thumbdrift

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        scheduleSplashScreen()
    }

    private fun scheduleSplashScreen() {
        val splashScreenDuration = getSplashScreenDuration()
        Handler().postDelayed(
            {
                routeToAppropriatePage()
                finish()
            },
            splashScreenDuration
        )
    }

    private fun getSplashScreenDuration() = 3000L

    private fun routeToAppropriatePage() {

        var intentDetails = Intent()
        intentDetails.setClass(this@SplashActivity, LoginActivity::class.java)

        startActivity(intentDetails)
    }

}

