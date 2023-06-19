package com.example.pedidosbach.domain.enums

import com.google.android.gms.common.annotation.KeepForSdk

enum class ErrorFirebase(val code: Int, val message : String) {
    ERROR_INVALID_CUSTOM_TOKEN(17000,"Token personalizado no válido"),
    ERROR_CUSTOM_TOKEN_MISMATCH(17002,"Discrepancia de token personalizado"),
    ERROR_INVALID_CREDENTIAL(17004,"credenciales incorrectas"),
    ERROR_USER_DISABLED(17005,"Este correo esta inhabilitado"),
    ERROR_OPERATION_NOT_ALLOWED(17006,"Operacion no permitida"),
    ERROR_EMAIL_ALREADY_IN_USE(17007,"Este correo ya está registrado"),
    ERROR_INVALID_EMAIL(17008,"Formato de correo inválido"),
    ERROR_WRONG_PASSWORD(17009,"Contraseña incorrecta"),
    ERROR_TOO_MANY_REQUESTS(17010,"Hay demasiadas solicitudes"),
    ERROR_USER_NOT_FOUND(17011,"El correo ingresado no esta registrado"),
    ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL(17012,"Cuenta existe con credencial diferente"),
    ERROR_REQUIRES_RECENT_LOGIN(17014,"Requiere inicio de sesión reciente"),
    ERROR_PROVIDER_ALREADY_LINKED(17015,"No hay un proveedor vinculado"),
    ERROR_NO_SUCH_PROVIDER(17016,"No hay proveedor"),
    ERROR_INVALID_USER_TOKEN(17017,"Token de usuario expirado"),
    ERROR_NETWORK_REQUEST_FAILED(17020,"Respuesta del usuario fallida"),
    ERROR_USER_TOKEN_EXPIRED(17021,"Token expirado"),
    ERROR_INVALID_API_KEY(17023,"Clave API inválida"),
    ERROR_USER_MISMATCH(17024,"Usuario igual"),
    ERROR_CREDENTIAL_ALREADY_IN_USE(17025,"Estos credenciales ya están en uso"),
    ERROR_WEAK_PASSWORD(17026,"Contraseña débil"),
    ERROR_APP_NOT_AUTHORIZED(17028,"Error aplicacion no autorizada"),
    ERROR_NO_SIGNED_IN_USER(17495,"Ningún usuario registrado"),
    ERROR_INTERNAL_ERROR(17499,"Error interno")
}