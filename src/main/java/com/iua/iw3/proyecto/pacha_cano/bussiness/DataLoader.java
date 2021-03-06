package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.RolRepository;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserRepository;
import com.iua.iw3.proyecto.pacha_cano.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RolRepository rolRepository;

    private OrdenCargaRepository ordenCargaRepository;

    @Override
    public void run(String... args) {

        if (rolRepository.findByNombre("ROLE_ADMIM").isEmpty()) {
            Rol admin = Rol.builder()
                    .nombre("ROLE_ADMIM")
                    .descripcion("Rol de administrador")
                    .build();
            try {
                rolRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            Rol user = Rol.builder()
                    .nombre("ROLE_USER")
                    .descripcion("Rol de usuario")
                    .build();
            try {
                rolRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (userRepository.findFirstByEmail("pc@pc.com").isEmpty()) {
            Set<Rol> roles = rolRepository.findAll().stream().collect(Collectors.toSet());
            User admin = User.builder()
                    .nombre("Pacha-Cano")
                    .apellido("IW3")
                    .enabled(true)
                    .email("pc@pc.com")
                    .password(passwordEncoder.encode("olis12345"))
                    .roles(roles)
                    .build();
            try {
                userRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (userRepository.findFirstByEmail("user@pc.com").isEmpty()) {
            Set<Rol> rol = new HashSet<>();
            rol.add(rolRepository.findByNombre("ROLE_USER").get());
            User user = User.builder()
                    .nombre("User 1")
                    .apellido("super")
                    .enabled(true)
                    .email("user@pc.com")
                    .password(passwordEncoder.encode("12345"))
                    .roles(rol).build();
            try {
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (ordenCargaRepository.findAll().isEmpty()) {
            Camion camion = Camion.builder()
                    .patente("ABC1234")
                    .descripcion("Alto camion papaaaaa")
                    .cisternado(250000L)
                    .codigoExterno("CAM_1234")
                    .build();

            Cliente cliente = Cliente.builder()
                    .razonSocial("Arcor")
                    .contacto("arc@gmail.com")
                    .codigoExterno("CLN123")
                    .build();

            Chofer chofer = Chofer.builder()
                    .nombre("Nicolas")
                    .apellido("G??mez")
                    .dni(4894891531L)
                    .codigoExterno("NIC_GOM_531")
                    .build();

            Producto producto = Producto.builder()
                    .nombre("Gas Liquido")
                    .descripcion("Gas GNC Liquido Vol??til")
                    .codigoExterno("GNC_LV")
                    .build();

            DatosCarga datosCarga = DatosCarga.builder()
                    .masaAcumulada(25000.65)
                    .temperatura(32.21)
                    .densidad(0.98)
                    .caudal(1.256)
                    .build();


            List<DatosCarga> listAux = new ArrayList<>();
            listAux.add(datosCarga);
            listAux.add(datosCarga);

            OrdenCarga ordenCarga = OrdenCarga.builder()
                    .numeroOrden(1L)
                    .camion(camion)
                    .cliente(cliente)
                    .chofer(chofer)
                    .producto(producto)
                    .codigoExterno("ORD_001_2021")
                    .pesoInicial(0.235)
                    .pesoFinal(255000.254)
                    .frecuencia(10)
                    .password(OrdenCarga.generateRandomPassword())
                    .estado(Estados.E1)
                    .fechaHoraRecepcion(new Date())
                    .fechaHoraPesoInicial(new Date())
                    .fechaHoraTurno(new Date())
                    .preset(2550L)
                    .fechaHoraInicioCarga(new Date())
                    .fechaHoraFinCarga(new Date())
                    .fechaHoraPesoFinal(new Date())
                    .registroDatosCarga(listAux)
                    .promedioDatosCarga(datosCarga)
                    .build();

            try {
                this.ordenCargaRepository.save(ordenCarga);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

    }
}
