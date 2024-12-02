package br.edu.fateczl.atividade14_01;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.fateczl.atividade14_01.controller.AlunoController;
import br.edu.fateczl.atividade14_01.model.Aluno;
import br.edu.fateczl.atividade14_01.persistence.AlunoDao;


public class AlunosFragment extends Fragment {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */

    private View view;
    private TextView tvListarAlunos;
    private TextView etRa;
    private Button btnBuscarAluno;
    private EditText etNomeAluno;
    private EditText etEmailAluno;
    private Button btnInserirAluno;
    private Button btnModificarAluno;
    private Button btnExcluirAluno;
    private Button btnListarAluno;
    private AlunoController alunoCtrl;

    public AlunosFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alunos, container, false);
        alunoCtrl = new AlunoController(new AlunoDao(view.getContext()));

        tvListarAlunos = view.findViewById(R.id.tvListaAlunos);
        tvListarAlunos.setMovementMethod(new ScrollingMovementMethod());

        etRa = view.findViewById(R.id.etRa);
        btnBuscarAluno = view.findViewById(R.id.btnBuscarAluno);
        etNomeAluno = view.findViewById(R.id.etNomeAluno);
        etEmailAluno = view.findViewById(R.id.etEmailAluno);

        btnInserirAluno = view.findViewById(R.id.btnInserirAluno);
        btnModificarAluno = view.findViewById(R.id.btnModificarAluno);
        btnExcluirAluno = view.findViewById(R.id.btnExcluirAluno);
        btnListarAluno = view.findViewById(R.id.btnListarAluno);

        btnInserirAluno.setOnClickListener(op -> inserir());
        btnModificarAluno.setOnClickListener(op -> modificar());
        btnExcluirAluno.setOnClickListener(op -> excluir());
        btnListarAluno.setOnClickListener(op -> listar());
        btnBuscarAluno.setOnClickListener(op -> buscar());



        return view;
    }

    private void inserir() {
        Aluno aluno = montaAluno();
        try {
            alunoCtrl.inserir(aluno);
            Toast.makeText(view.getContext(), "Aluno INSERIDO com sucesso", Toast.LENGTH_SHORT).show();
            limpaCampos();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void modificar() {
        Aluno aluno = montaAluno();
        try {
            alunoCtrl.modificar(aluno);
            Toast.makeText(view.getContext(), "Aluno MODIFICAOD com sucesso", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        Aluno aluno = montaAluno();
        try {
            alunoCtrl.deletar(aluno);
            Toast.makeText(view.getContext(), "Aluno EXCLUÍDO com sucesso", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void listar() {
        try {
            List<Aluno> alunos = alunoCtrl.listar();
            StringBuffer buffer = new StringBuffer();
            for (Aluno a : alunos) {
                buffer.append(a.toString() + "\n");
            }
            tvListarAlunos.setText(buffer.toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void buscar() {
        Aluno aluno = montaAluno();
        try {
            aluno = alunoCtrl.buscar(aluno);
            if (aluno.getNome() != null) {
                preencheCampos(aluno);
            } else {
                Toast.makeText(view.getContext(), "Aluno não encontrado", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencheCampos(Aluno aluno) {
        etRa.setText(String.valueOf(aluno.getRa()));
        etNomeAluno.setText(aluno.getNome());
        etEmailAluno.setText(aluno.getEmail());
    }

    private Aluno montaAluno() {
        Aluno aluno = new Aluno();
        String etRaStr = etRa.getText().toString();
        int ra = Integer.parseInt(etRaStr.isEmpty() ? "0" : etRaStr);
        String etNomeAlunoStr = etNomeAluno.getText().toString();
        String etEmailAlunoStr = etEmailAluno.getText().toString();

        aluno.setRa(ra);
        aluno.setNome(etNomeAlunoStr);
        aluno.setEmail(etEmailAlunoStr);
        return aluno;
    }

    private void limpaCampos() {
        etRa.setText("");
        etNomeAluno.setText("");
        etEmailAluno.setText("");
    }
}