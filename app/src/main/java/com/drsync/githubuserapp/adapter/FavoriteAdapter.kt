package com.drsync.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.drsync.githubuserapp.data.local.UserEntity
import com.drsync.githubuserapp.databinding.ItemDataBinding

class FavoriteAdapter(private val listFavorite: List<UserEntity>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = listFavorite[position]
        Glide.with(holder.itemView.context)
            .load(favorite.avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItem)
        holder.binding.tvUserName.text = favorite.login
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class ViewHolder(val binding : ItemDataBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserEntity)
    }
}