package com.job.jounalapp.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.job.jounalapp.R;
import com.job.jounalapp.datasource.Dairy;
import com.job.jounalapp.util.DairyViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.job.jounalapp.util.Constants.DAIRYCOL;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFrag";
    @BindView(R.id.home_list)
    RecyclerView homeList;
    @BindView(R.id.home_fab)
    FloatingActionButton homeFab;
    Unbinder unbinder;

    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore mFirestore;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //firebase
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "onActivityCreated: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

        setUpDairyList();

    }

    @OnClick(R.id.home_fab)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), AddItemActivity.class);
        startActivity(intent);
    }

    /*
     * This sets up a firestore recycler view that manages the list data as added int the database
     * changes are real time and responsive
     * */

    private void setUpDairyList() {

        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(getContext().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        homeList.setLayoutManager(linearLayoutManager);

        // Create a reference to the members collection
        final CollectionReference dairyRef = mFirestore.collection(DAIRYCOL);
        final Query query = dairyRef
                .whereEqualTo("userid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("timestamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Dairy> options = new FirestoreRecyclerOptions.Builder<Dairy>()
                .setQuery(query, Dairy.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Dairy, DairyViewHolder>(options) {
            @NonNull
            @Override
            public DairyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_dairy_item, parent, false);

                return new DairyViewHolder(view,getContext());
            }

            @Override
            protected void onBindViewHolder(@NonNull DairyViewHolder holder, int position, @NonNull Dairy model) {

                holder.init(model,mFirestore);
                holder.setUpListItem(model);

            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Log.e(TAG, "onError: ", e);
            }
        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        homeList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            sendToLogin();
        }

        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    private void sendToLogin() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }
}
