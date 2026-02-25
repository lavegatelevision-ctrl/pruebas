package com.lavegatelevision.tv.ui

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavegatelevision.tv.R
import com.lavegatelevision.tv.model.WpPost

class PostAdapter(
    private val onPostSelected: (WpPost) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val posts = mutableListOf<WpPost>()

    fun submitPosts(items: List<WpPost>) {
        posts.clear()
        posts.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view, onPostSelected)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    class PostViewHolder(
        itemView: View,
        private val onPostSelected: (WpPost) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.postTitle)
        private val excerpt: TextView = itemView.findViewById(R.id.postExcerpt)

        fun bind(post: WpPost) {
            title.text = Html.fromHtml(post.title.rendered, Html.FROM_HTML_MODE_COMPACT)
            excerpt.text = Html.fromHtml(post.excerpt.rendered, Html.FROM_HTML_MODE_COMPACT)

            itemView.setOnClickListener { onPostSelected(post) }
            itemView.setOnFocusChangeListener { _, hasFocus ->
                itemView.alpha = if (hasFocus) 1.0f else 0.85f
            }
        }
    }
}
