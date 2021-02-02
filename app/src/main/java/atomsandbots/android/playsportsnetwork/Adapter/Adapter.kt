package atomsandbots.android.playsportsnetwork.Adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import atomsandbots.android.playsportsnetwork.Data.Model
import atomsandbots.android.playsportsnetwork.R
import atomsandbots.android.playsportsnetwork.UI.VideoActivity
import com.squareup.picasso.Picasso

class Adapter(private val videoList: ArrayList<Model>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate video layout item
        val v = LayoutInflater.from(parent.context).inflate(R.layout.video_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(videoList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, VideoActivity::class.java)
            //bundled data for video activity
            val b = Bundle()
            b.putString("Title", videoList[position].title)
            b.putString("Description", videoList[position].description)
            b.putString("VideoID", videoList[position].videoID)
            intent.putExtras(b)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //return size of array
        return videoList.size
    }
    //View holder containing items for display
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: Model) {
            val textViewName = itemView.findViewById(R.id.title_text_view) as TextView
            val imageView = itemView.findViewById(R.id.image_view) as ImageView
            textViewName.text = model.title
            Picasso.get().load(model.thumbnail).placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)

        }
    }
}