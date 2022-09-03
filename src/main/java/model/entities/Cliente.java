package model.entities;

import java.util.Date;

public class Cliente {
	private Long id;
	private String nome;
	private Integer cpf;
	private Date dataNascimento;
	private Long telefone;
	private String email;
	private String senha;
	
	public Cliente() {
	}
	
	public Cliente(Long id, String nome, Integer cpf, Date dataNascimento, Long telefone, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}

	public void createCliente() {
		
	}
	
	public void readCliente() {
		
	}
	
	public void updateCliente() {
		
	}
	
	public void deleteCliente() {
		
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
