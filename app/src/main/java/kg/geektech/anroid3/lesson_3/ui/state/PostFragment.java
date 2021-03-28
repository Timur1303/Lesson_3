package kg.geektech.anroid3.lesson_3.ui.state;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.anroid3.lesson_3.R;
import kg.geektech.anroid3.lesson_3.data.interfaces.OnClickListener;
import kg.geektech.anroid3.lesson_3.data.interfaces.OnLongClickListener;
import kg.geektech.anroid3.lesson_3.data.model.Post;
import kg.geektech.anroid3.lesson_3.data.remote.RetrofitBuilder;
import kg.geektech.anroid3.lesson_3.ui.recycler.UserRVAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment implements OnClickListener, OnLongClickListener {

    private RecyclerView recyclerView;
    private UserRVAdapter postsAdapter;
    private List<Post> list;
    private int position;
    private Button btnAdd;

    public PostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadData();
    }

    private void loadData() {
        RetrofitBuilder
                .getInstance()
                .getPosts()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            list = response.body();
                            postsAdapter.setList(list);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                    }
                });
    }


    private void initView() {
        list = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recyclerView);
        postsAdapter = new UserRVAdapter();
        recyclerView.setAdapter(postsAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext()
                        , DividerItemDecoration.VERTICAL));
        postsAdapter.setOnClickListener(this);
        postsAdapter.setOnLongClick(this);
        btnAdd = getView().findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v ->
                fabClick()
        );
    }


    @Override
    public void onClickListener(Post post) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,PostChangeFragment.newInstance(post))
                .addToBackStack(null)
                .commit();

    }
    private void fabClick() {
        Navigation.findNavController(getView()).navigate(R.id.userDetailFragment);
    }

    @Override
    public void onLongClick(Post post) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setMessage("Хотите удалить?")
                .setTitle("Удалить")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteById(post);
                    }
                });
        builder.create().show();

    }

    private void deleteById(Post post) {
        RetrofitBuilder
                .getInstance()
                .deletePost(post.getId())
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.isSuccessful()) {
                            postsAdapter.deletePost(post);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getContext(), "Not deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
