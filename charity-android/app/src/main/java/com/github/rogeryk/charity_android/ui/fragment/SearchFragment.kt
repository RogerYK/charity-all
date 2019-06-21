package com.github.rogeryk.charity_android.ui.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.data.ProjectBasic
import com.github.rogeryk.charity_android.ui.activity.ProjectDetailActivity
import com.github.rogeryk.charity_android.utils.castTo
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : CoroutineFragment() {

    lateinit var  api: Api
        @Inject set

    lateinit var searchHistory: MutableSet<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.let {  it.application.castTo<App>().appComponent.inject(this)}
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        readSearchHistory()
        val flexLayout = FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP)
        search_history.layoutManager = flexLayout
        val tagAdapter = TagAdapter(searchHistory.toList())
        tagAdapter.setOnItemClick(this::historySearch)
        search_history.adapter = tagAdapter

        val linearLayout = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        search_result_list.layoutManager = linearLayout

        search_text.setOnEditorActionListener { _, _, _ ->
            search()
            return@setOnEditorActionListener true
        }
        search_done_btn.setOnClickListener { search() }
        search_history_delete.setOnClickListener {
            searchHistory.clear()
            search_history.adapter = TagAdapter(listOf())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveSearchHistory()
    }

    fun historySearch(name: String) {
        search_text.text.clear()
        search_text.text.append(name)
        search()
    }

    fun search()  = launch {
        val text = search_text.text.toString()
        if (text.isEmpty()) return@launch
        searchHistory.add(text)
        search_history.adapter = TagAdapter(searchHistory.toList())
        search_history_layout.visibility = View.GONE

        val res = api.project.byName(text).await()
        if (res.errCode == 0) {
            res.data?.let {
                search_result_list.adapter = ProjectAdapter(it.content, context!!)
                search_result_layout.visibility = View.VISIBLE
            }
        }
    }

    fun readSearchHistory() {
        val sf = context!!.getSharedPreferences("search", Context.MODE_PRIVATE)
        searchHistory = sf.getStringSet("search_history", mutableSetOf())!!
    }

    fun saveSearchHistory() {
        val sf = context!!.getSharedPreferences("search", Context.MODE_PRIVATE)
        sf.edit().apply {
            putStringSet("search_history", searchHistory)
            apply()
        }

    }


}

class TagAdapter(private val tags: List<String>) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    private val listeners: MutableList<(String) -> Unit> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val textView = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return ViewHolder(textView as TextView)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = tags[position]
        holder.textView.setOnClickListener {
            Log.i("history click", tags[position])
            onItemClick(tags[position])
        }

    }

    private fun onItemClick(item: String) {
        listeners.forEach {it(item)}
    }

    fun setOnItemClick(listener: (String)->Unit) {
        listeners.add(listener)
    }
}

class ProjectAdapter(private val projects: List<ProjectBasic>,
    private val context: Context
): RecyclerView.Adapter<ProjectAdapter.ViewHolder>(){

    class ViewHolder(v:View,
                     val img: ImageView,
                     val name: TextView ): RecyclerView.ViewHolder(v)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_project, parent, false)
        val holder: ViewHolder
        view.apply {
            holder = ViewHolder(
                    this,
                    img = findViewById(R.id.project_img),
                    name = findViewById(R.id.project_name)
            )
        }
        return holder
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        Picasso.with(context)
                .load(project.img)
                .into(holder.img)
        holder.name.text = project.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProjectDetailActivity::class.java)
            intent.putExtra("projectId", project.id)
            context.startActivity(intent)
        }
    }

}
