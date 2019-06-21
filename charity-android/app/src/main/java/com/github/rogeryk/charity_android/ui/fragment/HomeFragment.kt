package com.github.rogeryk.charity_android.ui.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.rogeryk.charity_android.App
import com.github.rogeryk.charity_android.R
import com.github.rogeryk.charity_android.api.Api
import com.github.rogeryk.charity_android.data.Banner
import com.github.rogeryk.charity_android.data.ProjectBasic
import com.github.rogeryk.charity_android.ui.activity.ProjectDetailActivity
import com.github.rogeryk.charity_android.utils.castTo
import com.squareup.picasso.Picasso
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(), CoroutineScope by CoroutineScope(Dispatchers.Main) {

    lateinit var api: Api
        @Inject set

    lateinit var bannerView: MZBannerView<Banner>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.let{it.application.castTo<App>().appComponent.inject(this)}
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bannerView = activity?.findViewById(R.id.banner)!!
        pullBanners()
//        pullHot()
        pullRecommend()

//        hot_grid_view.setOnItemClickListener { _, _, _, id ->
//            val intent = Intent(activity, ProjectDetailActivity::class.java)
//            intent.putExtra("projectId", id)
//            startActivity(intent)
//        }
        recommend_project_grid.setOnItemClickListener { _, _, _, id ->
            val intent = Intent(activity, ProjectDetailActivity::class.java)
            intent.putExtra("projectId", id)
            startActivity(intent)
        }
    }

    fun pullBanners() = launch {
            try {
                val banners = api.banner.all().await().data
                Log.i("banners", banners.toString())
                bannerView.setPages(banners, ::BannerViewHolder)
            } catch (e: Exception) {
                Log.e("errror", e.toString())
            }
    }

//    fun pullHot() = launch {
//        val hotProjects = api.project.hot().await().data
//        Log.i("projects", hotProjects.toString())
//        hot_grid_view.adapter = HotProjectAdapter(context!!, hotProjects!!)
//    }

    fun pullRecommend() = launch {
        val projects = api.project.recommend().await().data
        Log.i("recommend projects", projects.toString())
        recommend_project_grid.adapter = RecommendProjectAdapter(context!!, projects!!)
    }



}


class RecommendProjectAdapter(
        private val context: Context,
        private val projects: List<ProjectBasic>
) : BaseAdapter() {

    class ViewHoler(
            val projectImg: ImageView,
            val projectState: TextView,
            val projectRaisedMoney: TextView,
            val projectRaisedCount: TextView,
            val projectName: TextView,
            val userIcon: ImageView,
            val userNickname: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = View.inflate(context, R.layout.project_basic_grid_item, null)
            view.apply {
                tag = ViewHoler(
                        projectImg = findViewById(R.id.project_img),
                        projectState = findViewById(R.id.project_state),
                        projectRaisedMoney = findViewById(R.id.project_raised_money),
                        projectRaisedCount = findViewById(R.id.project_raised_count),
                        projectName = findViewById(R.id.project_name),
                        userIcon = findViewById(R.id.user_icon),
                        userNickname = findViewById(R.id.user_nickname)
                )
            }
        } else {
            view = convertView
        }
        val holder = view.tag as ViewHoler
        val project = projects[position]
        Picasso.with(context)
                .load(project.img)
                .into(holder.projectImg)
        Picasso.with(context)
                .load(project.author.avatar)
                .into(holder.userIcon)
        holder.apply {
            projectState.text = "募捐中"
            projectRaisedMoney.text = String.format("%d", project.raisedMoney.longValueExact())
            projectRaisedCount.text = "${project.donorCount}"
            projectName.text = project.name
            userNickname.text = project.author.nickName
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return projects[position]
    }

    override fun getItemId(position: Int): Long {
        return projects[position].id
    }

    override fun getCount(): Int {
        return projects.size
    }

}

class HotProjectAdapter(
        private val context: Context,
        private val projects: List<ProjectBasic>): BaseAdapter() {

    class ViewHolder(val img: ImageView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = View.inflate(context, R.layout.hot_project_grid_item, null)
            view.tag = ViewHolder(view.findViewById(R.id.project_img))
        } else {
            view = convertView
        }
        val holder = view.tag as ViewHolder
        Picasso.with(context)
                .load(projects[position].img)
                .into(holder.img)

        return view
    }

    override fun getItem(position: Int): Any {
        return projects[position]
    }

    override fun getItemId(position: Int): Long {
        return projects[position].id
    }

    override fun getCount(): Int {
        return projects.size
    }

}

class BannerViewHolder : MZViewHolder<Banner> {
    private var mImageView: ImageView? = null;

    override fun createView(context:Context):View {
        // 返回页面布局
        val view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }


    override fun onBind(context: Context, position: Int, data: Banner) {
        // 数据绑定
        Picasso.with(context)
                .load(data.img)
                .into(mImageView)
    }
}
