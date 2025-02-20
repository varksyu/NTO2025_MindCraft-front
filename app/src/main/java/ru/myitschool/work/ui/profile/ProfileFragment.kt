package ru.myitschool.work.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import ru.myitschool.work.R
import ru.myitschool.work.databinding.FragmentProfileBinding
import ru.myitschool.work.ui.login.EntryActivity
import ru.myitschool.work.ui.qr.scan.QrScanFragment
import ru.myitschool.work.utils.collectWithLifecycle

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _viewBinding: FragmentProfileBinding? = null
    private val viewBinding: FragmentProfileBinding get() = _viewBinding!!

    private val viewModel by viewModels<ProfileViewModel> { ProfileViewModel.Factory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = FragmentProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


        viewBinding.refresh.setOnClickListener { viewModel.clickRefresh() }
        viewBinding.refreshForProfile.setOnClickListener { viewModel.clickRefresh() }
        viewBinding.logout.setOnClickListener {
            val intent = Intent(requireContext(), EntryActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        viewBinding.qrScan.setOnClickListener{
            val qrScanFragment = QrScanFragment()
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, qrScanFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        viewModel.state.collectWithLifecycle(this) { state ->

            viewBinding.error.visibility = if (state is ProfileViewModel.State.Error) View.VISIBLE else View.GONE
            viewBinding.loading.visibility = if (state is ProfileViewModel.State.Loading) View.VISIBLE else View.GONE
            viewBinding.profile.visibility = if (state is ProfileViewModel.State.Show) View.VISIBLE else View.GONE



            when(state) {
                is ProfileViewModel.State.Loading -> Unit
                is ProfileViewModel.State.Show -> {
                    viewBinding.noData.visibility = View.GONE
                    viewBinding.name.text = state.profileInfo.name
                    viewBinding.position.text = "Должность: ${state.profileInfo.name}"
                    if (state.profileInfo.lastEntry == null) viewBinding.lastEntry.text = "Время последнего входа: Нет данных"
                    else viewBinding.lastEntry.text = "Время последнего входа: ${state.profileInfo.lastEntry}"
                    Picasso.get().load(state.profileInfo.avatarUrl).resize(100, 100).centerCrop().into(viewBinding.imageView)

                    if (state.entrancesList.size == 0) {
                        viewBinding.noData.visibility = View.VISIBLE
                    }

                }
                is ProfileViewModel.State.Error -> {
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