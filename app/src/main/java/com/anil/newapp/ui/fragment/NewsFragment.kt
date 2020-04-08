package com.anil.newapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.anil.newapp.R
import com.anil.newapp.persistance.entitiy.Article
import com.anil.newapp.ui.adapter.NewsAdapter
import com.anil.newapp.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModel()

    private val newsAdapter by lazy {
        NewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.articles.observe(
            viewLifecycleOwner, Observer {
                newsAdapter.submitList(it)
            }
        )
        newsViewModel.networkError.observe(
            viewLifecycleOwner, Observer {
                // notify(it)
            }
        )
        newsViewModel.articleErrors.observe(
            viewLifecycleOwner, Observer { isNetworkError ->
                if (isNetworkError) {
                    showSnackBarWithCloseButton(
                        view,
                        getString(R.string.internet_connection_problem)
                    )
                }
            }
        )
        val layoutManager = GridLayoutManager(context, 2)

        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 7 == 0) 2 else 1
            }
        }
        recyclerViewNews.layoutManager = layoutManager
        recyclerViewNews.adapter = newsAdapter
        newsAdapter.itemSelected = {
            onArticleSelected(it)
        }
        networkErrorListener()
        githubErrorListener()
    }


    private fun networkErrorListener() {
        newsViewModel.networkError.observe(viewLifecycleOwner, Observer { isNetworkError ->
            if (isNetworkError) {
                //       activityGeneralMessagesUtils.showSnackBarWithCloseButton(getString(R.string.internet_connection_problem))
            }
        })

        newsViewModel.networkState.observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected) {
            }
        })
    }

    private fun githubErrorListener() {
        newsViewModel.articleErrors.observe(viewLifecycleOwner, Observer { isGithubError ->
            if (isGithubError) {
                // activityGeneralMessagesUtils.showSnackBarWithCloseButton(getString(R.string.github_error))
            }
        })
    }

    private fun showSnackBarWithCloseButton(view: View, message: String) {
        val snackbar =
            Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .apply {
                    setAction(getString(R.string.ok)) { dismiss() }
                }
        snackbar.show()
    }


    private fun notify(message: String) =
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    private fun onArticleSelected(
        article: Article
    ) {
        article.url?.let {
            val direction = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(
                it
            )
            findNavController().navigate(direction)
        }


    }

}
