package br.com.hugo.victor.gistchallenge.activity.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.hugo.victor.gistchallenge.R;
import br.com.hugo.victor.gistchallenge.activity.data.database.FavoriteDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter {

    // DECLARAÇÃO DE VARIÁVEIS
    private Context mContext;
    private FavoriteDB[] mFavorites;

    public FavoriteAdapter(Context mContext, FavoriteDB[] mFavorites) {
        this.mContext = mContext;
        this.mFavorites = mFavorites;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        FavoriteHolder holder;

        // INFLA A VIEW QUE IRÁ APARECER NA LISTA
        view = LayoutInflater.from(mContext).inflate(R.layout.main_line_view, parent, false);

        // CRIA O VIEW HOLDER
        holder = new FavoriteHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // OBTEM O HOLDER
        FavoriteHolder holderF = (FavoriteHolder) holder;
        FavoriteDB favorite = mFavorites[position];

        // PREENCHENDO OS DADOS NA VIEW
        holderF.tvOwner.setText(favorite.getOwner());
        holderF.tvFilename.setText(favorite.getFilename());
        holderF.tvLanguage.setText(favorite.getLanguage());
        Picasso.with(mContext).load(Uri.parse(favorite.getAvatar())).fit().into(holderF.civAvatar);

    }

    @Override
    public int getItemCount() {
        return mFavorites.length;
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {
        // BIND DOS ELEMENTOS
        @BindView(R.id.civAvatar)
        CircleImageView civAvatar;
        @BindView(R.id.tvOwner)
        TextView tvOwner;
        @BindView(R.id.tvLanguage)
        TextView tvLanguage;
        @BindView(R.id.tvFilename)
        TextView tvFilename;

        FavoriteHolder(View itemView) {
            super(itemView);

            // INICIALIZAÇÃO DO BUTTER KNIFE
            ButterKnife.bind(this, itemView);

        }
    }
}
