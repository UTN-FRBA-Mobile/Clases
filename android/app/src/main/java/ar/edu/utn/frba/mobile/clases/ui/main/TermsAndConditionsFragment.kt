package ar.edu.utn.frba.mobile.clases.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.databinding.TermsAndConditionsFragmentBinding


class TermsAndConditionsFragment: Fragment() {
    private var listener: Listener? = null

    private var _binding: TermsAndConditionsFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TermsAndConditionsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        WebView.setWebContentsDebuggingEnabled(true)
        _binding = TermsAndConditionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.setBackgroundColor(Color.TRANSPARENT)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.addJavascriptInterface(this, "Clases")
        val url = "file:///android_asset/termsAndConditions.html"
        binding.webView.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add(R.string.acceptMenu).setOnMenuItemClickListener {
            acceptTermsAndConditions()
            true
        }
        menu.add(R.string.dontAcceptMenu).setOnMenuItemClickListener {
            doNotAcceptTermsAndConditions()
            true
        }
    }

    @JavascriptInterface
    fun getAppName() = getString(R.string.app_name)

    @JavascriptInterface
    fun acceptTermsAndConditions() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, R.string.accept, Toast.LENGTH_LONG).show()
            listener?.goBack()
        }
    }

    @JavascriptInterface
    fun doNotAcceptTermsAndConditions() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, R.string.dontAccept, Toast.LENGTH_LONG).show()
        }
    }

    interface Listener {
        fun goBack()
    }
}