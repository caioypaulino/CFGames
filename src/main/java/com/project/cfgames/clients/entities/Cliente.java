package com.project.cfgames.clients.entities;

import com.fasterxml.jackson.annotation.*;
import com.project.cfgames.clients.entities.relations.EnderecoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "CLIENTES")
@Entity(name = "Cliente")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Cliente.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    @NotBlank(message = "Campo não informado!")
    @Pattern(regexp = "^(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*(?: (?:(?:e|y|de(?:(?: la| las| lo| los))?|do|dos|da|das|del|van|von|bin|le) )?(?:(?:(?:d'|D'|O'|Mc|Mac|al\\-))?(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+|(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+(?:\\-(?:[\\p{Lu}&&[\\p{IsLatin}]])(?:(?:')?(?:[\\p{Ll}&&[\\p{IsLatin}]]))+)*))+(?: (?:Jr\\.|II|III|IV))?$", message = "Nome inválido ou incompleto.")
    private String nome;
    @NotBlank(message = "Campo não informado!")
    @CPF(message = "CPF inválido.")
    private String cpf;
    @NotNull(message = "Campo não informado!")
    @PastOrPresent(message = "Data de nascimento inválida.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;
    @NotBlank(message = "Campo não informado!")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message = "Telefone inválido.")
    private String telefone;
    @NotBlank(message = "Campo não informado!")
    @Email(message = "Email deve ser válido.")
    private String email;
    @NotBlank(message = "Campo não informado!")
    @Length(min = 8, message = "Senha muito curta (Mínimo de 8 caracteres).")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]+$", message = "Senha fraca ou inválida (Deve conter pelo menos: 1 Letra maiúscula e minúscula, 1 Número, 1 Caracter especial(Exemplos: $*&@#)")
    private String senha;
    @NotBlank(message = "Campo não informado!")
    @Transient
    private String confirmaSenha;

    @OneToMany(mappedBy = "cliente")
    private Set<EnderecoCliente> enderecos;

    @ManyToMany
    @JoinTable(name = "cartoes_clientes", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "numero_cartao"))
    private Set<Cartao> cartoes;

    public Cliente(String nome, String cpf, LocalDate dataNascimento, String telefone, String email, String senha, Set<EnderecoCliente> enderecos, Set<Cartao> cartoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.enderecos = enderecos;
        this.cartoes = cartoes;
    }

    public Set<Cartao> getCartoes() {
        return cartoes;
    }

    public void cartoesCliente(Cartao cartao) {
        cartoes.add(cartao);
    }
}