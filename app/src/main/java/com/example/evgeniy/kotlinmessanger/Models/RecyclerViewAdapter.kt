package com.example.evgeniy.kotlinmessanger.Models

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.evgeniy.kotlinmessanger.R
import com.example.evgeniy.kotlinmessanger.databinding.RecyclerviewItemBinding
import kotlinx.android.synthetic.main.recyclerview_item.view.*

/**
 * Created by Evgeniy on 14.02.2018.
 */
class RecyclerViewAdapter(Dataset: ArrayList<Message>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var DataSet: ArrayList<Message>? = null

    init{
        this.DataSet = Dataset
    }

    override fun getItemCount(): Int {
        if(this.DataSet?.size == null)
            return 0
        return this.DataSet!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val message : Message? = DataSet?.get(position)
        holder?.binding?.messageViewItem = message
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        //Создаётся визуальный элемент для одной строки и все его дочерние элементы
        var v = LayoutInflater.from(parent?.context)
        var binding = RecyclerviewItemBinding.inflate(v, parent, false)
        //Создаём объект вспомогательного класса для хранения информации по одной строке
        return ViewHolder(binding.root)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        var binding : RecyclerviewItemBinding? = DataBindingUtil.bind(itemView)

    }
}