package ru.myitschool.work.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.myitschool.work.R
import ru.myitschool.work.databinding.FragmentAdminBinding
import ru.myitschool.work.databinding.FragmentProfileBinding
import ru.myitschool.work.ui.profile.ProfileViewModel
import ru.myitschool.work.utils.collectWithLifecycle
import ru.sicampus.bootcamp2025.ui.centerList.EntranceAdapter

class AdminFragment : Fragment(R.layout.fragment_admin) {
    private var _viewBinding: FragmentAdminBinding? = null
    private val viewBinding: FragmentAdminBinding get() = _viewBinding!!
    private val viewModel by viewModels<AdminViewModel> { AdminViewModel.Factory }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = FragmentAdminBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val adapter = EntranceAdapter()
        viewBinding.recyclerView.adapter = adapter

        viewBinding.findButton.setOnClickListener {
            val login = viewBinding.userLogin.text.toString()
            viewModel.search(login)
        }


    viewModel.state.collectWithLifecycle(this) { state ->

        viewBinding.error.visibility = if (state is AdminViewModel.State.Error) View.VISIBLE else View.GONE


        when(state) {
            is AdminViewModel.State.Loading -> Unit
            is AdminViewModel.State.Show -> {
                viewBinding.noData.visibility = View.GONE
                viewBinding.name.text = "Должность: ${state.profileInfo.position}"
                if (state.profileInfo.lastEntry == null) viewBinding.lastEntry.text = "Время последнего входа: Нет данных"
                else viewBinding.lastEntry.text = "Время последнего входа: ${state.profileInfo.lastEntry}"
                Picasso.get().load(state.profileInfo.avatarUrl).resize(100, 100).centerCrop().into(viewBinding.imageView)

                if (state.entrancesList.isEmpty()) {
                    viewBinding.noData.visibility = View.VISIBLE
                }
                else {
                    viewBinding.recyclerView.visibility = View.VISIBLE
                    adapter.submitList(state.entrancesList)

                }
            }
            is AdminViewModel.State.Error -> {
                viewBinding.errorText.text = state.text
            }
        }
        }
}

override fun onDestroyView() {
    _viewBinding = null
    super.onDestroyView()
}
}