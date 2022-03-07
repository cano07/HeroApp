package roberto.cano.superheroapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import roberto.cano.superheroapp.R
import roberto.cano.superheroapp.data.model.response.HeroResultResponse
import roberto.cano.superheroapp.databinding.ItemHeroAdapterBinding

class HeroAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<HeroResultResponse, HeroAdapter.ViewHolder>(FYVTagsAdapterCallback()) {
    var isEditable = true
    var page = 0

    override fun onBindViewHolder(holder: HeroAdapter.ViewHolder, position: Int) {

        if (currentList.size == holder.adapterPosition +1){
            onClickListener.loadNewItems(page+1)
        }

        holder.binding.position.setText( (holder.adapterPosition +1).toString() )

        when (currentList[holder.adapterPosition].appereance?.gender){
            "Male" -> { changeButtonColor( holder , ContextCompat.getColor( holder.binding.root.context ,R.color.purple_500) )}
            "Female" -> { changeButtonColor( holder , ContextCompat.getColor( holder.binding.root.context ,R.color.purple_200) )}
            else -> { changeButtonColor( holder , ContextCompat.getColor( holder.binding.root.context ,R.color.teal_200) )}
        }

        holder.binding.superheroImage.load(currentList[holder.adapterPosition].image?.imageUrl)

        holder.binding.superheroName.setText(currentList[ holder.adapterPosition ].name)

        holder.binding.jobsButton.setOnClickListener {
            onClickListener.onClickListener(
                ButtonType.JOB ,
                currentList[holder.adapterPosition] )
        }
        holder.binding.connectionsButton.setOnClickListener {
            onClickListener.onClickListener(
                ButtonType.CONNECTION,
                currentList[holder.adapterPosition]
            )
        }
        holder.binding.appearanceButton.setOnClickListener {
            onClickListener.onClickListener(
                ButtonType.APPEARANCE,
                currentList[holder.adapterPosition]
            )
        }
        holder.binding.statsButton.setOnClickListener {
            onClickListener.onClickListener(ButtonType.STAT, currentList[holder.adapterPosition])
        }
        holder.binding.biographyButton.setOnClickListener {
            onClickListener.onClickListener(
                ButtonType.BIOGRAPHY,
                currentList[holder.adapterPosition]
            )
        }

    }

    fun changeButtonColor(holder: ViewHolder, color : Int) {
        holder.binding.biographyButton.setBackgroundColor(color)
        holder.binding.connectionsButton.setBackgroundColor(color)
        holder.binding.jobsButton.setBackgroundColor(color)
        holder.binding.statsButton.setBackgroundColor(color)
        holder.binding.appearanceButton.setBackgroundColor(color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroAdapter.ViewHolder {
        val binding =
            ItemHeroAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = currentList.size

    fun isEditable(isEditable: Boolean) {
        this.isEditable = isEditable
    }

    inner class ViewHolder(val binding: ItemHeroAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    enum class ButtonType { STAT, BIOGRAPHY, CONNECTION , APPEARANCE, JOB }

    interface OnClickListener{
        fun onClickListener(option: ButtonType, item : HeroResultResponse)
        fun loadNewItems( page : Int)
    }

    class FYVTagsAdapterCallback : DiffUtil.ItemCallback<HeroResultResponse>() {
        override fun areItemsTheSame(oldItem: HeroResultResponse, newItem: HeroResultResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HeroResultResponse, newItem: HeroResultResponse): Boolean {
            return oldItem.equals(newItem)
        }
    }
}