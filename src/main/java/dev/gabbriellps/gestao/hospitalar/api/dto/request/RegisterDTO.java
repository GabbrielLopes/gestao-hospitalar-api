package dev.gabbriellps.gestao.hospitalar.api.dto.request;

import dev.gabbriellps.gestao.hospitalar.api.enumeration.TipoUsuario;

public record RegisterDTO(String login, String password, TipoUsuario role) {
}