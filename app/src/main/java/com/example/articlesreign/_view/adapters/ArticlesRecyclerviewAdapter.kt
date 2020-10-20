package com.example.articlesreign._view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.articlesreign.R
import com.example.articlesreign._model.local.articles.ArticlesEntity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ArticlesRecyclerviewAdapter(
    var context: Context,
    var listArticles: MutableList<ArticlesEntity>
):
    RecyclerView.Adapter<ArticlesRecyclerviewAdapter.ArticlesViewHolder>() {


    var mInflater: LayoutInflater
    lateinit var listener: ArticlesRecyclerviewAdapterListener
    private var deleteVisible: Int = -1

    private val viewBinderHelper = ViewBinderHelper()


    class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewTitle: TextView
        var textViewautor: TextView
        var textViewDate: TextView
        var constraintclick: ConstraintLayout
        var buttonDelete: TextView
        var swipeRevealLayout: SwipeRevealLayout
        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewautor = itemView.findViewById(R.id.textViewautor)
            textViewDate = itemView.findViewById(R.id.textViewDate)
            constraintclick = itemView.findViewById(R.id.constraintclick)
            buttonDelete = itemView.findViewById(R.id.buttonDelete)
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout)
        }
    }
    init {
        mInflater = LayoutInflater.from(context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val itemView = mInflater.inflate(R.layout.item_swipe, parent, false)

        //itemView.setOnClickListener(this);
        return ArticlesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {

        val article = listArticles[position]
        viewBinderHelper.bind(holder.swipeRevealLayout, article.objectID);
        viewBinderHelper.setOpenOnlyOne(true)

        if(!article.title.equals(null)){
            holder.textViewTitle.text = "${article.title}"
        }
        else if(!article.story_title.equals(null)){
            holder.textViewTitle.text = "${article.story_title}"
        }

        holder.textViewautor.text = "${article.author}"

        if(!article.created_at.equals(null)){
            val formatString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date1: Date
            date1 = formatString.parse(article.created_at)

            val date2 = Date()
            val diff: Long = date2.time - date1.time

            val hour: Long = TimeUnit.HOURS.convert(diff,TimeUnit.MILLISECONDS)
            when{
                (hour==0.toLong())->{
                    val min: Long = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
                    holder.textViewDate.text = "${min} ${context.resources.getString(R.string.min)}"
                }
                (hour < 24 )->{
                    holder.textViewDate.text = "${hour}${context.resources.getString(R.string.hour)}"
                }
                (hour in 25..47)->{
                    holder.textViewDate.text = context.resources.getString(R.string.yesterdar)
                }
                else->{
                    val dias: Long = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
                    holder.textViewDate.text = "${context.resources.getString(R.string.ago)} ${dias}  ${context.resources.getString(R.string.days)}"
                }

            }

        }

        holder.constraintclick.setOnClickListener {
            listener.onClick(article)
        }
        holder.buttonDelete.setOnClickListener {
            listener.onDeleteItem(article, position)
        }

    }

    fun remove(position: Int) {
        listArticles.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listArticles.size)
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }
    fun setListner(listener: ArticlesRecyclerviewAdapterListener) {
        this.listener = listener
    }


    interface ArticlesRecyclerviewAdapterListener {
        fun onClick(articles: ArticlesEntity)
        fun onDeleteItem(articles: ArticlesEntity, pos: Int)
    }
}