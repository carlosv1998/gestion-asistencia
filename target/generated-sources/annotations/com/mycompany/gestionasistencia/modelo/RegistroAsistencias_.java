package com.mycompany.gestionasistencia.modelo;

import com.mycompany.gestionasistencia.modelo.Usuarios;
import java.sql.Time;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-01T17:34:53", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(RegistroAsistencias.class)
public class RegistroAsistencias_ { 

    public static volatile SingularAttribute<RegistroAsistencias, LocalDate> fecha;
    public static volatile SingularAttribute<RegistroAsistencias, Time> salidaEspecial;
    public static volatile SingularAttribute<RegistroAsistencias, Time> entradaEspecial;
    public static volatile SingularAttribute<RegistroAsistencias, Time> horaEntrada;
    public static volatile SingularAttribute<RegistroAsistencias, Usuarios> usuario;
    public static volatile SingularAttribute<RegistroAsistencias, Integer> id;
    public static volatile SingularAttribute<RegistroAsistencias, Time> horaSalida;

}