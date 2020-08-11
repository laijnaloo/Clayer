package com.example.clayer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders


class webbpageFragment : Fragment() {

    companion object {
        fun newInstance() = webbpageFragment()
    }

    private lateinit var viewModel: WebbpageViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.webbpage_fragment, container, false)

        val url = "https://www.ceramicscommunity.com/"
        //val url = "https://www.google.com/"
        val view = rootView.findViewById<View>(R.id.webview) as WebView
        view.settings.javaScriptEnabled = true
        view.loadUrl(url)

        return rootView
        //return inflater.inflate(R.layout.webbpage_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WebbpageViewModel::class.java)
        // TODO: Use the ViewModel


    }

}