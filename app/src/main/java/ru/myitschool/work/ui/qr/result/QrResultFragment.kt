package ru.myitschool.work.ui.qr.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.myitschool.work.R
import ru.myitschool.work.databinding.FragmentProfileBinding
import ru.myitschool.work.databinding.FragmentQrResultBinding
import ru.myitschool.work.ui.profile.ProfileViewModel
import ru.myitschool.work.ui.qr.scan.QrScanDestination
import ru.myitschool.work.utils.collectWithLifecycle

class QrResultFragment : Fragment(R.layout.fragment_qr_result) {
    private var _viewBinding: FragmentQrResultBinding? = null
    private val viewBinding: FragmentQrResultBinding get() = _viewBinding!!

    private val viewModel by viewModels<QrResultViewModel> { QrResultViewModel.Factory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _viewBinding = FragmentQrResultBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            QrScanDestination.REQUEST_KEY, viewLifecycleOwner
        ) {
            key, bundle ->
            if (key == QrScanDestination.REQUEST_KEY) {
                val code = QrScanDestination.getDataIfExist(bundle)
                viewModel.sendResult(code)
            }
        }
        viewBinding.close.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_qr_result_to_fragment_profile)
        }

        viewModel.state.collectWithLifecycle(this) { state ->

            viewBinding.error.visibility = if (state is QrResultViewModel.State.Error) View.VISIBLE else View.GONE
            viewBinding.loading.visibility = if (state is QrResultViewModel.State.Loading) View.VISIBLE else View.GONE
            viewBinding.result.visibility = if (state is QrResultViewModel.State.Show) View.VISIBLE else View.GONE


            when(state) {
                is QrResultViewModel.State.Loading -> Unit
                is QrResultViewModel.State.Show -> {
                    viewBinding.result.text = state.result

                }
                is QrResultViewModel.State.Error -> {
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


