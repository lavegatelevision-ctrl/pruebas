package com.lavegatelevision.tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lavegatelevision.tv.api.ApiClient
import com.lavegatelevision.tv.model.WpPost
import com.lavegatelevision.tv.model.WpRendered
import com.lavegatelevision.tv.model.WpSearchItem
import com.lavegatelevision.tv.ui.PostAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.postsRecyclerView)
        progressBar = findViewById(R.id.loadingProgress)
        errorText = findViewById(R.id.errorText)

        adapter = PostAdapter { post -> openPostDetail(post) }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadContent()
    }

    private fun loadContent() {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE

        lifecycleScope.launch {
            runCatching {
                val postsRequest = async { ApiClient.wordPressApi.getPosts() }
                val pagesRequest = async { ApiClient.wordPressApi.getPages() }

                val merged = (postsRequest.await() + pagesRequest.await())
                    .distinctBy { "${it.type}-${it.id}" }
                    .sortedByDescending { it.date }

                if (merged.isNotEmpty()) {
                    merged
                } else {
                    ApiClient.wordPressApi.searchContent().map { it.toWpPost() }
                }
            }.onSuccess { content ->
                renderPosts(content)
            }.onFailure {
                showError(getString(R.string.error_loading_posts))
            }
        }
    }

    private fun WpSearchItem.toWpPost(): WpPost {
        return WpPost(
            id = id,
            type = subtype,
            title = WpRendered(title),
            excerpt = WpRendered(getString(R.string.search_result_excerpt)),
            content = WpRendered(getString(R.string.search_result_content, url)),
            postUrl = url
        )
    }

    private fun renderPosts(posts: List<WpPost>) {
        progressBar.visibility = View.GONE
        if (posts.isEmpty()) {
            showError(getString(R.string.error_empty_posts))
            return
        }
        adapter.submitPosts(posts)
        recyclerView.requestFocus()
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        errorText.text = message
        errorText.visibility = View.VISIBLE
    }

    private fun openPostDetail(post: WpPost) {
        val intent = Intent(this, PostDetailActivity::class.java).apply {
            putExtra(EXTRA_TITLE, post.title.rendered)
            putExtra(EXTRA_CONTENT, post.content.rendered)
            putExtra(EXTRA_URL, post.postUrl)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_URL = "extra_url"
    }
}
