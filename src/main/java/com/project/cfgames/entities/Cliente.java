package com.project.cfgames.entities;

import com.fasterxml.jackson.annotation.*;
import com.project.cfgames.entities.enums.Genero;
import com.project.cfgames.entities.relations.EnderecoCliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "CLIENTES")
@Entity(name = "Cliente")

@Getter
@Setter
@NoArgsConstructor

@JsonIdentityInfo(
        scope = Cliente.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "accountNonLocked", "authorities", "password", "username", "credentialsNonExpired", "accountNonExpired"})
public class Cliente implements UserDetails {
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
    @NotNull(message = "Campo não informado!")
    private Genero genero;
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
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String confirmaSenha;

    @OneToMany(mappedBy = "cliente")
    private Set<EnderecoCliente> enderecos;

    @ManyToMany
    @JoinTable(name = "cartoes_clientes", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "numero_cartao"))
    private Set<Cartao> cartoes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "perfis_clientes", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private Set<Perfil> perfis;

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Genero genero, String telefone, String email, String senha, Set<EnderecoCliente> enderecos, Set<Cartao> cartoes) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.enderecos = enderecos;
        this.cartoes = cartoes;
    }

    public void addCartoesCliente(Cartao cartao) {
        cartoes.add(cartao);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}