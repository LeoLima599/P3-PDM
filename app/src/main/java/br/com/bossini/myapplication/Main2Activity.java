package br.com.bossini.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.Toast.makeText;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView mensagensRecyclerView;
    private ChatAdapter adapter;
    private List<Mensagem> mensagens;
    private CollectionReference collMensagensReference;

    private EditText mensagemEditText;

    private void setupFirebase (){
        collMensagensReference =
                FirebaseFirestore.
                        getInstance().
                        collection("mensagens");

        collMensagensReference.addSnapshotListener((result, e) -> {
            mensagens.clear();
            for (DocumentSnapshot doc : result.getDocuments()){
                Mensagem m = doc.toObject(Mensagem.class);
                mensagens.add(m);
            }
            Collections.sort(mensagens);
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setupFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mensagemEditText =
                findViewById(R.id.mensagemEditText);
        mensagensRecyclerView =
                findViewById(R.id.mensagensRecyclerView);
        mensagens = new ArrayList<>();
        adapter = new ChatAdapter(this, mensagens);
        LinearLayoutManager llm =
                new LinearLayoutManager(this);
        mensagensRecyclerView.setAdapter(adapter);
        mensagensRecyclerView.setLayoutManager(llm);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void enviarMensagem(View view) {
        String texto =
                mensagemEditText.getText().toString();
        Mensagem m =
                new Mensagem (texto,
                        new java.util.Date(), "nickname");
        collMensagensReference.add(m);
        mensagemEditText.setText("");
    }
}

class ChatViewHolder extends RecyclerView.ViewHolder{
    public TextView dataNomeTextView;
    public TextView mensagemTextView;
    public ChatViewHolder (View raiz){
        super (raiz);
        dataNomeTextView =
                raiz.findViewById(R.id.dataNomeTextView);
        mensagemTextView =
                raiz.findViewById(R.id.mensagemTextView);
    }
}



class ChatAdapter extends RecyclerView.Adapter <ChatViewHolder>{

    private Context context;
    private List <Mensagem> mensagens;

    public ChatAdapter(Context context, List<Mensagem> mensagens){
        this.context = context;
        this.mensagens = mensagens;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(
                R.layout.item_msg,
                parent,
                false
        );
        return new ChatViewHolder(raiz);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Mensagem m = mensagens.get(position);
            holder.dataNomeTextView.setText(
                    context.getString(
                            R.string.data_nome,
                            m.getData(),
                            m.getNickname()
                    )
            );
            holder.mensagemTextView.setText(m.getTexto());

    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}
