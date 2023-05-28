package com.example.listview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listview.realmdb.ListViewDb;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> itens = new ArrayList<>();

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);

        realm = Realm.getDefaultInstance();

        RealmResults<ListViewDb> objects = realm.where(ListViewDb.class).findAll();

        System.out.println("Aqui comeca o realm: ");
        System.out.println(objects);

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);


        // Iniciando a lista:
        updateListView();
        updateListView2();

        // Verificando o item que foi clicado:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String aux = itens.get(i);
                aux = aux + " clicked";
                itens.set(i, aux);
                updateListView();
                listView.requestFocusFromTouch();
                listView.setSelection(i);
            }
        });

    }


    // Inserir dados Realm
    public void insertNewItem(String item, int id){
        realm.beginTransaction();
        ListViewDb object = realm.createObject(ListViewDb.class);
        object.setItem(item);
        object.setId(id);
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    int counter =0;

    // Adiciona apenas a linha:
    public void addButtonItems1(View v){
        counter++;
        itens.add("Item "+counter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            MainActivity.this,
            android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );
        listView.setAdapter(adapter);
        Toast.makeText(
                MainActivity.this,
                "Adicionado",
                Toast.LENGTH_SHORT
        ).show();
    }

    // Adiciona o item com um botão editar e gera um modal:
    public void addButtonItems(View v) {
        counter++;
        itens.add("Item " + counter);

        String item = ("Item " + counter).toString();
        int id = counter;
        insertNewItem(item, id);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.list_item_layout,
                R.id.text_view_item,
                itens
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Button button = view.findViewById(R.id.button_item);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditDialog(position);
                    }
                });

                Button button_remove = view.findViewById(R.id.button_item_remove);
                button_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRemoveDialog(position);
                    }
                });

                return view;
            }
        };

        listView.setAdapter(adapter);
        Toast.makeText(
                MainActivity.this,
                "Adicionado",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void showEditDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.edit_dialog_layout, null);
        builder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.edit_text_item);
        editText.setText(itens.get(position));

        final AlertDialog dialog = builder.create();

        Button buttonSave = dialogView.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Salvar alterações
                itens.set(position, editText.getText().toString());
                ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Alterações salvas", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelar edição
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void showRemoveDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.edit_dialog_layout, null);
        builder.setView(dialogView);

        final EditText editText = dialogView.findViewById(R.id.edit_text_item);
        editText.setText(itens.get(position));
        editText.setText("Tem certeza que deseja remover?");
        editText.setEnabled(false);

        final AlertDialog dialog = builder.create();

        Button buttonSave = dialogView.findViewById(R.id.button_save);
        buttonSave.setText("Remover");
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Salvar alterações
                itens.remove(position);
//                itens.set(position, editText.getText().toString());
                ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Item excluído", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelar edição
                dialog.dismiss();
            }
        });

        dialog.show();
    }








    // Faz o update da listView2
    void updateListView2(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.list_item_layout,
                R.id.text_view_item,
                itens
        );
        listView.setAdapter(adapter);
    }

    // Faz o update da listView
    void updateListView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            MainActivity.this,
            android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );
        listView.setAdapter(adapter);
    }

}