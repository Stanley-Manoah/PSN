package atomsandbots.android.playsportsnetwork.UI

import android.os.Bundle
import android.widget.TextView
import atomsandbots.android.playsportsnetwork.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment


class VideoActivity : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        //receive bundle
        val bundle :Bundle ?=intent.extras
        val title = bundle!!.getString("Title")
        val description = bundle.getString("Description")
        val videoID = bundle.getString("VideoID")

        val videoTitle = findViewById<TextView>(R.id.videoTitle)
        val videoDescription = findViewById<TextView>(R.id.videoDescription)


        videoTitle.text = title
        videoDescription.text = description

        val youtubeFragment =
            fragmentManager.findFragmentById(R.id.youtubeFragment) as YouTubePlayerFragment
        youtubeFragment.initialize(
            Constants.API_KEY,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    //cue video
                    youTubePlayer.cueVideo(videoID)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })


    }
}