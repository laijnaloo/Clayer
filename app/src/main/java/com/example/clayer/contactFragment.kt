package com.example.clayer

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import net.glxn.qrgen.android.QRCode
import kotlinx.android.synthetic.main.contact_fragment.*
import kotlinx.android.synthetic.main.contact_fragment.view.*
import kotlinx.android.synthetic.main.contact_fragment.view.contact_phone
import java.net.URI
import java.util.jar.Manifest

class contactFragment : Fragment() {

    companion object {
        fun newInstance() = contactFragment()
    }

    private lateinit var viewModel: ContactViewModel
    private lateinit var viewOfLayout: View
    private var mImagePreview: ImageView? = null

    var REQUEST_CALL = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater!!.inflate(R.layout.contact_fragment, container, false)
        var phoneNumber = viewOfLayout.findViewById<TextView>(R.id.contact_phone)
        var email = viewOfLayout.findViewById<TextView>(R.id.contact_email)
        mImagePreview = viewOfLayout.findViewById(R.id.imagePreview) as ImageView


        phoneNumber.setOnClickListener {
            makePhoneCall()
        }

        email.setOnClickListener {
            openMail()
        }

        createQRCode()

        return viewOfLayout
        //return inflater.inflate(R.layout.contact_fragment, container, false)
    }


    fun makePhoneCall(){
        var number = contact_phone.text.toString()
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(android.Manifest.permission.CALL_PHONE), REQUEST_CALL)
        } else {
            var dial = "tel:" + number
            var intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse(dial))
            startActivity(intent)
        }
    }

    fun openMail(){
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:" + contact_email.text.toString())
        //mIntent.type = "text/plain"
        //mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contact_email.text))

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun createQRCode() {
        var text = "mailto:contact@clayer.com"
        val bitmap = QRCode.from(text).withSize(1000, 1000).bitmap()
        (mImagePreview as ImageView).setImageBitmap(bitmap)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if(requestCode == REQUEST_CALL){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ){
                makePhoneCall()


            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        // TODO: Use the ViewModel
    }

}