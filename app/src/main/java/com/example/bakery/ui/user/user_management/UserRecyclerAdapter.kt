package com.example.bakery.ui.user.user_management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bakery.data.model.User
import com.example.bakery.databinding.ItemUserBinding
import com.google.gson.Gson
import java.util.concurrent.Executors

class UserRecyclerAdapter<T : Any?>(
    private val userList: List<User?>,
    private val listener: OnClickListener
) :
    ListAdapter<User, UserRecyclerAdapter.MyViewHolder>(
        UserDiffCallBack()
    ) {

    class MyViewHolder(val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: User?) {
            itemBinding.tvName.text = user?.name
            itemBinding.tvRole.text = if (user?.admin == "1") "Quản trị viên" else "Nhân viên"
            itemBinding.tvEmail.text = user?.email
            itemBinding.tvCode.text = user?.code
            itemBinding.tvGender.text = user?.gender
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user: User? = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            listener.onClick(user)
        }

    }

    class OnClickListener(val clickListener: (user: User?) -> Unit) {
        fun onClick(user: User?) = clickListener(user)
    }
}

class UserDiffCallBack : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem?.code == newItem?.code
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}