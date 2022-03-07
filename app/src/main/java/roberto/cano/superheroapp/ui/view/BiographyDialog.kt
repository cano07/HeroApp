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
import roberto.cano.superheroapp.databinding.BiographyDialogBinding

class BiographyDialog : DialogFragment() {
    private lateinit var binding : BiographyDialogBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =  BiographyDialogBinding.inflate(layoutInflater)
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
            val info = Gson().fromJson( it , BiographyModel::class.java)


            binding.realName.setText( info.realName )
            binding.superheroName.setText( info.name )
            binding.comicDebut.setText( info.comicDebut )
            binding.aliases.setText( info.otherNames?.first() )
            binding.publiser.setText( info.publisher )
            binding.provenance.setText( info.provenance )
            binding.alterego.setText( info.alterego )
            binding.alignment.setText( info.alignment )

            arguments?.getString("image").let {
                binding.imageView.load(it)
            }
        }
    }

    fun putDataOnScreen(  ) {

    }

}