package com.example.appecommercekotlin.Entity

class Compra {

    private var id = 0
    private var idusuario = 0
    private var subtotal: String? = null
    private var entrega: String? = null
    private var impuesto: String? = null
    private var total: String? = null
    private var productos: String? = null

    private var fechacompra: String? = null

    fun Compra() {}

    fun Compra(
        id: Int,
        idusuario: Int,
        subtotal: String?,
        entrega: String?,
        impuesto: String?,
        total: String?,
        productos: String?,
        fechacompra: String?
    ) {
        this.id = id
        this.idusuario = idusuario
        this.subtotal = subtotal
        this.entrega = entrega
        this.impuesto = impuesto
        this.total = total
        this.productos = productos
        this.fechacompra = fechacompra
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getIdusuario(): Int {
        return idusuario
    }

    fun setIdusuario(idusuario: Int) {
        this.idusuario = idusuario
    }

    fun getSubtotal(): String? {
        return subtotal
    }

    fun setSubtotal(subtotal: String?) {
        this.subtotal = subtotal
    }

    fun getEntrega(): String? {
        return entrega
    }

    fun setEntrega(entrega: String?) {
        this.entrega = entrega
    }

    fun getImpuesto(): String? {
        return impuesto
    }

    fun setImpuesto(impuesto: String?) {
        this.impuesto = impuesto
    }

    fun getTotal(): String? {
        return total
    }

    fun setTotal(total: String?) {
        this.total = total
    }

    fun getProductos(): String? {
        return productos
    }

    fun setProductos(productos: String?) {
        this.productos = productos
    }

    fun getFechacompra(): String? {
        return fechacompra
    }

    fun setFechacompra(fechacompra: String?) {
        this.fechacompra = fechacompra
    }
}