package kg.geektech.anroid3.lesson_3.ui.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.anroid3.lesson_3.R;
import kg.geektech.anroid3.lesson_3.data.interfaces.OnClickListener;
import kg.geektech.anroid3.lesson_3.data.interfaces.OnLongClickListener;
import kg.geektech.anroid3.lesson_3.data.model.Post;
import kg.geektech.anroid3.lesson_3.data.model.User;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UserVH> {

    private List<Post> posts = new ArrayList<>();
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClick;




    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public void setOnLongClick(OnLongClickListener onLongClick){
        this.onLongClick = onLongClick;
    }

    public void setList(List<Post> post) {
        posts.addAll(post);
        notifyDataSetChanged();
    }

    public void deletePost(Post post) {
        notifyItemRemoved((posts.indexOf(post)));
        posts.remove(post);
    }

    class UserVH extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvName;
        private TextView tvGroup;

        public UserVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvGroup = itemView.findViewById(R.id.tv_group);

        }

        public void onBind(Post post) {
            tvTitle.setText(post.getTitle());
            tvContent.setText(post.getContent());
            tvName.setText(post.getUser().toString());
            tvGroup.setText(post.getGroup().toString());
            listener();
        }

        private void listener() {
            itemView.setOnLongClickListener(v -> {
                onLongClick.onLongClick(posts.get(getAdapterPosition()));
                return true;
            });
            itemView.setOnClickListener(v -> {
                onClickListener.onClickListener(posts.get(getAdapterPosition()));
            });
        }

    }
}
