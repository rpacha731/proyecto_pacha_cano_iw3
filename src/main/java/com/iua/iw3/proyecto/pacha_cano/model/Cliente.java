package com.iua.iw3.proyecto.pacha_cano.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.ClienteJsonSerializer;
import lombok.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="clientes")
@JsonSerialize(using = ClienteJsonSerializer.class)
@ApiModel(value = "Cliente", description = "Clase que describe al cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = -4871142170558316526L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Razón social que identifica al cliente", example = "PC SA", required = true)
    @Column(nullable = false, unique = true) //No sé si la razón social tiene que ser unique
    private String razonSocial;

    @ApiModelProperty(notes = "Contacto del cliente", example = "pc@gmail.com", required = true)
    private String contacto;

    @Column(unique = true)
    @ApiModelProperty(notes = "Código externo del cliente", example = "CLN123", required = true)
    private String codigoExterno;
}
