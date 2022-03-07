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
import roberto.cano.superheroapp.data.model.response.PowerStatsModel
import roberto.cano.superheroapp.databinding.BiographyDialogBinding
import roberto.cano.superheroapp.databinding.StatsDialogBinding

class StatsDialog : DialogFragment() {
    private lateinit var binding : StatsDialogBinding
    private lateinit var builder : AlertDialog.Builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =  StatsDialogBinding.inflate(layoutInflater)
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
            val info = Gson().fromJson( it , PowerStatsModel::class.java)
            println(Gson().toJson(info))
            try{
                binding.combat.progress = (info.combat?:"0").toInt()
                binding.inteligence.progress = (info.intelligence?:"0").toInt()
                binding.speed.progress = ((info.speed)?:"0").toInt()
                binding.power.progress = (info.power?:"0").toInt()
                binding.durability.progress = (info.durability?:"0").toInt()
                binding.strenght.progress = (info.strengh?:"0").toInt()
            }catch ( e: Exception){

                binding.combat.progress =  0
                binding.inteligence.progress =  0
                binding.speed.progress =  0
                binding.power.progress =  0
                binding.durability.progress =  0
                binding.strenght.progress = 0
            }

            binding.superheroName.setText( info.name )

            arguments?.getString("image").let {
                binding.imageView.load(it)
            }
        }
    }

}