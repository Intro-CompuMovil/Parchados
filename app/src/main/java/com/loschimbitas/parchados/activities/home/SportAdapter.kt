package com.loschimbitas.parchados.activities.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.loschimbitas.parchados.R

class SportsAdapter(context: Context, private val sportsList: List<Sport>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return sportsList.size
    }

    override fun getItem(position: Int): Any {
        return sportsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_sport, parent, false)
            holder = ViewHolder()
            holder.sportIcon = view.findViewById(R.id.imageview_sport_icon)
            holder.sportName = view.findViewById(R.id.textview_sport_name)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val sport = getItem(position) as Sport
        holder.sportIcon.setImageResource(sport.iconResourceId)
        holder.sportName.text = sport.name

        return view
    }

    private class ViewHolder {
        lateinit var sportIcon: ImageView
        lateinit var sportName: TextView
    }
}
