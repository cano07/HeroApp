package roberto.cano.superheroapp.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import coil.load
import com.google.gson.Gson
import roberto.cano.superheroapp.R
import roberto.cano.superheroapp.data.model.response.BiographyModel
import roberto.cano.superheroapp.data.model.response.WorkModel
import roberto.cano.superheroapp.databinding.BiographyDialogBinding
import roberto.cano.superheroapp.databinding.ConnectionDialogBinding
import roberto.cano.superheroapp.databinding.JobDialogBinding

class JobDialog : DialogFragment() {
    private lateinit var binding : JobDialogBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =  JobDialogBinding.inflate(layoutInflater)
        return activity?.let {
            initComponents()
            setListeners()
            builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            builder.create()
        }?: throw IllegalStateException("This was no longer available")
    }

    private fun setListeners() = with(binding){

        backButton.setOnClickListener {
            dismiss()
        }
    }

    private fun initComponents() {
        arguments?.getString("data").let {
            val info = Gson().fromJson( it , WorkModel::class.java)


            binding.base .setText( info.base )
            binding.superheroName.setText( info.name )
            binding.occupation.setText( info.title )

            arguments?.getString("image").let {
                binding.imageView.load(it)
            }
        }
    }

}