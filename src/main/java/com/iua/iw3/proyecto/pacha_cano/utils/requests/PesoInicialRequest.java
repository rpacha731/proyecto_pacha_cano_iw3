package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PesoInicialRequest {
    private Long numeroOrden;
    private Double pesoInicial;
}
