package com.example.appecommercekotlin.Entity

class Usuario {

    private var id = 0
    private var correo: String? = null
    private var usuario: String? = null
    private var password: String? = null
    private var nombre: String? = null
    private var edad = 0
    private var genero: String? = null
    private var direccion: String? = null

    fun Usuario() {}

    fun Usuario(
        id: Int,
        correo: String?,
        usuario: String?,
        password: String?,
        nombre: String?,
        edad: Int,
        genero: String?,
        direccion: String?
    ) {
        this.id = id
        this.correo = correo
        this.usuario = usuario
        this.password = password
        this.nombre = nombre
        this.edad = edad
        this.genero = genero
        this.direccion = direccion
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getCorreo(): String? {
        return correo
    }

    fun setCorreo(correo: String?) {
        this.correo = correo
    }

    fun getUsuario(): String? {
        return usuario
    }

    fun setUsuario(usuario: String?) {
        this.usuario = usuario
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }

    fun getEdad(): Int {
        return edad
    }

    fun setEdad(edad: Int) {
        this.edad = edad
    }

    fun getGenero(): String? {
        return genero
    }

    fun setGenero(genero: String?) {
        this.genero = genero
    }

    fun getDireccion(): String? {
        return this.direccion
    }

    fun setDireccion(direccion: String?) {
        this.direccion = direccion
    }
}