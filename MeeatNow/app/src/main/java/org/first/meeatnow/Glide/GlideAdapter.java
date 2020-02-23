package org.first.meeatnow.Glide;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.first.meeatnow.R;

import java.util.ArrayList;

public class GlideAdapter extends RecyclerView.Adapter<GlideAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<GlideDTO> mGlidesData;

    public GlideAdapter(Context context, ArrayList<GlideDTO> glidesData) {
        this.mContext = context;
        this.mGlidesData = glidesData;
    }

    @NonNull
    @Override
    public GlideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GlideAdapter.ViewHolder holder, int position) {
        GlideDTO currentGlideDTO = mGlidesData.get(position);
        holder.bindTo(currentGlideDTO);
    }

    @Override
    public int getItemCount() {
        return mGlidesData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView glideImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            glideImage = itemView.findViewById(R.id.listImage);

        }

        // Glide실행하는 부분
        void bindTo(GlideDTO currentGlideDTO) {
            Glide.with(mContext).load(currentGlideDTO.getimageURL())
                    .fitCenter()
                    .error(R.drawable.ic_launcher_background)
                    .into(glideImage);
        }


    }
}
