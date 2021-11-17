package com.iua.iw3.proyecto.pacha_cano.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DatosCarga implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double masaAcumulada;

    private Double temperatura;

    private Double densidad;

    private Double caudal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orden_id")
    private OrdenCarga orden;
}