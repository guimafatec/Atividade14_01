package br.edu.fateczl.atividade14_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.atividade14_01.controller.AluguelController;
import br.edu.fateczl.atividade14_01.controller.AlunoController;
import br.edu.fateczl.atividade14_01.controller.LivroController;
import br.edu.fateczl.atividade14_01.controller.RevistaController;
import br.edu.fateczl.atividade14_01.model.Aluguel;
import br.edu.fateczl.atividade14_01.model.Aluno;
import br.edu.fateczl.atividade14_01.model.Exemplar;
import br.edu.fateczl.atividade14_01.model.Livro;
import br.edu.fateczl.atividade14_01.model.Revista;
import br.edu.fateczl.atividade14_01.persistence.AluguelDao;
import br.edu.fateczl.atividade14_01.persistence.AlunoDao;
import br.edu.fateczl.atividade14_01.persistence.LivroDao;
import br.edu.fateczl.atividade14_01.persistence.RevistaDao;

public class AlugueisFragment extends Fragment {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */

    private View view;
    private Button btnBuscarAluguel, btnInserirAluguel, btnModificarAluguel, btnExcluirAluguel, btnListarAluguel;
    private AluguelController aluguelCtrl;
    private LivroController livroCtrl;
    private RevistaController revistaCtrl;
    private AlunoController alunoCtrl;

    private EditText etDtRetirada;
    private EditText etDtDevolucao;
    private Spinner spAluno;
    private Spinner spExemplar;
    private TextView tvListarAlugueis;

    public AlugueisFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alugueis, container, false);
        aluguelCtrl = new AluguelController(new AluguelDao(view.getContext()));
        livroCtrl = new LivroController(new LivroDao(view.getContext()));
        revistaCtrl = new RevistaController(new RevistaDao(view.getContext()));
        alunoCtrl = new AlunoController(new AlunoDao(view.getContext()));

        etDtRetirada = view.findViewById(R.id.etDtRetirada);
//        etDtRetirada.setText("2024-10-20");
        etDtDevolucao = view.findViewById(R.id.etDtDevolucao);
//        etDtDevolucao.setText("2024-10-30");
        spAluno = view.findViewById(R.id.spAluno);
        spExemplar = view.findViewById(R.id.spExemplar);

        tvListarAlugueis = view.findViewById(R.id.tvListarAlugueis);

        btnBuscarAluguel = view.findViewById(R.id.btnBuscarAluguel);
        btnInserirAluguel = view.findViewById(R.id.btnInserirAluguel);
        btnModificarAluguel = view.findViewById(R.id.btnModificarAluguel);
        btnExcluirAluguel = view.findViewById(R.id.btnExcluirAluguel);
        btnListarAluguel = view.findViewById(R.id.btnListarAluguel);

        btnInserirAluguel.setOnClickListener(op -> inserir());
        btnModificarAluguel.setOnClickListener(op -> modificar());
        btnExcluirAluguel.setOnClickListener(op -> excluir());
        btnListarAluguel.setOnClickListener(op -> listar());
        btnBuscarAluguel.setOnClickListener(op -> buscar());

        spinnerAluno();
        spinnerExemplar();
