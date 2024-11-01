package com.mycompany.gestionasistencia.modelo;

import com.mycompany.gestionasistencia.modelo.JornadaDias;
import java.sql.Time;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-01T17:34:53", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(JornadasDeTrabajo.class)
public class JornadasDeTrabajo_ { 

    public static volatile SingularAttribute<JornadasDeTrabajo, Time> horaEntrada;
    public static volatile ListAttribute<JornadasDeTrabajo, JornadaDias> jornadaDias;
    public static volatile SingularAttribute<JornadasDeTrabajo, Integer> id;
    public static volatile SingularAttribute<JornadasDeTrabajo, Time> horaSalida;

}