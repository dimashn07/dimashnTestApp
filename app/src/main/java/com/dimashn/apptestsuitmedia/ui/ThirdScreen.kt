package com.dimashn.apptestsuitmedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimashn.apptestsuitmedia.viewmodel.ThirdScreenViewModel
import com.dimashn.apptestsuitmedia.data.User
import com.dimashn.apptestsuitmedia.databinding.ThirdScreenBinding
import com.dimashn.apptestsuitmedia.utils.UserAdapter

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ThirdScreenBinding
    private lateinit var viewModel: ThirdScreenViewModel
    private lateinit var adapter: UserAdapter

    private var isLastItemReached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Third Screen"
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel = ViewModelProvider(this).get(ThirdScreenViewModel::class.java)
        adapter = UserAdapter()

        setupRecyclerView()
        observeViewModel()

        viewModel.loadData(page = 1, perPage = 10)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                isLastItemReached = lastVisibleItemPosition == totalItemCount - 1

                if (isLastItemReached && viewModel.listUsers.value?.isNotEmpty() == true && !recyclerView.canScrollVertically(1)) {
                    showLoadNextPageButton()
                } else {
                    hideLoadNextPageButton()
                }
            }
        })

        binding.loadMoreButton.setOnClickListener {
            viewModel.loadNextPage()
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@ThirdScreen, SecondScreen::class.java)
                intent.putExtra("FIRST_NAME", data.first_name)
                intent.putExtra("LAST_NAME", data.last_name)
                startActivity(intent)
            }
        })

    }

    private fun setupRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.listUsers.observe(this) { users ->
            adapter.updateList(users)

            if (users.isNotEmpty()) {
                binding.emptyTextView.visibility = View.GONE
                showLoadNextPageButton()
            } else {
                binding.emptyTextView.visibility = View.VISIBLE
                hideLoadNextPageButton()
            }
        }
    }

    private fun showLoadNextPageButton() {
        binding.loadMoreButton.visibility = View.VISIBLE
    }

    private fun hideLoadNextPageButton() {
        binding.loadMoreButton.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, SecondScreen::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
