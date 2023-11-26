function login() {
    var user, pass, userType;
    user = document.getElementById("user").value;
    pass = document.getElementById("pass").value;
    userType = document.getElementById("userType").value;
    console.log(user, pass,userType)

    var usuarios = [
        {"usuario":"glaker0","contraseña":"sL8@k%gI\"&uNl","tipoDeUsuario":"Gerente"},
        {"usuario":"cjelphs1","contraseña":"jD9*0p=z|K52>~wO","tipoDeUsuario":"Administrador de Sistema"},
        {"usuario":"dgruszka2","contraseña":"qU2=1}CU","tipoDeUsuario":"Administrador de Datos"},
        {"usuario":"scasbolt3","contraseña":"oE4+M/F',SFG%8","tipoDeUsuario":"Receptionista"},
        {"usuario":"rhekkert4","contraseña":"mP8!\"7&ER<~%A2","tipoDeUsuario":"Receptionista"},
        {"usuario":"rodogherty5","contraseña":"oA0_+)~)7\"A<7}#y","tipoDeUsuario":"Empleado"},
        {"usuario":"ssoaper6","contraseña":"bI2#''1fy","tipoDeUsuario":"Empleado"},
        {"usuario":"ncathrall7","contraseña":"pN1!_uktV*sNIL~q","tipoDeUsuario":"Empleado"},
        {"usuario":"crosson8","contraseña":"uW6$I5t4<j","tipoDeUsuario":"Empleado"},
        {"usuario":"lpayfoot9","contraseña":"mB6?Yv(m<IgLGG","tipoDeUsuario":"Cliente"},
        {"usuario":"gaviotica911","contraseña":"uwu","tipoDeUsuario":"Cliente"},
        {"usuario":"coislindo","contraseña":"amor","tipoDeUsuario":"Cliente"},
        {"usuario":"papiGelvez","contraseña":"hola","tipoDeUsuario":"Cliente"}
    ];

  
    var usuarioEncontrado = usuarios.find(function(usuarioJSON) {
        return usuarioJSON.usuario === user && usuarioJSON.contraseña === pass && usuarioJSON.tipoDeUsuario === userType;
    });

    if (usuarioEncontrado) {
       
        switch (userType) {
            case "cliente":
                window.location.href = "indexCliente.html";
                break;
            case "empleado":
                window.location.href = "indexEmpleado.html";
                break;
            case "receptionista":
                window.location.href = "indexReceptionista.html";
                break;
            case "administradorDatos":
                window.location.href = "adminDatosIndex.html";
                break;
            case "administradorSistema":
                window.location.href = "indexAdminSistema.html";
                break;
            case "Gerente":
                window.location.href = "indexReceptionista.html";
                break;
            default:
                alert("Tipo de usuario no válido");
        }
    } else {
        
        alert("Credenciales incorrectas");
    }
}
