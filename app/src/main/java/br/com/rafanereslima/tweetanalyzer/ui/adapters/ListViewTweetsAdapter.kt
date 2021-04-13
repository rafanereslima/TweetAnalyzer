package br.com.rafanereslima.tweetanalyzer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.rafanereslima.tweetanalyzer.models.Data

class ListViewTweetsAdapter(val context: Context, val listModelArrayList: ArrayList<Data>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.item_list_tweets, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.tvTitle.text = listModelArrayList[position].text
        //vh.tvContent.text = listModelArrayList[position].content
        return view
    }

    override fun getItem(position: Int): Any {
        return listModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listModelArrayList.size
    }
}

private class ViewHolder(view: View?) {
    val tvTitle: TextView = view?.findViewById<TextView>(R.id.tvTitle) as TextView
    //val tvContent: TextView = view?.findViewById<TextView>(R.id.tvContent) as TextView
}