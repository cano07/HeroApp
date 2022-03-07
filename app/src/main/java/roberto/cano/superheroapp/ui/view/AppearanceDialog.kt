package roberto.cano.superheroapp.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import coil.load
import com.google.gson.Gson
import roberto.cano.superheroapp.R
import roberto.cano.superheroapp.data.model.response.AppearanceModel
import roberto.cano.superheroapp.data.model.response.BiographyModel
import roberto.cano.superheroapp.databinding.AppearanceDialogBinding
import roberto.cano.superheroapp.databinding.BiographyDialogBinding
import roberto.cano.superheroapp.databinding.ConnectionDialogBinding

class AppearanceDialog : DialogFragment() {
    private lateinit var binding : AppearanceDialogBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =  AppearanceDialogBinding.inflate(layoutInflater)
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
            val info = Gson().fromJson( it , AppearanceModel::class.java)

            when (info.gender){
                "Male" -> {
                    binding.gender.foreground = ContextCompat.getDrawable( binding.root.context , R.drawable.ic_baseline_male_24)
                }
                "Female" -> {
                    binding.gender.foreground = ContextCompat.getDrawable( binding.root.context , R.drawable.ic_female) }
                else -> {
                    binding.gender.foreground = ContextCompat.getDrawable( binding.root.context , R.drawable.ic_unknown)}
            }

            binding.race.setText( info.race )
            binding.superheroName.setText( info.name )
            binding.height.setText( info.height?.get(1).toString() )
            binding.hair.setText( info.hairColor )
            binding.eyes.setText( info.eyesColor )
            binding.weight.setText( info.weight?.get(1).toString() )

            arguments?.getString("image").let {
                binding.imageView.load(it)
            }
        }
    }


}