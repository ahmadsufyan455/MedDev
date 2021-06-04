package com.capstone.meddev.dashboard.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.meddev.data.ArticleResponse
import com.capstone.meddev.databinding.RvItemLayoutBinding
import com.squareup.picasso.Picasso

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private val listArticle = ArrayList<ArticleResponse>()

    fun setData(list: List<ArticleResponse>) {
        listArticle.clear()
        listArticle.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }

    override fun getItemCount(): Int = listArticle.size

    class ViewHolder(private val binding: RvItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleResponse) {
            binding.articleTitleTxt.text = article.post_title
            binding.tvWebName.text = article.name
            Picasso.get()
                .load(article.url)
                .into(binding.articleImageImg)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticleActivity::class.java).apply {
                    putExtra("post_url", article.post_url)
                }
                it.context.startActivity(intent)
            }
        }
    }
}