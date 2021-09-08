package com.androidrion.movierealm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    private List<MovieModel> dataList;
    private List<MovieModel> dataListFilter;
    private OnItemClickListener mListener;
    Context CT;

    public MovieAdapter(List<MovieModel> dataList, Context CT) {
        this.dataList = dataList;
        this.CT = CT;
        this.dataListFilter = dataList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(CT);
        View view = layoutInflater.inflate(R.layout.itemview, parent, false);
        return new MovieViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getOriginal_title());
        holder.reldate.setText(dataList.get(position).getRelease_date());
        holder.overview.setText(dataList.get(position).getOverview());
        Picasso.get()
                .load(dataList.get(position).getPoster_path())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_list);
    }

    @Override
    public int getItemCount() {
        return (dataListFilter != null) ? dataListFilter.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()){
                    dataListFilter = dataList;
                } else {
                    ArrayList<MovieModel> isFiltered = new ArrayList<>();
                    for (MovieModel row : dataList) {
                        if (row.getOriginal_title().toLowerCase().contains(Key)) {
                            isFiltered.add(row);
                        }
                    }
                    dataListFilter = isFiltered;
                }
                FilterResults results = new FilterResults();
                results.values = dataListFilter;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataListFilter = (ArrayList<MovieModel>) results.values;
            }
        };
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView title, reldate, overview;
        private ImageView img_list;
        CardView MainLayout;

        public MovieViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            reldate = itemView.findViewById(R.id.reldate);
            overview = itemView.findViewById(R.id.overview);
            img_list = itemView.findViewById(R.id.img_list);
            MainLayout = (CardView) itemView.findViewById(R.id.mainlayout);

            MainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

}