package roberto.cano.superheroapp.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import coil.load
import com.google.gson.Gson
import roberto.cano.superheroapp.data.model.response.ConnectionsModel
import roberto.cano.superheroapp.databinding.ConnectionDialogBinding

class ConnectionsDialog : DialogFragment() {
    private lateinit var binding : ConnectionDialogBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =  ConnectionDialogBinding.inflate(layoutInflater)
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
            val info = Gson().fromJson( it , ConnectionsModel::class.java)

            binding.relatives.setText( info.relatives )
            binding.superheroName.setText( info.name )
            binding.affiliation.setText( info.teamName )

            arguments?.getString("image").let {
                binding.imageView.load(it)
            }
        }
    }

}