package com.iua.iw3.proyecto.pacha_cano.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.OrdenCargaJsonSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "ordenes")
@JsonSerialize(using = OrdenCargaJsonSerializer.class)
@ApiModel(value = "Orden de carga", description = "Clase que describe a la orden de carga")
public class OrdenCarga implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Número que identifica a cada orden", example = "198887", required = true)
    @Column(nullable = false, unique = true)
    private Long numeroOrden;

    @ApiModelProperty(notes = "Id que identifica al camión asignado a una orden", example = "333", required = true)
    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_camion")
    private Camion camion;

    @ApiModelProperty(notes = "Número que identifica al cliente de una orden", example = "333", required = true)
    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ApiModelProperty(notes = "Número que identifica al chofer asignado a una orden", example = "333", required = true)
    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_chofer")
    private Chofer chofer;

    @ApiModelProperty(notes = "Número que identifica al producto de una orden", example = "333", required = true)
    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ApiModelProperty(notes = "Fecha de carga prevista", example = "2021/11/20", required = true)
    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraTurno;

    @ApiModelProperty(notes = "Peso al que debe llegar el camión para cerrar la orden", example = "21000", required = true)
    @Column(nullable = false)
    private Long preset;

    @ApiModelProperty(notes = "Estado actual de la orden", example = "E1", required = true, allowableValues = "E1, E2, E3, E4")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estados estado;

    @ApiModelProperty(notes = "Frecuencia de guardado de datos de carga", example = "5", required = true, allowableValues = "1, 2, 5, 10, 15")
    private Integer frecuencia;

    @ApiModelProperty(notes = "Fecha y hora en la que se crea la orden", example = "2021-11-19 23:48:08", required = true)
    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date fechaHoraRecepcion;

    // Hasta acá sería el E1

    @ApiModelProperty(notes = "Peso inicial del camión (tara)", example = "0.235", required = true)
    private Double pesoInicial; // TARA

    @ApiModelProperty(notes = "Contraseña para activar la bomba y el caudalímetro", example = "12345", required = true)
    @Column (length = 5)
    private Integer password;

    @ApiModelProperty(notes = "Fecha y hora en el que se carga el peso inicial", example = "2021-11-19 23:48:08", required = true)
    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraPesoInicial;

    // Hasta acá sería el E2

    @ApiModelProperty(notes = "Fecha y hora en el que se inicia la carga de datos", example = "2021-11-19 23:48:08", required = true)
    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraInicioCarga;

    @ApiModelProperty(notes = "Lista de los registros de datos de carga")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "registro_datos_carga")
    private List<DatosCarga> registroDatosCarga;

    @ApiModelProperty(notes = "Fecha y hora en el que se finaliza la carga de datos", example = "2021-11-19 23:48:08", required = true)
    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraFinCarga;

    @ApiModelProperty(notes = "Peso final del camión", example = "255000.254", required = true)
    private Double pesoFinal;

    @ApiModelProperty(notes = "Fecha y hora en el que se carga el peso final", example = "2021-11-19 23:48:08", required = true)
    @Column(columnDefinition = "DATETIME")
    private Date fechaHoraPesoFinal;

    // Hasta acá sería el E3

    @ApiModelProperty(notes = "Masa acumulada total al cerrar la carga", example = "30")
    private Double masaAcumuladaTotal;

    @ApiModelProperty(notes = "Temperatura promedio", example = "30")
    private Double temperaturaPromedio;

    @ApiModelProperty(notes = "Densidad promedio", example = "0.11")
    private Double densidadPromedio;

    @ApiModelProperty(notes = "Caudal promedio", example = "11.2")
    private Double caudalPromedio;

    // El E4 es la conciliación

    @Column(unique = true)
    @ApiModelProperty(notes = "Código externo de la orden de carga", example = "ORD_001_2021", required = true)
    private String codigoExterno;

    @ApiModelProperty(notes = "Temperatura de umbral para enviar notificación", example = "45", required = true, allowableValues = "40, 45, 50")
    private Float temperaturaUmbral = 40F;

    public static Integer generateRandomPassword () {
        double aux = 10000 + Math.random() * 90000;
        return (int) aux;
    }

}
