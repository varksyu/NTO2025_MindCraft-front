package ru.myitschool.work.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.work.R
import ru.myitschool.work.databinding.FragmentLoginBinding
import ru.myitschool.work.utils.collectWhenStarted
import ru.myitschool.work.utils.visibleOrGone

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _viewBinding: FragmentLoginBinding? = null
    private val viewBinding: FragmentLoginBinding get() = _viewBinding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentLoginBinding.bind(view)


        /*viewBinding.signInButton.setOnClickListener {
            val login = viewBinding.userLogin.text.toString()
            val password = viewBinding.userPassword.text.toString()
            if (!isValidEmail(login)) {
                viewBinding.errorText.text = getString(R.string.error_valid)
                viewBinding.errorText.visibility = View.VISIBLE
            }
            else if (!isValidPassword(password)) {
                viewBinding.errorText.text = getString(R.string.error_valid)
                viewBinding.errorText.visibility = View.VISIBLE
            }
            else {
                viewModel.auth(email, password)
                viewBinding.errorText.visibility = View.GONE
            }
        }

        viewModel.state.collectWithLifecycle(this) { state ->
            if (state is AuthViewModel.State.Show) {
                viewBinding.errorText.text = state.errorText.toString()
                viewBinding.errorText.visibility =
                    if (state.errorText == null) View.GONE else View.VISIBLE
            }
        }

        viewModel.navigateToMain.collectWithLifecycle(viewLifecycleOwner) { userRole ->
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
                putExtra("USER_ROLE", userRole)
            }
            startActivity(intent)
            requireActivity().finish()
        }

        viewBinding.userLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) {

            }

        })*/
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidPassword(password : String) : Boolean {
        return password.length >= 8
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

}