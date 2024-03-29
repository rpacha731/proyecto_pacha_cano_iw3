package com.iua.iw3.proyecto.pacha_cano.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.DatosCargaJsonSerializer;
import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonSerialize(using = DatosCargaJsonSerializer.class)
@ApiModel(value = "Dato de carga", description = "Clase que describe al dato de carga")
public class DatosCarga implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Masa acumulada del dato de carga", example = "30", required = true)
    private Double masaAcumulada;

    @ApiModelProperty(notes = "Temperatura del dato de carga", example = "30", required = true)
    private Double temperatura;

    @ApiModelProperty(notes = "Densidad del dato de carga", example = "0.11", required = true)
    private Double densidad;

    @ApiModelProperty(notes = "Caudal del dato de carga", example = "11.2", required = true)
    private Double caudal;

    @ApiModelProperty(notes = "Fecha / hora en la que se guardó el dato", example = "11.2")
    @Column(columnDefinition = "DATETIME DEFAULT NULL")
    private Date estampaTiempo;
}
