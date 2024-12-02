package br.edu.fateczl.atividade14_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.fateczl.atividade14_01.controller.LivroController;
import br.edu.fateczl.atividade14_01.controller.RevistaController;
import br.edu.fateczl.atividade14_01.model.Aluno;
import br.edu.fateczl.atividade14_01.model.Livro;
import br.edu.fateczl.atividade14_01.model.Revista;
import br.edu.fateczl.atividade14_01.persistence.LivroDao;
import br.edu.fateczl.atividade14_01.persistence.RevistaDao;

public class ExemplaresFragment extends Fragment {

    private View view;
    private LivroController livroCtrl;
    private RevistaController revistaCtrl;
    private TextView tvListarExemplar;
    private EditText etNomeExemplar;
    private EditText etCodigoExemplar;
    private EditText etQtdPaginas;
    private RadioButton rb_livro;
    private RadioButton rb_revista;
    private EditText etEdicao;
    private EditText etIsbnIssn;
    private Button btnBuscarExemplar, btnInserirExemplar, btnModificarExemplar, btnExcluirExemplar, btnListarExemplar;

    public ExemplaresFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exemplares, container, false);
        livroCtrl = new LivroController(new LivroDao(view.getContext()));
        revistaCtrl = new RevistaController(new RevistaDao(view.getContext()));

        tvListarExemplar = view.findViewById(R.id.tvListarExemplar);
        tvListarExemplar.setMovementMethod(new ScrollingMovementMethod());

        etCodigoExemplar = view.findViewById(R.id.etCodigoExemplar);
        etNomeExemplar = view.findViewById(R.id.etNomeExemplar);
        etQtdPaginas = view.findViewById(R.id.etQtdPaginas);
        rb_livro = view.findViewById(R.id.rb_livro);
        rb_livro.setChecked(true);
        rb_revista = view.findViewById(R.id.rb_revista);
        etEdicao = view.findViewById(R.id.etEdicao);
        etIsbnIssn = view.findViewById(R.id.etIsbnIssn);


        btnBuscarExemplar = view.findViewById(R.id.btnBuscarExemplar);
        btnInserirExemplar = view.findViewById(R.id.btnInserirExemplar);
        btnModificarExemplar = view.findViewById(R.id.btnModificarExemplar);
        btnExcluirExemplar = view.findViewById(R.id.btnExcluirExemplar);
        btnListarExemplar = view.findViewById(R.id.btnListarExemplar);

        btnInserirExemplar.setOnClickListener(op -> inserir());
        btnModificarExemplar.setOnClickListener(op -> modificar());
        btnExcluirExemplar.setOnClickListener(op -> excluir());
        btnListarExemplar.setOnClickListener(op -> listar());
        btnBuscarExemplar.setOnClickListener(op -> buscar());

        return view;
    }

    private void inserir() {
        if (rb_livro.isChecked()) {
            Livro livro = montaLivro();
            System.out.println(livro.toString());
            try {
                livroCtrl.inserir(livro);
                Toast.makeText(view.getContext(), "Exemplar INSERIDO com sucesso", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Revista revista = montaRevista();
            System.out.println(revista.toString());
            try {
                revistaCtrl.inserir(revista);
                Toast.makeText(view.getContext(), "Exemplar INSERIDO com sucesso", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void modificar() {
        if (rb_livro.isChecked()) {
            Livro livro = montaLivro();
            try {
                livroCtrl.modificar(livro);
                Toast.makeText(view.getContext(), "Exemplar MODIFICADO com sucesso", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Revista revista = montaRevista();
            try {
                revistaCtrl.modificar(revista);
                Toast.makeText(view.getContext(), "Exemplar MODIFICADO com sucesso", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void excluir() {
        if (rb_livro.isChecked()) {
            Livro livro = montaLivro();
            try {
                livroCtrl.deletar(livro);
                Toast.makeText(view.getContext(), "Exemplar DELETAR com sucesso", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Revista revista = montaRevista();
            try {
                revistaCtrl.deletar(revista);
                Toast.makeText(view.getContext(), "Exemplar DELETAR com sucesso", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void listar() {
        try {
            List<Livro> livros = livroCtrl.listar();
            List<Revista> revistas = revistaCtrl.listar();
            StringBuffer buffer = new StringBuffer();
            for (Livro a : livros) {
                buffer.append(a.toString() + "\n");
            }
            for (Revista b: revistas) {
                buffer.append(b.toString() + "\n");
            }
            tvListarExemplar.setText(buffer.toString());
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void buscar() {
        try {
            Livro livro = new Livro();
            String codigoExemplarStr = etCodigoExemplar.getText().toString();
            int codigo = Integer.parseInt(codigoExemplarStr.isEmpty() ? "0" : codigoExemplarStr);
            livro.setCodigo(codigo);
            livro = livroCtrl.buscar(livro);
            if (livro.getNome() != null) {
                preencheCampos(livro);
            } else{
                Revista revista = new Revista();
                revista.setCodigo(codigo);
                limpaCampos();
                revista = revistaCtrl.buscar(revista);
                if (revista.getNome() != null) {
                    preencheCampos(revista);
                } else {
                    Toast.makeText(view.getContext(), "Exemplar não foi encontrado", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            Toast.makeText(view.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencheCampos(Livro livro) {
        etCodigoExemplar.setText(String.valueOf(livro.getCodigo()));
        etNomeExemplar.setText(String.valueOf(livro.getNome()));
        etQtdPaginas.setText(String.valueOf(livro.getQtdPaginas()));
        rb_revista.setChecked(false);
        rb_livro.setChecked(true);
        etIsbnIssn.setText(livro.getIsbn());
        etEdicao.setText(String.valueOf(livro.getEdicao()));
    }

    private void preencheCampos(Revista revista) {
        etCodigoExemplar.setText(String.valueOf(revista.getCodigo()));
        etNomeExemplar.setText(String.valueOf(revista.getNome()));
        etQtdPaginas.setText(String.valueOf(revista.getQtdPaginas()));
        rb_revista.setChecked(true);
        rb_livro.setChecked(false);
        etIsbnIssn.setText(revista.getIssn());
    }

    private Livro montaLivro() {
        Livro livro = new Livro();
        String codigoExemplarStr = etCodigoExemplar.getText().toString();
        int codigo = Integer.parseInt(codigoExemplarStr.isEmpty() ? "0" : codigoExemplarStr);
        String nomeStr = etNomeExemplar.getText().toString();
        String qtdPaginasStr = etQtdPaginas.getText().toString();
        int qtdPaginas = Integer.parseInt(qtdPaginasStr.isEmpty() ? "0" : qtdPaginasStr);
        String isbnStr = etIsbnIssn.getText().toString();
        String edicaoStr = etEdicao.getText().toString();
        int edicao = Integer.parseInt(edicaoStr.isEmpty() ? "0" : edicaoStr);

        livro.setCodigo(codigo);
        livro.setNome(nomeStr);
        livro.setQtdPaginas(qtdPaginas);
        livro.setIsbn(isbnStr);
        livro.setEdicao(edicao);
        return livro;
    }

    private Revista montaRevista() {
        Revista revista = new Revista();
        String codigoExemplarStr = etCodigoExemplar.getText().toString();
        int codigo = Integer.parseInt(codigoExemplarStr.isEmpty() ? "0" : codigoExemplarStr);
        String nomeStr = etNomeExemplar.getText().toString();
        String qtdPaginasStr = etQtdPaginas.getText().toString();
        int qtdPaginas = Integer.parseInt(qtdPaginasStr.isEmpty() ? "0" : qtdPaginasStr);
        String issnStr = etIsbnIssn.getText().toString();

        revista.setCodigo(codigo);
        revista.setNome(nomeStr);
        revista.setQtdPaginas(qtdPaginas);
        revista.setIssn(issnStr);
        return revista;
    }

    private void limpaCampos() {
        etCodigoExemplar.setText("");
        etNomeExemplar.setText("");
        etQtdPaginas.setText("");
        rb_revista.setChecked(false);
        rb_livro.setChecked(true);
        etIsbnIssn.setText("");
        etEdicao.setText("");
    }
}