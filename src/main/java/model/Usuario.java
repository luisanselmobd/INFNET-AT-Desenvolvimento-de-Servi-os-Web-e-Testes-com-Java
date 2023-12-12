package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Usuario {
    private int id;
    private String nome;
    private String senha;
}
