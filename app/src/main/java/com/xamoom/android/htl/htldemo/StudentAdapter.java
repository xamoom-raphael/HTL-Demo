package com.xamoom.android.htl.htldemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xamoom.android.xamoomsdk.Resource.Content;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context mContext;
    private StudentListInteractionListener mListener;
    private List<Content> mStudentList;

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView mStudentNameTextView;
        public ImageView mStudentImageView;
        public View mRootView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            mStudentNameTextView = (TextView) itemView.findViewById(R.id.student_name_text_view);
            mStudentImageView = (ImageView) itemView.findViewById(R.id.student_image_view);
            mRootView = itemView;
        }

    }

    public StudentAdapter(List<Content> studentList, StudentListInteractionListener listener,
                          Context context) {
        mStudentList = studentList;
        mContext = context;
        mListener = listener;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_view_holder, parent, false);

        StudentViewHolder vh = new StudentViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        final Content student = mStudentList.get(position);

        holder.mStudentNameTextView.setText(student.getTitle());
        Glide.with(holder.mStudentImageView.getContext())
                .load(student.getPublicImageUrl())
                .placeholder(R.drawable.loading)
                .centerCrop()
                .crossFade()
                .into(holder.mStudentImageView);

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onStudentClicked(student.getId());
            }
        });

    }

    interface StudentListInteractionListener {
        void onStudentClicked(String id);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mStudentList.size();
    }
}
