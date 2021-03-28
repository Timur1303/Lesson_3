package kg.geektech.anroid3.lesson_3.ui.state;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kg.geektech.anroid3.lesson_3.R;
import kg.geektech.anroid3.lesson_3.data.model.Post;
import kg.geektech.anroid3.lesson_3.data.remote.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostChangeFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";
    private static final String ARG_USER = "user";
    private static final String ARG_GROUP = "group";
    private static final String ARG_ID = "id";

    private String title;
    private String content;
    private Integer user;
    private Integer group;
    private Integer id;
    private boolean isUpdating;
    private EditText editTitleChange, editContentChange, editUserChange, editGroupChange;
    private Button btnChange;



    public PostChangeFragment() {

    }

    public static PostChangeFragment newInstance(Post post) {
        PostChangeFragment fragment = new PostChangeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, post.getTitle());
        args.putString(ARG_CONTENT, post.getContent());
        args.putInt(ARG_USER, post.getUser());
        args.putInt(ARG_GROUP, post.getGroup());
        args.putInt(ARG_ID, post.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
            user = getArguments().getInt(ARG_USER);
            group = getArguments().getInt(ARG_GROUP);
            id = getArguments().getInt(ARG_ID);
        }
    }

    private void setEditFields() {
        editTitleChange.setText(title);
        editContentChange.setText(content);
        editUserChange.setText(user.toString());
        editGroupChange.setText(group.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_change, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        if (isUpdating) setEditFields();
        btnChange.setOnClickListener(v -> updateClick());
    }

    private void init() {
        editTitleChange = getView().findViewById(R.id.et_title_change);
        editContentChange = getView().findViewById(R.id.et_content_change);
        editUserChange = getView().findViewById(R.id.et_user_change);
        editGroupChange = getView().findViewById(R.id.et_group_change);
        btnChange = getView().findViewById(R.id.btnChange);
    }

    private void updateClick() {
        String title = editTitleChange.getText().toString();
        String desc = editContentChange.getText().toString();
        String user = editUserChange.getText().toString();
        String group = editGroupChange.getText().toString();

        Post post = new Post( title, desc, Integer.parseInt(user), Integer.parseInt(group));
        if (isUpdating) upDatePost(post);
        Navigation.findNavController(getView()).navigateUp();

    }
    private void upDatePost(Post post) {
        RetrofitBuilder
                .getInstance()
                .updatePost(post)
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            getParentFragmentManager().popBackStackImmediate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                    }
                });

    }
}