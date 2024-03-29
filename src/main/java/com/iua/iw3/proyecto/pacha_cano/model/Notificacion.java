package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "notificaciones")
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String contenido;

    private Long numeroOrden;

    private boolean leida = false;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private Date fechaNotificacion;

    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private Date fechaNotificacionAceptada;

    private String observacion;

    private Long userId;



}