//        spAluno.setSelection(1);
//        spExemplar.setSelection(1);

        return view;
    }

    private void inserir() {
        Aluguel aluguel = montaAluguel();
        try {
            aluguelCtrl.inserir(aluguel);
            Toast.makeText(view.getContext(), "Aluguel INSERIDO com sucesso", Toast.LENGTH_SHORT).show();
            limpaCampos();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void modificar() {
        Aluguel aluguel = montaAluguel();
        try {
            aluguelCtrl.modificar(aluguel);
            Toast.makeText(view.getContext(), "Aluguel MODIFICADO com sucesso", Toast.LENGTH_SHORT).show();
            limpaCampos();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        Aluguel aluguel = montaAluguel();
        try {
            aluguelCtrl.deletar(aluguel);
            Toast.makeText(view.getContext(), "Aluguel EXCLUÍDO com sucesso", Toast.LENGTH_SHORT).show();
            limpaCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void listar() {
        try {
            List<Aluguel> alugueis = aluguelCtrl.listar();
            StringBuffer buffer = new StringBuffer();
            for (Aluguel aluguel : alugueis) {
                buffer.append(aluguel.toString() + "\n");
            }
            tvListarAlugueis.setText(buffer.toString());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscar() {
        Aluguel aluguel = montaAluguel();
        try {
            aluguel = aluguelCtrl.buscar(aluguel);
            if (aluguel.getDt_retirada() != null) {
                preencheCampos(aluguel);
            } else {
                Toast.makeText(view.getContext(), "Aluguel não encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
//        limpaCampos();
    }

    private void preencheCampos(Aluguel aluguel) {
        etDtRetirada.setText(aluguel.getDt_retirada().toString());
        etDtDevolucao.setText(aluguel.getDt_devolucao().toString());
//        spAluno.setSelection();
//        spExemplar.setSelection(0);
    }

    private Aluguel montaAluguel() {
        String livroSelecionado = spExemplar.getSelectedItem().toString();
        String atributosExemplar[] = livroSelecionado.split("\\|");
        String alunoSelecionado = spAluno.getSelectedItem().toString();
        String atributosaluno[] = alunoSelecionado.split("\\|");

        Aluno aluno = montaAluno(atributosaluno);
        Aluguel aluguel = new Aluguel();
        aluguel.setAluno(aluno);
        System.out.println("CONTAGEM: " + String.valueOf(atributosExemplar.length));
        if (atributosExemplar.length == 5) {
            Livro livro = montaLivro(atributosExemplar);
            aluguel.setExemplar(livro);
        } else {
            Revista revista = montaRevista(atributosExemplar);
            aluguel.setExemplar(revista);
        }
        String dtRetirada = etDtRetirada.getText().toString();
        dtRetirada = dtRetirada.isEmpty() ? LocalDate.now().toString() : dtRetirada;
        aluguel.setDt_retirada(LocalDate.parse(dtRetirada));

        String dtDevolucao = etDtDevolucao.getText().toString();
        dtDevolucao = dtDevolucao.isEmpty() ? LocalDate.now().toString() : dtDevolucao;
        aluguel.setDt_devolucao(LocalDate.parse(dtDevolucao));
        return aluguel;
    }

    private void limpaCampos() {
        etDtRetirada.setText("");
        etDtDevolucao.setText("");
        spAluno.setSelection(0);
        spExemplar.setSelection(0);
    }

    private void spinnerExemplar() {
        String default_item = "Selecione um Exemplar";
        try {
            List<String> exemplares = new ArrayList<>();
            exemplares.add(default_item);
            List<Livro> livros = livroCtrl.listar();
            List<Revista> revistas = revistaCtrl.listar();
            for (Livro livro : livros) {
                exemplares.add(livro.toString());
            }
            for (Revista revista : revistas) {
                exemplares.add(revista.toString());
            }
            ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, exemplares);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spExemplar.setAdapter(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void spinnerAluno() {
        String default_item = "Selecione um Aluno";
        try {
            List<String> listAlunos = new ArrayList<>();
            listAlunos.add(default_item);
            List<Aluno> alunos = alunoCtrl.listar();
            for (Aluno aluno : alunos) {
                listAlunos.add(aluno.toString());
            }
            ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, listAlunos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spAluno.setAdapter(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Livro montaLivro(String atributos[]) {
        Livro livro = new Livro();

        if (atributos[0] != "Selecione um Exemplar") {
            String codigoExemplarStr = atributos[0].strip();
            int codigo = Integer.parseInt(codigoExemplarStr.isEmpty() ? "0" : codigoExemplarStr);

            String isbnStr = atributos[1].strip();
            String nomeStr = atributos[2].strip();

            String qtdPaginasStr = atributos[3].strip();
            int qtdPaginas = Integer.parseInt(qtdPaginasStr.isEmpty() ? "0" : qtdPaginasStr);

            String edicaoStr = atributos[4].strip();
            int edicao = Integer.parseInt(edicaoStr.isEmpty() ? "0" : edicaoStr);

            livro.setCodigo(codigo);
            livro.setNome(nomeStr);
            livro.setQtdPaginas(qtdPaginas);
            livro.setIsbn(isbnStr);
            livro.setEdicao(edicao);
        }
        return livro;
    }

    private Revista montaRevista(String atributos[]) {
        Revista revista = new Revista();
        if (atributos[0] != "Selecione um Exemplar") {
            String codigoExemplarStr = atributos[0].strip();
            int codigo = Integer.parseInt(codigoExemplarStr.isEmpty() ? "0" : codigoExemplarStr);

            String issnStr = atributos[1].strip();
            String nomeStr = atributos[2].strip();

            String qtdPaginasStr = atributos[3].strip();
            int qtdPaginas = Integer.parseInt(qtdPaginasStr.isEmpty() ? "0" : qtdPaginasStr);

            revista.setCodigo(codigo);
            revista.setNome(nomeStr);
            revista.setQtdPaginas(qtdPaginas);
            revista.setIssn(issnStr);
        }
        return revista;
    }

    private Aluno montaAluno(String atributos[]) {

        Aluno aluno = new Aluno();

        if (atributos[0] != "Selecione um Aluno") {
            String etRaStr = atributos[0].strip();
            int ra = Integer.parseInt(etRaStr.isEmpty() ? "0" : etRaStr);

            String etNomeAlunoStr = atributos[1].strip();
            String etEmailAlunoStr = atributos[2].strip();

            aluno.setRa(ra);
            aluno.setNome(etNomeAlunoStr);
            aluno.setEmail(etEmailAlunoStr);
        }
        return aluno;
    }
}