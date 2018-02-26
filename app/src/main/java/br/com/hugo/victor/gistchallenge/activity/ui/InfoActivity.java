package br.com.hugo.victor.gistchallenge.activity.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.hugo.victor.gistchallenge.R;
import br.com.hugo.victor.gistchallenge.activity.adapter.PersonListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {

    // BIND DOS ELEMENTOS
    @BindView(R.id.rvDevelopedBy)
    RecyclerView rvDevelopedBy;

    // DECLARAÇÃO DE VARIÁVEIS
    private PersonListAdapter mDeveloperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

        // INFLA O RECYCLERVIEW COM OS DADOS DO ARRAY DE DESENVOLVEDORES
        mDeveloperAdapter = new PersonListAdapter(this, R.array.developer_name, R.array.developer_desc, R.array.developer_linkedin);
        rvDevelopedBy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvDevelopedBy.setAdapter(mDeveloperAdapter);
        mDeveloperAdapter.notifyDataSetChanged();


    }
}
