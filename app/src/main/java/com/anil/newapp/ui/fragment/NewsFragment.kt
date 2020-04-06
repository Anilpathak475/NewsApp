package com.anil.newapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anil.newapp.R
import com.anil.newapp.base.gone
import com.anil.newapp.base.visible
import com.anil.newapp.model.Article
import com.anil.newapp.model.ArticleResponse
import com.anil.newapp.networking.Resource
import com.anil.newapp.networking.Status
import com.anil.newapp.ui.adapter.NewsAdapter
import com.anil.newapp.ui.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModel()

    private val newsAdapter by lazy {
        NewsAdapter {
            onArticleSelected(it)
        }
    }

    private val newsObserver by lazy {
        Observer<Resource<ArticleResponse>> {
            when (it.status) {
                Status.SUCCESS -> {
                    loading.gone()
                    it.data?.articles?.let { newsAdapter.articals = it }
                }
                Status.ERROR -> {
                    it.message?.let { it1 -> notify(it1) }
                }
                Status.LOADING -> {
                    loading.visible()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.articleResponse.observe(
            viewLifecycleOwner, newsObserver)
        recyclerViewNews.adapter = newsAdapter
    }

    private fun notify(message: String) =
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    private fun onArticleSelected(
        article: Article
    ) {

    }

}
