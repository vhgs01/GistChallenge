package br.com.hugo.victor.gistchallenge.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.hugo.victor.gistchallenge.R;
import br.com.hugo.victor.gistchallenge.activity.data.models.Gist;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class GistAdapter extends RecyclerView.Adapter {

    // DECLARAÇÃO DE VARIÁVEIS
    private Context mContext;
    private List<Gist> mGists;
    private OnGistClickListener mListener;

    public GistAdapter(Context mContext, List<Gist> mGists, OnGistClickListener mListener) {
        this.mContext = mContext;
        this.mGists = mGists;
        this.mListener = mListener;
    }

    static class GistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // DECLARAÇÃO DE VARIÁVEIS
        private View mView;
        private OnGistClickListener mListener;

        // BIND DOS ELEMENTOS
        @BindView(R.id.civAvatar)
        CircleImageView civAvatar;
        @BindView(R.id.tvOwner)
        TextView tvOwner;
        @BindView(R.id.tvLanguage)
        TextView tvLanguage;
        @BindView(R.id.tvFilename)
        TextView tvFilename;

        GistHolder(View itemView, OnGistClickListener listener) {
            super(itemView);

            // INICIALIZAÇÃO DO BUTTER KNIFE
            ButterKnife.bind(this, itemView);

            mView = itemView;
            mListener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onClicked(getAdapterPosition());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout;
        GistHolder holder = null;

        // INFLA A VIEW QUE IRÁ APARECER NA LISTA
        try {
            layout = LayoutInflater.from(mContext).inflate(R.layout.main_line_view, parent, false);

            // CRIA O VIEW HOLDER
            holder = new GistHolder(layout, mListener);
        } catch (Exception error) {
            Log.e("Error", "Error at onCreateViewHolder in " + getClass().getName() + ". " + error.getMessage());
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            // OBTEM O HOLDER
            GistHolder holderG = (GistHolder) holder;
            Gist item = mGists.get(position);

            // DEFINE UMA STRING PARA O OWNER E PARA LANGUAGE
            String gistOwnerName = mContext.getString(R.string.privateName);
            String gistLanguage = mContext.getString(R.string.unknownLanguage);

            // PREENCHENDO OS DADOS NA VIEW
            if (item.owner != null) {
                gistOwnerName = item.owner.login;
                if (!TextUtils.isEmpty(item.owner.avatar)) {
                    Picasso.with(mContext).load(Uri.parse(item.owner.avatar)).fit().into(holderG.civAvatar);
                    holderG.civAvatar.setContentDescription(item.owner.avatar);
                }
            }

            if (!item.files.gists.get(0).language.isEmpty()) {
                gistLanguage = item.files.gists.get(0).language;
            }

            holderG.tvOwner.setText(gistOwnerName);
            holderG.tvFilename.setText(item.files.gists.get(0).filename);
            holderG.tvLanguage.setText(gistLanguage);

        } catch (Exception error) {
            Log.e("Error", "Error at onBindViewHolder in " + getClass().getName() + ". " + error.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mGists.size();
    }

    public interface OnGistClickListener {
        void onClicked(int position);
    }

}
