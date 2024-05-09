package com.example.projeto_avaliativo_fase2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.CharArrayWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //---------------------componentes de interface -----------//
    EditText nome, cpf, telefone, inteligencia, ra;
    Button inserir, consultar;
    ListView listViewParticipante;
    private Spinner camiseta;



    //--------------------obejeto de manipulação - BD ---------//
    gerenciadorBD db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //-----------associar componentes com seus elementos java----//
        nome = findViewById(R.id.nome);
        cpf = findViewById(R.id.cpf);
        telefone = findViewById(R.id.telefone);
        listViewParticipante = findViewById(R.id.listViewUsers);
        inserir = findViewById(R.id.btnInserir);
        consultar = findViewById(R.id.btnConsultar);

        //-----------instanciar o objeto - banco de dados ---------//
        db = new gerenciadorBD(this);

        //-----------evento do botão inserir-----------//
        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeLocal = nome.getText().toString();
                String cpfLOCAL = cpf.getText().toString();
                String telefoneLocal = telefone.getText().toString();
                String inteligenciaLocal = inteligencia.getText().toString();
                String raLocal = ra.getText().toString();

                boolean checkInsert = db.insertdate(nomeLocal, cpfLOCAL, telefoneLocal, inteligenciaLocal, raLocal);
                if (checkInsert) {
                    Toast.makeText(MainActivity.this, "dados inseridos com sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "falha ao inserir!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //-----------evento do botão consultar--------//
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listaParticipantes = new ArrayList<>();
                Cursor cursor = db.getData();

                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "TABELA VAZIA", Toast.LENGTH_SHORT).show();

                } else {
                    while (cursor.moveToNext()) {
                        listaParticipantes.add("nome: " + cursor.getString(0));
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaParticipantes);
                listViewParticipante.setAdapter(adapter);
                cursor.close();
                //obs: professor nao sei como resolver esse proplema na função do cursor.close
            }

        });
        camiseta =findViewById(R.id.camiseta);
    }
}