package atomsandbots.android.playsportsnetwork.UI

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import atomsandbots.android.playsportsnetwork.R

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
            //Splash out in 2 seconds and start MainActivity
        }, 2000)

    }

}