package com.mycompany.gestionasistencia.modelo;

import com.mycompany.gestionasistencia.modelo.Cargos;
import com.mycompany.gestionasistencia.modelo.Contratos;
import com.mycompany.gestionasistencia.modelo.Departamentos;
import com.mycompany.gestionasistencia.modelo.Gerencias;
import com.mycompany.gestionasistencia.modelo.Roles;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-01T17:34:53", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, Gerencias> gerencia;
    public static volatile SingularAttribute<Usuarios, Integer> sueldo;
    public static volatile SingularAttribute<Usuarios, Contratos> contrato;
    public static volatile SingularAttribute<Usuarios, String> nombre;
    public static volatile SingularAttribute<Usuarios, Roles> rol;
    public static volatile SingularAttribute<Usuarios, String> rut;
    public static volatile SingularAttribute<Usuarios, String> correo;
    public static volatile SingularAttribute<Usuarios, Departamentos> departamento;
    public static volatile SingularAttribute<Usuarios, LocalDate> fechaCreacion;
    public static volatile SingularAttribute<Usuarios, Integer> id;
    public static volatile SingularAttribute<Usuarios, Cargos> cargo;
    public static volatile SingularAttribute<Usuarios, String> telefono;
    public static volatile SingularAttribute<Usuarios, String> contra;

}