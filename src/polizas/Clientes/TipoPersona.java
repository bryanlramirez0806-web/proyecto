package polizas.Clientes;

/**
 * Tipo de persona del cliente. Son los valores que acepta la columna tipoPersona.
 * FISICA: persona (lleva apellidos y CURP, RFC de 13 caracteres).
 * MORAL: empresa (sin apellidos ni CURP, RFC de 12 caracteres).
 */
public enum TipoPersona {
    FISICA, MORAL
}
