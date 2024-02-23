package com.okjunkstore.beta.Helperclass.HomeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.okjunkstore.beta.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private Context context;
    private List<NoticeData> list;

    public TeacherAdapter(List<NoticeData> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {

        NoticeData item = list.get(position);
        holder.name.setText(item.getDate());
        holder.email.setText(item.getOwner());
        holder.post.setText(item.getTime());
        try {
            if(item.getImage() != null)
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Update Teacher", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

         TextView name,email,post;
//         Button update;
         ImageView imageView;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherNumber);
            post = itemView.findViewById(R.id.teacherPost);
//            update = itemView.findViewById(R.id.teacherUpdate);
            imageView = itemView.findViewById(R.id.teacherImage);
        }
    }

}
