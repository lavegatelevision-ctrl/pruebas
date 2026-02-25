package com.lavegatelevision.tv

import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PostDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val titleView: TextView = findViewById(R.id.detailTitle)
        val contentView: TextView = findViewById(R.id.detailContent)
        val linkView: TextView = findViewById(R.id.detailLink)

        val rawTitle = intent.getStringExtra(MainActivity.EXTRA_TITLE).orEmpty()
        val rawContent = intent.getStringExtra(MainActivity.EXTRA_CONTENT).orEmpty()
        val rawUrl = intent.getStringExtra(MainActivity.EXTRA_URL).orEmpty()

        titleView.text = Html.fromHtml(rawTitle, Html.FROM_HTML_MODE_COMPACT)
        contentView.text = Html.fromHtml(rawContent, Html.FROM_HTML_MODE_COMPACT)
        linkView.text = getString(R.string.source_link, rawUrl)
    }
}
